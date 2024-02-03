package dao;

import core.Db;
import core.Helper;
import entity.Room;

import java.sql.*;
import java.util.ArrayList;

public class RoomDao {
    private final Connection connection;
    public RoomDao() {
        this.connection = Db.getInstance();
    }

    public ArrayList<Room> getList() {
        ArrayList<Room> roomList = new ArrayList<>();
        String query = "SELECT * FROM room";

        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                roomList.add(this.match(resultSet));
            }
            statement.close();
            resultSet.close();

            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return roomList;
    }

    public ArrayList<Room> getListHotelId(int hotelID) {
        ArrayList<Room> roomList = new ArrayList<>();
        String query = "SELECT * FROM room WHERE hotel_id = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, hotelID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                roomList.add(this.match(resultSet));
            }
            preparedStatement.close();
            resultSet.close();

            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return roomList;
    }

    public ArrayList<Room> getListStayDates(Date customerArrival, Date customerDeparture) {
        ArrayList<Room> roomList = new ArrayList<>();
        String query = "SELECT room.room_id, room.hotel_id, room.period_id, room.name, room.number_of_beds, room.item, room.square_meter, room.stock\n" +
                "FROM room\n" +
                "INNER JOIN period ON room.period_id = period.period_id\n" +
                "WHERE\n" +
                "period.winter_start <= ?\n" +
                "  AND period.winter_end >= ?\n" +
                "OR period.summer_start <= ? AND period.summer_end >= ?" +
                "AND room.stock > 0";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setDate(1, customerArrival);
            preparedStatement.setDate(2, customerDeparture);
            preparedStatement.setDate(3, customerArrival);
            preparedStatement.setDate(4, customerDeparture);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                roomList.add(this.match(resultSet));
            }
            preparedStatement.close();
            resultSet.close();

            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return roomList;
    }

    public boolean add(Room room) {
        String query = "INSERT INTO room (hotel_id, period_id, name, number_of_beds, item, square_meter, stock) VALUES (?,?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            this.match(preparedStatement, room);
            int update = preparedStatement.executeUpdate();
            preparedStatement.close();

            return update != -1;

            } catch (SQLException e) {
            throw new RuntimeException(e);
            }
        }


    public boolean delete(int roomID) {
        String query = "DELETE FROM room WHERE room_id = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, roomID);

            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean update(Room room) {
        String query = "UPDATE room SET hotel_id =?, period_id = ?, name = ?, number_of_beds = ?, item = ?, square_meter = ?, stock = ? WHERE room_id = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            this.match(preparedStatement, room);
            preparedStatement.setInt(8, room.getRoomID());
            int update = preparedStatement.executeUpdate();
            preparedStatement.close();

            return update != -1;

            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Room> searchList(Room room) {
        ArrayList<Room> roomList = new ArrayList<>();

        String query = getQuery(room);

        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                roomList.add(this.match(resultSet));
            }
            statement.close();
            resultSet.close();

            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return roomList;
    }
    private static String getQuery(Room room) {
        String[] searchCriteria = {
                "hotel_id", String.valueOf(room.getHotelId()),
                "name", room.getName(),
        };
        return Helper.querySearch("room", searchCriteria, "AND");
    }

    public Room findByRoomID(int roomID) {
        Room room = null;
        String query = "SELECT * FROM room WHERE room_id = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, roomID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                room = this.match(resultSet);
            }
            preparedStatement.close();
            resultSet.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return room;
    }
    public Room searchWithNameHotelId(int hotelID, String name) {
        Room room = null;
        String query = "SELECT * FROM room WHERE hotel_id = ? AND name = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, hotelID);
            preparedStatement.setString(2, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                room = this.match(resultSet);
            }
            preparedStatement.close();
            resultSet.close();

            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return room;
    }
    private Room match(ResultSet resultSet) throws SQLException {
            Room room = new Room();
            room.setRoomID(resultSet.getInt("room_id"));
            room.setHotelID(resultSet.getInt("hotel_id"));
            room.setPeriodID(resultSet.getInt("period_id"));
            room.setName(resultSet.getString("name"));
            room.setNumberOfBeds(resultSet.getInt("number_of_beds"));
            room.setItem(resultSet.getString("item"));
            room.setSquareMeter(resultSet.getString("square_meter"));
            room.setStock(resultSet.getInt("stock"));
            return room;
        }

    private void match(PreparedStatement preparedStatement, Room room) throws SQLException {
        preparedStatement.setInt(1, room.getHotelId());
        preparedStatement.setInt(2, room.getPeriodID());
        preparedStatement.setString(3, room.getName());
        preparedStatement.setInt(4, room.getNumberOfBeds());
        preparedStatement.setString(5, room.getItem());
        preparedStatement.setString(6, room.getSquareMeter());
        preparedStatement.setInt(7, room.getStock());
    }
}
