package se.goteborg.retursidan.model;

public class DepartmentDivisionCountTuple {
    private String department;
    private String division;
    private Long count;

    public DepartmentDivisionCountTuple(String department, String division, Long count) {
        this.department = department;
        this.division = division;
        this.count = count;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
