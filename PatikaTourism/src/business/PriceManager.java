package business;

import dao.PriceDao;
import entity.Price;

import java.util.ArrayList;

public class PriceManager {
    private final PriceDao priceDAO;
    public PriceManager() {
        this.priceDAO = new PriceDao();
    }

    public ArrayList<Price> getList() {
        return this.priceDAO.getList();
    }

    public ArrayList<Price> getListRoomId(int roomID) {
        return this.priceDAO.getListRoomId(roomID);
    }

    public ArrayList<Price> getListStayId(int lodgingID) {
        return this.priceDAO.getListStayId(lodgingID);
    }

    public Price searchPriceId(int priceID) {
        return this.priceDAO.searchPriceId(priceID);
    }

    public Price searchStayIdRoomId(int lodgingID, int roomID) {
        return this.priceDAO.searchStayIdRoomId(lodgingID, roomID);
    }

    public boolean add(Price price) {
        return this.priceDAO.add(price);
    }

    public boolean update(Price price) {
        return this.priceDAO.update(price);
    }

    public boolean delete(int priceID) {
        return this.priceDAO.delete(priceID);
    }

    public boolean deleteRoomId(int roomID) {
        return this.priceDAO.delete(roomID);
    }

    public boolean deleteStayId(int lodgingID) {
        return this.priceDAO.deleteStayId(lodgingID);
    }




}
