package se.goteborg.retursidan.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import se.goteborg.retursidan.model.entity.Unit;

/**
 *  Data access object for the Unit entity objects
 * 
 */
@Repository
public class UnitDAO extends BaseDAO<Unit> {
	
	/**
	 * @see BaseDAO#findById(int)
	 */
	@Override
	public Unit findById(int id) {
		return (Unit)getSessionFactory().getCurrentSession().get(Unit.class, id);
	}

	/**
	 * @see BaseDAO#findAll()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Unit> findAll() {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Unit.class);
		criteria.addOrder(Order.asc("name"));
		return criteria.list();
	}
}
