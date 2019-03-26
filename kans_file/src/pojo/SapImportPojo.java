package pojo;

import java.io.Serializable;
import java.sql.Date;

public class SapImportPojo extends BasePojo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private  String companycode;
	
	private Date begin;
	
	private Date end;

	public String getCompanycode() {
		return companycode;
	}

	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}

	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}
	
	
	
}
