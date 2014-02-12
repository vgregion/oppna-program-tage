package se.goteborg.retursidan.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import se.goteborg.retursidan.model.GeneralEntityBean;

/**
 * Model bean representing a visit, which is all visits made by a
 * specific user.
 *
 */
@Entity
@Table(name="vgr_tage_visit")
public class Visit extends GeneralEntityBean implements Serializable {
	private static final long serialVersionUID = 653537183934165621L;

	@Column(nullable=false)
	private String userId;

	@Column(nullable=false)
	private Integer visitCount;
	
	@Column(nullable=false)
	private Date lastVisit = new Date();

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(Integer visitCount) {
		this.visitCount = visitCount;
	}

	public Date getLastVisit() {
		return lastVisit;
	}

	public void setLastVisit(Date lastVisit) {
		this.lastVisit = lastVisit;
	}
}
