<%@ page language="java"   contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>韶院兼职-登录</title>
  <link rel="stylesheet" href="css\Login.css">
</head>
<body>
  <div class="main">
    <div class="info">
      <!-- <p>登录</p> -->
      <div class="logo"><img src="img/job.png" alt="" width="110px"></div>
      <form action="/login">
          <!-- <input type="text" class="phone-num" placeholder="Telephone Number"><br>
          <input type="password" class="password" placeholder="Password"> -->
          <!-- <input type="text" class="phone-num" placeholder="请输入手机号码" onfocus="hideErrorInfo('error-phone-num');" name="Telephone Number" id="Phone-num"><br> -->
          <!-- <div class="error error-phone-num"></div> -->
          <!-- <input type="password" class="password" placeholder="请输入密码"> -->
          <!-- <div class="error error-phone-num"></div> -->
          <!--手机号-->
       <ul class="phoneMumber-list wall-form-ipt-list active">
         <li>
           <input type="hidden" name="lt" value="">
           <input type="hidden" name="execution" value="e1s1">
           <input type="hidden" name="_eventId" value="submit">
           <i></i>
           <input type="text" placeholder="请输入手机号"  class="phone-num input-error" name="username" id="lUsername" value="" onfocus="hideErrorInfo('form-ipt-error-l-username');" >
           <!-- <span class="form-ipt-error is-visible" id="form-ipt-error-l-username">请输入手机号</span> -->
         </li>
         <li class="clearPassword-ico">
           <input type="password" placeholder="请输入密码" class="password input-error" name="password" id="lPassword" onfocus="hideErrorInfo('form-ipt-error-l-password');">
           <!-- <label class="form-ipt-error is-visible" id="form-ipt-error-l-password">请输入密码</label> -->
           <i class="pasword-icon"></i>
         </li>
       </ul>
       <!--手机号-->

          <div class="other">没有账号？
            <a href="sign.jsp">注册一个</a>
            <a href="#" class="wechat-png">
              <img src="img/wechat3.png" alt="" width="32px">
            </a>
          </div>
          <!-- <a href="#" class="log-a">Login</a> -->
          <a href="#" class="log-a">登录</a>
      </form>
    </div>
  </div>
</body>
</html>
