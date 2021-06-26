import java.io.Serializable;

public class Customer implements Serializable {
    
    private String name;
    private String seatType;
    private int seatCount;

    public Customer(String name, String seatType, int seatCount) {
        this.name = name;
        this.seatType = seatType;
        this.seatCount = seatCount;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSeatType() {
        return this.seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public int getSeatCount() {
        return this.seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }
}