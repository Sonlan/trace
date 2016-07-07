$(document).ready(function(){

    //批量激活
	function validProcess(data){
	    $('div#validControl input').val("");
	    if(data.errorCode == 0) {
		    alert('激活成功！');
		} else {
		    alert(data.errorMsg);
		}
	}

    $('div#validControl button').bind('click',function(){
	    var dataSend = $('div#validControl input').val();
	    var idsFlag = 0;
	    ids = dataSend.split(',');
	    len = ids.length;
	    dataSend = 'data=[';
	    for(var i=0; i<len; i++) {
            if(ids[i].length != 13) {
            	idsFlag = 1;
            	break;
            }
	    }
	    dataSend += $('div#validControl input').val()
	              + ']'; 
	    
	    if(idsFlag == 1) {
	    	alert('输入格式有误！');
	    } else if(idsFlag == 0) {
	    	$.post('../../aimin/label/bind',dataSend,validProcess,"json");
	    }
		//$.post('../../aimin/label/bind',dataSend,validProcess,"json");
	});

    

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
});