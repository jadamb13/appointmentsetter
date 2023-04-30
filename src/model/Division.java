package model;

import DBAccess.DBDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Class representing a Country object */
public class Division {
    private int divisionId;
    private String division;
    private int countryId;

    /* Getters and Setters */
    public Division(int divisionId, String division, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    /**
     Gets all Division Names in database.

     @return divisionNames ObservableList of Strings representing all division names

     */
    public static ObservableList<String> getAllDivisionNames(){
        ObservableList<String> divisionNames = FXCollections.observableArrayList();
        for (Division d : DBDivision.getAllDivisionsFromDb()){
            divisionNames.add(d.getDivision());
        }
        return divisionNames;
    }

    /**
     Gets all Division Names associated with the United States.

     @return unitedStates ObservableList of Strings representing all United States division names

     */
    public static ObservableList<String> getAllUnitedStatesDivisionNames(){
        ObservableList<String> unitedStatesDivisions = FXCollections.observableArrayList();
        for (Division d : DBDivision.getAllDivisionsFromDb()){
            if(d.getCountryId() == 1){
                unitedStatesDivisions.add(d.getDivision());
            }
        }

        return unitedStatesDivisions;
    }

    /**
     Gets all Division Names associated with Canada.

     @return canadaDivisions ObservableList of Strings representing all Canada division names

     */
    public static ObservableList<String> getAllCanadaDivisionNames(){
        ObservableList<String> canadaDivisions = FXCollections.observableArrayList();
        for (Division d : DBDivision.getAllDivisionsFromDb()){
            if(d.getCountryId() == 3){
                canadaDivisions.add(d.getDivision());
            }
        }
        return canadaDivisions;
    }

    /**
     Gets all Division Names associated with the United Kingdom.

     @return unitedKingdomDivisions ObservableList of Strings representing all United Kingdom division names

     */
    public static ObservableList<String> getAllUnitedKingdomDivisionNames(){
        ObservableList<String> unitedKingdomDivisions = FXCollections.observableArrayList();
        for (Division d : DBDivision.getAllDivisionsFromDb()){
            if(d.getCountryId() == 2){
                unitedKingdomDivisions.add(d.getDivision());
            }
        }
        return unitedKingdomDivisions;
    }

    /**
     Gets Division ID associated with a Division name.

     @return d.getDivisionId() customerID of customer with the parameter name; or -1 if none found

     */
    public static int getDivisionIdByName(String divisionName){
        for (Division d : DBDivision.getAllDivisionsFromDb()){
            if(d.getDivision().equals(divisionName)){
                return d.getDivisionId();
            }
        }
        return -1;
    }

    /**
     Gets Division Name associated with a Division ID.

     @return c.getDivision() Division Name associated with the parameter divisionId; or empty string if none found

     */
    public static String getDivisionNameById(int divisionId){
        for (Division d : DBDivision.getAllDivisionsFromDb()){
            if(d.getDivisionId() == divisionId){
                return d.getDivision();
            }
        }
        return "";
    }

}
