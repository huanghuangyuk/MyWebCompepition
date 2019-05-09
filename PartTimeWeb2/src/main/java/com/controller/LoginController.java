package com.controller;

import com.entity.User;
import com.service.UserService;
import com.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    @RequestMapping(value="login",method = RequestMethod.POST)
        public void uselogin(String username, String password, HttpServletResponse response, HttpServletRequest request){
        User user=new User();
        user.setPhonenumber(username);
        user.setPassword(password);
        UserService us=new UserServiceImpl();
        HttpSession session=request.getSession();
        if(us.getUser(user)==false){
            session.setAttribute("error_msg","用户名或密码错误");
        }
        session.setAttribute("msg","登陆成功");
       session.setAttribute("username",username);
        session.setAttribute("password",password);
    }
//    public void addCookie( User user, HttpServletResponse response){
//        Cookie cookie=new Cookie(user.getPhonenumber(),user.getPassword());
//        cookie.setMaxAge(60*60*24*7);
//        cookie.setPath("/");
//        response.addCookie(cookie);
//    }
//    public void deleteCookie(User user,HttpServletRequest request,HttpServletResponse response){
//        Cookie[] cookies=request.getCookies();
//        for(Cookie cookie:cookies){
//            if(cookie.getName().equals(user.getPhonenumber())){
//                cookie.setMaxAge(0);
//                cookie.setPath("/");
//                response.addCookie(cookie);
//            }
//        }
//    }
}
