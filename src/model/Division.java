package model;

import DBAccess.DBCountry;
import DBAccess.DBDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Division {
    private int divisionId;
    private String division;
    private int countryId;

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

    public static ObservableList<String> getAllDivisionNames(){
        ObservableList<String> divisionNames = FXCollections.observableArrayList();
        for (Division d : DBDivision.getAllDivisionsFromDb()){
            divisionNames.add(d.getDivision());
        }
        return divisionNames;
    }

    public static ObservableList<String> getAllUnitedStatesDivisionNames(){
        ObservableList<String> unitedStatesDivisions = FXCollections.observableArrayList();
        for (Division d : DBDivision.getAllDivisionsFromDb()){
            if(d.getCountryId() == 1){
                unitedStatesDivisions.add(d.getDivision());
            }
        }

        return unitedStatesDivisions;
    }

    public static ObservableList<String> getAllCanadaDivisionNames(){
        ObservableList<String> canadaDivisions = FXCollections.observableArrayList();
        for (Division d : DBDivision.getAllDivisionsFromDb()){
            if(d.getCountryId() == 3){
                canadaDivisions.add(d.getDivision());
            }
        }
        return canadaDivisions;
    }

    public static ObservableList<String> getAllUnitedKingdomDivisionNames(){
        ObservableList<String> unitedKingdomDivisions = FXCollections.observableArrayList();
        for (Division d : DBDivision.getAllDivisionsFromDb()){
            if(d.getCountryId() == 2){
                unitedKingdomDivisions.add(d.getDivision());
            }
        }
        return unitedKingdomDivisions;
    }

    public static int getDivisionIdByName(String divisionName){
        for (Division d : DBDivision.getAllDivisionsFromDb()){
            if(d.getDivision().equals(divisionName)){
                return d.getDivisionId();
            }
        }
        return -1;
    }

    public static String getDivisionNameById(int divisionId){
        for (Division d : DBDivision.getAllDivisionsFromDb()){
            if(d.getDivisionId() == divisionId){
                return d.getDivision();
            }
        }
        return "";
    }

}
