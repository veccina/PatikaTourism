package dao;

import core.Db;
import entity.Period;

import java.sql.*;
import java.util.ArrayList;

public class PeriodDao {
    private final Connection connection;

    public PeriodDao() {
        this.connection = Db.getInstance();
    }

    public ArrayList<Period> getList() {
        ArrayList<Period> periodList = new ArrayList<>();
        String query = "SELECT * FROM period";

        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                periodList.add(this.match(resultSet));
            }
            statement.close();
            resultSet.close();

            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return periodList;
    }

    public boolean add(Period period) {
        String query = "INSERT INTO period (hotel_id, winter_start, winter_end, summer_start, summer_end) VALUES (?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            this.match(preparedStatement, period);
            int response = preparedStatement.executeUpdate();
            preparedStatement.close();

            return response != -1;

            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean delete(int periodID) {
        String query = "DELETE FROM period WHERE period_id = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, periodID);

            return preparedStatement.executeUpdate() != -1;

            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean deleteWithHotelId(int hotelID) {
        String query = "DELETE FROM period WHERE hotel_id = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, hotelID);

            return preparedStatement.executeUpdate() != -1;

            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean update(Period period) {
        String query = "UPDATE period SET hotel_id = ?, winter_start = ?, winter_end = ?, summer_start = ?, summer_end = ? WHERE period_id = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);

            this.match(preparedStatement, period);

            preparedStatement.setInt(6, period.getPeriodID());

            int response = preparedStatement.executeUpdate();

            preparedStatement.close();

            return response != -1;

            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public Period SearchWithPeriodId(int periodID) {
        Period period = null;
        String query = "SELECT * FROM period WHERE period_id = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, periodID);
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                period = this.match(rs);
            }
            preparedStatement.close();
            rs.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return period;
    }
    public Period searchWithHotelId(int hotelID) {
        Period period = null;
        String query = "SELECT * FROM period WHERE hotel_id = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, hotelID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                period = this.match(resultSet);
            }
            preparedStatement.close();
            resultSet.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return period;
    }

    public Period match(ResultSet resultSet) throws SQLException {
        Period period = new Period();
        period.setPeriodID(resultSet.getInt("period_id"));
        period.setHotelID(resultSet.getInt("hotel_id"));
        period.setWinterStart(resultSet.getDate("winter_start"));
        period.setWinterEnd(resultSet.getDate("winter_end"));
        period.setSummerStart(resultSet.getDate("summer_start"));
        period.setSummerEnd(resultSet.getDate("summer_end"));
        return period;
    }

    public void match(PreparedStatement preparedStatement, Period period) throws SQLException {
        preparedStatement.setInt(1, period.getHotelID());
        preparedStatement.setDate(2, period.getWinterStart());
        preparedStatement.setDate(3, period.getWinterEnd());
        preparedStatement.setDate(4, period.getSummerStart());
        preparedStatement.setDate(5, period.getSummerEnd());
    }
}
