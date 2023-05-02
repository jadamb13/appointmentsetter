package model;

import DBAccess.DBCustomer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Class representing a Country object */
public class Customer {
    private int customerId;
    private String customerName;
    private String customerAddress;
    private String customerPostalCode;
    private String customerPhoneNumber;
    private int divisionId;

    public Customer(int customerId, String customerName, String customerAddress, String customerPostalCode,
                    String customerPhoneNumber, int divisionId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhoneNumber = customerPhoneNumber;
        this.divisionId = divisionId;
    }

    /* Getters and Setters */
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerPostalCode() {
        return customerPostalCode;
    }

    public void setCustomerPostalCode(String customerPostalCode) {
        this.customerPostalCode = customerPostalCode;
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(String customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    /**
     Gets Customer ID associated with a name.

     @return c.getCustomerId() customerID of customer with the parameter name; or -1 if none found

     */
    public static int getCustomerIdByName(String name){
        for (Customer c : DBCustomer.getAllCustomersFromDb()){
            if(c.getCustomerName().equals(name)){
                return c.getCustomerId();
            }

        }
        return -1;
    }

    /**
     Gets Name associated with a Customer ID.

     @return c.getCustomerName() Customer name associated with the parameter id

     */
    public static String getCustomerNameById(int id){
        for (Customer c : DBCustomer.getAllCustomersFromDb()){
            if(c.getCustomerId() == id){
                return c.getCustomerName();
            }
        }
        return "";
    }

    /**
     Creates list of names representing all Customer objects.

     @return customerNames list of String objects representing names of all Customers

     */
    public static ObservableList<String> getAllCustomerNames(){
        ObservableList<String> customerNames = FXCollections.observableArrayList();
        for (Customer c : DBCustomer.getAllCustomersFromDb()){
            customerNames.add(c.getCustomerName());
        }
        return customerNames;
    }

}
