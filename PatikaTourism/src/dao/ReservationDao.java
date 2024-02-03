package dao;

import core.Db;
import entity.Reservation;

import java.sql.*;
import java.util.ArrayList;

public class ReservationDao {
    private final Connection connection;

    public ReservationDao() {
        this.connection = Db.getInstance();
    }

    public ArrayList<Reservation> getList() {
        String query = "SELECT * FROM reservation";

        ArrayList<Reservation> reservationList = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                reservationList.add(this.match(resultSet));
            }
            statement.close();
            resultSet.close();

            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reservationList;
    }

    public ArrayList<Reservation> getListRoomId(int roomID) {
        String query = "SELECT * FROM reservation WHERE room_id = ?";

        ArrayList<Reservation> reservationList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, roomID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                reservationList.add(this.match(resultSet));
            }
            preparedStatement.close();
            resultSet.close();

            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reservationList;
    }

    public Reservation searchReservationId(int reservationID) {
        Reservation reservation = null;
        String query = "SELECT * FROM reservation WHERE reservation_id = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, reservationID);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                reservation = this.match(rs);
            }
            preparedStatement.close();
            rs.close();

            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reservation;
    }

    public boolean add(Reservation reservation) {
        String query = "INSERT INTO reservation (reservation_id, room_id, contact_name, contact_telephone, contact_email, " +
                "note, adult_information, child_information, arrival, departure) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            this.match(preparedStatement, reservation);
            int response = preparedStatement.executeUpdate();
            preparedStatement.close();

            return response != -1;

            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean delete(int reservationID) {
        String query = "DELETE FROM reservation WHERE reservation_id = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, reservationID);

            return preparedStatement.executeUpdate() != -1;

            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public boolean deleteRoomId(int roomID) {
        String query = "DELETE FROM reservation WHERE room_id = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, roomID);

            return preparedStatement.executeUpdate() != -1;

            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }
    public Reservation match(ResultSet resultSet) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setReservationID(resultSet.getInt("reservation_id"));
        reservation.setRoomID(resultSet.getInt("room_id"));
        reservation.setContactName(resultSet.getString("contact_name"));
        reservation.setContactTelephone(resultSet.getString("contact_telephone"));
        reservation.setContactEmail(resultSet.getString("contact_email"));
        reservation.setNote(resultSet.getString("note"));
        reservation.setAdultInformation(resultSet.getString("adult_information"));
        reservation.setChildInformation(resultSet.getString("child_information"));
        reservation.setArrival(resultSet.getDate("arrival"));
        reservation.setDeparture(resultSet.getDate("departure"));

        return reservation;
    }
    public void match(PreparedStatement preparedStatement, Reservation reservation) throws SQLException {
        preparedStatement.setInt(1, reservation.getReservationID());
        preparedStatement.setInt(2, reservation.getRoomId());
        preparedStatement.setString(3, reservation.getContactName());
        preparedStatement.setString(4, reservation.getContactTelephone());
        preparedStatement.setString(5, reservation.getContactEmail());
        preparedStatement.setString(6, reservation.getNote());
        preparedStatement.setString(7, reservation.getAdultInformation());
        preparedStatement.setString(8, reservation.getChildInformation());
        preparedStatement.setDate(9, reservation.getArrival());
        preparedStatement.setDate(10, reservation.getDeparture());
    }
}
