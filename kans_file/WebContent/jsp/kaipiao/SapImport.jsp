<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="Head.jsp" />
</head>
<body>
<div id="SapImport">
<div v-show="formisshow">
<form style="margin-top: 10px">
	<div class="row">
		<div class="col-md-1 " style="padding-left: 30px; padding-top: 8px">StartTime:</div>
		<div class="col-md-2 bootstrap-timepicker" >
           <input type="text" class="form-control date"  id="StartTime" placeholder="必填" />  
		</div>
		<div class="col-md-1" style="padding-left: 30px; padding-top: 8px">EndTime</div>
		<div class="col-md-2 control-label"  >
		   <input type="text" class="form-control date"  id="EndTime" placeholder="必填" />  
		</div>
		<!-- <div class="col-md-1" style="padding-left: 30px;">公&nbsp;&nbsp;司<br/>编&nbsp;&nbsp;码:</div>
		<div class="col-md-2">
			<input class="form-control" size="15" type="text"
				v-model="companyCode" placeholder="必填">
		</div> -->
		<div class="col-md-1" style="padding-left: 30px;">
		<button class="btn btn-warning" v-on:click="doimport" ><i class="fa fa-sign-in" aria-hidden="true"></i> 开始导入</button>
		</div>
	</div>
</form>
</div>
<div v-show="isshow">
<h4>&nbsp;&nbsp;正在进行导入:</h4>
			<div style="margin-top: 10px" class="table-responsive" >
				<table class="table table-bordered table-striped " >
					<thead>
						<tr>
							<th>序号</th>
							<th>公司代码</th>
							<th>开始时间</th>
							<th>结束时间</th>
						</tr>
					</thead>
					<tbody>
						<tr v-for="list in lists">
							<td>{{ list.id}}</td>
							<td>{{ list.companycode}}</td>
							<td>{{ list.begin}}</td>
							<td>{{ list.end}}</td>
						</tr>
					</tbody>
				</table>
</div>
</div>
</div>
</body>
<script type="text/javascript">
	var app = new Vue({
		el : '#SapImport',
		data : {
			instart : null, //开始日期
			inend : null, //结束日期
			companyCode:null,
			apiUrl : "/kans_file/sap/sapimportselect",
			apiUrlimport: "/kans_file/sap/sapimport",
			lists : null,
			MATNR : null, //物料号
			isshow : false,
			request : null,
			PageNum : 1,
			PageSize : 50,
			request :null,
			caculate:null,
			request:null,
			resultcode:null,
			formisshow:false
		},
		mounted:function(){
			$("#StartTime").datetimepicker({
        		format : 'yyyy-mm-dd',
        		language : "zh-CN",
        		weekStart : 1,
        		todayBtn : 1,
        		autoclose : 1,
        		todayHighlight : 1,
        		startView : 2,
        		minView : 2
        	});
			$("#EndTime").datetimepicker({
					format : 'yyyy-mm-dd',
					language : "zh-CN",
					weekStart : 1,
					todayBtn : 1,
					autoclose : 1,
					todayHighlight : 1,
					startView : 2,
					minView : 2
				});
		  this.$http.post(this.apiUrl,JSON.stringify(this.request),emulateJSON = false).then(function(data) {
				this.lists = data.body.lists;
				this.resultcode = data.body.resultCode;
				if(data.body.resultCode != 0)
			   {    this.formisshow=false;
					this.isshow = true;
			    }else{
			    	 this.formisshow=true;
			    	this.isshow = false;
			    }
			}, function() {
				alert('请求失败处理'); //失败处理
			});
		},
		methods : {
			    doimport : function() {
				this.instart = $("#StartTime").val();
				this.inend = $("#EndTime").val();
				if(this.instart.trim() && this.inend.trim() &&  this.resultcode != 1){
					this.request = {
							"timebegin":this.instart,
							"timeend" :this.inend,
							"companyCode" :this.companyCode
						};
					this.formisshow =false;
					this.isshow = true;
					this.lists={
							id:1,
							companycode:this.companyCode,
							begin:this.begin,
							end:this.end
							
					};
					this.resultcode = 1;
				  /*   $("#page li").click(function(){
					   $("li").removeClass("active");
					   $(this).addClass("active");
					   if($(this).index()===0&&this.PageNum!=1)
						 {
						   this.PageNum=this.PageNum-1;
						 }
					   if($(this).index()===11&&this.PageNum!=1)
						 {
						   this.PageNum=this.PageNum+1;
						 }
					   this.PageNum =$(this).index();
					});  */
					//发送post请求
					this.$http.post(this.apiUrlimport, JSON.stringify(this.request), emulateJSON = false).then(function(data) {
						this.lists = data.body.lists;
						//alert(this.lists);
						if(data.body.resultCode != 0)
					   {
							this.isshow = true;
					    }else{
					    	this.isshow = false;
					    }
					}, function() {
					});
					alert("开始导入数据");
					//window.location.reload();
				}else{
					alert("请将必填项填写完整");
				}
			}
		}	
	});
</script>

</html>