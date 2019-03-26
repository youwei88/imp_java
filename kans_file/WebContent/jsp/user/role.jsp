<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String base = request.getContextPath();
	request.setAttribute("base", base);
	/* String basePath = request.getScheme() + "://"
	+ request.getServerName() + ":" + request.getServerPort()
	+ base + "/"; */
%>

<html>
<head>
<meta charset="UTF-8">
<title></title>
<link rel="stylesheet" type="text/css"
	href="${base}/js/easyUI/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="${base}/js/easyUI/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="${base}/js/easyUI/themes/demo.css">
<script type="text/javascript"
	src="${base}/js/easyUI/jquery-1.4.4.min.js"></script>
<script type="text/javascript"
	src="${base}/js/easyUI/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${base}/jsp/user/user.js"></script>
<script type="text/javascript">
		
		function getSelections(){
			
			var ss = [];
			
			var rows = $('#dg').datagrid('getSelections');
			
			for(var i=0; i<rows.length; i++){
				var row = rows[i];
				ss.push('<span>'+row.id+'</span>');
			}
			
			$.messager.alert('选中的id', ss.join('<br/>'));
		}
	</script>

</head>
<body id="b">
	<!-- <div style="margin: 20px 0;"></div> -->

	<div id="tb" style="padding: 3px">
		<span>ID:</span> <input id="itemid"
			style="line-height: 26px; border: 1px solid #ccc"> <span>状态:</span>
		<input id="productid"
			style="line-height: 26px; border: 1px solid #ccc"> <a
			href="#" class="easyui-linkbutton" plain="true" onclick="doSearch()">查询</a>
	</div>
	<table id="dg" title="数据集合" style="width: 100%; height: 80%" iconCls="icon-search">

	</table>


	<div id="roleDiv"></div>

	<script>
		 $(function(){
			 $('#dg').datagrid({
			        title: "列表",  
			        url: "<%=base%>/role/roleList",
			        pageSize:5,
			        pageList : [ 5, 10, 15, 20 ],//可以选择的分页集合  
		            nowrap : true,//设置为true，当数据长度超出列宽时将会自动截取  
		            striped : true,//设置为true将交替显示行背景。  
		            collapsible : true,//显示可折叠按钮  
			        columns: [  
			            [  
			                {field: 'id', title: '主键ID', width: 180, align: "center"},
			                {field: 'roleName', title: '角色名称', width: 180, align: "center"},
			                {field: 'status', title: '状态', width: 180, align: 'center',formatter : function(value,row,index){
			                    if(value=='1'){
			                    		return '启用'
			                    	}else{
			                    		return '停用'
			                    	}                        
			                  }
			                },
			                {field: 'mark', title: '备注', width: 180, align: "center"}
			            ]  
			        ], toolbar: [  
			            {  
			                text: '添加',  
			                iconCls: 'icon-add',  
			                handler: function () {
			                	
			                	getSelections();
			                	
			                	//
			                	<%-- dialogWindow("operationDiv", "<%=base%>/user/role", "添加角色", "icon-add",$('#dg')); --%>
			                }  
			            },  
			            '-',
			            {
			                text: '修改角色权限',
			                iconCls: 'icon-edit',
			                handler: function () {
			                	/* $('#w').window('open'); */
			                    /* openDialog("add_dialog", "edit");   */
			                    
			                	<%-- dialogWindow("operationDiv", "<%=base%>/user/role", "修改角色", "icon-add",$('#dg')); --%>
			                }  
			            },
			            '-',  
			            {
			                text: '删除',  
			                iconCls: 'icon-remove',
			                handler: function () {
			                	
			                	<%-- dialogWindow("operationDiv", "<%=base%>/user/role", "添加角色", "icon-edit",$('#dg')) --%>
			                	
			                }  
			            }
			        ],
			        loadMsg : '数据装载中......',  
		            singleSelect:true,//为true时只能选择单行  
		            fitColumns:true,//允许表格自动缩放，以适应父容器  
		            //id : 'xh',//当数据表格初始化时以哪一列来排序  
		            //id : 'desc',//定义排序顺序，可以是'asc'或者'desc'（正序或者倒序）。  
		            remoteSort : false,  
		             frozenColumns : [ [ {  
		                field : 'ck',  
		                checkbox : false  //多选框
		            } ] ],   
		            pagination : true,//分页  
		            rownumbers : true//行数  
			    });  
			  
			 	/* debugger */
			    //设置分页控件  
			    var p = $('#dg').datagrid('getPager');
			    p.pagination({
			        pageSize: 5,//每页显示的记录条数，默认为10  
			        pageList: [5, 10, 15],//可以设置每页记录条数的列表  
			        beforePageText: '第',//页数文本框前显示的汉字  
			        afterPageText: '页    共 {pages} 页',  
			        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'  
			    });  
		 });
		 
	</script>
</body>
</html>