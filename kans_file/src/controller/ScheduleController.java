package controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;

import controller.user.RoleController;
import domain.RequestObject;
import domain.ResponceObject;
import pojo.SapImportPojo;
import sap.ISapHelperNew;
import service.billing.IBillingService;

/**
 * @desc系统的各种定时任务 以及sap导入
 * @author 余林枫
 *
 */
@Controller
@RequestMapping("/sap")
public class ScheduleController {
	protected static Logger logger = Logger.getLogger(RoleController.class);
	
	@Autowired
	ISapHelperNew iSapHelper;
	
	@Autowired
	IBillingService iBillingService;
	
	public static  int i = 0;
	
	/**
	 * @author 余林枫
	 * @desc 每天凌晨4点执行  昨天的数据
	 * @
	 * 
	 */
	@Scheduled(cron="0 0 4 * * ?")
	public  void doSapImport(){
		i=0;
		logger.info("定时器开始执行");
	    Date date =new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		//System.out.println(df.format(date));// new Date()为获取当前系统时间
		String startTime = df.format(date);
		String endTime = df.format(date);
		List<String> list = new LinkedList<String>();
		list.add("6200");
		list.add("6100");
		list.add("6500");
		list.add("6300");
		list.add("6000");
		list.add("6400");
		list.add("6900");
		for(int i=0;i<list.size();i++)
		{   
			logger.info("开始导入数据"+list.get(i));
			iSapHelper.sapimport(startTime, endTime, list.get(i));
		}

	}
	
	/**
	 * @author 余林枫
	 * @desc 每天凌晨8点根据et002的数据更新 et001的数据
	 */
	@Scheduled(cron="0 0 8 * * ?")
	public  void updateEtFirst(){
		logger.info("8点开始根据et_002更新et_001");
		iSapHelper.updateEtFirst();
	}
	
/*	@Scheduled(cron="0/30 * * * * ?")
	public void test(){
		 Date date =new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		date = calendar.getTime();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		System.out.println(df.format(date));// new Date()为获取当前系统时间
		System.out.println("定时器执行");
	}*/
	
	@RequestMapping(value = "/sapimport", method = RequestMethod.POST,consumes = "application/json; charset=utf-8")
	@ResponseBody
	public  void  SapImportByUser(HttpServletRequest request ) throws ParseException{
		String result =null; 
		try {
        	InputStream in=  request.getInputStream();
        	BufferedReader read =new BufferedReader(new InputStreamReader(in));
        	result=read.readLine();  
	      } catch (Exception e) {
		// TODO: handle exception
	      }
		Gson gson =new Gson();
        RequestObject requestObject =gson.fromJson(result, RequestObject.class);
        List<String> list = new LinkedList<String>();
		list.add("6200");
		list.add("6100");
		list.add("6500");
		list.add("6300");
		list.add("6000");
		list.add("6400");
		list.add("6900");
		for(int i=0;i<list.size();i++)
		{   
			logger.info("开始导入数据"+list.get(i));
			iSapHelper.sapimport(requestObject.getTimebegin(), requestObject.getTimeend(), list.get(i));
		}
        iSapHelper.updateEtFirst();
	}
	
	
	/**
	 * 查询正在导入的数据
	 * @param request
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/sapimportselect", method = RequestMethod.POST,consumes = "application/json; charset=utf-8")
	@ResponseBody
	public  ResponceObject<SapImportPojo>  SapImportSelect(HttpServletRequest request) throws ParseException{
		ResponceObject<SapImportPojo> responceObject = new ResponceObject<>();
		responceObject = iBillingService.selectSapImport();
		return responceObject;
        
	}
	
	
}
