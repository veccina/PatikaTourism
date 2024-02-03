package entity;

import business.HotelManager;

public class Facility {
    private int hotelID;
    private int facilityID;
    private String type;

    public Facility() {}
    public Facility(int facilityID, int hotelID, String type) {
        this.hotelID = hotelID;
        this.facilityID = facilityID;
        this.type = type;
    }
    public int getHotelID() {
        return hotelID;
    }

    public void setHotelID(int hotelID) {
        this.hotelID = hotelID;
    }

    public int getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(int facilityID) {
        this.facilityID = facilityID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
