<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="Head.jsp" />
<style type="text/css">
  td {
  overflow:hidden; 
  text-overflow:ellipsis;
  white-space:nowrap;
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
</style>
</head>
<body>
	<div id="oragnized">
		<div style="margin-top: 10px; margin-left: 10px">
			<button class="btn btn-danger btn-sm" @click="batchVerification"><i class="fa fa-close" aria-hidden="true"></i> 批量删除</button>
			<button class="btn btn-primary btn-sm enter-submit" type="submit"
				v-on:click="searchoffinfo"><i class="fa fa-search" aria-hidden="true"></i> 查&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;询</button>
		</div>
		<div style="margin: 10px 10px 0 0" class="query-region">
			<div class="row">
				<div class="col-md-1 col-sm-1 col-xs-12" style="padding-left: 30px; padding-top: 8px">StartTime:</div>
				<div class="col-md-2 col-sm-2 col-xs-12 bootstrap-timepicker" >
                   <input type="text" class="form-control date"  id="StartTime" placeholder="必填" />  
				</div>
				<div class="col-md-1 col-sm-1 col-xs-12" style="padding-left: 30px; padding-top: 8px">EndTime:</div>
				<div class="col-md-2 col-sm-2 col-xs-12 control-label"  >
				   <input type="text" class="form-control date"  id="EndTime" placeholder="必填" />  
				</div>
				<div class="col-md-1 col-sm-1 col-xs-12" style="padding-left: 30px;">
					销&nbsp;&nbsp;售<br/>组&nbsp;&nbsp;织:
				</div>
				<div class="col-md-2 col-sm-2 col-xs-12 control-label">
					<input class="form-control" size="15" type="text"
						v-model="VKORG" placeholder="必填" />
				</div>
				<div class="col-md-1 col-sm-1 col-xs-12" style="padding-left: 30px;">
					分&nbsp;&nbsp;销<br/>渠&nbsp;&nbsp;道:
				</div>
				<div class="col-md-2 col-sm-2 col-xs-12 control-label">
					<input class="form-control" size="15" placeholder="必填"
						type="text" v-model="VTWEG" />
				</div>
			</div>
			<div class="row" style="padding-top:10px;">
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
						v-model="VBELN" placeholder="非必填">
				</div>
				<div class="col-md-1 col-sm-1 col-xs-12" style="padding-left: 30px; padding-top: 8px">
					付&nbsp;款&nbsp;方:
				</div>
				<div class="col-md-2 col-sm-2 col-xs-12">
					<input class="form-control" size="15" type="text" placeholder="非必填"
						v-model="KUNRG">
				</div>
				<div class="col-md-1 col-sm-1 col-xs-12" style="padding-left: 30px;"></div>
			</div>
		</div>
		<div v-show="isshow" style="width: 200%; margin:0 0 0 10px;" >
			<div>
				<ul class="pagination" id="pagination2"></ul>
			</div>
			<!-- <div style="margin-top: 10px" class="table-responsive" > -->
			<div class="table-responsive" >
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
							<!-- <th>销售凭证类型</th> -->
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
						<tr v-for="list in lists" >
							<td style="text-align: center;"><input type="checkbox" class="mycheck" :data-listsid="list.id" /></td>
							<!-- <td><button class="btn btn-danger btn-sm" v-on:click="tableclick">删除 </button></td> -->
							<td>{{ list.id}}</td>
							<td>{{ list.vbeln}}</td>
							<td>{{ list.posnr}}</td>
							<td>{{ list.fkart}}</td>
							<td >{{ list.fkart_t}}</td>
							<td>{{ list.vgbel}}</td>
							<td>{{ list.aubel}}</td>
							<!-- <td>{{ list.auart}}</td> -->
							<td>{{ list.vkorg}}</td>
							<td  >{{ list.vkorg_t}}</td>
							<td>{{ list.vtweg}}</td>
							<td >{{ list.vtweg_t}}</td>
							<td>{{ list.spart}}</td>
							<td >{{ list.spart_t}}</td>
							<td>{{ list.vkbur}}</td>
							<td >{{ list.vkbur_t}}</td>
							<td>{{ list.fkdat}}</td>
							<td>{{ list.kunag}}</td>
							<td >{{ list.name1}}</td>
							<td >{{ list.sort1}}</td>
							<td>{{ list.kunrg}}</td>
							<td >{{ list.name2}}</td>
							<td >{{ list.matnr}}</td>
							<td>{{ list.arktx}}</td>
							<td>{{ list.fkimg}}</td>
							<td>{{ list.vrkme}}</td>
							<td>{{ list.hsdj}}</td>
							<td>{{ list.hszj}}</td>
							<td>{{ list.btaux}}</td>
							<td>{{ list.taxde}}</td>
							<td >{{ list.cpudt}}</td>
							<td>{{ list.cputm}}</td>
							<td>{{ list.jsj}}</td>
							<td>{{ list.jsj}}</td>
							<td>{{ list.cbj}}</td>
							<td>{{ list.bukrs}}</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<!-- Modal确定 -->
		<div class="modal fade" id="determine-modal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content modal-sm">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">警告</h4>
					</div>
					<div class="modal-body">
						<span>确定删除吗？</span>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<button type="button" class="btn btn-primary" data-dismiss="modal"
							@click="batchDeleteById">确定</button>
					</div>
				</div>
			</div>
		</div>
		<!-- Modal提示 -->
		<div class="modal fade" id="prompt-modal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content modal-sm">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">错误</h4>
					</div>
					<div class="modal-body">
						<span>请选择一条数据！</span>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
					</div>
				</div>
			</div>
		</div>
	    <div id="loading" class="loading" v-show ="loadcontrol">程序执行中  请等待...</div>  
	</div>
</body>
<%-- <script type="text/javascript" src="${base}/js/kaipiao/OragnizeInfo.js"></script>  --%>
<script type="text/javascript">
	var app = new Vue({
		el : '#oragnized',
		data : {
			instart : null, //开始日期
			inend : null, //结束日期
			VBELN : null, //IMP虚拟开票号
			BillingStream : null, //发票流水号
			VKORG : null, //销售组织
			VTWEG : null, //分销渠道
			SPART : null, //品牌
			KUNAG : null, //售达方
			KUNRG : null,
			apiUrl : '/kans_file/kaipiao/getBillingsForUp',
			apiUrl2 : '/kans_file/kaipiao/updatebillingstatus',
			apiUrl3 : '/kans_file/kaipiao/batchupdatebillingstatus',
			getsparturl : '/kans_file/kaipiao/selectSpart',
			lists : null,
			MATNR : null, //物料号
			isshow : false,
			request : null,
			PageNum : 1,
			PageSize : 50,
			request :null,
			caculate:null,
			controllerpage:0,
			totalPages:null,
			currentPage:null,
			id:null,
			message:null,
			loadcontrol:false,
			spartlists:null,
			checkcontroller:0
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
		},
		methods : {
			allselect : function() {
				if (this.checkcontroller == 0) {
					$(".mycheck").prop("checked", true);
					this.checkcontroller = 1;

				} else if (this.checkcontroller == 1) {
					$(".mycheck").prop("checked", false);
					this.checkcontroller = 0;

				}
			},
			//tableclick:function(e){
			//	var i = e.currentTarget;
			//	this.id= $(i).parent().next().html();
			//	this.deletebyid(this.id);
			//	$(i).parent().parent().remove();
			//},
			//deletebyid:function(id){
			//	this.loadcontrol = true; 
			//	this.id = id;
			//	this.$http.post(this.apiUrl2, JSON.stringify({id:this.id}), emulateJSON = false).then(function(data) {
			//		this.message = data.body.resultMessage;
			//		this.loadcontrol = false; 
			//		alert("隐藏成功");
			//	}, function() {
			//		this.loadcontrol = false;
			//		alert('请求失败处理'); //失败处理
			//	});
			//},
			batchDeleteById:function(){
				var idArray = [];
				$("input[class=mycheck]:checked").each(function(index){
					idArray.push($(this).data('listsid'));
					$(this).parent().parent().remove();
				});
				this.$http.post(this.apiUrl3, JSON.stringify({ idArray : idArray }), emulateJSON = false).then(function(data) {
					this.message = data.body.resultMessage;
					this.loadcontrol = false; 
					alert("批量隐藏成功");
				}, function() {
					this.loadcontrol = false;
					alert('请求失败处理'); //失败处理
				});
			},
			batchVerification:function(){
				if($("input[class=mycheck]:checked").length){
					$('#determine-modal').modal('show');
				}else{
					$('#prompt-modal').modal('show');
				}
			},
			page:function(){
				var fun = this.searchoffinfo;
				var sum =this.pagesum;
			    $.jqPaginator('#pagination2', {
			  	    totalPages: this.totalPages,
				    visiblePages: 10,
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
				           }else{	   
				           } 
				            }
				    }); 
			},
			pagesum:function(i){
				this.PageNum =  i;
			},
			searchoffinfo : function() {
				this.loadcontrol = true; 
				this.instart = $("#StartTime").val();
				this.inend = $("#EndTime").val();
				if(this.instart.trim() && this.inend.trim() && this.VKORG.trim() && this.VTWEG.trim()){
					this.request = {
						"PageSize" : this.PageSize,
						"PageNum" :this.PageNum,
						"VBELN" : this.VBELN,
						"MATNR" : this.MATNR,
						"VKORG" :this.VKORG,
						"VTWEG" :this.VTWEG,
						"SPART" :this.SPART,
						"KUNRG" :this.KUNRG,
						"timebegin":this.instart,
						"timeend" :this.inend
					};
					this.$http.post(this.apiUrl, JSON.stringify(this.request), emulateJSON = false).then(function(data) {
					   this.lists = data.body.lists;
					   this.currentPage=1;
				       this.totalPages= data.body.total;
				       this.loadcontrol = false;
						if(data.body.resultCode != 0)
					   {
							  if(this.controllerpage ==0)
						    	 {
						    	   this.page();
						    	   this.controllerpage=1;
						    	 }else{
						    		 
						    	 }
						
							this.isshow = true;
					    }else{
					    	this.isshow = false;
					    }
					}, function() {
						this.loadcontrol = false;
						alert('请求失败处理'); //失败处理
					});
				}else{
					this.loadcontrol = false;
					alert("请将必填项填写完整");
				}
			}
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
		$(".query-region").bind("keydown",function(e){
		    var theEvent = e || window.event;    
		    var code = theEvent.keyCode || theEvent.which || theEvent.charCode;    
		    if (code == 13) {
		    	$('.enter-submit').trigger('click');
	        }
		});
	});
</script>

</html>