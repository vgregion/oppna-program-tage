package se.goteborg.retursidan.service;

import org.apache.commons.collections4.KeyValue;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.goteborg.retursidan.dao.AdvertisementDAO;
import se.goteborg.retursidan.dao.RequestDAO;
import se.goteborg.retursidan.model.DivisionDepartmentKey;
import se.goteborg.retursidan.model.Year;
import se.goteborg.retursidan.model.entity.Advertisement.Status;
import se.goteborg.retursidan.model.entity.Area;
import se.goteborg.retursidan.model.entity.Unit;
import se.goteborg.retursidan.util.ExcelUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.BiFunction;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class StatisticsService {
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	@Autowired
	private AdvertisementDAO advertisementDAO;

	@Autowired
	private RequestDAO requestDAO;

	@Autowired
	private ModelService modelService;

	public Map<Integer, Integer> getTotalNumberOfAds() {
		return advertisementDAO.count();
	}

	public Map<Integer, Integer> getTotalNumberOfRequests() {
		return requestDAO.count();
	}

	public Map<Integer, Integer> getTotalAdsForUnit(Unit unit) {
		return advertisementDAO.count(unit);
	}

	public Map<Integer, Integer> getTotalRequestsForUnit(Unit unit) {
		return requestDAO.count(unit);
	}

	public Map<Integer, Integer> getTotalNumberOfBookedAds() {
		return advertisementDAO.count(Status.BOOKED);
	}

	public Map<Integer, Integer> getBookedAdsForUnit(Unit unit) {
		return advertisementDAO.count(Status.BOOKED, unit);
	}

	public Map<Integer, Integer> getTotalNumberOfExpiredAds() {
		return advertisementDAO.count(Status.EXPIRED);
	}

	public Map<Integer, Integer> getTotalNumberOfAdsWithArea() {
		return advertisementDAO.countNonNullArea(null);
	}

	public Map<Integer, Integer> getTotalNumberOfRequestsWithArea() {
		return requestDAO.countNonNullArea();
	}

	public Map<Integer, Integer> getTotalAdsForArea(Area area) {
		return advertisementDAO.count(null, area);
	}

	public Map<Integer, Integer> getTotalRequestsForArea(Area area) {
		return requestDAO.count(area);
	}

	public Map<Integer, Integer> getTotalNumberOfBookedAdsWithArea() {
		return advertisementDAO.countNonNullArea(Status.BOOKED);
	}

	public Map<Integer, Integer> getBookedAdsForArea(Area area) {
		return advertisementDAO.count(Status.BOOKED, area);
	}

	public void writeDivisionsAndDepartmentsAsExcel(OutputStream outputStream, Status status) {
		Map<String, List<Object[]>> sheets = new LinkedHashMap<>();

		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		List<Integer> years = Arrays.asList(currentYear - 4, currentYear - 3, currentYear - 2, currentYear - 1, currentYear);

		String[] headings = new String[2 + years.size()];
		headings[0] = "Förvaltning";
		headings[1] = "Avdelning";

		for (int i = 2; i < years.size() + 2; i++) {
			headings[i] = years.get(i - 2) + "";
		}

		List<Object[]> departmentsMatrix = new ArrayList<>();

		departmentsMatrix.add(headings);

		Map<DivisionDepartmentKey, Map<Year, Long>> aggregateYears = aggregateYearsOfDivisionsAndDepartments(
				years,
				status
		);

		for (Map.Entry<DivisionDepartmentKey, Map<Year, Long>> entry : aggregateYears.entrySet()) {
			String division = entry.getKey().getDivision();
			String department = entry.getKey().getDepartment();

			Object[] row = new Object[2 + years.size()];
			row[0] = division;
			row[1] = department;

			for (int i = 2; i < years.size() + 2; i++) {
				Year year = Year.of(years.get(i - 2));
				Long count = entry.getValue().get(year);
				row[i] = count != null ? count : 0;
			}

			departmentsMatrix.add(row);
		}

		// Create sum row
		Object[] sumRow = new Object[2 + years.size()];
		sumRow[0] = "summa";
		sumRow[1] = "";
		for (int i = 2; i < years.size() + 2; i++) {
			Year year = Year.of(years.get(i - 2));
			Collection<Map<Year, Long>> values = aggregateYears.values();
			Long count = values.stream().reduce(
					0L,
					(n, yearLongMap) -> n + (yearLongMap.get(year) != null ? yearLongMap.get(year) : 0),
					Long::sum
			);

			sumRow[i] = count;
		}
		departmentsMatrix.add(sumRow);

		sheets.put("Statistik", departmentsMatrix);

            /*for (Map.Entry<String, List<DepartmentDivisionCountTuple>> keyToList : departmentsAndDivisions.entrySet()) {
                row[i]
            }*/






//        headings[1] = "Totalt";
        /*for (int i = 1; i <= 5; i++) {
            headings[i] = years.get(i - 1).toString();
        }*/


		/*for (Map.Entry<String, Long> entry : departmentsAndDivisions.getDepartmentsCount().entrySet()) {
			String department = entry.getKey();
			departmentsMatrix.add(new String[]{department, entry.getValue().toString()});
		}
		sheets.put("Avdelningar", departmentsMatrix);

		List<String[]> divisionsMatrix = new ArrayList<>();
		headings = new String[6];
		headings[0] = "Förvaltning";
		headings[1] = "Totalt";

		divisionsMatrix.add(headings);

		for (Map.Entry<String, Long> entry : departmentsAndDivisions.getDivisionsCount().entrySet()) {
			String division = entry.getKey();
			divisionsMatrix.add(new String[]{division, entry.getValue().toString()});
		}
		sheets.put("Förvaltningar", divisionsMatrix);
*/
		try (InputStream inputStream = ExcelUtil.exportToStream(sheets)) {
//			 FileOutputStream out = new FileOutputStream("/temp/tage-statistik.xlsx")) {
			IOUtils.copy(inputStream, outputStream);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	Map<DivisionDepartmentKey, Map<Year, Long>> aggregateYearsOfDivisionsAndDepartments(List<Integer> years,
																						Status status) {
		Map<DivisionDepartmentKey, Map<Year, Long>> aggregateYears = new TreeMap<>();

		boolean firstYear = true;
		for (int i = 2; i < years.size() + 2; i++) {
			Year year = Year.of(years.get(i - 2));
//			Year yearString = year + "";

			Map<DivisionDepartmentKey, KeyValue<Year, Long>> departmentsAndDivisionsOneYear = modelService
					.calculateDepartmentAndDivisionGroupCount(year.intValue, status);

//            if (firstYear) {
			// Then add all divisions and departments
			for (Map.Entry<DivisionDepartmentKey, KeyValue<Year, Long>> keyToList : departmentsAndDivisionsOneYear.entrySet()) {

				DivisionDepartmentKey key = keyToList.getKey();
				if (aggregateYears.containsKey(key)) {
					// Add another year for a given key.
					aggregateYears.get(key).put(year, keyToList.getValue().getValue());
				} else {
					Map<Year, Long> yearToCount = new TreeMap<>();
					yearToCount.put(year, keyToList.getValue().getValue());
					aggregateYears.put(key, yearToCount);
				}
//                }
			}

		}
		return aggregateYears;
	}
}
