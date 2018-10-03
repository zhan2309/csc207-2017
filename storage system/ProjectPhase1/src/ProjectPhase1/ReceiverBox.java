package ProjectPhase1;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The Class for Receiver Gui.
 */
public class ReceiverBox extends JFrame{

    private JPanel ReceiverPanel;
    private JButton scanin;
    private JButton viewCost;
    private JButton viewThreshold;
    private JButton viewPriceHistoryButton;
    private JButton refill;
    private JButton viewPriceButton;
    private JButton viewLocationButton;
    private JFormattedTextField UPC;
    private JButton back;
    private JButton viewOrdersButton;
    private JLabel picture;

    /**
     * The Constructor for Receiver Gui.
     */
    public ReceiverBox(){
        super("Receiver");
        setContentPane(ReceiverPanel);
        setSize(600,500);
        ImageIcon icon = new ImageIcon("rcv.png");
        picture.setIcon(icon);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Receiver receiver = new Receiver();
        viewPriceButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e Click to view Price.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String upc = UPC.getText();
                if(Helpers.isValidUPc(upc) &&
                        Helpers.isUpcExist(upc)){
                    JOptionPane.showMessageDialog(null,
                            "Current Price:" + receiver.getPrice(upc),
                            "CurrentCost", JOptionPane.PLAIN_MESSAGE);
                } else {
                    invalidUpc();
                }
            }
        });
        viewThreshold.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e Click to view Threshold.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String upc = UPC.getText();
                if(Helpers.isValidUPc(upc) &&
                        Helpers.isUpcExist(upc)){
                    JOptionPane.showMessageDialog(null,
                            "Current Threshold:" + receiver.getThreshold(upc),
                            "CurrentThreshold", JOptionPane.PLAIN_MESSAGE);
                } else {
                    invalidUpc();
                }
            }
        });
        viewLocationButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e Click to view Location.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String upc = UPC.getText();
                if(Helpers.isValidUPc(upc) &&
                        Helpers.isUpcExist(upc)){
                    JOptionPane.showMessageDialog(null,
                              "Current Location: " + receiver.viewLocation(upc),
                            "CurrentLocation", JOptionPane.PLAIN_MESSAGE);
                } else {
                    invalidUpc();
                }
            }
        });
        viewCost.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e Click to view Cost.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String upc = UPC.getText();
                if(Helpers.isValidUPc(upc) &&
                        Helpers.isUpcExist(upc)){
                    JOptionPane.showMessageDialog(null,
                            "Current Cost:" + receiver.getCost(upc),
                            "CurrentCost", JOptionPane.PLAIN_MESSAGE);
                } else {
                    invalidUpc();
                }
            }
        });
        viewPriceHistoryButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e Click to view Price History.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                receiver.viewPriceHistory();
            }
        });
        back.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e Click to go back.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login = new Login();
                login.setVisible(true);
                dispose();
            }
        });
        scanin.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e Click to scan in.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                ScanInBox scanInBox = new ScanInBox();
                scanInBox.setVisible(true);
                dispose();
            }
        });
        refill.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e Click to refill goods.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputValue = JOptionPane.showInputDialog(
                        "Could you input an OrderNumber that you wan to refill");
                try {
                    if (inputValue == null){
                        return;
                    }
                    boolean orderExist = DataBaseViewer.loopColumnForString(
                            "orderNumber",inputValue,"Orders");
                    if(orderExist){
                        receiver.dealWithOrder(Integer.parseInt(inputValue));
                    } else {
                        Helpers.showInVaildMessage();
                    }
                } catch (Exception e1){
                    Helpers.showInVaildMessage();
                }
            }
        });
        viewOrdersButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e Click to view Orders.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                DataBaseShowTable dataBaseShowTable = new DataBaseShowTable("Orders");
            }
        });
    }

    /**
     * Helper method to show the warning invalidUPC dialog.
     */
    private void invalidUpc(){
        JOptionPane.showMessageDialog(null,"Invalid UPC or Good does not exist!",
                "Invalid UPC", JOptionPane.WARNING_MESSAGE);
    }
}
