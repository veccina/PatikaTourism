package dao;

import core.Db;
import core.Helper;
import entity.User;

import java.sql.*;
import java.util.ArrayList;

public class UserDao {
    private final Connection connection;

    public UserDao() {
        this.connection = Db.getInstance();
    }

    public ArrayList<User> getList() {
        ArrayList<User> userList = new ArrayList<>();
        String query = "SELECT * FROM user";
        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                userList.add(this.match(resultSet));
            }
            statement.close();
            resultSet.close();

            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userList;
    }

    public ArrayList<User> searchList(String name, String email, String role) {
        ArrayList<User> userList = new ArrayList<>();
        String[] searchCriteria = {"name", name, "email", email, "role", role};

        String query = Helper.querySearch("user", searchCriteria, "AND");

        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                userList.add(this.match(resultSet));
            }
            statement.close();
            resultSet.close();

            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userList;
    }

    public boolean add(User user) {
        String query = "INSERT INTO user (name, email, pass, role) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPass());
            preparedStatement.setString(4, user.getRole());

            int response = preparedStatement.executeUpdate();

            if (response == -1) {
                Helper.showMsg("error");
            }
            preparedStatement.close();

            return response != -1;
            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }
    public boolean update(User user) {
        String query = "UPDATE user SET name = ?, email = ?, pass = ?, role = ? WHERE id = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getEmail());
            preparedStatement.setString(3, user.getPass());
            preparedStatement.setString(4, user.getRole());
            preparedStatement.setInt(5, user.getId());
            int response = preparedStatement.executeUpdate();

            if (response == -1) {
                Helper.showMsg("error");
            }
            preparedStatement.close();
            return response != -1;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }
    public boolean delete(int id) {
        String query = "DELETE FROM user WHERE id = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            return preparedStatement.executeUpdate() != -1;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    public ArrayList<User> getListByRole(String role) {
        ArrayList<User> userList = new ArrayList<>();
        String query = "SELECT * FROM user WHERE role = '" + role + "'";

        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                userList.add(this.match(resultSet));
            }
            statement.close();
            resultSet.close();

            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userList;
    }
    public User searchWithLoginInfo(String email, String pass) {
        User user = null;
        String query = "SELECT * FROM user WHERE email = ? AND pass = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, pass);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = this.match(resultSet);
            }

            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }
    public User searchWithEmail(String email) {
        User user = null;
        String query = "SELECT * FROM user WHERE email = ?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = this.match(resultSet);
            }
            preparedStatement.close();
            resultSet.close();

            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    public User searchWithId(int id) {
        User user = null;
        String query = "SELECT * FROM user WHERE id = ?";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = this.match(resultSet);
            }
            preparedStatement.close();
            resultSet.close();

            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }


    public ArrayList<User> search(String name, String email, String role) {
        ArrayList<User> userList = new ArrayList<>();
        String[] searchCriteria = {"name", name, "email", email, "role", role};
        String query = Helper.querySearch("user", searchCriteria, "AND");
        User obj;

        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                userList.add(this.match(resultSet));
            }
            statement.close();
            resultSet.close();

            } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userList;
    }

    public User match(ResultSet rs) throws SQLException {
        User obj = new User();
        obj.setId(rs.getInt("id"));
        obj.setName(rs.getString("name"));
        obj.setEmail(rs.getString("email"));
        obj.setPass(rs.getString("pass"));
        obj.setRole(rs.getString("role"));
        return obj;
    }
}
