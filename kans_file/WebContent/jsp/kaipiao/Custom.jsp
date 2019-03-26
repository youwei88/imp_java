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
.form-style>.row:not(:first-child){
	margin-top: 10px;
}
</style>
</head>
<body>
	<div id="customer">
		<div style="margin: 10px 10px 10px 0;" class="row">
			<div class="col-md-2 col-sm-2 col-xs-12">
				<form method="post"
					action="/kans_file/uploadExcel/importCustomer"
					enctype="multipart/form-data" id="insertcustomer">
					<input id="upfile" type="file" name="upfile" />
				</form>
			</div>
			<div class="col-md-1 col-sm-1 col-xs-12">
				<Button class="btn btn-success btn-sm" v-on:click="insertcustomerclick"><i class="fa fa-sign-in" aria-hidden="true"></i> 导入客户数据</Button>
			</div>
			<div class="col-md-1 col-sm-1 col-xs-12">
				<a class="btn btn-warning btn-sm" href="/kans_file/kaipiao/exportCustomer"><i class="fa fa-share-square-o" aria-hidden="true"></i> 导出客户数据</a>
			</div>
			<!-- <div class="col-md-1">
				<span style="width: 10px"></span>
				<button class="btn btn-success">导出Excel</button>
			</div> -->
			<div class="col-md-2 col-sm-2 col-xs-12">
				<input type="text"
					class="form-control input-sm" name="KUNRG" v-model="KUNRG" placeholder="付款方  (例:108630)" />
			</div>
			<div class="col-md-2 col-sm-2 col-xs-12">
				<input type="text"
					class="form-control input-sm" name="NAME2" v-model="NAME2" placeholder="开票公司名称  (例:上美公司)" />
			</div>
			<div class="col-md-1 col-sm-1 col-xs-12">
				<Button class="btn btn-primary btn-sm" data-toggle="modal"
					data-target="#myModal"><i class="fa fa-plus" aria-hidden="true"></i> 新&nbsp;&nbsp;&nbsp;&nbsp;增</button>
			</div>
			<div class="col-md-1 col-sm-1 col-xs-12">
				<button class="btn btn-primary btn-sm enter-submit" v-on:click ="searchcustom">
					<i class="fa fa-search" aria-hidden="true">
					</i> 查&nbsp;&nbsp;&nbsp;&nbsp;询</button>
			</div>
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
						<h4 class="modal-title" id="myModalLabel">客户信息</h4>
					</div>
					<div class="modal-body">
						<form style="margin-top: 10px" class="form-style">
							<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height:32px;">品牌:</div>
								<div class="col-md-7">
									<input type="text" class="form-control" placeholder="例如:01"
										v-model="SPART" />
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height:32px;">销售组织:</div>
								<div class="col-md-7">
									<input type="text" class="form-control" placeholder="例如:6300"
										v-model="VKORG" />
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height:32px;">客户代码:</div>
								<div class="col-md-7">
									<input type="text" class="form-control"
										placeholder="例如:100891" v-model="KUNRG" />
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height:32px;">客户简称:</div>
								<div class="col-md-7">
									<input type="text" class="form-control"
										placeholder="例如:广州索悠纳贸易有限公司" v-model="NAME2" />
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height:32px;">客户名称:</div>
								<div class="col-md-7">
									<input type="text" class="form-control"
										placeholder="例如:广州索悠纳贸易有限公司" v-model="COMNAME" />
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height:32px;">税号:</div>
								<div class="col-md-7">
									<input type="text" class="form-control"
										placeholder="例如:91370112306891096T" v-model="PAYINGNUM" />
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height:32px;">地址电话:</div>
								<div class="col-md-7">
									<input type="text" class="form-control"
										placeholder="例如:湛江市麻章区麻章镇麻海路53号" v-model="ADDRESS" />
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height:32px;">开户行:</div>
								<div class="col-md-7">
									<input type="text" class="form-control"
										placeholder="例如:广发银行股份有限公司湛江 开发区支行" v-model="BANK" />
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height:32px;">银行账号:</div>
								<div class="col-md-7">
									<input type="text" class="form-control"
										placeholder="例如:9550880200789000111" v-model="BANKNUM" />
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height:32px;">收件人:</div>
								<div class="col-md-7">
									<input type="text" class="form-control" placeholder="例如:李四"
										v-model="PEOPLE" />
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height:32px;">收件地址:</div>
								<div class="col-md-7">
									<input type="text" class="form-control" placeholder="例如:XX路XX号"
										v-model="BILLINGADDRESS" />
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height:32px;">电话:</div>
								<div class="col-md-7">
									<input type="text" class="form-control"
										placeholder="例如:0221-88888888" v-model="PHONE" />
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right; line-height:32px;">发票类型:</div>
								<div class="col-md-7">
									<input type="text" class="form-control" placeholder="例如:0或者1"
										v-model="BILLINGTYPE" />
								</div>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<button type="button" class="btn btn-primary" data-dismiss="modal"
							v-on:click="savecustom">确定</button>
					</div>
				</div>
			</div>
		</div>
		<div class="table-responsive">
			<table class="table table-bordered table-striped ">
				<thead>
					<tr>
					    <th>操作</th>
					    <!-- <th>审核操作</th> -->
						<th>序号</th>
						<th>销售组织</th>
						<th>品牌号</th>
						<th>付款方</th>
						<th>付款方名称</th>
						<th>开票公司名称</th>
						<th>纳税人识别号</th>
						<th>地址电话</th>
						<th>开户行</th>
						<th>开户行账号</th>
						<th>发票快递地址</th>
						<th>签收人联系电话</th>
						<th>发票快递签收人</th>
						<th>开票类型</th>
						
					</tr>
				</thead>
				<tbody v-for="custom in CUSTOMLIST" id="sparttable">
					<tr>
					    <td><button class="btn btn-warning" type="button"
								data-target="#updateBillingmodel" data-toggle="modal" v-on:click="updatebilling">修改付款方</button></td>
						<!-- <td ><button class="btn btn-danger"  type="button" v-if=" custom.auditstatus == 0" v-on:click="doaudit">执行审核</button></td> -->
						<td>{{ custom.id }}</td>
						<td>{{ custom.vkorg }}</td>
						<td>{{ custom.spart }}</td>
						<td>{{ custom.kunrg }}</td>
						<td>{{ custom.name2 }}</td>
						<td>{{ custom.comname }}</td>
						<td>{{ custom.payingnum }}</td>
						<td>{{ custom.address }}</td>
						<td>{{ custom.bank }}</td>
						<td>{{ custom.banknum }}</td>
						<td>{{ custom.billingaddress }}</td>
						<td>{{ custom.phone }}</td>
						<td>{{ custom.people }}</td>
						<td>{{ custom.billingtype }}</td>
						
					</tr>
				</tbody>
			</table>
		</div>
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
						<h4 class="modal-title">请更改付款方信息</h4>
					</div>
					<div class="modal-body">
						<form style="margin-top: 10px">
							<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right;"><h4>序号:</h4></div>
								<div class="col-md-7">
									<input type="text" class="form-control" v-model ="NOWid" readonly/>
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right;"><h4>销售组织:</h4></div>
								<div class="col-md-7">
										<input type="text" class="form-control" v-model ="NOWVKORG" readonly/>
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px; text-align: right;"><h4>品牌号码:</h4></div>
								<div class="col-md-7">
									<input type="text" class="form-control" v-model ="NOWSPART" readonly/>
								</div>
							</div>
							<div class="row">
								<div class="col-md-4 "
									style="padding-left: 30px;text-align: right;"><h4>付款方编码:</h4></div>
								<div class="col-md-7">
								    <input type="text" class="form-control" v-model ="NOWKUNRG"/>
								</div>
							</div>
							<div class="row">
								<div class="col-md-4"
									style="padding-left: 30px;text-align: right;"><h4>付款方名称:</h4></div>
								<div class="col-md-7">
								 <input type="text" class="form-control" v-model ="NOWNAME2"/>
								</div>
							</div>
							<div class="row">
								<div class="col-md-4"
									style="padding-left: 30px;text-align: right;"><h4>开票公司名称:</h4></div>
								<div class="col-md-7">
							
								<input type="text" class="form-control"  v-model ="NOWCOMNAME" />
								</div>
							</div>
							<div class="row">
							 <div class="col-md-4"
									style="padding-left: 30px; text-align: right;"><h4>纳税人识别号:</h4></div>
								<div class="col-md-7">
									<input type="text" class="form-control" v-model ="NOWPAYINGNUM"/>
								</div>
							</div>
							<div class="row">
							 <div class="col-md-4"
									style="padding-left: 30px; text-align: right;"><h4>地址电话:</h4></div>
								<div class="col-md-7">
									<input type="text" class="form-control" v-model ="NOWADDRESS"/>
								</div>
							</div>
							<div class="row">
							 <div class="col-md-4 "
									style="padding-left: 30px;text-align: right;"><h4>开户行:</h4></div>
								<div class="col-md-7">
									<input type="text" class="form-control" v-model ="NOWBANK"/>
								</div>
							</div>
							<div class="row">
							 <div class="col-md-4 "
									style="padding-left: 30px; text-align: right;"><h4>开户行账号:</h4></div>
								<div class="col-md-7">
									<input type="text" class="form-control" v-model ="NOWBANKNUM" />
								</div>
							</div>
							<div class="row">
							 <div class="col-md-4 "
									style="padding-left: 30px; text-align: right;"><h4>发票快递地址:</h4></div>
								<div class="col-md-7">
									<input type="text" class="form-control" v-model ="NOWBILLINGADDRESS"/>
								</div>
							</div>
							<div class="row">
							 <div class="col-md-4 "
									style="padding-left: 30px; text-align: right;"><h4>签收人联系电话:</h4></div>
								<div class="col-md-7">
									<input type="text" class="form-control" v-model ="NOWPHONE"/>
								</div>
							</div>
							<div class="row">
							 <div class="col-md-4 "
									style="padding-left: 30px;text-align: right;"><h4>发票快递签收人:</h4></div>
								<div class="col-md-7">
									<input type="text" class="form-control" v-model ="NOWPEOPLE"/>
								</div>
							</div>
							<div class="row">
							 <div class="col-md-4"
									style="padding-left: 30px; text-align: right;"><h4>开票类型:</h4></div>
								<div class="col-md-7">
									<input type="text" class="form-control" v-model ="NOWBILLINGTYPE"/>
								</div>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<button type="button" class="btn btn-primary" data-dismiss="modal"
							v-on:click="updatecusbyid">确定</button>
					</div>
				</div>
			</div>
		</div>
		 <div id="loading" class="loading" v-show ="loadcontrol">程序执行中  请等待...</div>  
	</div>
	<script type="text/javascript">
		var app = new Vue({
			el : '#customer',
			data : {
				id    : null,
				VKORG : null, //销售组织 
				SPART : null, //品牌
				KUNRG : null, //付款方代码
				NAME2 : null, //付款方名称
				COMNAME : null, //开票公司名称
				PAYINGNUM : null, //纳税人识别号
				ADDRESS : null, // 地址
				BANK : null, //银行
				BANKNUM : null, //银行账号
				BILLINGADDRESS : null, //发票地址
				PHONE : null, //电话
				PEOPLE : null, //签收人 
				BILLINGTYPE : null,//发票类型
				savecustomurl : "/kans_file/kaipiao/insertCustomer",//保存客户URl
				searchcustomurl :  "/kans_file/kaipiao/searchCustomers",//查询客户URl
				updatecustomurl :  "/kans_file/kaipiao/upcustomerbyid",
/* 				auditurl :"/kans_file/kaipiao/auditcustomer", */
				saverequest : null,
				resultcode : null,
				CUSTOMLIST : null,
				searchrequest : null,
				NOWid :null,
				NOWVKORG:null,
				NOWSPART :null,
				NOWKUNRG :null,
				NOWNAME2 :null,
				NOWCOMNAME :null,
				NOWPAYINGNUM :null,
				NOWADDRESS :null,
				NOWBANK :null,
				NOWBANKNUM :null,
				NOWBILLINGADDRESS:null,
				NOWPHONE:null,
				NOWPEOPLE:null,
				NOWBILLINGTYPE:null,
				uprequest :null,
				loadcontrol:false,
				AUDITID :null,
				AUDITREQUEST :null
			},
			mounted : {

			},
			methods : {
				insertcustomerclick : function() {
					document.getElementById("insertcustomer").submit();
				},
				 updatebilling : function(e){

					var m =e.target;
					this.NOWid=       $(m).parent().next().html();
					this.NOWVKORG=    $(m).parent().next().next().html();
					this.NOWSPART=    $(m).parent().next().next().next().html();
					this.NOWKUNRG =   $(m).parent().next().next().next().next().html();
					this.NOWNAME2 =	  $(m).parent().next().next().next().next().next().html();
					this.NOWCOMNAME = $(m).parent().next().next().next().next().next().next().html();
					this.NOWPAYINGNUM =	$(m).parent().next().next().next().next().next().next().next().html();
					this.NOWADDRESS =	$(m).parent().next().next().next().next().next().next().next().next().html();
				    this.NOWBANK  =         $(m).parent().next().next().next().next().next().next().next().next().next().html();
				    this.NOWBANKNUM =       $(m).parent().next().next().next().next().next().next().next().next().next().next().html();
				    this.NOWBILLINGADDRESS =$(m).parent().next().next().next().next().next().next().next().next().next().next().next().html();
				    this.NOWPHONE =         $(m).parent().next().next().next().next().next().next().next().next().next().next().next().next().html();
				    this.NOWPEOPLE =         $(m).parent().next().next().next().next().next().next().next().next().next().next().next().next().next().html();
				    this.NOWBILLINGTYPE = $(m).parent().next().next().next().next().next().next().next().next().next().next().next().next().next().next().html();
				}, 
/* 				doaudit : function(e){
					var audit =e.target;
					this.AUDITID =  $(audit).parent().next().html();
					this.AUDITREQUEST ={
						id:this.AUDITID
					};
					this.loadcontrol = true; 
					this.$http.post(this.auditurl,
							JSON.stringify(this.AUDITREQUEST),
							emulateJSON = false).then(function(data) {
								this.loadcontrol = false; 
							    alert("审核成功");
					}, function() {
						this.loadcontrol = false;
						alert('审核失败');
					});
					
				}, */
				updatecusbyid :function(){
					this.uprequest ={
							"id":this.NOWid,
							"VKORG":this.NOWVKORG,
							"SPART":this.NOWSPART,
							"KUNRG":this.NOWKUNRG,
							"NAME2":this.NOWNAME2,
							"COMNAME":this.NOWCOMNAME,
							"PAYINGNUM":this.NOWPAYINGNUM,
							"ADDRESS" :this.NOWADDRESS,
							"BANK" :this.NOWBANK,
							"BANKNUM":this.NOWBANKNUM,
							"BILLINGADDRESS":this.NOWBILLINGADDRESS,
							"PHONE": this.NOWPHONE ,
							"PEOPLE":this.NOWPEOPLE,
							"BILLINGTYPE":this.NOWBILLINGTYPE
					};
					
					debugger;
					this.loadcontrol = true; 
					this.$http.post(this.updatecustomurl,
							JSON.stringify(this.uprequest),
							emulateJSON = false).then(function(data) {
								this.loadcontrol = false; 
						this.resultcode = data.body.resultcode;
						if (this.resultcode == 1) {
							alert("更新客户成功");
						}
					}, function() {
						this.loadcontrol = false;
						alert('更新客户失败');
					});
				},
				
				savecustom : function() {
					this.saverequest = {
						"VKORG" : this.VKORG,
						"SPART" : this.SPART,
						"KUNRG" : this.KUNRG,
						"NAME2" : this.NAME2,
						"COMNAME" : this.COMNAME,
						"PAYINGNUM" : this.PAYINGNUM,
						"ADDRESS" : this.ADDRESS,
						"BANK" : this.BANK,
						"BANKNUM" : this.BANKNUM,
						"BILLINGADDRESS" : this.BILLINGADDRESS,
						"PHONE" : this.PHONE,
						"PEOPLE" : this.PEOPLE,
						"BILLINGTYPE" : this.BILLINGTYPE
					};
					this.loadcontrol = true; 
					this.$http.post(this.savecustomurl,
							JSON.stringify(this.saverequest),
							emulateJSON = false).then(function(data) {
								this.loadcontrol = false; 
								this.resultcode = data.body.resultcode;
						if (this.resultcode == 1) {
							alert("插入客户成功");
						}
					}, function() {
						this.loadcontrol = false;
						alert('插入客户失败');
					});
				},
				searchcustom : function() {
					this.searchrequest = {
							"KUNRG":this.KUNRG,
							"NAME2":this.NAME2
					};
					this.loadcontrol = true; 
					this.$http.post(this.searchcustomurl,
							JSON.stringify(this.searchrequest),
							emulateJSON = false).then(function(data) {
								this.loadcontrol = false; 
								this.CUSTOMLIST = data.body.lists;
						if (this.resultcode == 1) {
							alert("查询客户成功");
						}
					}, function() {
						this.loadcontrol = false;
						alert('查询客户失败');
					});
				}

			}
		});
		$(function(){
			//监听form表单区域的Enter
			$("input[name=KUNRG],input[name=NAME2]").bind("keydown",function(e){
			    var theEvent = e || window.event;    
			    var code = theEvent.keyCode || theEvent.which || theEvent.charCode;    
			    if (code == 13) {
			    	$('.enter-submit').trigger('click');
		        }
			});
		});
	</script>

</body>
</html>