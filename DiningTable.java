import java.sql.Date;

public class DiningTable {
    private int tableNo;
    private int capacity;
    private TableStatus status;
    private Date expectedFreeTime;

    public void reserve() {
        status = TableStatus.RESERVED;
    }

    public void occupy() {
        status = TableStatus.UNAVAILABLE;
    }

    public void release() {
        status = TableStatus.AVAILABLE;
    }

    public boolean isAvailable(int partySize) {
        return status == TableStatus.AVAILABLE && capacity >= partySize;
    }
}
