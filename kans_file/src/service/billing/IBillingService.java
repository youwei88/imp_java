package service.billing;


import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import domain.RequestObject;
import domain.ResponceObject;
import pojo.Billing;
import pojo.Customer;
import pojo.MergeBilling;
import pojo.MergeBillingNo;
import pojo.MergePojoBase;
import pojo.SapImportPojo;
import pojo.UpdateEtFirst;
import pojo.VitureGold;

public interface IBillingService {

	/**
	 * 查询开票信息
	 * @param hashMap
	 * @return
	 */
	public  ResponceObject<Billing> getBillings(RequestObject requestObject);
	
	/**
	 * 查询品牌信息
	 * @param requestObject
	 * @return
	 */
	public  ResponceObject<Billing> selectSpart();
   
	/**
	 * 查询冲销组织发票
	 * @param hashMap
	 * @return
	 */
    public   ResponceObject<Billing>  getOffBillings(RequestObject requestObject);
    
    /**
	 * 查询未冲销组织发票
	 * @param hashMap
	 * @return
	 */
    public   ResponceObject<Billing>  getUnOrgBilling(RequestObject requestObject);
    
    public   ResponceObject<SapImportPojo> selectSapImport();
    
    public ResponceObject<Object> updatebillingstatus(RequestObject requestObject);
    
    /**
     * 批量隐藏行
     * @param params
     * @author wangjinduo
     * @return
     */
    public ResponceObject<Object> batchupdatebillingstatus(Map<String, Object> params);
    
    /**
     * 导出冲销组织信息的Excel
     * @param requestObject
     * @param name
     * @param titles
     * @param outputStream
     */
    public void exportExcel(RequestObject requestObject,String name, String[] titles, ServletOutputStream outputStream);
    
    /**
     * 导出已开票未审核的Excel
     * @param requestObject
     * @param name
     * @param titles
     * @param outputStream
     */
    public void exportExcelKpNotSh(RequestObject requestObject,String name, String[] titles, ServletOutputStream outputStream);
    

    /**
     * 导出已开票的Excel
     * @param requestObject
     * @param name
     * @param titles
     * @param outputStream
     */
    public void exportExcelForDoBill(RequestObject requestObject,String name, String[] titles, ServletOutputStream outputStream);
   
    /**
     * 导出已审核开票的Excel
     * @param requestObject
     * @param name
     * @param titles
     * @param outputStream
     */
    public void exportExcelForDoBillAudit(RequestObject requestObject,String name, String[] titles, ServletOutputStream outputStream);
  
    
    /**
     * 开票
     * @param requestObject
     */
    public ResponceObject<Object> mergeBillings(RequestObject requestObject);
    
    /**
     * 获取开票信息
     * @param requestObject
     * @return
     */
    public ResponceObject<MergeBilling>  searchMergeBilling(RequestObject requestObject);
    
    /**
     * 根据ID更新发票号
     * @param updateEtFirst
     * @return
     */
    public ResponceObject<Object>  SaveUpdateByid(UpdateEtFirst updateEtFirst);
    
    /**
     * 根据发票号审核发票
     * @param updateEtFirst
     * @return
     */
    public ResponceObject<Object>  SaveupdateByVitureinvoce(UpdateEtFirst updateEtFirst);
    
    
    /**
     * 根据发票号回滚发票
     * @param updateEtFirst
     * @return
     */
    public ResponceObject<Object>  RollbackByVitureinvoce(UpdateEtFirst updateEtFirst);
    
    /**
     * 根据发票号回滚已审核的发票
     * @param updateEtFirst
     * @return
     */
    public ResponceObject<Object>  RollbackByVitureinvoceAudit(UpdateEtFirst updateEtFirst);
    
    /**
     * 获取已审核开票信息
     * @param requestObject
     * @return
     */
    public ResponceObject<MergeBilling>  searchMergeBillingAudit(RequestObject requestObject);
    
    /**
     * 金税发票
     * @param requestObject
     * @return
     */
    public void  exportMergeBillingAuditGold(RequestObject requestObject, String name, String[] titles,
			ServletOutputStream outputStream);
    
    /**
     *  批量插入Customer
     * @param list
     * @return
     */
    public ResponceObject<Object> insertCustomer(List<Customer> list);
    
    /**
     *  批量插入反审金税发票
     * @param list
     * @return
     */
    public ResponceObject<Object> insertGoldVoince(List<VitureGold> list);
    
    
    /**
     * 根据付款方号码查询付款方信息
     * @param customer
     * @return
     */
    public  ResponceObject<Customer> searchcustoms(Customer customer);
    
    /**
     * 根据ID跟新客户数据
     * @param customer
     * @return
     */
    public ResponceObject<Object> upcustomerbyid(Customer customer);
    
    /**
     * 查询出所有的付款方
     * @param customer
     * @return
     */
    public ResponceObject<Object> selectKunrgForInport(Customer customer);
    
    /**
     * 根据发票号查询出所有的付款方
     * @param mergebilling
     * @return
     */
    public List<String> selectkunrgbyvitureinvoce(MergeBillingNo mergebilling);


    /**
     * 插入发票号
     * @param mergebilling
     * @return
     */
    public Integer insertintoetmergeno(MergeBillingNo mergebilling);
    
    

    /**
     * 新的开票页面显示 逻辑
     * @param requestObject
     * @return
     */
    public  ResponceObject<MergePojoBase>  getOffBillingsNewMerge(RequestObject requestObject);
    
    /**
     * 审核客户
     * @param requestObject
     * @return
     */
    public  ResponceObject<Object> auditcustomer(RequestObject requestObject);
    
    
    /**
     * 导出出所有的已开票的数据
     * @return
     */
    public void selectallbytimeexportexcel(RequestObject requestObject, String name, String[] titles,
			ServletOutputStream outputStream);
    
    /**
     * 导出出所有的已开票的数据
     * @return
     */
    public void selectShNotFdExcel(RequestObject requestObject, String name, String[] titles,
			ServletOutputStream outputStream);
    
    /**
     * 获取当天最新的流水号billingNO
     * @return
     */
    public String getLastBillingNO(String date);
    
    /**
     * 导出所有的客户信息
     * @return
     */
    public void exportAllCutomers(String name, String[] titles,
			ServletOutputStream outputStream);
}
