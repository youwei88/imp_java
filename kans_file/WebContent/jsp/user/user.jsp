<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

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
<link rel="stylesheet" type="text/css" href="${base}/js/easyUI/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css" href="${base}/js/easyUI/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${base}/js/easyUI/themes/demo.css">
<script type="text/javascript" src="${base}/js/easyUI/jquery-1.4.4.min.js"></script>
<script type="text/javascript" src="${base}/js/easyUI/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${base}/jsp/user/user.js"></script>
<script type="text/javascript">
		
		function getSelections(){
			
			var ids = [];
			var rows = $('#tb').datagrid('getSelections');
			alert(rows);
			
			/* for(var i=0; i<rows.length; i++){
				ids.push(rows[i].itemid);
			} */
			/* alert(ids.join('\n')); */
		}
	</script>

</head>
<body id="b">
	<!-- <div style="margin: 20px 0;"></div> -->

	<div id="tb" style="padding:3px">
		<span>ID:</span>
		<input id="itemid" style="line-height:26px;border:1px solid #ccc">
		<span>状态:</span>
		<input id="productid" style="line-height:26px;border:1px solid #ccc">
		<a href="#" class="easyui-linkbutton" plain="true" onclick="doSearch()">查询</a>
	</div>
	<table id="dg" title="数据集合"
		style="width: 100%; height: 80%" iconCls="icon-search" >
		<!-- 
		data-options="
				rownumbers:true,
				singleSelect:true,
				autoRowHeight:false,
				pagination:true,
				pageSize:20"
		<thead>
			<tr>
				<th field="inv" width="80">Inv No</th>
				<th field="date" width="100">Date</th>
				<th field="name" width="80">Name</th>
				<th field="amount" width="80" align="right">Amount</th>
				<th field="price" width="80" align="right">Price</th>
				<th field="cost" width="100" align="right">Cost</th>
				<th field="note" width="110">Note</th>
			</tr>
		</thead> -->
	</table>
	
	<!--页面弹框修改窗口-->
	<!-- <div id="w" class="easyui-window" title="修改密码" 
	data-options="iconCls:'icon-edit',modal:true,collapsible:false,minimizable:false,maximizable:false,resizable:false,closed:true" 
		style="width: 600px; height: 400px; padding: 5px; background: #fafafa;">
		<div class="easyui-layout" fit="true">
			<div region="center" border="false"
				style="padding: 10px; background: #fff; border: 1px solid #ccc;">
				<table cellpadding=3>
					<tr>
						<td>新密码：</td>
						<td><input id="txtNewPass" type="Password" class="txt01" /></td>
					</tr>
					<tr>
						<td>确认密码：</td>
						<td><input id="txtRePass" type="Password" class="txt01" /></td>
					</tr>
				</table>
			</div>
			<div region="south" border="false" style="text-align: right; height: 30px; line-height: 30px;">
				<a id="btnEp" class="easyui-linkbutton" icon="icon-ok"
					href="javascript:closePwd()"> 确定</a> <a id="btnCancel"
					class="easyui-linkbutton" icon="icon-cancel"
					href="javascript:closePwd()">取消</a>
			</div>
		</div>
	</div> -->
	
	
	<div id="operationDiv"></div>
	
	<script>
		/* (function($) {
			function pagerFilter(data) {
				if ($.isArray(data)) { // is array
					data = {
						total : data.length,
						rows : data
					}
				}
				var target = this;
				var dg = $(target);
				var state = dg.data('datagrid');
				var opts = dg.datagrid('options');
				
				if (!state.allRows) {
					state.allRows = (data.rows);
				}
				if (!opts.remoteSort && opts.sortName) {
					var names = opts.sortName.split(',');
					var orders = opts.sortOrder.split(',');
					state.allRows
							.sort(function(r1, r2) {
								var r = 0;
								for (var i = 0; i < names.length; i++) {
									var sn = names[i];
									var so = orders[i];
									var col = $(target).datagrid(
											'getColumnOption', sn);
									var sortFunc = col.sorter
											|| function(a, b) {
												return a == b ? 0 : (a > b ? 1
														: -1);
											};
									r = sortFunc(r1[sn], r2[sn])
											* (so == 'asc' ? 1 : -1);
									if (r != 0) {
										return r;
									}
								}
								return r;
							});
				}
				var start = (opts.pageNumber - 1) * parseInt(opts.pageSize);
				var end = start + parseInt(opts.pageSize);
				data.rows = state.allRows.slice(start, end);
				return data;
			}

			var loadDataMethod = $.fn.datagrid.methods.loadData;
			var deleteRowMethod = $.fn.datagrid.methods.deleteRow;
			$.extend(
							$.fn.datagrid.methods,
							{
								clientPaging : function(jq) {
									return jq.each(function() {
										var dg = $(this);
										var state = dg.data('datagrid');
										var opts = state.options;
										opts.loadFilter = pagerFilter;
										var onBeforeLoad = opts.onBeforeLoad;
										opts.onBeforeLoad = function(param) {
											state.allRows = null;
											return onBeforeLoad.call(this,
													param);
										}
										var pager = dg.datagrid('getPager');
										pager.pagination({
											onSelectPage : function(pageNum,
													pageSize) {
												opts.pageNumber = pageNum;
												opts.pageSize = pageSize;
												pager.pagination('refresh', {
													pageNumber : pageNum,
													pageSize : pageSize
												});
												dg.datagrid('loadData',
														state.allRows);
											}
										});
										$(this)
												.datagrid('loadData',
														state.data);
										if (opts.url) {
											$(this).datagrid('reload');
										}
									});
								},
								loadData : function(jq, data) {
									jq
											.each(function() {
												$(this).data('datagrid').allRows = null;
											});
									return loadDataMethod.call(
											$.fn.datagrid.methods, jq, data);
								},
								deleteRow : function(jq, index) {
									return jq
											.each(function() {
												var row = $(this).datagrid(
														'getRows')[index];
												deleteRowMethod.call(
														$.fn.datagrid.methods,
														$(this), index);
												var state = $(this).data(
														'datagrid');
												if (state.options.loadFilter == pagerFilter) {
													for (var i = 0; i < state.allRows.length; i++) {
														if (state.allRows[i] == row) {
															state.allRows
																	.splice(i,
																			1);
															break;
														}
													}
													$(this).datagrid(
															'loadData',
															state.allRows);
												}
											});
								},
								getAllRows : function(jq) {
									return jq.data('datagrid').allRows;
								}
							})
		})(jQuery); */

		/* function getData() {
			var rows = [];
			for (var i = 1; i <= 800; i++) {
				var amount = Math.floor(Math.random() * 1000);
				var price = Math.floor(Math.random() * 1000);
				rows.push({
					inv : 'Inv No ' + i,
					date : $.fn.datebox.defaults.formatter(new Date()),
					name : 'Name ' + i,
					amount : amount,
					price : price,
					cost : amount * price,
					note : 'Note ' + i
				});
			}
			return rows;
		} */

		
		/* $(function() {
			
			$('#dg').datagrid({
				data : getData()
			}).datagrid('clientPaging');
			
			//添加控件
			var pager = $('#dg').datagrid().datagrid('getPager');	// get the pager of datagrid
			pager.pagination({
				buttons:[{
					iconCls:'icon-remove',
					handler:function(){
						
						var rows = $('#dg').datagrid('getSelections');
						alert(rows);
						
						for(var i=0; i<rows.length; i++){
							alert(rows[i].inv);
						}
					alert(ids.join('\n'));
						
						getSelections();
						alert('删除');
					}
				},{
					iconCls:'icon-add',
					handler:function(){
						alert('add');
					}
				},{
					iconCls:'icon-edit',
					handler:function(){
						alert('edit');
					}
				}]
			});	
			
			
		});
		 */
		 
		 $(function(){
			 $('#dg').datagrid({
			        title: "列表",  
			        url: "<%=base%>/user/userList",
			        pageSize:5,
			        pageList : [ 5, 10, 15, 20 ],//可以选择的分页集合  
		            nowrap : true,//设置为true，当数据长度超出列宽时将会自动截取  
		            striped : true,//设置为true将交替显示行背景。  
		            collapsible : true,//显示可折叠按钮  
			        columns: [  
			            [  
			                {field: 'id', title: '主键ID', width: 180, align: "center"},  
			                {field: 'userName', title: '参数名称', width: 180, align: "center"},  
			                {field: 'email', title: '邮箱', width: 180, align: 'center'},  
			                {field: 'qq', title: 'qq', width: 180, align: "center"}  
			            ]  
			        ], toolbar: [  
			            {  
			                text: '添加',  
			                iconCls: 'icon-add',  
			                handler: function () {
			                	
			                	//
			                	<%-- dialogWindow("operationDiv", "<%=base%>/user/role", "添加角色", "icon-add",$('#dg')); --%>
			                }  
			            },  
			            '-',
			            {
			                text: '修改角色',  
			                iconCls: 'icon-edit',
			                handler: function () {
			                	/* $('#w').window('open'); */
			                    /* openDialog("add_dialog", "edit");   */
			                    
			                	dialogWindow("operationDiv", "<%=base%>/user/role", "修改角色", "icon-add",$('#dg'));
			                }  
			            },
			            '-',  
			            {
			                text: '删除',  
			                iconCls: 'icon-remove',
			                handler: function () {
			                	
			                	dialogWindow("operationDiv", "<%=base%>/user/role", "添加角色", "icon-edit",$('#dg'))
			                	
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
		 
		 
		 
		 
		 
		<%-- //设置登录窗口
        function openPwd() {
			
            $('#w').window({
                title: '修改密码',
                width: 300,
                modal: true,
                shadow: false,
                closed: true,
                height: 160,
                resizable:false
            });
        }
		
      //关闭登录窗口
        function closePwd() {
            $('#w').window('close');
        }
      
      
      
      
      //
        function SendProduct() {
            $("#SendProduct").dialog("open");
        }
      
        $("#SendProduct").dialog({
            title: '删除',
            href: '<%=base%>/jsp/user/editUser.jsp',
            width:'900',
 	    	height:'600',
            iconCls: 'icon-edit',
            modal: true,
            closed: true
        }); --%>
	</script>
</body>
</html>