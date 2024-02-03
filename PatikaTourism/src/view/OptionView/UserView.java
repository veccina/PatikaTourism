package view.OptionView;

import business.UserManager;
import core.Helper;
import entity.User;
import view.Layout;

import javax.swing.*;
import java.util.ArrayList;

public class UserView extends Layout {
    private JPanel container;
    private JLabel lbl_user_action;
    private JLabel lbl_user_name;
    private JTextField tf_user_name;
    private JLabel lbl_user_email;
    private JTextField tf_user_email;
    private JLabel lbl_user_pass;
    private JTextField tf_user_pass;
    private JLabel lbl_user_role;
    private JComboBox cmb_user_role;
    private JButton btn_user_action;
    private JPanel pnl_user_popup;

    private User user;
    private UserManager userManager;

    private static ArrayList<User> filteredUsers;

    public UserView(User user, String actionType) {
        this.add(container);
        this.user = user;
        this.userManager = new UserManager();
        this.initView(300, 400, actionType);

        switch (actionType) {
            case "update" -> {
                displayUserData();
                initUpdateButton();
            }
            case "add" -> initAddButton();
            case "search" -> initSearchButton();
        }
    }

    private void displayUserData() {
        tf_user_name.setText(user.getName());
        tf_user_email.setText(user.getEmail());
        tf_user_pass.setText(user.getPass());

        if (user.getRole().equals("admin")) {
            cmb_user_role.addItem("Admin");
            cmb_user_role.addItem("Agent");
        } else {
            cmb_user_role.addItem("Agent");
            cmb_user_role.addItem("Admin");
        }
    }

    private void initUpdateButton() {
        lbl_user_action.setText("Update User");
        btn_user_action.setText("Update");
        btn_user_action.addActionListener(e -> {
            JTextField[] checkTextField = {tf_user_name, tf_user_email, tf_user_pass};

            if (Helper.isFieldListEmpty(checkTextField)){
                Helper.showMsg("fill");
                return;
            }

            if (this.userManager.update(this.getUserFromTextFields(user.getId()))) {
                Helper.showMsg("success");
            }

            dispose();

        });
    }

    private void initAddButton() {
        lbl_user_action.setText("Kullanıcı Ekle");
        btn_user_action.setText("Ekle");
        fillComboBox();
        btn_user_action.addActionListener(e -> {
            JTextField[] checkTextField = {tf_user_name, tf_user_email, tf_user_pass};

            if (Helper.isFieldListEmpty(checkTextField)){
                Helper.showMsg("fill");
                return;
            }

            if (this.userManager.add(this.getUserFromTextFields(0))) {
                Helper.showMsg("success");
            }

            dispose();
        });
    }

    private void initSearchButton() {
        lbl_user_action.setText("Kullanıcı Ara");
        btn_user_action.setText("Ara");

        this.container.remove(lbl_user_pass);
        this.container.remove(tf_user_pass);

        fillComboBox();
        btn_user_action.addActionListener(e -> {
            User user = getUserFromTextFields(this.user.getId());

            if (user.getName().isEmpty() && user.getEmail().isEmpty() && user.getRole().isEmpty()) {
                dispose();
                return;
            }

            filteredUsers = this.userManager.searchList(tf_user_name.getText(), tf_user_email.getText(), tf_user_pass.getText());

            dispose();
        });
    }

    private void fillComboBox() {
        cmb_user_role.addItem("Agent");
        cmb_user_role.addItem("Admin");
    }

    private User getUserFromTextFields(int id) {
        String name = tf_user_name.getText();
        String email = tf_user_email.getText();
        String pass = tf_user_pass.getText();
        String role = cmb_user_role.getSelectedItem().toString().toLowerCase();
        return new User(id, name, email, pass, role);
    }

    public static ArrayList<User> getFilteredUsers() {
        return filteredUsers;
    }
}
