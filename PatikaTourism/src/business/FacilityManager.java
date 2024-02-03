package business;

import dao.FacilityDao;
import entity.Facility;

import java.util.ArrayList;

public class FacilityManager {
    private final FacilityDao facilityDAO;

    public FacilityManager() {
        this.facilityDAO = new FacilityDao();
    }

    public ArrayList<Facility> getList() {
        return this.facilityDAO.getList();
    }
    public ArrayList<Facility> getListHotelId(int hotelID) {
        return this.facilityDAO.getListHotelId(hotelID);
    }

    public boolean add(Facility facility) {
        return this.facilityDAO.add(facility);
    }
    public boolean delete(int facilityID) {
        return this.facilityDAO.delete(facilityID);
    }

    public boolean update(Facility facility) {
        return this.facilityDAO.update(facility);
    }

    public Facility searchWithFacilityId(int facilityID) {
        return this.facilityDAO.searchWithFacilityId(facilityID);
    }

    public Facility searchWithHotelId(int hotelID) {
        return this.facilityDAO.searchWithHotelId(hotelID);
    }

    public Facility searchWithHotelTypeId(int hotelID, String type) {
        return this.facilityDAO.searchWithHotelTypeId(hotelID, type);
    }
    public ArrayList<Facility> searchList(Facility facility) {
        return this.facilityDAO.searchList(facility);
    }
}
