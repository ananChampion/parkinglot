package jmu.controller;

import jmu.service.NoticeService;
import jmu.vo.Notice;
import jmu.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/notice")
public class NoticeController{
    @Autowired
    private NoticeService noticeService;

    @RequestMapping("/queryNotice")
    private String queryNotice(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        List<Notice> notices = new ArrayList<>();
        if (session.getAttribute("Admin") != null)
        {
            notices = this.noticeService.queryNotice();
        }
        else {
            User user = (User) session.getAttribute("User");
            notices = this.noticeService.selectByNUId(0);
            List<Notice> notices2 = this.noticeService.selectByNUId(user.getUId());
            if (notices2 != null){
                notices.addAll(notices2);
            }
        }
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
