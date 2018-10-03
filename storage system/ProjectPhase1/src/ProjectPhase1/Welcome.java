package ProjectPhase1;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The Class of Welcome shows the main user window.
 */
public class Welcome extends JFrame{

    private JPanel WelcomePanel;
    private JButton startButton;
    private JLabel picture;


    /**
     * THe Constructor for Welcome which is the main class in this project.
     */
    public Welcome() {
        super("Welcome");
        setContentPane(WelcomePanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon("op.png");
        picture.setIcon(icon);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"Welcome to Inventory System");
                Login loginBox = new Login();
                loginBox.setVisible(true);
                dispose();
            }
        });
    }

    /**
     * The main method in Welcome Class.
     * @param args String[]
     */
    public static void main(String[] args) {
        Welcome main = new Welcome();
        main.pack();
        main.setVisible(true);

    }
}
