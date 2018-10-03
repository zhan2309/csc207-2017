package ProjectPhase1;

import org.omg.CORBA.IMP_LIMIT;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The Class is for the Cashier GUI.
 */
public class CashierBox extends JFrame{
    private JPanel CashierPanel;
    private JFormattedTextField UPC;
    private JFormattedTextField QUANTITY;
    private JButton CHECKONSALETIMEButton;
    private JButton SCANOUTButton;
    private JButton backToLastMenuButton;
    private JLabel picture;

    /**
     * The Constructor for the Cashier GUI.
     */
    public CashierBox(){
        super("Cashier");
        setContentPane(CashierPanel);
        pack();
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon("ca.png");
        picture.setIcon(icon);
        Cashier cashier = new Cashier();
        CHECKONSALETIMEButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e Click to check OnSaleTime.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                cashier.checkOnSaleTime();
            }
        });
        SCANOUTButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e Click to scan in items.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = "<html><b style='color:red'>Enter Your Name:</b><br>"
                        + "Use letters only.";
                try{
                    String upc = UPC.getText();
                    if(Helpers.isValidUPc(upc) &&
                            DataBaseViewer.loopColumnForString("upc", upc, "InventoryName")) {
                        int quantity = Integer.parseInt(QUANTITY.getText());
                        cashier.scanOut(upc, quantity);
                        UPC.setValue("");
                        QUANTITY.setValue("");
                    } else {
                        Helpers.showInvalidUpc();
                    }
                } catch (Exception e1){
                    Helpers.showInVaildMessage();
                }
            }
        });
        backToLastMenuButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e Go back to Last menu.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                Login login = new Login();
                login.setVisible(true);
                dispose();
            }
        });
    }
}
