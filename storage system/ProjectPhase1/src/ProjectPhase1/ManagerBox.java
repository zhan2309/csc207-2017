package ProjectPhase1;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The Class for Manager Gui.
 */
public class ManagerBox extends JFrame{
    private JPanel ManagerPanel;
    private JButton viewDailyTotalProfitSingleGoodButton;
    private JButton viewSaleVolumOnAButton;
    private JButton viewSaleVolumOnAButton1;
    private JButton viewDailyTotalRevenueSingleGoodButton;
    private JButton viewOrdersButton;
    private JButton backToMenuButton;
    private JButton setSalePriceButton;
    private JButton VIEWSALEHISTORYTABLEButton;
    private JFormattedTextField Date;
    private JLabel picture;

    /**
     * The Constructor for the Manager Gui.
     */
    public ManagerBox(){
        super("Manager");
        setContentPane(ManagerPanel);
        pack();
        ImageIcon icon = new ImageIcon("man.png");
        picture.setIcon(icon);
        setSize(700,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Date.setValue("Date:" + Helpers.getSystemTime());
        Manager manager = new Manager();
        viewDailyTotalProfitSingleGoodButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e Click to view the Daily Total Profit by inputing one upc and date.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String upcNumber = JOptionPane.showInputDialog("Please input a upc");
                if (upcNumber == null){
                    return;
                }
                else if(Helpers.isValidUPc(upcNumber)){
                    if (Helpers.isUpcExist(upcNumber)){
                        String date = JOptionPane.showInputDialog("Please input a date(yyyy-mm-dd)");
                        if (date == null){
                            return;
                        }
                        else if (Helpers.isValidDate(date)){
                            double profit = manager.viewDailyTotalProfit(upcNumber,date);
                            if (profit == 0){
                                Helpers.showDateDoesNotExistDialog();
                            }
                            JOptionPane.showMessageDialog(null,
                                    "Total Profit on Date: " +
                                            date + "for this good" +
                                            " is " + profit ,
                                    "CurrentDailyToTalProfit", JOptionPane.PLAIN_MESSAGE);
                        }
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
        viewOrdersButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e Click to show the Orders Table.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.viewOrders();
            }
        });
        viewSaleVolumOnAButton1.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e Click to show the SaleVolume.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                String date = JOptionPane.showInputDialog("Please input a date(yyyy-mm-dd)");
                if (date == null){
                        return;
                    }
                else if (Helpers.isValidDate(date)){
                    double salesVolume = manager.VDTotalSalesVolume(date);
                    if (salesVolume == 0){
                        Helpers.showDateDoesNotExistDialog();
                    }
                    JOptionPane.showMessageDialog(null,
                            "Total SaleVolume on Date: " +
                                    date + "for all goods" +
                                    " is " + salesVolume ,
                            "CurrentDailyToTalSaleVolume", JOptionPane.PLAIN_MESSAGE);
                }
                }catch (Exception e1){
                    Helpers.showInvalidDateDialog();
                }
            }
        });
        setSalePriceButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e Click to set the Sale Price.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputValue = JOptionPane.showInputDialog("Please input a upc");
                if(inputValue == null){
                    return;
                }
                if(Helpers.isValidUPc(inputValue)){
                    if (Helpers.isUpcExist(inputValue)) {
                        try {
                            double priceDiscount = Double.parseDouble(JOptionPane.showInputDialog(
                                    "Please input a discount(i.e 0.3 means 30% off)"));
                            if (priceDiscount == 0) {
                                return;
                            }
                            try {
                                if( 0 < priceDiscount && priceDiscount < 1){
                                manager.setSalePrice(inputValue, priceDiscount);
                                    JOptionPane.showMessageDialog(null,
                                            "Current Price has been deducted by :" +
                                                    priceDiscount,
                                            "Current Discount", JOptionPane.PLAIN_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(null,
                                            "Invalid Input Discount please input number i.e. 0.3, 0.5!",
                                            "Invalid Discount", JOptionPane.WARNING_MESSAGE);
                                }
                            } catch (Exception e1) {
                                JOptionPane.showMessageDialog(null,
                                        "Invalid Input Price!",
                                        "Invalid Price", JOptionPane.WARNING_MESSAGE);
                            }
                        } catch (Exception e1) {
                            Helpers.showInVaildMessage();
                        }
                    }
                    else{
                            JOptionPane.showMessageDialog(null,
                                    "Invalid UPC or Good does not exist!",
                                    "Invalid UPC", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    Helpers.showInvalidUpc();
                }
            }
        });
        viewDailyTotalRevenueSingleGoodButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e Click to view the Daily Total Revenue.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String upcNumber = JOptionPane.showInputDialog("Please input a upc");
                if (upcNumber == null){
                    return;
                }
                else if(Helpers.isValidUPc(upcNumber)){
                    if (Helpers.isUpcExist(upcNumber)){
                        String date = JOptionPane.showInputDialog("Please input a date(yyyy-mm-dd)");
                        if(date == null){
                            return;
                        }
                        if (Helpers.isValidDate(date)){
                            double revenue = manager.viewDailyTotalRevenue(upcNumber,date);
                            if (revenue == 0){
                                Helpers.showDateDoesNotExistDialog();
                            }
                            JOptionPane.showMessageDialog(null,
                                    "Total Revenue on Date: " +
                                            date + "for this good" +
                                            " is " + revenue ,
                                    "CurrentDailyToTalRevenue", JOptionPane.PLAIN_MESSAGE);
                        }
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
        VIEWSALEHISTORYTABLEButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e Click to View Sale History.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                manager.viewSaleHistory();
            }
        });
        backToMenuButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e Click to go Back.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login = new Login();
                login.setVisible(true);
                dispose();
            }
        });
        viewSaleVolumOnAButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e click to view One Single good Daily Total SaleVolume.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String upcNumber = JOptionPane.showInputDialog("Please input a upc");
                if (upcNumber == null){
                    return;
                }
                else if(Helpers.isValidUPc(upcNumber)){
                    if (Helpers.isUpcExist(upcNumber)){
                        String date = JOptionPane.showInputDialog("Please input a date(yyyy-mm-dd)");
                        if (date == null){
                            return;
                        }
                        else if (Helpers.isValidDate(date)){
                            int salesVolume = manager.VDSingleGoodSalesVolume(upcNumber,date);
                            if (salesVolume == 0){
                                Helpers.showDateDoesNotExistDialog();
                            }
                            JOptionPane.showMessageDialog(null,
                                    "Total SaleVolume on Date: " +
                                            date + "for this good" +
                                            " is " + salesVolume ,
                                    "CurrentDailyToTalSaleVolume", JOptionPane.PLAIN_MESSAGE);
                        }
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
