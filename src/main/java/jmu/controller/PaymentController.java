package jmu.controller;

import jmu.service.PaymentService;
import jmu.vo.Payment;
import jmu.vo.Record;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;


@Controller
@RequestMapping("/pay")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @RequestMapping("/goPay")
    public String pay(){
        return "pay";
    }

    @RequestMapping("/insert")
    private String insert(String pMethod, Integer pSum, Integer rId){
        Payment payment = new Payment();
        payment.setPSum(pSum);
        payment.setRId(rId);
        payment.setPMethod(pMethod);
        payment.setPTime(new Date());
    System.out.println("过来了"+payment);
        this.paymentService.insert(payment);
        return "redirect:/user/home";
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

    @RequestMapping("/select")
    private String select(HttpServletRequest request, @ModelAttribute("records") List<Record> records, Model model){
        HttpSession session = request.getSession();
        List<Payment> payments = new ArrayList<>();
        Map<Record, Payment> recordMap = new HashMap<>();
        if (session.getAttribute("User") ==null )
        {
            payments = this.paymentService.queryPayment();
        }else {
            Payment payment = new Payment();
            List<Integer> rIdList = new ArrayList<>();
            for (Record record : records) {
                rIdList.add(record.getRId());
            }
            payments = this.paymentService.queryPaymentByRid(rIdList);


        }
        model.addAttribute("records", records);
        model.addAttribute("payments", payments);
        return "index";
    }
}
