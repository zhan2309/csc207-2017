package ProjectPhase1;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The Class for Reshelver Gui.
 */
public class ReshelverBox extends JFrame{
    private JPanel ReshelverPanel;
    private JButton changeGoodsLocationButton;
    private JButton viewProductHistoryButton;
    private JButton VIewProductOrderHistoryButton;
    private JButton viewAllOrderHistoryButton;
    private JButton backButton;
    private JButton viewQuantityButton;
    private JLabel picture;

    /**
     * The constructor for Reshelver Gui.
     */
    public ReshelverBox(){
        super("Reshelver");
        setContentPane(ReshelverPanel);
        pack();
        setSize(700,400);
        ImageIcon icon = new ImageIcon("rs.png");
        picture.setIcon(icon);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Reshelver reshelver = new Reshelver();
        VIewProductOrderHistoryButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e Click to view Product Order History.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputValue = JOptionPane.showInputDialog("Please input a upc");
                if(inputValue == null){
                    return;
                }
                if(Helpers.isValidUPc(inputValue)){
                    if (Helpers.isUpcExist(inputValue)){
                        reshelver.viewProductOrderHistory(inputValue);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Invalid UPC or Good does not exist!",
                                "Invalid UPC", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    Helpers.showInvalidUpc();
                }
            }
        });
        viewProductHistoryButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e Click to view Product History.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputValue = JOptionPane.showInputDialog("Please input a upc");
                if(inputValue == null){
                    return;
                }
                if(Helpers.isValidUPc(inputValue)){
                    if (Helpers.isUpcExist(inputValue)){
                        String location = reshelver.viewProductLocation(inputValue);
                        JOptionPane.showMessageDialog(null,
                                "Current Location is:" + location,
                                "CurrentLocation", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Invalid UPC or Good does not exist!",
                                "Invalid UPC", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    Helpers.showInvalidUpc();
                }
            }
        });
        changeGoodsLocationButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e Click to view Good Location.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                    String upcNumber = JOptionPane.showInputDialog("Please input a upc");
                if(upcNumber == null){
                    return;
                }
                if(Helpers.isValidUPc(upcNumber)){
                    if (Helpers.isUpcExist(upcNumber)){
                        String upcLocation = JOptionPane.showInputDialog("Please input a location");
                        reshelver.changeLocation(upcNumber,upcLocation);
                        JOptionPane.showMessageDialog(null,
                                "Current Location has been changed to:" + upcLocation,
                                "CurrentLocation", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Invalid UPC or Good does not exist!",
                                "Invalid UPC", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    Helpers.showInvalidUpc();
                }
            }
        });
        backButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e Click to Back.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login = new Login();
                login.setVisible(true);
                dispose();
            }
        });
        viewAllOrderHistoryButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e Click to view Order History.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                reshelver.viewAllOrderHistory();
            }
        });
        viewQuantityButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e Click to view Quantity.
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                String inputValue = JOptionPane.showInputDialog("Please input a upc");
                if(inputValue == null){
                    return;
                }
                if(Helpers.isValidUPc(inputValue)){
                    if (Helpers.isUpcExist(inputValue)){
                        JOptionPane.showMessageDialog(null,
                                "Current Quantity:" + reshelver.viewQuantity(inputValue),
                                "CurrentQuantity", JOptionPane.PLAIN_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Invalid UPC or Good does not exist!",
                                "Invalid UPC", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    Helpers.showInvalidUpc();
                }
            }
        });
    }
}
