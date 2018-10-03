package ProjectPhase1;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ScanIn class for the ScanIn Gui.
 */
public class ScanInBox extends JFrame {
    private JPanel ScanInBox;
    private JFormattedTextField UPC;
    private JFormattedTextField NAME;
    private JFormattedTextField QUANTITY;
    private JFormattedTextField PRICE;
    private JFormattedTextField LOCATION;
    private JFormattedTextField COST;
    private JFormattedTextField THRESHOLD;
    private JFormattedTextField StoreName;
    private JButton SCANINButton;
    private JButton backToLastMenuButton;
    private JLabel hint;

    /**
     * The constructor for the ScanIn Gui.
     */
    public ScanInBox (){
        super("Scan In");
        setContentPane(ScanInBox);
        setSize(600,400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Receiver receiver = new Receiver();
        SCANINButton.addActionListener(new ActionListener() {

            /**
             * Invoked when an action occurs.
             *
             * @param e Click to run the ScanIn Box.
             */
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    String upc = UPC.getText();
                    String name = NAME.getText();
                    int quantity = Integer.parseInt(QUANTITY.getText());
                    double price = Double.parseDouble(PRICE.getText());
                    String location = LOCATION.getText();
                    String storeName = StoreName.getText();
                    int threshold = Integer.parseInt(THRESHOLD.getText());
                    double cost = Double.parseDouble(COST.getText());
                    if(Helpers.isValidUPc(upc) && price > 0 &&
                            quantity > 0 && threshold >= 0 && cost >0){
                        Goods goods = new Goods(upc,name,quantity,price,location,cost,threshold);
                        if (Helpers.isUpcExist(upc)){
                            JOptionPane.showMessageDialog(null,
                                    "UPC has already existed",
                                    "Invalid Input", JOptionPane.WARNING_MESSAGE);
                        } else {
                            receiver.scanIn(goods, storeName);
                            JOptionPane.showMessageDialog(null, "ScanIn successfully");
                            UPC.setValue("");
                            NAME.setValue("");
                            QUANTITY.setValue("");
                            PRICE.setValue("");
                            LOCATION.setValue("");
                            StoreName.setValue("");
                            THRESHOLD.setValue("");
                            COST.setValue("");
                        }
                    } else {
                        Helpers.showInVaildMessage();
                    }
                } catch (Exception e1){
                    JOptionPane.showMessageDialog(null,
                            "Invalid Input Information", "Invalid Input", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        backToLastMenuButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e Click to Go Back.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                ReceiverBox receiverBox = new ReceiverBox();
                receiverBox.setVisible(true);
                dispose();
            }
        });
    }
}
