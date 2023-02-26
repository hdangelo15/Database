package Model;

import java.time.LocalDateTime;

/**
 * A class that models the Country objects.
 * @author Hayley D'Angelo
 */
public class Country {

    private int Country_ID;
    private String Country;
    private LocalDateTime Create_Date;
    private String Created_By;
    private LocalDateTime Last_Update;
    private String Last_Updated_By;

    /**
     * Constructor for the Country class.
     * @param Country_ID
     * @param Country
     * @param Create_Date
     * @param Created_By
     * @param Last_Update
     * @param Last_Updated_By
     */
    public Country(int Country_ID, String Country, LocalDateTime Create_Date, String Created_By, LocalDateTime Last_Update, String Last_Updated_By) {

        this.Country_ID = Country_ID;
        this.Country = Country;
        this.Create_Date = Create_Date;
        this.Created_By = Created_By;
        this.Last_Update = Last_Update;
        this.Last_Updated_By = Last_Updated_By;

    }

    /**
     * Gets the ID of a country.
     * @return Country_ID
     */
    public int getCountry_ID() { return Country_ID; }

    /**
     * Gets the name of a country.
     * @return Country
     */
    public String getCountry() { return Country; }

    /**
     * Overrides the toString method for Country objects to display the country name.
     * @return A string displaying the country name.
     */
    @Override
    public String toString(){
        return (Country);
    }

}
