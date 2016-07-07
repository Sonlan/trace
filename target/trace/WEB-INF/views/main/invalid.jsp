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
    <div class="container-fluid bg-primary m-padding m-bottom-margin"><!-- 页头 -->
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
	
	<div class="container"><!-- 导航 -->
	  <ul class="nav nav-tabs nav-justified">
		<li role="presentation" id="navIndex"><a href="index.html">状态查询</a></li>
		<li role="presentation" id="navValid"><a href="valid.html">批量激活</a></li>
		<li role="presentation" id="navInvalid" class="active"><a href="invalid.html">手动报废</a></li>
		<li role="presentation" id="navUser"><a href="user.html">用户管理</a></li>
	  </ul>
	</div>
	
	<div class="container m-top-margin">
	  <div class="input-group m-top-margin" id="invalidControl">
		<input type="text" class="form-control" placeholder="报废条码，以英文逗号分隔">
		<span class="input-group-btn">
		<button class="btn btn-default" type="button">提交</button>
		</span>
      </div><!-- /input-group -->
	</div>
	
	<div id="footer" class="bg-primary">
      <p class="center-text s-top-margin">爱民制药:空调滤芯控制系统, by <a href="http://nacerc.hust.edu.cn/">国家防伪工程技术研究中心</a>.</p>
	</div>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="${ctx}/static/bootstrap-3.3.5-dist/jquery/jquery-1.11.1.js"></script>
	<!-- user jQuery -->
	<script src="${ctx}/static/user/js/invalid.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${ctx}/static/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
  </body>
</html>