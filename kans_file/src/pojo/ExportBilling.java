package pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ExportBilling implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	
	private String VKORG;
	

    
	private Date MERGE_TIME;
	 
	
 	private   Double hj = 0.0;
	private   Double cbl = 0.0;
	private   Double zdzkje = 0.0;
	private   Double zkhjg = 0.0;
	private   Double cbj = 0.0;
	
	
	
	
	
	public Double getHj() {
		return hj;
	}

	public void setHj(Double hj) {
		this.hj = hj;
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

	public Double getZkhjg() {
		return zkhjg;
	}

	public void setZkhjg(Double zkhjg) {
		this.zkhjg = zkhjg;
	}

	public Double getCbj() {
		return cbj;
	}

	public void setCbj(Double cbj) {
		this.cbj = cbj;
	}

	public Date getMERGE_TIME() {
		return MERGE_TIME;
	}

	public void setMERGE_TIME(Date mERGE_TIME) {
		MERGE_TIME = mERGE_TIME;
	}

	private List<ExportBillingAudit> lists;
	
	public List<ExportBillingAudit> getLists() {
		return lists;
	}

	public void setLists(List<ExportBillingAudit> lists) {
		this.lists = lists;
	}



	public String getVKORG() {
		return VKORG;
	}

	public void setVKORG(String vKORG) {
		VKORG = vKORG;
	}


	
	
	
	
}
