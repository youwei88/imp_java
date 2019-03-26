package pojo;

import java.sql.Date;
import java.util.List;

public class MergePojoBase {
   
	//新的发票号
	private String BILLINGNO;
    
	//发票总号
	private String BILLINGBASE;
	
    //新的付款方
    private String KUNRG;
    
    //开票基础列表
    private List<Billing> billings;
    
    
    private Date MERGE_TIME;
    
    //含税总价
    private Double SUM;
    
    
    

	public Double getSUM() {
		return SUM;
	}


	public void setSUM(Double sUM) {
		SUM = sUM;
	}


	public String getBILLINGBASE() {
		return BILLINGBASE;
	}


	public void setBILLINGBASE(String bILLINGBASE) {
		BILLINGBASE = bILLINGBASE;
	}


	public Date getMERGE_TIME() {
		return MERGE_TIME;
	}


	public void setMERGE_TIME(Date mERGE_TIME) {
		MERGE_TIME = mERGE_TIME;
	}


	public String getBILLINGNO() {
		return BILLINGNO;
	}


	public void setBILLINGNO(String bILLINGNO) {
		BILLINGNO = bILLINGNO;
	}


	public String getKUNRG() {
		return KUNRG;
	}


	public void setKUNRG(String kUNRG) {
		KUNRG = kUNRG;
	}


	public List<Billing> getBillings() {
		return billings;
	}


	public void setBillings(List<Billing> billings) {
		this.billings = billings;
	}

    
	
    
}
