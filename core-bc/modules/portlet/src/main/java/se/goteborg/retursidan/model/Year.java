package se.goteborg.retursidan.model;

public class Year implements Comparable<Year> {

    public final String stringValue;
    public final int intValue;

    public static Year of(String year) {
        return new Year(year);
    }

    public static Year of(Integer year) {
        return new Year(year.toString());
    }

    private Year(String year) {
        this.stringValue = year;
        this.intValue = Integer.parseInt(year);
    }

    @Override
    public int compareTo(Year o) {
        return this.stringValue.compareTo(o.stringValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Year year = (Year) o;

        return stringValue.equals(year.stringValue);
    }

    @Override
    public int hashCode() {
        return stringValue.hashCode();
    }

    @Override
    public String toString() {
        return stringValue;
    }
}
