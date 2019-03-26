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
  .loading{  
    width:300px;  
    height:56px;  
    position: absolute;  
    top:50%;  
    left:50%;  
    line-height:56px;  
    color:#fff;  
    padding-left:60px;  
    font-size:15px;  
    background: #000 no-repeat 10px 50%;  
    opacity: 0.7;  
    z-index:9999;  
    -moz-border-radius:20px;  
    -webkit-border-radius:20px;  
    border-radius:20px;  
    filter:progid:DXImageTransform.Microsoft.Alpha(opacity=70);  
}  
.form-style>.row:not(:first-child){
	margin-top: 10px;
}
</style>
</head>
<body>
	<div id="oragnized">
		<div style="margin-top: 10px; margin-left: 10px">
			<!-- <button class="btn btn-danger" type="submit" v-on:click="allmergebilling()">全部合并开票</button>
			<span style="width: 10px"></span> -->
			<button class="btn btn-danger btn-sm" type="submit" v-on:click="mergebilling" ><i class="fa fa-clone" aria-hidden="true"></i> 合并开票</button>
			<!-- <span style="width: 10px"></span>
			<button class="btn btn-warning" type="submit">开&nbsp;&nbsp;票</button> -->
			<span style="width: 10px"></span>
			<button class="btn btn-success btn-sm" type="submit" data-toggle="modal"
				data-target="#myModal" v-on:click=""><i class="fa fa-share-square-o" aria-hidden="true"></i> 导出尚未开票Excel</button>
			<!-- <span style="width: 10px"></span>
			<button class="btn btn-default" type="button" >导出单月份未开票数据</button> -->
			<span style="width: 10px"></span>
			<button class="btn btn-primary btn-sm enter-submit" type="submit"
				v-on:click="searchoffinfo"><i class="fa fa-search" aria-hidden="true"></i> 查&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;询</button>
		</div>
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
						<h4 class="modal-title" id="myModalLabel">尚未开票的数据</h4>
					</div>
					<div class="modal-body">
					
					<form style="margin-top: 10px" class="form-style">
							<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height: 32px;">开始时间:</div>
								<div class="col-md-7">
									<input type="text" class="form-control date" id="choosestart" />
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height: 32px;">结束时间:</div>
								<div class="col-md-7">
									<input type="text" class="form-control date" id="chooseend" />
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height: 32px;">截止到财务审核日期:</div>
								<div class="col-md-7">
									<input type="text" class="form-control date" id="cpudt" />
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height: 32px;">销售组织:</div>
								<div class="col-md-7">
									<input type="text" class="form-control"  id ="VKORGID"  placeholder="例如:6300"  />
								</div>
							</div>
								<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height: 32px;">分销渠道:</div>
								<div class="col-md-7">
									<input type="text" class="form-control"  id = "VTWEGID"  placeholder="例如:10"  />
								</div>
							</div>
								<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height: 32px;">品牌:
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
		<form style="margin: 10px 10px 0 0" name="query-region">
			<div class="row">
				<div class="col-md-1 col-sm-1 col-xs-12" style="padding-left: 30px; padding-top: 8px">StartTime:</div>
				<div class="col-md-2 col-sm-2 col-xs-12 bootstrap-timepicker">
					<input type="text" class="form-control date" id="StartTime" placeholder="必填" />
				</div>
				<div class="col-md-1 col-sm-1 col-xs-12" style="padding-left: 30px; padding-top: 8px">EndTime</div>
				<div class="col-md-2 col-sm-2 col-xs-12 control-label">
					<input type="text" class="form-control date" id="EndTime" placeholder="必填" />
				</div>
				<div class="col-md-1 col-sm-1 col-xs-12" style="padding-left: 30px;">
					销售<br />组织:
				</div>
				<div class="col-md-2 col-sm-2 col-xs-12 control-label">
					<input class="form-control" size="15" type="text"
						placeholder="必填" v-model="VKORG">
				</div>
				<div class="col-md-1 col-sm-1 col-xs-12" style="padding-left: 30px;">
					分销<br />渠道:
				</div>
				<div class="col-md-2 col-sm-2 col-xs-12 control-label">
					<input class="form-control" size="15" placeholder="必填"
						type="text" v-model="VTWEG">
				</div>
			</div>
			<div class="row" style="margin-top:10px;">
				<div class="col-md-1 col-sm-1 col-xs-12" style="padding-left: 30px; padding-top: 8px">
					品&nbsp;&nbsp;牌:
				</div>
				<div class="col-md-2 col-sm-2 col-xs-12">
					<div class="form-group">  
				        <div class="input-group col-xs-12"> 
				            <input type="text" class="form-control input_spart" v-model="SPART" placeholder="非必填">  
				            <span class="input-group-btn">  
				                <select id="selectpicker" class="selectpicker" onchange="selectSpart()" multiple data-live-search="true" tabindex="-1"></select>  
				            </span>  
				        </div>  
				    </div>
				</div>
				<div class="col-md-1 col-sm-1 col-xs-12" style="padding-left: 30px;">
					S&nbsp;A&nbsp;P<br/>票&nbsp;&nbsp;号:
				</div>
				<div class="col-md-2 col-sm-2 col-xs-12">
					<input class="form-control" size="15" type="text"
						v-model="SAPBillingNo" placeholder="非必填">
				</div>
				<div class="col-md-1 col-sm-1 col-xs-12" style="padding-left: 30px; padding-top: 8px">物料号:</div>
				<div class="col-md-2 col-sm-2 col-xs-12">
					<input class="form-control" size="15" type="text" placeholder="非必填"
						v-model="MATNR">
				</div>
				<div class="col-md-1 col-sm-1 col-xs-12" style="padding-left: 30px; padding-top: 8px">
					付款方:
				</div>
				<div class="col-md-2 col-sm-2 col-xs-12">
					<input class="form-control" size="15" type="text" placeholder="非必填"
						v-model="KUNRG">
				</div>
			</div>
		</form>
		<!-- 加载品牌的Modal
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
		</div> -->
		<div v-show="isshow" style="width: 200%">
			<!-- <div>
				<ul class="pagination" id="pagination2"></ul>
			</div> -->
			<div style="margin-top: 10px" class="table-responsive">
				<table class="table table-bordered table-striped table-hover table-condensed">
					<thead>
						<tr>
							<th id="selectAll" v-on:click="allselect" style="cursor: pointer;"><span style="color: red">全选</span></th>
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
							<td><input type="checkbox" class="mycheck"></td>
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
		<div id="loading" class="loading" v-show ="loadcontrol">程序执行中  请等待...</div> 
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
			apiUrl : '/kans_file/kaipiao/getOffBillings',
			exporturl : '/kans_file/kaipiao/downloadExcelOrg',
			getsparturl : '/kans_file/kaipiao/selectSpart',
			mergeurl : '/kans_file/kaipiao/mergeBillings',
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
			NEWSPART : null,
			loadcontrol:false,
			cpudt : null/*,
			mergeIds : null ,
			PageNum : 1,
			totalPages:null,
			currentPage:null,
			controllerpage:0 */
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
			$("#cpudt").datetimepicker({
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
				}
			);
		},
		created:function(){
			this.$http.post(this.getsparturl, "", emulateJSON = false).then(
				function(data) {
					this.spartlists = data.body.lists;
					$('#selectpicker').find('option').remove();
	                for (var i = 0; i < this.spartlists.length; i++) {
	                    $('#selectpicker').append($('<option value=' + this.spartlists[i].spart + '>' + this.spartlists[i].spart + '-' + this.spartlists[i].spart_t + '</option>'));
	                }
	                $('#selectpicker').selectpicker({
	    				style: 'btn-default',
	    				size: 10,
	    				noneSelectedText: ''
	                }).next().css('width', '100%');
				}, function() {
					alert('加载品牌信息失败');
				}
			);
		},
		methods : {
			/* allmergebilling : function(){
				//发送post请求
				if (null == this.mergeIds || "" == this.mergeIds.mergeid || null == this.mergeIds.mergeid) {
					alert('没有发票！');
					return;
				}
				this.loadcontrol = true; 
				this.$http.post(this.mergeurl, JSON.stringify(this.mergeIds),
						emulateJSON = false).then(function(data) {
							this.loadcontrol = false; 
							alert('开票成功');
							$('input[type="checkbox"]').each(function(){
								 $(this).parent().parent().remove();
							}); 
							
				}, function() {
					this.loadcontrol = false;
					alert('开票失败'); //失败处理
				});
			}, */
			mergebilling : function(){
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
				 
				 if (null==j || ""==j) {
					 alert('请选择发票！');
					 return;
				 }
				//发送post请求
				this.loadcontrol = true; 
				this.$http.post(this.mergeurl, JSON.stringify(this.mergeid),
						emulateJSON = false).then(function(data) {
							this.loadcontrol = false; 
							alert('开票成功');
							 /* $('input[type="checkbox"]:checked').each(function(){
								 $(this).parent().parent().remove();
							 }); */
							this.searchoffinfo();
							
				}, function() {
					this.loadcontrol = false;
					alert('开票失败,可能流水号紊乱'); //失败处理
				});
				 
				 
			},
			selectspartconfirm : function(){
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
					$(".mycheck").prop("checked", true);
					this.checkcontroller = 1;

				} else if (this.checkcontroller == 1) {
					$(".mycheck").prop("checked", false);
					this.checkcontroller = 0;

				}
			},
			exportexcelbytime : function() {
				this.exportstart = $("#choosestart").val();
				this.exportend = $("#chooseend").val();
				this.cpudt = $("#cpudt").val();
				this.NEWVKORG = $("#VKORGID").val();
				this.NEWVTWEG = $("#VTWEGID").val();
				this.NEWSPART = $("#SPARTID").val();
				
				location.href = this.exporturl + "?" + "exportstart="
						+ this.exportstart + "&exportend=" + this.exportend + "&cpudt=" + this.cpudt +"&VKORG="+this.NEWVKORG+"&VTWEG="+this.NEWVTWEG+"&SPART="+this.NEWSPART;
			},
			searchoffinfo : function() {
				this.instart = $("#StartTime").val();
				this.inend = $("#EndTime").val();
				if (this.instart.trim() && this.inend.trim() && this.VKORG.trim() && this.VTWEG.trim()) {
					this.request = {
						/* "PageSize" : 18,
						"PageNum" : this.PageNum, */
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
						this.loadcontrol = true; 
					this.$http.post(this.apiUrl, JSON.stringify(this.request),
							emulateJSON = false).then(function(data) {
						this.loadcontrol = false; 
						this.lists = data.body.lists;
						/*this.mergeIds = {"mergeid":data.body.mergeIds};
						 this.currentPage=1;
				        this.totalPages= data.body.total; */
						if (data.body.resultCode != 0) {
							/* if(this.controllerpage ==0)
					    	 {
					    	   this.page();
					    	   this.controllerpage=1;
					    	 } */
					    	$(".mycheck").prop("checked", false);
							this.checkcontroller = 0;
							this.isshow = true;
						} else {
							this.isshow = false;
						}
					}, function() {
						this.loadcontrol = false;
						alert('请求失败处理'); //失败处理
					});
				} else {
					 this.loadcontrol = false;
					alert("请将必填项填写完整");
				}

			},
			/* page:function(){
				var fun = this.searchoffinfo;
				var sum =this.pagesum;
			    $.jqPaginator('#pagination2', {
			  	    totalPages: this.totalPages,
				    visiblePages: 20,
				    currentPage: this.currentPage,
				    prev: '<li class="prev"><a href="javascript:;">Previous</a></li>',
				    next: '<li class="next"><a href="javascript:;">Next</a></li>',
				    page: '<li class="page"><a href="javascript:;">{{page}}</a></li>',
				    onPageChange: function (num, type) {
				    	    this.instart = $("#StartTime").val();
							this.inend = $("#EndTime").val();
				            if(num>1)
				           {    
				            	sum(num);
				            	fun();
				           } 
		            }
			    }); 
			},
			pagesum:function(i){
				this.PageNum =  i;
			} */
		}
	});
	//select触发onchange事件
	function selectSpart(){
		var spartArray = $('#selectpicker').val();
		$('.input_spart').val(spartArray ? spartArray.join(',') : spartArray);
		this.app.SPART = spartArray ? spartArray.join(',') : spartArray;
	}
	$(function(){
		//监听form表单区域的Enter
		$("form[name=query-region]").bind("keydown",function(e){
		    var theEvent = e || window.event;    
		    var code = theEvent.keyCode || theEvent.which || theEvent.charCode;    
		    if (code == 13) {
		    	$('.enter-submit').trigger('click');
	        }
		});
	});
</script>

</html>