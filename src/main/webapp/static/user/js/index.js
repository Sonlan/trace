$(document).ready(function(){

    var 
	maxPage = 0,  //最大页数
	currentPage = 0,  //当前页数
	
	currentDay = 1;
    currentWashRemain = '';
	
	maxDay_prim = 0;
    maxHour_prim = 0;
    maxMin_prim = 0;
    maxWash_prim = 0;  //最大清洗次数
	maxDay_mid = 0;
    maxHour_mid = 0;
    maxMin_mid = 0;
    maxWash_mid = 0;  //最大清洗次数
    maxAirNum = 0;  //最大空调数
    refreshRate = 0;  //刷新周期 min
    tempCount = 0;
    
    //表单验证数据
    airIDTemp = 0;
    washRemainTemp = 0;
    usingTimeTemp = 0;
    
	JSONData = {                    //查询请求JSON
		aliveTime_day : 1,  //剩余时间
		aliveTime_hour : 0,  
		aliveTime_min : 0,
		level : 0,  //滤芯等级 0 初级
		washRemain : 0,  //剩余清洗次数
		inuse : 1,  //是否使用,正在使用
		alive : 1,  //是否报废,未报废
		ac_id : '01',  //空调号
		page : 0  //当前页面
	};
	
	//定时刷新
	function startrequest(){
		tempCount += 5;
		if(tempCount >= refreshRate*60) {
			tempCount = 0;
			defaultInquiry();
		}
	} 
	
	
	//剩余清洗次数刷新
	function refreshWashRemain(maxWash){
		$('select#selectWashRemain option:gt(0)').remove();
		for(var i = 0; i < maxWash + 1; i ++) {
			var opt = "<option value=" + (i+1) + ">" + (i+1) + "</option>";
			$('select#selectWashRemain').append(opt);
		}
		
		$('select#selectWashRemain option[value=' + JSONData.washRemain + ']').attr('selected','selected');
	}
	
	//空调号查询
	function refreshAirNum(maxAir) {
		$('select#selectAIRId option').remove();
		for(var i = 0; i < maxAir; i ++) {
			var opt = "<option value=" + (i+1) + ">" + (i+1) + "</option>";
			$('select#selectAIRId').append(opt);
		}
		
		$('select#selectAIRId option[value=' + JSONData.ac_id + ']').attr('selected','selected');
	}
	
	//清洗时间
	function refreshTime(day) {
		$('select#selectRemainDay option').remove();
		for(var i = 0; i <= day; i ++) {
			var opt = "<option value=" + i + ">" + i + "</option>";
			$('select#selectRemainDay').append(opt);
		}
		//choose value
		$('select#selectRemainDay option[value=' + JSONData.aliveTime_day + ']').attr('selected','selected');
	}
	
	//表单项删除回调函数
	function itemDeleteProcess(data){
		if(data.errorCode == 0) {
			alert('删除成功！');
			defaultInquiry();
		} else {
			alert(data.errorMsg);
			defaultInquiry();
		}
	}
	
	//表单数据手动修改 回调函数
	function dataEditProcess(data){
		if(data.errorCode == 0) {
			alert('修改成功！');
			defaultInquiry();
		}else {
			alert(data.errorCode + data.errorMsg);
			defaultInquiry();
		}
	}
	
	//构造发送串
	function changeString(){
	    var dataSend = "";

		JSONData.alive = $('div#inquiryStatus input[name="in-valid"]:checked').attr('value');
		JSONData.inuse = $('div#inquiryStatus input[name="in-use"]:checked').attr('value');
		JSONData.ac_id = $('select#selectAIRId option:selected').attr('value');
		JSONData.level = $('div#inquiryStatus input[name="filter-class"]:checked').attr('value');
		JSONData.washRemain = $('select#selectWashRemain option:selected').attr('value');
		JSONData.aliveTime_day = $('select#selectRemainDay option:selected').attr('value');
		JSONData.aliveTime_hour = $('select#selectRemainHour option:selected').attr('value');
		JSONData.aliveTime_min = $('select#selectRemainMin option:selected').attr('value');
		
		dataSend = 'aliveTime_day=' + JSONData.aliveTime_day
			 + '&aliveTime_hour=' + JSONData.aliveTime_hour
			 + '&aliveTime_min=' + JSONData.aliveTime_min
			 + '&washRemain=' + ((JSONData.washRemain == 0) ? '' : JSONData.washRemain)
			 + '&level=' + JSONData.level
			 + '&inuse=' + JSONData.inuse
			 + '&alive=' + JSONData.alive
			 + '&ac_id=' + JSONData.ac_id
			 + '&page=' + JSONData.page;
			 
		return dataSend;
	}
	
	//查询回调函数
	function indexInquiryProcess(data){
		//清零
		var table = document.getElementById('mainTable');
		for(var i=0; i<9; i++) {
            table.children[0].children[i+1].children[0].innerHTML = ''; 
            table.children[0].children[i+1].children[7].innerHTML = ''; 
		}
	    $('input.input_air').val('').hide();
	    $('input.input_wash').val('').hide();
	    $('input.input_time').val('').hide();
	    $('select.select_filter').hide();
	    $('select.select_inuse').hide();
	    $('select.select_invalid').hide();
	    $('button.buttonEdit').hide();
	    $('button.buttonDone').hide();
	    $('button.buttonDelete').hide();
	    //清零
	    
		/* 初始化 */
		var msg = eval ("(" + data.errorMsg + ")");
		
		//获取剩余时间信息
		var timeMsg_prim = msg.limitTime[0].split('-');
		maxDay_prim = timeMsg_prim[0];
		maxHour_prim = timeMsg_prim[1];
		maxMin_prim = timeMsg_prim[2];
		var timeMsg_mid = msg.limitTime[1].split('-');
		maxDay_mid = timeMsg_mid[0];
		maxHour_mid = timeMsg_mid[1];
		maxMin_mid = timeMsg_mid[2];
		
		//获取清洗次数信息
		maxWash_prim = msg.washCountLimit[0];
		maxWash_mid = msg.washCountLimit[1];
		
		//获取空调数信息
		maxAirNum = msg.ac_ids.length;
		
		//获取刷新周期信息
		refreshRate = msg.rate;
		
	    /* 更新下拉菜单 */
	    // 剩余清洗次数 及 剩余使用时间下拉菜单
	    //此时初级滤芯被选中
	    if($('input[name="filter-class"]:checked').attr('value') == 0) {
	    	refreshWashRemain(maxWash_prim);
	    	refreshTime(maxDay_prim);
	    //此时中级滤芯被选中
	    }else if($('input[name="filter-class"]:checked').attr('value') == 1) {
	    	refreshWashRemain(maxWash_mid);
	    	refreshTime(maxDay_mid);
	    }
	    // 空调号查询下拉菜单
	    refreshAirNum(maxAirNum);/* 更新下拉菜单 */
	    
//初始化完成

		if(data.param != null) { //操作成功显示查询信息
		    //显示页码信息
			//alert(data.errorMsg);
			
			//data.errorMsg转化为JSON对象
			
			//计算最大页数信息
		    maxPage = Math.floor(msg.pageSize / 9);
		    if((data.errorMsg % 9) == 0)
		    	maxPage -= 1;

		    /* 填充mainTable */
			var itemNum = data.param.length;  //本次返回条目数
			for(var k=0; k<itemNum; k++) {
			  $('.select_filter:eq(' + k + ')').show().attr('disabled', 'disabled');
			  $('.select_invalid:eq(' + k + ')').show().attr('disabled', 'disabled');
			  $('.select_inuse:eq(' + k + ')').show().attr('disabled', 'disabled');
			  $('button.buttonEdit:eq(' + k + ')').show();
			  $('button.buttonDone:eq(' + k + ')').show();
			  $('button.buttonDelete:eq(' + k + ')').show();
			  //滤芯id
			  table.children[0].children[k+1].children[0].innerHTML = data.param[k].id;  
			  //滤芯等级
			  (data.param[k].id.substring(2,3) == '0') ?
					                     ($('.select_filter:eq(' + k + ') option:eq(0)').attr('selected','selected')) : 
		                                 ($('.select_filter:eq(' + k + ') option:eq(1)').attr('selected','selected'));
			  //空调id显示                              
              $('input.input_air:eq(' + k + ')').val(data.param[k].id.toString().substring(0,2)).show().attr('disabled', 'disabled');
              //剩余清洗次数
              $('input.input_wash:eq(' + k + ')').val(data.param[k].washRemain).show().attr('disabled', 'disabled');
              //剩余使用时间
              $('input.input_time:eq(' + k + ')').val(data.param[k].aliveTime).show().attr('disabled', 'disabled');
              //$('input.input_time:eq(' + k + ')').val(data.param[k].aliveTime.split(':')[0] + '天' +
              //                                        data.param[k].aliveTime.split(':')[1] + '时' +
              //                                        data.param[k].aliveTime.split(':')[2] + '分').show().attr('disabled', 'disabled');
              //是否使用
			  (data.param[k].inuse == 1) ? 
					                     ($('.select_inuse:eq(' + k + ') option:eq(0)').attr('selected','selected')) : 
					                     ($('.select_inuse:eq(' + k + ') option:eq(1)').attr('selected','selected'));
			  //是否报废
			  (data.param[k].alive == 1) ? 
					                     ($('.select_invalid:eq(' + k + ') option:eq(1)').attr('selected','selected')) : 
					                     ($('.select_invalid:eq(' + k + ') option:eq(0)').attr('selected','selected'));
			  //报废时间
			  table.children[0].children[k+1].children[7].innerHTML = (data.param[k].scrap_time == undefined) ? '-' : data.param[k].scrap_time;  		                     
			}
		}else {
            alert('查询失败！');
		}
	}
	
	//表单编辑
	$('button.buttonEdit').bind('click', function(evt){
		var conf = window.confirm('数据将被修改，是否继续？');
		if(conf) {
			//失能其他编辑按钮
			$('button.buttonEdit').attr('disabled', 'disabled');
			$('button.buttonDone').attr('disabled', 'disabled');
			$(this).next('button.buttonDone').attr('disabled', false);
			
			//$(this).parent().parent().children('td:eq(1)').children('select').attr('disabled', false);
			//$(this).parent().parent().children('td:eq(2)').children('input').attr('disabled', false);
			$(this).parent().parent().children('td:eq(3)').children('input').attr('disabled', false);
			$(this).parent().parent().children('td:eq(4)').children('input').attr('disabled', false);
			$(this).parent().parent().children('td:eq(5)').children('select').attr('disabled', false);
			$(this).parent().parent().children('td:eq(6)').children('select').attr('disabled', false);
		}else {
			
		}
	});
	//表单编辑  确定按钮
	$('button.buttonDone').bind('click', function(evt){
		//失能信息编辑
		$(this).parent().parent().children('td:eq(1)').children('select').attr('disabled', 'disabled');
		$(this).parent().parent().children('td:eq(2)').children('input').attr('disabled', 'disabled');
		$(this).parent().parent().children('td:eq(3)').children('input').attr('disabled', 'disabled');
		$(this).parent().parent().children('td:eq(4)').children('input').attr('disabled', 'disabled');
		$(this).parent().parent().children('td:eq(5)').children('select').attr('disabled', 'disabled');
		$(this).parent().parent().children('td:eq(6)').children('select').attr('disabled', 'disabled');
		//使能buttonEdit以及buttonDone
		$('button.buttonEdit').attr('disabled', false);
		$('button.buttonDone').attr('disabled', false);		
		//向后台发送数据
		var dataSend = "";
		dataSend = 'id=' + $(this).parent().parent().children('td:eq(0)').html()
		         //+ '&filter=' + $(this).parent().parent().children('td:eq(1)').children('select').children('option:selected').attr('value')
		         //+ '&acId=' +  $(this).parent().parent().children('td:eq(2)').children('input').val()
		         + '&washRemain=' + $(this).parent().parent().children('td:eq(3)').children('input').val()
		         + '&aliveTime=' + $(this).parent().parent().children('td:eq(4)').children('input').val()
		         + '&inuse=' + $(this).parent().parent().children('td:eq(5)').children('select').children('option:selected').attr('value')
		         + '&alive=' + $(this).parent().parent().children('td:eq(6)').children('select').children('option:selected').attr('value');
		$.get('../../aimin/label/edit',dataSend,dataEditProcess,'json');
	});
	//编辑中，改变滤芯等级后检测剩余清洗次数是否合法
	$('table#mainTable td:nth-child(2) select').bind('change', function(evt){
		$(this).parent().parent().children('td:nth-child(4)').children('input').blur();
	});
	//表单验证，空调id
	$('table#mainTable td:nth-child(3) input').bind('focus', function(evt){
		airIDTemp = $(this).val();
	});
	$('table#mainTable td:nth-child(3) input').bind('blur', function(evt){
		var newAirID = $(this).val();
		var regExp = /[0-9]{2}/g;
		//2位数字 不大于maxAirNum
		if(regExp.test(newAirID) && (newAirID.length == 2) && (parseInt(newAirID) <= maxAirNum)){
		} else {
			alert('空调号输入不合法！');
			$(this).val(airIDTemp);
		}

	});
	//表单验证，剩余清洗次数
	$('table#mainTable td:nth-child(4) input').bind('focus', function(evt){
		washRemainTemp = $(this).val();
	});
	$('table#mainTable td:nth-child(4) input').bind('blur', function(evt){
		var newWashRemain = $(this).val();
		var regExp = /^[0-9]*$/g;
		var washRemain = 0;
		//初级滤芯
		if($(this).parent().parent().children('td:nth-child(2)').children('select').children('option:selected').attr('value') == 0) {
			washRemain = maxWash_prim;
		}else {
			washRemain = maxWash_mid;
		}
		if(regExp.test(newWashRemain) && (parseInt(newWashRemain) <= washRemain)){
		} else {
			alert('清洗次数输入不合法！最大清洗次数为' + washRemain);
			$(this).val(washRemainTemp);
		}
	});
	//表单验证，剩余时间
	$('table#mainTable td:nth-child(5) input').bind('focus', function(evt){
		usingTimeTemp = $(this).val();
	});
	$('table#mainTable td:nth-child(5) input').bind('blur', function(evt){
		var newUsingTime = $(this).val();
		var regExp = /\d{2}:\d{2}:\d{2}/g;
		//初级滤芯
		if($(this).parent().parent().children('td:nth-child(2)').children('select').children('option:selected').attr('value') == 0) {
			var dhms = newUsingTime.split(':');
			var maxdhms = parseInt(dhms[0] + dhms[1] + dhms[2]);
			var maxdhms_prim = parseInt(maxDay_prim + maxHour_prim + maxMin_prim);
			var hourMinFlag = (parseInt(dhms[1]) <= 23)&&(parseInt(dhms[2]) <= 59);
			if(hourMinFlag && regExp.test(newUsingTime) && (newUsingTime.length==8) && (maxdhms <= maxdhms_prim)) {
				//符合条件
			}else {
				alert('输入剩余时间不合法！格式为天:时:分，最大剩余时间：' 
						+ maxDay_prim + ':' + maxHour_prim + ':' + maxMin_prim);
				$(this).val(usingTimeTemp);
			}
		}else { //中级滤芯
			var dhms = newUsingTime.split(':');
			var maxdhms = parseInt(dhms[0] + dhms[1] + dhms[2]);
			var maxdhms_mid = parseInt(maxDay_mid + maxHour_mid + maxMin_mid);
			if(regExp.test(newUsingTime) && (newUsingTime.length==8) && (maxdhms <= maxdhms_mid)) {
				//符合条件
			}else {
				alert('输入剩余时间不合法！格式为天:时:分，最大剩余时间：' 
						+ maxDay_mid + ':' + maxHour_mid + ':' + maxMin_mid);
				$(this).val(usingTimeTemp);
			}
		}
	});	
	
	//计时查询
	function timingStopProcess(data){
		if(data.errorCode == 0) {
		    $('p#timingStatus').html("当前状态：停止计时");
		}else if(data.errorCode == 1) {
		}
	}
	function timingStartProcess(data){
		if(data.errorCode == 0) {
			$('p#timingStatus').html("当前状态：开始计时");
		}
	}
	function timingQueryProcess(data){
		if(data.errorCode == 0) {
			$('p#timingStatus').html("当前状态：开始计时");
		}else if(data.errorCode == 1) {
			$('p#timingStatus').html("当前状态：停止计时");
		}
	}
	
	/* 操作 */
	
	//表单项删除
	$('button.buttonDelete').bind('click', function(evt){
		var conf = window.confirm('确定删除？');
		if(conf) {
			var dataSend = 'id=' + $(this).parent().parent().children('td:eq(0)').html();
			$.get('../../aimin/label/delete',dataSend,itemDeleteProcess);
		}
	});
	
	//计时操作 
	$('button#stopTiming').bind('click',function(evt){
	    $.get('../../aimin/timing/stop','',timingStopProcess,'json');
	});
	$('button#startTiming').bind('click',function(evt){
	    $.get('../../aimin/timing/start','',timingStartProcess,'json');
	});
	
	//翻页操作
	$('li>a[href="previous"]').bind('click',function(evt){
	    evt.preventDefault();
		if(currentPage == 0) {
		} else {
		  //发送请求
			currentPage --;
			JSONData.page = currentPage;
			var dataSend = changeString();
			$.get('../../aimin/label/query',sendData,indexInquiryProcess);
		}
	});
	$('li>a[href="next"]').bind('click',function(evt){
	    evt.preventDefault();
		if(currentPage == maxPage) {
		} else {
		  //发送请求
			currentPage ++;
			JSONData.page = currentPage;
			var JSONSend = changeString(JSONData);
			sendQuery(JSONSend);
		}
	});
	
	//滤芯类型选择
	$('input[name="filter-class"]').bind('change',function(evt){
		if($(this).val() == 0) {//初级滤芯
			refreshTime(maxDay_prim);
			refreshWashRemain(maxWash_prim);
		}else if($(this).val() == 1) {//中级滤芯
			refreshTime(maxDay_mid);
			refreshWashRemain(maxWash_mid);
		}
	});

    //提交查询
    $('div#inquiryStatus button[type="submit"]').bind('click',function(evt){
	    evt.preventDefault();
		var sendData = changeString();	
	    $.get('../../aimin/label/query',sendData,indexInquiryProcess);
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
	
	//初始页面查询
	function defaultInquiry(){
	    var dataSend = changeString();
		$.get('../../aimin/label/query',dataSend,indexInquiryProcess);
		//查询计时功能
		$.get('../../aimin/timing/query','',timingQueryProcess,'json');
	}
	
	defaultInquiry();
	//定时器
	setInterval(startrequest,5000);  
	//空调动态查询
});