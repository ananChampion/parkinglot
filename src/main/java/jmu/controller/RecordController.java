package jmu.controller;

import jmu.service.PaymentService;
import jmu.service.RecordService;
import jmu.vo.Payment;
import jmu.vo.Record;
import jmu.vo.TempRecord;
import jmu.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/record")
public class RecordController {
    @Autowired
    private RecordService recordService;

    //根据登录类型查询停车记录
    @RequestMapping("/selectRecord")
    private String showRecord(HttpServletRequest request, RedirectAttributes attributes){
        HttpSession session = request.getSession();
        List<Record> records = new ArrayList<>();
        if (session.getAttribute("User") == null)
        {
            records = this.recordService.queryRecordById();
            System.out.println("找到了"+records);

        }
        else {
            User user = (User) session.getAttribute("User");
            records = this.recordService.selectByCarnum(user.getUCarnum());
            //TODO：查找支付记录

        }
        attributes.addFlashAttribute("records", records);
        return "redirect:/pay/select";
    }

    //通过Rid删除记录
    @RequestMapping("/deleteByRId")
    private String deleteByRId(HttpServletRequest request, @ModelAttribute("rId") Integer rId){
        Integer num = this.recordService.deleteByRId(rId);
        if (num == 1)
        {
            return "redirect:/record/selectRecord";
        }
        else {
            System.out.println("删除失败");//提示
            return "redirect:/record/selectRecord";
        }
    }

    //插入停车记录
    @RequestMapping(value = "/insertRecord")
    public String insertRecord(HttpServletRequest request, @ModelAttribute("carnum") String carnum, @ModelAttribute("flag") Integer flag, Model model){
    System.out.println(carnum+flag);
        //先判断该车辆是否有入场记录，如果没有，则入场插入TRecord；如果有，则出场，插入Record，并创建支付订单（职员均为0，外来用户需要计算）
        TempRecord tempRecord = this.recordService.selectTRByCarnum(carnum);
        Date date = new Date();
        if (tempRecord == null){
            tempRecord = new TempRecord();
            tempRecord.setTCarnum(carnum);
            tempRecord.setTIntime(date);
            this.recordService.insertTRecord(tempRecord);
            return "home";
        }
        else {
            Record record = new Record();
            record.setRCarnum(carnum);
            record.setRIntime(tempRecord.getTIntime());
            record.setROuttime(date);
            this.recordService.insertRecord(record);
            this.recordService.deleteTRByCarnum(carnum);
            record = this.recordService.queryRecordById().get(0);

            long totalm = (record.getROuttime().getTime()-record.getRIntime().getTime());
            long m = 30*60*1000;
            long count = 0;
            Integer pSum = 0;
            if (totalm%(30*60*1000) == 0){
                count = totalm / m;
            }
            else {
                count = totalm / m + 1;
            }

            if (flag == 1 || count <= 1) {
                pSum = 0;
            }
            else {
                if (count > 17){
                    pSum = 50;
                }else {
                    pSum = Math.toIntExact((count - 1) * 3);
                }
            }
            double time = 1.0 * totalm / (m*2);
            DecimalFormat df = new DecimalFormat("#.0");
            String result = df.format(time);
            model.addAttribute("time", result);
            model.addAttribute("record", record);
            model.addAttribute("pSum", pSum);
            return "pay";
            //跳转到支付页面，显示车牌号，停车时间，总时长，费用，选择支付方式。
            
        }


    }

    //通过车牌查找停车记录
    @RequestMapping("/selectByCarnum/{carnum}")
    private String selectByCarnum(@PathVariable("carnum") String carnum, Model model){
        List<Record> records = this.recordService.selectByCarnum(carnum);
        model.addAttribute("records",records);
        return "index";
    }

    @RequestMapping("/queryTRecord")
    private String queryTRecord(Model model){
        List<TempRecord> tempRecords = this.recordService.queryTempRecord();
        model.addAttribute("tempRecords", tempRecords);
        return "tempRecord";
    }

    //TODO:查找当前在库内车辆
    //TODO:时间排序
    //TODO:导出报表
    //TODO：图标显示数据
    //TODO：分页
}
