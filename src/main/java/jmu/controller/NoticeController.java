package jmu.controller;

import jmu.service.NoticeService;
import jmu.vo.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/notice")
public class NoticeController{
    @Autowired
    private NoticeService noticeService;

    @RequestMapping("/home")
    private String goHome(Model model) {
        List<Notice> notices = this.noticeService.queryNotice();
        model.addAttribute("notice", notices.get(0));
        return "home";
    }

    @RequestMapping("/queryNotice")
    private String queryNotice(Model model){
        List<Notice> notices = this.noticeService.queryNotice();
        model.addAttribute("notices", notices);
        return "notice";
    }

    //删除
    @RequestMapping("/delete/{nId}")
    private String delete(@PathVariable("nId")Integer nId){
        Integer num = this.noticeService.deleteByNId(nId);
        if (num == 1)
        {
            return "redirect:/notice/queryNotice";
        }
        else{
            return "redirect:/notice/queryNotice";
        }
    }

    //更新
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    private String update(Integer nId, String nTitle, String nText){
        Notice notice = this.noticeService.selectByNId(nId);
        notice.setNTime(new Date());
        notice.setNTitle(nTitle);
        notice.setNText(nText+"（已修改）");
        Integer num = this.noticeService.updateNotice(notice);
        if (num == 1)
        {
            return "redirect:/notice/queryNotice";
        }
        else{
            return "redirect:/notice/queryNotice";
        }
    }

    //添加
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    private String insert(Notice notice){
        System.out.println(notice);
        notice.setNTime(new Date());
        Integer num = this.noticeService.insertNotice(notice);
        if (num == 1)
        {
            return "redirect:/notice/queryNotice";
        }
        else{
            return "redirect:/notice/queryNotice";
        }
    }
}
