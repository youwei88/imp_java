package domain;

import java.io.Serializable;
import java.util.Date;


public class ET_002 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	private String VBELN;
	
	private Date CPUDT;
	
	private String CPUTM;

	public String getVBELN() {
		return VBELN;
	}

	public void setVBELN(String vBELN) {
		VBELN = vBELN;
	}

	public Date getCPUDT() {
		return CPUDT;
	}

	public void setCPUDT(Date cPUDT) {
		CPUDT = cPUDT;
	}

	public String getCPUTM() {
		return CPUTM;
	}

	public void setCPUTM(String cPUTM) {
		CPUTM = cPUTM;
	}

    
}
