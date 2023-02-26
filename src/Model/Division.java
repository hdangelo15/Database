package Model;

import java.time.LocalDateTime;

/**
 * A class that models the Division objects.
 * @author Hayley D'Angelo
 */
public class Division {

    private int Division_ID;
    private String Division;
    private LocalDateTime Create_Date;
    private String Created_By;
    private LocalDateTime Last_Update;
    private String Last_Updated_By;
    private int Country_ID;

    /**
     * The constructor for the Division class.
     * @param Division_ID
     * @param Division
     * @param Create_Date
     * @param Created_By
     * @param Last_Update
     * @param Last_Updated_By
     * @param Country_ID
     */
    public Division(int Division_ID, String Division, LocalDateTime Create_Date, String Created_By,
                    LocalDateTime Last_Update, String Last_Updated_By, int Country_ID){

        this.Division_ID = Division_ID;
        this.Division = Division;
        this.Create_Date = Create_Date;
        this.Created_By = Created_By;
        this.Last_Update = Last_Update;
        this.Last_Updated_By = Last_Updated_By;
        this.Country_ID = Country_ID;

    }

    /**
     * Gets the division ID for a division.
     * @return Division_ID
     */
    public int getDivision_ID() { return Division_ID; }

    /**
     * Gets the division name for a division.
     * @return Division
     */
    public String getDivision() { return Division; }

    /**
     * Gets the country ID for a division.
     * @return Country_ID
     */
    public int getCountry_ID() { return Country_ID; }

    /**
     * Overrides the toString method for Division objects to display the division name.
     * @return A string displaying the division name.
     */
    @Override
    public String toString(){
        return (Division);
    }

}
