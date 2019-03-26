package pojo;

import java.io.Serializable;

public class Customer implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 主键
     */
	private int id ;
	
	/**
	 * 销售组织
	 */
	private String VKORG;
	
	/**
	 * 品牌
	 */
	private String SPART;
	
	/**
	 * 付款方
	 */
	private String KUNRG;
	
	/**
	 * 付款方名称
	 */
	private String NAME2;
	
	/**
	 * 创建人
	 */
	private int CREATER;
	
	/**
	 * 开票公司名称
	 */
	private String COMNAME;
	
	/**
	 * 纳税人识别号
	 */
	private String PAYINGNUM;
	
	/**
	 * 纳税人名称
	 */
	private String PAYINGNAME;
	
	/**
	 * 地址电话
	 */
    private String ADDRESS;

    /**
     * 开户行
     */
    private String BANK;

    /**
     * 开户行账号
     */
    private String BANKNUM;
    
    /**
     * 发票地址
     */
    private String BILLINGADDRESS;
    
    /**
     * 电话号码
     */
    private String PHONE;

    /**
     * 发票快递签收
     */
    private String PEOPLE;

    /**
     * 发票类型
     */
    private String BILLINGTYPE;
    
    /**
     * 创建时间
     */
    private String CREATE_TIME; 
    
    /**
     * 更新人
     */
    private int UPDATER;

    /**
     * 更新人
     */
    private String UPDATE_TIME;
    
    /**
     * 审核状态
     */
    private int AUDITSTATUS;
    
    
    
	public int getAUDITSTATUS() {
		return AUDITSTATUS;
	}

	public void setAUDITSTATUS(int aUDITSTATUS) {
		AUDITSTATUS = aUDITSTATUS;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getVKORG() {
		return VKORG;
	}

	public void setVKORG(String vKORG) {
		VKORG = vKORG;
	}

	public String getSPART() {
		return SPART;
	}

	public void setSPART(String sPART) {
		SPART = sPART;
	}

	public String getKUNRG() {
		return KUNRG;
	}

	public void setKUNRG(String kUNRG) {
		KUNRG = kUNRG;
	}

	public String getNAME2() {
		return NAME2;
	}

	public void setNAME2(String nAME2) {
		NAME2 = nAME2;
	}

	public int getCREATER() {
		return CREATER;
	}

	public void setCREATER(int cREATER) {
		CREATER = cREATER;
	}

	public String getCOMNAME() {
		return COMNAME;
	}

	public void setCOMNAME(String cOMNAME) {
		COMNAME = cOMNAME;
	}

	public String getPAYINGNUM() {
		return PAYINGNUM;
	}

	public void setPAYINGNUM(String pAYINGNUM) {
		PAYINGNUM = pAYINGNUM;
	}

	public String getPAYINGNAME() {
		return PAYINGNAME;
	}

	public void setPAYINGNAME(String pAYINGNAME) {
		PAYINGNAME = pAYINGNAME;
	}

	public String getADDRESS() {
		return ADDRESS;
	}

	public void setADDRESS(String aDDRESS) {
		ADDRESS = aDDRESS;
	}

	public String getBANK() {
		return BANK;
	}

	public void setBANK(String bANK) {
		BANK = bANK;
	}

	public String getBANKNUM() {
		return BANKNUM;
	}

	public void setBANKNUM(String bANKNUM) {
		BANKNUM = bANKNUM;
	}

	public String getBILLINGADDRESS() {
		return BILLINGADDRESS;
	}

	public void setBILLINGADDRESS(String bILLINGADDRESS) {
		BILLINGADDRESS = bILLINGADDRESS;
	}

	public String getPHONE() {
		return PHONE;
	}

	public void setPHONE(String pHONE) {
		PHONE = pHONE;
	}

	public String getPEOPLE() {
		return PEOPLE;
	}

	public void setPEOPLE(String pEOPLE) {
		PEOPLE = pEOPLE;
	}

	public String getBILLINGTYPE() {
		return BILLINGTYPE;
	}

	public void setBILLINGTYPE(String bILLINGTYPE) {
		BILLINGTYPE = bILLINGTYPE;
	}

	public String getCREATE_TIME() {
		return CREATE_TIME;
	}

	public void setCREATE_TIME(String cREATE_TIME) {
		CREATE_TIME = cREATE_TIME;
	}

	public int getUPDATER() {
		return UPDATER;
	}

	public void setUPDATER(int uPDATER) {
		UPDATER = uPDATER;
	}

	public String getUPDATE_TIME() {
		return UPDATE_TIME;
	}

	public void setUPDATE_TIME(String uPDATE_TIME) {
		UPDATE_TIME = uPDATE_TIME;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", VKORG=" + VKORG + ", SPART=" + SPART + ", KUNRG=" + KUNRG + ", NAME2=" + NAME2
				+ ", CREATER=" + CREATER + ", COMNAME=" + COMNAME + ", PAYINGNUM=" + PAYINGNUM + ", PAYINGNAME="
				+ PAYINGNAME + ", ADDRESS=" + ADDRESS + ", BANK=" + BANK + ", BANKNUM=" + BANKNUM + ", BILLINGADDRESS="
				+ BILLINGADDRESS + ", PHONE=" + PHONE + ", PEOPLE=" + PEOPLE + ", BILLINGTYPE=" + BILLINGTYPE
				+ ", CREATE_TIME=" + CREATE_TIME + ", UPDATER=" + UPDATER + ", UPDATE_TIME=" + UPDATE_TIME + "]";
	}
    
    
    
    
}
