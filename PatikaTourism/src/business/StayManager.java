package business;

import dao.StayDao;
import entity.Stay;

import java.util.ArrayList;

public class StayManager {
    private final StayDao stayDAO;
    private PriceManager priceManager;

    public StayManager() {
        this.stayDAO = new StayDao();
        this.priceManager = new PriceManager();
    }

    public ArrayList<Stay> getList() {
        return this.stayDAO.getList();
    }

    public ArrayList<Stay> getListHotelId(int hotelID) {
        return this.stayDAO.getListHotelId(hotelID);
    }

    public boolean add(Stay Stay) {
        return this.stayDAO.add(Stay);
    }

    public boolean delete(int lodgingID) {
        if (!priceManager.getListStayId(lodgingID).isEmpty())
            priceManager.deleteStayId(lodgingID);

        return this.stayDAO.delete(lodgingID);
    }

    public boolean update(Stay Stay) {
        return this.stayDAO.update(Stay);
    }

    public ArrayList<Stay> searchList(Stay Stay) {
        return this.stayDAO.searchList(Stay);
    }

    public Stay searchWithStayId(int lodgingID) {
        return this.stayDAO.searchWithStayId(lodgingID);
    }

    public Stay searchWithHotelId(int hotelID) {
        return this.stayDAO.searchWithHotelId(hotelID);
    }

    public Stay searchWithHotelIdTypeId(int hotelID, String type) {
        return this.stayDAO.searchWithHotelIdTypeId(hotelID, type);
    }
}
