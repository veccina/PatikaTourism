package business;

import core.Helper;
import dao.UserDao;
import entity.User;

import java.util.ArrayList;

public class UserManager {
    private final UserDao userDAO;

    public UserManager() {
        this.userDAO = new UserDao();
    }

    public ArrayList<User> getList() {
        return this.userDAO.getList();
    }

    public ArrayList<User> searchList(String name, String email, String role) {
        return this.userDAO.searchList(name, email, role);
    }

    public boolean add(User user) {
        if (this.userDAO.searchWithEmail(user.getEmail()) != null) {
            Helper.showMsg("error");
            return false;
        }
        return this.userDAO.add(user);
    }

    public boolean update(User user) {
        if (this.userDAO.searchWithEmail(user.getEmail()) != null && this.userDAO.searchWithEmail(user.getEmail()).getId() != user.getId()) {
            Helper.showMsg("Kayıtlı kullanıcı\nLütfen başka bir email adresi giriniz.");
            return false;
        }

        if (!user.getRole().equals("admin") && !user.getRole().equals("agent")) {
            Helper.showMsg("Lütfen kullanıcının rolünü belirtiniz. (admin/agent)");
            return false;
        }

        return this.userDAO.update(user);
    }

    public boolean delete(int id) {
        return this.userDAO.delete(id);
    }

    public ArrayList<User> getListByRole(String role) {
        return this.userDAO.getListByRole(role);
    }

    public User searchWithLoginInfo(String email, String pass) {
        return this.userDAO.searchWithLoginInfo(email, pass);
    }

    public User searchWithEmail(String email) {
        return searchWithEmail(email);
    }

    public User searchWithId(int id) {
        return this.userDAO.searchWithId(id);
    }
}
