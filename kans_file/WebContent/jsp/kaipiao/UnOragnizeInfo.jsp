<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="Head.jsp" />
<style type="text/css">
td {
	overflow: hidden;
	text-overflow: ellipsis;
	white-space: nowrap;
}
</style>
</head>
<body>
	<div id="oragnized">
		<!-- Modal -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">请选择导出时间</h4>
					</div>
					<div class="modal-body">
					
					<form style="margin-top: 10px">
							<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right;">开始时间:</div>
								<div class="col-md-7">
									<input type="text" class="form-control date" id="choosestart" />
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right;">结束时间:</div>
								<div class="col-md-7">
									<input type="text" class="form-control date" id="chooseend" />
								</div>
							</div>
								<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right;">销售组织:</div>
								<div class="col-md-7">
									<input type="text" class="form-control"  id ="VKORGID"  placeholder="例如:6300"  />
								</div>
							</div>
								<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right;">分销渠道:</div>
								<div class="col-md-7">
									<input type="text" class="form-control"  id = "VTWEGID"  placeholder="例如:10"  />
								</div>
							</div>
								<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right;">品牌:
									</div>
								<div class="col-md-7">
									<input type="text" class="form-control" id ="SPARTID"  placeholder="例如:01,02,03"  />
								</div>
							</div>
					  </form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<button type="button" class="btn btn-primary" data-dismiss="modal"
							v-on:click="exportexcelbytime">执行导出</button>
					</div>
				</div>
			</div>
		</div>
		<form style="margin-top: 10px">
			<div class="row">
				<div class="col-md-1 " style="padding-left: 30px; padding-top: 8px">StartTime:</div>
				<div class="col-md-2 bootstrap-timepicker">
					<input type="text" class="form-control date" id="StartTime" />
				</div>
				<div class="col-md-1" style="padding-left: 30px; padding-top: 8px">EndTime</div>
				<div class="col-md-2 control-label">
					<input type="text" class="form-control date" id="EndTime" />
				</div>
				<div class="col-md-1">SAP票号:</div>
				<div class="col-md-2">
					<input class="form-control" size="15" type="text"
						v-model="SAPBillingNo" placeholder="SAP发票号">
				</div>
				<div class="col-md-1" style="padding-left: 30px;">
					付款方:
				</div>
				<div class="col-md-2" >
					<input class="form-control" size="15" type="text" placeholder="付款方"
						v-model="KUNRG">
				</div>
			</div>
			<div class="row">
				<div class="col-md-1" style="padding-left: 30px;">
					销售<br />组织:
				</div>
				<div class="col-md-2 control-label" style="margin-top: 7px">
					<input class="form-control" size="15" type="text"
						placeholder="销售组织" v-model="VKORG">
				</div>
				<div class="col-md-1" style="padding-left: 30px;">
					分销<br />渠道:
				</div>
				<div class="col-md-2 control-label" style="margin-top: 7px">
					<input class="form-control" size="15" placeholder="分销渠道"
						type="text" v-model="VTWEG">
				</div>
				<div class="col-md-1" style="padding-left: 30px;">
					</br>
					<span>品牌:</span><i class="fa fa-plus-square" data-toggle="modal"
						data-target="#sparkmodel" style="color: red;"></i>
				</div>
				<div class="col-md-2" style="margin-top: 7px">
					<input class="form-control" size="15" type="text" placeholder="品牌"
						v-model="SPART">
				</div>
				<div class="col-md-1" style="padding-left: 30px;">
					<br />
				</div>
				<div class="col-md-2" style="margin-top: 7px">
					<button class="btn btn-primary" type="submit"
				v-on:click="searchoffinfo">查&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;询</button>
				</div>
			</div>
		</form>
		<!-- 加载品牌的Modal -->
		<div class="modal fade" id="sparkmodel" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">请选择品牌</h4>
					</div>
					<div class="modal-body">
						<div class="table-responsive">
							<table class="table table-bordered table-striped ">
								<thead>
									<tr>
										<th>选择</th>
										<th>品牌号</th>
										<th>平排名</th>
									</tr>
								</thead>
								<tbody v-for="myspark in spartlists" id="sparttable">
									<tr>
										<td><input type="checkbox"></td>
										<td>{{ myspark.spart }}</td>
										<td>{{ myspark.spart_t }}</td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<button type="button" class="btn btn-primary" data-dismiss="modal"
							v-on:click="selectspartconfirm">确定</button>
					</div>
				</div>
			</div>
		</div>
		<div v-show="isshow" style="width: 200%">
			<div style="margin-top: 10px" class="table-responsive">
				<table class="table table-bordered table-striped ">
					<thead>
						<tr>
							<th id="selectAll" v-on:click="allselect">全选</th>
							<th>序号</th>
							<th>SAP票号</th>
							<th>发票行号</th>
							<th>发票类型</th>
							<th>发票类型描述</th>
							<th>发货单</th>
							<th>销售凭证</th>
							<th>销售组织</th>
							<th>销售组织描述</th>
							<th>分销渠道</th>
							<th>分销渠道描述</th>
							<th>品牌</th>
							<th>品牌描述</th>
							<th>销售部门</th>
							<th>销售部门描述</th>
							<th>开票日期</th>
							<th>售达方</th>
							<th>售达方名称</th>
							<th>售达方简称</th>
							<th>付款方</th>
							<th>付款方名称</th>
							<th>物料号</th>
							<th>物料描述</th>
							<th>开票数量</th>
							<th>销售单位</th>
							<th>含税单价</th>
							<th>含税总价</th>
							<th>税率</th>
							<th>税额</th>
							<th>财务审核日期</th>
							<th>财务审核时间</th>
							<th>供货价</th>
							<th>结算价</th>
							<th>成本价</th>
							<th>公司代码</th>
						</tr>
					</thead>
					<tbody>
						<tr v-for="list in lists">
							<td><input type="checkbox"></td>
							<td>{{ list.id}}</td>
							<td>{{ list.vbeln}}</td>
							<td>{{ list.posnr}}</td>
							<td>{{ list.fkart}}</td>
							<td>{{ list.fkart_t}}</td>
							<td>{{ list.vgbel}}</td>
							<td>{{ list.aubel}}</td>
							<td>{{ list.vkorg}}</td>
							<td>{{ list.vkorg_t}}</td>
							<td>{{ list.vtweg}}</td>
							<td>{{ list.vtweg_t}}</td>
							<td>{{ list.spart}}</td>
							<td>{{ list.spart_t}}</td>
							<td>{{ list.vkbur}}</td>
							<td>{{ list.vkbur_t}}</td>
							<td>{{ list.fkdat}}</td>
							<td>{{ list.kunag}}</td>
							<td>{{ list.name1}}</td>
							<td>{{ list.sort1}}</td>
							<td>{{ list.kunrg}}</td>
							<td>{{ list.name2}}</td>
							<td>{{ list.matnr}}</td>
							<td>{{ list.arktx}}</td>
							<td>{{ list.fkimg}}</td>
							<td>{{ list.vrkme}}</td>
							<td>{{ list.hsdj}}</td>
							<td>{{ list.hszj}}</td>
							<td>{{ list.btaux}}</td>
							<td>{{ list.taxde}}</td>
							<td>{{ list.cpudt}}</td>
							<td>{{ list.cputm}}</td>
							<td>{{ list.ghj}}</td>
							<td>{{ list.jsj}}</td>
							<td>{{ list.cbj}}</td>
							<td>{{ list.bukrs}}</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	var app = new Vue({
		el : '#oragnized',
		data : {
			instart : null, //开始日期
			inend : null, //结束日期
			SAPBillingNo : null, //IMP虚拟开票号
			BillingStream : null, //发票流水号
			VKORG : null, //销售组织
			VTWEG : null, //分销渠道
			SPART : null, //品牌
			KUNAG : null, //售达方
			KUNRG : null, //付款方
			apiUrl : '/kans_file/kaipiao/getUnOrgBilling',
			/* exporturl : '/kans_file/kaipiao/downloadExcelOrg',
			mergeurl : '/kans_file/kaipiao/mergeBillings', */
			getsparturl : '/kans_file/kaipiao/selectSpart',
			lists : null,
			MATNR : null, //物料号
			isshow : false,
			request : null,
			PageNum : 1,
			PageSize : 50,
			request : null,
			caculate : null,
			checkcontroller : 0,
			exportstart : null,
			exportend : null,
			spartlists : null,
			mergeid : null,
			NEWVKORG : null,
			NEWVTWEG : null,
			NEWSPART : null
		},
		mounted : function() {
			$("#choosestart").datetimepicker({
				format : 'yyyy-mm-dd',
				language : "zh-CN",
				weekStart : 1,
				todayBtn : 1,
				autoclose : 1,
				todayHighlight : 1,
				startView : 2,
				minView : 2
			});
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
			$("#chooseend").datetimepicker({
				format : 'yyyy-mm-dd',
				language : "zh-CN",
				weekStart : 1,
				todayBtn : 1,
				autoclose : 1,
				todayHighlight : 1,
				startView : 2,
				minView : 2
			});
			this.$http.post(this.getsparturl, "", emulateJSON = false).then(
					function(data) {
						this.spartlists = data.body.lists;
					}, function() {
						alert('加载品牌信息失败');
					});
		},
		methods : {
			/* mergebilling : function(){
				var j =null;
				 $('input[type="checkbox"]:checked').each(function(){
					 if(j!=null)
					 {
						 j = j +","+ $(this).parent().next().html().trim();
					 }
					 else
					{  
						 j = $(this).parent().next().html().trim();
					}
				    }); 
				 this.mergeid = 
					 {
						 "mergeid":j
					 };
				 
				//发送post请求
					this.$http.post(this.mergeurl, JSON.stringify(this.mergeid),
							emulateJSON = false).then(function(data) {
								alert('开票成功');
								 $('input[type="checkbox"]:checked').each(function(){
									 $(this).parent().parent().remove();
								 }); 
								
					}, function() {
						alert('开票失败'); //失败处理
					});
				 
				 
			}, */
		/* 	selectspartconfirm : function(){
				var i = null;
				 $('input[type="checkbox"]:checked').each(function(){
					 $(this).prop("checked",false);
					 if(i!=null)
					 {
						 i = i +","+ $(this).parent().next().html().trim();
					 }
					 else
					{  
						 i = $(this).parent().next().html().trim();
					}
				}); 
               this.SPART = i;
         
			},
			showmodal : function() {
				$('#myModal').on('shown.bs.modal', function() {
					$('#myInput').focus()
				});
			},
			allselect : function() {
				if (this.checkcontroller == 0) {
					$(":checkbox").prop("checked", true);
					this.checkcontroller = 1;

				} else if (this.checkcontroller == 1) {
					$(":checkbox").prop("checked", false);
					this.checkcontroller = 0;

				}
			}, */
		/* 	exportexcelbytime : function() {
				this.exportstart = $("#choosestart").val();
				this.exportend = $("#chooseend").val();
				this.NEWVKORG = $("#VKORGID").val();
				this.NEWVTWEG = $("#VTWEGID").val();
				this.NEWSPART = $("#SPARTID").val();
				
				location.href = this.exporturl + "?" + "exportstart="
						+ this.exportstart + "&exportend=" + this.exportend+"&VKORG="+this.NEWVKORG+"&VTWEG="+this.NEWVTWEG+"&SPART="+this.NEWSPART;
			}, */
			searchoffinfo : function() {
				this.instart = $("#StartTime").val();
				this.inend = $("#EndTime").val();
				if (this.instart != '' && this.instart != null
						&& this.inend != '' && this.inend != null
						&& this.VKORG != '' && this.VKORG != null
						&& this.VTWEG != '' && this.VTWEG != null
						&& this.SPART != '' && this.SPART != null) {
					this.request = {
						"PageSize" : this.PageSize,
						"PageNum" : this.PageNum,
						"VBELN" : this.SAPBillingNo,
						"MATNR" : this.MATNR,
						"VKORG" : this.VKORG,
						"VTWEG" : this.VTWEG,
						"SPART" : this.SPART,
						"KUNRG" : this.KUNRG,
						"timebegin" : this.instart,
						"timeend" : this.inend
					};
					//发送post请求
					this.$http.post(this.apiUrl, JSON.stringify(this.request),
							emulateJSON = false).then(function(data) {
						this.lists = data.body.lists;
						if (data.body.resultCode != 0) {
							this.isshow = true;
						} else {
							this.isshow = false;
						}
					}, function() {
						alert('请求失败处理'); //失败处理
					});
				} else {
					 this.loadcontrol = false;
					alert("请正确输入参数");
					
				}

			}

		}

	});
</script>

</html>