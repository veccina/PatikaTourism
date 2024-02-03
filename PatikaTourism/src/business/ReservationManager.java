package business;

import dao.ReservationDao;
import entity.Reservation;
import entity.Room;

import java.util.ArrayList;

public class ReservationManager {
    private static ReservationManager instance = null;
    private final ReservationDao reservationDAO;
    private RoomManager roomManager;


    // değerlendirme 18 : Acenta çalışanı müşterinin talebine uygun odayı müşteri bilgilerini girerek başarılı şekilde rezervasyon yapabiliyor
    // değerlendirme 19 : Rezervasyon yapılan odanın stoğu azalıyor
    // değerlendirme 23 : Rezervasyon silme işleminden sonra ilgili odanın stoğu artıyor.
    private ReservationManager() {
        this.reservationDAO = new ReservationDao();

    }

    public static ReservationManager getInstance() {
        if (instance == null) {
            synchronized (RoomManager.class) {
                if (instance == null) {
                    instance = new ReservationManager();
                }
            }
        }
        return instance;
    }

    public ArrayList<Reservation> getList() {
        return this.reservationDAO.getList();
    }

    public ArrayList<Reservation> getListRoomId(int roomID) {
        return this.reservationDAO.getListRoomId(roomID);
    }

    public Reservation searchReservationId(int reservationID) {
        return this.reservationDAO.searchReservationId(reservationID);
    }

    public boolean add(Reservation reservation) {
        this.roomManager = RoomManager.getInstance();
        Room room = this.roomManager.searchRoomId(reservation.getRoomId());

        if (room != null) {
            room.setStock(room.getStock() - 1);
            this.roomManager.update(room);
        }

        return this.reservationDAO.add(reservation);
    }

    public boolean delete(int reservationID) {
        this.roomManager = RoomManager.getInstance();
        Reservation reservation = searchReservationId(reservationID);

        Room room = this.roomManager.searchRoomId(reservation.getRoomId());

        if (room != null) {
            room.setStock(room.getStock() + 1);
            this.roomManager.update(room);
        }
        return this.reservationDAO.delete(reservationID);
    }

    public boolean deleteRoomId(int roomID) {
        return this.reservationDAO.deleteRoomId(roomID);
    }
}
