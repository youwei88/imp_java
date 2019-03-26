package sap;

import com.sap.conn.jco.rt.JCoMiddleware.Client;

public interface ISapHelperNew {
/*    public Client getConn();*/
	
	public void sapimport(String startTime,String endTime,String companyNum);
	
    public void updateEtFirst();
}
