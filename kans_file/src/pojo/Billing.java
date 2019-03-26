package pojo;

import java.io.Serializable;
import java.sql.Date;

public class Billing extends BasePojo implements Serializable  {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * SAP发票号
	 */
	private String vbeln;

	/**
	 * 发票行号
	 */
	private String posnr;
	/**
	 * 发票类型
	 */
	private String fkart;
	
	/**
	 * 发票类型描述
	 */
	private String fkart_t;
	
	/**
	 * 发货单
	 */
	private String vgbel;
	
	/**
	 * 销售凭证
	 */
	private String aubel;
	
	/**
	 * 销售凭证类型
	 */
	private String auart;
	
	/**
	 * 销售组织
	 */
	private String vkorg;
	
	/**
	 * 销售组织描述
	 */
	private String vkorg_t;
    /**
     * 分销渠道	
     */
	private String  vtweg;
	
	/**
	 * 分销渠道描述
	 */
	private String vtweg_t;
	
	/**
	 * 品牌
	 */
	private String spart;
	
	/**
	 * 品牌描述
	 */
	private String spart_t;
	
	/**
	 * 销售部门
	 */
	private String vkbur;
	
	/**
	 * 销售部门描述
	 */
	private String vkbur_t;
	
	/**
	 * 开票日期
	 */
	private Date fkdat;
	
	/**
	 * 售达方
	 */
	private String kunag;
	
	/**
	 * 售达方名称
	 */
	private String  name1;
	
	/**
	 * 售达方简称
	 */
	private String sort1;
	
	/**
	 * 付款方
	 */
	private String kunrg;
	
	/**
	 * 付款方名称
	 */
	private String name2;
	
	/**
	 * 物料号
	 */
	private String matnr;
	
	/**
	 * 物料描述
	 */
	private String arktx;
	
	/**
	 * 开票数量
	 */
	private String fkimg;
	
	/**
	 * 销售单位
	 */
	private String vrkme;
	
	/**
	 * 含税单价
	 */
	private Double hsdj;
	
	/**
	 * 含税总价
	 */
	private Double hszj;
	
	/**
	 * 公司抬头
	 */
	private String abrvw;
	
	/**
	 * 公司抬头描述
	 */
	private String bezei;
	
	/**
	 * 税率
	 */
	private Double btaux;
	
	/**
	 * 税额
	 */
	private Double taxde;
	
	/**
	 * 财务审核日期
	 */
	private Date cpudt;
	
	/**
	 * 财务审核时间
	 */
	private String cputm;
	
	/**
	 * 供货价
	 */
	private Double ghj;
	
	/**
	 * 结算价
	 */
	private Double jsj;
	
	/**
	 * 成本价
	 */
	private Double cbj;
	
	/**
	 * 产品层次
	 */
	private String prdha;
	
	/**
	 * 公司代码
	 */
	private String bukrs;
	
	/**
	 * 产品规格
	 */
	private String normt;
	
	/**
	 * 是否开票
	 */
	private int isbill;
	
	/**
	 * 是否取消开票
	 */
	private int iscancelbill;
	
	/**
	 * 开票状态
	 */
	private  int kpstatus;
	
	/**
	 * 发票号
	 */
	private String vitureinvoce ;
	
	
	/**
	 * 开票人
	 */
	private String merge_people;
	
	/**
	 * 开票时间
	 */
	private Date merge_time;
	
	/**
	 * 审核人
	 */
	private String auditor ;
	
	/**
	 * 审核时间
	 */
	private Date auditor_time;
	
	/**
	 * 审核状态
	 */
	private int auditorstatus;

	/**
	 * 纳税人登记号
	 */
	private String payingnum;
     
	/**
	 * 购方地址及电话
	 */
	private String address;

	/**
	 * 银行地址及账号
	 */
	private String newbank;
	
	/**
	 * 发票类型
	 */
	private String bilingtype;
	
	
	
	
	public int getKpstatus() {
		return kpstatus;
	}

	public void setKpstatus(int kpstatus) {
		this.kpstatus = kpstatus;
	}

	public String getVitureinvoce() {
		return vitureinvoce;
	}

	public void setVitureinvoce(String vitureinvoce) {
		this.vitureinvoce = vitureinvoce;
	}

	public String getMerge_people() {
		return merge_people;
	}

	public void setMerge_people(String merge_people) {
		this.merge_people = merge_people;
	}

	public Date getMerge_time() {
		return merge_time;
	}

	public void setMerge_time(Date merge_time) {
		this.merge_time = merge_time;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public Date getAuditor_time() {
		return auditor_time;
	}

	public void setAuditor_time(Date auditor_time) {
		this.auditor_time = auditor_time;
	}

	public int getAuditorstatus() {
		return auditorstatus;
	}

	public void setAuditorstatus(int auditorstatus) {
		this.auditorstatus = auditorstatus;
	}

	public String getPayingnum() {
		return payingnum;
	}

	public void setPayingnum(String payingnum) {
		this.payingnum = payingnum;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNewbank() {
		return newbank;
	}

	public void setNewbank(String newbank) {
		this.newbank = newbank;
	}

	public String getBilingtype() {
		return bilingtype;
	}

	public void setBilingtype(String bilingtype) {
		this.bilingtype = bilingtype;
	}

	public String getNormt() {
		return normt;
	}

	public void setNormt(String normt) {
		this.normt = normt;
	}

	public int getIsbill() {
		return isbill;
	}

	public void setIsbill(int isbill) {
		this.isbill = isbill;
	}

	public int getIscancelbill() {
		return iscancelbill;
	}

	public void setIscancelbill(int iscancelbill) {
		this.iscancelbill = iscancelbill;
	}


	public String getVtweg_t() {
		return vtweg_t;
	}

	public void setVtweg_t(String vtweg_t) {
		this.vtweg_t = vtweg_t;
	}

	public String getVbeln() {
		return vbeln;
	}

	public void setVbeln(String vbeln) {
		this.vbeln = vbeln;
	}

	public String getPosnr() {
		return posnr;
	}

	public void setPosnr(String posnr) {
		this.posnr = posnr;
	}

	public String getFkart() {
		return fkart;
	}

	public void setFkart(String fkart) {
		this.fkart = fkart;
	}

	public String getFkart_t() {
		return fkart_t;
	}

	public void setFkart_t(String fkart_t) {
		this.fkart_t = fkart_t;
	}

	public String getVgbel() {
		return vgbel;
	}

	public void setVgbel(String vgbel) {
		this.vgbel = vgbel;
	}

	public String getAubel() {
		return aubel;
	}

	public void setAubel(String aubel) {
		this.aubel = aubel;
	}

	public String getAuart() {
		return auart;
	}

	public void setAuart(String auart) {
		this.auart = auart;
	}

	public String getVkorg() {
		return vkorg;
	}

	public void setVkorg(String vkorg) {
		this.vkorg = vkorg;
	}

	public String getVkorg_t() {
		return vkorg_t;
	}

	public void setVkorg_t(String vkorg_t) {
		this.vkorg_t = vkorg_t;
	}

	public String getVtweg() {
		return vtweg;
	}

	public void setVtweg(String vtweg) {
		this.vtweg = vtweg;
	}

	public String getSpart() {
		return spart;
	}

	public void setSpart(String spart) {
		this.spart = spart;
	}

	public String getSpart_t() {
		return spart_t;
	}

	public void setSpart_t(String spart_t) {
		this.spart_t = spart_t;
	}

	public String getVkbur() {
		return vkbur;
	}

	public void setVkbur(String vkbur) {
		this.vkbur = vkbur;
	}

	public String getVkbur_t() {
		return vkbur_t;
	}

	public void setVkbur_t(String vkbur_t) {
		this.vkbur_t = vkbur_t;
	}

	public Date getFkdat() {
		return fkdat;
	}

	public void setFkdat(Date fkdat) {
		this.fkdat = fkdat;
	}

	public String getKunag() {
		return kunag;
	}

	public void setKunag(String kunag) {
		this.kunag = kunag;
	}

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public String getSort1() {
		return sort1;
	}

	public void setSort1(String sort1) {
		this.sort1 = sort1;
	}

	public String getKunrg() {
		return kunrg;
	}

	public void setKunrg(String kunrg) {
		this.kunrg = kunrg;
	}

	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public String getMatnr() {
		return matnr;
	}

	public void setMatnr(String matnr) {
		this.matnr = matnr;
	}

	public String getArktx() {
		return arktx;
	}

	public void setArktx(String arktx) {
		this.arktx = arktx;
	}

	public String getFkimg() {
		return fkimg;
	}

	public void setFkimg(String fkimg) {
		this.fkimg = fkimg;
	}

	public String getVrkme() {
		return vrkme;
	}

	public void setVrkme(String vrkme) {
		this.vrkme = vrkme;
	}

	public String getAbrvw() {
		return abrvw;
	}

	public void setAbrvw(String abrvw) {
		this.abrvw = abrvw;
	}

	public String getBezei() {
		return bezei;
	}

	public void setBezei(String bezei) {
		this.bezei = bezei;
	}

	public Date getCpudt() {
		return cpudt;
	}

	public void setCpudt(Date cpudt) {
		this.cpudt = cpudt;
	}



	public Double getHsdj() {
		return hsdj;
	}

	public void setHsdj(Double hsdj) {
		this.hsdj = hsdj;
	}

	public Double getHszj() {
		return hszj;
	}

	public void setHszj(Double hszj) {
		this.hszj = hszj;
	}

	public Double getBtaux() {
		return btaux;
	}

	public void setBtaux(Double btaux) {
		this.btaux = btaux;
	}

	public Double getTaxde() {
		return taxde;
	}

	public void setTaxde(Double taxde) {
		this.taxde = taxde;
	}

	public String getCputm() {
		return cputm;
	}

	public void setCputm(String cputm) {
		this.cputm = cputm;
	}

	public Double getGhj() {
		return ghj;
	}

	public void setGhj(Double ghj) {
		this.ghj = ghj;
	}

	public Double getJsj() {
		return jsj;
	}

	public void setJsj(Double jsj) {
		this.jsj = jsj;
	}

	public Double getCbj() {
		return cbj;
	}

	public void setCbj(Double cbj) {
		this.cbj = cbj;
	}

	public String getPrdha() {
		return prdha;
	}

	public void setPrdha(String prdha) {
		this.prdha = prdha;
	}

	public String getBukrs() {
		return bukrs;
	}

	public void setBukrs(String bukrs) {
		this.bukrs = bukrs;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	
}
