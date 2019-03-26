package pojo;

import java.io.Serializable;
import java.sql.Date;

public class BaseBilling implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int ID;
    
	//SAP发票号
	private String VBELN;
	//发票行号
	private String POSNR;
	//发票类型
	private String FKART;
	//发票类型描述
	private String FKART_T;
	//发货单
	private String VGBEL;
	//销售凭证
	private String AUBEL;
	
	//销售组织
	private String VKORG;
	//销售组织描述
	private String VKORG_T;
	//分销渠道
	private String VTWEG;
	//分销渠道描述
    private String VTWEG_T;
    //品牌
    private String SPART; 
    //品牌描述
    private String SPART_T;
    
    //销售部门
    private String VKBUR;
    
    //销售部门描述
    private String VKBUR_T;
    
    //开票日期
    private Date FKDAT;
    //售达方
    private String KUNAG;
    //售达方名称
    private String NAME1;
    
    //售达方简称
    private String SORT1;
    
    //付款方
    private String KUNRG;
    
    //付款方名称
    private String NAME2;
    
    //物料号
    private String MATNR;
    
    //物料描述
    private String ARKTX;
    
    //开票数量
    private String FKIMG;
    
    //销售单位
    private String VRKME;
     
    //含税单价
    private String HSDJ;
    
    //含税总价
    private String HSZJ;
    
    //公司抬头
    private String ABRVW;
    
    //公司抬头描述
    private String BEZEI;
    
    //税率
    private String BTAUX;
    
    //税额
    private String TAXDE;
    
    //供货价
    private Double GHJ;
    
    //结算价
    private Double JSJ;
    
    //成本价
    private Double CBJ;
    
    //公司编码
    private String BURKS;
    
    //产品编码
    private String NORMT;
    
    //总发票号
    private String VITUREINVOCE;
    
    //审核时间
    private Date AUDITOR_TIME;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getVBELN() {
		return VBELN;
	}

	public void setVBELN(String vBELN) {
		VBELN = vBELN;
	}

	public String getPOSNR() {
		return POSNR;
	}

	public void setPOSNR(String pOSNR) {
		POSNR = pOSNR;
	}

	public String getFKART() {
		return FKART;
	}

	public void setFKART(String fKART) {
		FKART = fKART;
	}

	public String getFKART_T() {
		return FKART_T;
	}

	public void setFKART_T(String fKART_T) {
		FKART_T = fKART_T;
	}

	public String getVGBEL() {
		return VGBEL;
	}

	public void setVGBEL(String vGBEL) {
		VGBEL = vGBEL;
	}

	public String getAUBEL() {
		return AUBEL;
	}

	public void setAUBEL(String aUBEL) {
		AUBEL = aUBEL;
	}

	public String getVKORG() {
		return VKORG;
	}

	public void setVKORG(String vKORG) {
		VKORG = vKORG;
	}

	public String getVKORG_T() {
		return VKORG_T;
	}

	public void setVKORG_T(String vKORG_T) {
		VKORG_T = vKORG_T;
	}

	public String getVTWEG() {
		return VTWEG;
	}

	public void setVTWEG(String vTWEG) {
		VTWEG = vTWEG;
	}

	public String getVTWEG_T() {
		return VTWEG_T;
	}

	public void setVTWEG_T(String vTWEG_T) {
		VTWEG_T = vTWEG_T;
	}

	public String getSPART() {
		return SPART;
	}

	public void setSPART(String sPART) {
		SPART = sPART;
	}

	public String getSPART_T() {
		return SPART_T;
	}

	public void setSPART_T(String sPART_T) {
		SPART_T = sPART_T;
	}

	public String getVKBUR() {
		return VKBUR;
	}

	public void setVKBUR(String vKBUR) {
		VKBUR = vKBUR;
	}

	public String getVKBUR_T() {
		return VKBUR_T;
	}

	public void setVKBUR_T(String vKBUR_T) {
		VKBUR_T = vKBUR_T;
	}

	public Date getFKDAT() {
		return FKDAT;
	}

	public void setFKDAT(Date fKDAT) {
		FKDAT = fKDAT;
	}

	public String getKUNAG() {
		return KUNAG;
	}

	public void setKUNAG(String kUNAG) {
		KUNAG = kUNAG;
	}

	public String getNAME1() {
		return NAME1;
	}

	public void setNAME1(String nAME1) {
		NAME1 = nAME1;
	}

	public String getSORT1() {
		return SORT1;
	}

	public void setSORT1(String sORT1) {
		SORT1 = sORT1;
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

	public String getMATNR() {
		return MATNR;
	}

	public void setMATNR(String mATNR) {
		MATNR = mATNR;
	}

	public String getARKTX() {
		return ARKTX;
	}

	public void setARKTX(String aRKTX) {
		ARKTX = aRKTX;
	}

	public String getFKIMG() {
		return FKIMG;
	}

	public void setFKIMG(String fKIMG) {
		FKIMG = fKIMG;
	}

	public String getVRKME() {
		return VRKME;
	}

	public void setVRKME(String vRKME) {
		VRKME = vRKME;
	}

	public String getHSDJ() {
		return HSDJ;
	}

	public void setHSDJ(String hSDJ) {
		HSDJ = hSDJ;
	}

	public String getHSZJ() {
		return HSZJ;
	}

	public void setHSZJ(String hSZJ) {
		HSZJ = hSZJ;
	}

	public String getABRVW() {
		return ABRVW;
	}

	public void setABRVW(String aBRVW) {
		ABRVW = aBRVW;
	}

	public String getBEZEI() {
		return BEZEI;
	}

	public void setBEZEI(String bEZEI) {
		BEZEI = bEZEI;
	}

	public String getBTAUX() {
		return BTAUX;
	}

	public void setBTAUX(String bTAUX) {
		BTAUX = bTAUX;
	}

	public String getTAXDE() {
		return TAXDE;
	}

	public void setTAXDE(String tAXDE) {
		TAXDE = tAXDE;
	}

	public Double getGHJ() {
		return GHJ;
	}

	public void setGHJ(Double gHJ) {
		GHJ = gHJ;
	}

	public Double getJSJ() {
		return JSJ;
	}

	public void setJSJ(Double jSJ) {
		JSJ = jSJ;
	}

	public Double getCBJ() {
		return CBJ;
	}

	public void setCBJ(Double cBJ) {
		CBJ = cBJ;
	}

	public String getBURKS() {
		return BURKS;
	}

	public void setBURKS(String bURKS) {
		BURKS = bURKS;
	}

	public String getNORMT() {
		return NORMT;
	}

	public void setNORMT(String nORMT) {
		NORMT = nORMT;
	}

	public String getVITUREINVOCE() {
		return VITUREINVOCE;
	}

	public void setVITUREINVOCE(String vITUREINVOCE) {
		VITUREINVOCE = vITUREINVOCE;
	}

	public Date getAUDITOR_TIME() {
		return AUDITOR_TIME;
	}

	public void setAUDITOR_TIME(Date aUDITOR_TIME) {
		AUDITOR_TIME = aUDITOR_TIME;
	}
    
}


