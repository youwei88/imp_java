package test;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoRepository;
import com.sap.conn.jco.JCoTable;

import domain.ET_001;
import sap.SapJco3Conn;

public class SapTest {
public static void main(String[] args) {
		// 获取连接
		// client = getConn();
		JCoDestination dest;
		try {
			dest = SapJco3Conn.getDestination();
		
		JCoRepository repository = dest.getRepository();
		JCoFunction fm = repository.getFunction("ZRFC_IMP_GET_INVOICE_OLD");
		if (fm == null) {
			throw new RuntimeException("Function does not exists in SAP system.");
		}
		// 传参
		fm.getImportParameterList().setValue("I_BDATE", "20171126"); // 全部获取
		fm.getImportParameterList().setValue("I_EDATE", "20171201"); // 全部获取
//		fm.getImportParameterList().setValue("I_BUKRS", companyCode); // 全部获取
		// 执行
		fm.execute(dest);

		/** 1、获取返回的结果 **/
		JCoTable etTable1 = fm.getTableParameterList().getTable("ET_001");
		int j=0;
		for (int i = 0; i < etTable1.getNumRows(); i++) {
			etTable1.setRow(i);
			ET_001 et001 = new ET_001();
			// 付款方90003,90000不导入，20170825添加
			if (etTable1.getString("VBELN").trim().equals("0090366416")) {
				System.out.println(++j);
				System.out.println(etTable1.getString("VBELN"));
			}
//			et001.setVbeln(etTable1.getString("VBELN").trim());
//			et001.setPosnr(etTable1.getString("POSNR").trim());
//			et001.setFkart(etTable1.getString("FKART").trim());
//			et001.setFkart_t(etTable1.getString("FKART_T").trim());
//			et001.setVgbel(etTable1.getString("VGBEL").trim());
//			et001.setAubel(etTable1.getString("AUBEL").trim());
//			et001.setAuart(etTable1.getString("AUART").trim());
//			et001.setVkorg(etTable1.getString("VKORG").trim());
//			et001.setVkorg_t(etTable1.getString("VKORG_T").trim());
//			et001.setVtweg(etTable1.getString("VTWEG").trim());
//			if (etTable1.getString("VTWEG").trim().equals("10")) {
//				et001.setVtweg_t("非直营");
//			} else {
//				et001.setVtweg_t("直营");
//			}
//
//			et001.setSpart(etTable1.getString("SPART").trim());
//			et001.setSpart_t(etTable1.getString("SPART_T").trim());
//			et001.setVkbur(etTable1.getString("VKBUR").trim());
//			et001.setVkbur_t(etTable1.getString("VKBUR_T").trim());
//			et001.setFkdat(etTable1.getDate("FKDAT"));
//			et001.setKunag(etTable1.getString("KUNAG").trim());
//			et001.setName1(etTable1.getString("NAME1").trim());
//			et001.setSort1(etTable1.getString("SORT1").trim());
//			et001.setKunrg(etTable1.getString("KUNRG").trim());
//			et001.setName2(etTable1.getString("NAME2").trim());
//			et001.setMatnr(etTable1.getString("MATNR").trim());
//			// et001.setArktx(etTable1.getString("ARKTX").trim());
//			// 特殊字符" "（unicode码为/ua0,并非一般的空格）转空字符"",特殊字符•或™换成对应的unicode码
//			et001.setArktx(etTable1.getString("ARKTX").trim().replace(" ", "")
//					.replace("•", "\\u" + Integer.toHexString('•')).replace("™", "\\u" + Integer.toHexString('™')));
//			et001.setFkimg(etTable1.getString("FKIMG").trim());
//			et001.setVrkme(etTable1.getString("VRKME").trim());
//			et001.setHsdj(etTable1.getDouble("HSDJ"));
//			et001.setHszj(etTable1.getDouble("HSZJ"));
//			et001.setAbrvw(etTable1.getString("ABRVW").trim());
//			et001.setBezei(etTable1.getString("BEZEI").trim());
//			et001.setBtaux(etTable1.getDouble("BTAUX"));
//			et001.setTaxde(etTable1.getDouble("TAXDE"));
//			et001.setCpudt(etTable1.getDate("CPUDT"));
//			et001.setCputm(sdf1.format(etTable1.getDate("CPUTM")));
//			et001.setGhj(etTable1.getDouble("GHJ"));
//			et001.setJsj(etTable1.getDouble("JSJ"));
//			et001.setCbj(etTable1.getDouble("CBJ"));
//			et001.setPrdha(etTable1.getString("PRDHA").trim());
//			et001.setBukrs(etTable1.getString("BUKRS").trim());
//			// et001.setNormt(etTable1.getString("NORMT").trim());
//			// 特殊字符" "（unicode码为/ua0,非一般空格）转空字符""
//			et001.setNormt(etTable1.getString("NORMT").trim().replace(" ", ""));
//			et001.setCreater(10000);
//			et001.setCreatetime(sdf3.format(date));
//			et001.setUpdateter(10000);
//			et001.setUpdatetime(sdf3.format(date));

		}
		} catch (JCoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}}
