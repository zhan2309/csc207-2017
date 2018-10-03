package ProjectPhase1;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This Class is for the Administrator Gui.
 */
public class AdministratorBox extends JFrame{
    private JPanel AdministratorPanel;
    private JButton viewEmployeeListsButton;
    private JButton viewOnSaleTimeButton;
    private JButton backToMenuButton;
    private JButton viewAllGoodsSectionButton;
    private JButton viewSaleHistoryButton;
    private JButton viewPriceHistoryButton;
    private JLabel picture;

    /**
     * The Constructor for the Administrator.
     */
    public AdministratorBox() {
        super("Administrator");
        setContentPane(AdministratorPanel);
        ImageIcon icon = new ImageIcon("adm.png");
        picture.setIcon(icon);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Administrator administrator = new Administrator();
        viewEmployeeListsButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e Click to view EmployeeList.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                administrator.showEmployeeList();
            }
        });
        viewOnSaleTimeButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e Click to view OnSaleTime table.
             */
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        backToMenuButton.addActionListener(new ActionListener() {
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
        viewAllGoodsSectionButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e Click to view Goods Sections.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                administrator.viewAllGoodsSection();
            }
        });
        viewOnSaleTimeButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e Click to view OnSale time.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                administrator.viewOnSaleTime();
            }
        });
        viewSaleHistoryButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e Click to view the Sale History.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                administrator.viewSaleHistory();
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
                administrator.viewPriceHisoty();
            }
        });
    }
}
