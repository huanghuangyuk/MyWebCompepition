window.onload=function(){
	loginText2();
}


// 登录验证
function loginText2() {
	// 获取注册表单的每个按钮
	var aInput = document.getElementById("form-login").getElementsByTagName("input");

	var phone =aInput[0];
	var pwd = aInput[1];

	var name_length = 0;

	var tip1 = document.getElementById("tip-1");
	var tip2 = document.getElementById("tip-2");

	// 在键盘按键被松开时发生
	phone.onkeyup = function() {

		name_length = getLength(this.value);

		// if (name_length == 0) {
		// 	count.style.visibility = "hidden";
		// }
	}
	phone.onblur = function() {
		var reg = /^1[3|4|5|7|8][0-9]{9}$/;
		//含有非法字符 不能为空  长度超过25个字符 长度少于6个字符
		if (!reg.test(this.value)) {
			tip1.style.display="block";
			return false;
		}
		else {
			tip1.style.display="none";
			return true;
		}
	}


	//密码框   在键盘按键被松开时发生
	pwd.onkeyup = function() {

		name_length = getLength(this.value);

		// if (name_length == 0) {
		// 	count.style.visibility = "hidden";
		// }
	}
	// 在对象失去焦点时发生
	pwd.onblur = function() {
		var m = findStr(pwd.value, pwd.value[0]);
		//不能全为数字的正则
		var re_n = /[^\d]/g;
		//不能全为英语的正则
		var re_w = /[^a-zA-Z]/g;

		if (this.value == "" || this.value.length < 6 || this.value.length > 16) {
			tip2.style.display="block";
			return false;
		}
		else {
			tip2.style.display="none";
			return true;
		}
	}
}



  // 获取字符的长度
  function getLength(str) {
  	return str.replace(/[^\x00-xff]/g, "xx").length;
  }

  // 索引每个字符
  function findStr(str, n) {
  	var tmp = 0;
  	for (var i = 0; i < str.length; i++) {
  		if (str.charAt(i) == n) {
  			tmp++;
  		}
  	}
  	return tmp;
  }

  // 验证表单是否正确
  function dosubmit() {
  	var aInput = document.getElementById("form-login").getElementsByTagName("input");
  	var phone =aInput[0];
  	var pwd = aInput[1];
  	var input1 = checkSelect(phone);
  	var input2 = checkSelect(pwd);

  	if (input1 && input2) {
  		//提交表单代码
  		return true;
  	} else {
  		alert("表单含有错误填写！");
  		return false;
  	}
  }
