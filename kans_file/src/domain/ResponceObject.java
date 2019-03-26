package domain;

import java.util.HashMap;
import java.util.List;

public class ResponceObject<T> {

	private  int resultCode;
	
	private String resultMessage;
	
	private List<T>   lists;
	
	private int total;

	private String mergeIds;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMessage() {
		return resultMessage;
	}

	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}

	public List<T> getLists() {
		return lists;
	}

	public void setLists(List<T> lists) {
		this.lists = lists;
	}

	public String getMergeIds() {
		return mergeIds;
	}

	public void setMergeIds(String mergeIds) {
		this.mergeIds = mergeIds;
	}
   
	
}
