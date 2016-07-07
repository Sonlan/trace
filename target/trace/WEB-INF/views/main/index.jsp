<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>空调滤芯管理系统</title>

    <!-- Bootstrap -->
    <link href="${ctx}/static/bootstrap-3.3.5-dist/css/bootstrap.min.css" rel="stylesheet">
	<link href="${ctx}/static/user/css/style.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <body>
    <div class="container-fluid bg-primary m-padding m-bottom-margin">
	  <div class="row">
	    <div class="col-md-2">
		  <p class="big-font">空调滤芯管理系统</p>
		</div>
		<div class="col-md-3 right-align">
		<!-- 用户管理导航 -->
		  <div>
			  <a href="logout" class="white-font s-right-margin"><span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>退出登录</a>
		  </div>
		</div>
	  </div>
	</div>
	
	<div class="container">
	  <ul class="nav nav-tabs nav-justified">
		<li role="presentation" id="navIndex" class="active"><a href="${ctx}/main/index">状态查询</a></li>
		<li role="presentation" id="navValid"><a href="${ctx}/main/valid">批量激活</a></li>
		<li role="presentation" id="navInvalid"><a href="${ctx}/main/invalid">手动报废</a></li>
		<li role="presentation" id="navUser"><a href="${ctx}/main/user">用户管理</a></li>
	  </ul>
	</div>
	
	<div class="container-fluid">
	  <div class="row">
	    <div class="col-md-2 col-md-offset-1 bg-info m-padding l-top-margin">
		    <p class="big-font">计时功能:</p>
			<div class="btn-group" role="group">
			  <button type="button" class="btn btn-default" id="stopTiming">停止计时</button>
			  <button type="button" class="btn btn-default" id="startTiming">开始计时</button>
			</div>
			<p id="timingStatus">当前状态：</p>
			
		  	<p class="big-font">查询条件:</p>
		<div id="inquiryStatus">
			<div class="s-bottom-margin"><!-- 是否报废 -->
				<label class="radio-inline">
				  <input type="radio" name="in-valid" value="1" checked>未报废</input>
				</label>
				<label class="radio-inline">
				  <input type="radio" name="in-valid" value="0">已报废</input>
				</label>
			</div>
			
			<div class="s-bottom-margin"><!-- 是否正在使用 -->
				<label class="radio-inline">
				  <input type="radio" name="in-use" value="1" checked> 正在使用
				</label>
				<label class="radio-inline">
				  <input type="radio" name="in-use" value="0"> 未使用
				</label>
			</div>
			
			<div class="s-bottom-margin"><!-- 过滤器类型 -->
				<label class="radio-inline">
				  <input type="radio" name="filter-class" value="0" checked> 初级滤芯
				</label>
				<label class="radio-inline">
				  <input type="radio" name="filter-class" value="1"> 中级滤芯
				</label>
			</div>
			
			<div class="s-bottom-margin"><!-- 空调选择 -->
              <p class="inline-display">空调号选择：</p>
			  <select class="form-control inline-display width-middle" id="selectAIRId">
			    <option value='01'>1</option>
			    <option value='02'>2</option>
			    <option value='03'>3</option>
			  </select>
			</div>
			
			<div class="s-bottom-margin"><!-- 剩余清洗次数 -->
			  <p class="inline-display">剩余清洗次数：</p>
			  <select class="form-control inline-display width-middle" id="selectWashRemain">
			    <option value=0>--所有--</option>
			    <option value=1>1</option>
				<option value=2>2</option>
				<option value=3>3</option>
				<option value=4>4</option>
				<option value=5>5</option>
			  </select>
			</div>
			
			<div class="s-bottom-margin"><!-- 剩余使用时间 -->
			  <p class="inline-display">剩余使用时间：</p>
			  <select class="form-control inline-display width-middle" id="selectRemainDay">
			    <option value=0>0</option>
			    <option value=1 selected>1</option>
				<option value=2>2</option>
				<option value=3>3</option>
				<option value=4>4</option>
				<option value=5>5</option>
			  </select><p class="inline-display">天</p></br>
			  <p class="inline-display">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
			  <select class="form-control inline-display width-middle" id="selectRemainHour">
			    <option value=0>0</option>
			    <option value=1>1</option>
				<option value=2>2</option>
				<option value=3>3</option>
				<option value=4>4</option>
				<option value=5>5</option>
			    <option value=6>6</option>
			    <option value=7>7</option>
				<option value=8>8</option>
				<option value=9>9</option>
				<option value=10>10</option>
				<option value=11>11</option>
			    <option value=12>12</option>
			    <option value=13>13</option>
				<option value=14>14</option>
				<option value=15>15</option>
				<option value=16>16</option>
				<option value=17>17</option>
			    <option value=18>18</option>
			    <option value=19>19</option>
				<option value=20>20</option>
				<option value=21>21</option>
				<option value=22>22</option>
				<option value=23>23</option>
			  </select><p class="inline-display">时</p></br>
			  <p class="inline-display">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</p>
			  <select class="form-control inline-display width-middle" id="selectRemainMin">
			    <option value=0>0</option>
			    <option value=1>1</option>
				<option value=2>2</option>
				<option value=3>3</option>
				<option value=4>4</option>
				<option value=5>5</option>
			    <option value=6>6</option>
			    <option value=7>7</option>
				<option value=8>8</option>
				<option value=9>9</option>
				<option value=10>10</option>
				<option value=11>11</option>
			    <option value=12>12</option>
			    <option value=13>13</option>
				<option value=14>14</option>
				<option value=15>15</option>
				<option value=16>16</option>
				<option value=17>17</option>
			    <option value=18>18</option>
			    <option value=19>19</option>
				<option value=20>20</option>
				<option value=21>21</option>
				<option value=22>22</option>
				<option value=23>23</option>
				<option value=24>24</option>
				<option value=25>25</option>
			    <option value=26>26</option>
			    <option value=27>27</option>
				<option value=28>28</option>
				<option value=29>29</option>
				<option value=30>30</option>
				<option value=31>31</option>
				<option value=32>32</option>
				<option value=33>33</option>
				<option value=34>34</option>
				<option value=35>35</option>
				<option value=36>36</option>
				<option value=37>37</option>
			    <option value=38>38</option>
			    <option value=39>39</option>
				<option value=40>40</option>
				<option value=41>41</option>
				<option value=42>42</option>
				<option value=43>43</option>
				<option value=44>44</option>
			    <option value=45>45</option>
			    <option value=46>46</option>
			    <option value=47>47</option>
				<option value=48>48</option>
				<option value=49>49</option>
				<option value=50>50</option>
				<option value=51>51</option>
				<option value=52>52</option>
			    <option value=53>53</option>
				<option value=54>54</option>
				<option value=55>55</option>
				<option value=56>56</option>
				<option value=57>57</option>
				<option value=58>58</option>
				<option value=59>59</option>
			  </select><p class="inline-display">分</p>
			</div>
			
			<button class="btn btn-default" type="submit">提交查询</button>
		</div>
	  </div><!-- col-md-2 -->
	  <div class="col-md-8 l-top-margin">
	    <table class="table table-hover table-striped table-bordered" id="mainTable">
		  <tr>
			  <th>滤芯号</th>
			  <th>滤芯等级</th>
			  <th>空调号</th>
			  <th>清洗次数</th>
			  <th>剩余时间(天:时:分)</th>
			  <th>是否使用</th>
			  <th>是否报废</th>
			  <th>报废时间</th>
			  <th>编辑</th>
			  <th>删除</th>
		  </tr>
		  <tr>
		      <td></td>
			  <td><select class='form-control inline-display width-middle select_filter'>
			        <option value=0 selected>初级</option>
		            <option value=1>中级</option>
		          </select></td>
			  <td><input type="text" class="input_air width-small"></td>
			  <td><input type="text" class="input_wash width-small"></td>
			  <td><input type="text" class="input_time width-small"></td>
			  <td><select class='form-control inline-display width-middle select_inuse'>
			        <option value=1 selected>正使用</option>
		            <option value=0>未使用</option>
		          </select></td>
			  <td class="td_invalid"><select class='form-control inline-display width-middle select_invalid'> 
		            <option value=0 selected>已报废</option>
		            <option value=1>未报废</option>
		          </select></td>
		      <td class='center-text'></td>
		      <td><button class="buttonEdit">修改</button><button class="buttonDone">确定</button></td>
		      <td><button class="buttonDelete">删除</button></td>
		  </tr>
		  <tr>
		      <td></td>
			  <td><select class='form-control inline-display width-middle select_filter'>
			        <option value=0 selected>初级</option>
		            <option value=1>中级</option>
		          </select></td>
			  <td><input type="text" class="input_air width-small"></td>
			  <td><input type="text" class="input_wash width-small"></td>
			  <td><input type="text" class="input_time width-small"></td>
			  <td><select class='form-control inline-display width-middle select_inuse'>
			        <option value=1 selected>正使用</option>
		            <option value=0>未使用</option>
		          </select></td>
			  <td class="td_invalid"><select class='form-control inline-display width-middle select_invalid'> 
		            <option value=0 selected>已报废</option>
		            <option value=1>未报废</option>
		          </select></td>
		      <td class='center-text'></td>
		      <td><button class="buttonEdit">修改</button><button class="buttonDone">确定</button></td>
		      <td><button class="buttonDelete">删除</button></td>
		  </tr>
		  <tr>
		      <td></td>
			  <td><select class='form-control inline-display width-middle select_filter'>
			        <option value=0 selected>初级</option>
		            <option value=1>中级</option>
		          </select></td>
			  <td><input type="text" class="input_air width-small"></td>
			  <td><input type="text" class="input_wash width-small"></td>
			  <td><input type="text" class="input_time width-small"></td>
			  <td><select class='form-control inline-display width-middle select_inuse'>
			        <option value=1 selected>正使用</option>
		            <option value=0>未使用</option>
		          </select></td>
			  <td class="td_invalid"><select class='form-control inline-display width-middle select_invalid'> 
		            <option value=0 selected>已报废</option>
		            <option value=1>未报废</option>
		          </select></td>
		      <td class='center-text'></td>
		      <td><button class="buttonEdit">修改</button><button class="buttonDone">确定</button></td>
		      <td><button class="buttonDelete">删除</button></td>
		  </tr>
		  <tr>
		      <td></td>
			  <td><select class='form-control inline-display width-middle select_filter'>
			        <option value=0 selected>初级</option>
		            <option value=1>中级</option>
		          </select></td>
			  <td><input type="text" class="input_air width-small"></td>
			  <td><input type="text" class="input_wash width-small"></td>
			  <td><input type="text" class="input_time width-small"></td>
			  <td><select class='form-control inline-display width-middle select_inuse'>
			        <option value=1 selected>正使用</option>
		            <option value=0>未使用</option>
		          </select></td>
			  <td class="td_invalid"><select class='form-control inline-display width-middle select_invalid'> 
		            <option value=0 selected>已报废</option>
		            <option value=1>未报废</option>
		          </select></td>
		      <td class='center-text'></td>
		      <td><button class="buttonEdit">修改</button><button class="buttonDone">确定</button></td>
		      <td><button class="buttonDelete">删除</button></td>
		  </tr>
		  <tr>
		      <td></td>
			  <td><select class='form-control inline-display width-middle select_filter'>
			        <option value=0 selected>初级</option>
		            <option value=1>中级</option>
		          </select></td>
			  <td><input type="text" class="input_air width-small"></td>
			  <td><input type="text" class="input_wash width-small"></td>
			  <td><input type="text" class="input_time width-small"></td>
			  <td><select class='form-control inline-display width-middle select_inuse'>
			        <option value=1 selected>正使用</option>
		            <option value=0>未使用</option>
		          </select></td>
			  <td class="td_invalid"><select class='form-control inline-display width-middle select_invalid'> 
		            <option value=0 selected>已报废</option>
		            <option value=1>未报废</option>
		          </select></td>
		      <td class='center-text'></td>
		      <td><button class="buttonEdit">修改</button><button class="buttonDone">确定</button></td>
		      <td><button class="buttonDelete">删除</button></td>
		  </tr>
		  <tr>
		      <td></td>
			  <td><select class='form-control inline-display width-middle select_filter'>
			        <option value=0 selected>初级</option>
		            <option value=1>中级</option>
		          </select></td>
			  <td><input type="text" class="input_air width-small"></td>
			  <td><input type="text" class="input_wash width-small"></td>
			  <td><input type="text" class="input_time width-small"></td>
			  <td><select class='form-control inline-display width-middle select_inuse'>
			        <option value=1 selected>正使用</option>
		            <option value=0>未使用</option>
		          </select></td>
			  <td class="td_invalid"><select class='form-control inline-display width-middle select_invalid'> 
		            <option value=0 selected>已报废</option>
		            <option value=1>未报废</option>
		          </select></td>
		      <td class='center-text'></td>
		      <td><button class="buttonEdit">修改</button><button class="buttonDone">确定</button></td>
		      <td><button class="buttonDelete">删除</button></td>
		  </tr>
		  <tr>
		      <td></td>
			  <td><select class='form-control inline-display width-middle select_filter'>
			        <option value=0 selected>初级</option>
		            <option value=1>中级</option>
		          </select></td>
			  <td><input type="text" class="input_air width-small"></td>
			  <td><input type="text" class="input_wash width-small"></td>
			  <td><input type="text" class="input_time width-small"></td>
			  <td><select class='form-control inline-display width-middle select_inuse'>
			        <option value=1 selected>正使用</option>
		            <option value=0>未使用</option>
		          </select></td>
			  <td class="td_invalid"><select class='form-control inline-display width-middle select_invalid'> 
		            <option value=0 selected>已报废</option>
		            <option value=1>未报废</option>
		          </select></td>
		      <td class='center-text'></td>
		      <td><button class="buttonEdit">修改</button><button class="buttonDone">确定</button></td>
		      <td><button class="buttonDelete">删除</button></td>
		  </tr>
		  <tr>
		      <td></td>
			  <td><select class='form-control inline-display width-middle select_filter'>
			        <option value=0 selected>初级</option>
		            <option value=1>中级</option>
		          </select></td>
			  <td><input type="text" class="input_air width-small"></td>
			  <td><input type="text" class="input_wash width-small"></td>
			  <td><input type="text" class="input_time width-small"></td>
			  <td><select class='form-control inline-display width-middle select_inuse'>
			        <option value=1 selected>正使用</option>
		            <option value=0>未使用</option>
		          </select></td>
			  <td class="td_invalid"><select class='form-control inline-display width-middle select_invalid'> 
		            <option value=0 selected>已报废</option>
		            <option value=1>未报废</option>
		          </select></td>
		      <td class='center-text'></td>
		      <td><button class="buttonEdit">修改</button><button class="buttonDone">确定</button></td>
		      <td><button class="buttonDelete">删除</button></td>
		  </tr>
		  <tr>
		      <td></td>
			  <td><select class='form-control inline-display width-middle select_filter'>
			        <option value=0 selected>初级</option>
		            <option value=1>中级</option>
		          </select></td>
			  <td><input type="text" class="input_air width-small"></td>
			  <td><input type="text" class="input_wash width-small"></td>
			  <td><input type="text" class="input_time width-small"></td>
			  <td><select class='form-control inline-display width-middle select_inuse'>
			        <option value=1 selected>正使用</option>
		            <option value=0>未使用</option>
		          </select></td>
			  <td><select class='form-control inline-display width-middle select_invalid'>
			        <option value=0 selected>已报废</option>
		            <option value=1>未报废</option>
		          </select></td>
		      <td class='center-text'></td>
		      <td><button class="buttonEdit">修改</button><button class="buttonDone">确定</button></td>
		      <td><button class="buttonDelete">删除</button></td>
		  </tr>
		</table>
		
		<nav>
		  <ul class="pager">
			<li><a href="previous">上一页</a></li>
			<li><a href="next">下一页</a></li>
		  </ul>
		</nav>
	  </div><!-- col-md-7 -->
	</div><!-- container -->
	
	<div id="footer" class="bg-primary">
      <p class="center-text s-top-margin">爱民制药:空调滤芯控制系统, by <a href="http://nacerc.hust.edu.cn/">国家防伪工程技术研究中心</a>.</p>
	</div>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="${ctx}/static/bootstrap-3.3.5-dist/jquery/jquery-1.11.1.js"></script>
	<!-- user jQuery -->
	<script src="${ctx}/static/user/js/index.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${ctx}/static/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
  </body>
</html>