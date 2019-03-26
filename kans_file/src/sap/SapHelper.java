/*package sap;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.Client;
import com.sap.mw.jco.JCO.Function;
import com.sap.mw.jco.JCO.ParameterList;
import com.sap.mw.jco.JCO.Repository;
import com.sap.mw.jco.JCO.Table;
import dao.IBillingDao;
import domain.ET_001;
import domain.ET_002;
import domain.RequestObject;
import pojo.Billing;

@Service
public class SapHelper implements ISapHelper {
	
	@Autowired
	IBillingDao iBillingDao;
	
	public Client getConn(){
		Client client = null;
		try {
				client = JCO.createClient(
						"800",         // SAP client 
						"imp",            // userid 
						"imp123456",           // password 
						"ZH",                 // language (null for the default language) 
						"172.18.172.47",     // application server host name 
						"00");                // system number 
				client.connect();
		}catch(Exception e){
			e.printStackTrace();
		}
		return client;
	}
	
	public Function getFunction(Client client,String apiName){
		Repository repository = new Repository("PRD", client);
		IFunctionTemplate iFunctionTemplate = 
				repository.getFunctionTemplate(apiName);
		Function function = iFunctionTemplate.getFunction();
		return function;
	}
	
	*//**
	 * 执行SAP数据导入
	 * 每天四点定时导入
	 *//*
	@Transactional
	public void sapimport(String startTime,String endTime,String companyCode) {
		SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd" );
		SimpleDateFormat sdf1 =   new SimpleDateFormat( "HH:mm:ss" );
		RequestObject requestObject =new RequestObject();
		try {
			requestObject.setTimebegin(startTime);
			requestObject.setTimeend(endTime);
			requestObject.setStartTime(sdf.parse(startTime));
			requestObject.setEndTime(sdf.parse(endTime));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		requestObject.setCompanyCode(companyCode);
		iBillingDao.insertSapImport(requestObject);
		List<String> lists =  iBillingDao.getBillingNos(requestObject);
		String VBELN = " " ;
		for(int j=0;j<lists.size();j++)
		{
			VBELN= VBELN+"','"+lists.get(j).trim();
		}
		requestObject.setVBELN(VBELN);
		iBillingDao.deleteByCpudt(requestObject);
		//iBillingDao.deleteByCpudtTwo(requestObject);

	 	
		// SAP接口更新
		Client client = null;
		try {
			//获取连接
			client = getConn();
			Function function = getFunction(client,"ZRFC_IMP_GET_INVOICE_OLD");
			*//**1、普通传参**//*
			ParameterList importParameterList = function.getImportParameterList();
			//传参
			//importParameterList.setValue("4500021262", "I_EBELN"); //采购订单号
			importParameterList.setValue(startTime,"I_BDATE");
			importParameterList.setValue(endTime,"I_EDATE");
			importParameterList.setValue(companyCode,"I_BUKRS");
			*//**2、Table形式传参
			ParameterList importParameterList = function.getTableParameterList();
			Table itTable = importParameterList.getTable("IT_TAB");
			itTable.appendRow();
			itTable.setValue(werksParam, "WERKS");
			**//*
			//执行
			client.execute(function);
			*//**1、获取返回的结果**//*
			ParameterList exportParam = function.getExportParameterList();
			String eType = exportParam.getString("E_TYPE");
			String eMsg = exportParam.getString("E_MSG");
			System.out.println("返回结果："+eType+" msg:"+eMsg);
			*//**2、获取以Table形式返回的结果
			 * 
			 *//*
			ParameterList exportTableList = function.getTableParameterList();
			//Table etTable = exportTableList.getTable("ET_EKKO");
			Table etTable1 = exportTableList.getTable("ET_001");
			for (int i = 0; i < etTable1.getNumRows(); i++) {
				etTable1.setRow(i);
			  //  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				ET_001 et001 = new ET_001();
				if(lists.contains(etTable1.getString("VBELN").trim()))
				{
					continue;
				}
				et001.setVbeln(etTable1.getString("VBELN").trim());
				et001.setPosnr(etTable1.getString("POSNR").trim());
				et001.setFkart(etTable1.getString("FKART").trim());
				et001.setFkart_t(etTable1.getString("FKART_T").trim());
				et001.setVgbel(etTable1.getString("VGBEL").trim());
				et001.setAubel(etTable1.getString("AUBEL").trim());
				et001.setAuart(etTable1.getString("AUART").trim());
				et001.setVkorg(etTable1.getString("VKORG").trim());
				et001.setVkorg_t(etTable1.getString("VKORG_T").trim());
				et001.setVtweg(etTable1.getString("VTWEG").trim());
				if(etTable1.getString("VTWEG").trim().equals("10"))
				{
					et001.setVtweg_t("非直营");
				}else
				{
					et001.setVtweg_t("直营");
				}
				
				et001.setSpart(etTable1.getString("SPART").trim());
				et001.setSpart_t(etTable1.getString("SPART_T").trim());
				et001.setVkbur(etTable1.getString("VKBUR").trim());
				et001.setVkbur_t(etTable1.getString("VKBUR_T").trim());
				et001.setFkdat(etTable1.getDate("FKDAT"));
                et001.setKunag(etTable1.getString("KUNAG").trim());
                et001.setName1(etTable1.getString("NAME1").trim());
                et001.setSort1(etTable1.getString("SORT1").trim());
                et001.setKunrg(etTable1.getString("KUNRG").trim());
                et001.setName2(etTable1.getString("NAME2").trim());
                et001.setMatnr(etTable1.getString("MATNR").trim());
                et001.setArktx(etTable1.getString("ARKTX").trim());
                et001.setFkimg(etTable1.getString("FKIMG").trim());
                et001.setVrkme(etTable1.getString("VRKME").trim());
                et001.setHsdj(etTable1.getDouble("HSDJ"));
                et001.setHszj(etTable1.getDouble("HSZJ"));
                et001.setAbrvw(etTable1.getString("ABRVW").trim());
                et001.setBezei(etTable1.getString("BEZEI").trim());
                et001.setBtaux(etTable1.getDouble("BTAUX"));
                et001.setTaxde(etTable1.getDouble("TAXDE"));
                et001.setCpudt(etTable1.getDate("CPUDT"));
                et001.setCputm(sdf1.format(etTable1.getDate("CPUTM")));
                et001.setGhj(etTable1.getDouble("GHJ"));
                et001.setJsj(etTable1.getDouble("JSJ"));
                et001.setCbj(etTable1.getDouble("CBJ"));
                et001.setPrdha(etTable1.getString("PRDHA").trim());
                et001.setBukrs(etTable1.getString("BUKRS").trim());
                et001.setNormt(etTable1.getString("NORMT").trim());
                iBillingDao.insertEt_001(et001);
                
			}
			Table etTable2 = exportTableList.getTable("ET_002");
			for (int i = 0; i < etTable2.getNumRows(); i++) {
				etTable2.setRow(i);
			  //  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				*//**
				 * 先根据et002的数据改变 et001 默认为  et001 里面存在  et002 的数据
				 *//*
				ET_002 et002 = new ET_002();
				et002.setVBELN(etTable2.getString("VBELN").trim());
				et002.setCPUDT(etTable2.getDate("CPUDT"));
		        et002.setCPUTM(sdf1.format(etTable2.getDate("CPUTM")));
		        iBillingDao.insertEt_002(et002);
			}
		    iBillingDao.deleteSapImport();	
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			client.disconnect();
			System.out.println("连接断开");
		}
		// 本地更新
	}

	@Override
	public void updateEtFirst() {
		// TODO Auto-generated method stub
		iBillingDao.upetByBillNo();
	}
	

}
*/