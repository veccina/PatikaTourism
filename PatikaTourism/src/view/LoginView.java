package view;

import business.UserManager;
import core.Helper;
import entity.User;

import javax.swing.*;

public class LoginView extends Layout {
    private JPanel container;
    private JPanel pnl_user_login;
    private JLabel lbl_user_login;
    private JLabel lbl_user_email;
    private JTextField tf_user_email;
    private JLabel lbl_user_pass;
    private JTextField tf_user_pass;
    private JButton btn_user_login;
    private JLabel amblem;
    private final UserManager userManager;


    // Değerlendirme sorusu 9: Login işleminde kullanıcının kaydı olup olmadığı kontrol ediliyor mu, kaydı yoksa ya da hatalı giriş yapıldıysa kullanıcıya hatalı giriş mesajı veriliyor.
    public LoginView() {
        this.userManager = new UserManager();

        this.add(container);
        this.initView(750, 750, "User Login");

        btn_user_login.addActionListener(e -> {
            JTextField[] checkFieldList = {this.tf_user_email, this.tf_user_pass};
            if (Helper.isFieldListEmpty(checkFieldList)) {
                Helper.showMsg("fill");
                return;
            }

            User user = this.userManager.searchWithLoginInfo(tf_user_email.getText(), tf_user_pass.getText());

            if (user == null) {
                Helper.showMsg("notFound");
                return;
            }

            Helper.showMsg("success");

            login(user);

        });
        }

    public void login(User user) {
        switch (user.getRole()) {
            case "admin" -> new AdminView(user);
            case "agent" -> new AgentView(user);
        }
        dispose();
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        amblem = new JLabel(new ImageIcon("src/view/patika.png"));
    }
}
