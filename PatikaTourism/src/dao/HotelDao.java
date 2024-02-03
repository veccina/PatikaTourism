package dao;

import core.Db;
import core.Helper;
import entity.Hotel;

import java.sql.*;
import java.util.ArrayList;

public class HotelDao {
    private final Connection connection;

    public HotelDao() {
        this.connection = Db.getInstance();
    }

    public ArrayList<Hotel> getList() {
        ArrayList<Hotel> hotelList = new ArrayList<>();
        String query = "SELECT * FROM hotel";

        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                hotelList.add(this.match(resultSet));
            }

            statement.close();
            resultSet.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return hotelList;
    }

    public Hotel match(ResultSet resultSet) throws SQLException {
        Hotel hotel = new Hotel();
        hotel.setHotelID(resultSet.getInt("hotel_id"));
        hotel.setName(resultSet.getString("name"));
        hotel.setCity(resultSet.getString("city"));
        hotel.setRegion(resultSet.getString("region"));
        hotel.setAddress(resultSet.getString("address"));
        hotel.setEmail(resultSet.getString("email"));
        hotel.setTelephone(resultSet.getString("telephone"));
        hotel.setStar(resultSet.getString("star"));
        return hotel;
    }

    public void match(PreparedStatement preparedStatement, Hotel hotel) throws SQLException {
        preparedStatement.setString(1, hotel.getName());
        preparedStatement.setString(2, hotel.getCity());
        preparedStatement.setString(3, hotel.getRegion());
        preparedStatement.setString(4, hotel.getAddress());
        preparedStatement.setString(5, hotel.getEmail());
        preparedStatement.setString(6, hotel.getTelephone());
        preparedStatement.setString(7, hotel.getStar());
    }

    public boolean add(Hotel hotel) {
        String query = "INSERT INTO hotel (name, city, region, address, email, telephone, star) VALUES (?,?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);

            this.match(preparedStatement, hotel);

            int response = preparedStatement.executeUpdate();

            preparedStatement.close();

            return response != -1;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public Hotel searchWithHotelId(int hotelID) {
        Hotel obj = null;
        String query = "SELECT * FROM hotel WHERE hotel_id = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, hotelID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                obj = this.match(resultSet);
            }

            preparedStatement.close();
            resultSet.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return obj;
    }

    public Hotel searchWithEmail(String email) {
        Hotel obj = null;
        String query = "SELECT * FROM hotel WHERE email = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                this.match(resultSet);
            }

            preparedStatement.close();
            resultSet.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return obj;
    }

    public boolean delete(int hotelID) {
        String query = "DELETE FROM hotel WHERE hotel_id = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, hotelID);

            return preparedStatement.executeUpdate() != -1;
            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean update(Hotel hotel) {
        String query = "UPDATE hotel SET name = ?, city = ?, region = ?, address = ?, email = ?, telephone = ?, star = ? WHERE hotel_id = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            this.match(preparedStatement, hotel);
            preparedStatement.setInt(8, hotel.getHotelID());
            int executeUpdate = preparedStatement.executeUpdate();
            preparedStatement.close();
            return executeUpdate != -1;

            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public ArrayList<Hotel> searchList(Hotel hotel) {
        ArrayList<Hotel> hotelList = new ArrayList<>();


        String[] arrCri = {
                "name", hotel.getName(),
                "city", hotel.getCity(),
                "region", hotel.getRegion(),
                "star", hotel.getStar()

        };


        String query = Helper.querySearch("hotel", arrCri, "AND");

        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {

                hotelList.add(match(resultSet));
            }
            statement.close();
            resultSet.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return hotelList;
    }


    public ArrayList<Hotel> searchList(String value) {
        ArrayList<Hotel> hotelList = new ArrayList<>();

        String[] arrCri = {"name", value, "city", value, "region", value};
        String query = Helper.querySearch("hotel", arrCri, "OR");

        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                hotelList.add(this.match(resultSet));
            }
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return hotelList;
    }


}
