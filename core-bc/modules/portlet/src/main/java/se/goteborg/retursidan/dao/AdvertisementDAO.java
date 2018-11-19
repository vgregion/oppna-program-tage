package se.goteborg.retursidan.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import se.goteborg.retursidan.model.PagedList;
import se.goteborg.retursidan.model.entity.Advertisement;
import se.goteborg.retursidan.model.entity.Advertisement.Status;
import se.goteborg.retursidan.model.entity.Area;
import se.goteborg.retursidan.model.entity.Category;
import se.goteborg.retursidan.model.entity.Photo;
import se.goteborg.retursidan.model.entity.Unit;

/**
 * Data access object for the Advertisement entity objects
 *
 */
@Repository
public class AdvertisementDAO extends BaseDAO<Advertisement> {
	
	/**
	 * @see BaseDAO#findById(int)
	 */
	@Override
	public Advertisement findById(int id) {
		Advertisement advertisement = (Advertisement)getSessionFactory().getCurrentSession().get(Advertisement.class, id);
		if (advertisement != null) {
			replaceProxiedPhoto(advertisement);
		}
		return advertisement;
	}

	/**
	 * @see BaseDAO#findAll()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Advertisement> findAll() {
		throw new UnsupportedOperationException("Not used");
	}
	
	/**
	 * Retrieve a paged list of advertisements from the database, filtered using the method parameters
	 * @param creatorUid The userId of the author of the advertisement, or null for all authors
	 * @param status The advertisement status, or null for all statuses
	 * @param topCategory The top category for the advertisements, or null for all categories
	 * @param category The sub category for the advertisements, or null for all sub categories
	 * @param unit The unit of the advertisement, or null for all units
	 * @param usingDisplayOption If true and no unit is set, fetch only advertisements aimed for the entire city 	
	 * @param page The page number
	 * @param pageSize The size of each page
	 * @return a PagedList containing the results
	 */
	@SuppressWarnings("unchecked")
	public PagedList<Advertisement> find(String creatorUid,
										 Status status,
										 Category topCategory,
										 Category category,
										 Unit unit,
										 Area area,
										 Boolean hidden,
										 boolean usingDisplayOption,
										 int page,
										 int pageSize) {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Advertisement.class);
		if (creatorUid != null) {
			criteria.add(Restrictions.eq("creatorUid", creatorUid));
		}
		if (status != null) {
			criteria.add(Restrictions.eq("status", status));
		}
		if (topCategory != null && topCategory.getId() != -1) {
			criteria.createCriteria("category").add(Restrictions.eq("parent", topCategory));
			if (category != null && category.getId() != -1) {
				criteria.add(Restrictions.eq("category", category));
			}
		}
		if (unit  != null && unit.getId() != -1) {
			criteria.add(Restrictions.eq("unit", unit));
		} else if (usingDisplayOption){
			criteria.add(Restrictions.eq("displayOption", Advertisement.DisplayOption.ENTIRE_CITY));
		}
		
		if (area  != null && area.getId() != -1) {
			criteria.add(Restrictions.eq("area", area));
		}

		if (hidden != null) {
			if (hidden) {
				criteria.add(Restrictions.eq("hidden", true));
			} else {
				criteria.add(Restrictions.or(Restrictions.isNull("hidden"), Restrictions.eq("hidden", false)));
			}
		}

		// get the row count for this query
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria.setProjection(Projections.rowCount());
		long totalCount = (Long)criteria.list().get(0);
		
		// clear the projection and set the result transformer again
		criteria.setProjection(null);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		criteria.addOrder(Order.desc("created"));
		criteria.setMaxResults(pageSize);
		criteria.setFetchSize(pageSize);
		criteria.setFirstResult((page - 1) * pageSize);
		List<Advertisement> list = criteria.list();
				
		return new PagedList<Advertisement>(replaceProxiedPhotos(list), page, pageSize, (int)totalCount);
	}
	
	/**
	 * Replace all photo objects attached to this advertisement with new, non-proxied objects. This is needed in order to 
	 * avoid errors regarding missing hibernate session when the photo objects are accessed in the JSP pages. We do not need the 
	 * proxied objects because we are not interested of the image byte content but only the id's of the images, in order to construct
	 * photo serving resource URL's. 
	 * @param advertisement The advertisement for which all photos should be replaced
	 * @return The advertisement with replaced photos
	 */
	private Advertisement replaceProxiedPhoto(Advertisement advertisement) {
		List<Photo> photos = advertisement.getPhotos();
		List<Photo> newPhotos = new ArrayList<Photo>();
		for (Photo photo : photos) {
			Photo newPhoto = new Photo();
			newPhoto.setId(photo.getId());
			newPhotos.add(newPhoto);
		}
		advertisement.setPhotos(newPhotos);
		return advertisement;
	}	
	
	/**
	 * Replace all photos for all advertisements in a list
	 * @see AdvertisementDAO#replaceProxiedPhoto(Advertisement)
	 * @param list The list of advertisements
	 * @return The list with advertisements that has been updated with replaced photos
	 */
	private List<Advertisement> replaceProxiedPhotos(List<Advertisement> list) {
		for (Advertisement advertisement : list) {
			replaceProxiedPhoto(advertisement);
		}
		return list;
	}
	
	/**
	 * Retrieve the count of all advertisements in the database
	 * @return the number of advertisements
	 */
	public Integer count() {
		return count(null, (Unit) null);
	}

	/**
	 * Retrieve the count of all advertisements for the specified unit
	 * @param unit The unit to use as filter
	 * @return the number of advertisements
	 */
	public Integer count(Unit unit) {
		return count(null, unit);
	}

	/**
	 * Retrieve the count of all advertisements for the specified status
	 * @param status The status to use as a filter
	 * @return the number of advertisements
	 */
	public Integer count(Status status) {
		return count(status, (Unit) null);
	}

	/**
	 * Count the number of advertisements in the database, filtered using the provided method parameters
	 * @param status The status to filter with, or null for all statuses
	 * @param unit The unit to filter with, or null for all units
	 * @return the number of advertisements
	 */
	public Integer count(Status status, Unit unit) {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Advertisement.class);
		if (unit != null) {
			criteria.add(Restrictions.eq("unit", unit));
		}
		if (status != null) {
			criteria.add(Restrictions.eq("status", status));
		}
		return ((Number)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
	}

	/**
	 * Expire any ad that is older than the provided amount of days
	 * @param days The maximum number of days 
	 * @return the number of ads removed
	 */
	public List<Advertisement> expireOldAds(int days) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -days);
		Date maxDate = cal.getTime();

		List<Advertisement> list = queryTheSameAsGetsUpdated(maxDate);

		Query updateQuery = getSessionFactory().getCurrentSession().createQuery("UPDATE Advertisement SET status=:status WHERE created <= :maxDate AND status=:publishStatus");
		updateQuery.setParameter("status", Status.EXPIRED);
		updateQuery.setParameter("publishStatus", Status.PUBLISHED);
		updateQuery.setDate("maxDate", maxDate);

		int count = updateQuery.executeUpdate();

		if (list.size() != count) {
			throw new RuntimeException("Unexpected count. Query and update did not give the same number.");
		}

		return list;
	}

	private List<Advertisement> queryTheSameAsGetsUpdated(Date maxDate) {
		Query query = getSessionFactory().getCurrentSession().createQuery("SELECT a FROM Advertisement a WHERE a.created <= :maxDate AND a.status=:publishStatus");
		query.setParameter("publishStatus", Status.PUBLISHED);
		query.setDate("maxDate", maxDate);

		return (List<Advertisement>) query.list();
	}

	public Integer countNonNullArea(Status status) {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Advertisement.class);

		criteria.add(Restrictions.isNotNull("area"));

		if (status != null) {
			criteria.add(Restrictions.eq("status", status));
		}

		return ((Number) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
	}

	public Integer count(Status status, Area area) {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Advertisement.class);
		if (area != null) {
			criteria.add(Restrictions.eq("area", area));
		}
		if (status != null) {
			criteria.add(Restrictions.eq("status", status));
		}
		return ((Number)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
	}
}
