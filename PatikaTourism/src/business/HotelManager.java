package business;

import core.Helper;
import dao.HotelDao;
import entity.Facility;
import entity.Hotel;
import entity.Stay;
import entity.Room;

import java.util.ArrayList;

public class HotelManager {
    private final HotelDao hotelDAO;
    private final FacilityManager facilityManager;
    private final StayManager stayManager;
    private final PeriodManager periodManager;
    private final RoomManager roomManager;

    public HotelManager() {
        this.hotelDAO = new HotelDao();
        this.facilityManager = new FacilityManager();
        this.stayManager = new StayManager();
        this.periodManager = new PeriodManager();
        this.roomManager = RoomManager.getInstance();
    }

    public ArrayList<Hotel> getList() {
        return this.hotelDAO.getList();
    }

    public boolean add(Hotel hotel) {
        if (this.hotelDAO.searchWithEmail(hotel.getEmail()) != null) {
            Helper.showMsg("error");
            return false;
        }
        return this.hotelDAO.add(hotel);
    }

    public boolean delete(int hotelID) {
        if (!facilityManager.getListHotelId(hotelID).isEmpty()) {
            ArrayList<Facility> deleteFac = facilityManager.getListHotelId(hotelID);

            for (Facility facility : deleteFac)
                facilityManager.delete(facility.getFacilityID());
        }

        if (stayManager.searchWithHotelId(hotelID) != null) {
            ArrayList<Stay> deleteStay = stayManager.getListHotelId(hotelID);

            for (Stay Stay : deleteStay)
                stayManager.delete(Stay.getLodgingID());
        }

        if (!roomManager.getListHotelId(hotelID).isEmpty()) {
            ArrayList<Room> deleteRoom = roomManager.getListHotelId(hotelID);

            for (Room room : deleteRoom)
                roomManager.delete(room.getRoomID());
        }

        if (periodManager.SearchWithHotelId(hotelID) != null)
            periodManager.deleteWithHotelId(hotelID);

        return this.hotelDAO.delete(hotelID);
    }

    public boolean update(Hotel hotel) {
        if (this.hotelDAO.searchWithEmail(hotel.getEmail()) != null && this.hotelDAO.searchWithEmail(hotel.getEmail()).getHotelID() != hotel.getHotelID()) {
            Helper.showMsg("Kayıtlı e-mail. Lütfen farklı bir e-mail adresi giriniz !!");
            return false;
        }
        return this.hotelDAO.update(hotel);
    }
    public Hotel searchWithHotelId(int id) {
        return this.hotelDAO.searchWithHotelId(id);
    }
    public Hotel searchWithEmail(String email) {
        return this.hotelDAO.searchWithEmail(email);
    }

    public ArrayList<Hotel> searchList(Hotel hotel) {
        return this.hotelDAO.searchList(hotel);
    }
    public ArrayList<Hotel> searchList(String value) {
        return this.hotelDAO.searchList(value);
    }
}
