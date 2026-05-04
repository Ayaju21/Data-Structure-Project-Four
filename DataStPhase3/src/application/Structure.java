package application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Structure {

    public final static int FIRST_YEAR = 2017;
    public final static int LAST_YEAR = 2023;

    private static final YearsTree data = new YearsTree();

    public static YearsTree getData() {
        return data;
    }

    public static String getDaysTree(int year , int month , boolean pretify) throws Exception {
        YearNode y = data.search(year);
        if ( y == null ) {
            throw new Exception("There is NO data in year (" + year + ")");
        }
        MonthNode m = y.months.search(month);
        if ( m == null ) {
            throw new Exception("There is NO data in month (" + month + ") in year (" + year + ")");
        }
        return m.days.getLevelOrderData(pretify);
    }

    public static String getMonthsTree(int year , boolean pretify) throws Exception {
        YearNode y = data.search(year);
        if ( y == null ) {
            throw new Exception("There is NO data in year (" + year + ")");
        }
        return y.months.getLevelOrderData(pretify);
    }

    public static String getYearsTree(boolean pretify) {
        return data.getLevelOrderData(pretify);
    }

    //----------------------------------------------------------------------------------
    public static String getYearsRootHeight() {
        YearNode y = data.getRoot();
        return "Root of years tree is " + y.year + ", and height = " + y.height;
    }

    public static String getMonthsRootHeight(int year) {
        MonthNode m = data.search(year).months.getRoot();
        return "Root of Month tree is " + m.getMonthString() + ", and height = " + m.height;
    }

    public static String getDaysRootHeight(int year , int month) {
        DayNode d = data.search(year).months.search(month).days.getRoot();
        return "Root of day tree is " + d.day + ", and height = " + d.height;
    }
    //-------------------------------------------------------------------------------

    public static int getYearHeight(int year) {
        YearNode y = data.search(year);
        return y.height;
    }

    public static int getMonthHeight(int year , int month) {
        MonthNode m = data.search(year).months.search(month);
        return m.height;
    }

    public static int getDayHeight(int year , int month , int day) {
        DayNode d = data.search(year).months.search(month).days.search(day);
        return d.height;
    }

    private static boolean isDateMatchs(LocalDate date , String searchField) {
        searchField = searchField.replaceAll(" " , "").replaceAll("-" , "/") + "/ / /";
        String year = searchField.split("/")[0].trim();
        String month = searchField.split("/")[1].trim();
        String day = searchField.split("/")[2].trim();
        boolean yearMatches = year.isEmpty() || date.getYear() == Integer.parseInt(year);
        boolean monthMatches = month.isEmpty() || date.getMonthValue() == Integer.parseInt(month);
        boolean dayMatches = day.isEmpty() || date.getDayOfMonth() == Integer.parseInt(day);

        return yearMatches && monthMatches && dayMatches;
    }

    public static List<Electricity> searchInData(int fromIndex , int toIndex , String searchField) {
        int indexCounter = 0;
        List<Electricity> data = new ArrayList<>();

        List<YearNode> years = Structure.data.inOrderList();
        for ( YearNode year : years ) {
            List<MonthNode> months = year.months.inOrderList();
            for ( MonthNode month : months ) {
                List<DayNode> days = month.days.inOrderList();
                for ( DayNode day : days ) {
                    Electricity record = day.getRecord();
                    if ( record != null ) {
                        if ( searchField == null || searchField.isEmpty()
                                || isDateMatchs(record.getDate() , searchField) ) {
                            if ( indexCounter < fromIndex ) {
                                indexCounter++;
                            } else if ( indexCounter <= toIndex || toIndex < 0 ) {
                                indexCounter++;
                                data.add(record);
                            } else {
                                return data;
                            }
                        }
                    }
                }
            }
        }
        return data;
    }

    public static void appendRecords(List<Electricity> records) {
        for ( Electricity record : records ) {
            appendRecord(record);
        }
    }

    public static void editRecords(List<Electricity> records) {
        for ( Electricity record : records ) {
            editRecord(record);
        }
    }

    public static boolean appendRecord(Electricity record) {
        if ( data.search(record.getDate().getYear()) == null ) {
            data.insert(record.getDate().getYear());
            return data.search(record.getDate().getYear()).addRecord(record);
        }
        return data.search(record.getDate().getYear()).addRecord(record);
    }

    public static boolean editRecord(Electricity record) {
        return data.editRecord(record);
    }

    public static void initTheDataList() {
        for ( int year = FIRST_YEAR ; year <= LAST_YEAR ; year++ ) {
            MonthsTree months = new MonthsTree();
            for ( int month = 1 ; month <= 12 ; month++ ) {
                DaysTree days = new DaysTree();
                for ( int day = 1 ; day <= 31 ; day++ ) {
                    days.insert(day);
                }
                months.insert(month);
                months.search(month).days = days;
            }
            data.insert(year);
            data.search(year).months = months;
        }
    }

    public static boolean deleteRecord(Electricity record) {
        return data.deleteRecord(record);
    }

}