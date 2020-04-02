package se.goteborg.retursidan.service;

import org.apache.poi.util.IOUtils;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import se.goteborg.retursidan.util.ExcelUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class StatisticsExport {

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Test
    public void testExport() throws IOException {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext-IT.xml");

        ModelService modelService = ctx.getBean(ModelService.class);

        Map<String, Long>[] departmentsAndDivisions = modelService.groupCountDepartmentsAndDivisions();

        Map<String, List<String[]>> sheets = new LinkedHashMap<>();
        List<String[]> departmentsMatrix = new ArrayList<>();


        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        List<Integer> years = Arrays.asList(currentYear - 4, currentYear - 3, currentYear - 2, currentYear - 1, currentYear);

        String[] headings = new String[6];
        headings[0] = "Avdelning";
        headings[1] = "Totalt";
        /*for (int i = 1; i <= 5; i++) {
            headings[i] = years.get(i - 1).toString();
        }*/

        departmentsMatrix.add(headings);

        for (Map.Entry<String, Long> entry : departmentsAndDivisions[0].entrySet()) {
            String department = entry.getKey();
            departmentsMatrix.add(new String[]{department, entry.getValue().toString()});
        }
        sheets.put("Avdelningar", departmentsMatrix);

        List<String[]> divisionsMatrix = new ArrayList<>();
        headings = new String[6];
        headings[0] = "Förvaltning";
        headings[1] = "Totalt";

        divisionsMatrix.add(headings);

        for (Map.Entry<String, Long> entry : departmentsAndDivisions[1].entrySet()) {
            String division = entry.getKey();
            divisionsMatrix.add(new String[]{division, entry.getValue().toString()});
        }
        sheets.put("Förvaltningar", divisionsMatrix);

        try (InputStream inputStream = ExcelUtil.exportToStream(sheets);
            FileOutputStream out = new FileOutputStream("/temp/tage-statistik.xlsx")) {
            IOUtils.copy(inputStream, out);
        }
    }

}
