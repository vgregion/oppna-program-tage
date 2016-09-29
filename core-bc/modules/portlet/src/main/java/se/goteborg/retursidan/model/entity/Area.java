package se.goteborg.retursidan.model.entity;

import se.goteborg.retursidan.model.GeneralEntityBean;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Model bean representing a unit
 *
 */
@Entity
@Table(name="vgr_tage_area")
public class Area extends GeneralEntityBean implements Serializable {

	private static final long serialVersionUID = 4648226583786485251L;

	private String name;

	public Area() {
	}
	public Area(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
