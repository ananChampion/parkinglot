package jmu.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import jmu.service.PaymentService;
import jmu.vo.DayPayment;
import jmu.vo.ExcelPayment;
import jmu.vo.Payment;
import jmu.vo.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.*;


@Controller
@RequestMapping("/pay")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @RequestMapping("/goHome")
    public String goHome(Model model){
        model.addAttribute("msg", "出场成功，感谢您使用智能停车系统！");
        return "home";
    }

    @RequestMapping("/goPay")
    public String pay(){
        return "pay";
    }

    @RequestMapping("/insert")
    private String insert(Model model, @ModelAttribute("record") Record record, @ModelAttribute("time") String time, @ModelAttribute("pSum") Integer pSum){
        Payment payment = new Payment();
        payment.setPSum(pSum);
        payment.setRId(record.getRId());
        payment.setPMethod("支付宝");
        payment.setPTime(new Date());
    System.out.println("过来了"+payment);
        this.paymentService.insert(payment);
        payment = this.paymentService.queryPayment().get(0);
        model.addAttribute("record", record);
        model.addAttribute("payment", payment);
        model.addAttribute("time", time);
        return "pay";
    }

    @RequestMapping("/update")
    private String update(Payment payment, Model model){
        payment.setPTime(new Date());
        this.paymentService.update(payment);
        return "redirect:/pay/goHome";
    }

    @RequestMapping("/delete/{rId}")//restful风格
    private String delete(@PathVariable("rId") Integer rId, RedirectAttributes redirectAttributes){
        Integer num = this.paymentService.delete(rId);
        if (num == 1)
        {
            redirectAttributes.addFlashAttribute("rId", rId);
            return "redirect:/record/deleteByRId";
        }
        else {
            System.out.println("删除失败");//提示
            return "redirect:/record/selectRecord";
        }

    }

    @RequestMapping("/select/index")
    private String select(HttpServletRequest request, @ModelAttribute("records") List<Record> records, Model model){
        HttpSession session = request.getSession();
        List<Payment> payments = new ArrayList<>();
        payments = getPayments(records, session);
        model.addAttribute("records", records);
        model.addAttribute("payments", payments);
        return "index";


    }

    @RequestMapping("/select/payment")
    private String select2(HttpServletRequest request, @ModelAttribute("records") List<Record> records, Model model){
        HttpSession session = request.getSession();
        List<String> timeList = new ArrayList<>();
        List<Payment> payments = getPayments(records, session);
        for (Record record: records){
            long totalm = (record.getROuttime().getTime()-record.getRIntime().getTime());
            long m = 30*60*1000;
            double time = 1.0 * totalm / (m*2);
            DecimalFormat df = new DecimalFormat("0.0");
            String result = df.format(time);
            timeList.add(result);
        }
        model.addAttribute("records", records);
        model.addAttribute("payments", payments);
        model.addAttribute("timeList", timeList);
        return "payment";

    }

    @RequestMapping("/searchPayment")
    private String searchPayment(@ModelAttribute("records") List<Record> records, Model model){
        List<String> timeList = new ArrayList<>();
        List<Integer> rIdList = new ArrayList<>();
        for (Record record : records) {
            rIdList.add(record.getRId());
        }
        List<Payment> payments = this.paymentService.queryPaymentByRid(rIdList);
        for (Record record: records){
            long totalm = (record.getROuttime().getTime()-record.getRIntime().getTime());
            long m = 30*60*1000;
            double time = 1.0 * totalm / (m*2);
            DecimalFormat df = new DecimalFormat("0.0");
            String result = df.format(time);
            timeList.add(result);
        }
        model.addAttribute("records", records);
        model.addAttribute("payments", payments);
        model.addAttribute("timeList", timeList);

        return "payment";
    }

    private List<Payment> getPayments(@ModelAttribute("records") List<Record> records, HttpSession session) {
        List<Payment> payments;
        if (session.getAttribute("User") == null )
        {
            payments = this.paymentService.queryPayment();
        }else {
            List<Integer> rIdList = new ArrayList<>();
            for (Record record : records) {
                rIdList.add(record.getRId());
            }
            payments = this.paymentService.queryPaymentByRid(rIdList);


        }
        return payments;
    }

    @RequestMapping("/selectByUser")
    private String selectByUser(@ModelAttribute("records") List<Record> records, Model model){
        return selectPaymentByRecords(records, model);
    }

    @RequestMapping(value = "/paymentSum", produces = "application/json; charset=utf-8")
    @ResponseBody
    private List<DayPayment> paymentSum(){
        return this.paymentService.selectSumByDay();
    }

    @RequestMapping("/searchRecord")
    private String searchRecord(@ModelAttribute("records") List<Record> records, Model model){
        return selectPaymentByRecords(records, model);
    }

    private String selectPaymentByRecords(List<Record> records, Model model) {
        List<Integer> rIdList = new ArrayList<>();
        for (Record record : records) {
            rIdList.add(record.getRId());
        }
        List<Payment> payments = this.paymentService.queryPaymentByRid(rIdList);
        model.addAttribute("records", records);
        model.addAttribute("payments", payments);

        return "index";
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    private void export(HttpServletResponse response, @ModelAttribute("records") List<Record> records) throws IOException {
        // 设置响应头信息
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("收费记录.xlsx", StandardCharsets.UTF_8));

        //创建一个新类，查找所有的停车记录、收费记录，组装新类。

        List<Integer> rIdList = new ArrayList<>();
        for (Record record : records) {
            rIdList.add(record.getRId());
        }
        List<Payment> payments = this.paymentService.queryPaymentByRid(rIdList);
        List<String> timeList = new ArrayList<>();
        for (Record record: records){
            long totalm = (record.getROuttime().getTime()-record.getRIntime().getTime());
            long m = 30*60*1000;
            double time = 1.0 * totalm / (m*2);
            DecimalFormat df = new DecimalFormat("0.0");
            String result = df.format(time);
            timeList.add(result);
        }

        List<ExcelPayment> excelPayments = new ArrayList<>();
        for (int i = 0; i < records.size(); i++) {
            ExcelPayment excelPayment = new ExcelPayment();
            excelPayment.setCarnum(records.get(i).getRCarnum());
            excelPayment.setTime(timeList.get(i));
            excelPayment.setPayTime(payments.get(i).getPTime());
            excelPayment.setMethod(payments.get(i).getPMethod());
            excelPayment.setSum(payments.get(i).getPSum());

            excelPayments.add(excelPayment);
        }

        ExcelWriter writer = EasyExcel.write(response.getOutputStream(), ExcelPayment.class).build();
        WriteSheet writeSheet = EasyExcel.writerSheet("收费记录").build();
        writer.write(excelPayments, writeSheet);
        writer.finish();
    }
}
