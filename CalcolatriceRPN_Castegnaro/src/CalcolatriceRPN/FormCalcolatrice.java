package CalcolatriceRPN;

import javax.swing.*;
import java.util.Stack;
import Database.Connector.DB;

public class FormCalcolatrice {
    private JButton btn7;
    private JButton btn4;
    private JButton btn1;
    private JButton btnExp;
    private JTextField textField;
    private JButton btn2;
    private JButton btn3;
    private JButton btn5;
    private JButton btn6;
    private JButton btn8;
    private JButton btn9;
    private JButton btnMol;
    private JButton btnSub;
    private JButton btnAdd;
    private JButton btnDiv;
    private JButton btnPoint;
    private JButton btnUguale;
    private JButton btnCanc;
    private JButton btnC;
    private JButton btnCE;
    private JButton btn0;
    public JPanel Calcolatrice;

    public FormCalcolatrice() {
        btn1.addActionListener(e -> textField.setText(textField.getText()+btn1.getText()));
        btn2.addActionListener(e -> textField.setText(textField.getText()+btn2.getText()));
        btn3.addActionListener(e -> textField.setText(textField.getText()+btn3.getText()));
        btn4.addActionListener(e -> textField.setText(textField.getText()+btn4.getText()));
        btn5.addActionListener(e -> textField.setText(textField.getText()+btn5.getText()));
        btn6.addActionListener(e -> textField.setText(textField.getText()+btn6.getText()));
        btn7.addActionListener(e -> textField.setText(textField.getText()+btn7.getText()));
        btn8.addActionListener(e -> textField.setText(textField.getText()+btn8.getText()));
        btn9.addActionListener(e -> textField.setText(textField.getText()+btn9.getText()));
        btn0.addActionListener(e -> textField.setText(textField.getText()+btn0.getText()));
        btnAdd.addActionListener(e -> textField.setText(textField.getText()+btnAdd.getText()));
        btnMol.addActionListener(e -> textField.setText(textField.getText()+btnMol.getText()));
        btnDiv.addActionListener(e -> textField.setText(textField.getText()+btnDiv.getText()));
        btnSub.addActionListener(e -> textField.setText(textField.getText()+btnSub.getText()));
        btnPoint.addActionListener(e -> textField.setText(textField.getText()+btnPoint.getText()));

        btnCanc.addActionListener(e -> {
            String bck = null;
            if(textField.getText().length()>0){
                StringBuilder str = new StringBuilder(textField.getText());
                str.deleteCharAt(textField.getText().length()-1);
                bck = str.toString();
                textField.setText(bck);
            }
        });
        btnC.addActionListener(e -> {
            String bck = null;
            if(textField.getText().length()>0){
                StringBuilder str = new StringBuilder(textField.getText());
                str.deleteCharAt(textField.getText().length()-1);
                textField.setText(bck);
            }
        });
        btnCE.addActionListener(e -> {
            String bck = null;
            if(textField.getText().length()>0){
                StringBuilder str = new StringBuilder(textField.getText());
                str.deleteCharAt(textField.getText().length()-1);
                textField.setText(bck);
            }
        });
        btnUguale.addActionListener(e -> {
            DB db = new DB();
            String input = textField.getText();
            String rpnExpression = RPN(input);
            float result = calcoloRPN(rpnExpression);
            textField.setText(String.valueOf(result));

            db.registraCronologia(textField.getText());

            int scelta = JOptionPane.showConfirmDialog(null, "Vuoi vedere la tua cronologia?", "SI", JOptionPane.YES_NO_OPTION);
            if(scelta == JOptionPane.YES_OPTION)
                JOptionPane.showMessageDialog(null, db.stampaInformazione());
        });
    }
    public static int Prec (char ch){
        switch (ch){
            case '+':
            case '-':
                return 1;

            case '*':
            case '/':
                return 2;

            case '^':
                return 3;
        }
        return -1;
    }
    public static String RPN(String exp) {
        String result = new String("");

        Stack<Character> stack = new Stack<>();

        for(int i =0; i < exp.length(); ++i) {
            char c = exp.charAt(i);

            if(Character.isDigit(c) || c == ' ')
                result += c;

            else if (c == '(')
                stack.push(c);

            else if (c ==')'){
                while (!stack.isEmpty() && stack.peek() != '('){
                    result += stack.pop();
                }
                stack.pop();
            }
            else {
                result += " ";
                while (!stack.isEmpty() && Prec(c) <= Prec(stack.peek())){
                    result += stack.peek();
                    stack.pop();
                }
                stack.push(c);
            }
        }
        while (!stack.isEmpty()){
            if(stack.peek() == '(')
                return "Invalid Expression";
            result += stack.peek();
            stack.pop();
        }

        return result;
    }

    public static float calcoloRPN(String espressione){
        Stack<Float> stack = new Stack<>();
        String temp = "";
        for(int i =0; i < espressione.length(); i++){
            switch (espressione.charAt(i)){
                case '1', '2', '3', '4', '5', '6', '7', '8', '9', '0':
                    temp += espressione.charAt(i);
                    break;
                case ' ':
                    if (!temp.isEmpty()) {
                        stack.push(Float.parseFloat(temp));
                        temp = "";
                    }
                    break;
                case '+':
                    if(!temp.isEmpty()){
                        stack.push(Float.parseFloat(temp));
                        temp = "";
                    }
                    stack.push(stack.pop() + stack.pop());
                    break;
                case '-':
                    if(!temp.isEmpty()){
                        stack.push(Float.parseFloat(temp));
                        temp = "";
                    }
                    stack.push(-stack.pop() + stack.pop());
                    break;
                case '/':
                    if(!temp.isEmpty()){
                        stack.push(Float.parseFloat(temp));
                        temp = "";
                    }
                    stack.push(1 / stack.pop() + stack.pop());
                    break;
                case '*':
                    if(!temp.isEmpty()){
                        stack.push(Float.parseFloat(temp));
                        temp = "";
                    }
                    stack.push(stack.pop() * stack.pop());
                    break;
            }
        }
        return stack.pop();

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("FormCalcolatrice");
        frame.setContentPane(new FormCalcolatrice().Calcolatrice);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
