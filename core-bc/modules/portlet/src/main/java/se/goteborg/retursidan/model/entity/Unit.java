package se.goteborg.retursidan.model.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

import se.goteborg.retursidan.model.GeneralEntityBean;

/**
 * Model bean representing a unit
 *
 */
@Entity
@Table(name="vgr_tage_unit")
public class Unit extends GeneralEntityBean implements Serializable {
	private static final long serialVersionUID = 7781919883548238711L;

	private String name;

	public Unit() {
	}
	public Unit(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
