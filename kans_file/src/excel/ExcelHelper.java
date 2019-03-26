package excel;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Component;

@Component
public class ExcelHelper {
	
	  public static List<Map<String, String>> parseExcel(InputStream fis) {  
	        List<Map<String, String>> data = new ArrayList<Map<String, String>>();;  
	        try {  
	            HSSFWorkbook book = new HSSFWorkbook(fis);  
	            HSSFSheet sheet = book.getSheetAt(0);  
	            int firstRow = sheet.getFirstRowNum();  
	            int lastRow = sheet.getLastRowNum();  
	            //除去表头和第一行  
//	          ComnDao dao = SysBeans.getComnDao();  
	            for(int i = firstRow + 1; i<lastRow+1; i++) {  
	                Map map = new HashMap();  
	                  
	                HSSFRow row = sheet.getRow(i);  
	                int firstCell = row.getFirstCellNum();  
	                int lastCell = row.getLastCellNum();  
	                  
	                  
	                for(int j=firstCell; j<lastCell; j++) {  
	                      
	                    HSSFCell cell2 = sheet.getRow(firstRow + 1).getCell(j);  
	                    String key = cell2.getStringCellValue();  
	                      
	                    HSSFCell cell = row.getCell(j);  
	                      
	                    if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {  
	                        cell.setCellType(HSSFCell.CELL_TYPE_STRING);  
	                    }  
	                    String val = cell.getStringCellValue();  
	                      
//	              System.out.println(val);  
	                      
	                    if(i == firstRow + 1) {  
	                        break;  
	                    }else{  
	                        map.put(key, val);  
	                          
	                    }  
//	              System.out.println(map);  
	                }  
	                if(i != firstRow + 1) {  
	                    data.add(map);  
	                    System.out.println(map);  
	                }  
	            }  
	            System.out.println(data);  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	        return data;  
	    }  
}
