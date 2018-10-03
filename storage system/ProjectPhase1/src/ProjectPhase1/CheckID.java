package ProjectPhase1;

import sun.rmi.runtime.Log;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * The Class of CheckID shows the CheckID window to check UserIdentity.
 */
public class CheckID extends JFrame {

    private JPanel checkIdPanel;
    private JFormattedTextField userName;
    private JFormattedTextField AccessCode;
    private JButton Enter;
    private JButton backButton;
    // Connect to DataBase.
    private static Connection connection = SqliteConnection.dbConnector();

    /**
     * The Constructor for CheckID.
     * @param major String major User chose.
     */
    public CheckID(String major) {
        super("CheckID" + " for " + major);
        setContentPane(checkIdPanel);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Enter.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             * this will automatically distinguish the user type i.e. cashier, manager,
             * receiver, administrator.
             * @param e Click to login
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                String name = userName.getText();
                String accessCode = AccessCode.getText();
                if (major.equals("cashier")) {

                    if (isAllCorrect(name,accessCode,major)) {
                        CashierBox cashierBox = new CashierBox();
                        cashierBox.setVisible(true);
                        dispose();
                    } else {
                        showWarning();
                    }
                } else if (major.equals("manager")){
                    if (isAllCorrect(name,accessCode,major)) {
                        ManagerBox managerBox = new ManagerBox();
                        managerBox.setVisible(true);
                        dispose();
                    } else {
                        showWarning();
                    }
                } else if (major.equals("receiver")){
                    if (isAllCorrect(name,accessCode,major)) {
                        ReceiverBox receiverBox = new ReceiverBox();
                        receiverBox.setVisible(true);
                        dispose();
                    } else {
                        showWarning();
                    }
                } else if (major.equals("reshelver")){
                    if (isAllCorrect(name,accessCode,major)) {
                        ReshelverBox reshelverBox = new ReshelverBox();
                        reshelverBox.setVisible(true);
                        dispose();
                    } else {
                        showWarning();
                    }
                } else if (major.equals("administrator")){
                    if(name.equals("admin") && accessCode.equals("123")){
                        AdministratorBox administratorBox = new AdministratorBox();
                        administratorBox.setVisible(true);
                        dispose();
                    } else {
                        showWarning();
                    }
                }
                }catch (Exception e1){
                    Helpers.showInVaildMessage();
                }
            }
        });
        backButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             * Back to last menu.
             * @param e Click the back button
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login = new Login();
                login.setVisible(true);
                dispose();
            }
        });
    }

    /**
     * Helper function for CheckID to check if all information such that userName, password, major are correct.
     * @param userName String userName
     * @param password String password
     * @param major String major
     * @return boolean
     */
    private boolean isAllCorrect(String userName, String password, String major) {
        String query = "SELECT major, userName, accessCode"
                + " FROM EmployList";
        boolean found = false;
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            // loop through the result set
            while (rs.next()) {
                String correctMajor = rs.getString("major");
                String correctUserName = rs.getString("userName");
                String correctAccessCode = rs.getString("accessCode");
                if (correctAccessCode.equals(password) &&
                        correctUserName.equals(userName) && correctMajor.equals(major)) {
                    found = true;
                }
            }
            return found;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return found;
        }

    }

    /**
     * Helper method to show Warning message.
     */
    private void showWarning(){
        JOptionPane.showMessageDialog(null,
                "Invalid UserName or AccessCode", "Warning", JOptionPane.WARNING_MESSAGE);
    }

    }


