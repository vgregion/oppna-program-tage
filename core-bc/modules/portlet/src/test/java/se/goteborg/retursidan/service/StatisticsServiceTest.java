package se.goteborg.retursidan.service;

import org.apache.commons.collections4.KeyValue;
import org.apache.commons.collections4.keyvalue.DefaultKeyValue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import se.goteborg.retursidan.model.DivisionDepartmentKey;
import se.goteborg.retursidan.model.Year;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StatisticsServiceTest {

    @Mock
    private ModelService modelService;

    @InjectMocks
    private StatisticsService statisticsService;

    @Before
    public void setup() {
        Map<DivisionDepartmentKey, KeyValue<Year, Long>> _2018 = new TreeMap<>();
        _2018.put(DivisionDepartmentKey.from("division1", "department1"), new DefaultKeyValue<>(Year.of("2018"), 5L));
        _2018.put(DivisionDepartmentKey.from("division2", "department2"), new DefaultKeyValue<>(Year.of("2018"), 10L));
        
        Map<DivisionDepartmentKey, KeyValue<Year, Long>> _2019 = new TreeMap<>();
        _2019.put(DivisionDepartmentKey.from("division1", "department1"), new DefaultKeyValue<>(Year.of("2019"), 6L));
        _2019.put(DivisionDepartmentKey.from("division1", "department11"), new DefaultKeyValue<>(Year.of("2019"), 7L));
        _2019.put(DivisionDepartmentKey.from("division2", "department2"), new DefaultKeyValue<>(Year.of("2019"), 11L));
        _2019.put(DivisionDepartmentKey.from("division3", "department3"), new DefaultKeyValue<>(Year.of("2019"), 15L));


        when(modelService.calculateDepartmentAndDivisionGroupCount(eq(2018))).thenReturn(_2018);
        when(modelService.calculateDepartmentAndDivisionGroupCount(eq(2019))).thenReturn(_2019);
    }

    @Test
    public void aggregateYearsOfDivisionsAndDepartments() {
        Map<DivisionDepartmentKey, Map<Year, Long>> keyMapMap = statisticsService.aggregateYearsOfDivisionsAndDepartments(
                List.of(2018, 2019)
        );

        assertTrue(keyMapMap.containsKey(DivisionDepartmentKey.from("division1", "department1")));
        assertTrue(keyMapMap.containsKey(DivisionDepartmentKey.from("division1", "department11")));
        assertTrue(keyMapMap.containsKey(DivisionDepartmentKey.from("division2", "department2")));
        assertTrue(keyMapMap.containsKey(DivisionDepartmentKey.from("division3", "department3")));

        assertEquals(5L, keyMapMap.get(DivisionDepartmentKey.from("division1", "department1")).get(Year.of("2018")).longValue());
        assertNull(keyMapMap.get(DivisionDepartmentKey.from("division1", "department11")).get(Year.of("2018")));
        assertEquals(10L, keyMapMap.get(DivisionDepartmentKey.from("division2", "department2")).get(Year.of("2018")).longValue());
        assertNull(keyMapMap.get(DivisionDepartmentKey.from("division3", "department3")).get(Year.of("2018")));

        assertEquals(6L, keyMapMap.get(DivisionDepartmentKey.from("division1", "department1")).get(Year.of("2019")).longValue());
        assertEquals(7L, keyMapMap.get(DivisionDepartmentKey.from("division1", "department11")).get(Year.of("2019")).longValue());
        assertEquals(11L, keyMapMap.get(DivisionDepartmentKey.from("division2", "department2")).get(Year.of("2019")).longValue());
        assertEquals(15L, keyMapMap.get(DivisionDepartmentKey.from("division3", "department3")).get(Year.of("2019")).longValue());
    }

    @Test
    public void exportDivisionsAndDepartmentsAsExcel() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        statisticsService.writeDivisionsAndDepartmentsAsExcel(baos);

        assertTrue(baos.size() > 0);
    }
}
