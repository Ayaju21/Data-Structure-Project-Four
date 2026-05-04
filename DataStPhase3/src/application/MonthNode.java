package application;
public class MonthNode {

    private String monthString;
    private int month;
    int height;
    public MonthNode left, right;

    public DaysTree days;

    public MonthNode(int month) {
        setMonth(month);
        this.days = new DaysTree();
        this.height = 1;
    }

    public boolean addRecord(Electricity record) {
        if ( record.getDate().getMonthValue() > this.month ) {
            if ( right == null ) {
                right = new MonthNode(record.getDate().getMonthValue());
            }
            return right.addRecord(record);
        }
        if ( record.getDate().getMonthValue() < this.month ) {
            if ( left == null ) {
                right = new MonthNode(record.getDate().getMonthValue());
            }
            return left.addRecord(record);
        }
        return days.addRecord(record);
    }

    public boolean editRecord(Electricity record) {
        if ( record.getDate().getMonthValue() > this.month ) {
            if ( right == null ) {
                return false;
            }
            return right.editRecord(record);
        }
        if ( record.getDate().getMonthValue() < this.month ) {
            if ( left == null ) {
                return false;
            }
            return left.editRecord(record);
        }
        return days.editRecord(record);
    }

    public boolean deleteRecord(Electricity record) {
        if ( record.getDate().getMonthValue() > this.month ) {
            if ( right == null ) {
                return false;
            }
            return right.deleteRecord(record);
        }
        if ( record.getDate().getMonthValue() < this.month ) {
            if ( left == null ) {
                return false;
            }
            return left.deleteRecord(record);
        }
        return days.deleteRecord(record);
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
        switch ( month ) {
            case 1: this.monthString = "Jan";
                break;
            case 2: this.monthString = "Feb";
                break;
            case 3: this.monthString = "Mar";
                break;
            case 4: this.monthString = "Apr";
                break;
            case 5: this.monthString = "May";
                break;
            case 6: this.monthString = "June";
                break;
            case 7: this.monthString = "July";
                break;
            case 8: this.monthString = "Aug";
                break;
            case 9: this.monthString = "Sep";
                break;
            case 10: this.monthString = "Oct";
                break;
            case 11: this.monthString = "Nov";
                break;
            case 12: this.monthString = "Dec";
                break;
            default: this.monthString = "";
        }
    }

    public String getMonthString() {
        return monthString;
    }

    @Override
    public String toString() {
        return monthString + " : " + days == null ? "{}" : days.toString();
    }

}

