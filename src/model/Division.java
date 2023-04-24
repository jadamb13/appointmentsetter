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
}
