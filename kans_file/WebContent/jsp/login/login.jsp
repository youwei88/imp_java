<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%

	String base = request.getContextPath();
	request.setAttribute("base", base);
	/* String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ base + "/"; */
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link type="text/css" rel="stylesheet" href="${base}/css/login.css" />
<script type="text/javascript" src="${base}/js/jquery-1.9.1.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录页面</title>
</head>

<SCRIPT type="text/javascript">
	$(function() {
		//得到焦点
		$("#j_password").focus(function() {
			$("#left_hand").animate({
				left : "150",
				top : " -38"
			}, {
				step : function() {
					if (parseInt($("#left_hand").css("left")) > 140) {
						$("#left_hand").attr("class", "left_hand");
					}
				}
			}, 2000);
			$("#right_hand").animate({
				right : "-64",
				top : "-38px"
			}, {
				step : function() {
					if (parseInt($("#right_hand").css("right")) > -70) {
						$("#right_hand").attr("class", "right_hand");
					}
				}
			}, 2000);
		});
		//失去焦点
		$("#j_password").blur(function() {
			$("#left_hand").attr("class", "initial_left_hand");
			$("#left_hand").attr("style", "left:100px;top:-12px;");
			$("#right_hand").attr("class", "initial_right_hand");
			$("#right_hand").attr("style", "right:-112px;top:-12px");
		});
	});
</SCRIPT>

<body >
	
	<form action="${base}/j_spring_security_check" method="post">
		<DIV class="top_div" style="background: url("${base}/images/hanshu.jpg");"></DIV>
       
		<DIV
			style="background: rgb(255, 255, 255); margin: -100px auto auto; border: 1px solid rgb(231, 231, 231); border-image: none; width: 400px; height: 200px; text-align: center;">

			<DIV style="width: 165px; height: 96px; position: absolute;">

				<DIV class="tou"></DIV>

				<DIV class="initial_left_hand" id="left_hand"></DIV>

				<DIV class="initial_right_hand" id="right_hand"></DIV>
			</DIV>

			<P style="padding: 30px 0px 10px; position: relative;">
				<SPAN class="u_logo"></SPAN> 
				<INPUT class="ipt" type="text" id="j_username"
				name="j_username" placeholder="请输入用户名或邮箱" value="" />

			</P>

			<P style="position: relative;">
				<SPAN class="p_logo"></SPAN> 
				<INPUT class="ipt" id="j_password"
				name="j_password" type="password" placeholder="请输入密码" value="" />
			</P>

			<DIV
				style="height: 50px; line-height: 50px; margin-top: 30px; border-top-color: rgb(231, 231, 231); border-top-width: 1px; border-top-style: solid;">

				<P style="margin: 0px 35px 20px 45px;">
					<SPAN style="float: left;"><A
						style="color: rgb(204, 204, 204);" href="#">忘记密码?</A></SPAN> <SPAN
						style="float: right;"><A
						style="color: rgb(204, 204, 204); margin-right: 10px;" href="#">注册</A>

						<input type="submit" value="登录"
						style="background: rgb(0, 142, 173); padding: 7px 10px; border-radius: 4px; border: 1px solid rgb(26, 117, 152); border-image: none; color: rgb(255, 255, 255); font-weight: bold;"
						href="../j_spring_security_check" /> </SPAN>
				</P>
			</DIV>
		</DIV>
		<div style="text-align: center;">
			<p>
				<%-- 来源:<a href="#" target="_blank">${error}</a> --%>
				<b >${error}</b>
				
				<%-- ${sessionScope.SPRING_SECURITY_LAST_EXCEPTION.message} --%>
				
			</p>
		</div>
	</form>
   
</body>
</html>