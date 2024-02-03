package dao;

import core.Db;
import entity.Price;

import java.sql.*;
import java.util.ArrayList;

public class PriceDao {

    private final Connection connection;

    public PriceDao() {
        this.connection = Db.getInstance();
    }

    public ArrayList<Price> getList() {
        ArrayList<Price> priceList = new ArrayList<>();
        String query = "SELECT * FROM price";

        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                priceList.add(this.match(resultSet));
            }
            statement.close();
            resultSet.close();

            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return priceList;
    }

    public ArrayList<Price> getListRoomId(int roomID) {
        ArrayList<Price> priceList = new ArrayList<>();
        String query = "SELECT * FROM price WHERE room_id = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, roomID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                priceList.add(this.match(resultSet));
            }
            preparedStatement.close();
            resultSet.close();

            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return priceList;
    }

    public ArrayList<Price> getListStayId(int lodgingID) {
        ArrayList<Price> priceList = new ArrayList<>();
        String query = "SELECT * FROM price WHERE lodging_id = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, lodgingID);
            preparedStatement.execute();

            preparedStatement.close();

            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return priceList;
    }

    public boolean add(Price price) {
        String query = "INSERT INTO price (lodging_id, room_id, winter_adult_price, winter_child_price, summer_adult_price, summer_child_price) VALUES (?,?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            this.match(preparedStatement, price);
            int response = preparedStatement.executeUpdate();
            preparedStatement.close();

            return response != -1;

            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(int priceID) {
        String query = "DELETE FROM price WHERE price_id = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, priceID);

            return preparedStatement.executeUpdate() != -1;

            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean deleteRoomID(int roomID) {
        String query = "DELETE FROM price WHERE room_id = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, roomID);

            return preparedStatement.executeUpdate() != -1;
            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean deleteStayId(int lodgingID) {
        String query = "DELETE FROM price WHERE lodging_id = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, lodgingID);

            return preparedStatement.executeUpdate() != -1;

            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean update(Price price) {
        String query = "UPDATE price SET lodging_id = ?, room_id = ?, winter_adult_price = ?, winter_child_price = ?, summer_adult_price = ?, summer_child_price = ? WHERE price_id = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            this.match(preparedStatement, price);
            preparedStatement.setInt(7, price.getPriceID());
            int response = preparedStatement.executeUpdate();
            preparedStatement.close();

            return response != -1;

            } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Price searchPriceId(int priceID) {
        Price price = null;
        String query = "SELECT * FROM price WHERE price_id = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, priceID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                price = this.match(resultSet);
            }
            preparedStatement.close();
            resultSet.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return price;
    }

    public Price searchStayIdRoomId(int lodgingID, int roomID) {
        Price price = null;
        String query = "SELECT * FROM price WHERE lodging_id = ? AND room_id = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, lodgingID);
            preparedStatement.setInt(2, roomID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                price = this.match(resultSet);
            }
            preparedStatement.close();
            resultSet.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return price;
    }

    private Price match(ResultSet resultSet) throws SQLException {
        Price price = new Price();
        price.setPriceID(resultSet.getInt("price_id"));
        price.setLodgingID(resultSet.getInt("lodging_id"));
        price.setRoomID(resultSet.getInt("room_id"));
        price.setWinterAdultPrice(resultSet.getDouble("winter_adult_price"));
        price.setWinterChildPrice(resultSet.getDouble("winter_child_price"));
        price.setSummerAdultPrice(resultSet.getDouble("summer_adult_price"));
        price.setSummerChildPrice(resultSet.getDouble("summer_child_price"));
        return price;
    }

    private void match(PreparedStatement preparedStatement, Price price) throws SQLException {
        preparedStatement.setInt(1, price.getLodgingID());
        preparedStatement.setInt(2, price.getRoomID());
        preparedStatement.setDouble(3, price.getWinterAdultPrice());
        preparedStatement.setDouble(4, price.getWinterChildPrice());
        preparedStatement.setDouble(5, price.getSummerAdultPrice());
        preparedStatement.setDouble(6, price.getSummerChildPrice());
    }
}
