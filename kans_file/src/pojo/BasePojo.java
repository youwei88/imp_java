package pojo;

import java.io.Serializable;
import java.sql.Date;

public class BasePojo implements Serializable {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	private int id; 
	
	/**
	 * 创建人
	 */
	private int creater;
	
	/**
	 * 创建时间
	 */
	private Date createtime;
	
	/**
	 * 更新人
	 */
	private int updater;
	
	/**
	 * 更新时间
	 */
	private Date updatetime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCreater() {
		return creater;
	}

	public void setCreater(int creater) {
		this.creater = creater;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public int getUpdater() {
		return updater;
	}

	public void setUpdater(int updater) {
		this.updater = updater;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
	
}
