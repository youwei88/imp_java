package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import common.util.StringUtils;
import domain.RequestObject;
import domain.ResponceObject;
import excel.ExcelHelper;
import net.sf.json.JSONObject;
import pojo.Billing;
import pojo.Customer;
import pojo.MergeBilling;
import pojo.MergeBillingNo;
import pojo.MergePojoBase;
import pojo.UpdateEtFirst;
import service.billing.IBillingService;
import vo.UserVo;

/**
 * 
 * @author 余林枫
 * @desc 开票控制器
 */
@Controller
@RequestMapping("/kaipiao")
public class BillingController {
	protected static Logger logger = Logger.getLogger("controller");

	@Autowired
	public IBillingService iBillingService;

	/**
	 * 发票查询
	 * @return
	 */
	@RequestMapping(value = "/BillingSearch", method = RequestMethod.GET)
	public String getBillingSearch() {
		logger.debug("发票查询");
		// 获取session 用户
		UserVo user = (UserVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return "/kaipiao/BillingSearch";
	}
	
	/**
	 * 未冲销销售组织
	 * @return
	 */
	@RequestMapping(value = "/UnOragnizeInfo", method = RequestMethod.GET)
	public String unOrgnizeInfo() {
		logger.debug("发票查询");
		// 获取session 用户
		UserVo user = (UserVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return "/kaipiao/UnOragnizeInfo";
	}

	/**
	 * 开票信息查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/DoBilling", method = RequestMethod.GET)
	public String getBillingInfo() {
		logger.debug("开票");
		// 获取session 用户
		UserVo user = (UserVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return "/kaipiao/DoBilling";
	}

	/**
	 * 冲销销售组织信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/OragnizeInfo", method = RequestMethod.GET)
	public String orgnizeInfo() {
		logger.debug("开票");
		// 获取session 用户
		UserVo user = (UserVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return "/kaipiao/OragnizeInfo";
	}

	/**
	 * SAP数据导入
	 * 
	 * @return
	 */
	@RequestMapping(value = "/SapImport", method = RequestMethod.GET)
	public String sapImport() {
		logger.debug("开票");
		// 获取session 用户
		UserVo user = (UserVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return "/kaipiao/SapImport";
	}
    
	/**
	 * 客户管理界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/Custom", method = RequestMethod.GET)
	public String custom() {
		logger.debug("开票");
		// 获取session 用户
		UserVo user = (UserVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return "/kaipiao/Custom";
	}
    
	
	
	/**
	 * 冲销销售组织信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/AuditBilling", method = RequestMethod.GET)
	public String AuditBilling() {
		logger.debug("开票");
		// 获取session 用户
		UserVo user = (UserVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return "/kaipiao/AuditBilling";
	}
	
	/**
	 * 获取冲销信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getBillingsForUp", method = RequestMethod.POST, consumes = "application/json; charset=utf-8")
	@ResponseBody
	public ResponceObject<Billing> getBillings(HttpServletRequest request) {
		String result = null;
		try {
			InputStream in = request.getInputStream();
			BufferedReader read = new BufferedReader(new InputStreamReader(in,"UTF-8"));
			result = read.readLine();
		} catch (Exception e) {
			// TODO: handle exception
		}
		Gson gson = new Gson();
		RequestObject requestObject = gson.fromJson(result, RequestObject.class);
		String vtwegs =requestObject.getVTWEG();
		String[] v =vtwegs.replace(" ", ",").replace("，", ",").split(",");
		String newvtwegs =v[0];
		if(v.length>=2)
		{
			for(int j=1;j<v.length;j++){
				newvtwegs =newvtwegs +"','"+v[j];
			}
		}
		requestObject.setVTWEG(newvtwegs);
		String parts =requestObject.getSPART();
		if(parts != null){
			String[] s =parts.replace(" ", ",").replace("，", ",").split(",");
			String newparts=s[0];
			if(s.length>=2){
				for(int i=1;i<s.length;i++){
					newparts =newparts +"','"+s[i];
				}
			}
			requestObject.setSPART(newparts);			
		}
		return iBillingService.getBillings(requestObject);

	}

	/**
	 * @desc Oragnizedinfo页面导出单月份未开票数据
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/downloadExcelOrg", method = RequestMethod.GET)
	@ResponseBody
	public void downloadExcelOrg(HttpServletRequest request, HttpServletResponse httpServletResponse, @RequestParam("exportstart") String exportstart, @RequestParam("exportend") String exportend, @RequestParam("cpudt") String cpudt,@RequestParam("VKORG") String VKORG,@RequestParam("SPART") String SPART,@RequestParam("VTWEG") String VTWEG) throws IOException {
		RequestObject requestObject =new RequestObject();
		requestObject.setTimebegin(exportstart);
		requestObject.setTimeend(exportend);
		requestObject.setVKORG(VKORG);
		requestObject.setCpudt(cpudt);
		String parts =SPART;
		String[] s =parts.replace(" ", ",").replace("，", ",").split(",");
		String newparts=s[0];
		if(s.length>=2){
			for(int i=1;i<s.length;i++){
				newparts =newparts +"','"+s[i];
			}
		}
		requestObject.setSPART(newparts);
		String vtwegs =VTWEG;
		String[] v =vtwegs.replace(" ", ",").replace("，", ",").split(",");
		String newvtwegs =v[0];
		if(v.length>=2)
		{
			for(int j=1;j<v.length;j++){
				newvtwegs =newvtwegs +"','"+v[j];
			}
		}
		requestObject.setVTWEG(newvtwegs);
		SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd");  
		Date date =new Date();
		String name =formatter.format(date);
		httpServletResponse.setCharacterEncoding("UTF-8");
		httpServletResponse.setContentType("application/vnd.ms-excel");
		try {
			ServletOutputStream outputStream = httpServletResponse.getOutputStream();
			String fileName = new String((name).getBytes(), "UTF-8");
			httpServletResponse.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");// 组装附件名称和格式
			String[] titles = { "序号", "SAP票号","发票行号","发票类型","发票类型描述","发货单","销售凭证","销售组织","销售组织描述","分销渠道","分销渠道描述","品牌","品牌描述","销售部门","销售部门描述","开票日期","售达方","售达方名称","售达方简称","付款方","付款方名称","物料号","物料描述","开票数量","销售单位","含税单价","含税总价","税率","税额","财务审核日期","财务审核时间","供货价","结算价","成本价","公司代码"};
			iBillingService.exportExcel(requestObject,name, titles, outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * @desc dobilling页面导出未审核开票数据
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/downloadExcelDoBilling", method = RequestMethod.GET)
	@ResponseBody
	public void downloadExcelDoBilling(HttpServletRequest request, HttpServletResponse httpServletResponse, @RequestParam("exportstart") String exportstart, @RequestParam("exportend") String exportend,@RequestParam("VKORG") String VKORG,@RequestParam("SPART") String SPART,@RequestParam("VTWEG") String VTWEG) throws IOException {
		RequestObject requestObject =new RequestObject();
		requestObject.setTimebegin(exportstart);
		requestObject.setTimeend(exportend);
		requestObject.setVKORG(VKORG);
		String parts =SPART;
		String[] s =parts.replace(" ", ",").replace("，", ",").split(",");
		String newparts=s[0];
		if(s.length>=2){
			for(int i=1;i<s.length;i++){
				newparts =newparts +"','"+s[i];
			}
		}
		requestObject.setSPART(newparts);
		String vtwegs =VTWEG;
		String[] v =vtwegs.replace(" ", ",").replace("，", ",").split(",");
		String newvtwegs =v[0];
		if(v.length>=2)
		{
			for(int j=1;j<v.length;j++){
				newvtwegs =newvtwegs +"','"+v[j];
			}
		}
		requestObject.setVTWEG(newvtwegs);
		 SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd");  
		 Date date =new Date();
		String name =formatter.format(date);
		httpServletResponse.setCharacterEncoding("UTF-8");
		httpServletResponse.setContentType("application/vnd.ms-excel");
		try {
			ServletOutputStream outputStream = httpServletResponse.getOutputStream();
			String fileName = new String((name).getBytes(), "UTF-8");
			httpServletResponse.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");// 组装附件名称和格式
			String[] titles = { "序号", "SAP票号","发票行号","发票类型","发票类型描述","发货单","销售凭证","销售组织","销售组织描述","分销渠道","分销渠道描述","品牌","品牌描述","销售部门","销售部门描述","开票日期","售达方","售达方名称","售达方简称","付款方","付款方名称","物料号","物料描述","开票数量","销售单位","含税单价","含税总价","税率","税额","财务审核日期","财务审核时间","供货价","结算价","成本价","公司代码","开票公司名称","纳税人登记号","购方地址及电话","开户银行及账号","备注"};
			iBillingService.exportExcelForDoBill(requestObject,name, titles, outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @desc dobilling页面导出未审核开票数据
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/downloadKpNotSh", method = RequestMethod.GET)
	@ResponseBody
	public void downloadKpNotSh(HttpServletRequest request, HttpServletResponse httpServletResponse, @RequestParam("exportstart") String exportstart, @RequestParam("exportend") String exportend,@RequestParam("VKORG") String VKORG,@RequestParam("SPART") String SPART,@RequestParam("VTWEG") String VTWEG) throws IOException {
		RequestObject requestObject =new RequestObject();
		requestObject.setTimebegin(exportstart);
		requestObject.setTimeend(exportend);
		requestObject.setVKORG(VKORG);
		requestObject.setSPART(SPART);
		requestObject.setVTWEG(VTWEG);
		SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd");
		String name =formatter.format(new Date());
		httpServletResponse.setCharacterEncoding("UTF-8");
		httpServletResponse.setContentType("application/vnd.ms-excel");
		try {
			ServletOutputStream outputStream = httpServletResponse.getOutputStream();
			String fileName = new String(name.getBytes(), "UTF-8");
			httpServletResponse.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");// 组装附件名称和格式
			String[] titles = { "序号", "SAP票号","发票行号","发票类型","发票类型描述","发货单","销售凭证","销售组织","销售组织描述","分销渠道","分销渠道描述","品牌","品牌描述","销售部门","销售部门描述","开票日期","售达方","售达方名称","售达方简称","付款方","付款方名称","物料号","物料描述","开票数量","销售单位","含税单价","含税总价","税率","税额","财务审核日期","财务审核时间","供货价","结算价","成本价","公司代码","产品规格","总发票号","合并开票日期"};
			iBillingService.exportExcelKpNotSh(requestObject,name, titles, outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @desc 导出已审核的开票信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/downloadExcelDoBillingAudit", method = RequestMethod.GET)
	@ResponseBody
	public void downloadExcelDoBillingAudit(HttpServletRequest request, HttpServletResponse httpServletResponse, @RequestParam("exportstart") String exportstart, @RequestParam("exportend") String exportend,@RequestParam("VKORG") String VKORG,@RequestParam("SPART") String SPART,@RequestParam("VTWEG") String VTWEG) throws IOException {
		RequestObject requestObject =new RequestObject();
		requestObject.setTimebegin(exportstart);
		requestObject.setTimeend(exportend);
		requestObject.setVKORG(VKORG);
		
		String parts =SPART;
		String[] s =parts.replace(" ", ",").replace("，", ",").split(",");
		String newparts=s[0];
		if(s.length>=2){
			for(int i=1;i<s.length;i++){
				newparts =newparts +"','"+s[i];
			}
		}
		requestObject.setSPART(newparts);
		
		requestObject.setVTWEG(VTWEG);
		String vtwegs =requestObject.getVTWEG();
		String[] v =vtwegs.replace(" ", ",").replace("，", ",").split(",");
		String newvtwegs =v[0];
		if(v.length>=2)
		{
			for(int j=1;j<v.length;j++){
				newvtwegs =newvtwegs +"','"+v[j];
			}
		}
		requestObject.setVTWEG(newvtwegs);
		
		//vkorg做in值
		String[] vkorgArray =VKORG.replace(" ", ",").replace("，", ",").split(",");
		String vkorgIn =vkorgArray[0];
		if(vkorgArray.length>=2)
		{
			for(int j=1;j<vkorgArray.length;j++){
				vkorgIn =vkorgIn +"','"+vkorgArray[j];
			}
		}
		requestObject.setVKORG(vkorgIn);
		
		 SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd");  
		 Date date =new Date();
		String name =formatter.format(date);
		httpServletResponse.setCharacterEncoding("UTF-8");
		httpServletResponse.setContentType("application/vnd.ms-excel");
		try {
			ServletOutputStream outputStream = httpServletResponse.getOutputStream();
			String fileName = new String((name).getBytes(), "UTF-8");
			httpServletResponse.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");// 组装附件名称和格式
			String[] titles = { "序号","开票索引号","店铺代码","店铺号","个别","发票号码","产品编码","产品名称","规格型号","单位","开票数量","含税单价","含税总价","零售价","开发票名称","纳税人登记号","购方地址及电话","开户银行及账号","备注","产品组","销售组织"};
			iBillingService.exportExcelForDoBillAudit(requestObject,name, titles, outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * @desc 导出已审核的开票信息金税发票
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/downloadExcelDoBillingAuditGold", method = RequestMethod.GET)
	@ResponseBody
	public void downloadExcelDoBillingAuditGold(HttpServletRequest request, HttpServletResponse httpServletResponse, @RequestParam("exportstart") String exportstart, @RequestParam("exportend") String exportend,@RequestParam("VKORG") String VKORG,@RequestParam("SPART") String SPART,@RequestParam("VTWEG") String VTWEG) throws IOException {
		RequestObject requestObject =new RequestObject();
		requestObject.setTimebegin(exportstart);
		requestObject.setTimeend(exportend);
		requestObject.setVKORG(VKORG);
		String parts =SPART;
		String[] s =parts.replace(" ", ",").replace("，", ",").split(",");
		String newparts=s[0];
		if(s.length>=2){
			for(int i=1;i<s.length;i++){
				newparts =newparts +"','"+s[i];
			}
		}
		requestObject.setSPART(newparts);
		requestObject.setVTWEG(VTWEG);
		String vtwegs =requestObject.getVTWEG();
		String[] v =vtwegs.replace(" ", ",").replace("，", ",").split(",");
		String newvtwegs =v[0];
		if(v.length>=2)
		{
			for(int j=1;j<v.length;j++){
				newvtwegs =newvtwegs +"','"+v[j];
			}
		}
		requestObject.setVTWEG(newvtwegs);
		 SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd");  
		 Date date =new Date();
		String name =formatter.format(date);
		httpServletResponse.setCharacterEncoding("UTF-8");
		httpServletResponse.setContentType("application/vnd.ms-excel");
		try {
			ServletOutputStream outputStream = httpServletResponse.getOutputStream();
			String fileName = new String((name).getBytes(), "UTF-8");
			httpServletResponse.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");// 组装附件名称和格式
			String[] titles = { "序号","开票索引号","金税发票","店铺代码","店铺号","个别","发票号码","产品编码","产品名称","规格型号","单位","开票数量","含税单价","含税总价","零售价","开发票名称","纳税人登记号","购方地址及电话","开户银行及账号","备注","整单折扣金额","成本率","折扣后价格","合计","开票日期"};
			iBillingService.exportMergeBillingAuditGold(requestObject,name, titles, outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * 冲销订单
	 * @param request
	 * @return
	 * @throws ParseException
	 */

	@RequestMapping(value = "/getOffBillings", method = RequestMethod.POST, consumes = "application/json; charset=utf-8")
	@ResponseBody
	public ResponceObject<Billing> getOffBillings(HttpServletRequest request) throws ParseException {
		String result = null;
		try {
			InputStream in = request.getInputStream();
			BufferedReader read = new BufferedReader(new InputStreamReader(in,"UTF-8"));
			result = read.readLine();
		} catch (Exception e) {
			// TODO: handle exception
		}
		Gson gson = new Gson();
		RequestObject requestObject = gson.fromJson(result, RequestObject.class);	
		
		String vtwegs =requestObject.getVTWEG();
		String[] v =vtwegs.replace(" ", ",").replace("，", ",").split(",");
		String newvtwegs =v[0];
		if(v.length>=2)
		{
			for(int j=1;j<v.length;j++){
				newvtwegs =newvtwegs +"','"+v[j];
			}
		}
		requestObject.setVTWEG(newvtwegs);
		String parts =requestObject.getSPART();
		if(parts != null){
			String[] s =parts.replace(" ", ",").replace("，", ",").split(",");
			String newparts=s[0];
			if(s.length>=2){
				for(int i=1;i<s.length;i++){
					newparts =newparts +"','"+s[i];
				}
			}
			requestObject.setSPART(newparts);
		}
		ResponceObject<Billing> responceObject = iBillingService.getOffBillings(requestObject);
		
		return responceObject;

	}

	/**
	 * 隐藏订单
	 * 
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/updatebillingstatus", method = RequestMethod.POST, consumes = "application/json; charset=utf-8")
	@ResponseBody
	public ResponceObject<Object> updatebillingstatus(HttpServletRequest request) throws ParseException {
		String result = null;
		try {
			InputStream in = request.getInputStream();
			BufferedReader read = new BufferedReader(new InputStreamReader(in,"UTF-8"));
			result = read.readLine();
		} catch (Exception e) {
			// TODO: handle exception
		}
		Gson gson = new Gson();
		RequestObject requestObject = gson.fromJson(result, RequestObject.class);
		/*
		 * SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 * if(null!=requestObject.getTimebegin()&&null!=requestObject.getTimeend
		 * ()&&!"".equals(requestObject.getTimebegin())&&!"".equals(
		 * requestObject.getTimeend())) {
		 * requestObject.setStartTime(sdf.parse(requestObject.getTimebegin()));
		 * requestObject.setEndTime(sdf.parse(requestObject.getTimeend())); }
		 */
		ResponceObject<Object> responceObject = iBillingService.updatebillingstatus(requestObject);
		return responceObject;

	}
	
	/**
	 * 批量隐藏订单
	 * @param request
	 * @return
	 * @author wangjinduo
	 * @throws ParseException
	 */
	@RequestMapping(value = "/batchupdatebillingstatus", method = RequestMethod.POST, consumes = "application/json; charset=utf-8")
	@ResponseBody
	public ResponceObject<Object> batchupdatebillingstatus(HttpServletRequest request) throws ParseException {
		String result = null;
		try {
			InputStream in = request.getInputStream();
			BufferedReader read = new BufferedReader(new InputStreamReader(in,"UTF-8"));
			result = read.readLine();
		} catch (Exception e) {
			// TODO: handle exception
		}
		Gson gson = new Gson();
		RequestObject requestObject = gson.fromJson(result, RequestObject.class);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idArray", requestObject.getIdArray());
		ResponceObject<Object> responceObject = iBillingService.batchupdatebillingstatus(params);
		return responceObject;
	}
	
	/**
	 * 查询品牌
	 * 
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/selectSpart", method = RequestMethod.POST, consumes = "application/json; charset=utf-8")
	@ResponseBody
	public ResponceObject<Billing> selectSpart() {
		
		return iBillingService.selectSpart();
	}
	
	/**
	 * 查询品牌
	 * 
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/mergeBillings", method = RequestMethod.POST, consumes = "application/json; charset=utf-8")
	@ResponseBody
	public ResponceObject<Object> mergeBIlling(HttpServletRequest request) {
		ResponceObject<Object>  responceObject =new ResponceObject<>();
		String result = null;
		try {
			InputStream in = request.getInputStream();
			BufferedReader read = new BufferedReader(new InputStreamReader(in,"UTF-8"));
			result = read.readLine();
		} catch (Exception e) {
			// TODO: handle exception
		}
		Gson gson = new Gson();
		RequestObject requestObject = gson.fromJson(result, RequestObject.class);
		String Mergeid =requestObject.getMergeid();
		Date date =new Date();
		SimpleDateFormat simpleDateFormat =new SimpleDateFormat("yyyyMMddHHmmSS");
		SimpleDateFormat simpleDateFormat1 =new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
		requestObject.setMERGE_TIME(sdf.format(date));
		UserVo user = (UserVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		requestObject.setMERGE_PEOPLE(user.getId().toString());
		String vitureinvocenum =simpleDateFormat.format(date);
		String vitureinvocenum1 =simpleDateFormat1.format(date);
		//ScheduleController.i =ScheduleController.i +1;
		requestObject.setVITUREINVOCE(vitureinvocenum);
		String[] s =Mergeid.replace(" ", ",").replace("，", ",").split(",");
		int mertype = s.length;
		if(mertype>=1000)
		{
			int m=mertype/500;
			int n =mertype%500;
				//控制循环次数
				for(int num1 =0;num1<=m;num1++)
				{   String newMergeid="";
					if(num1==m&&n!=0){
						for(int y=m*500;y<m*500+n;y++)
						{
							newMergeid =newMergeid +"','"+s[y];
							
						}
						requestObject.setMergeid(newMergeid);
						iBillingService.mergeBillings(requestObject);
						newMergeid="";
					}else{
						for( int z=num1*500;z<num1*500+500;z++)
						{
							newMergeid =newMergeid +"','"+s[z];
						}
						requestObject.setMergeid(newMergeid);
						iBillingService.mergeBillings(requestObject);
						newMergeid="";
					}	
					
				}
		}else{
			
			String newMergeid=s[0];
			if(s.length>=2){
				for(int i=1;i<s.length;i++){
					newMergeid =newMergeid +"','"+s[i];
				}
			}
			requestObject.setMergeid(newMergeid);
			iBillingService.mergeBillings(requestObject);
		}
		MergeBillingNo my =new MergeBillingNo();
		my.setVITUREINVOCE(vitureinvocenum);
		List<String> list= iBillingService.selectkunrgbyvitureinvoce(my);
		//int i=ScheduleController.i;
		for(int n =0;n<list.size();n++)
		{   
			MergeBillingNo mergeBillingNo =new MergeBillingNo();
			mergeBillingNo.setVITUREINVOCE(vitureinvocenum);
			ScheduleController.i=ScheduleController.i+1;
			String s1=String.valueOf(ScheduleController.i);
			switch (s1.length()) {
			case 1:s1=vitureinvocenum1+"00000"+s1;break;
			case 2:s1=vitureinvocenum1+"0000"+s1;break;
			case 3:s1=vitureinvocenum1+"000"+s1;break;
			case 4:s1=vitureinvocenum1+"00"+s1;break;
			case 5:s1=vitureinvocenum1+"0"+s1;break;
			case 6:s1=vitureinvocenum1+""+s1;break;
			}
			SimpleDateFormat simpleDateFormat2 =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			mergeBillingNo.setBILLINGNO(s1);
			mergeBillingNo.setKUNRG(list.get(n).trim());
			mergeBillingNo.setCREATER(user.getId());
			mergeBillingNo.setCREATETIME(simpleDateFormat2.format(date));
			iBillingService.insertintoetmergeno(mergeBillingNo);
			
		}
		responceObject = iBillingService.mergeBillings(requestObject);
	    return  responceObject;
	}
	
	
	/**
	 * 查询开票信息
	 */
	@RequestMapping(value = "/searchMergeBilling", method = RequestMethod.POST, consumes = "application/json; charset=utf-8")
	@ResponseBody
	public ResponceObject<MergeBilling> searchMergeBilling(HttpServletRequest request) {
		  ResponceObject<MergeBilling>  responceObject =new ResponceObject<>();
			String result = null;
			try {
				InputStream in = request.getInputStream();
				BufferedReader read = new BufferedReader(new InputStreamReader(in,"UTF-8"));
				result = read.readLine();
			} catch (Exception e) {
				// TODO: handle exception
			}
			Gson gson = new Gson();
			RequestObject requestObject = gson.fromJson(result, RequestObject.class);
			String parts =requestObject.getSPART();
			String[] s =parts.replace(" ", ",").replace("，", ",").split(",");
			String newparts=s[0];
			if(s.length>=2){
				for(int i=1;i<s.length;i++){
					newparts =newparts +"','"+s[i];
				}
			}
			requestObject.setSPART(newparts);
			String vtwegs =requestObject.getVTWEG();
			String[] v =vtwegs.replace(" ", ",").replace("，", ",").split(",");
			String newvtwegs =v[0];
			if(v.length>=2)
			{
				for(int j=1;j<v.length;j++){
					newvtwegs =newvtwegs +"','"+v[j];
				}
			}
			requestObject.setVTWEG(newvtwegs);
			responceObject =iBillingService.searchMergeBilling(requestObject);
			return responceObject;
		  
	  }
	
	/**
	 * 根据ID更新发票
	 * @param updateEtFirst
	 * @return
	 */
	@RequestMapping(value = "/SaveUpdateByid", method = RequestMethod.POST, consumes = "application/json; charset=utf-8")
	@ResponseBody
	public ResponceObject<Object> saveUpdateByid(HttpServletRequest request){
		ResponceObject<Object> responceObject =new ResponceObject<>();
		String result = null;
		try {
			InputStream in = request.getInputStream();
			BufferedReader read = new BufferedReader(new InputStreamReader(in,"UTF-8"));
			result = read.readLine();
		} catch (Exception e) {
			// TODO: handle exception
		}
		Gson gson = new Gson();
		UpdateEtFirst updateEtFirst = gson.fromJson(result, UpdateEtFirst.class);
		UserVo user = (UserVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		updateEtFirst.setUPDATETER(user.getId());
		Date date =new Date();
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
		updateEtFirst.setUPDATETIME(sdf.format(date));
		responceObject =iBillingService.SaveUpdateByid(updateEtFirst);
		return responceObject;	
	}
	
	/**
	 * 根据发票号审核发票
	 * @param updateEtFirst
	 * @return
	 */
	@RequestMapping(value = "/SaveupdateByVitureinvoce", method = RequestMethod.POST, consumes = "application/json; charset=utf-8")
	@ResponseBody
	public ResponceObject<Object> saveupdateByVitureinvoce(HttpServletRequest request){
		ResponceObject<Object> responceObject =new ResponceObject<>();
		String result = null;
		try {
			InputStream in = request.getInputStream();
			BufferedReader read = new BufferedReader(new InputStreamReader(in,"UTF-8"));
			result = read.readLine();
		} catch (Exception e) {
			// TODO: handle exception
		}
		Gson gson = new Gson();
		UpdateEtFirst updateEtFirst = gson.fromJson(result, UpdateEtFirst.class);
		String vituregroup =updateEtFirst.getVitureinvocegroup();
		UserVo user = (UserVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
		Date date =new Date();
		String[] s =vituregroup.replace(" ", ",").replace("，", ",").split(",");
		for(int i=0;i<s.length;i++)
		{
			UpdateEtFirst myup =new UpdateEtFirst();
			myup.setVITUREINCOCE(s[i].split("#")[0]);
			myup.setKUNRG(s[i].split("#")[1]);
			myup.setAUDITOR_TIME(sdf.format(date));
			myup.setAUDITOR(user.getId());
			iBillingService.SaveupdateByVitureinvoce(myup);
		}
		
		String newvituregroup=s[0];
		if(s.length>=2){
			for(int i=1;i<s.length;i++){
				newvituregroup =newvituregroup +"','"+s[i];
			}
		}
		return responceObject;	
	}
	
	/**
	 * 根据发票号回滚发票
	 * @param updateEtFirst
	 * @return
	 */
	@RequestMapping(value = "/roleBackByVitureinvoce", method = RequestMethod.POST, consumes = "application/json; charset=utf-8")
	@ResponseBody
	public ResponceObject<Object> roleBackByVitureinvoce(HttpServletRequest request){
		ResponceObject<Object> responceObject =new ResponceObject<>();
		String result = null;
		try {
			InputStream in = request.getInputStream();
			BufferedReader read = new BufferedReader(new InputStreamReader(in,"UTF-8"));
			result = read.readLine();
		} catch (Exception e) {
			// TODO: handle exception
		}
		Gson gson = new Gson();
		UpdateEtFirst updateEtFirst = gson.fromJson(result, UpdateEtFirst.class);
		String vituregroup =updateEtFirst.getVitureinvocegroup();
		UserVo user = (UserVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Date date =new Date();
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
		
		String[] s =vituregroup.replace(" ", ",").replace("，", ",").split(",");
		for(int i=0;i<s.length;i++)
		{
			UpdateEtFirst myup =new UpdateEtFirst();
			myup.setVITUREINCOCE(s[i].split("#")[0]);
			myup.setKUNRG(s[i].split("#")[1]);
			myup.setAUDITOR_TIME(sdf.format(date));
			myup.setAUDITOR(user.getId());
			iBillingService.RollbackByVitureinvoce(myup);
		}
		/*String newvituregroup=s[0].split("|")[0];
		if(s.length>=2){
			for(int i=1;i<s.length;i++){
				newvituregroup =newvituregroup +"','"+s[i].split("|")[0];
			}
		}
		updateEtFirst.setVitureinvocegroup(newvituregroup);
		updateEtFirst.setVITUREINCOCE(newvituregroup.split("|")[0]);
		updateEtFirst.setKUNRG(newvituregroup.split("|")[1]);
		
		updateEtFirst.setUPDATETER(user.getId());*/
	
		//responceObject =
		return responceObject;	
	}
	
	
	/**
	 * 根据发票号回滚已审发票
	 * @param updateEtFirst
	 * @return
	 */
	@RequestMapping(value = "/roleBackAuditByVitureinvoce", method = RequestMethod.POST, consumes = "application/json; charset=utf-8")
	@ResponseBody
	public ResponceObject<Object> roleBackAuditByVitureinvoce(HttpServletRequest request){
		ResponceObject<Object> responceObject =new ResponceObject<>();
		String result = null;
		try {
			InputStream in = request.getInputStream();
			BufferedReader read = new BufferedReader(new InputStreamReader(in,"UTF-8"));
			result = read.readLine();
		} catch (Exception e) {
			// TODO: handle exception
		}
		Gson gson = new Gson();
		UpdateEtFirst updateEtFirst = gson.fromJson(result, UpdateEtFirst.class);
		String vituregroup =updateEtFirst.getVitureinvocegroup();
		String[] s =vituregroup.replace(" ", ",").replace("，", ",").split(",");
		String newvituregroup=s[0];
		if(s.length>=2){
			for(int i=1;i<s.length;i++){
				newvituregroup =newvituregroup +"','"+s[i];
			}
		}
		updateEtFirst.setVitureinvocegroup(newvituregroup);
		
		UserVo user = (UserVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		updateEtFirst.setUPDATETER(user.getId());
		Date date =new Date();
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
		updateEtFirst.setAUDITOR_TIME(sdf.format(date));
		responceObject =iBillingService.RollbackByVitureinvoceAudit(updateEtFirst);
		return responceObject;	
	}
	
	
	/**
	 * 查询已审核开票信息
	 */
	@RequestMapping(value = "/searchMergeBillingAudit", method = RequestMethod.POST, consumes = "application/json; charset=utf-8")
	@ResponseBody
	public ResponceObject<MergeBilling> searchMergeBillingAudit(HttpServletRequest request) {
		  ResponceObject<MergeBilling>  responceObject =new ResponceObject<>();
			String result = null;
			try {
				InputStream in = request.getInputStream();
				BufferedReader read = new BufferedReader(new InputStreamReader(in,"UTF-8"));
				result = read.readLine();
			} catch (Exception e) {
				// TODO: handle exception
			}
			Gson gson = new Gson();
			RequestObject requestObject = gson.fromJson(result, RequestObject.class);
			String parts =requestObject.getSPART();
			if(parts != null){
				String[] s =parts.replace(" ", ",").replace("，", ",").split(",");
				String newparts=s[0];
				if(s.length>=2){
					for(int i=1;i<s.length;i++){
						newparts =newparts +"','"+s[i];
					}
				}
				requestObject.setSPART(newparts);
			}
			String vtwegs =requestObject.getVTWEG();
			String[] v =vtwegs.replace(" ", ",").replace("，", ",").split(",");
			String newvtwegs =v[0];
			if(v.length>=2)
			{
				for(int j=1;j<v.length;j++){
					newvtwegs =newvtwegs +"','"+v[j];
				}
			}
			requestObject.setVTWEG(newvtwegs);
			responceObject =iBillingService.searchMergeBillingAudit(requestObject);
			return responceObject;
		  
	  }
	
	/**
	 * 插入付款公司信息
	 * 
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/insertCustomer", method = RequestMethod.POST, consumes = "application/json; charset=utf-8")
	@ResponseBody
	 public ResponceObject<Object> insertCustomer(HttpServletRequest request){
			String result = null;
			try {
				InputStream in = request.getInputStream();
				BufferedReader read = new BufferedReader(new InputStreamReader(in,"UTF-8"));
				result = read.readLine();
			} catch (Exception e) {
				// TODO: handle exception
			}
			Gson gson = new Gson();
			Customer customer = gson.fromJson(result, Customer.class);
			List<Customer> list =new LinkedList<>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		    Date date =new Date();
		   customer.setCREATE_TIME( sdf.format(date));
		   if (StringUtils.isNotEmpty(customer.getKUNRG())) {
			   customer.setKUNRG("0000000000".substring(customer.getKUNRG().length()) + customer.getKUNRG());
		   }
		   list.add(customer);
			ResponceObject<Object> responceObject = iBillingService.insertCustomer(list);
			return responceObject;
		 
	 }
	
	
	/**
	 * 查询付款公司信息
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws ParseException
	 */
	@RequestMapping(value = "/searchCustomers", method = RequestMethod.POST, consumes = "application/json; charset=utf-8")
	@ResponseBody
	 public ResponceObject<Customer> searchCustomers(HttpServletRequest request) throws UnsupportedEncodingException{	
		request.setCharacterEncoding("UTF-8");
		    String result = null;
			try {
				InputStream in = request.getInputStream();
				BufferedReader read = new BufferedReader(new InputStreamReader(in,"UTF-8"));
				result = read.readLine();
			} catch (Exception e) {
				// TODO: handle exception
			}
			Gson gson = new Gson();
		      String s = new String(result.getBytes("UTF-8"),"UTF-8");
			Customer customer = gson.fromJson(s, Customer.class);
			if (StringUtils.isNotEmpty(customer.getKUNRG())) {
				customer.setKUNRG("0000000000".substring(customer.getKUNRG().length()) + customer.getKUNRG());
			}
			return iBillingService.searchcustoms(customer);
		 
	 }
	
	/**
	 * 查询未冲销组织信息
	 * 
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws ParseException
	 */
	@RequestMapping(value = "/getUnOrgBilling", method = RequestMethod.POST, consumes = "application/json; charset=utf-8")
	@ResponseBody
	public ResponceObject<Billing> getUnOrgBilling(HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		String result = null;
		try {
			InputStream in = request.getInputStream();
			BufferedReader read = new BufferedReader(new InputStreamReader(in,"UTF-8"));
			result = read.readLine();
		} catch (Exception e) {
			// TODO: handle exception
		}
		Gson gson = new Gson();
		RequestObject requestObject = gson.fromJson(result, RequestObject.class);	
		String parts =requestObject.getSPART();
		String[] s =parts.replace(" ", ",").replace("，", ",").split(",");
		String newparts=s[0];
		if(s.length>=2){
			for(int i=1;i<s.length;i++){
				newparts =newparts +"','"+s[i];
			}
		}
		requestObject.setSPART(newparts);
		String vtwegs =requestObject.getVTWEG();
		String[] v =vtwegs.replace(" ", ",").replace("，", ",").split(",");
		String newvtwegs =v[0];
		if(v.length>=2)
		{
			for(int j=1;j<v.length;j++){
				newvtwegs =newvtwegs +"','"+v[j];
			}
		}
		requestObject.setVTWEG(newvtwegs);
	    
		ResponceObject<Billing> responceObject = iBillingService.getUnOrgBilling(requestObject);
		return responceObject;
	}
	
	/**
	 * 根据ID更新用户
	 * @param customer
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/upcustomerbyid", method = RequestMethod.POST, consumes = "application/json; charset=utf-8")
	@ResponseBody
	public ResponceObject<Object> upcustomerbyid(HttpServletRequest request) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
		UserVo user = (UserVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String result = null;
		try {
			InputStream in = request.getInputStream();
			BufferedReader read = new BufferedReader(new InputStreamReader(in,"UTF-8"));
			result = read.readLine();
		} catch (Exception e) {
			// TODO: handle exception
		}
		Gson gson = new Gson();
		String s = new String(result.getBytes("UTF-8"),"UTF-8");
		Customer customer = gson.fromJson(result, Customer.class);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    Date date =new Date();
	   customer.setCREATE_TIME( sdf.format(date));
	   customer.setUPDATER(user.getId());
		ResponceObject<Object> responceObject = iBillingService.upcustomerbyid(customer);
		return responceObject;
		
	}
	
	/**
     * 新的开票页面显示 逻辑
     * @param requestObject
     * @return
     */
	@RequestMapping(value = "/getOffBillingsNewMerge", method = RequestMethod.POST, consumes = "application/json; charset=utf-8")
	@ResponseBody
    public  ResponceObject<MergePojoBase>  getOffBillingsNewMerge(HttpServletRequest request){
    	  ResponceObject<MergePojoBase>  responceObject = new ResponceObject<MergePojoBase>();
			String result = null;
			try {
				InputStream in = request.getInputStream();
				BufferedReader read = new BufferedReader(new InputStreamReader(in,"UTF-8"));
				result = read.readLine();
			} catch (Exception e) {
				// TODO: handle exception
			}
			Gson gson = new Gson();
			RequestObject requestObject = gson.fromJson(result, RequestObject.class);
			String parts =requestObject.getSPART();
			if(parts != null){
				String[] s =parts.replace(" ", ",").replace("，", ",").split(",");
				String newparts=s[0];
				if(s.length>=2){
					for(int i=1;i<s.length;i++){
						newparts =newparts +"','"+s[i];
					}
				}
				requestObject.setSPART(newparts);
			}
			
			String vtwegs =requestObject.getVTWEG();
			String[] v =vtwegs.replace(" ", ",").replace("，", ",").split(",");
			String newvtwegs =v[0];
			if(v.length>=2)
			{
				for(int j=1;j<v.length;j++){
					newvtwegs =newvtwegs +"','"+v[j];
				}
			}
			requestObject.setVTWEG(newvtwegs);
			
			if(StringUtils.isNotEmpty(requestObject.getIMPNUM())){
				String impum =requestObject.getIMPNUM().replace(' ', ',');
				String[] k =impum.replace(" ", ",").replace("，", ",").split(",");
				String newimpum =k[0];
				if(k.length>=2)
				{
					for(int j=1;j<k.length;j++){
						newimpum =newimpum +"','"+k[j];
					}
				}
				requestObject.setIMPNUM(newimpum);
			}
			
			responceObject =iBillingService.getOffBillingsNewMerge(requestObject);
			return responceObject;
    }
	
	/**
	 * 审核客户
	 * @param requestObject
	 * @return
	 */
	@RequestMapping(value = "/auditcustomer", method = RequestMethod.POST, consumes = "application/json; charset=utf-8")
	@ResponseBody
	public ResponceObject<Object> auditcustomer(HttpServletRequest request) {
		UserVo user = (UserVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ResponceObject<Object>  responceObject =new ResponceObject<Object>();
			String result = null;
			try {
				InputStream in = request.getInputStream();
				BufferedReader read = new BufferedReader(new InputStreamReader(in,"UTF-8"));
				result = read.readLine();
			} catch (Exception e) {
				// TODO: handle exception
			}
			Gson gson = new Gson();
			RequestObject requestObject = gson.fromJson(result, RequestObject.class);
			Date date =new Date();
			SimpleDateFormat simpleDateFormat2 =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			requestObject.setTimebegin(simpleDateFormat2.format(date));
			requestObject.setAUDITOR(user.getId().toString());
			return iBillingService.auditcustomer(requestObject);
	}
	
	/**
	 * @desc 导出已审核的全部开票信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/downloadExcelDoBillingAuditAll", method = RequestMethod.GET)
	@ResponseBody
	public void downloadExcelDoBillingAuditAll(HttpServletRequest request, HttpServletResponse httpServletResponse, @RequestParam("exportstart") String exportstart, @RequestParam("exportend") String exportend) throws IOException {
		RequestObject requestObject =new RequestObject();
		requestObject.setTimebegin(exportstart);
		requestObject.setTimeend(exportend);
		 SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd");  
		 Date date =new Date();
		String name =formatter.format(date);
		httpServletResponse.setCharacterEncoding("UTF-8");
		httpServletResponse.setContentType("application/vnd.ms-excel");
		try {
			ServletOutputStream outputStream = httpServletResponse.getOutputStream();
			String fileName = new String((name).getBytes(), "UTF-8");
			httpServletResponse.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");// 组装附件名称和格式
			String[] titles = { "序号","SAP票号","发票行号","发票类型","发票类型描述","发货单","销售凭证","销售组织","销售组织描述","分销渠道","分销渠道描述","品牌","品牌描述","销售部门","销售部门描述","开票日期","售达方","售达方简称","付款方","付款方名称","物料号","物料号描述","开票数量","销售单位","含税单价","含税总价","公司抬头","公司抬头描述","税率","税额","供货价","结算价","成本价"};
			iBillingService.selectallbytimeexportexcel(requestObject,name, titles, outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * @desc 导出已审核未反导的全部开票信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/downloadExcelShNotFd", method = RequestMethod.GET)
	@ResponseBody
	public void downloadExcelShNotFd(HttpServletRequest request, HttpServletResponse httpServletResponse, @RequestParam("exportstart") String exportstart, @RequestParam("exportend") String exportend) throws IOException {
		RequestObject requestObject =new RequestObject();
		requestObject.setTimebegin(exportstart);
		requestObject.setTimeend(exportend);
		 SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd");  
		String name =formatter.format(new Date());
		httpServletResponse.setCharacterEncoding("UTF-8");
		httpServletResponse.setContentType("application/vnd.ms-excel");
		try {
			ServletOutputStream outputStream = httpServletResponse.getOutputStream();
			String fileName = new String((name).getBytes(), "UTF-8");
			httpServletResponse.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");// 组装附件名称和格式
			String[] titles = { "序号","SAP票号","发票行号","发票类型","发票类型描述","发货单","销售凭证","销售组织","销售组织描述","分销渠道","分销渠道描述","品牌","品牌描述","销售部门","销售部门描述","开票日期","售达方","售达方简称","付款方","付款方名称","物料号","物料号描述","开票数量","销售单位","含税单价","含税总价","公司抬头","公司抬头描述","税率","税额","供货价","结算价","成本价","公司代码","产品规格","总发票号","审核时间"};
			iBillingService.selectShNotFdExcel(requestObject,name, titles, outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * @desc 导出客户信息
	 * 
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/exportCustomer", method = RequestMethod.GET)
	public void downloadExcelDoBillingAuditAll(HttpServletRequest request, HttpServletResponse httpServletResponse) throws IOException {
		 SimpleDateFormat formatter=new SimpleDateFormat("yyyyMMdd");  
		 Date date =new Date();
		String name =formatter.format(date);
		httpServletResponse.setCharacterEncoding("UTF-8");
		httpServletResponse.setContentType("application/vnd.ms-excel");
		try {
			ServletOutputStream outputStream = httpServletResponse.getOutputStream();
			String fileName = new String(("customers").getBytes(), "UTF-8");
			httpServletResponse.setHeader("Content-disposition", "attachment; filename=" + fileName + ".xlsx");// 组装附件名称和格式
			String[] titles = {"销售组织","品牌","付款方","付款方名称","创建人","开票公司名称","纳税人识别号","纳税人名称","地址电话","开户行","开户行账号","发票地址","电话号码","发票快递签收","发票类型"};
			iBillingService.exportAllCutomers(name, titles, outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
