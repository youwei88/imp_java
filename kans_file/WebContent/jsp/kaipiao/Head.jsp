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
<link href="${base}/css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="${base}/css/bootstrap-multiselect.css" rel="stylesheet">
<%--   <link href="${base}/css/font-awesome.min.css" rel="stylesheet" >  --%>
<link href="${base}/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
<link href="${base}/css/bootstrap-select.min.css" rel="stylesheet" media="screen">
<script type="text/javascript" src="${base}/js/vue.js"></script>
<script type="text/javascript" src="${base}/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="${base}/js/jquery.form.js"></script>
<script type="text/javascript" src="${base}/js/jqPaginator.min.js"></script>
<script type="text/javascript" src="${base}/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${base}/js/backbone.paginator.min.js"></script>
<script type="text/javascript" src="${base}/js/vue-resource.min.js"></script>
<script type="text/javascript" src="${base}/js/bootstrap-multiselect.min.js"></script>
<script type="text/javascript" src="${base}/js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
<script type="text/javascript" src="${base}/js/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<script type="text/javascript" src="${base}/js/bootstrap-select.min.js" charset="UTF-8"></script>
<%-- <script src="https://cdn.jsdelivr.net/vue.resource/1.3.1/vue-resource.min.js"></script>
<script src="https://cdn.bootcss.com/backbone.paginator/2.0.5/backbone.paginator.min.js"></script>
<link href="https://cdn.bootcss.com/bootstrap-multiselect/0.9.13/css/bootstrap-multiselect.css" rel="stylesheet">
<script src="https://cdn.bootcss.com/bootstrap-multiselect/0.9.13/js/bootstrap-multiselect.min.js"></script>
<script type="text/javascript" src="${base}/js/xlsx.full.min.js"></script>
<link href="//netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet"> --%>
 <link href="//netdna.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">                   
</head>
</html>