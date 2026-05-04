package application;
public class YearNode {

    int year;
    int height;
    public YearNode left, right;

    public MonthsTree months;

    public YearNode(int year) {
        this.year = year;
        this.months = new MonthsTree();
        this.height = 1;
    }

    public boolean addRecord(Electricity record) {   
        if ( record.getDate().getYear() > this.year ) {
            if ( right == null ) {
                right = new YearNode(record.getDate().getYear());
            }
            return right.addRecord(record); //add to the right 
        }
        if ( record.getDate().getYear() < this.year ) {
            if ( left == null ) {
                left = new YearNode(record.getDate().getYear()); //add to the left 
            }
            return left.addRecord(record);
        }
        return months.addRecord(record);
    }

    public boolean editRecord(Electricity record) {
        if ( record.getDate().getYear() > this.year ) {
            if ( right == null ) {
                return false;  // because there exist 
            }
            return right.editRecord(record); // انا بعمل تعديل على اشي موجود 
        }
        if ( record.getDate().getYear() < this.year ) {
            if ( left == null ) {
                return false;
            }
            return left.editRecord(record);
        }
        return months.editRecord(record);
    }

    public boolean deleteRecord(Electricity record) {
        if ( record.getDate().getYear() > this.year ) {
            if ( right == null ) {
                return false;
            }
            return right.deleteRecord(record);
        }
        if ( record.getDate().getYear() < this.year ) {
            if ( left == null ) {
                return false;
            }
            return left.deleteRecord(record);
        }
        return months.deleteRecord(record);
    }

    
    @Override
    public String toString() {
        return year + " : " + months == null ? "{}" : months.toString();
    }

}
