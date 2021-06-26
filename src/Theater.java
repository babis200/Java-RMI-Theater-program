import java.io.Serializable;

public class Theater implements Serializable{

    int[] price = new int[5];
    int[] availability = new int[5];

    protected Theater() {
        //DEFAULTS

        //Price of each seat type
        price[0] = 50;  //Orchestra - Zone A
        price[1] = 40;  //Orchestra - Zone B
        price[2] = 30;  //Orchestra - Zone C
        price[3] = 25;  //Main Balcony
        price[4] = 20;  //Side Balconies

        //Availability of each seat type
        availability[0] = 200;  //Orchestra - Zone A
        availability[1] = 300;  //Orchestra - Zone B
        availability[2] = 500;  //Orchestra - Zone C
        availability[3] = 100;  //Main Balcony
        availability[4] = 50;   //Side Balconies
    }

    public int getPrice(String seatType) {
        int p;
        switch (seatType) {
            case "OA" : p = this.price[0]; break;    //OA = Orchestra - Zone A
            case "OB" : p = this.price[1]; break;    //OB = Orchestra - Zone B
            case "OC" : p = this.price[2]; break;    //OC = Orchestra - Zone C
            case "MB" : p = this.price[3]; break;    //MB = Main Balcony
            case "SB" : p = this.price[4]; break;    //SB = Side Balconies
            default : p = 0;
        };
        return p;
    }

    public int getAvail(String seatType) {
        int avail;
        switch (seatType) {
            case "OA" : avail = this.availability[0]; break;    //OA = Orchestra - Zone A
            case "OB" : avail = this.availability[1]; break;    //OB = Orchestra - Zone B
            case "OC" : avail = this.availability[2]; break;    //OC = Orchestra - Zone C
            case "MB" : avail = this.availability[3]; break;    //MB = Main Balcony
            case "SB" : avail = this.availability[4]; break;    //SB = Side Balconies
            default : avail = 0;
        };
        return avail;
    }

    public void updateAvail(String seatType, int seatCount) {  
        switch (seatType) {
            case "OA" : this.availability[0] = availability[0] - seatCount; break;  
            case "OB" : this.availability[1] = availability[1] - seatCount; break;
            case "OC" : this.availability[2] = availability[2] - seatCount; break;
            case "MB" : this.availability[3] = availability[3] - seatCount; break;
            case "SB" : this.availability[4] = availability[4] - seatCount; break;
            default : throw new IllegalStateException("Unexpected value: " + seatType);
        }
    }

    protected boolean book(String seatType, int seatCount) {    //returns true when there are enough seats to be booked
        boolean ans = false;
        switch (seatType) {
            case "OA" : if (this.availability[0] >= seatCount) ans = true; break;
            case "OB" : if (this.availability[1] >= seatCount) ans = true; break;
            case "OC" : if (this.availability[2] >= seatCount) ans = true; break;
            case "MB" : if (this.availability[3] >= seatCount) ans = true; break;
            case "SB" : if (this.availability[4] >= seatCount) ans = true; break;
            default : ans = false;
        }
        return ans;
    }

    public boolean cancel(Customer customer, String seatType, int seatCount) {  //checks if the customer has the seats he want to cancel

        return customer.getSeatCount() >= seatCount && customer.getSeatType().equals(seatType);
    }


}