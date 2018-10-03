package ProjectPhase1;

/**
 * Administrator class, can view all the static tables on DataBase.
 */
class Administrator {
    /**
     * show a dialog of  a list of all employees.
     */
    void showEmployeeList(){
        DataBaseShowTable dataBaseShowTable
                = new DataBaseShowTable("EmployList");
    }
    
    /**
     * show a dialog of  a list of all sections.
     */
    void viewAllGoodsSection(){
        DataBaseShowTable dataBaseShowTable
                = new DataBaseShowTable("InventoryName");
    }

    /**
     * show a dialog of  a list of all OnSaleTime.
     */
    void viewOnSaleTime(){
        DataBaseShowTable dataBaseShowTable
                = new DataBaseShowTable("OnSaleTime");
    }

    /**
     * show a dialog of  a list of all PriceHistory.
     */
    public void viewPriceHisoty(){
        DataBaseShowTable dataBaseShowTable
                = new DataBaseShowTable("PriceHistory");
    }

    /**
     * show a dialog of  a list of all SaleHistory.
     */
    public void viewSaleHistory(){
        DataBaseShowTable dataBaseShowTable
                = new DataBaseShowTable("SaleHistory");
    }

}
