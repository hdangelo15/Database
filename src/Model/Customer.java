package Model;

import java.time.LocalDateTime;

/**
 * A class that models the Customer objects.
 * @author Hayley D'Angelo
 */
public class Customer {

    private int Customer_ID;
    private String Customer_Name;
    private String Address;
    private String Postal_Code;
    private String Phone;
    private LocalDateTime Create_Date;
    private String Created_By;
    private LocalDateTime Last_Update;
    private String Last_Updated_By;
    private int Division_ID;
    private String Division;
    private String Country;

    /**
     * Constructor for the Customer class.
     * @param Customer_ID
     * @param Customer_Name
     * @param Address
     * @param Postal_Code
     * @param Phone
     * @param Create_Date
     * @param Created_By
     * @param Last_Update
     * @param Last_Updated_By
     * @param Division_ID
     * @param Division
     * @param Country
     */
    public Customer(int Customer_ID, String Customer_Name, String Address, String Postal_Code, String Phone, LocalDateTime Create_Date,
                    String Created_By, LocalDateTime Last_Update, String Last_Updated_By, int Division_ID, String Division, String Country) {

        this.Customer_ID = Customer_ID;
        this.Customer_Name = Customer_Name;
        this.Address = Address;
        this.Postal_Code = Postal_Code;
        this.Phone = Phone;
        this.Create_Date = Create_Date;
        this.Created_By = Created_By;
        this.Last_Update = Last_Update;
        this.Last_Updated_By = Last_Updated_By;
        this.Division_ID = Division_ID;
        this.Division = Division;
        this.Country = Country;

    }

    /**
     * Gets the ID of a customer.
     * @return Customer_ID
     */
    public int getCustomer_ID() { return Customer_ID; }

    /**
     * Gets the name of a customer.
     * @return Customer_Name
     */
    public String getCustomer_Name() { return Customer_Name; }

    /**
     * Gets the address of a customer.
     * @return Address
     */
    public String getAddress() { return Address; }

    /**
     * Gets the postal code of a customer.
     * @return Postal_Code
     */
    public String getPostal_Code() { return Postal_Code; }

    /**
     * Gets the phone number of a customer.
     * @return Phone
     */
    public String getPhone() { return Phone; }

    /**
     * Gets the division ID of a customer.
     * @return Division_ID
     */
    public int getDivision_ID() { return Division_ID; }

    /**
     * Gets the division of a customer.
     * @return Division
     */
    public String getDivision() { return Division; }

    /**
     * Gets the country of a customer.
     * @return Country
     */
    public String getCountry() { return Country; }

    /**
     * Overrides the toString method of a Customer object to display the customer ID and name.
     * @return A string displaying the customer id and name.
     */
    @Override
    public String toString(){
        return (Customer_ID + " " + Customer_Name);
    }

}

