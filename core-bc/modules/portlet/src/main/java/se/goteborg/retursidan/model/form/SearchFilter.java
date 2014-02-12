package se.goteborg.retursidan.model.form;

import org.springframework.stereotype.Component;

import se.goteborg.retursidan.model.GeneralModelBean;
import se.goteborg.retursidan.model.entity.Category;
import se.goteborg.retursidan.model.entity.Unit;

/**
 * Model bean representing a search filter
 *
 */
@Component
public class SearchFilter extends GeneralModelBean {
	private Category topCategory;
	private Category subCategory;
	private Unit unit;

	public Category getTopCategory() {
		return topCategory;
	}
	public void setTopCategory(Category topCategory) {
		this.topCategory = topCategory;
	}
	public Category getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(Category subCategory) {
		this.subCategory = subCategory;
	}
	public Unit getUnit() {
		return unit;
	}
	public void setUnit(Unit unit) {
		this.unit = unit;
	}
}
