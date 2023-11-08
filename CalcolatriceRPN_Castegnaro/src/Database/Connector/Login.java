package Database.Connector;
import CalcolatriceRPN.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    private JTextField txtNickname;
    private JTextField txtPassword;
    private JButton RegisterButton;
    private JButton LoginButton;
    public JPanel LoginPanel;

    private DB db = new DB();

    public Login(){

        LoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nickname = txtNickname.getText();
                String password = txtPassword.getText();
                JFrame framecalc;
                if(db.login(nickname, password)== 1) {
                    framecalc = new JFrame("Calcotrice");
                    framecalc.setContentPane(new FormCalcolatrice().Calcolatrice);
                    framecalc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    framecalc.setSize(450, 450);
                    framecalc.setVisible(true);
                }
                else{
                    JOptionPane.showConfirmDialog(null, "Login fallito!");
                    txtNickname.setText("");
                    txtPassword.setText("");
                }

            }
        });


        RegisterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame registerForm = new JFrame("Registrazione");
                registerForm.setContentPane(new RegisterForm().Register);
                registerForm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                registerForm.pack();
                registerForm.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Login");
        frame.setContentPane(new Login().LoginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
