package pojo;

import java.io.Serializable;
import java.util.Date;

public class UserTable implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Integer id;//主键
	private String userName;//
	private String userPhone;//
	private String userPassWord;//
	private String sourceUrl;//交易机构
	private String sourceType;//交易渠道号
	private String pageType;//第几套页面
	private String accessType;//数据来自
	private Date creatTime;		//创建时间
	
	public Date getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}
	public String getPageType() {
		return pageType;
	}
	public void setPageType(String pageType) {
		this.pageType = pageType;
	}
	
	public String getAccessType() {
		return accessType;
	}
	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getUserPassWord() {
		return userPassWord;
	}
	public void setUserPassWord(String userPassWord) {
		this.userPassWord = userPassWord;
	}
	public String getSourceUrl() {
		return sourceUrl;
	}
	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	
	

}
