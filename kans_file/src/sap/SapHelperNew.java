package sap;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import com.sap.conn.jco.JCoRepository;
import com.sap.conn.jco.JCoTable;
import com.sap.conn.jco.rt.JCoMiddleware.Client;

import dao.IBillingDao;
import domain.ET_001;
import domain.ET_002;
import domain.RequestObject;
import vo.UserVo;

@Service
public class SapHelperNew implements ISapHelperNew {

	protected static Logger logger = Logger.getLogger("SapHelperNew");

	@Autowired
	IBillingDao iBillingDao;

	/*
	 * public Client getConn(){ Client client = null; try { client =
	 * JCO.createClient( "800", // SAP client "imp", // userid "imp123456", //
	 * password "ZH", // language (null for the default language)
	 * "172.18.172.47", // application server host name "00"); // system number
	 * client.connect(); }catch(Exception e){ e.printStackTrace(); } return
	 * client; }
	 */

	/*
	 * public Function getFunction(Client client,String apiName){ Repository
	 * repository = new Repository("PRD", client); IFunctionTemplate
	 * iFunctionTemplate = repository.getFunctionTemplate(apiName); Function
	 * function = iFunctionTemplate.getFunction(); return function; }
	 */

	/**
	 * 执行SAP数据导入 每天四点定时导入 手动啦先判断已经开票的 然后删掉没开票的
	 */
	@Transactional
	public void sapimport(String startTime, String endTime, String companyCode) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
		//UserVo user = (UserVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd");
		Date date =new Date();
		RequestObject requestObject = new RequestObject();
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
		List<String> lists = iBillingDao.getBillingNos(requestObject);
		List<String> cancelLists = iBillingDao.getCancelBillingNos(requestObject);
		lists.addAll(cancelLists);
		String VBELN = " ";
		for (int j = 0; j < lists.size(); j++) {
			VBELN = VBELN + "','" + lists.get(j).trim();
		}
		logger.debug("VBELN kpstatus=1 or isBill = 1 or isCancelBill = 2 between " + startTime + " and " + endTime
				+ " are: " + VBELN);
		requestObject.setVBELN(VBELN);
		iBillingDao.deleteByCpudt(requestObject);
		// iBillingDao.deleteByCpudtTwo(requestObject);

		// SAP接口更新
		// Client client = null;
		try {
			// 获取连接
			// client = getConn();
			JCoDestination dest = SapJco3Conn.getDestination();
			JCoRepository repository = dest.getRepository();
			JCoFunction fm = repository.getFunction("ZRFC_IMP_GET_INVOICE_OLD");
			if (fm == null) {
				throw new RuntimeException("Function does not exists in SAP system.");
			}
			// 传参
			fm.getImportParameterList().setValue("I_BDATE", startTime); // 全部获取
			fm.getImportParameterList().setValue("I_EDATE", endTime); // 全部获取
			fm.getImportParameterList().setValue("I_BUKRS", companyCode); // 全部获取
			// 执行
			fm.execute(dest);

			/** 1、获取返回的结果 **/
			JCoTable etTable1 = fm.getTableParameterList().getTable("ET_001");
			for (int i = 0; i < etTable1.getNumRows(); i++) {
				logger.debug(etTable1.getString("VBELN").trim());
				etTable1.setRow(i);
				ET_001 et001 = new ET_001();
				// 付款方90003,90000不导入，20170825添加
				if (lists.contains(etTable1.getString("VBELN").trim())
						|| etTable1.getString("KUNRG").trim().equals("0000090003")
						|| etTable1.getString("KUNRG").trim().equals("90003")
						|| etTable1.getString("KUNRG").trim().equals("0000090000")
						|| etTable1.getString("KUNRG").trim().equals("90000")) {
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
				if (etTable1.getString("VTWEG").trim().equals("10")) {
					et001.setVtweg_t("非直营");
				} else {
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
				// et001.setArktx(etTable1.getString("ARKTX").trim());
				// 特殊字符" "（unicode码为/ua0,并非一般的空格）转空字符"",特殊字符•或™换成对应的unicode码
				et001.setArktx(etTable1.getString("ARKTX").trim().replace(" ", "")
						.replace("•", "\\u" + Integer.toHexString('•')).replace("™", "\\u" + Integer.toHexString('™')));
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
				// et001.setNormt(etTable1.getString("NORMT").trim());
				// 特殊字符" "（unicode码为/ua0,非一般空格）转空字符""
				et001.setNormt(etTable1.getString("NORMT").trim().replace(" ", ""));
/*				et001.setCreater(10000);
				et001.setCreatetime(sdf3.format(date));
				et001.setUpdateter(10000);
				et001.setUpdatetime(sdf3.format(date));*/
				iBillingDao.insertEt_001(et001);

			}
			JCoTable etTable2 = fm.getTableParameterList().getTable("ET_002");
			for (int i = 0; i < etTable2.getNumRows(); i++) {
				etTable2.setRow(i);
				// SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				/**
				 * 先根据et002的数据改变 et001 默认为 et001 里面存在 et002 的数据
				 */
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
			// client.disconnect();
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
