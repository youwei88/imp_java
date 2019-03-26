package pojo;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public class MergeBilling implements Serializable {
    

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
	private Date MERGE_TIME;
	
	/**
	 * 审核人
	 */
	private String AUDITOR;
	
	/**
	 * 审核时间
	 */
	private Date AUDITOR_TIME;
	
	/**
	 * 审核状态
	 */
	private int AUDITSTATUS;

	/**
	 * 成本率
	 */
	private Double cbl;
	
	/**
	 * 整单折扣金额
	 */
	private Double zdzkje;
	
	/**
	 * 合计
	 */
	private Double hj;
	
	/**
	 * 折扣后金额
	 */
	private Double zkhjg;
	
	
	/**
	 * 成本率 百分数
	 */
	private String cblb;
	
	
	
	
	public String getCblb() {
		return cblb;
	}


	public void setCblb(String cblb) {
		this.cblb = cblb;
	}


	public Double getCbl() {
		return cbl;
	}


	public void setCbl(Double cbl) {
		this.cbl = cbl;
	}


	public Double getZdzkje() {
		return zdzkje;
	}


	public void setZdzkje(Double zdzkje) {
		this.zdzkje = zdzkje;
	}


	public Double getHj() {
		return hj;
	}


	public void setHj(Double hj) {
		this.hj = hj;
	}


	public Double getZkhjg() {
		return zkhjg;
	}


	public void setZkhjg(Double zkhjg) {
		this.zkhjg = zkhjg;
	}


	private List<Billing> lists;

	
	

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


	public Date getMERGE_TIME() {
		return MERGE_TIME;
	}


	public void setMERGE_TIME(Date mERGE_TIME) {
		MERGE_TIME = mERGE_TIME;
	}


	public String getAUDITOR() {
		return AUDITOR;
	}


	public void setAUDITOR(String aUDITOR) {
		AUDITOR = aUDITOR;
	}


	public Date getAUDITOR_TIME() {
		return AUDITOR_TIME;
	}


	public void setAUDITOR_TIME(Date aUDITOR_TIME) {
		AUDITOR_TIME = aUDITOR_TIME;
	}


	public int getAUDITSTATUS() {
		return AUDITSTATUS;
	}


	public void setAUDITSTATUS(int aUDITSTATUS) {
		AUDITSTATUS = aUDITSTATUS;
	}


	public List<Billing> getLists() {
		return lists;
	}


	public void setLists(List<Billing> lists) {
		this.lists = lists;
	}

    private List<GoldVirtureInvoice> goldlists;




	public List<GoldVirtureInvoice> getGoldlists() {
		return goldlists;
	}


	public void setGoldlists(List<GoldVirtureInvoice> goldlists) {
		this.goldlists = goldlists;
	}
  
	
	
}
