package jmu.controller;

import jmu.service.PlateRecognitionService;
import jmu.service.UserService;
import jmu.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PlateRecognitionService plateRecognitionService;

    //跳转到登录页面
    @RequestMapping("/goLogin")
    private String goLogin(){ return "userLogin"; }

    //跳转到注册页面
    @RequestMapping("/goRegister")
    private String goRegister()
    {
        return "userRegister";
    }

    //跳转到主页面
    @RequestMapping("home")
    private String goHome() { return "home"; }

    //管理页面
    @RequestMapping("index.html")
    private String goIndex(){
        return "index";
    }

    //修改信息页面
    @RequestMapping("/goUpdate/{uId}")
    private String goUpdate(Model model, @PathVariable("uId") Integer uId){
        User user = this.userService.selectById(uId);
        model.addAttribute("User",user);
        return "userUpdate";
    }

    //测试
    @RequestMapping("/test")
    private String goTest() { return "chart"; }

    //登录
    @RequestMapping("/login")
    private String userLogin(HttpServletRequest request, @RequestParam("uAccount") String uAccount, @RequestParam("uPwd") String uPwd, Model message){
        if (StringUtils.isEmpty(uAccount) || StringUtils.isEmpty(uPwd)) {
            message.addAttribute("msg","用户名或密码不能为空");
            return "/userLogin";
        }
        HashMap<String,String> hashMap = new HashMap<String,String>();
        hashMap.put("uAccount", uAccount);
        hashMap.put("uPwd", uPwd);
        System.out.println(hashMap);
        User user = this.userService.userLogin(hashMap);
        if (user != null)
        {
            System.out.println(user);
            request.getSession().setAttribute("User",user);
            request.getSession().removeAttribute("msg");
            return "redirect:/record/selectRecord/index";
        }
        else {
            message.addAttribute("msg","用户名或密码错误");
            return "/userLogin";
        }

    }

    //注册
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    private String userRegister(HttpServletRequest request, User user, Model message){
        System.out.println(user);
        Integer num = this.userService.insertUser(user);
        System.out.println(user);
        if (num == 1)
        {
            request.getSession().setAttribute("User",user);
            return "redirect:/record/selectRecord/index";
            //TODO:跳转到selectRecord找到自己车的记录，并且找到消费记录（内部人员没有）
        }
        else {
            message.addAttribute("msg","注册失败请检查内容");
            return "userRegister";
        }
    }

    //更新
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    private String userUpdate(HttpServletRequest request, User user, Model message){
        HttpSession session = request.getSession();
        System.out.println(session.getAttribute("User"));
        System.out.println("更新"+user);
        //TODO:错误信息提示（登录、注册、修改）
        Integer num = this.userService.updateUser(user);
        if (num == 1) {
            return "redirect:/record/selectRecord/index";
        }
        else{
            return "redirect:/user/goUpdate";
        }
    }

    //所有用户信息
    @RequestMapping("/queryAll/{type}")
    private String queryAll(HttpServletRequest request, @PathVariable("type") Integer type, Model model){
        //根据type来判断查找User的类型，再返回
        List<User> users = this.userService.selectByType(type);
        String typeName = "";
        if (type == 1){
            typeName = "职工";
        }
        else if (type == 2)
        {
            typeName = "外来用户";
        }
        model.addAttribute("users",users);
        model.addAttribute("typeName",typeName);
        return "queryUser";
    }

    //删除
    @RequestMapping("/deleteById/{uId}")
    private String deleteById(@PathVariable("uId") Integer uId, Model message){
        Integer num = this.userService.deleteByUId(uId);
        if (num == 1) {
            message.addAttribute("msg","删除成功");
        }
        else {
            message.addAttribute("msg","删除失败");
        }

        return "redirect:/user/queryAll/2";

    }

    //退出登录
    @RequestMapping("/logout")
    private String logout(HttpServletRequest request){
        request.getSession().removeAttribute("User");
        return "redirect:/user/goLogin";
    }

    //查询用户类型
    @RequestMapping(value = "/userType")
    private String userType(@RequestParam("carnum") String carnum, RedirectAttributes attributes, Model model){
        if(carnum.equals("") || carnum.length() < 7 || carnum.length()>8)
        {
            model.addAttribute("errormsg", "请输入正确的车牌号");
            return "home";
        } else {
            System.out.println(carnum);
            User user = this.userService.selectByCarnum(carnum);
            if (user == null || user.getUType() == 2) {
            attributes.addFlashAttribute("flag", 2);
            } else {
            attributes.addFlashAttribute("flag", 1);
            }
            attributes.addFlashAttribute("carnum", carnum);
            return "redirect:/record/insertRecord";
        }
    }

    @RequestMapping(value = "/recognition", method = RequestMethod.POST)
    private String rec(HttpServletRequest request, @RequestParam("image") MultipartFile image, Model model){
        try {
            byte[] imageBytes = image.getBytes();
            String carnum = this.plateRecognitionService.recognizePlate(imageBytes);
            if (carnum.equals("未识别到车牌"))
            {
                model.addAttribute("errormsg", "未识别到车牌，请输入车牌号");
            } else {
                model.addAttribute("carnum", carnum);
            }
            return "home";
        } catch (IOException e) {
            throw new RuntimeException("上传失败", e);
        }
    }






    //TODO:防止回退键重复登录
    //TODO:重定向
    //TODO:防盗链
    //TODO:判断已经登陆
    //TODO:验证一下格式
    //TODO:退出登录
}
