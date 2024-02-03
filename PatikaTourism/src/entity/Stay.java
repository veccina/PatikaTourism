package entity;

public class Stay {
    private String type;
    private int hotelID;
    private int lodgingID;

    public Stay() {}

    public Stay(int lodgingID, int hotelID, String type) {
        this.type = type;
        this.lodgingID = lodgingID;
        this.hotelID = hotelID;
    }

    public int getLodgingID() {
        return lodgingID;
    }
    public void setLodgingID(int lodgingID) {
        this.lodgingID = lodgingID;
    }

    public int getHotelID() {
        return hotelID;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
