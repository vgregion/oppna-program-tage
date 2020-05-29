package se.goteborg.retursidan.model;

import java.util.Map;

public class DepartmentAndDivisionGroupCount {
    private Map<String, Long> departmentsCount;
    private Map<String, Long> divisionsCount;

    public DepartmentAndDivisionGroupCount(Map<String, Long> departmentsCount, Map<String, Long> divisionsCount) {
        this.departmentsCount = departmentsCount;
        this.divisionsCount = divisionsCount;
    }

    public Map<String, Long> getDepartmentsCount() {
        return departmentsCount;
    }

    public void setDepartmentsCount(Map<String, Long> departmentsCount) {
        this.departmentsCount = departmentsCount;
    }

    public Map<String, Long> getDivisionsCount() {
        return divisionsCount;
    }

    public void setDivisionsCount(Map<String, Long> divisionsCount) {
        this.divisionsCount = divisionsCount;
    }
}
