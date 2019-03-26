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
	<div id="dobilling">
		<div style="margin-top: 10px; margin-left: 10px">
		     <button class="btn btn-warning btn-sm" type="submit" v-on:click="allselect" ><i class="fa fa-check-square" aria-hidden="true"></i> 全选</button>
			<span style="width: 10px"></span>
			<button class="btn btn-danger btn-sm" type="submit" v-on:click="checkbillingstatus"><i class="fa fa-check" aria-hidden="true"></i> 审核发票</button>
			<span style="width: 10px"></span>
			<button class="btn btn-warning btn-sm" type="submit" v-on:click="rollbackbillings" ><i class="fa fa-reply" aria-hidden="true"></i> 撤销开票</button>
			<span style="width: 10px"></span>
			<button class="btn btn-success btn-sm" type="button" data-toggle="modal" data-target="#myModal"
				><i class="fa fa-share-square-o" aria-hidden="true"></i> 导出开票后的Excel</button>
			<span style="width: 10px"></span>
			<button class="btn btn-success btn-sm" type="button" data-toggle="modal" data-target="#myModal1"
				><i class="fa fa-share-square-o" aria-hidden="true"></i> 导出校验的Excel</button>
			<span style="width: 10px"></span>
			<button class="btn btn-info btn-sm" type="button" data-toggle="modal" data-target="#myModal2"
				><i class="fa fa-share-square-o" aria-hidden="true"></i> 导出已开票未审核的Excel</button>
			<span style="width: 10px"></span>
			<button class="btn btn-primary btn-sm enter-submit" type="submit"
				v-on:click="searchmergebilling"><i class="fa fa-search" aria-hidden="true"></i> 查&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;询</button>
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
									<input type="text" class="form-control date" id="choosestart" placeholder="开票开始日期"  />
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height:32px;">结束时间:</div>
								<div class="col-md-7">
									<input type="text" class="form-control date" id="chooseend" placeholder="开票结束日期" />
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
						<button type="button" class="btn btn-primary" data-dismiss="modal"
							v-on:click="exportexcelbytime">执行导出</button>
					</div>
				</div>
			</div>
		</div>
	    <!-- Modal -->
		<div class="modal fade" id="myModal1" tabindex="-1" role="dialog"
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
									<input type="text" class="form-control date" id="choosestart1" placeholder="开票开始日期"  />
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height:32px;">结束时间:</div>
								<div class="col-md-7">
									<input type="text" class="form-control date" id="chooseend1" placeholder="开票结束日期" />
								</div>
							</div>
								<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height:32px;">销售组织:</div>
								<div class="col-md-7">
									<input type="text" class="form-control"  id ="VKORGID1"  placeholder="例如:6300"  />
								</div>
							</div>
								<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height:32px;">分销渠道:</div>
								<div class="col-md-7">
									<input type="text" class="form-control"  id = "VTWEGID1"  placeholder="例如:10"  />
								</div>
							</div>
								<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height:32px;">品牌:
									</div>
								<div class="col-md-7">
									<input type="text" class="form-control" id ="SPARTID1"  placeholder="例如:01,02,03"  />
								</div>
							</div>
					  </form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<button type="button" class="btn btn-primary" data-dismiss="modal"
							v-on:click="exportexcelbytime1">执行导出</button>
					</div>
				</div>
			</div>
		</div>
		<div class="modal fade" id="myModal2" tabindex="-1" role="dialog"
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
									<input type="text" class="form-control date" id="choosestart2" placeholder="开票开始日期"  />
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height:32px;">结束时间:</div>
								<div class="col-md-7">
									<input type="text" class="form-control date" id="chooseend2" placeholder="开票结束日期" />
								</div>
							</div>
								<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height:32px;">销售组织:</div>
								<div class="col-md-7">
									<input type="text" class="form-control"  id ="VKORGID2"  placeholder="例如:6300"  />
								</div>
							</div>
								<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height:32px;">分销渠道:</div>
								<div class="col-md-7">
									<input type="text" class="form-control"  id = "VTWEGID2"  placeholder="例如:10"  />
								</div>
							</div>
								<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height:32px;">品牌:
									</div>
								<div class="col-md-7">
									<input type="text" class="form-control" id ="SPARTID2"  placeholder="例如:01,02,03"  />
								</div>
							</div>
					  </form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<button type="button" class="btn btn-primary" data-dismiss="modal"
							v-on:click="exportexcelbytime2">执行导出</button>
					</div>
				</div>
			</div>
		</div>
		<form style="margin: 10px 10px 0 0;" name="query-region">
			<div class="row">
				<div class="col-md-1 col-sm-1 col-xs-12" style="padding-left: 30px; padding-top: 8px">StartTime:</div>
				<div class="col-md-2 col-sm-2 col-xs-12 bootstrap-timepicker">
					<input type="text" class="form-control date" id="StartTime" placeholder="必填"  />
				</div>
				<div class="col-md-1 col-sm-1 col-xs-12" style="padding-left: 30px; padding-top: 8px">EndTime</div>
				<div class="col-md-2 col-sm-2 col-xs-12 control-label">
					<input type="text" class="form-control date" id="EndTime" placeholder="必填"  />
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
					<span>品牌:</span>
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
				<div class="col-md-1 col-sm-1 col-xs-12" style="padding-left: 30px; padding-top: 8px">付款方:</div>
				<div class="col-md-2 col-sm-2 col-xs-12">
					<input class="form-control" size="15" type="text" placeholder="非必填"
						v-model="KUNRG">
				</div>
				<div class="col-md-1 col-sm-1 col-xs-12" style="padding-left: 30px; padding-top: 8px">IMP发票号:</div>
				<div class="col-md-2 col-sm-2 col-xs-12">
					<input class="form-control" size="15" type="text" placeholder="非必填"
						v-model="impnum">
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
						<form style="margin-top: 10px" class="form-style">
							<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height:32px;"><h4>序号:</h4></div>
								<div class="col-md-7">
									  <input type="text" class="form-control" v-model ="nowid" readonly/>
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height:32px;"><h4>销售组织:</h4></div>
								<div class="col-md-7">
									  <input type="text" class="form-control" v-model ="nowvkorg" readonly/>
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px;text-align: right; line-height:32px;"><h4>渠道:</h4></div>
								<div class="col-md-7">
									  <input type="text" class="form-control" v-model ="nowvtweg" readonly/>
								</div>
							</div>
							<div class="row">
								<div class="col-md-4"
									style="padding-left: 30px;text-align: right; line-height:32px;"><h4>品牌代码:</h4></div>
								<div class="col-md-7">
									   <input type="text" class="form-control" v-model ="nowspart" readonly/>
								</div>
							</div>
							<div class="row">
							 <div class="col-md-4"
									style="padding-left: 30px; text-align: right; line-height:32px;"><h4>付款方名称:</h4></div>
								<div class="col-md-7">
								    <input type="text" class="form-control" v-model ="nowname2" readonly/>
								</div>
							</div>
							<div class="row">
							 <div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height:32px;"><h4>物料编码:</h4></div>
								<div class="col-md-7">
								<input type="text" class="form-control" v-model ="nowmatnr" readonly/>
								</div>
							</div>
							<div class="row">
							 <div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height:32px;"><h4>物料描述:</h4></div>
								<div class="col-md-7">
								<input type="text" class="form-control" v-model ="nowarktx" readonly/>
								</div>
							</div>
							<div class="row">
							 <div class="col-md-4"
									style="padding-left: 30px;text-align: right; line-height:32px;"><h4>税额:</h4></div>
								<div class="col-md-7">
									<input type="text" class="form-control" v-model ="nowtaxde"/>
								</div>
							</div>
							<div class="row">
							 <div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height:32px;"><h4>开票数量:</h4></div>
								<div class="col-md-7">
									<input type="text" class="form-control" v-model ="nowfkimg" />
								</div>
							</div>
							<div class="row">
							 <div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height:32px;"><h4>供货价:</h4></div>
								<div class="col-md-7">
									<input type="text" class="form-control" v-model ="nowghj"/>
								</div>
							</div>
							<div class="row">
							 <div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height:32px;"><h4>结算价:</h4></div>
								<div class="col-md-7">
									<input type="text" class="form-control" v-model ="nowjsj"/>
								</div>
							</div>
							<div class="row">
							 <div class="col-md-4 "
									style="padding-left: 30px;text-align: right; line-height:32px;"><h4>成本价:</h4></div>
								<div class="col-md-7">
									<input type="text" class="form-control" v-model ="nowcbj"/>
								</div>
							</div>
							<div class="row">
							 <div class="col-md-4"
									style="padding-left: 30px; text-align: right; line-height:32px;"><h4>含税单价:</h4></div>
								<div class="col-md-7">
									<input type="text" class="form-control" v-model ="nowhsdj"/>
								</div>
							</div>
							<div class="row">
							 <div class="col-md-4"
									style="padding-left: 30px; text-align: right; line-height:32px;"><h4>含税总价:</h4></div>
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
				  <input type="checkbox" class="col-md-1 mycheck" /> 
				   <label class="col-md-1">IMP发票头:</label>
				   
					<label class="col-md-1" style="color: orange;">{{list.billingbase}}</label>
				   <label class="col-md-1">付款方编码:</label>
				   
					<label class="col-md-1" style="color: orange;">{{list.kunrg}}</label>
				   <label
					class="col-md-1">IMP发票号:</label> <label class="col-md-1"
					style="color: orange;">{{list.billingno}}</label>
					 <label
					class="col-md-1">开票日期: </label> <label class="col-md-1"
					style="color: orange;">{{list.merge_TIME}}</label> 
					
					<label class="col-md-1">发票状态:</label>
					<label class="col-md-1" style="color: orange;">未审核</label>
			</div>
			<table class="table table-bordered">
				<thead>
					<tr>
						<th>序号</th>
						<th>销售组织</th>
						<th>渠道</th>
						<th>品牌代码</th>
						<th>付款方名称</th>
						<th>物料编码</th>
						<th>物料描述</th>
						<th>税额</th>
						<th>开票数量</th>
						<th>供货价</th>
						<th>结算价</th>
						<th>成本价</th>
						<th>含税单价</th>
						<th>含税总价</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
					<tr v-for="ls in list.billings">
						<td>{{ ls.id }}</td>
						<td>{{ ls.vkorg }}</td>
						<td>{{ ls.vtweg }}</td>
						<td>{{ ls.spart }}</td>
						<td>{{ ls.name2 }}</td>
						<td>{{ ls.matnr }}</td>
						<td>{{ ls.arktx }}</td>
						<td>{{ ls.taxde }}</td>
						<td>{{ ls.fkimg }}</td>
						<td>{{ ls.ghj }}</td>
						<td>{{ ls.jsj }}</td>
						<td>{{ ls.cbj }}</td>
						<td>{{ ls.hsdj }}</td>
						<td>{{ ls.hszj }}</td>
						<td><button class="btn btn-warning" type="button"
								data-target="#updateBillingmodel" data-toggle="modal" v-on:click="updatebilling">修改发票</button></td>
					</tr>
				</tbody>
			</table>
			<div class="row" style="margin-left: 20px">
				<label class="col-md-1">应开总价:</label><label class="col-md-2"
					style="color: orange;">{{ list.sum }}</label> <label class="col-md-1"></label>
				<label class="col-md-2" style="color: orange;"></label> <label
					class="col-md-1"></label> <label class="col-md-2"
					style="color: orange;"></label> <label class="col-md-1"></label>
				<label class="col-md-2" style="color: orange;"></label>
			</div> 
		</div>
		<div id="loading" class="loading" v-show ="loadcontrol">程序执行中  请等待...</div>  
	</div>
</body>
<script type="text/javascript">
	var app = new Vue({
		el : '#dobilling',
		data : {
			instart : null, //开始日期
			inend : null, //结束日期
			SAPBillingNo : null, //IMP虚拟开票号
			BillingStream : null, //发票流水号
			VKORG : null, //销售组织
			VTWEG : null, //分销渠道
			SPART : null, //品牌
			KUNAG : null, //售达方
			apiUrl : '/kans_file/kaipiao/getOffBillingsNewMerge',
		    exporturl :'/kans_file/kaipiao/downloadExcelDoBillingAudit',
		    exporturl1 : '/kans_file/kaipiao/downloadExcelDoBilling', 
		    exporturl2 : '/kans_file/kaipiao/downloadKpNotSh', 
			getsparturl : '/kans_file/kaipiao/selectSpart',
			mergeurl : '/kans_file/kaipiao/mergeBillings',
			saveupdateurl :'/kans_file/kaipiao/SaveUpdateByid',
			checkbillingstatusurl : '/kans_file/kaipiao/SaveupdateByVitureinvoce',
			rollbackbillingsurl :'/kans_file/kaipiao/roleBackByVitureinvoce',
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
			nowid : null,//ID号
			nowvkorg :null,//销售组织
			nowvtweg :null,//销售渠道
			nowspart :null,//品牌代码
			nowname2 :null,//付款方描述
		    nowmatnr :null,//物料号
			nowarktx :null,//物料描述
			nowtaxde : null,//税额
			nowfkimg : null,//开票数量
			nowghj : null,//供货价
			nowjsj : null,//结算及
			nowcbj : null,//成本价
			nowhsdj : null,//含税单价
			nowhszj : null,//含税总价
			saveupdaterequest : null,
			vitureinvocegroup : null,
			NEWVKORG : null,
			NEWVTWEG : null,
			NEWSPART : null,
			loadcontrol:false,
			impnum:null
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
			$("#choosestart1").datetimepicker({
				format : 'yyyy-mm-dd',
				language : "zh-CN",
				weekStart : 1,
				todayBtn : 1,
				autoclose : 1,
				todayHighlight : 1,
				startView : 2,
				minView : 2
			});
			$("#chooseend1").datetimepicker({
				format : 'yyyy-mm-dd',
				language : "zh-CN",
				weekStart : 1,
				todayBtn : 1,
				autoclose : 1,
				todayHighlight : 1,
				startView : 2,
				minView : 2
			});
			$("#choosestart2").datetimepicker({
				format : 'yyyy-mm-dd',
				language : "zh-CN",
				weekStart : 1,
				todayBtn : 1,
				autoclose : 1,
				todayHighlight : 1,
				startView : 2,
				minView : 2
			});
			$("#chooseend2").datetimepicker({
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
			updatebilling : function(e){
				var m =e.target;
				this.nowhszj= $(m).parent().prev().html();
				this.nowhsdj= $(m).parent().prev().prev().html();
				this.nowcbj=  $(m).parent().prev().prev().prev().html();
			    this.nowjsj=  $(m).parent().prev().prev().prev().prev().html();
			    this.nowghj=  $(m).parent().prev().prev().prev().prev().prev().html();
			    this.nowfkimg=$(m).parent().prev().prev().prev().prev().prev().prev().html();
			    this.nowtaxde=$(m).parent().prev().prev().prev().prev().prev().prev().prev().html();
			    this.nowarktx=$(m).parent().prev().prev().prev().prev().prev().prev().prev().prev().html();
			    this.nowmatnr=$(m).parent().prev().prev().prev().prev().prev().prev().prev().prev().prev().html();
			    this.nowname2=$(m).parent().prev().prev().prev().prev().prev().prev().prev().prev().prev().prev().html();
			    this.nowspart=$(m).parent().prev().prev().prev().prev().prev().prev().prev().prev().prev().prev().prev().html();    
			    this.nowvtweg=$(m).parent().prev().prev().prev().prev().prev().prev().prev().prev().prev().prev().prev().prev().html();  
			    this.nowvkorg=$(m).parent().prev().prev().prev().prev().prev().prev().prev().prev().prev().prev().prev().prev().prev().html(); 
			    this.nowid=   $(m).parent().prev().prev().prev().prev().prev().prev().prev().prev().prev().prev().prev().prev().prev().prev().html(); 
			},
			checkbillingstatus : function(){
				var checkvitureinvoice = null ;
				$('input[type="checkbox"]:checked').each(function() {
					 if (checkvitureinvoice != null) {
						 checkvitureinvoice = checkvitureinvoice + "," + $(this).next().next().html().trim()+"#"+ $(this).next().next().next().next().html().trim();
					} else {
						checkvitureinvoice = $(this).next().next().html().trim()+"#"+ $(this).next().next().next().next().html().trim();
					} 
				});
				this.vitureinvocegroup =
					{
						'vitureinvocegroup':checkvitureinvoice
					};
				//发送post请求
				this.loadcontrol = true; 
				this.$http.post(this.checkbillingstatusurl, JSON.stringify(this.vitureinvocegroup),
						emulateJSON = false).then(function(data) {
							this.loadcontrol = false; 
							alert('审核开票成功');
					/* $('input[type="checkbox"]:checked').each(function() {
						$(this).parent().parent().remove();
					}); */
				this.searchmergebilling();
				}, function() {
					this.loadcontrol = false; 
					alert('审核开票失败'); //失败处理
				});
				
			},
			rollbackbillings : function(){
				var rollbackvitureinvoices = null ;
				$('input[type="checkbox"]:checked').each(function() {
					 if (rollbackvitureinvoices != null) {
						 rollbackvitureinvoices = rollbackvitureinvoices + "," + $(this).next().next().html().trim()+'#'+$(this).next().next().next().next().html().trim();
					 } else {
						rollbackvitureinvoices = $(this).next().next().html().trim()+'#'+$(this).next().next().next().next().html().trim();
					 } 
				});
				
				this.vitureinvocegroup =
				{
					'vitureinvocegroup':rollbackvitureinvoices
				};
				//alert(rollbackvitureinvoices);
				//发送post请求
				this.loadcontrol = true; 
				this.$http.post(this.rollbackbillingsurl, JSON.stringify(this.vitureinvocegroup),
						emulateJSON = false).then(function(data) {
							this.loadcontrol = false; 
							alert('撤销开票成功');
							this.lists =null;
					/* $('input[type="checkbox"]:checked').each(function() {
						$(this).parent().parent().remove();
					});
 */
				}, function() {
					this.loadcontrol = false;
					alert('撤销开票失败'); //失败处理
				});
				
			},
			trim :function(){
				  return this.replace(/(^\s*)|(\s*$)/g, ''); 
			},
			saveupdatebilling :function(){
				this.saveupdaterequest={
						SPART:this.nowspart.trim(),
						ID:this.nowid.trim(),
						TAXDE:this.nowtaxde.trim(),
						FKIMG:this.nowfkimg.trim(),
						GHJ:this.nowghj.trim(),
						JSJ:this.nowjsj.trim(),
						CBJ:this.nowcbj.trim(),
						HSDJ:this.nowhsdj.trim(),
						HSZJ:this.nowhszj.trim()
				};
				//发送post请求
				this.loadcontrol = true; 
				this.$http.post(this.saveupdateurl, JSON.stringify(this.saveupdaterequest),
						emulateJSON = false).then(function(data) {
							this.loadcontrol = false; 
							alert('更改成功');
				}, function() {
					this.loadcontrol = false;
					alert('更改失败'); 
				});	
			},
			
			
		/* 	mergebilling : function() {
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

				//发送post请求
				this.$http.post(this.mergeurl, JSON.stringify(this.mergeid),
						emulateJSON = false).then(function(data) {
					alert('开票成功');
					$('input[type="checkbox"]:checked').each(function() {
						$(this).parent().parent().remove();
					});

				}, function() {
					alert('开票失败'); //失败处理
				});

			}, */
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
				this.NEWVKORG = $("#VKORGID").val();
				this.NEWVTWEG = $("#VTWEGID").val();
				this.NEWSPART = $("#SPARTID").val();
				
				location.href = this.exporturl + "?" + "exportstart="
						+ this.exportstart + "&exportend=" + this.exportend+"&VKORG="+this.NEWVKORG+"&VTWEG="+this.NEWVTWEG+"&SPART="+this.NEWSPART;
			},
			exportexcelbytime1 : function() {
				this.exportstart = $("#choosestart1").val();
				this.exportend = $("#chooseend1").val();
				this.NEWVKORG = $("#VKORGID1").val();
				this.NEWVTWEG = $("#VTWEGID1").val();
				this.NEWSPART = $("#SPARTID1").val();
				
				location.href = this.exporturl1 + "?" + "exportstart="
						+ this.exportstart + "&exportend=" + this.exportend+"&VKORG="+this.NEWVKORG+"&VTWEG="+this.NEWVTWEG+"&SPART="+this.NEWSPART;
			},
			exportexcelbytime2 : function() {
				this.exportstart = $("#choosestart2").val();
				this.exportend = $("#chooseend2").val();
				this.NEWVKORG = $("#VKORGID2").val();
				this.NEWVTWEG = $("#VTWEGID2").val();
				this.NEWSPART = $("#SPARTID2").val();
				
				location.href = this.exporturl2 + "?" + "exportstart="
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
						"KUNRG" : this.KUNRG,
						"timebegin" : this.instart,
						"timeend" : this.inend,
						"IMPNUM" : this.impnum
					};
					//发送post请求
					this.loadcontrol = true; 
					this.$http.post(this.apiUrl, JSON.stringify(this.request),
							emulateJSON = false).then(function(data) {
								this.loadcontrol = false; 
								this.lists = data.body.lists;		
						if (data.body.resultCode != 0) {
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