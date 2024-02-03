package dao;

import core.Db;
import core.Helper;
import entity.Facility;

import java.sql.*;
import java.util.ArrayList;

public class FacilityDao {
    private final Connection connection;

    public FacilityDao() {
        this.connection = Db.getInstance();
    }

    public ArrayList<Facility> getList() {
        ArrayList<Facility> facilities = new ArrayList<>();
        String query = "SELECT * FROM facility";

        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                facilities.add(this.match(resultSet));
            }
            statement.close();
            resultSet.close();

            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return facilities;
    }

    public Facility match(ResultSet rs) throws SQLException {
        Facility obj = new Facility();
        obj.setFacilityID(rs.getInt("facility_id"));
        obj.setHotelID(rs.getInt("hotel_id"));
        obj.setType(rs.getString("type"));
        return obj;
    }

    public void match(PreparedStatement ps, Facility facility) throws SQLException {
        ps.setInt(1, facility.getHotelID());
        ps.setString(2, facility.getType());
    }
    public ArrayList<Facility> getListHotelId(int hotelID) {
        ArrayList<Facility> facList = new ArrayList<>();
        String query = "SELECT * FROM facility WHERE hotel_id = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, hotelID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                facList.add(this.match(resultSet));
            }
            preparedStatement.close();
            resultSet.close();

            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return facList;
    }

    public boolean add(Facility facility) {
        String query = "INSERT INTO facility (hotel_id, type) VALUES (?,?)";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            this.match(preparedStatement, facility);

            int update = preparedStatement.executeUpdate();
            preparedStatement.close();

            return update != -1;

            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
    public boolean delete(int facilityID) {
        String query = "DELETE FROM facility WHERE facility_id = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, facilityID);

            return preparedStatement.executeUpdate() != -1;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

    public boolean update(Facility facility) {
        String query = "UPDATE facility SET hotel_id = ?, type = ? WHERE facility_id = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);

            this.match(preparedStatement, facility);

            preparedStatement.setInt(3, facility.getFacilityID());

            int respond = preparedStatement.executeUpdate();

            preparedStatement.close();

            return respond != -1;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public Facility searchWithFacilityId(int facilityID) {
        Facility facility = null;
        String query = "SELECT * FROM facility WHERE facility_id = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, facilityID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                facility = this.match(resultSet);
            }

            preparedStatement.close();
            resultSet.close();

            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return facility;
    }

    public Facility searchWithHotelId(int hotelID) {
        Facility facility = null;
        String query = "SELECT * FROM facility WHERE hotel_id = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, hotelID);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                facility = this.match(resultSet);
            }

            preparedStatement.close();
            resultSet.close();

            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return facility;
    }

    public Facility searchWithHotelTypeId(int hotelID, String type) {
        Facility facility = null;
        String query = "SELECT * FROM facility WHERE hotel_id = ? AND type = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, hotelID);
            preparedStatement.setString(2, type);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                facility = this.match(resultSet);
            }
            preparedStatement.close();
            resultSet.close();

            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return facility;
    }
    public ArrayList<Facility> searchList(Facility facility) {
        ArrayList<Facility> facilityArrayList = new ArrayList<>();

        String[] arrSearchReq = {
                "hotel_id", String.valueOf(facility.getHotelID()),
                "type", facility.getType()
        };
        // Updated to match the new signature of querySearch
        String query = Helper.querySearch("facility", arrSearchReq, "AND");

        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                facilityArrayList.add(this.match(resultSet));
            }
            statement.close();
            resultSet.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return facilityArrayList;
    }


}
