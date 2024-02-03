package dao;

import core.Db;
import core.Helper;
import entity.Stay;

import java.sql.*;
import java.util.ArrayList;

public class StayDao {
    private final Connection connection;

    public StayDao() {
        this.connection = Db.getInstance();
    }

    public ArrayList<Stay> getList() {
        ArrayList<Stay> stayList = new ArrayList<>();
        String query = "SELECT * FROM lodging";

        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                stayList.add(this.match(resultSet));
            }
            statement.close();
            resultSet.close();

            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return stayList;
    }


    public boolean add(Stay Stay) {
        String query = "INSERT INTO lodging (hotel_id, type) VALUES (?,?)";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            this.match(preparedStatement, Stay);
            int response = preparedStatement.executeUpdate();
            preparedStatement.close();

            return response != -1;

            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean delete(int lodgingID) {
        String query = "DELETE FROM lodging WHERE lodging_id = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, lodgingID);

            return preparedStatement.executeUpdate() != -1;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean update(Stay Stay) {
        String query = "UPDATE lodging SET hotel_id = ?, type = ?, number_available = ? WHERE lodging_id = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            this.match(preparedStatement, Stay);
            preparedStatement.setInt(3, Stay.getLodgingID());
            int update = preparedStatement.executeUpdate();
            preparedStatement.close();

            return update != -1;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public ArrayList<Stay> getListHotelId(int hotelID) {
        ArrayList<Stay> stayList = new ArrayList<>();
        String query = "SELECT * FROM lodging WHERE hotel_id = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, hotelID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                stayList.add(this.match(resultSet));
            }
            preparedStatement.close();
            resultSet.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return stayList;
    }
    public Stay searchWithStayId(int lodgingID) {
        Stay Stay = null;
        String query = "SELECT * FROM lodging WHERE lodging_id = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, lodgingID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Stay = this.match(resultSet);
            }
            preparedStatement.close();
            resultSet.close();

            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Stay;
    }

    public Stay searchWithHotelId(int hotelID) {
        Stay Stay = null;
        String query = "SELECT * FROM lodging WHERE hotel_id = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, hotelID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Stay = this.match(resultSet);
            }

            preparedStatement.close();
            resultSet.close();

            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Stay;
    }
    public Stay searchWithHotelIdTypeId(int hotelID, String type) {
        Stay Stay = null;
        String query = "SELECT * FROM lodging WHERE hotel_id = ? AND type = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, hotelID);
            preparedStatement.setString(2, type);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Stay = this.match(resultSet);
            }
            preparedStatement.close();
            resultSet.close();

            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Stay;
    }

    public ArrayList<Stay> searchList(Stay Stay) {
        ArrayList<Stay> stayList = new ArrayList<>();
        String[] searchCriteria = {
            "hotel_id", String.valueOf(Stay.getHotelID()),
            "type", Stay.getType()
        };
        String query = Helper.querySearch("lodging", searchCriteria, "AND");

        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                stayList.add(this.match(resultSet));
            }
            statement.close();
            resultSet.close();

            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return stayList;
    }
    public Stay match(ResultSet resultSet) throws SQLException {
        Stay stay = new Stay();
        stay.setLodgingID(resultSet.getInt("lodging_id"));
        stay.setHotelID(resultSet.getInt("hotel_id"));
        stay.setType(resultSet.getString("type"));
        return stay;
    }
    public void match(PreparedStatement preparedStatement, Stay Stay) throws SQLException {
        preparedStatement.setInt(1, Stay.getHotelID());
        preparedStatement.setString(2, Stay.getType());
    }
}
