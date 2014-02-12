package se.goteborg.retursidan.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import se.goteborg.retursidan.model.entity.Photo;

@Repository
public class PhotoDAO extends BaseDAO<Photo> {
	
	/**
	 * @see BaseDAO#findById(int)
	 */
	@Override
	public Photo findById(int id) {
		return (Photo)getSessionFactory().getCurrentSession().get(Photo.class, id);
	}

	/**
	 * @see BaseDAO#findAll()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Photo> findAll() {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Photo.class);
		criteria.addOrder(Order.desc("id"));
		return criteria.list();
	}
}
