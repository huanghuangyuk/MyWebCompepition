<%@ page language="java"   contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>韶院兼职-登录</title>
  <link rel="stylesheet" href="css\Login.css">
  <script type="text/javascript" src="js/login.js">

  </script>
</head>
<body>
  <div class="main">
    <div class="info">
      <!-- <p>登录</p> -->
      <div class="logo"><img src="img/job.png" alt="" width="110px"></div>
      <div id="form-login">
        <form action="" onsubmit="return dosubmit()" method="post">
            <!-- <input type="text" class="phone-num" placeholder="Telephone Number"><br>
            <input type="password" class="password" placeholder="Password"> -->
            <input type="text" class="form-control" id="phone" placeholder="请输入手机号码" /><br>
            <p class="error error-phone-num" id="tip-1" style="display:none" >手机号输入错误</p>
            <input type="password" class="form-control" id="password" placeholder="请输入密码" />
            <p class="error error-pwd-num" id="tip-2" style="display:none" >密码错误</p>

            <div class="other">没有账号？
              <a href="sign.html">注册一个</a>
              <a href="#" class="wechat-png">
                <img src="img/wechat3.png" alt="" width="32px">
              </a>
            </div>
            <!-- <a href="#" class="log-a">Login</a> -->
            <a href="#" class="log-a">登录</a>
        </form>
      </div>
    </div>
  </div>
</body>
</html>

