package Database.Connector;

import CalcolatriceRPN.FormCalcolatrice;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class RegisterForm {
    private JTextField txtNome;
    private JTextField txtCognome;
    private JTextField txtNickname;
    private JTextField txtPassword;
    private JTextField txtEta;
    private JButton registerButton;
    public JPanel Register;
    private JPasswordField pswPassword;
    private DB db = new DB();

    public RegisterForm() {
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frameCalc;
                String nome = txtNome.getText();
                String cognome = txtCognome.getText();
                String nickname = txtNickname.getText();
                String password = new String(pswPassword.getPassword());
                int eta = Integer.parseInt(txtEta.getText());

                try {
                    if(db.register(nome, cognome, nickname, password, eta) == 1){
                        frameCalc = new JFrame("Calcotrice");
                        frameCalc.setContentPane(new FormCalcolatrice().Calcolatrice);
                        frameCalc.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frameCalc.setSize(450, 450);
                        frameCalc.setVisible(true);
                    }
                    else
                        JOptionPane.showMessageDialog(null,"LoginPanel fallito");
                }catch (SQLException ex){
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("RegisterForm");
        frame.setContentPane(new RegisterForm().Register);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
