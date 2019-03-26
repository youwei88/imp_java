package pojo;

import java.io.Serializable;

public class GoldVirtureInvoice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String VITUREINVOCE;
	
	private String VITUREINVOCEGOLD;
	
	private int CREATER;
	
	private String CREATETIME;
	
	private int UPDATER;
	
	private String UPDATETIME;

	public String getVITUREINVOCE() {
		return VITUREINVOCE;
	}

	public void setVITUREINVOCE(String vITUREINVOCE) {
		VITUREINVOCE = vITUREINVOCE;
	}

	public String getVITUREINVOCEGOLD() {
		return VITUREINVOCEGOLD;
	}

	public void setVITUREINVOCEGOLD(String vITUREINVOCEGOLD) {
		VITUREINVOCEGOLD = vITUREINVOCEGOLD;
	}

	public int getCREATER() {
		return CREATER;
	}

	public void setCREATER(int cREATER) {
		CREATER = cREATER;
	}

	public String getCREATETIME() {
		return CREATETIME;
	}

	public void setCREATETIME(String cREATETIME) {
		CREATETIME = cREATETIME;
	}

	public int getUPDATER() {
		return UPDATER;
	}

	public void setUPDATER(int uPDATER) {
		UPDATER = uPDATER;
	}

	public String getUPDATETIME() {
		return UPDATETIME;
	}

	public void setUPDATETIME(String uPDATETIME) {
		UPDATETIME = uPDATETIME;
	}
   
	


}
