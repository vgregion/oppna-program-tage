package se.goteborg.retursidan.dao;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import se.goteborg.retursidan.model.PagedList;
import se.goteborg.retursidan.model.entity.Area;
import se.goteborg.retursidan.model.entity.Request;
import se.goteborg.retursidan.model.entity.Request.Status;
import se.goteborg.retursidan.model.entity.Unit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *  Data access object for the Request entity objects
 *
 */
@Repository
public class RequestDAO extends BaseDAO<Request> {

	/**
	 * @see BaseDAO#findById(int)
	 */
	@Override
	public Request findById(int id) {
		return (Request)getSessionFactory().getCurrentSession().get(Request.class, id);
	}

	/**
	 * @see BaseDAO#findAll()
	 */
	@Override
	public List<Request> findAll() {
		return findAll(null);
	}

	/**
	 * Find wanted requests as a paged list using the status as filter
	 * @param status The status to filter with, or null no status should be used to filter
	 * @param page The page number to retrieve
	 * @param pageSize The size of each page
	 * @return A paged list with the result
	 */
	@SuppressWarnings("unchecked")
	public PagedList<Request> find(Status status, int page, int pageSize) {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Request.class);
		if (status != null) {
			criteria.add(Restrictions.eq("status", status));
		}
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		criteria.setProjection(Projections.rowCount());
		long totalCount = (Long)criteria.list().get(0);
		criteria.setProjection(null);

        criteria.addOrder(Order.desc("created"));
		criteria.setMaxResults(pageSize);
		criteria.setFetchSize(pageSize);
		criteria.setFirstResult((page - 1) * pageSize);

		return new PagedList<Request>(criteria.list(), page, pageSize, (int)totalCount);
	}


	/**
	 * Find all wanted request for the given status
	 * @param status the status to search for or null if all requests should be retrieved
	 * @return a list of found requests
	 */
	@SuppressWarnings("unchecked")
	public List<Request> findAll(Status status) {
		Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Request.class);
		if (status != null) {
			criteria.add(Restrictions.eq("status", status));
		}
		criteria.addOrder(Order.desc("created"));
		return criteria.list();
	}

	/**
	 * Retrieve the number of requests found in the database
	 * @return the number of requests found
	 */
	public Map<Integer, Integer> count() {
		return count((Unit) null);
	}

	/**
	 * Retrieve the number of requests found in the database for the given unit
	 * @param unit the unit to search for, or null if all requests should be counted
	 * @return the number of requests found
	 */
	public Map<Integer, Integer> count(Unit unit) {
		Map<Integer, Integer> result = new LinkedHashMap<>();

		List<Integer> lastYears = getLastYears();

		for (Integer year : lastYears) {

            Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Request.class);
            if (unit != null) {
                criteria.add(Restrictions.eq("unit", unit));
            }

			addDateRestriction(year, criteria);

			int number = ((Number)criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();

            result.put(year, number);
		}

        return result;
	}

	private List<Integer> getLastYears() {
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);

		return Arrays.asList(currentYear - 4, currentYear - 3, currentYear - 2, currentYear - 1, currentYear);
	}

	/**
	 * Expire any request that is older than the provided amount of days
	 * @param days The maximum number of days
	 * @return the number of ads removed
	 */
	public int expireOldRequests(int days) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -days);
		Date maxDate = cal.getTime();
		Query query = getSessionFactory().getCurrentSession().createQuery("UPDATE Request SET status=:status WHERE created <= :maxDate AND status=:publishStatus");
		query.setParameter("status", Status.EXPIRED);
		query.setParameter("publishStatus", Status.PUBLISHED);
		query.setDate("maxDate", maxDate);
		return query.executeUpdate();
	}

	public Map<Integer, Integer> countNonNullArea() {
        Map<Integer, Integer> result = new LinkedHashMap<>();

        List<Integer> lastYears = getLastYears();

        for (Integer year : lastYears) {
            Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Request.class);

            criteria.add(Restrictions.isNotNull("area"));

			addDateRestriction(year, criteria);

			int number = ((Number) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();

            result.put(year, number);
        }

        return result;
	}

	private void addDateRestriction(Integer year, Criteria criteria) {
		try {
			String startOfYear = year + "-01-01";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			criteria.add(Restrictions.ge("created", sdf.parse(startOfYear)));
			String startOfNextYear = (year + 1) + "-01-01";
			criteria.add(Restrictions.lt("created", sdf.parse(startOfNextYear)));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public Map<Integer, Integer> count(Area area) {
        Map<Integer, Integer> result = new HashMap<>();

        List<Integer> lastYears = getLastYears();

        for (Integer year : lastYears) {
            Criteria criteria = getSessionFactory().getCurrentSession().createCriteria(Request.class);
            if (area != null) {
                criteria.add(Restrictions.eq("area", area));
            }

			addDateRestriction(year, criteria);

			int number = ((Number) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();

            result.put(year, number);
        }

        return result;
	}

}
