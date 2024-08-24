package com.hs.settlement.controller;

import com.hs.settlement.domain.Request;
import com.hs.settlement.domain.Settlement;
import com.hs.settlement.domain.Transaction;
import com.hs.settlement.service.Halt;
import com.hs.settlement.service.Initialization;
import com.hs.settlement.service.Redemption;
import com.hs.settlement.service.Subsciption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

// 导出数据，不需要调用后端

@RestController
@RequestMapping("/settlement")
public class SettlementController {

    @Autowired
    private Initialization initialService;

    @Autowired
    private Subsciption subsciptionService;

    @Autowired
    private Redemption redemptService;

    @Autowired
    private Halt haltService;

    /*日初始化：传入当前日期；返回成功失败、(初始化后日期、更新的产品数)*/
    @GetMapping("/initial")
    public ResponseEntity<Map<String, Object>> initialization(String date) {
        Map<String, Object> response = new HashMap<>();
        if (!isValidDate(date)) {
            response.put("message", "Invalid date.");// 前端日期格式不对
            return ResponseEntity.badRequest().body(response);
        }
        String newDate = getTransactionDate(date,true); // 计算下一个交易日
        int result = initialService.updateNavs(date, newDate); // 若返回-1则数据库有异常
        if(result == -1){
            response.put("message", "Error in the database.");
            return ResponseEntity.badRequest().body(response);
        }
        response.put("message", "Succeed.");
        response.put("newDate", newDate); // 初始化后的日期（今天日期）
        response.put("allProductCount", result); // 当日的产品行情总数（即更新的产品净值总数）
        return ResponseEntity.ok(response);
    }

    /*接收行情：传入日期，返回所有产品当日净值的记录*/
    @GetMapping("/quotatian")
    public ResponseEntity<Map<String, Object>> getQuotation() {
        Map<String, Object> response = new HashMap<>();
        try{
            List<Settlement> quotationList = initialService.getQuotation();
            response.put("allProductCount", quotationList.size());// 获取行情的产品数
            // 产品行情数据列表（当天日期、产品代码、当日净值）
            response.put("quotationList", quotationList);
            return ResponseEntity.ok(response);
        }
        catch (Exception e){
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }


    /*申购确认：传入初始化后的日期；返回份额交易表的关键字段的列表、交易总数*/
    @GetMapping("/confirmSubscribe")
    public ResponseEntity<Map<String, Object>> confirmSubscribe(String date) {
        Map<String, Object> response = new HashMap<>();
        if (!isValidDate(date)) {
            response.put("message", "Invalid date.");// 前端日期格式不对
            return ResponseEntity.badRequest().body(response);
        }
        String newDate = getTransactionDate(date,false); // 计算上一个交易日
        try{
            List<Request> subscriptionList =  subsciptionService.confirmSubscribe(newDate);
            response.put("confirmCount", subscriptionList.size());// 确认的申购申请数
            // 确认的申购申请的数据列表（订单号、产品代码、银行卡号、金额、份额、订单状态）
            response.put("subscriptionList", subscriptionList);
            return ResponseEntity.ok(response);
        }
        catch (Exception e){
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /*赎回确认：传入日期，返回份额交易表的关键字段的列表、资金交易表的关键字段的列表、交易总数*/
    @GetMapping("/confirmRedempt")
    public ResponseEntity<Map<String, Object>> confirmRedempt(String date) {
        Map<String, Object> response = new HashMap<>();
        if (!isValidDate(date)) {
            response.put("message", "Invalid date.");// 前端日期格式不对
            return ResponseEntity.badRequest().body(response);
        }
        String newDate = getTransactionDate(date,false); // 计算上一个交易日
        String transactionId = generateTransactionId(date); // 获取今天的新交易流水id
        try{
            List<Request> redemptionList =  redemptService.confirmRedempt(newDate, date, transactionId);
            List<Transaction> transactionList =  redemptService.getTransaction(date);
            response.put("confirmCount", redemptionList.size());// 确认的赎回申请数
            // 确认的赎回申请的数据列表（订单号、产品代码、银行卡号、金额、份额、订单状态）
            response.put("redemptionList", redemptionList);
            // 赎回金额入账的数据列表
            response.put("transactionList", transactionList);
            return ResponseEntity.ok(response);
        }
        catch (Exception e){
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    /*停止当日申请：传入日期，返回当日提交的待确认的申购数和赎回数*/
    @GetMapping("/halt")
    public ResponseEntity<Map<String, Object>> endRequest(String date) {
        Map<String, Object> response = new HashMap<>();
        if (!isValidDate(date)) {
            response.put("message", "Invalid date.");// 前端日期格式不对
            return ResponseEntity.badRequest().body(response);
        }
        try{
            response.put("subsriptionCount", haltService.getSubCount(date));// 当日待确认的申购数
            response.put("redemptionCount", haltService.getRedCount(date)); // 当日待确认的赎回数
            return ResponseEntity.ok(response);
        }
        catch (Exception e){
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // 根据当天和当天申请次数生成申请编号
    public String generateTransactionId(String date)
    {
        int requestCount = redemptService.countTodayTransaction(date); // 当日资金流水数
        return LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE)
                .format(DateTimeFormatter.ofPattern("yyyyMMdd")) + String.format("%06d", requestCount + 1);
    }

    // 检查日期是否合法
    private boolean isValidDate(String date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.setLenient(false);
            Date parsedDate = sdf.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    // 计算上一个/下一个交易日的日期
    public String getTransactionDate(String date, Boolean next)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parsedDate = sdf.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parsedDate);
            if(next){
                // 循环找下一个工作日
                do {
                    calendar.add(Calendar.DAY_OF_MONTH, 1); // 加一天
                } while (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                        calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
            }
            else{
                // 循环找上一个工作日
                do {
                    calendar.add(Calendar.DAY_OF_MONTH, -1); // 减一天
                } while (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                        calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
            }

            String nextDate = sdf.format(calendar.getTime());
            return nextDate;
        } catch (ParseException e) {
            throw new RuntimeException("Error parsing date", e);
        }
    }

    @PostMapping("/test/initial")
    public String insertOneSettlement(@RequestBody Settlement settlement) {
        int result = initialService.updateSettlement(settlement);
        return result > 0 ? "Insert successful" : "Insert failed";
    }
}

