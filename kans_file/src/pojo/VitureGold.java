package pojo;

import java.io.Serializable;

public class VitureGold implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int ID;
	
	private String VITUREINVOCE;
	
	private String VITUREINVOCEGOLD;
	
	private int CREATER;
	
	private String CREATETIME;
	
	private int UPDATETER;
	
	private String UPDATETIME;
	
	

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

	public int getUPDATETER() {
		return UPDATETER;
	}

	public void setUPDATETER(int uPDATETER) {
		UPDATETER = uPDATETER;
	}

	public String getUPDATETIME() {
		return UPDATETIME;
	}

	public void setUPDATETIME(String uPDATETIME) {
		UPDATETIME = uPDATETIME;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

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
	
	
}
