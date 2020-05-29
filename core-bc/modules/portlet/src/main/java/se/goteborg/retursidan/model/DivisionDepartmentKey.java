package se.goteborg.retursidan.model;

public class DivisionDepartmentKey implements Comparable<DivisionDepartmentKey> {

    private final String division, department;

    private DivisionDepartmentKey(String division, String department) {
        this.division = division;
        this.department = department;
    }

    public static DivisionDepartmentKey from(String division, String department) {
        return new DivisionDepartmentKey(division, department);
    }

    public String getDivision() {
        return division;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DivisionDepartmentKey that = (DivisionDepartmentKey) o;

        if (!division.equals(that.division)) return false;
        return department.equals(that.department);
    }

    @Override
    public int hashCode() {
        int result = division.hashCode();
        result = 31 * result + department.hashCode();
        return result;
    }

    @Override
    public int compareTo(DivisionDepartmentKey o) {
        return (this.getDivision() + this.getDepartment()).compareTo((o.getDivision() + o.getDepartment()));
    }
}
