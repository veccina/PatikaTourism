package business;

import core.Helper;
import dao.RoomDao;
import entity.Room;

import java.sql.Date;
import java.util.ArrayList;

public class RoomManager {
    private static RoomManager instance = null;

    private final RoomDao roomDAO;
    private final ReservationManager reservationManager;
    private final PriceManager priceManager;

    private RoomManager() {
        this.roomDAO = new RoomDao();
        this.reservationManager = ReservationManager.getInstance();
        this.priceManager = new PriceManager();
    }

    public static RoomManager getInstance() {
        if (instance == null) {
            synchronized (RoomManager.class) {
                if (instance == null) {
                    instance = new RoomManager();
                }
            }
        }
        return instance;
    }

    public ArrayList<Room> getList() {
        return this.roomDAO.getList();
    }

    public ArrayList<Room> getListStayDates(Date customerArrival, Date customerDeparture) {
        return this.roomDAO.getListStayDates(customerArrival, customerDeparture);
    }

    public ArrayList<Room> getListHotelId(int hotelID) {
        return this.roomDAO.getListHotelId(hotelID);
    }

    public boolean add(Room room) {

        if (searchWithNameHotelId(room.getHotelId(), room.getName()) != null) {
            Helper.showMsg("Bu oda türünden zaten var. Eklemek isterseniz lütfen stoğunu arttırınız.");
            return false;
        }

        return this.roomDAO.add(room);
    }
    public boolean delete(int roomID) {
        if (!reservationManager.getListRoomId(roomID).isEmpty())
            reservationManager.deleteRoomId(roomID);

        if (!priceManager.getListRoomId(roomID).isEmpty())
            reservationManager.deleteRoomId(roomID);

        return this.roomDAO.delete(roomID);
    }
    public boolean update(Room room) {
        return this.roomDAO.update(room);
    }

    public ArrayList<Room> searchList(Room room) {
        return this.roomDAO.searchList(room);
    }

    public Room searchRoomId(int roomID) {
        return this.roomDAO.findByRoomID(roomID);
    }

    public Room searchWithNameHotelId(int hotelID, String name) {
        return this.roomDAO.searchWithNameHotelId(hotelID, name);
    }
}
