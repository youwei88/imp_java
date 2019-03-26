package service.billing.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import common.util.StringUtils;
import dao.IBillingDao;
import domain.RequestObject;
import domain.ResponceObject;
import excel.ExportUtil;
import pojo.BaseBilling;
import pojo.Billing;
import pojo.Customer;
import pojo.ExportBilling;
import pojo.ExportBillingAudit;
import pojo.GoldVirtureInvoice;
import pojo.MergeBilling;
import pojo.MergeBillingNo;
import pojo.MergePojoBase;
import pojo.OrgPojo;
import pojo.SapImportPojo;
import pojo.UpdateEtFirst;
import pojo.VitureGold;
import pojo.test;
import service.billing.IBillingService;

@Service
public class BillingService implements IBillingService {

	@Autowired
	IBillingDao iBillingDao;

	@Override
	public ResponceObject<Billing> getBillings(RequestObject requestObject) {
		// TODO Auto-generated method stub
		ResponceObject<Billing> responceObject = new ResponceObject<>();
		PageHelper.startPage(requestObject.getPageNum(), requestObject.getPageSize());
		List<Billing> billings = iBillingDao.getBillings(requestObject);
		PageInfo<Billing> p = new PageInfo<Billing>(billings);
		responceObject.setTotal(p.getPages());
		responceObject.setLists(billings);
		if (0 != responceObject.getLists().size()) {
			responceObject.setResultCode(1);
			responceObject.setResultMessage("获取查询发票信息成功");
		}
		return responceObject;
	}

	/**
	 * @desc 根据条件获取未开票的数据
	 */
	@Override
	/*public ResponceObject<Billing> getOffBillings(RequestObject requestObject) {
		// TODO Auto-generated method stub
		ResponceObject<Billing> responceObject = new ResponceObject<>();
		List<Billing> billings = iBillingDao.getOffBillings(requestObject);
		
		responceObject.setLists(billings);
		

		if (0 != responceObject.getLists().size()) {
			responceObject.setResultCode(1);
			responceObject.setResultMessage("获取查询冲销组织信息成功");
		}
		return responceObject;
	}*/
	public ResponceObject<Billing> getOffBillings(RequestObject requestObject) {
		ResponceObject<Billing> responceObject = new ResponceObject<>();
		
		// 先查询含税总价小于等于0的付款方
		List<String> kunrgs = iBillingDao.getNULLHSZJKunrgs(requestObject);
		StringBuilder kunrgsStr = new StringBuilder("1");
		if (kunrgs.size() != 0) {
			for (String s : kunrgs) {
				kunrgsStr.append(",").append(s);
			}
		}
		// 过滤掉上面查出的付款方
		requestObject.setKunrgsStr(kunrgsStr.toString());
		List<Billing> billings = iBillingDao.getOffBillings(requestObject);
		responceObject.setLists(billings);
		
		// 当前的全部发票id
		/*if (0 != billings.size()) {
			StringBuilder mergeIds = new StringBuilder(String.valueOf(billings.get(0).getId()));
			for (int i = 1; i < billings.size() ; i++) {
				mergeIds.append(",").append(billings.get(i).getId());
			}
			responceObject.setMergeIds(mergeIds.toString());
		}*/

		if (0 != responceObject.getLists().size()) {
			responceObject.setResultCode(1);
			responceObject.setResultMessage("获取查询冲销组织信息成功");
		}
		return responceObject;
	}

	@Override
	public ResponceObject<SapImportPojo> selectSapImport() {
		// TODO Auto-generated method stub
		ResponceObject<SapImportPojo> responceObject = new ResponceObject<>();
		responceObject.setLists(iBillingDao.selectSapImport());
		// responceObject.setLists();
		if (0 != responceObject.getLists().size()) {
			responceObject.setResultCode(1);
			responceObject.setResultMessage("获取查询导入信息成功");
		}
		return responceObject;
	}

	/**
	 * 根据ID控制隐藏行
	 */
	@Override
	public ResponceObject<Object> updatebillingstatus(RequestObject requestObject) {
		ResponceObject<Object> responceObject = new ResponceObject<Object>();
		// TODO Auto-generated method stub
		Integer i = iBillingDao.updatebillingstatus(requestObject);
		if (null != i) {
			responceObject.setResultCode(1);
			responceObject.setResultMessage("隐藏订单成功");
		} else {
			responceObject.setResultCode(0);
			responceObject.setResultMessage("隐藏订单失败");
		}
		return responceObject;
	}

	/**
	 * @desc 导出excel  导出单月份全部数据
	 * 
	 */
	@Override
	public void exportExcel(RequestObject requestObject, String name, String[] titles,
			ServletOutputStream outputStream) {
		// TODO Auto-generated method stub
		List<Billing> billings = iBillingDao.getoffBillingsMounth(requestObject);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 创建一个workbook 对应一个excel应用文件
		XSSFWorkbook workBook = new XSSFWorkbook();
		// 在workbook中添加一个sheet,对应Excel文件中的sheet
		XSSFSheet sheet = workBook.createSheet(name);
		ExportUtil exportUtil = new ExportUtil(workBook, sheet);
		XSSFCellStyle headStyle = exportUtil.getHeadStyle();
		XSSFCellStyle bodyStyle = exportUtil.getBodyStyle();
		// 构建表头
		XSSFRow headRow = sheet.createRow(0);
		XSSFCell cell = null;
		for (int i = 0; i < titles.length; i++) {
			cell = headRow.createCell(i);
			cell.setCellStyle(headStyle);
			cell.setCellValue(titles[i]);
		}
		// 构建表体数据
		if (billings != null && billings.size() > 0) {
			for (int j = 0; j < billings.size(); j++) {
				XSSFRow bodyRow = sheet.createRow(j + 1);
				Billing billing = billings.get(j);
				// 序列号
				cell = bodyRow.createCell(0);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getId());

				// 发票号
				cell = bodyRow.createCell(1);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getVbeln());

				// 发票行号
				cell = bodyRow.createCell(2);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getPosnr());

				// 发票类型
				cell = bodyRow.createCell(3);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getFkart());

				// 发票类型描述
				cell = bodyRow.createCell(4);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getFkart_t());

				// 发货单
				cell = bodyRow.createCell(5);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getVgbel());

				// 销售凭证
				cell = bodyRow.createCell(6);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getAubel());

				// 销售组织
				cell = bodyRow.createCell(7);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getVkorg());

				// 销售组织描述
				cell = bodyRow.createCell(8);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getVkorg_t());

				// 分销渠道
				cell = bodyRow.createCell(9);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getVtweg());

				// 分销渠道描述
				cell = bodyRow.createCell(10);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getVtweg_t());

				// 品牌
				cell = bodyRow.createCell(11);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getSpart());

				// 品牌描述
				cell = bodyRow.createCell(12);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getSpart_t());

				// 销售部门
				cell = bodyRow.createCell(13);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getVkbur());

				// 销售部门描述
				cell = bodyRow.createCell(14);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getVkbur_t());

				// 开票日期
				cell = bodyRow.createCell(15);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(sdf.format(billing.getFkdat()));

				// 售达方名称
				cell = bodyRow.createCell(16);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getKunag());

				// 开票日期
				cell = bodyRow.createCell(17);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getName1());

				// 售达方简称
				cell = bodyRow.createCell(18);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getSort1());

				// 付款方
				cell = bodyRow.createCell(19);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getKunrg());

				// 付款方名称
				cell = bodyRow.createCell(20);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getName2());

				// 物料号
				cell = bodyRow.createCell(21);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getMatnr());

				// 物料号描述
				cell = bodyRow.createCell(22);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getArktx());

				// 开票日期
				cell = bodyRow.createCell(23);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getFkimg());

				// 开票数量
				cell = bodyRow.createCell(24);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getVrkme());

				// 销售单价
				cell = bodyRow.createCell(25);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getHsdj());

				// 含税单价
				cell = bodyRow.createCell(26);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getHszj());

				// 开票日期
				cell = bodyRow.createCell(27);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getBtaux());

				// 开票日期
				cell = bodyRow.createCell(28);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getTaxde());

				// 开票日期
				cell = bodyRow.createCell(29);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(sdf.format(billing.getCpudt()));

				// 开票日期
				cell = bodyRow.createCell(30);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getCputm());

				// 开票日期
				cell = bodyRow.createCell(31);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getGhj());

				// 开票日期
				cell = bodyRow.createCell(32);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getJsj());

				// 开票日期
				cell = bodyRow.createCell(33);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getCbj());

				// 开票日期
				cell = bodyRow.createCell(34);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getBukrs());

			}
		}
		try {
			workBook.write(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @desc 导出已开票未审核的excel
	 * 
	 */
	@Override
	public void exportExcelKpNotSh(RequestObject requestObject, String name, String[] titles,
			ServletOutputStream outputStream) {
		// TODO Auto-generated method stub
		List<Billing> billings = iBillingDao.getBillingsKpNotSh(requestObject);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 创建一个workbook 对应一个excel应用文件
		XSSFWorkbook workBook = new XSSFWorkbook();
		// 在workbook中添加一个sheet,对应Excel文件中的sheet
		XSSFSheet sheet = workBook.createSheet(name);
		ExportUtil exportUtil = new ExportUtil(workBook, sheet);
		XSSFCellStyle headStyle = exportUtil.getHeadStyle();
		XSSFCellStyle bodyStyle = exportUtil.getBodyStyle();
		// 构建表头
		XSSFRow headRow = sheet.createRow(0);
		XSSFCell cell = null;
		for (int i = 0; i < titles.length; i++) {
			cell = headRow.createCell(i);
			cell.setCellStyle(headStyle);
			cell.setCellValue(titles[i]);
		}
		// 构建表体数据
		if (billings != null && billings.size() > 0) {
			for (int j = 0; j < billings.size(); j++) {
				XSSFRow bodyRow = sheet.createRow(j + 1);
				Billing billing = billings.get(j);
				// 序列号
				cell = bodyRow.createCell(0);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getId());

				// 发票号
				cell = bodyRow.createCell(1);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getVbeln());

				// 发票行号
				cell = bodyRow.createCell(2);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getPosnr());

				// 发票类型
				cell = bodyRow.createCell(3);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getFkart());

				// 发票类型描述
				cell = bodyRow.createCell(4);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getFkart_t());

				// 发货单
				cell = bodyRow.createCell(5);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getVgbel());

				// 销售凭证
				cell = bodyRow.createCell(6);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getAubel());

				// 销售组织
				cell = bodyRow.createCell(7);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getVkorg());

				// 销售组织描述
				cell = bodyRow.createCell(8);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getVkorg_t());

				// 分销渠道
				cell = bodyRow.createCell(9);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getVtweg());

				// 分销渠道描述
				cell = bodyRow.createCell(10);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getVtweg_t());

				// 品牌
				cell = bodyRow.createCell(11);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getSpart());

				// 品牌描述
				cell = bodyRow.createCell(12);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getSpart_t());

				// 销售部门
				cell = bodyRow.createCell(13);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getVkbur());

				// 销售部门描述
				cell = bodyRow.createCell(14);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getVkbur_t());

				// 开票日期
				cell = bodyRow.createCell(15);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(sdf.format(billing.getFkdat()));

				// 售达方名称
				cell = bodyRow.createCell(16);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getKunag());

				// 开票日期
				cell = bodyRow.createCell(17);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getName1());

				// 售达方简称
				cell = bodyRow.createCell(18);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getSort1());

				// 付款方
				cell = bodyRow.createCell(19);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getKunrg());

				// 付款方名称
				cell = bodyRow.createCell(20);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getName2());

				// 物料号
				cell = bodyRow.createCell(21);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getMatnr());

				// 物料号描述
				cell = bodyRow.createCell(22);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getArktx());

				// 开票日期
				cell = bodyRow.createCell(23);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getFkimg());

				// 开票数量
				cell = bodyRow.createCell(24);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getVrkme());

				// 销售单价
				cell = bodyRow.createCell(25);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getHsdj());

				// 含税总价
				cell = bodyRow.createCell(26);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getHszj());

				// 税率
				cell = bodyRow.createCell(27);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getBtaux());

				// 税额
				cell = bodyRow.createCell(28);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getTaxde());

				// 财务审核日期
				cell = bodyRow.createCell(29);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(sdf.format(billing.getCpudt()));

				// 财务审核时间
				cell = bodyRow.createCell(30);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getCputm());

				// 供货价
				cell = bodyRow.createCell(31);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getGhj());

				// 结算价
				cell = bodyRow.createCell(32);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getJsj());

				// 成本价
				cell = bodyRow.createCell(33);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getCbj());

				// 公司代码
				cell = bodyRow.createCell(34);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getBukrs());
				
				// 产品规格
				cell = bodyRow.createCell(35);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getNormt());
				
				// 总发票号
				cell = bodyRow.createCell(36);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(billing.getVitureinvoce());
				
				// 合并开票日期
				cell = bodyRow.createCell(37);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(sdf.format(billing.getMerge_time()));

			}
		}
		try {
			workBook.write(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @desc 获取品牌信息
	 */
	@Override
	public ResponceObject<Billing> selectSpart() {
		// TODO Auto-generated method stub
		ResponceObject<Billing> responceObject = new ResponceObject<>();

		List<Billing> billings = iBillingDao.selectSpart();
		PageInfo<Billing> p = new PageInfo<Billing>(billings);
		responceObject.setLists(billings);
		responceObject.setTotal(p.getPages());
		if (0 != responceObject.getLists().size()) {
			responceObject.setResultCode(1);
			responceObject.setResultMessage("获取品牌信息成功");
		}
		return responceObject;
	}

	/**
	 * 执行开票
	 */
	@Override
	public ResponceObject<Object> mergeBillings(RequestObject requestObject) {
		// TODO Auto-generated method stub
		ResponceObject<Object> responceObject = new ResponceObject<>();

		Integer i = iBillingDao.mergeBillings(requestObject);
		if (null != i) {
			responceObject.setResultCode(1);
			responceObject.setResultMessage("开票成功");
		}
		return responceObject;
	}

	/**
	 * 获取开票信息
	 */
	@Override
	public ResponceObject<MergeBilling> searchMergeBilling(RequestObject requestObject) {
		
		ResponceObject<MergeBilling> responceObject = new ResponceObject<>();
		DecimalFormat df = new DecimalFormat("######0.00");
		List<MergeBilling> lists = iBillingDao.searchMergeBilling(requestObject);
		for(int i=0;i<lists.size();i++)
		{
			Double hj = 0.0;
			Double cbl = 0.0;
			Double zdzkje = 0.0;
			Double zkhjg = 0.0;
			Double cbj = 0.0;
           for(int m =0;m<lists.get(i).getLists().size();m++)
           {
        		Billing mybill = lists.get(i).getLists().get(m);
    			cbj = mybill.getCbj() * Double.valueOf(mybill.getFkimg());
    			hj = mybill.getHszj() + hj;
    			if (mybill.getGhj() == 0) {
    				mybill.setGhj(mybill.getCbj() * 1.8);
    				zdzkje = mybill.getGhj() * Double.valueOf(mybill.getFkimg());
    			}
    			
           }
           cbl = cbj*100/ hj; 
		   lists.get(i).setHj(hj);
		   lists.get(i).setZdzkje(zdzkje);
		   lists.get(i).setCbl(cbl);
		   lists.get(i).setCblb(df.format(cbl) + "%");
		   lists.get(i).setZkhjg(hj-zdzkje);
           
           
		}
		
		
		if (!lists.isEmpty()) {
			responceObject.setResultCode(1);
			responceObject.setResultMessage("获取开票成功");
			responceObject.setLists(lists);
		}
		// TODO Auto-generated method stub
		return responceObject;
	}

	@Override
	public ResponceObject<Object> SaveupdateByVitureinvoce(UpdateEtFirst updateEtFirst) {
		// TODO Auto-generated method stub
		ResponceObject<Object> responceObject = new ResponceObject<>();
		Integer i = iBillingDao.SaveupdateByVitureinvoce(updateEtFirst);
		if (null != i) {
			responceObject.setResultCode(1);
			responceObject.setResultMessage("更新成功");
		}
		// TODO Auto-generated method stub
		return responceObject;
	}

	@Override
	public ResponceObject<Object> SaveUpdateByid(UpdateEtFirst updateEtFirst) {
		ResponceObject<Object> responceObject = new ResponceObject<>();
		Integer i = iBillingDao.SaveUpdateByid(updateEtFirst);
		if (null != i) {
			responceObject.setResultCode(1);
			responceObject.setResultMessage("更新成功");
		}
		// TODO Auto-generated method stub
		return responceObject;
	}

	@Override
	@Transactional
	public ResponceObject<Object> RollbackByVitureinvoce(UpdateEtFirst updateEtFirst) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		ResponceObject<Object> responceObject = new ResponceObject<>();
		/*List<Billing> lists = iBillingDao.selectidbyvitureinvoce(updateEtFirst);
		String idgroup = null;
		if (null != lists) {
			idgroup = String.valueOf(lists.get(0).getId());
			for (int i = 1; i < lists.size(); i++) {
				idgroup = idgroup + "','" + String.valueOf(lists.get(i).getId());
			}
		}
		HashMap<String, String> hashMap = new HashMap<>();
		hashMap.put("idgroup", idgroup);*/
		Integer status = iBillingDao.rollbackbyid(updateEtFirst);
		if (null != status) {
			responceObject.setResultCode(1);
			responceObject.setResultMessage("回滚成功");
		}
		// TODO Auto-generated method stub
		return responceObject;
	}

	@Override
	public ResponceObject<Object> RollbackByVitureinvoceAudit(UpdateEtFirst updateEtFirst) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		ResponceObject<Object> responceObject = new ResponceObject<>();
		/*List<Billing> lists = iBillingDao.selectidbyvitureinvoce(updateEtFirst);
		String idgroup = null;
		if (null != lists) {
			idgroup = String.valueOf(lists.get(0).getId());
			for (int i = 1; i < lists.size(); i++) {
				idgroup = idgroup + "','" + String.valueOf(lists.get(i).getId());
			}
		}
		HashMap<String, String> hashMap = new HashMap<>();
		hashMap.put("idgroup", idgroup);*/
		Integer status = iBillingDao.rollbackbyidaudit(updateEtFirst);
		if (null != status) {
			responceObject.setResultCode(1);
			responceObject.setResultMessage("回滚成功");
		}
		// TODO Auto-generated method stub
		return responceObject;
	}

	@Override
	public ResponceObject<MergeBilling> searchMergeBillingAudit(RequestObject requestObject) {
		// TODO Auto-generated method stub
		ResponceObject<MergeBilling> responceObject = new ResponceObject<>();
		DecimalFormat df = new DecimalFormat("######0.00");
		List<MergeBilling> lists = iBillingDao.searchMergeBillingAudit(requestObject);
		for(int i=0;i<lists.size();i++)
		{
			Double hj = 0.0;
			Double cbl = 0.0;
			Double zdzkje = 0.0;
			Double zkhjg = 0.0;
			Double cbj = 0.0;
           for(int m =0;m<lists.get(i).getLists().size();m++)
           {
        		Billing mybill = lists.get(i).getLists().get(m);
    			cbj = mybill.getCbj() * Double.valueOf(mybill.getFkimg());
    			hj = mybill.getHszj() + hj;
    			if (mybill.getGhj() == 0) {
    				mybill.setGhj(mybill.getCbj() * 1.8);
    				zdzkje = mybill.getGhj() * Double.valueOf(mybill.getFkimg());
    			}
    			
           }
           cbl = cbj*100/ hj; 
		   lists.get(i).setHj(hj);
		   lists.get(i).setZdzkje(zdzkje);
		   lists.get(i).setCbl(cbl);
		   lists.get(i).setCblb(df.format(cbl) + "%");
		   lists.get(i).setZkhjg(hj-zdzkje);
		}
		if (!lists.isEmpty()) {
			responceObject.setResultCode(1);
			responceObject.setResultMessage("获取开票成功");
			responceObject.setLists(lists);
		}
		// TODO Auto-generated method stub
		return responceObject;
	}

	@Override
	@Transactional
	public ResponceObject<Object> insertCustomer(List<Customer> list) {
		// TODO Auto-generated method stub
		int total =0;
		ResponceObject<Object> responceObject = new ResponceObject<>();
		String message ="";
		ArrayList<String> kunrglist =(ArrayList<String>) iBillingDao.selectKunrgForInport();
		ArrayList<String> kunrglist1 =(ArrayList<String>) iBillingDao.selectKunrgForInportAudit();
		/*for(int t =0;t<list.size();t++)
		{
			if(kunrglist.contains(list.get(t).getKUNRG()))
			{   RequestObject requestObject = new RequestObject();
			    requestObject.setKUNRG(list.get(t).getKUNRG());	
			    iBillingDao.deletecustomerbykunrg(requestObject);
			    message=list.get(t).getKUNRG()+","+message;
			}
		}
		if(""== message)
		{
			for(int t =0;t<list.size();t++)
			{
				if(kunrglist1.contains(list.get(t).getKUNRG()))
				{   RequestObject requestObject = new RequestObject();
				    requestObject.setKUNRG(list.get(t).getKUNRG());	
				    iBillingDao.deletecustomerbykunrg(requestObject);
				  //  message=list.get(t).getKUNRG()+","+message;
				}
			}*/
			
			for(int m=0;m<list.size();m++)
			{ 
				Customer cs = list.get(m);
				RequestObject requestObject = new RequestObject();
			    requestObject.setKUNRG(cs.getKUNRG());	
			    iBillingDao.deletecustomerbykunrg(requestObject);
				iBillingDao.insertCustomer(cs);  
				total=total+1;
			}
//		}	
		if (total != 0) {
			responceObject.setResultCode(1);
			responceObject.setResultMessage("批量插入客户成功"+total+"条");
		}else{
			responceObject.setResultCode(0);
			responceObject.setResultMessage("数据重复的有"+message);
		}
		return responceObject;
	}

	@Override
	public ResponceObject<Object> insertGoldVoince(List<VitureGold> list) {
		// TODO Auto-generated method stub
		ResponceObject<Object> responceObject = new ResponceObject<>();
		Integer i = iBillingDao.insertGoldVoinceNew(list);
		if (null != i) {
			responceObject.setResultCode(1);
			responceObject.setResultMessage("批量插入客户成功");
		}
		return responceObject;
	}

	@Override
	public ResponceObject<Customer> searchcustoms(Customer customer) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		ResponceObject<Customer> responceObject = new ResponceObject<>();
		List<Customer> customers = iBillingDao.searchcustoms(customer);
		if (null != customers) {
			responceObject.setLists(customers);
			responceObject.setResultCode(1);
			responceObject.setResultMessage("查询客户成功");
		}
		return responceObject;
	}

	@Override
	public void exportExcelForDoBill(RequestObject requestObject, String name, String[] titles,
			ServletOutputStream outputStream) {
		DecimalFormat df = new DecimalFormat("######0.00");
		int row = 1;
		List<MergeBilling> billings = iBillingDao.searchMergeBilling(requestObject);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 创建一个workbook 对应一个excel应用文件
		XSSFWorkbook workBook = new XSSFWorkbook();
		// 在workbook中添加一个sheet,对应Excel文件中的sheet
		XSSFSheet sheet = workBook.createSheet(name);
		// 控制颜色
		XSSFCellStyle my_style = workBook.createCellStyle();

		
		
		// We will now specify a background cell color */
		/*my_style.setFillPattern(XSSFCellStyle.FINE_DOTS);
		my_style.setFillBackgroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
*/
		ExportUtil exportUtil = new ExportUtil(workBook, sheet);
		XSSFCellStyle headStyle = exportUtil.getHeadStyle();
		XSSFCellStyle bodyStyle = exportUtil.getBodyStyle();
		// 构建表头
		XSSFRow headRow = sheet.createRow(0);
		XSSFCell cell = null;
		for (int i = 0; i < titles.length; i++) {
			cell = headRow.createCell(i);
			cell.setCellStyle(headStyle);
			cell.setCellValue(titles[i]);
		}
		// 构建表体数据
		if (billings != null && billings.size() > 0) {

			for (int j = 0; j < billings.size(); j++) {
				Double hj = 0.0;
				Double cbl = 0.0;
				Double zdzkje = 0.0;
				Double zkhjg = 0.0;
				Double cbj = 0.0;
				for (int m = 0; m < billings.get(j).getLists().size(); m++)

				{
					Billing mybill = billings.get(j).getLists().get(m);
					cbj = mybill.getCbj() * Double.valueOf(mybill.getFkimg());
					hj = mybill.getHszj() + hj;
					if (mybill.getGhj() == 0) {
						mybill.setGhj(mybill.getCbj() * 1.8);
						zdzkje = mybill.getGhj() * Double.valueOf(mybill.getFkimg());
					}
					XSSFRow bodyRow1 = sheet.createRow(row);
					row = row + 1;
					// 序列号
				
						cell = bodyRow1.createCell(0);
						cell.setCellStyle(bodyStyle);
						cell.setCellValue(mybill.getId());
			
					

					// 发票号
					cell = bodyRow1.createCell(1);
					cell.setCellStyle(bodyStyle);
					cell.setCellValue(mybill.getVbeln());

					// 发票行号
					cell = bodyRow1.createCell(2);
					cell.setCellStyle(bodyStyle);
					cell.setCellValue(mybill.getPosnr());

					// 发票类型
					cell = bodyRow1.createCell(3);
					cell.setCellStyle(bodyStyle);
					cell.setCellValue(mybill.getFkart());

					// 发票类型描述
					cell = bodyRow1.createCell(4);
					cell.setCellStyle(bodyStyle);
					cell.setCellValue(mybill.getFkart_t());

					// 发货单
					cell = bodyRow1.createCell(5);
					cell.setCellStyle(bodyStyle);
					cell.setCellValue(mybill.getVgbel());

					// 销售凭证
					cell = bodyRow1.createCell(6);
					cell.setCellStyle(bodyStyle);
					cell.setCellValue(mybill.getAubel());

					// 销售组织
					cell = bodyRow1.createCell(7);
					cell.setCellStyle(bodyStyle);
					cell.setCellValue(mybill.getVkorg());

					// 销售组织描述
					cell = bodyRow1.createCell(8);
					cell.setCellStyle(bodyStyle);
					cell.setCellValue(mybill.getVkorg_t());

					// 分销渠道
					cell = bodyRow1.createCell(9);
					cell.setCellStyle(bodyStyle);
					cell.setCellValue(mybill.getVtweg());

					// 分销渠道描述
					cell = bodyRow1.createCell(10);
					cell.setCellStyle(bodyStyle);
					cell.setCellValue(mybill.getVtweg_t());

					// 品牌
					cell = bodyRow1.createCell(11);
					cell.setCellStyle(bodyStyle);
					cell.setCellValue(mybill.getSpart());

					// 品牌描述
					cell = bodyRow1.createCell(12);
					cell.setCellStyle(bodyStyle);
					cell.setCellValue(mybill.getSpart_t());

					// 销售部门
					cell = bodyRow1.createCell(13);
					cell.setCellStyle(bodyStyle);
					cell.setCellValue(mybill.getVkbur());

					// 销售部门描述
					cell = bodyRow1.createCell(14);
					cell.setCellStyle(bodyStyle);
					cell.setCellValue(mybill.getVkbur_t());

					// 开票日期
					cell = bodyRow1.createCell(15);
					cell.setCellStyle(bodyStyle);
					cell.setCellValue(sdf.format(mybill.getFkdat()));

					// 售达方名称
					cell = bodyRow1.createCell(16);
					cell.setCellStyle(bodyStyle);
					cell.setCellValue(mybill.getKunag());

					// 开票日期
					cell = bodyRow1.createCell(17);
					cell.setCellStyle(bodyStyle);
					cell.setCellValue(mybill.getName1());

					// 售达方简称
					cell = bodyRow1.createCell(18);
					cell.setCellStyle(bodyStyle);
					cell.setCellValue(mybill.getSort1());

					// 付款方
					cell = bodyRow1.createCell(19);
					cell.setCellStyle(bodyStyle);
					cell.setCellValue(mybill.getKunrg());

					// 付款方名称
					cell = bodyRow1.createCell(20);
					cell.setCellStyle(bodyStyle);
					cell.setCellValue(mybill.getName2());

					// 物料号
					cell = bodyRow1.createCell(21);
					cell.setCellStyle(bodyStyle);
					cell.setCellValue(mybill.getMatnr());

					// 物料号描述
					cell = bodyRow1.createCell(22);
					cell.setCellStyle(bodyStyle);
					cell.setCellValue(mybill.getArktx());

					// 开票日期
					if(null!=mybill.getFkimg())
					{
						cell = bodyRow1.createCell(23);
						cell.setCellStyle(bodyStyle);
						cell.setCellValue(Double.valueOf(mybill.getFkimg()));
					}
				

					// 开票数量
					if(null!=mybill.getVrkme())
					{
						cell = bodyRow1.createCell(24);
						cell.setCellStyle(bodyStyle);
						cell.setCellValue(mybill.getVrkme());
					}
					

					// 销售单价
					if(null!=mybill.getHsdj())
					{
						cell = bodyRow1.createCell(25);
						cell.setCellStyle(bodyStyle);
						cell.setCellValue(mybill.getHsdj());

					}
					
					// 含税单价
					if(null!=mybill.getHszj())
					{
						cell = bodyRow1.createCell(26);
						cell.setCellStyle(bodyStyle);
						cell.setCellValue(mybill.getHszj());
					}
					

					// 开票日期
					if(null!=mybill.getBtaux())
					{
						cell = bodyRow1.createCell(27);
						cell.setCellStyle(bodyStyle);
						cell.setCellValue(mybill.getBtaux());
					}
				

					// 开票日期
					if(null!=mybill.getTaxde())
					{
						cell = bodyRow1.createCell(28);
						cell.setCellStyle(bodyStyle);
						cell.setCellValue(mybill.getTaxde());
					}
					

					// 开票日期
					if(null!=mybill.getCpudt())
					{
						cell = bodyRow1.createCell(29);
						cell.setCellStyle(bodyStyle);
						cell.setCellValue(sdf.format(mybill.getCpudt()));
					}
					

					// 开票日期
					if(null!=mybill.getCputm())
					{
						cell = bodyRow1.createCell(30);
						cell.setCellStyle(bodyStyle);
						cell.setCellValue(mybill.getCputm());
					}
					

					// 开票日期
					if(null!=mybill.getGhj())
					{
						cell = bodyRow1.createCell(31);
						cell.setCellStyle(bodyStyle);
						cell.setCellValue(mybill.getGhj());

					}
					
					// 开票日期
					if(null!=mybill.getJsj())
					{
						cell = bodyRow1.createCell(32);
						cell.setCellStyle(bodyStyle);
						cell.setCellValue(mybill.getJsj());
					}
				

					// 开票日期
					if(null!=mybill.getCbj())
					{
						cell = bodyRow1.createCell(33);
						cell.setCellStyle(bodyStyle);
						cell.setCellValue(mybill.getCbj());
					}
					

					// 开票日期
					if(null!=mybill.getBukrs())
					{
						cell = bodyRow1.createCell(34);
						cell.setCellStyle(bodyStyle);
						cell.setCellValue(mybill.getBukrs());
					}
				

					// 开发票名称
					if(null!=mybill.getName2())
					{
						cell = bodyRow1.createCell(35);
						cell.setCellStyle(bodyStyle);
						cell.setCellValue(mybill.getName2());
					}
				

					// 纳税人账号
					if(null!=mybill.getPayingnum())
					{
						cell = bodyRow1.createCell(36);
						cell.setCellStyle(bodyStyle);
						cell.setCellValue(mybill.getPayingnum());
					}
					

					// 购方地址及电话
					if(null!=mybill.getAddress())
					{
						cell = bodyRow1.createCell(37);
						cell.setCellStyle(bodyStyle);
						cell.setCellValue(mybill.getAddress());
					}
					

					// 开户银行及账号
					if(null!=mybill.getNewbank())
					{
						cell = bodyRow1.createCell(38);
						cell.setCellStyle(bodyStyle);
						cell.setCellValue(mybill.getNewbank());
					}
					

					// 开户银行及账号
					if(null!=mybill.getBilingtype())
					{
						cell = bodyRow1.createCell(39);
						cell.setCellStyle(bodyStyle);
						if(mybill.getBilingtype().equals("0"))
						{
							cell.setCellValue("补票");
						}else{
							cell.setCellValue("增票");
						}
					}
				
					

				}
				cbl = cbj * 100 / hj;
				XSSFRow bodyRow = sheet.createRow(row);
				row = row + 1;
				MergeBilling mergeBilling = billings.get(j);
				cell = bodyRow.createCell(0);
				cell.setCellStyle(headStyle);
				cell.setCellValue("IMP虚拟发票号:");
				cell = bodyRow.createCell(1);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(mergeBilling.getVITUREINVOCE());
				cell = bodyRow.createCell(2);
				cell.setCellStyle(headStyle);
				cell.setCellValue("整单折扣金额:");
				cell = bodyRow.createCell(3);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(df.format(zdzkje));
				cell = bodyRow.createCell(4);
				cell.setCellStyle(headStyle);
				cell.setCellValue("成本率:");
				cell = bodyRow.createCell(5);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(df.format(cbl) + "%");
				cell = bodyRow.createCell(6);
				cell.setCellStyle(headStyle);
				cell.setCellValue("折扣后价格:");
				cell = bodyRow.createCell(7);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(df.format(hj - zdzkje));
				cell = bodyRow.createCell(8);
				cell.setCellStyle(headStyle);
				cell.setCellValue("合计:");
				cell = bodyRow.createCell(9);
				cell.setCellValue(df.format(hj));
				cell.setCellStyle(bodyStyle);
				cell = bodyRow.createCell(10);
				cell.setCellStyle(headStyle);
				cell.setCellValue("开票日期:");
				cell = bodyRow.createCell(11);
				cell.setCellValue(sdf.format(mergeBilling.getMERGE_TIME()));
				cell.setCellStyle(bodyStyle);
			}
		}
		try {
			workBook.write(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void exportExcelForDoBillAudit(RequestObject requestObject, String name, String[] titles,
			ServletOutputStream outputStream) {
		List<OrgPojo> orglist = iBillingDao.searchOrgName();
		HashMap<String, String> orgmap =new HashMap<>();
		for(int org =0;org<orglist.size();org++){
			orgmap.put(orglist.get(org).getVkorg().trim(), orglist.get(org).getVkorg_t().trim());
		}
		DecimalFormat df = new DecimalFormat("######0.00");
		int row = 3;
		int rowid = 1;
		List<ExportBilling> billings = iBillingDao.searchMergeBillingAuditExport(requestObject);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 创建一个workbook 对应一个excel应用文件
		XSSFWorkbook workBook = new XSSFWorkbook();
		// 在workbook中添加一个sheet,对应Excel文件中的sheet
		XSSFSheet sheet = workBook.createSheet(name);
		// 控制颜色
		XSSFCellStyle my_style = workBook.createCellStyle();

		//设置字体
		  Font ztFont = workBook.createFont();   
        ztFont.setItalic(true);                     // 设置字体为斜体字   
        ztFont.setColor(Font.COLOR_RED);            // 将字体设置为“红色”   
        ztFont.setFontHeightInPoints((short)14);    // 将字体大小设置为18px   
        ztFont.setFontName("华文行楷");             // 将“华文行楷”字体应用到当前单元格上   
       // ztFont.setUnderline(Font.U_DOUBLE);         // 添加（Font.U_SINGLE单条下划线/Font.U_DOUBLE双条下划线）  
      //  ztFont.setBoldweight(Font.BOLDWEIGHT_BOLD); //字体加粗
//      ztFont.setStrikeout(true);                  // 是否添加删除线   
        my_style.setFont(ztFont);                    // 将字体应用到样式上面   
               // 样式应用到该单元格上   
		
		
		// We will now specify a background cell color */
		/*my_style.setFillPattern(XSSFCellStyle.FINE_DOTS);
		my_style.setFillBackgroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
*/
		ExportUtil exportUtil = new ExportUtil(workBook, sheet);
		XSSFCellStyle headStyle = exportUtil.getHeadStyle();
		XSSFCellStyle bodyStyle = exportUtil.getBodyStyle();
		XSSFRow titleRow = sheet.createRow(1);
		XSSFCell cell = null;
		cell = titleRow.createCell(0);
		cell.setCellStyle(headStyle);
		cell.setCellValue("申请部门");
		cell = titleRow.createCell(1);
		cell.setCellStyle(my_style);
		String vkorg = "";
		for (String s : requestObject.getVKORG().split(",")) {
			vkorg += "；" + orgmap.get(s);
		}
//		cell.setCellValue(orgmap.get(requestObject.getVKORG()));
		cell.setCellValue(vkorg.substring(1));
		
		// 构建表头
		XSSFRow headRow = sheet.createRow(2);
	
		for (int i = 0; i < titles.length; i++) {
			cell = headRow.createCell(i);
			cell.setCellStyle(headStyle);
			cell.setCellValue(titles[i]);
		}
		// 构建表体数据
		if (billings != null && billings.size() > 0) {

			for (int j = 0; j < billings.size(); j++) {
				/*Double hj = 0.0;
				Double cbl = 0.0;
				Double zdzkje = 0.0;
				Double zkhjg = 0.0;
				Double cbj = 0.0;
				ExportBilling exportBilling =new ExportBilling();
				 for(int n = 0;n<billings.get(j).getLists().size(); n++)
				    {
				    	
				    	ExportBillingAudit mybilljisuan = billings.get(j).getLists().get(n);
						cbj = mybilljisuan.getCBJ() * Double.valueOf(mybilljisuan.getFKIMG())+cbj;
						hj = mybilljisuan.getHSZJ() + hj;
						if (mybilljisuan.getGHJ() == 0) {
							mybilljisuan.setGHJ(mybilljisuan.getCBJ() * 1.8);
							zdzkje = mybilljisuan.getGHJ() * Double.valueOf(mybilljisuan.getFKIMG())+zdzkje;
						}
						
				    }
				     cbl = cbj * 100 / hj;
					exportBilling.setCbj(cbj);
					exportBilling.setCbl(cbl);
					exportBilling.setHj(hj);
					exportBilling.setZdzkje(zdzkje);
					exportBilling.setZkhjg(hj - zdzkje);*/
					Double zkhjgnew =0.00;
					Double Zkhjgnew1=0.00;
					Double hj1 =0.00;
					Double hj2 =0.00;//含税总价的合计
					Double cbhj=0.00;//成本合计
					for (int m = 0; m < billings.get(j).getLists().size(); m++)
					{  
						Double cbj1= 0.00;
						ExportBillingAudit mybill = billings.get(j).getLists().get(m);
						
/*						if(mybill !=null && mybill.getNAME2()!=null && mybill.getNAME2().equals("莱州市佳骏工贸有限公司A")){
*/						//||(mybill!=null &&mybill.getVITUREINVOCE().equals("20171211000878"))
						// 计算最终含税单价,针对折扣料号81005912将finalHszj设为0,且不展示在excel上
						Double finalHsdj = mybill.getHSDJ();
						if (mybill.getMATNR().contains("81005912")) {
							Zkhjgnew1=Zkhjgnew1+mybill.getHSZJ();
							
							if((m+1)==billings.get(j).getLists().size() || !billings.get(j).getLists().get(m).getVITUREINVOCE().equals(billings.get(j).getLists().get(m+1).getVITUREINVOCE())){
										XSSFRow bodyRow = sheet.createRow(row);
										// 序列号
										cell = bodyRow.createCell(0);
										cell.setCellStyle(bodyStyle);
										cell.setCellValue(rowid);
										cell = bodyRow.createCell(1);
										cell.setCellStyle(bodyStyle);
										cell.setCellValue(billings.get(j).getLists().get(m).getVITUREINVOCE());
										cell = bodyRow.createCell(7);
										cell.setCellStyle(bodyStyle);
										cell.setCellValue("折扣金额");
										cell = bodyRow.createCell(10);
										cell.setCellStyle(bodyStyle);
										cell.setCellValue(-1);							
										if((zkhjgnew-Zkhjgnew1)!=0){
											cell = bodyRow.createCell(11);
											cell.setCellStyle(bodyStyle);
											cell.setCellValue(zkhjgnew-Zkhjgnew1);
											cell = bodyRow.createCell(12);
											cell.setCellStyle(bodyStyle);
											cell.setCellValue(-1*(zkhjgnew-Zkhjgnew1));
										}
										// 开发票名称
										if(null!=billings.get(j).getLists().get(m).getCOMNAME())
										{
											cell = bodyRow.createCell(14);
											cell.setCellStyle(bodyStyle);
											cell.setCellValue(billings.get(j).getLists().get(m).getCOMNAME());
										}
										

										// 纳税人账号
										if(null!=billings.get(j).getLists().get(m).getPAYINGNUM())
										{
											cell = bodyRow.createCell(15);
											cell.setCellStyle(bodyStyle);
											cell.setCellValue(billings.get(j).getLists().get(m).getPAYINGNUM());
										}
										

										// 购方地址及电话
										if(null!=billings.get(j).getLists().get(m).getPAYINGNAME())
										{
											cell = bodyRow.createCell(16);
											cell.setCellStyle(bodyStyle);
											cell.setCellValue(billings.get(j).getLists().get(m).getPAYINGNAME());
										}
										

										// 开户银行及账号
										if(null!=billings.get(j).getLists().get(m).getBANK()||null!=billings.get(j).getLists().get(m).getBANKNUM())
										{
											cell = bodyRow.createCell(17);
											cell.setCellStyle(bodyStyle);
											cell.setCellValue(billings.get(j).getLists().get(m).getBANK()+billings.get(j).getLists().get(m).getBANKNUM());
										}
										

										// 开户银行及账号
										if(null!=billings.get(j).getLists().get(m).getBILLINGTYPE())
										{
											cell = bodyRow.createCell(18);
											cell.setCellStyle(bodyStyle);
											if(billings.get(j).getLists().get(m).getBILLINGTYPE().equals("0"))
											{
												cell.setCellValue("增票");
											}else{
												cell.setCellValue("普票");
											}
											
										}
										
										// 产品组
										if(StringUtils.isNotEmpty(mybill.getSPART()))
										{
											cell = bodyRow.createCell(19);
											cell.setCellStyle(bodyStyle);
											cell.setCellValue(mybill.getSPART());
										}
										
										// 销售组织
										if(StringUtils.isNotEmpty(mybill.getVKORG()))
										{
											cell = bodyRow.createCell(20);
											cell.setCellStyle(bodyStyle);
											cell.setCellValue(mybill.getVKORG());
										}
										
										cell = bodyRow.createCell(21);
										cell.setCellStyle(my_style);
										cell.setCellValue("成本率:");
										// 开户银行及账号
										if(hj2!=0)
										{
											cell = bodyRow.createCell(22);
											cell.setCellStyle(bodyStyle);
											cell.setCellValue(String.valueOf(cbhj*100/hj2*1.17)+"%");
//																	cell.setCellValue(String.valueOf(mybill.getCBJ()*100/hj2*1.17)+"%");
										}
										hj2=0.00;
										cbhj=0.00;
										Zkhjgnew1 =0.00;
										zkhjgnew =0.00;
										row = row + 1;
										rowid =rowid +1;
										
								}
							
							
							continue;
						} 
						/*else {
							finalHsdj = mybill.getHSDJ();
							if (finalHsdj==0) {
								finalHsdj = mybill.getGHJ();
								if (finalHsdj==0) {
									finalHsdj=mybill.getCBJ()*1.8;
								}
							}
						}*/
						/*cbj = mybill.getCBJ() * Double.valueOf(mybill.getFKIMG());*/
						//cbj = mybill.getCBJ();
						cbj1 = mybill.getCBJ()*1.8;
						cbhj=cbhj+mybill.getCBJ()*Double.valueOf(mybill.getFKIMG());
						hj2=hj2+mybill.getHSZJ();
						//hj = mybill.getHSZJ() + hj;
						hj1 =hj1+mybill.getHSZJ();
						/*if (mybill.getGHJ() == 0) {
							mybill.setGHJ(mybill.getCBJ() * 1.8);
						//	zdzkje = mybill.getGHJ() * Double.valueOf(mybill.getFKIMG());
						}*/
						XSSFRow bodyRow1 = sheet.createRow(row);
						row = row + 1;
						rowid =rowid +1;
						// 序列号
						cell = bodyRow1.createCell(0);
						cell.setCellStyle(bodyStyle);
						cell.setCellValue(rowid);
						
						/*// 金税发票号
						if(null!=mybill.getVITUREINVOCEGOLD())
						{
							cell = bodyRow1.createCell(1);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue(mybill.getVITUREINVOCEGOLD());
						}*/
						
						
						// IMP发票号
						if(null!=mybill.getVITUREINVOCE())
						{
							cell = bodyRow1.createCell(1);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue(mybill.getVITUREINVOCE());
						}
                         
						// 产品编码
						if(null!=mybill.getMATNR())
						{
							cell = bodyRow1.createCell(6);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue(Double.valueOf(mybill.getMATNR()));
						}

						// 产品名称
						if(null!=mybill.getARKTX())
						{
							cell = bodyRow1.createCell(7);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue(mybill.getARKTX());
						}
						
						//
						// 规格型号
						if(null!=mybill.getNORMT())
						{
							cell = bodyRow1.createCell(8);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue(mybill.getNORMT());
						}

						// 单位
						if(null!=mybill.getVRKME())
						{
							cell = bodyRow1.createCell(9);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue(mybill.getVRKME());
						}
						
						// 开票数量
						if(null!=mybill.getFKIMG())
						{
							cell = bodyRow1.createCell(10);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue(mybill.getFKIMG());
						}

						// 含税单价
//						if(null!=mybill.getHSDJ())
//						{
//							if(mybill.getHSDJ()==0)
//							{
//								mybill.setHSDJ(cbj1);
//							}
							cell = bodyRow1.createCell(11);
							cell.setCellStyle(bodyStyle);
//							cell.setCellValue((double)Math.round(mybill.getGHJ()*100)/100);
							cell.setCellValue((double)Math.round(finalHsdj*100)/100);
//						}
						
						// 含税总价
						if(null!=mybill.getHSZJ())
						{   Zkhjgnew1=Zkhjgnew1+mybill.getHSZJ();
							if(mybill.getHSZJ()==0)
							{
//								mybill.setHSZJ(mybill.getGHJ()*Double.valueOf(mybill.getFKIMG()));
								mybill.setHSZJ(finalHsdj*Double.valueOf(mybill.getFKIMG()));
								//zkhjgnew=zkhjgnew+mybill.getHSZJ();
							}
							//zkhjgnew=zkhjgnew+mybill.getGHJ()*Double.valueOf(mybill.getFKIMG());
//							zkhjgnew=zkhjgnew+((double)Math.round(mybill.getGHJ()*100)/100)*Double.valueOf(mybill.getFKIMG());
							zkhjgnew=zkhjgnew+((double)Math.round(finalHsdj*100)/100)*Double.valueOf(mybill.getFKIMG());
							cell = bodyRow1.createCell(12);
							cell.setCellStyle(bodyStyle);
//							cell.setCellValue(((double)Math.round(mybill.getGHJ()*100)/100)*Double.valueOf(mybill.getFKIMG()));
							cell.setCellValue(((double)Math.round(finalHsdj*100)/100)*Double.valueOf(mybill.getFKIMG()));
						}

						// 开发票名称
						if(null!=mybill.getCOMNAME())
						{
							cell = bodyRow1.createCell(14);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue(mybill.getCOMNAME());
						}else
						{
							cell = bodyRow1.createCell(14);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue(mybill.getKUNRG());
						}

						// 纳税人账号
						if(null!=mybill.getPAYINGNUM())
						{
							cell = bodyRow1.createCell(15);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue(mybill.getPAYINGNUM());
						}
						
						// 购方地址及电话
						if(null!=mybill.getPAYINGNAME())
						{
							cell = bodyRow1.createCell(16);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue(mybill.getPAYINGNAME());
						}

						// 开户银行及账号
						if(null!=mybill.getBANK()||null!=mybill.getBANKNUM())
						{
							cell = bodyRow1.createCell(17);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue(mybill.getBANK()+mybill.getBANKNUM());
						}

						// 开户银行及账号
						if(null!=mybill.getBILLINGTYPE())
						{
							cell = bodyRow1.createCell(18);
							cell.setCellStyle(bodyStyle);
							if(mybill.getBILLINGTYPE().equals("0"))
							{
								cell.setCellValue("增票");
							}else{
								cell.setCellValue("普票");
							}
						}
						
						// 产品组
						if(StringUtils.isNotEmpty(mybill.getSPART()))
						{
							cell = bodyRow1.createCell(19);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue(mybill.getSPART());
						}
						
						// 销售组织
						if(StringUtils.isNotEmpty(mybill.getVKORG()))
						{
							cell = bodyRow1.createCell(20);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue(mybill.getVKORG());
						}
						
						/*// 开户银行及账号
						if(null!=exportBilling.getZdzkje())
						{
							cell = bodyRow1.createCell(19);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue(exportBilling.getZdzkje());
						}
						
						
						
						
						// 开户银行及账号
						if(null!=exportBilling.getZkhjg())
						{
							cell = bodyRow1.createCell(21);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue(exportBilling.getZkhjg());
						}
						
						
						// 开户银行及账号
						if(null!=exportBilling.getHj())
						{
							cell = bodyRow1.createCell(22);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue(exportBilling.getHj());
						}
						
						
						// 开户银行及账号
						if(null!=exportBilling.getMERGE_TIME())
						{
							cell = bodyRow1.createCell(23);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue(sdf.format(exportBilling.getMERGE_TIME()));
						}*/
						
						
						int m1=m+1;
						if(m1==billings.get(j).getLists().size() || !billings.get(j).getLists().get(m).getVITUREINVOCE().equals(billings.get(j).getLists().get(m+1).getVITUREINVOCE())){
/*							if()
							{*/
								XSSFRow bodyRow = sheet.createRow(row);
								// 序列号
								cell = bodyRow.createCell(0);
								cell.setCellStyle(bodyStyle);
								cell.setCellValue(rowid);
								cell = bodyRow.createCell(1);
								cell.setCellStyle(bodyStyle);
								cell.setCellValue(billings.get(j).getLists().get(m).getVITUREINVOCE());
								cell = bodyRow.createCell(7);
								cell.setCellStyle(bodyStyle);
								cell.setCellValue("折扣金额");
								cell = bodyRow.createCell(10);
								cell.setCellStyle(bodyStyle);
								cell.setCellValue(-1);							
								if((zkhjgnew-Zkhjgnew1)!=0){
									cell = bodyRow.createCell(11);
									cell.setCellStyle(bodyStyle);
									cell.setCellValue(zkhjgnew-Zkhjgnew1);
									cell = bodyRow.createCell(12);
									cell.setCellStyle(bodyStyle);
									cell.setCellValue(-1*(zkhjgnew-Zkhjgnew1));
								}
								// 开发票名称
								if(null!=billings.get(j).getLists().get(m).getCOMNAME())
								{
									cell = bodyRow.createCell(14);
									cell.setCellStyle(bodyStyle);
									cell.setCellValue(billings.get(j).getLists().get(m).getCOMNAME());
								}
								

								// 纳税人账号
								if(null!=billings.get(j).getLists().get(m).getPAYINGNUM())
								{
									cell = bodyRow.createCell(15);
									cell.setCellStyle(bodyStyle);
									cell.setCellValue(billings.get(j).getLists().get(m).getPAYINGNUM());
								}
								

								// 购方地址及电话
								if(null!=billings.get(j).getLists().get(m).getPAYINGNAME())
								{
									cell = bodyRow.createCell(16);
									cell.setCellStyle(bodyStyle);
									cell.setCellValue(billings.get(j).getLists().get(m).getPAYINGNAME());
								}
								

								// 开户银行及账号
								if(null!=billings.get(j).getLists().get(m).getBANK()||null!=billings.get(j).getLists().get(m).getBANKNUM())
								{
									cell = bodyRow.createCell(17);
									cell.setCellStyle(bodyStyle);
									cell.setCellValue(billings.get(j).getLists().get(m).getBANK()+billings.get(j).getLists().get(m).getBANKNUM());
								}
								

								// 开户银行及账号
								if(null!=billings.get(j).getLists().get(m).getBILLINGTYPE())
								{
									cell = bodyRow.createCell(18);
									cell.setCellStyle(bodyStyle);
									if(billings.get(j).getLists().get(m).getBILLINGTYPE().equals("0"))
									{
										cell.setCellValue("增票");
									}else{
										cell.setCellValue("普票");
									}
									
								}
								
								// 产品组
								if(StringUtils.isNotEmpty(mybill.getSPART()))
								{
									cell = bodyRow.createCell(19);
									cell.setCellStyle(bodyStyle);
									cell.setCellValue(mybill.getSPART());
								}
								
								// 销售组织
								if(StringUtils.isNotEmpty(mybill.getVKORG()))
								{
									cell = bodyRow.createCell(20);
									cell.setCellStyle(bodyStyle);
									cell.setCellValue(mybill.getVKORG());
								}
								
								cell = bodyRow.createCell(21);
								cell.setCellStyle(my_style);
								cell.setCellValue("成本率:");
								// 开户银行及账号
								if(hj2!=0)
								{
									cell = bodyRow.createCell(22);
									cell.setCellStyle(bodyStyle);
									cell.setCellValue(String.valueOf(cbhj*100/hj2*1.17)+"%");
//									cell.setCellValue(String.valueOf(mybill.getCBJ()*100/hj2*1.17)+"%");
								}
								hj2=0.00;
								cbhj=0.00;
								Zkhjgnew1 =0.00;
								zkhjgnew =0.00;
								row = row + 1;
								rowid =rowid +1;
								
/*							}*/
						}
						/*else{
							XSSFRow bodyRow = sheet.createRow(row);
							// 序列号
							cell = bodyRow.createCell(0);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue(rowid);
							cell = bodyRow.createCell(1);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue(billings.get(j).getLists().get(m).getVITUREINVOCE());
							cell = bodyRow.createCell(7);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue("折扣金额");
							cell = bodyRow.createCell(10);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue("-1");
							if((zkhjgnew-Zkhjgnew1)!=0)
							{
								cell = bodyRow.createCell(11);
								cell.setCellStyle(bodyStyle);
								cell.setCellValue(zkhjgnew-Zkhjgnew1);
								cell = bodyRow.createCell(12);
								cell.setCellStyle(bodyStyle);
								cell.setCellValue(-1*(zkhjgnew-Zkhjgnew1));
							}
							
							
							// 开发票名称
							if(null!=billings.get(j).getLists().get(m).getCOMNAME())
							{
								cell = bodyRow.createCell(14);
								cell.setCellStyle(bodyStyle);
								cell.setCellValue(billings.get(j).getLists().get(m).getCOMNAME());
							}
							

							// 纳税人账号
							if(null!=billings.get(j).getLists().get(m).getPAYINGNUM())
							{
								cell = bodyRow.createCell(15);
								cell.setCellStyle(bodyStyle);
								cell.setCellValue(billings.get(j).getLists().get(m).getPAYINGNUM());
							}
							

							// 购方地址及电话
							if(null!=billings.get(j).getLists().get(m).getPAYINGNAME())
							{
								cell = bodyRow.createCell(16);
								cell.setCellStyle(bodyStyle);
								cell.setCellValue(billings.get(j).getLists().get(m).getPAYINGNAME());
							}
							

							// 开户银行及账号
							if(null!=billings.get(j).getLists().get(m).getBANK()||null!=billings.get(j).getLists().get(m).getBANKNUM())
							{
								cell = bodyRow.createCell(17);
								cell.setCellStyle(bodyStyle);
								cell.setCellValue(billings.get(j).getLists().get(m).getBANK()+billings.get(j).getLists().get(m).getBANKNUM());
							}
							

							// 开户银行及账号
							if(null!=billings.get(j).getLists().get(m).getBILLINGTYPE())
							{
								cell = bodyRow.createCell(18);
								cell.setCellStyle(bodyStyle);
								if(billings.get(j).getLists().get(m).getBILLINGTYPE().equals("0"))
								{
									cell.setCellValue("增票");
								}else{
									cell.setCellValue("普票");
								}
								
							}
							
							// 产品组
							if(StringUtils.isNotEmpty(mybill.getSPART()))
							{
								cell = bodyRow.createCell(19);
								cell.setCellStyle(bodyStyle);
								cell.setCellValue(mybill.getSPART());
							}
							
							// 销售组织
							if(StringUtils.isNotEmpty(mybill.getVKORG()))
							{
								cell = bodyRow.createCell(20);
								cell.setCellStyle(bodyStyle);
								cell.setCellValue(mybill.getVKORG());
							}
							
							cell = bodyRow.createCell(21);
							cell.setCellStyle(my_style);
							cell.setCellValue("成本率:");
							// 开户银行及账号
							if(hj2!=0)
							{
								cell = bodyRow.createCell(22);
								cell.setCellStyle(bodyStyle);
								cell.setCellValue(String.valueOf(cbhj*100/hj2*1.17)+"%");
//								cell.setCellValue(String.valueOf(mybill.getCBJ()*100/hj2*1.17)+"%");
							}
							hj2=0.00;
							cbhj=0.00;
							zkhjgnew =0.00;
							Zkhjgnew1=0.00;
							row = row + 1;
							rowid =rowid +1;
						
						}*/
						
/*					}*/
					
				}
				
				
			}
		}
		try {
			workBook.write(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public ResponceObject<Billing> getUnOrgBilling(RequestObject requestObject) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		// PageHelper.startPage(requestObject.getPageNum(),
		// requestObject.getPageSize());
		ResponceObject<Billing> responceObject = new ResponceObject<>();
		List<Billing> billings = iBillingDao.getUnOrgBilling(requestObject);
		// PageInfo<Billing> p = new PageInfo<Billing>(billings);
		responceObject.setLists(billings);
		// responceObject.setTotal(p.getPages());
		if (0 != responceObject.getLists().size()) {
			responceObject.setResultCode(1);
			responceObject.setResultMessage("获取查询未冲销组织信息成功");
		}
		return responceObject;
	}

	@Override
	public ResponceObject<Object> upcustomerbyid(Customer customer) {
		// TODO Auto-generated method stub
		ResponceObject<Object> responceObject = new ResponceObject<Object>();
		Integer i = iBillingDao.upcustomerbyid(customer);
		if (null != i) {
			responceObject.setResultCode(1);
			responceObject.setResultMessage("更新用户成功");
		} else {
			responceObject.setResultCode(0);
			responceObject.setResultMessage("更新用户失败");
		}
		return responceObject;
	}

	@Override
	public ResponceObject<Object> selectKunrgForInport(Customer customer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> selectkunrgbyvitureinvoce(MergeBillingNo mergebilling) {
		// TODO Auto-generated method stub
		 
		
		return iBillingDao.selectkunrgbyvitureinvoce(mergebilling);
	}

	@Override
	public Integer insertintoetmergeno(MergeBillingNo mergebilling) {
		// TODO Auto-generated method stub
		return iBillingDao.insertintoetmergeno(mergebilling);
	}

	@Override
	public void exportMergeBillingAuditGold(RequestObject requestObject, String name,
			String[] titles, ServletOutputStream outputStream) {
		List<OrgPojo> orglist = iBillingDao.searchOrgName();
		HashMap<String, String> orgmap =new HashMap<>();
		for(int org =0;org<orglist.size();org++){
			orgmap.put(orglist.get(org).getVkorg().trim(), orglist.get(org).getVkorg_t().trim());
		}
		DecimalFormat df = new DecimalFormat("######0.00");
		int row = 3;
		int rowid = 1;
		List<ExportBilling> billings = iBillingDao.searchMergeBillingAuditExportgold(requestObject);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 创建一个workbook 对应一个excel应用文件
		XSSFWorkbook workBook = new XSSFWorkbook();
		// 在workbook中添加一个sheet,对应Excel文件中的sheet
		XSSFSheet sheet = workBook.createSheet(name);
		// 控制颜色
		XSSFCellStyle my_style = workBook.createCellStyle();

		//设置字体
		Font ztFont = workBook.createFont();   
        ztFont.setItalic(true);                     // 设置字体为斜体字   
        ztFont.setColor(Font.COLOR_RED);            // 将字体设置为“红色”   
        ztFont.setFontHeightInPoints((short)14);    // 将字体大小设置为18px   
        ztFont.setFontName("华文行楷");             // 将“华文行楷”字体应用到当前单元格上   
       // ztFont.setUnderline(Font.U_DOUBLE);         // 添加（Font.U_SINGLE单条下划线/Font.U_DOUBLE双条下划线）  
      //  ztFont.setBoldweight(Font.BOLDWEIGHT_BOLD); //字体加粗
//      ztFont.setStrikeout(true);                  // 是否添加删除线   
        my_style.setFont(ztFont);                    // 将字体应用到样式上面   
               // 样式应用到该单元格上   
		
		
		// We will now specify a background cell color */
		/*my_style.setFillPattern(XSSFCellStyle.FINE_DOTS);
		my_style.setFillBackgroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
*/
		ExportUtil exportUtil = new ExportUtil(workBook, sheet);
		XSSFCellStyle headStyle = exportUtil.getHeadStyle();
		XSSFCellStyle bodyStyle = exportUtil.getBodyStyle();
		XSSFRow titleRow = sheet.createRow(1);
		XSSFCell cell = null;
		cell = titleRow.createCell(0);
		cell.setCellStyle(headStyle);
		cell.setCellValue("申请部门");
		cell = titleRow.createCell(1);
		cell.setCellStyle(my_style);
		cell.setCellValue(orgmap.get(requestObject.getVKORG()));
		
		
		// 构建表头
		XSSFRow headRow = sheet.createRow(2);
	
		for (int i = 0; i < titles.length; i++) {
			cell = headRow.createCell(i);
			cell.setCellStyle(headStyle);
			cell.setCellValue(titles[i]);
		}
		// 构建表体数据
		if (billings != null && billings.size() > 0) {

			for (int j = 0; j < billings.size(); j++) {
				/*Double hj = 0.0;
				Double cbl = 0.0;
				Double zdzkje = 0.0;
				Double zkhjg = 0.0;
				Double cbj = 0.0;
				ExportBilling exportBilling =new ExportBilling();
				 for(int n = 0;n<billings.get(j).getLists().size(); n++)
				    {
				    	
				    	ExportBillingAudit mybilljisuan = billings.get(j).getLists().get(n);
						cbj = mybilljisuan.getCBJ() * Double.valueOf(mybilljisuan.getFKIMG())+cbj;
						hj = mybilljisuan.getHSZJ() + hj;
						if (mybilljisuan.getGHJ() == 0) {
							mybilljisuan.setGHJ(mybilljisuan.getCBJ() * 1.8);
							zdzkje = mybilljisuan.getGHJ() * Double.valueOf(mybilljisuan.getFKIMG())+zdzkje;
						}
						
				    }
				     cbl = cbj * 100 / hj;
					exportBilling.setCbj(cbj);
					exportBilling.setCbl(cbl);
					exportBilling.setHj(hj);
					exportBilling.setZdzkje(zdzkje);
					exportBilling.setZkhjg(hj - zdzkje);*/
					Double zkhjgnew =0.00;
					Double Zkhjgnew1=0.00;
					Double hj1 =0.00;
					Double hj2 =0.00;//含税总价的合计
					Double cbhj=0.00;//成本合计
					for (int m = 0; m < billings.get(j).getLists().size(); m++)
					{  
						Double cbj1= 0.00;
						ExportBillingAudit mybill = billings.get(j).getLists().get(m);
						/*cbj = mybill.getCBJ() * Double.valueOf(mybill.getFKIMG());*/
						//cbj = mybill.getCBJ();
						cbj1 = mybill.getCBJ()*1.8;
						cbhj=cbhj+mybill.getCBJ()*Double.valueOf(mybill.getFKIMG());
						hj2=hj2+mybill.getHSZJ();
						//hj = mybill.getHSZJ() + hj;
						hj1 =hj1+mybill.getHSZJ();
//						if (mybill.getGHJ() == 0) {
//							mybill.setGHJ(mybill.getCBJ() * 1.8);
//						//	zdzkje = mybill.getGHJ() * Double.valueOf(mybill.getFKIMG());
//						}
						XSSFRow bodyRow1 = sheet.createRow(row);
						row = row + 1;
						rowid =rowid +1;
						// 序列号
						cell = bodyRow1.createCell(0);
						cell.setCellStyle(bodyStyle);
						cell.setCellValue(rowid);
                        
						// 金税发票号
						if(null!=mybill.getVITUREINVOCEGOLD())
						{
							cell = bodyRow1.createCell(2);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue(mybill.getVITUREINVOCEGOLD());
						}
						
						
						
						// IMP发票号
						if(null!=mybill.getVITUREINVOCE())
						{
							cell = bodyRow1.createCell(1);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue(mybill.getVITUREINVOCE());
						}
						

						
                         
						// 产品编码
						if(null!=mybill.getMATNR())
						{
							cell = bodyRow1.createCell(7);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue(Double.valueOf(mybill.getMATNR()));
						}
						

						// 产品名称
						if(null!=mybill.getARKTX())
						{
							cell = bodyRow1.createCell(8);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue(mybill.getARKTX());
						}
						

						//
						// 规格型号
						if(null!=mybill.getNORMT())
						{
							cell = bodyRow1.createCell(9);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue(mybill.getNORMT());
						}
						

						// 单位
						if(null!=mybill.getVRKME())
						{
							cell = bodyRow1.createCell(10);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue(mybill.getVRKME());
						}
						

						// 开票数量
						if(null!=mybill.getFKIMG())
						{
							cell = bodyRow1.createCell(11);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue(mybill.getFKIMG());
						}
						
						// 计算最终含税单价,针对折扣料号81005912将finalHsdj设为0
						Double finalHsdj = 0.00;
						if (!mybill.getMATNR().replaceFirst("^0*", "").equals("81005912")) {
							finalHsdj = mybill.getHSDJ();
							if (finalHsdj==0) {
								finalHsdj = mybill.getGHJ();
								if (finalHsdj==0) {
									finalHsdj=mybill.getCBJ()*1.8;
								}
							}
						}
						
						// 含税单价
//						if(null!=mybill.getHSDJ())
//						{
//							if(mybill.getHSDJ()==0)
//							{
//								mybill.setHSDJ(cbj1);
//							}
							cell = bodyRow1.createCell(12);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue((double)Math.round(finalHsdj*100)/100);
//						}
						

						// 含税总价
						if(null!=mybill.getHSZJ())
						{   Zkhjgnew1=Zkhjgnew1+mybill.getHSZJ();
							if(mybill.getHSZJ()==0)
							{
								mybill.setHSZJ(finalHsdj*Double.valueOf(mybill.getFKIMG()));
								//zkhjgnew=zkhjgnew+mybill.getHSZJ();
							}
							zkhjgnew=zkhjgnew+((double)Math.round(finalHsdj*100)/100)*Double.valueOf(mybill.getFKIMG());
							cell = bodyRow1.createCell(13);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue(((double)Math.round(finalHsdj*100)/100)*Double.valueOf(mybill.getFKIMG()));
						}
						


						// 开发票名称
						if(null!=mybill.getCOMNAME())
						{
							cell = bodyRow1.createCell(15);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue(mybill.getCOMNAME());
						}else
						{
							cell = bodyRow1.createCell(15);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue(mybill.getKUNRG());
						}
						

						// 纳税人账号
						if(null!=mybill.getPAYINGNUM())
						{
							cell = bodyRow1.createCell(16);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue(mybill.getPAYINGNUM());
						}
						

						// 购方地址及电话
						if(null!=mybill.getPAYINGNAME())
						{
							cell = bodyRow1.createCell(17);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue(mybill.getPAYINGNAME());
						}
						

						// 开户银行及账号
						if(null!=mybill.getBANK()||null!=mybill.getBANKNUM())
						{
							cell = bodyRow1.createCell(18);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue(mybill.getBANK()+mybill.getBANKNUM());
						}
						

						// 开户银行及账号
						if(null!=mybill.getBILLINGTYPE())
						{
							cell = bodyRow1.createCell(19);
							cell.setCellStyle(bodyStyle);
							if(mybill.getBILLINGTYPE().equals("0"))
							{
								cell.setCellValue("增票");
							}else{
								cell.setCellValue("普票");
							}
							
						}
						
						
						/*// 开户银行及账号
						if(null!=exportBilling.getZdzkje())
						{
							cell = bodyRow1.createCell(19);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue(exportBilling.getZdzkje());
						}
						
						
						
						
						// 开户银行及账号
						if(null!=exportBilling.getZkhjg())
						{
							cell = bodyRow1.createCell(21);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue(exportBilling.getZkhjg());
						}
						
						
						// 开户银行及账号
						if(null!=exportBilling.getHj())
						{
							cell = bodyRow1.createCell(22);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue(exportBilling.getHj());
						}
						
						
						// 开户银行及账号
						if(null!=exportBilling.getMERGE_TIME())
						{
							cell = bodyRow1.createCell(23);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue(sdf.format(exportBilling.getMERGE_TIME()));
						}*/
						int m1=m+1;
						if(m1!=billings.get(j).getLists().size()){
							if(!billings.get(j).getLists().get(m).getVITUREINVOCE().equals(billings.get(j).getLists().get(m+1).getVITUREINVOCE()))
							{
								XSSFRow bodyRow = sheet.createRow(row);
								// 序列号
								cell = bodyRow.createCell(0);
								cell.setCellStyle(bodyStyle);
								cell.setCellValue(rowid);
								cell = bodyRow.createCell(1);
								cell.setCellStyle(bodyStyle);
								cell.setCellValue(billings.get(j).getLists().get(m).getVITUREINVOCE());
								cell = bodyRow.createCell(8);
								cell.setCellStyle(bodyStyle);
								cell.setCellValue("折扣金额");
								cell = bodyRow.createCell(11);
								cell.setCellStyle(bodyStyle);
								cell.setCellValue(-1);							
								if((zkhjgnew-Zkhjgnew1)!=0){
									cell = bodyRow.createCell(12);
									cell.setCellStyle(bodyStyle);
									cell.setCellValue(zkhjgnew-Zkhjgnew1);
									cell = bodyRow.createCell(13);
									cell.setCellStyle(bodyStyle);
									cell.setCellValue(-1*(zkhjgnew-Zkhjgnew1));
								}
								// 开发票名称
								if(null!=billings.get(j).getLists().get(m).getCOMNAME())
								{
									cell = bodyRow.createCell(15);
									cell.setCellStyle(bodyStyle);
									cell.setCellValue(billings.get(j).getLists().get(m).getCOMNAME());
								}
								

								// 纳税人账号
								if(null!=billings.get(j).getLists().get(m).getPAYINGNUM())
								{
									cell = bodyRow.createCell(16);
									cell.setCellStyle(bodyStyle);
									cell.setCellValue(billings.get(j).getLists().get(m).getPAYINGNUM());
								}
								

								// 购方地址及电话
								if(null!=billings.get(j).getLists().get(m).getPAYINGNAME())
								{
									cell = bodyRow.createCell(17);
									cell.setCellStyle(bodyStyle);
									cell.setCellValue(billings.get(j).getLists().get(m).getPAYINGNAME());
								}
								

								// 开户银行及账号
								if(null!=billings.get(j).getLists().get(m).getBANK()||null!=billings.get(j).getLists().get(m).getBANKNUM())
								{
									cell = bodyRow.createCell(18);
									cell.setCellStyle(bodyStyle);
									cell.setCellValue(billings.get(j).getLists().get(m).getBANK()+billings.get(j).getLists().get(m).getBANKNUM());
								}
								

								// 开户银行及账号
								if(null!=billings.get(j).getLists().get(m).getBILLINGTYPE())
								{
									cell = bodyRow.createCell(19);
									cell.setCellStyle(bodyStyle);
									if(billings.get(j).getLists().get(m).getBILLINGTYPE().equals("0"))
									{
										cell.setCellValue("增票");
									}else{
										cell.setCellValue("普票");
									}
									
								}
								
								cell = bodyRow.createCell(20);
								cell.setCellStyle(my_style);
								cell.setCellValue("成本率:");
								// 开户银行及账号
								if(hj2!=0)
								{
									cell = bodyRow.createCell(21);
									cell.setCellStyle(bodyStyle);
									cell.setCellValue(String.valueOf(cbhj*100/hj2*1.17)+"%");
								}
								hj2=0.00;
								cbhj=0.00;
								Zkhjgnew1 =0.00;
								zkhjgnew =0.00;
								row = row + 1;
								rowid =rowid +1;
								
							}
						}else{
							XSSFRow bodyRow = sheet.createRow(row);
							// 序列号
							cell = bodyRow.createCell(0);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue(rowid);
							cell = bodyRow.createCell(1);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue(billings.get(j).getLists().get(m).getVITUREINVOCE());
							cell = bodyRow.createCell(8);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue("折扣金额");
							cell = bodyRow.createCell(11);
							cell.setCellStyle(bodyStyle);
							cell.setCellValue("-1");
							if((zkhjgnew-Zkhjgnew1)!=0)
							{
								cell = bodyRow.createCell(12);
								cell.setCellStyle(bodyStyle);
								cell.setCellValue(zkhjgnew-Zkhjgnew1);
								cell = bodyRow.createCell(13);
								cell.setCellStyle(bodyStyle);
								cell.setCellValue(-1*(zkhjgnew-Zkhjgnew1));
							}
							
							
							// 开发票名称
							if(null!=billings.get(j).getLists().get(m).getCOMNAME())
							{
								cell = bodyRow.createCell(15);
								cell.setCellStyle(bodyStyle);
								cell.setCellValue(billings.get(j).getLists().get(m).getCOMNAME());
							}
							

							// 纳税人账号
							if(null!=billings.get(j).getLists().get(m).getPAYINGNUM())
							{
								cell = bodyRow.createCell(16);
								cell.setCellStyle(bodyStyle);
								cell.setCellValue(billings.get(j).getLists().get(m).getPAYINGNUM());
							}
							

							// 购方地址及电话
							if(null!=billings.get(j).getLists().get(m).getPAYINGNAME())
							{
								cell = bodyRow.createCell(17);
								cell.setCellStyle(bodyStyle);
								cell.setCellValue(billings.get(j).getLists().get(m).getPAYINGNAME());
							}
							

							// 开户银行及账号
							if(null!=billings.get(j).getLists().get(m).getBANK()||null!=billings.get(j).getLists().get(m).getBANKNUM())
							{
								cell = bodyRow.createCell(18);
								cell.setCellStyle(bodyStyle);
								cell.setCellValue(billings.get(j).getLists().get(m).getBANK()+billings.get(j).getLists().get(m).getBANKNUM());
							}
							

							// 开户银行及账号
							if(null!=billings.get(j).getLists().get(m).getBILLINGTYPE())
							{
								cell = bodyRow.createCell(19);
								cell.setCellStyle(bodyStyle);
								if(billings.get(j).getLists().get(m).getBILLINGTYPE().equals("0"))
								{
									cell.setCellValue("增票");
								}else{
									cell.setCellValue("普票");
								}
								
							}
							cell = bodyRow.createCell(20);
							cell.setCellStyle(my_style);
							cell.setCellValue("成本率:");
							// 开户银行及账号
							if(hj2!=0)
							{
								cell = bodyRow.createCell(21);
								cell.setCellStyle(bodyStyle);
								cell.setCellValue(String.valueOf(cbhj*100/hj2*1.17)+"%");
							}
							hj2=0.00;
							cbhj=0.00;
							zkhjgnew =0.00;
							Zkhjgnew1=0.00;
							row = row + 1;
							rowid =rowid +1;
						
						}
						
							
					/*}*/
					
				}
				
				
			}
		}
		try {
			workBook.write(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public ResponceObject<MergePojoBase> getOffBillingsNewMerge(RequestObject requestObject) {
		// TODO Auto-generated method stub
		ResponceObject<MergePojoBase> responceObject = new ResponceObject<MergePojoBase>();
		List<MergePojoBase>  listnew =  iBillingDao.getOffBillingsNewMerge(requestObject);
		List<MergePojoBase>   newlist =new LinkedList<MergePojoBase>(); 
		for(int m=0;m<listnew.size();m++){
			Double sum = 0.00;
		    for (int n =0;n<listnew.get(m).getBillings().size();n++)
		    {
		    	sum=sum+listnew.get(m).getBillings().get(n).getHszj();
		    }
		    listnew.get(m).setSUM(sum);
		    newlist.add(listnew.get(m));
		    sum=0.00;
		}
		
		if(null!=newlist){
			responceObject.setLists(newlist);
			responceObject.setResultMessage("获取开票信息成功");
			responceObject.setResultCode(1);
		}else{
			responceObject.setResultMessage("获取开票信息失败");
			responceObject.setResultCode(0);
		}
		return responceObject;
	}

	@Override
	public ResponceObject<Object> auditcustomer(RequestObject requestObject) {
		// TODO Auto-generated method stub
		ResponceObject<Object> responceObject = new  ResponceObject<Object>();
		Integer au =iBillingDao.auditcustomer(requestObject);
		if(null!=au)
		{
			responceObject.setResultMessage("审核客户成功");
			responceObject.setResultCode(1);
		}else{
			responceObject.setResultMessage("审核客户失败");
			responceObject.setResultCode(0);
		}
		return responceObject;
	}

	@Override
	public void selectallbytimeexportexcel(RequestObject requestObject, String name, String[] titles,
			ServletOutputStream outputStream) {
		// TODO Auto-generated method stub
		List<BaseBilling> myBillingList = iBillingDao.selectallbytimeexportexcel(requestObject);
		XSSFWorkbook workBook = new XSSFWorkbook();
		// 在workbook中添加一个sheet,对应Excel文件中的sheet
		XSSFSheet sheet = workBook.createSheet(name);
		// 控制颜色
		XSSFCellStyle my_style = workBook.createCellStyle();
		ExportUtil exportUtil = new ExportUtil(workBook, sheet);
		XSSFCellStyle headStyle = exportUtil.getHeadStyle();
		XSSFCellStyle bodyStyle = exportUtil.getBodyStyle();
		int row=0;	
		// 构建表头
		XSSFRow headRow = sheet.createRow(row);
		row=row+1;
	    XSSFCell cell = null;
		for (int i = 0; i < titles.length; i++) {
			cell = headRow.createCell(i);
			cell.setCellStyle(headStyle);
			cell.setCellValue(titles[i]);
		}
		for(int m=0;m<myBillingList.size();m++){

			XSSFRow bodyRow = sheet.createRow(row);
			row=row+1;
			//ID
			cell = bodyRow.createCell(0);
			cell.setCellStyle(bodyStyle);
	        cell.setCellValue(myBillingList.get(m).getID());
			
	        //SAP发票号
			cell = bodyRow.createCell(1);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getVBELN());
			
			//发票行号
			cell = bodyRow.createCell(2);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getPOSNR());
			
			//发票类型
			cell = bodyRow.createCell(3);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getFKART());
			
			//发票类型描述
			cell = bodyRow.createCell(4);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getFKART_T());
			
			//发货单
			cell = bodyRow.createCell(5);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getVGBEL());
			
			//销售凭证
			cell = bodyRow.createCell(6);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getAUBEL());
			
			//销售凭证
			cell = bodyRow.createCell(7);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getVKORG());
			
			//销售凭证
			cell = bodyRow.createCell(8);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getVKORG_T());
			

			//销售凭证
			cell = bodyRow.createCell(9);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getVTWEG());
			
			//销售凭证
			cell = bodyRow.createCell(10);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getVTWEG_T());
			
			//销售品牌
			cell = bodyRow.createCell(11);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getSPART());
			
			//销售凭证
			cell = bodyRow.createCell(12);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getSPART_T());
			
			//销售凭证
			cell = bodyRow.createCell(13);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getVKBUR());
			
			//销售凭证
			cell = bodyRow.createCell(14);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getVKBUR_T());
			
			//销售凭证
			cell = bodyRow.createCell(15);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getFKDAT());
			
			//销售凭证
			cell = bodyRow.createCell(16);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getKUNAG());
			
			//销售凭证
			cell = bodyRow.createCell(17);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getSORT1());
		
			//销售凭证
			cell = bodyRow.createCell(18);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getKUNRG());
			
			//销售凭证
			cell = bodyRow.createCell(19);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getNAME2());
			
			//销售凭证
			cell = bodyRow.createCell(20);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getMATNR());
			
			//销售凭证
			cell = bodyRow.createCell(21);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getARKTX());
			
			//销售凭证
			cell = bodyRow.createCell(22);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getFKIMG());
			
			//销售凭证
			cell = bodyRow.createCell(23);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getVRKME());
			
			//销售凭证
			cell = bodyRow.createCell(24);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getHSDJ());
			
			//销售凭证
			cell = bodyRow.createCell(25);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getHSZJ());
			
			//销售凭证
			cell = bodyRow.createCell(26);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getABRVW());
			
			//销售凭证
			cell = bodyRow.createCell(27);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getBEZEI());
			
			//销售凭证
			cell = bodyRow.createCell(28);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getBTAUX());
			
			//销售凭证
			cell = bodyRow.createCell(29);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getTAXDE());
			
			cell = bodyRow.createCell(30);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getGHJ());
			
			cell = bodyRow.createCell(31);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getJSJ());
			
			cell = bodyRow.createCell(32);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getCBJ());
						
		}
		
		try {
			workBook.write(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	@Override
	public void selectShNotFdExcel(RequestObject requestObject, String name, String[] titles,
			ServletOutputStream outputStream) {
		// TODO Auto-generated method stub
		List<BaseBilling> myBillingList = iBillingDao.selectShNotFdExcel(requestObject);
		DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		XSSFWorkbook workBook = new XSSFWorkbook();
		// 在workbook中添加一个sheet,对应Excel文件中的sheet
		XSSFSheet sheet = workBook.createSheet(name);
		// 控制颜色
		XSSFCellStyle my_style = workBook.createCellStyle();
		ExportUtil exportUtil = new ExportUtil(workBook, sheet);
		XSSFCellStyle headStyle = exportUtil.getHeadStyle();
		XSSFCellStyle bodyStyle = exportUtil.getBodyStyle();
		int row=0;	
		// 构建表头
		XSSFRow headRow = sheet.createRow(row);
		row=row+1;
	    XSSFCell cell = null;
		for (int i = 0; i < titles.length; i++) {
			cell = headRow.createCell(i);
			cell.setCellStyle(headStyle);
			cell.setCellValue(titles[i]);
		}
		for(int m=0;m<myBillingList.size();m++){

			XSSFRow bodyRow = sheet.createRow(row);
			row=row+1;
			//ID
			cell = bodyRow.createCell(0);
			cell.setCellStyle(bodyStyle);
	        cell.setCellValue(myBillingList.get(m).getID());
			
	        //SAP发票号
			cell = bodyRow.createCell(1);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getVBELN());
			
			//发票行号
			cell = bodyRow.createCell(2);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getPOSNR());
			
			//发票类型
			cell = bodyRow.createCell(3);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getFKART());
			
			//发票类型描述
			cell = bodyRow.createCell(4);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getFKART_T());
			
			//发货单
			cell = bodyRow.createCell(5);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getVGBEL());
			
			//销售凭证
			cell = bodyRow.createCell(6);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getAUBEL());
			
			//销售凭证
			cell = bodyRow.createCell(7);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getVKORG());
			
			//销售凭证
			cell = bodyRow.createCell(8);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getVKORG_T());
			

			//销售凭证
			cell = bodyRow.createCell(9);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getVTWEG());
			
			//销售凭证
			cell = bodyRow.createCell(10);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getVTWEG_T());
			
			//销售品牌
			cell = bodyRow.createCell(11);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getSPART());
			
			//销售凭证
			cell = bodyRow.createCell(12);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getSPART_T());
			
			//销售凭证
			cell = bodyRow.createCell(13);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getVKBUR());
			
			//销售凭证
			cell = bodyRow.createCell(14);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getVKBUR_T());
			
			//销售凭证
			cell = bodyRow.createCell(15);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getFKDAT());
			
			//销售凭证
			cell = bodyRow.createCell(16);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getKUNAG());
			
			//销售凭证
			cell = bodyRow.createCell(17);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getSORT1());
		
			//销售凭证
			cell = bodyRow.createCell(18);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getKUNRG());
			
			//销售凭证
			cell = bodyRow.createCell(19);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getNAME2());
			
			//销售凭证
			cell = bodyRow.createCell(20);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getMATNR());
			
			//销售凭证
			cell = bodyRow.createCell(21);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getARKTX());
			
			//销售凭证
			cell = bodyRow.createCell(22);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getFKIMG());
			
			//销售凭证
			cell = bodyRow.createCell(23);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getVRKME());
			
			//销售凭证
			cell = bodyRow.createCell(24);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getHSDJ());
			
			//销售凭证
			cell = bodyRow.createCell(25);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getHSZJ());
			
			//销售凭证
			cell = bodyRow.createCell(26);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getABRVW());
			
			//销售凭证
			cell = bodyRow.createCell(27);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getBEZEI());
			
			//销售凭证
			cell = bodyRow.createCell(28);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getBTAUX());
			
			//销售凭证
			cell = bodyRow.createCell(29);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getTAXDE());
			
			cell = bodyRow.createCell(30);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getGHJ());
			
			cell = bodyRow.createCell(31);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getJSJ());
			
			cell = bodyRow.createCell(32);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getCBJ());
			
			cell = bodyRow.createCell(33);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getBURKS());
			
			cell = bodyRow.createCell(34);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getNORMT());
			
			cell = bodyRow.createCell(35);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(myBillingList.get(m).getVITUREINVOCE());
			
			cell = bodyRow.createCell(36);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(df.format(myBillingList.get(m).getAUDITOR_TIME()));
						
		}
		
		try {
			workBook.write(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public String getLastBillingNO(String date) {
		return iBillingDao.getLastBillingNO(date);
	}

	@Override
	public void exportAllCutomers(String name, String[] titles,
			ServletOutputStream outputStream) {

		// TODO Auto-generated method stub
		List<Customer> customerList = iBillingDao.searchcustoms(null);
		XSSFWorkbook workBook = new XSSFWorkbook();
		// 在workbook中添加一个sheet,对应Excel文件中的sheet
		XSSFSheet sheet = workBook.createSheet(name);
		// 控制颜色
		XSSFCellStyle my_style = workBook.createCellStyle();
		ExportUtil exportUtil = new ExportUtil(workBook, sheet);
		XSSFCellStyle headStyle = exportUtil.getHeadStyle();
		XSSFCellStyle bodyStyle = exportUtil.getBodyStyle();
		int row=0;	
		// 构建表头
		XSSFRow headRow = sheet.createRow(row);
		row=row+1;
	    XSSFCell cell = null;
		for (int i = 0; i < titles.length; i++) {
			cell = headRow.createCell(i);
			cell.setCellStyle(headStyle);
			cell.setCellValue(titles[i]);
		}
		for(int m=0;m<customerList.size();m++){

			XSSFRow bodyRow = sheet.createRow(row);
			row=row+1;
	        //销售组织
			cell = bodyRow.createCell(0);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(customerList.get(m).getVKORG());
			
			//品牌
			cell = bodyRow.createCell(1);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(customerList.get(m).getSPART());
			
			//付款方
			cell = bodyRow.createCell(2);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(customerList.get(m).getKUNRG().replaceFirst("^0*", ""));
			
			//付款方名称
			cell = bodyRow.createCell(3);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(customerList.get(m).getNAME2());
			
			//创建人
			cell = bodyRow.createCell(4);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(customerList.get(m).getCREATER());
			
			//开票公司名称
			cell = bodyRow.createCell(5);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(customerList.get(m).getCOMNAME());
			
			//纳税人识别号
			cell = bodyRow.createCell(6);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(customerList.get(m).getPAYINGNUM());
			
			//纳税人名称
			cell = bodyRow.createCell(7);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(customerList.get(m).getPAYINGNAME());
			

			//地址电话
			cell = bodyRow.createCell(8);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(customerList.get(m).getADDRESS());
			
			//开户行
			cell = bodyRow.createCell(9);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(customerList.get(m).getBANK());
			
			//开户行账号
			cell = bodyRow.createCell(10);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(customerList.get(m).getBANKNUM());
			
			//发票地址
			cell = bodyRow.createCell(11);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(customerList.get(m).getBILLINGADDRESS());
			
			//电话号码
			cell = bodyRow.createCell(12);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(customerList.get(m).getPHONE());
			
			//发票快递签收
			cell = bodyRow.createCell(13);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(customerList.get(m).getPEOPLE());
			
			//发票类型
			cell = bodyRow.createCell(14);
			cell.setCellStyle(bodyStyle);
			cell.setCellValue(customerList.get(m).getBILLINGTYPE());
			
		}
		
		try {
			workBook.write(outputStream);
			outputStream.flush();
			outputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public ResponceObject<Object> batchupdatebillingstatus(Map<String, Object> params) {
		// TODO Auto-generated method stub
		ResponceObject<Object> responceObject = new ResponceObject<Object>();
		Integer i = iBillingDao.batchupdatebillingstatus(params);
		if (null != i) {
			responceObject.setResultCode(1);
			responceObject.setResultMessage("隐藏订单成功");
		} else {
			responceObject.setResultCode(0);
			responceObject.setResultMessage("隐藏订单失败");
		}
		return responceObject;
	}
}
