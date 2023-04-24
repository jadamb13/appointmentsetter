package DBAccess;

import helper.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import model.Customer;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCountry {
    public static ObservableList<Country> getAllCountries() {
        ObservableList<Country> countriesList = FXCollections.observableArrayList();

        try {
            // SQL statement
            String sql = "SELECT Country_ID, Country FROM countries";

            // Create a PreparedStatement
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            // Execute query and get results
            ResultSet rs = ps.executeQuery();

            // Work through result set one row at a time
            while (rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                Country c = new Country(countryId, countryName);
                countriesList.add(c);
            }

        }catch (SQLException e){
            System.out.println("Caught ye country: " + e.getMessage());
        }

        return countriesList;
    }


}
