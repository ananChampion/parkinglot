package jmu.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jmu.service.PlateRecognitionService;
import jmu.service.RecordService;
import jmu.vo.*;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/record")
public class RecordController {
    @Autowired
    private RecordService recordService;

    @Autowired
    private PlateRecognitionService plateRecognitionService;

    //根据登录类型查询停车记录
    @RequestMapping("/selectRecord/index")
    private String showRecord(HttpServletRequest request, RedirectAttributes attributes){
        selectPayment(request, attributes);
        return "redirect:/pay/select/index";
    }

    @RequestMapping("/selectRecord/payment")
    private String showRecord2(HttpServletRequest request, RedirectAttributes attributes){
        selectPayment(request, attributes);
        return "redirect:/pay/select/payment";
    }

    private void selectPayment(HttpServletRequest request, RedirectAttributes attributes) {
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
            //查找支付记录
        }
        attributes.addFlashAttribute("records", records);
    }

    //通过Rid删除记录
    @RequestMapping("/deleteByRId")
    private String deleteByRId(@ModelAttribute("rId") Integer rId){
        Integer num = this.recordService.deleteByRId(rId);
        if (num == 1)
        {
            return "redirect:/record/selectRecord/index";
        }
        else {
            System.out.println("删除失败");//提示
            return "redirect:/record/selectRecord/index";
        }
    }

    //插入停车记录
    @RequestMapping(value = "/insertRecord")
    public String insertRecord(@ModelAttribute("carnum") String carnum, @ModelAttribute("flag") Integer flag, Model model, RedirectAttributes attributes){
    System.out.println(carnum+flag);
        //先判断该车辆是否有入场记录，如果没有，则入场插入TRecord；如果有，则出场，插入Record，并创建支付订单（职员均为0，外来用户需要计算）
        TempRecord tempRecord = this.recordService.selectTRByCarnum(carnum);
        Date date = new Date();
        if (tempRecord == null){
            tempRecord = new TempRecord();
            tempRecord.setTCarnum(carnum);
            tempRecord.setTIntime(date);
            tempRecord.setTSend(false);
            this.recordService.insertTRecord(tempRecord);
            model.addAttribute("carnum", null);
            model.addAttribute("msg", "停车成功");
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
            int pSum = 0;
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
            DecimalFormat df = new DecimalFormat("0.0");
            String result = df.format(time);
            attributes.addFlashAttribute("time", result);
            attributes.addFlashAttribute("record", record);
            attributes.addFlashAttribute("pSum", pSum);
            return "redirect:/pay/insert";
        }
    }

    //通过车牌查找停车记录
    @RequestMapping("/selectByCarnum/{carnum}")
    private String selectByCarnum(@PathVariable("carnum") String carnum, RedirectAttributes attributes){
        List<Record> records = this.recordService.selectByCarnum(carnum);
        attributes.addFlashAttribute("records",records);
        return "redirect:/pay/selectByUser";
    }

    @RequestMapping("/queryTRecord")
    private String queryTRecord(Model model){
        List<TempRecord> tempRecords = this.recordService.queryTempRecord();
        model.addAttribute("tempRecords", tempRecords);
        return "tempRecord";
    }

    @RequestMapping("/api")
    private String api(){
    System.out.println(this.plateRecognitionService.testAPI());
        return null;
    }

    @RequestMapping(value = "/recordCount", produces = "application/json; charset=utf-8")
    @ResponseBody
    private List<DayRecord> recordCount(){
        List<DayRecord> dayRecords = this.recordService.selectRecordNumByDay();
        return dayRecords;
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    private void export(HttpServletResponse response) throws IOException{
        // 设置响应头信息
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("停车记录.xlsx", StandardCharsets.UTF_8));

        List<Record> records = this.recordService.queryRecordById();

        ExcelWriter writer = EasyExcel.write(response.getOutputStream(), Record.class).build();
        WriteSheet writeSheet = EasyExcel.writerSheet("停车记录").build();
        writer.write(records, writeSheet);
        writer.finish();
    }

    @RequestMapping("/exportPayment")
    private String exportPayment(RedirectAttributes attributes){
        List<Record> records = this.recordService.queryRecordById();
        attributes.addFlashAttribute("records", records);

        return "redirect:/pay/export";
    }

    @RequestMapping("/searchRecord")
    private String search(String carnum, RedirectAttributes attributes){
        if (carnum.equals("")){
            return "redirect:/record/selectRecord/index";
        }
        else {
            List<Record> records = this.recordService.searchByCarnum(carnum);
            attributes.addFlashAttribute("records", records);
            return "redirect:/pay/searchRecord";
        }
    }

    @RequestMapping("/searchPayment")
    private String searchPayment(String carnum, RedirectAttributes attributes){
        if (carnum.equals("")){
            return "redirect:/record/selectRecord/payment";
        }
        else {
            List<Record> records = this.recordService.searchByCarnum(carnum);
            attributes.addFlashAttribute("records", records);
            return "redirect:/pay/searchPayment";
        }
    }
    //查找当前在库内车辆
    //TODO:时间排序
    //导出报表
    //图标显示数据
    //TODO：分页
}
