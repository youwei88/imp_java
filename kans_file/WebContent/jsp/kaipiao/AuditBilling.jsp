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
	<div id="auditbilling">
	      <div style="margin-top: 10px; margin-left: 10px">
			<div class="row">
			<div class="col-md-2 col-sm-2 col-xs-2" >
			<form style="margin-top: 3px" method="post" action="/kans_file/uploadExcel/importGold" enctype="multipart/form-data" id="insertgold">
				<input id="upfile" type="file" name="upfile" >
			</form>
			</div>
			<div class="col-md-10 col-sm-10 col-xs-10">
				<Button  class="btn btn-info btn-sm" v-on:click="uploadExcel" ><i class="fa fa-sign-in" aria-hidden="true"></i> 导入金税发票</Button>
				<button class="btn btn-danger btn-sm" type="submit"
					v-on:click="rollbackbillings"><i class="fa fa-close" aria-hidden="true"></i> 取消已审发票</button>
				<button class="btn btn-success btn-sm" type="submit" data-toggle="modal"
					data-target="#myModal"><i class="fa fa-share-square-o" aria-hidden="true"></i> 导出已审发票</button>
				<!--<button style="float: left;" class="btn btn-danger" v-on:click="uploadExcel">导入金税发票</button>  -->
				<button class="btn btn-warning btn-sm" type="submit"
				 data-target="#myModalGetAllMerged" data-toggle="modal" ><i class="fa fa-share-square-o" aria-hidden="true"></i> 导出单月开票已审核全部原始数据</button>
				 <button class="btn btn-primary btn-sm enter-submit" type="submit"
					v-on:click="searchmergebilling"><i class="fa fa-search" aria-hidden="true"></i> 查&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;询</button>
			</div>
			<!-- <button class="btn btn-info" type="submit"
			 data-target="#myModalGetAllMerged" data-toggle="modal" >导出已审核未反导金税发票的数据</button> -->
			<!-- <div class="col-md-2">
			<button class="btn btn-primary" type="submit"
				data-target="#myModalGetAllisMerged" v-on:click="exportauditzhijin" data-toggle="modal">根据时间导出至今未开票的数据</button>
			</div>
			</div> -->
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
						<h4 class="modal-title" id="myModalLabel">请选择导出时间</h4>
					</div>
					<div class="modal-body">
					<form style="margin-top: 10px" class="form-style">
							<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height:32px;">开始时间:</div>
								<div class="col-md-7">
									<input type="text" class="form-control date" id="choosestart" placeholder="审核开始日期"  />
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height:32px;">结束时间:</div>
								<div class="col-md-7">
									<input type="text" class="form-control date" id="chooseend" placeholder="审核结束日期" />
								</div>
							</div>
								<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height:32px;">销售组织:</div>
								<div class="col-md-7">
									<input type="text" class="form-control"  id ="VKORGID"  placeholder="例如:6300"  />
								</div>
							</div>
								<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height:32px;">分销渠道:</div>
								<div class="col-md-7">
									<input type="text" class="form-control"  id = "VTWEGID"  placeholder="例如:10"  />
								</div>
							</div>
								<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height:32px;">品牌:
									</div>
								<div class="col-md-7">
									<input type="text" class="form-control" id ="SPARTID"  placeholder="例如:01,02,03"  />
								</div>
							</div>
					  </form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<button type="button" class="btn btn-primary" data-dismiss="modal" v-on:click="exportexcelbytime">执行导出</button>
					</div>
				</div>
			</div>
		</div>
			<!-- Modal -->
		<div class="modal fade" id="myModalGetAllMerged" tabindex="-1" role="dialog"
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
					
					<form style="margin-top: 10px" class="form-style">
							<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height:32px;">开始时间:</div>
								<div class="col-md-7">
									<input type="text" class="form-control date" id="choosestartquanbu" placeholder="审核开始日期"  />
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height:32px;">结束时间:</div>
								<div class="col-md-7">
									<input type="text" class="form-control date" id="chooseendquanbu" placeholder="审核结束日期" />
								</div>
							</div>
					  </form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<button type="button" class="btn btn-primary" data-dismiss="modal" v-on:click="exportaudityuanshi" >执行导出</button>
					</div>
				</div>
			</div>
		</div>
		
		<div class="modal fade" id="myModalGetAllMerged2" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">请选择审核时间</h4>
					</div>
					<div class="modal-body">
					
					<form style="margin-top: 10px">
							<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right;">开始时间:</div>
								<div class="col-md-7">
									<input type="text" class="form-control date" id="choosestartquanbu2" placeholder="审核开始日期"  />
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right;">结束时间:</div>
								<div class="col-md-7">
									<input type="text" class="form-control date" id="chooseendquanbu2" placeholder="审核结束日期" />
								</div>
							</div>
					  </form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<button type="button" class="btn btn-primary" data-dismiss="modal" v-on:click="exportaudityuanshi" >执行导出</button>
					</div>
				</div>
			</div>
		</div>
		
			<!-- Modal -->
		<div class="modal fade" id="myModalGetAllisMerged" tabindex="-1" role="dialog"
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
									<input type="text" class="form-control date" id="choosestartzhijin" placeholder="例如:2017-01-01"  />
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right;">结束时间:</div>
								<div class="col-md-7">
									<input type="text" class="form-control date" id="chooseendzhijin" placeholder="例如:2017-06-26" />
								</div>
							</div>
					  </form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<button type="button" class="btn btn-primary" data-dismiss="modal" >执行导出</button>
					</div>
				</div>
			</div>
		</div>
		<form style="margin: 10px 10px 0 0;" name="query-region">
			<div class="row">
				<div class="col-md-1 " style="padding-left: 30px; padding-top: 8px">StartTime:</div>
				<div class="col-md-2 bootstrap-timepicker">
					<input type="text" class="form-control date" id="StartTime"  placeholder="必填" />
				</div>
				<div class="col-md-1" style="padding-left: 30px; padding-top: 8px">EndTime</div>
				<div class="col-md-2 control-label">
					<input type="text" class="form-control date" id="EndTime" placeholder="必填"  />
				</div>
				<div class="col-md-1" style="padding-left: 30px;">
					销&nbsp;&nbsp;售<br />组&nbsp;&nbsp;织:
				</div>
				<div class="col-md-2 control-label">
					<input class="form-control" size="15" type="text"
						placeholder="必填" v-model="VKORG">
				</div>
				<div class="col-md-1" style="padding-left: 30px;">
					分&nbsp;&nbsp;销<br />渠&nbsp;&nbsp;道:
				</div>
				<div class="col-md-2 control-label">
					<input class="form-control" size="15" placeholder="必填"
						type="text" v-model="VTWEG">
				</div>
			</div>
			<div class="row" style="margin-top:10px;">
				<div class="col-md-1" style="padding-left: 30px; padding-top: 8px">
					<span>品&nbsp;&nbsp;牌:</span>
				</div>
				<div class="col-md-2">
					<div class="form-group">  
				        <div class="input-group col-xs-12"> 
				            <input type="text" size="15" class="form-control input_spart" v-model="SPART" placeholder="非必填">  
				            <span class="input-group-btn">  
				                <select id="selectpicker" class="selectpicker" onchange="selectSpart()" multiple data-live-search="true" tabindex="-1"></select>  
				            </span>  
				        </div>  
				    </div>
				</div>
				<div class="col-md-1" style="padding-left: 30px; padding-top: 8px">
					售达方:
				</div>
				<div class="col-md-2">
					<input class="form-control" size="15" type="text" placeholder="非必填"
						v-model="KUNAG">
				</div>
				<div class="col-md-1" style="padding-left: 30px;">I&nbsp;M&nbsp;P<br/>票&nbsp;&nbsp;号:</div>
				<div class="col-md-2">
					<input class="form-control" size="15" type="text"
						v-model="VITUREINVOCE" placeholder="非必填">
				</div>
				<div class="col-md-1" style="padding-left: 30px; padding-top: 8px">付款方:</div>
				<div class="col-md-2">
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
		<!-- 更改发票的model -->
		<div class="modal fade" id="updateBillingmodel" tabindex="-1"
			role="dialog" aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">请更改发票</h4>
					</div>
					<div class="modal-body">
						<form style="margin-top: 10px">
							<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right;"><h4>序号:</h4></div>
								<div class="col-md-7">
									<label>{{ nowid }}</label>
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right;"><h4>品牌名称:</h4></div>
								<div class="col-md-7">
									<label>{{ nowspart }}</label>
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px;text-align: right;"><h4>售达方编码:</h4></div>
								<div class="col-md-7">
									<label>{{ nowkunag }}</label>
								</div>
							</div>
							<div class="row">
								<div class="col-md-4"
									style="padding-left: 30px;text-align: right;"><h4>售达方名称:</h4></div>
								<div class="col-md-7">
									<label>{{ nowname2 }}</label>
								</div>
							</div>
							<div class="row">
							 <div class="col-md-4"
									style="padding-left: 30px; text-align: right;"><h4>物料描述:</h4></div>
								<div class="col-md-7">
									<label>{{ nowarktx }}</label>
								</div>
							</div>
							<div class="row">
							 <div class="col-md-4 "
									style="padding-left: 30px; text-align: right;"><h4>公司编码:</h4></div>
								<div class="col-md-7">
									<label>{{ nowbukrs }}</label>
								</div>
							</div>
							<div class="row">
							 <div class="col-md-4 "
									style="padding-left: 30px;text-align: right;"><h4>税额:</h4></div>
								<div class="col-md-7">
									<input type="text" class="form-control" v-model ="nowtaxde"/>
								</div>
							</div>
							<div class="row">
							 <div class="col-md-4 "
									style="padding-left: 30px; text-align: right;"><h4>开票数量:</h4></div>
								<div class="col-md-7">
									<input type="text" class="form-control" v-model ="nowfkimg" />
								</div>
							</div>
							<div class="row">
							 <div class="col-md-4 "
									style="padding-left: 30px; text-align: right;"><h4>供货价:</h4></div>
								<div class="col-md-7">
									<input type="text" class="form-control" v-model ="nowghj"/>
								</div>
							</div>
							<div class="row">
							 <div class="col-md-4 "
									style="padding-left: 30px; text-align: right;"><h4>结算价:</h4></div>
								<div class="col-md-7">
									<input type="text" class="form-control" v-model ="nowjsj"/>
								</div>
							</div>
							<div class="row">
							 <div class="col-md-4 "
									style="padding-left: 30px;text-align: right;"><h4>成本价:</h4></div>
								<div class="col-md-7">
									<input type="text" class="form-control" v-model ="nowcbj"/>
								</div>
							</div>
							<div class="row">
							 <div class="col-md-4"
									style="padding-left: 30px; text-align: right;"><h4>含税单价:</h4></div>
								<div class="col-md-7">
									<input type="text" class="form-control" v-model ="nowhsdj"/>
								</div>
							</div>
							<div class="row">
							 <div class="col-md-4"
									style="padding-left: 30px; text-align: right;"><h4>含税总价:</h4></div>
								<div class="col-md-7">
									<input type="text" class="form-control" v-model ="nowhszj"/>
								</div>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<button type="button" class="btn btn-primary" data-dismiss="modal"
							v-on:click="saveupdatebilling">确定</button>
					</div>
				</div>
			</div>
		</div>

		<div style="margin-top: 10px" v-for="list in lists">
			<div class="row" style="margin-left: 20px">
				<input type="checkbox" class="col-md-1"/> <label
					class="col-md-1">IMP发票号:</label> <label class="col-md-2"
					style="color: orange;">{{list.vitureinvoce}}</label> <label
					class="col-md-1">开票日期: </label> <label class="col-md-2"
					style="color: orange;">{{list.merge_TIME}}</label> <label
					class="col-md-1"></label> <label class="col-md-1">发票状态: </label> <label
					class="col-md-1" style="color: orange;">已审核</label>
			</div>
			<table class="table table-bordered">
				<thead>
					<tr>
						<th>序号</th>
						<th>品牌名称</th>
						<th>售达方编码</th>
						<th>售达方名称</th>
						<th>物料描述</th>
						<th>公司编码</th>
						<th>税额</th>
						<th>开票数量</th>
						<th>供货价</th>
						<th>结算价</th>
						<th>成本价</th>
						<th>含税总价</th>
						<th>开票金额</th>
					</tr>
				</thead>
				<tbody>
					<tr v-for="ls in list.lists">
						<td>{{ ls.id }}</td>
						<td>{{ ls.spart }}</td>
						<td>{{ ls.kunag }}</td>
						<td>{{ ls.name2 }}</td>
						<td>{{ ls.arktx }}</td>
						<td>{{ ls.bukrs }}</td>
						<td>{{ ls.taxde }}</td>
						<td>{{ ls.fkimg }}</td>
						<td>{{ ls.ghj }}</td>
						<td>{{ ls.jsj }}</td>
						<td>{{ ls.cbj }}</td>
						<td>{{ ls.hsdj }}</td>
						<td>{{ ls.hszj }}</td>
					</tr>
				</tbody>
			</table>
			<div class="row" style="margin-left: 20px">
					<label class="col-md-1">合计:</label> <label class="col-md-2"
					style="color: orange;">{{ list.hj }}</label> <label class="col-md-1">整单折扣金额:</label>
				<label class="col-md-2" style="color: orange;">{{ list.zdzkje}}</label> <label
					class="col-md-1">成本率:</label> <label class="col-md-2"
					style="color: orange;">{{ list.cblb }}</label> <label class="col-md-1">折扣后价格:</label>
				<label class="col-md-2" style="color: orange;">{{ list.zkhjg }}</label>
			</div>
		</div>
		 <div id="loading" class="loading" v-show ="loadcontrol">程序执行中  请等待...</div>  
	</div>
</body>
<script type="text/javascript">
	var app = new Vue({
		el : '#auditbilling',
		data : {
			instart : null, //开始日期
			inend : null, //结束日期
			SAPBillingNo : null, //IMP虚拟开票号
			BillingStream : null, //发票流水号
			VKORG : null, //销售组织
			VTWEG : null, //分销渠道
			SPART : null, //品牌
			KUNAG : null, //售达方
			apiUrl : '/kans_file/kaipiao/searchMergeBillingAudit',
			exporturl : '/kans_file/kaipiao/downloadExcelDoBillingAuditGold',
			getsparturl : '/kans_file/kaipiao/selectSpart',
			mergeurl : '/kans_file/kaipiao/mergeBillings',
			saveupdateurl :'/kans_file/kaipiao/SaveUpdateByid',
			checkbillingstatusurl : '/kans_file/kaipiao/SaveupdateByVitureinvoce',
			rollbackbillingsurl :'/kans_file/kaipiao/roleBackAuditByVitureinvoce',
			uploadExcelUrl :'/kans_file/uploadExcel/importGold',
			exportaudityuanshiUrl:'/kans_file/kaipiao/downloadExcelDoBillingAuditAll',
			exportaudityuanshiUrl2:'/kans_file/kaipiao/downloadExcelShNotFd',
			exportauditzhijinUrl:'/kans_file/kaipiao/',
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
			VITUREINVOCE : null,
			KUNRG : null,
			nowid : null,
			nowspart : null,
			nowkunag : null,
			nowname2 : null,
			nowarktx : null,
			nowbukrs : null,
			nowtaxde : null,
			nowfkimg : null,
			nowghj : null,
			nowjsj : null,
			nowcbj : null,
			nowhsdj : null,
			nowhszj : null,
			saveupdaterequest : null,
			vitureinvocegroup : null,
			NEWVKORG : null,
			NEWVTWEG : null,
			NEWSPART : null,
			loadcontrol:false
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
			$("#choosestartquanbu").datetimepicker({
				format : 'yyyy-mm-dd',
				language : "zh-CN",
				weekStart : 1,
				todayBtn : 1,
				autoclose : 1,
				todayHighlight : 1,
				startView : 2,
				minView : 2
			});
			$("#chooseendquanbu").datetimepicker({
				format : 'yyyy-mm-dd',
				language : "zh-CN",
				weekStart : 1,
				todayBtn : 1,
				autoclose : 1,
				todayHighlight : 1,
				startView : 2,
				minView : 2
			});
			$("#choosestartquanbu2").datetimepicker({
				format : 'yyyy-mm-dd',
				language : "zh-CN",
				weekStart : 1,
				todayBtn : 1,
				autoclose : 1,
				todayHighlight : 1,
				startView : 2,
				minView : 2
			});
			$("#chooseendquanbu2").datetimepicker({
				format : 'yyyy-mm-dd',
				language : "zh-CN",
				weekStart : 1,
				todayBtn : 1,
				autoclose : 1,
				todayHighlight : 1,
				startView : 2,
				minView : 2
			});
			$("#choosestartzhijin").datetimepicker({
				format : 'yyyy-mm-dd',
				language : "zh-CN",
				weekStart : 1,
				todayBtn : 1,
				autoclose : 1,
				todayHighlight : 1,
				startView : 2,
				minView : 2
			});
			$("#chooseendzhijin").datetimepicker({
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
			function checkData(){
				  var fileDir = $("#upfile").val();  
	                var suffix = fileDir.substr(fileDir.lastIndexOf("."));  
	                if("" == fileDir){  
	                    alert("选择需要导入的Excel文件！");  
	                    return false;  
	                }  
	                if(".xls" != suffix && ".xlsx" != suffix ){  
	                    alert("选择Excel格式的文件导入！");  
	                    return false;  
	                }  
	                return true;  
			};
		},
		methods : {
			exportaudityuanshi:function(){
				this.exportstart = $("#choosestartquanbu").val();
				this.exportend = $("#chooseendquanbu").val();
				location.href = this.exportaudityuanshiUrl + "?" + "exportstart="
						+ this.exportstart + "&exportend=" + this.exportend;
			},
			
			exportaudityuanshi2:function(){
				this.exportstart = $("#choosestartquanbu2").val();
				this.exportend = $("#chooseendquanbu2").val();
				location.href = this.exportaudityuanshiUrl2 + "?" + "exportstart="
						+ this.exportstart + "&exportend=" + this.exportend;
			},
			exportauditzhijin:function(){
				
			},
			uploadExcel : function(){
				 document.getElementById("insertgold").submit();
			},
			checkbillingstatus : function(){
				var checkvitureinvoice = null ;
				$('input[type="checkbox"]:checked').each(function() {
					 if (checkvitureinvoice != null) {
						 checkvitureinvoice = checkvitureinvoice + "," + $(this).next().next().html().trim();
					} else {
						checkvitureinvoice = $(this).next().next().html().trim();
					} 
				});
				this.vitureinvocegroup =
					{
						'vitureinvocegroup':checkvitureinvoice
					};
					
				//alert(this.vitureinvocegroup);
				//发送post请求
				this.loadcontrol = true; 
				this.$http.post(this.checkbillingstatusurl, JSON.stringify(this.vitureinvocegroup),
						emulateJSON = false).then(function(data) {
					this.loadcontrol = false; 
					alert('审核开票成功');
				
					$('input[type="checkbox"]:checked').each(function() {
						$(this).parent().parent().remove();
					});

				}, function() {
					this.loadcontrol = false; 
					alert('审核开票失败'); //失败处理
				
				});
				
			},
			rollbackbillings : function(){
				var rollbackvitureinvoices = null ;
				$('input[type="checkbox"]:checked').each(function() {
					 if (rollbackvitureinvoices != null) {
						 rollbackvitureinvoices = rollbackvitureinvoices + "," + $(this).next().next().html().trim();
					} else {
						rollbackvitureinvoices = $(this).next().next().html().trim();
					} 
				});
				
				this.vitureinvocegroup =
				{
					'vitureinvocegroup':rollbackvitureinvoices
				};
				//发送post请求
				this.loadcontrol = true; 
				this.$http.post(this.rollbackbillingsurl, JSON.stringify(this.vitureinvocegroup),
						emulateJSON = false).then(function(data) {
					this.loadcontrol = false; 
					alert('撤销开票成功');
					/* $('input[type="checkbox"]:checked').each(function() {
						$(this).parent().parent().remove();
					}); */
					this.searchmergebilling();

				}, function() {
					this.loadcontrol = false;
					alert('撤销开票失败'); //失败处理
				});
				
			},
			trim :function(){
				  return this.replace(/(^\s*)|(\s*$)/g, ''); 
			},
			/* saveupdatebilling :function(){
				this.saveupdaterequest={
						ID:this.nowid.trim(),
						SPART:this.nowspart.trim(),
						KUNAG:this.nowkunag.trim(),
						NAME2:this.nowname2.trim(),
						ARKTX:this.nowarktx.trim(),
						BUKRS:this.nowbukrs.trim(),
						TAXDE:this.nowtaxde.trim(),
						FKIMG:this.nowfkimg.trim(),
						GHJ:this.nowghj.trim(),
						JSJ:this.nowjsj.trim(),
						CBJ:this.nowcbj.trim(),
						HSDJ:this.nowhsdj.trim(),
						HSZJ:this.nowhszj.trim()
				};
				//发送post请求
				this.$http.post(this.saveupdateurl, JSON.stringify(this.saveupdaterequest),
						emulateJSON = false).then(function(data) {
					alert('更改成功');
				}, function() {
					alert('更改失败'); 
				});	
			}, */
			
			
			mergebilling : function() {
				var j = null;
				$('input[type="checkbox"]:checked').each(function() {
					if (j != null) {
						j = j + "," + $(this).parent().next().html().trim();
					} else {
						j = $(this).parent().next().html().trim();
					}
				});
				this.mergeid = {
					"mergeid" : j
				};
				this.loadcontrol = true;
				//发送post请求
				this.$http.post(this.mergeurl, JSON.stringify(this.mergeid),
						emulateJSON = false).then(function(data) {
							this.loadcontrol = false;
							alert('开票成功');
					$('input[type="checkbox"]:checked').each(function() {
						$(this).parent().parent().remove();
					});

				}, function() {
					alert('开票失败'); //失败处理
				});

			},
			selectspartconfirm : function() {
				var i = null;
				$('input[type="checkbox"]:checked').each(function() {
					$(this).prop("checked", false);
					if (i != null) {
						i = i + "," + $(this).parent().next().html().trim();
					} else {
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
			},
			exportexcelbytime : function() {
				this.exportstart = $("#choosestart").val();
				this.exportend = $("#chooseend").val();
				this.NEWVKORG = $("#VKORGID").val();
				this.NEWVTWEG = $("#VTWEGID").val();
				this.NEWSPART = $("#SPARTID").val();
				
				location.href = this.exporturl + "?" + "exportstart="
						+ this.exportstart + "&exportend=" + this.exportend+"&VKORG="+this.NEWVKORG+"&VTWEG="+this.NEWVTWEG+"&SPART="+this.NEWSPART;
			},
			searchmergebilling : function() {
				this.instart = $("#StartTime").val();
				this.inend = $("#EndTime").val();
				if (this.instart.trim() && this.inend.trim() && this.VKORG.trim() && this.VTWEG.trim()) {
					this.request = {
						"VKORG" : this.VKORG,
						"VTWEG" : this.VTWEG,
						"SPART" : this.SPART,
						"KUNAG" : this.KUNAG,
						"KUNRG" : this.KUNRG,
						"timebegin" : this.instart,
						"timeend" : this.inend,
						"VITUREINVOCE" : this.VITUREINVOCE

					};
					this.loadcontrol = true;
					//发送post请求
					this.$http.post(this.apiUrl, JSON.stringify(this.request),
							emulateJSON = false).then(function(data) {
						this.loadcontrol = false;
						this.lists = data.body.lists;
						if (data.body.resultCode != 0) {
							$(".col-md-1").prop("checked", false);
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