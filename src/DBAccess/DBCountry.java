package DBAccess;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** A class to communicate with the DB about Country data. */
public class DBCountry {

    /**
     * Queries DB to gather data and create Country objects to be added to ObservableList.
     *
     * @return countriesList list of Country objects created from data in the DB
     */
    public static ObservableList<Country> getAllCountries() {
        ObservableList<Country> countriesList = FXCollections.observableArrayList();

        try {
            String sql = "SELECT Country_ID, Country FROM countries";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Country c = new Country(countryId, countryName);
                countriesList.add(c);
            }

        }catch (SQLException e){
            e.printStackTrace();
        }

        return countriesList;
    }


}
