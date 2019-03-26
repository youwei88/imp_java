package controller;

 
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;  

import javax.servlet.http.HttpServletRequest;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;  
import org.springframework.web.bind.annotation.RequestMapping;  
import org.springframework.web.bind.annotation.RequestMethod;  
import org.springframework.web.bind.annotation.ResponseBody;  
import org.springframework.web.multipart.MultipartFile;  
import org.springframework.web.multipart.MultipartHttpServletRequest;

import common.util.StringUtils;
import domain.ResponceObject;
import excel.ImportExcelUtil;
import pojo.Customer;
import pojo.VitureGold;
import service.billing.IBillingService;
import vo.UserVo;  


@Controller  
@RequestMapping("/uploadExcel")    
public class UploadExcelControl {  
    
	@Autowired
	public IBillingService iBillingService;
	
  /**  
   * 描述：通过传统方式form表单提交方式导入excel文件  
   * @param request  
   * @throws Exception  
   */  
  @RequestMapping(value="/importCustomer",method={RequestMethod.GET,RequestMethod.POST}) 
  @ResponseBody
  public  ResponceObject<Object>  importCustomer(HttpServletRequest request) throws Exception { 
		UserVo user = (UserVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	  ResponceObject<Object> responceObject = new ResponceObject<Object>();
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;    
      System.out.println("通过传统方式form表单提交方式导入excel文件！");  
    
      InputStream in =null;  
      List<List<Object>> listob = null;  
      MultipartFile file = multipartRequest.getFile("upfile");  
      if(file.isEmpty()){  
          throw new Exception("文件不存在！");  
      }  
      in = file.getInputStream();  
      listob = new ImportExcelUtil().getBankListByExcelCustomer(in,file.getOriginalFilename());  
      in.close();  
      List<Customer> list =new LinkedList<Customer>();
      //该处可调用service相应方法进行数据保存到数据库中，现只对数据输出  
      for (int i = 0; i < listob.size(); i++) {  
          List<Object> lo = listob.get(i);  
          Customer customer = new Customer();  
          //销售组织
          customer.setVKORG(String.valueOf(lo.get(0)).trim().split("\\.")[0]);
          //品牌号
          customer.setSPART(String.valueOf(lo.get(1)).trim().split("\\.")[0]);
          //付款方代码
          String kunrg = String.valueOf(lo.get(2)).split("\\.")[0];
          if (StringUtils.isNotEmpty(kunrg)) {
        	  customer.setKUNRG("0000000000".substring(0, 10 - kunrg.length()) + kunrg);
          }
          //开票公司名称
          customer.setCOMNAME(String.valueOf(lo.get(3)).trim());
          //付款方名称
          customer.setNAME2(String.valueOf(lo.get(4)).trim());
          //纳税人识别号
          customer.setPAYINGNUM(String.valueOf(lo.get(5)).trim());
          //地址电话
          // customer.setPAYINGNAME(String.valueOf(lo.get(6)).trim());
          customer.setADDRESS(String.valueOf(lo.get(6)).trim());
          customer.setBANK(String.valueOf(lo.get(7)).trim());
          String s = String.valueOf(lo.get(8)).trim();
          customer.setBANKNUM(s.indexOf(".") == -1 ? s : s.substring(0, s.indexOf(".")));
          customer.setBILLINGADDRESS(String.valueOf(lo.get(9)).trim());
          customer.setPHONE(String.valueOf(lo.get(10)).trim());
          customer.setPEOPLE(String.valueOf(lo.get(11)).trim());
          customer.setBILLINGTYPE(String.valueOf(lo.get(12)).trim());  
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		   Date date =new Date();
		   customer.setCREATER(user.getId());
		   customer.setCREATE_TIME(sdf.format(date));
		   list.add(customer);
      }  
      responceObject=iBillingService.insertCustomer(list);
      return responceObject;  
  }  
  
  /**  
   * 描述：通过传统方式form表单提交方式导入excel文件  
   * @param request  
   * @throws Exception  
   */  
  @RequestMapping(value="/importGold",method={RequestMethod.GET,RequestMethod.POST}) 
  @ResponseBody
  public  ResponceObject<Object>  importGold(HttpServletRequest request) throws Exception { 
	UserVo user = (UserVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	  ResponceObject<Object> responceObject = new ResponceObject<Object>();
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;    
      System.out.println("通过传统方式form表单提交方式导入excel文件！");  
    
      InputStream in =null;  
      List<List<Object>> listob = null;  
      MultipartFile file = multipartRequest.getFile("upfile");  
      if(file.isEmpty()){  
          throw new Exception("文件不存在！");  
      }  
      in = file.getInputStream();  
      listob = new ImportExcelUtil().getBankListByExcelGold(in,file.getOriginalFilename());  
      in.close();  

      // 该处可调用service相应方法进行数据保存到数据库中，现只对数据输出  
      // 计数
      int number = 0;
      for (int i = 0; i < listob.size(); i++) {  
          List<Object> lo = listob.get(i);  
          List<VitureGold> list =new LinkedList<VitureGold>();
          VitureGold vituregold =new VitureGold();
          vituregold.setVITUREINVOCE(String.valueOf(lo.get(0)).trim());
          vituregold.setVITUREINVOCEGOLD(String.valueOf(lo.get(1)).trim());
          vituregold.setCREATER(user.getId());
          SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		  Date date =new Date();
		  vituregold.setCREATETIME(sdf.format(date));
		  list.add(vituregold);
		  iBillingService.insertGoldVoince(list);
		  number ++;
      }  
      responceObject.setResultMessage("成功导入数据"+number+"条");
      return responceObject;  
  }  
    
  /** 
   * 描述：通过 jquery.form.js 插件提供的ajax方式上传文件 
   * @param request 
   * @param response 
   * @throws Exception 
   */  
 /* @ResponseBody  
  @RequestMapping(value="/importGold",method={RequestMethod.GET,RequestMethod.POST})  
  public  void  ajaxUploadExcel(HttpServletRequest request,HttpServletResponse response) throws Exception {  
      MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;    
        
      System.out.println("通过 jquery.form.js 提供的ajax方式上传文件！");  
        
      InputStream in =null;  
      List<List<Object>> listob = null;  
      MultipartFile file = multipartRequest.getFile("upfile");  
      if(file.isEmpty()){  
          throw new Exception("文件不存在！");  
      }  
        
      in = file.getInputStream();  
      listob = new ImportExcelUtil().getBankListByExcel(in,file.getOriginalFilename());          
      //该处可调用service相应方法进行数据保存到数据库中，现只对数据输出  
      for (int i = 0; i < listob.size(); i++) {  
          List<Object> lo = listob.get(i);  
          Customer customer = new Customer();  
          customer.setVKORG(String.valueOf(lo.get(0)));
          customer.setSPART(String.valueOf(lo.get(1)));
          customer.setKUNRG(String.valueOf(lo.get(2)));
          customer.setCOMNAME(String.valueOf(lo.get(3)));
          customer.setNAME2(String.valueOf(lo.get(4)));
          customer.setPAYINGNAME(String.valueOf(lo.get(5)));
          customer.setADDRESS(String.valueOf(lo.get(6)));
          customer.setBANK(String.valueOf(lo.get(7)));
          customer.setBANKNUM(String.valueOf(lo.get(8)));
          customer.setBILLINGADDRESS(String.valueOf(lo.get(9)));
          customer.setPHONE(String.valueOf(lo.get(10)));
          customer.setPEOPLE(String.valueOf(lo.get(11)));
          customer.setBILLINGTYPE(String.valueOf(lo.get(12)));  
         System.out.println(customer.toString());
      }  
      PrintWriter out = null;  
      response.setCharacterEncoding("utf-8");  //防止ajax接受到的中文信息乱码  
      out = response.getWriter();  
      out.print("文件导入成功！");  
      out.flush();  
      out.close();  
  }  */
}