$(document).ready(function(){

    var userMaxPage = 0;  //用户列表页数
	var userCurrentPage = 0;  //当前用户表页

    /* user.html done*/
	//用户注册提交
	$('form#formUserRegister').bind('submit', function(evt){
	    evt.preventDefault();
		if($(this).find('input#exampleInputEmail1').val() == '') {
		   alert('用户名不能为空！');
		   $(this)[0].reset();
		} else {
          if($(this).find('input#exampleInputPassword1').val() == 
		     $(this).find('input#exampleInputPassword2').val()) {
		    //向后台提交
			var formData = "username=" + $(this).find('input#exampleInputEmail1').val()
			               + "&password=" + $(this).find('input#exampleInputPassword1').val();
			$.post('../../aimin/user/login',formData,userRegisterProcess,'json');
		  }else {
		    alert('两次密码不一致！');
			$(this)[0].reset();
		  }
		}
	}); 
	//上下翻页操作
	$('button#checkPrevious').bind('click', function(){
    	if(userCurrentPage == 0) {
    		//当前是第一页
    	} else {
    		userCurrentPage --;
    		var dataSend = 'page=' + userCurrentPage;
    		$.get('../../aimin/user/query',dataSend,userManageProcess);
    	}
	});
	$('button#checkNext').bind('click', function(){
	  	if(userCurrentPage == userMaxPage) {
    		
    	} else {
    		userCurrentPage ++;
    		var dataSend = 'page=' + userCurrentPage;
    		$.get('../../aimin/user/query',dataSend,userManageProcess);  		
    	}
	});
	//删除、更改密码操作
	$('table#userManageTable td.userEdit input:first-child').bind('click', function(evt){
		var con = window.confirm('确定删除该用户？');
	    if(con) {
		    var dataSend = 'username=' + $(this).parent().prev().text();
			$.get('../../aimin/user/delete', dataSend, userDeleteProcess);
	    }
	});
	$('table#userManageTable td.userEdit input:last-child').bind('click', function(evt){
	    var newPassword = window.prompt("请输入新密码","");
		//发送新密码
	    var dataSend = 'username=' + $(this).parent().prev().text()
			         + '&password=' + newPassword;
		$.get('../../aimin/user/edit', dataSend, userNewPasswordProcess);
	});

	//用户注册提交回调函数
	function userRegisterProcess(data){
		var alertMsg = "";
		if(data.errorCode == 0)
		  alertMsg += "注册成功！";
		else if(data.errorCode == 1)
		  alertMsg += "注册失败！";
	    else if(data.errorCode == 2)
		  alertMsg += "后台异常！";
		  
		alertMsg += data.errorMsg;
		
		//重置表单
		$('form#formUserRegister')[0].reset();
		alert(alertMsg);
		//刷新用户页面
		$.get('../../aimin/user/query',"page=0",userManageProcess);
	}
	//用户删除回调函数
	function userDeleteProcess(data) {
	    if(data.errorCode == 0) {
	    	alert('删除成功！');
	        var dataSend = 'page=' + userCurrentPage;
    		$.get('../../aimin/user/query',dataSend,userManageProcess);  //更新用户列表
		} else {
			alert('删除操作异常！');
		}
	}
	//用户新密码回调函数
	function userNewPasswordProcess(data){
	    if(data.errorCode == 0) {
			alert('密码修改成功！');
		} else {
			alert('密码修改失败！');
		}
	}
	//用户管理页面查询翻页回调函数
	function userManageProcess(data) {
	    //清零
		var trs = document.getElementById('userManageTable').children[1].querySelectorAll('tr');
		for(var i=0; i<5; i++) {
			trs[i].children[0].innerHTML = '';
			trs[i].children[1].querySelectorAll('input')[0].style.display = "none";
			trs[i].children[1].querySelectorAll('input')[1].style.display = "none";
		}
		//显示
		if(data.errorCode == 0) {
			var len = data.errorMsg;
			//最大页数
			userMaxPage = Math.floor(data.errorMsg/5);
			if((data.errorMsg%5) == 0)
				userMaxPage -= 1;
			var disLen = (data.param.length < 5) ? data.param.length : 5;
			for(var j=0; j<disLen; j++) {
				trs[j].children[0].innerHTML = data.param[j].username;
			    trs[j].children[1].querySelectorAll('input')[0].style.display = "";
			    trs[j].children[1].querySelectorAll('input')[1].style.display = "";
			}
		}
	}
	
	//用户管理操作
	$('a[href="password"]').bind('click',function(evt){
	    evt.preventDefault();
		var passwordNew = window.prompt("输入新密码");
		//修改密码
	});
	$('a[href="logout"]').bind('click',function(evt){
	    evt.preventDefault();
	    var conf = window.confirm("是否退出登录？");
		if(conf) {
		    $.get('../../aimin/user/logout');
			window.location.href = 'login.html';
		} else {
		}
	});
	
	//初始查询
	$.get('../../aimin/user/query',"page=0",userManageProcess);
	
});