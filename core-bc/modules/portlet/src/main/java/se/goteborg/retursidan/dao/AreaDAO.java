package se.goteborg.retursidan.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import se.goteborg.retursidan.model.entity.Area;
import se.goteborg.retursidan.model.entity.Unit;

import java.util.List;

/**
 *  Data access object for the Area entity objects
 * 
 */
@Repository
public class AreaDAO extends BaseDAO<Area> {
	
	/**
	 * @see BaseDAO#findById(int)
	 */
	@Override
	public Area findById(int id) {
		return (Area)getSessionFactory().getCurrentSession().get(Area.class, id);
	}

	/**
	 * @see BaseDAO#findAll()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Area> findAll() {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Area.class);
		criteria.addOrder(Order.asc("name"));
		return criteria.list();
	}
}
