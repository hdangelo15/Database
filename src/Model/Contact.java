package Model;

/**
 * A class that models the Contact objects.
 * @author Hayley D'Angelo
 */
public class Contact {

    private int Contact_ID;
    private String Contact_Name;
    private String Email;

    /**
     * Constructor for the Contact class.
     * @param Contact_ID
     * @param Contact_Name
     * @param Email
     */
    public Contact(int Contact_ID, String Contact_Name, String Email) {

        this.Contact_ID = Contact_ID;
        this.Contact_Name = Contact_Name;
        this.Email = Email;

    }

    /**
     * Gets the contact ID for a contact.
     * @return Contact_ID
     */
    public int getContact_ID() { return Contact_ID; }

    /**
     * Gets the contact name for a contact.
     * @return Contact_Name
     */
    public String getContact_Name() { return Contact_Name; }

    /**
     * Overrides the toString method for Contacts to display their ID and name.
     * @return A string with the contact ID and name.
     */
    @Override
    public String toString(){
        return (Contact_ID + " " + Contact_Name);
    }

}
