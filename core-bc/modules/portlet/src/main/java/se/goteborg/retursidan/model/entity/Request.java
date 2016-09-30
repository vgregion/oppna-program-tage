package se.goteborg.retursidan.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.format.annotation.DateTimeFormat;

import se.goteborg.retursidan.model.GeneralEntityBean;
import se.goteborg.retursidan.util.StringFormatter;

/**
 * Model bean representing a request (a wanted ad).
 *
 */
@Entity
@Table(name="vgr_tage_request")
public class Request extends GeneralEntityBean implements Serializable {
	private static final long serialVersionUID = 5734565963237558347L;

	public enum Status {
	    PUBLISHED, EXPIRED 
	}
	
	@Column(nullable=false)
	private Status status = Status.PUBLISHED;
	
	@Column(nullable=false)
	private String creatorUid;
	
	@Column(nullable=false)
	private String title;
	
	@OneToOne(optional=false)
	@Cascade({CascadeType.SAVE_UPDATE})
	private Person contact;
	
	@DateTimeFormat(style="SS")
	@Column(nullable=false)
	private Date created = new Date();
	
	@Transient
	private Category topCategory;
	
	@ManyToOne(optional=false)
	private Category category;
	
	@Column(nullable=false)
	private String description;
	
	@ManyToOne(optional=false)
	private Unit unit;
	
	@ManyToOne(optional=true)
	private Area area;

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Person getContact() {
		return contact;
	}
	public void setContact(Person contact) {
		this.contact = contact;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getCreatorUid() {
		return creatorUid;
	}
	public void setCreatorUid(String creatorUid) {
		this.creatorUid = creatorUid;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public String getFormattedCreatedDate() {
		return StringFormatter.formatShortDate(created);
	}
	public Category getTopCategory() {
		return topCategory;
	}
	public void setTopCategory(Category topCategory) {
		this.topCategory = topCategory;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public boolean isPublished() {
		return Status.PUBLISHED.equals(status);
	}
	public boolean isExpired() {
		return Status.EXPIRED.equals(status);
	}
}
