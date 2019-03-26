package dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

import domain.ET_001;
import domain.ET_002;
import domain.RequestObject;
import pojo.BaseBilling;
import pojo.Billing;
import pojo.Customer;
import pojo.ExportBilling;
import pojo.MergeBilling;
import pojo.MergeBillingNo;
import pojo.MergePojoBase;
import pojo.OrgPojo;
import pojo.SapImportPojo;
import pojo.UpdateEtFirst;
import pojo.VitureGold;

@MapperScan
public interface IBillingDao {
     public  List<Billing> getBillings(RequestObject requestObject);
      
     /**
      * 获取品牌
      * @param requestObject
      * @return
      */
     public  List<Billing> selectSpart();
     /**
      * 获取冲销信息
      * @param requestObject
      * @return
      */
     public  List<Billing> getOffBillings(RequestObject requestObject);
     
     /**
      * 获取单月份全部开票数据
      * @param requestObject
      * @return
      */
     public  List<Billing> getoffBillingsMounth(RequestObject requestObject);
     
     /**
      * 获取已开票未审核数据
      * @param requestObject
      * @return
      */
     public  List<Billing> getBillingsKpNotSh(RequestObject requestObject);
     
     /**
      * 向et001插入数据
      * @param et_001
      * @return
      */
     public  Integer insertEt_001(ET_001 et_001);
     
     /**
      * 向et002插入数据
      * @param et_002
      * @return
      */
     public  Integer insertEt_002(ET_002 et_002);
     
      /**
       * 根据日期获取订单号   用来删除重复导入问题
       * @param requestObject
       * @return
       */
     public  List<String>  getBillingNos(RequestObject requestObject);
     
     /**
      * 不开票的发票不需要重新同步
      * @param requestObject
      * @return
      */
    public  List<String>  getCancelBillingNos(RequestObject requestObject);
     
     /**
      * 根据时间删除001的数据
      * @param requestObject
      */
     public  void  deleteByCpudt(RequestObject requestObject);
     /**
      * sap导入正在执行条件
      */
     public void  insertSapImport(RequestObject requestObject);
     /**
      * sap导入正在执行条件
      */
     public void  deleteSapImport();
     /**
      * sap导入正在执行条件
      */
     public List<SapImportPojo>  selectSapImport();
     /**
      * 根据时间删除002的数据
      * @param requestObject
      */
     public void  deleteByCpudtTwo(RequestObject requestObject);
     
     /**
      * 根据et002的数据 更新et001
      * @param requestObject
      */
     public void upetByBillNo();
     
     
     /**
      * 根据ID更新发票状态 控制其显不显示
      * @param requestObject
      * @return
      */
     public Integer updatebillingstatus(RequestObject requestObject);
     
     /**
      * 根据ID更新发票状态 控制其显不显示
      * @param requestObject
      * @return
      */
     public Integer batchupdatebillingstatus(Map<String, Object> params);
     
     
     public Integer mergeBillings(RequestObject requestObject);
    
     /**
      * 获取开票信息
      * @param requestObject
      * @return
      */
     public List<MergeBilling>  searchMergeBilling(RequestObject requestObject);
     
     
     /**
      * 获取已审核开票信息
      * @param requestObject
      * @return
      */
     public List<MergeBilling>  searchMergeBillingAudit(RequestObject requestObject);
     /**
      * 根据iD更新数据
      * @param updateEtFirst
      * @return
      */
     public Integer SaveUpdateByid(UpdateEtFirst updateEtFirst);
     
     /**
      * 根据发票号审核
      * @param updateEtFirst
      * @return
      */
     public Integer SaveupdateByVitureinvoce(UpdateEtFirst updateEtFirst);
     
     /**
      * 根据发票号查询ID
      * @param updateEtFirst
      * @return
      */
     public List<Billing> selectidbyvitureinvoce(UpdateEtFirst updateEtFirst);

     /**
      * 根据ID号回滚开票
      * @param hashMap
      * @return
      */
     public Integer rollbackbyid(UpdateEtFirst updateEtFirst);
     
     /**
      * 根据ID号回滚已审核开票
      * @param hashMap
      * @return
      */
     public Integer rollbackbyidaudit(UpdateEtFirst updateEtFirst);
     
     /**
      * 批量插入Customer
      * @param list
      * @return
      */
     public Integer insertCustomer(Customer customer);
     
     
     /**
      * 批量插入VitureGold
      * @param list
      * @return
      */
     public Integer insertGoldVoince(List<VitureGold> list);
     
     
     /**
      * 根据付款方号码查询付款方信息
      * @param customer
      * @return
      */
     public List<Customer> searchcustoms(Customer customer);
     
     /**
      * 获取组织代码及名字
      * @return
      */
     public List<OrgPojo>  searchOrgName();

     /**
      * 获取为冲销组织信息
      * @param requestObject
      * @return
      */
     public List<Billing>  getUnOrgBilling(RequestObject requestObject);

     
     /**
      * 导出开票信息
      * @param requestObject
      * @return
      */
     public List<ExportBilling> searchMergeBillingAuditExport(RequestObject requestObject);
     
     /**
      * 根据ID更新客户数据
      * @param customer
      * @return
      */
     public Integer upcustomerbyid(Customer customer);
     
     /**
      * 查询出所有的付款方
      * @return
      */
     public List<String> selectKunrgForInport();
     
     /**
      * 根据发票号查询出所有的付款方
      * @param mergeBillingNo
      * @return
      */
     public List<String> selectkunrgbyvitureinvoce(MergeBillingNo mergeBillingNo);
     
     
     /**
      * 重新生成发票还
      * @param mergeBillingNo
      * @return
      */
     public Integer   insertintoetmergeno(MergeBillingNo mergeBillingNo);
     
     /**
      * 导出金税发票
      * @param requestObject
      * @return
      */
     public List<ExportBilling>  searchMergeBillingAuditExportgold(RequestObject requestObject);

     /**
      * 新的开票页面显示 逻辑
      * @param requestObject
      * @return
      */
     public List<MergePojoBase>   getOffBillingsNewMerge(RequestObject requestObject);
     
     /**
      * 审核客户
      * @param requestObject
      * @return
      */
     public Integer auditcustomer(RequestObject requestObject);
     
    
     /**
      * 根据付款方编码删除客户
      * @param requestObject
      * @return
      */
     public Integer deletecustomerbykunrg(RequestObject requestObject);
     
     
     
     /**
      * 查询出所有的未审核付款方
      * @return
      */
     public List<String> selectKunrgForInportAudit();
     
     /**
      * 导出出所有的已开票的数据
      * @return
      */
     public List<BaseBilling> selectallbytimeexportexcel(RequestObject requestObject);
     
     /**
      * 导出已审核未反导的数据
      * @return
      */
     public List<BaseBilling> selectShNotFdExcel(RequestObject requestObject);
     
     /**
      * 插入金税发票新版
      * @return
      */
     public Integer insertGoldVoinceNew(List<VitureGold> list);
     
     /**
      * 获取当天最新的流水号billingNO
      * @return
      */
     public String getLastBillingNO(String date);
     
     /**
      * 查询出条件内含税总价为0的付款方
      * @return
      */
     public List<String> getNULLHSZJKunrgs(RequestObject requestObject);
}
