package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class RequestObject implements Serializable {
    private  int id ;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
   

	/**
	 * 开票ID结合
	 */
    private String mergeid;

    

	public String getMergeid() {
		return mergeid;
	}

	public void setMergeid(String mergeid) {
		this.mergeid = mergeid;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

     
	/**
     * 页数	
     */
	private int PageNum;
	
	/**
	 * 分页大小
	 */
	
	private int PageSize;
	
	private String timebegin;
	
	private String timeend;
	
	private String companyCode;
	

	/**
	 * 开始时间
	 */
	private Date StartTime;


	/**
	 * 结束时间
	 */
	private Date EndTime;
	
	/**
	 * SAP订单号
	 */
	private String SAPBillingNo;
	
	/**
	 * 物料号
	 */
	private String MATNR;
	
	/**
	 * 销售组织
	 */
	private String VKORG;
	
	/**
	 * 分销渠道
	 */
	private String VTWEG;
	
	/**
	 * 品牌代码
	 */
	private String SPART;
	
	/**
	 * 售达方
	 */
	private String KUNAG;
	
	
	/**
	 * 付款方
	 */
	private String KUNRG;
	
	private String IMPNUM;
	
	/**
	 * id数组
	 */
	private String[] idArray;
	
	
	public String[] getIdArray() {
		return idArray;
	}

	public void setIdArray(String[] idArray) {
		this.idArray = idArray;
	}

	public String getIMPNUM() {
		return IMPNUM;
	}

	public void setIMPNUM(String iMPNUM) {
		IMPNUM = iMPNUM;
	}

	public String getKUNRG() {
		return KUNRG;
	}

	public void setKUNRG(String kUNRG) {
		KUNRG = kUNRG;
	}

	private String VBELN;
	
	
	/**
	 * 开票状态
	 */
	private  int KPSTATUS;
	
	/**
	 * 发票号
	 */
	private String VITUREINVOCE;
	
	
	/**
	 * 开票人
	 */
	private String MERGE_PEOPLE;
	
	/**
	 * 开票时间
	 */
	private String MERGE_TIME;
	
	/**
	 * 审核人
	 */
	private String AUDITOR;
	
	/**
	 * 审核时间
	 */
	private String AUDITOR_TIME;
	
	/**
	 *财务审核时间
	 */
	private String cpudt;

	public String getAUDITOR_TIME() {
		return AUDITOR_TIME;
	}

	/**
	 * 审核状态
	 */
	private int AUDITSTATUS;
	
	
	
    public int getKPSTATUS() {
		return KPSTATUS;
	}

	public void setKPSTATUS(int kPSTATUS) {
		KPSTATUS = kPSTATUS;
	}

	public String getVITUREINVOCE() {
		return VITUREINVOCE;
	}

	public void setVITUREINVOCE(String vITUREINVOCE) {
		VITUREINVOCE = vITUREINVOCE;
	}

	public String getMERGE_PEOPLE() {
		return MERGE_PEOPLE;
	}

	public void setMERGE_PEOPLE(String mERGE_PEOPLE) {
		MERGE_PEOPLE = mERGE_PEOPLE;
	}

   
	public String getMERGE_TIME() {
		return MERGE_TIME;
	}

	public void setMERGE_TIME(String mERGE_TIME) {
		MERGE_TIME = mERGE_TIME;
	}

	public void setAUDITOR_TIME(String aUDITOR_TIME) {
		AUDITOR_TIME = aUDITOR_TIME;
	}

	public String getAUDITOR() {
		return AUDITOR;
	}

	public void setAUDITOR(String aUDITOR) {
		AUDITOR = aUDITOR;
	}

	

	public int getAUDITSTATUS() {
		return AUDITSTATUS;
	}

	public void setAUDITSTATUS(int aUDITSTATUS) {
		AUDITSTATUS = aUDITSTATUS;
	}

	private int[]  array;
	
	private String kunrgsStr;
    
	
	public int[] getArray() {
		return array;
	}

	public void setArray(int[] array) {
		this.array = array;
	}

	public int getPageNum() {
		return PageNum;
	}





	public void setPageNum(int pageNum) {
		PageNum = pageNum;
	}





	public int getPageSize() {
		return PageSize;
	}





	public void setPageSize(int pageSize) {
		PageSize = pageSize;
	}





	public String getTimebegin() {
		return timebegin;
	}





	public void setTimebegin(String timebegin) {
		this.timebegin = timebegin;
	}





	public String getTimeend() {
		return timeend;
	}





	public void setTimeend(String timeend) {
		this.timeend = timeend;
	}





	public String getCompanyCode() {
		return companyCode;
	}





	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}





	public Date getStartTime() {
		return StartTime;
	}





	public void setStartTime(Date startTime) {
		StartTime = startTime;
	}





	public Date getEndTime() {
		return EndTime;
	}





	public void setEndTime(Date endTime) {
		EndTime = endTime;
	}





	public String getSAPBillingNo() {
		return SAPBillingNo;
	}





	public void setSAPBillingNo(String sAPBillingNo) {
		SAPBillingNo = sAPBillingNo;
	}





	public String getMATNR() {
		return MATNR;
	}





	public void setMATNR(String mATNR) {
		MATNR = mATNR;
	}





	public String getVKORG() {
		return VKORG;
	}





	public void setVKORG(String vKORG) {
		VKORG = vKORG;
	}





	public String getVTWEG() {
		return VTWEG;
	}





	public void setVTWEG(String vTWEG) {
		VTWEG = vTWEG;
	}





	public String getSPART() {
		return SPART;
	}





	public void setSPART(String sPART) {
		SPART = sPART;
	}





	public String getKUNAG() {
		return KUNAG;
	}





	public void setKUNAG(String kUNAG) {
		KUNAG = kUNAG;
	}





	public String getVBELN() {
		return VBELN;
	}





	public void setVBELN(String vBELN) {
		VBELN = vBELN;
	}


	public String getKunrgsStr() {
		return kunrgsStr;
	}

	public void setKunrgsStr(String kunrgsStr) {
		this.kunrgsStr = kunrgsStr;
	}

	public String getCpudt() {
		return cpudt;
	}

	public void setCpudt(String cpudt) {
		this.cpudt = cpudt;
	}
	
	@Override
	public String toString() {
		return "RequestObject [PageNum=" + PageNum + ", PageSize=" + PageSize + ", timebegin=" + timebegin
				+ ", timeend=" + timeend + ", companyCode=" + companyCode + ", StartTime=" + StartTime + ", EndTime="
				+ EndTime + ", SAPBillingNo=" + SAPBillingNo + ", MATNR=" + MATNR + ", VKORG=" + VKORG + ", VTWEG="
				+ VTWEG + ", SPART=" + SPART + ", KUNAG=" + KUNAG + ", VBELN=" + VBELN + ", kunrgsStr=" + kunrgsStr + ", cpudt=" + cpudt + "]";
	}


	
	
}
