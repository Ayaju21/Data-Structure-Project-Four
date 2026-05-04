package application;
public class DayNode {

    int day;
    private Electricity record;

    int height;
    DayNode left, right;

    // Constructor to make objects of node with record data
    public DayNode(Electricity record) {
        this.record = record;
        this.day = record.getDate().getDayOfMonth();
    }

    public DayNode(int Day) {
        this.day = Day;
        this.height = 1;
    }

    public Electricity getRecord() {
        return record;
    }

    public void setRecord(Electricity record) {
        this.record = record;
        day = record != null ? record.getDate().getDayOfMonth() : day;
    }

    public boolean addRecord(Electricity record) {
        if ( record.getDate().getDayOfMonth() > this.day ) {
            return right.addRecord(record);
        }
        if ( record.getDate().getDayOfMonth() < this.day ) {
            return left.addRecord(record);
        }
        if ( this.record != null ) {
            return false;
        }
        this.record = record;
        return true;
    }

    public boolean editRecord(Electricity record) {
        if ( record.getDate().getDayOfMonth() > this.day ) {
            return right.editRecord(record);
        }
        if ( record.getDate().getDayOfMonth() < this.day ) {
            return left.editRecord(record);
        }
        this.record = record;
        return true;
    }

    public boolean deleteRecord(Electricity record) {
        if ( record.getDate().getDayOfMonth() > this.day ) {
            return right.deleteRecord(record);
        }
        if ( record.getDate().getDayOfMonth() < this.day ) {
            return left.deleteRecord(record);
        }
        this.record = null;
        return true;
    }

    @Override
    public String toString() {
        return day + " : " + (record == null ? "{}" : record.toString());
    }

}