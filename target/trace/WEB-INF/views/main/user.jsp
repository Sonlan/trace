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
		<li role="presentation" id="navInvalid"><a href="invalid.html">手动报废</a></li>
		<li role="presentation" id="navUser" class="active"><a href="user.html">用户管理</a></li>
	  </ul>
	</div>
	
	<div class="container m-top-margin">
	  <div class="row m-top-margin">
	    <div class="col-md-4">
		    <p class="big-font">用户注册</p>
			<form class="m-top-margin" id="formUserRegister">
			  <div class="form-group">
				<label for="exampleInputEmail1">用户名</label>
				<input type="text" class="form-control" id="exampleInputEmail1" placeholder="用户名">
			  </div>
			  <div class="form-group">
				<label for="exampleInputPassword1">密码</label>
				<input type="password" class="form-control" id="exampleInputPassword1" placeholder="密码">
			  </div>
			  <div class="form-group">
				<label for="exampleInputPassword2">确认密码</label>
				<input type="password" class="form-control" id="exampleInputPassword2" placeholder="确认密码">
			  </div>
			  <button type="submit" class="btn btn-default">提交</button>
			</form>
		</div>
		<div class="col-md-8">
		  <p class="big-font">用户管理</p>
		  <table class="table table-hover table-striped table-bordered" id="userManageTable">
		    <thead>
			  <tr>
			    <th>用户名</th>
				<th>操作</th>
			  </tr>
			</thead>
			<tbody>
			  <tr>
			    <td></td>
				<td class="center-text userEdit"><input type="button" value="删除">
				                        <input type="button" value="修改密码"></td>
			  </tr>
			  <tr>
			    <td></td>
				<td class="center-text userEdit"><input type="button" value="删除">
				                        <input type="button" value="修改密码"></td>
			  </tr>
			  <tr>
			    <td></td>
				<td class="center-text userEdit"><input type="button" value="删除">
				                        <input type="button" value="修改密码"></td>
			  </tr>
			  <tr>
			    <td></td>
				<td class="center-text userEdit"><input type="button" value="删除">
				                        <input type="button" value="修改密码"></td>
			  </tr>
			  <tr>
			    <td></td>
				<td class="center-text userEdit"><input type="button" value="删除">
				                        <input type="button" value="修改密码"></td>
			  </tr>
			</tbody>
		  </table>
		  <div class="center-text">
			  <button type="button" class="btn btn-default" id="checkPrevious">上一页</button>
			  <button type="button" class="btn btn-default" id="checkNext">下一页</button>
		  </div>
		</div>
	  </div>
	</div>
	
	<div id="footer" class="bg-primary">
      <p class="center-text s-top-margin">爱民制药:空调滤芯控制系统, by <a href="http://nacerc.hust.edu.cn/">国家防伪工程技术研究中心</a>.</p>
	</div>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="${ctx}/static/bootstrap-3.3.5-dist/jquery/jquery-1.11.1.js"></script>
	<!-- user jQuery -->
	<script src="${ctx}/static/user/js/user.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="${ctx}/static/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
  </body>
</html>