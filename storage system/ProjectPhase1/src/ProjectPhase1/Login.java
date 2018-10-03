package ProjectPhase1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

/**
 * The Class of Login shows the Login window.
 */
public class Login extends JFrame{

    private JPanel LoginPanel;
    private JButton cashierButton;
    private JButton managerButton;
    private JButton administratorButton;
    private JButton receiverButton;
    private JButton reshelverButton;
    private JButton helpButton;
    private JLabel picture;
    private JButton button1;

    /**
     * Helper method to pass a String to the CheckID box.
     * @param major String major choose
     */
    private void showCheckBox(String major){
        CheckID checkID = new CheckID(major);
        checkID.setVisible(true);
        dispose();
    }

    /**
     * The Constructor for Login Class.
     */
    public Login() {
        super("User Select Interface");
        setContentPane(LoginPanel);
        setSize(800,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon("cm.jpg");
        picture.setIcon(icon);
        reshelverButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e clickReshelverButton
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                showCheckBox("reshelver");
            }
        });
        administratorButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e clickAdministratorButton
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                showCheckBox("administrator");
            }
        });
        receiverButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e clickReceiverButton
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                showCheckBox("receiver");
            }
        });
        managerButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e clickManagerButton
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                showCheckBox("manager");
            }
        });
        cashierButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e clickCashierButton
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                showCheckBox("cashier");
            }
        });
        helpButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e clickHelpButton
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                Desktop desktop = Desktop.getDesktop();
                File file = new File("help.txt");
                try {
                    desktop.open(file);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }
}
