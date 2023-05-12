package jmu.controller;

import jmu.service.AdminService;
import jmu.vo.Admin;
import jmu.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/listadmin",method = RequestMethod.GET)
    public Map<String,Object> listArea(){
        Map<String,Object> modelMap = new HashMap<String,Object>();
        List<Admin> list = adminService.queryAdmin();
        System.out.println(list);
        modelMap.put("adminList",list);
        return modelMap;
    }

    @RequestMapping("/selectall")
    public String selectAll(Model model){
        List<Admin> list = adminService.queryAdmin();
        model.addAttribute("adminList",list);
        model.addAttribute("title","管理员列表");
        return "queryAdmin";

    }

    //跳转到登录页面
    @RequestMapping("/goLogin")
    private String goLogin(){

        return "adminLogin";
    }

    @RequestMapping("index.html")
    private String goIndex(){
        return "index";
    }

    //登录
    @RequestMapping("/login")
    private String adminLogin(HttpServletRequest request, @RequestParam("adId") String adId, @RequestParam("adPwd") String adPwd, Model message){
        if (StringUtils.isEmpty(adId) || StringUtils.isEmpty(adPwd)) {
            message.addAttribute("msg","用户名或密码不能为空");
            return "adminLogin";
        }
        HashMap<String,String> hashMap = new HashMap<String,String>();
        hashMap.put("adId", adId);
        hashMap.put("adPwd", adPwd);
        System.out.println(hashMap);
        Admin admin = this.adminService.adminLogin(hashMap);
        if (admin != null)
        {
            System.out.println(admin);
            request.getSession().setAttribute("Admin",admin);
            return "redirect:/admin/goChart";
        }
        else {
            message.addAttribute("msg","用户名或密码错误");
            return "adminLogin";
        }
    }

    //退出登录
    @RequestMapping("/logout")
    private String logout(HttpServletRequest request){
        request.getSession().removeAttribute("Admin");
        request.getSession().removeAttribute("msg");
        return "redirect:/admin/goLogin";
    }

    @RequestMapping("/goChart")
    private String goChart(){
        return "chart";
    }


}
