package integration;
import model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SchoolDAO {
    private static final String url = "jdbc:postgresql://localhost/soundgood_musical_school_v3?user=postgres&password=hej12345";
    private Connection conn;

    private static final String STUDENT_ID_COLUMN_NAME = "student_id";
    private static final String STUDENT_SSN_COLUMN_NAME = "personal_number";
    private static final String STUDENT_NAME_COLUMN_NAME = "name";
    private static final String STUDENT_AGE_COLUMN_NAME = "age";
    private static final String STUDENT_ADDRESS_COLUMN_NAME = "address";
    private static final String STUDENT_SKILL_COLUMN_NAME = "skill_level";
    private static final String STUDENT_SIBLING_COLUMN_NAME = "sibling";
    private static final String STUDENT_ACCEPTED_COLUMN_NAME = "is_accepted";

    private static final String INSTRUMENT_ID_COLUMN_NAME = "rental_instrument_id";
    private static final String INSTRUMENT_TYPE_COLUMN_NAME = "instrument";
    private static final String INSTRUMENT_BRAND_COLUMN_NAME = "brand";
    private static final String INSTRUMENT_AVAILABLE_COLUMN_NAME = "available";
    private static final String INSTRUMENT_QUANTITY_COLUMN_NAME = "quantity";

    public SchoolDAO() {
        connectToDB();
    }

    /**
     * connectToDB method establish a connection to the database
     */
    private void connectToDB() {
        try {
            this.conn = DriverManager.getConnection(url);
            this.conn.setAutoCommit(false);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * 
     * @param ID
     * @return
     * @throws SchoolDBException
     */
    public Student findStudentByID(String ID) throws SchoolDBException {
        ResultSet result = null;
        try {
            Statement statement = this.conn.createStatement();
            result = statement.executeQuery("SELECT * FROM student WHERE student_id=" + ID);
            if (result.next()) {
                return new Student(result.getString(STUDENT_ID_COLUMN_NAME),
                        result.getString(STUDENT_SSN_COLUMN_NAME),
                        result.getString(STUDENT_NAME_COLUMN_NAME),
                        result.getString(STUDENT_AGE_COLUMN_NAME),
                        result.getString(STUDENT_ADDRESS_COLUMN_NAME),
                        result.getString(STUDENT_SKILL_COLUMN_NAME),
                        result.getString(STUDENT_SIBLING_COLUMN_NAME),
                        result.getString(STUDENT_ACCEPTED_COLUMN_NAME));
            }
            conn.commit();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    /**
     * 
     * @param kind
     * @throws SchoolDBException
     */
    public List<Instrument> readAllAvailableInstruments(String kind) throws SchoolDBException {
        ResultSet result = null;
        List<Instrument> instruments = new ArrayList<>();
        try {
            Statement statement = this.conn.createStatement();
            result = statement.executeQuery(
                    "SELECT * FROM rental_instrument WHERE instrument='" + kind + "' AND available='true'");
            while (result.next()) {
                instruments.add(new Instrument(result.getString(INSTRUMENT_ID_COLUMN_NAME),
                        result.getString(INSTRUMENT_TYPE_COLUMN_NAME),
                        result.getString(INSTRUMENT_BRAND_COLUMN_NAME),
                        result.getString(INSTRUMENT_AVAILABLE_COLUMN_NAME),
                        result.getString(INSTRUMENT_QUANTITY_COLUMN_NAME)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return instruments;
    }

    public String createLeaseContract(String student_ID, String rental_instrument_ID) {
        // Gör ett nytt leasecontract
        // Gör instrumentet not available
        try {
            Statement statement = this.conn.createStatement();
            if (statement.executeQuery("SELECT * FROM rental_instrument WHERE rental_instrument_id='" + rental_instrument_ID + "' AND available='true'").next()) {
                if (!statement.executeQuery("SELECT * FROM lease_contract WHERE student_id='" + student_ID + "' AND terminated='false'").next()) {
                    statement.executeUpdate(
                            "INSERT INTO lease_contract (student_id, rental_instrument_id, price, time_start, time_end) VALUES ("
                                    + student_ID + ", " + rental_instrument_ID
                                    + ", 39, '2021-02-11 17:21:05', '2021-10-23 21:35:51');");
                    statement.executeUpdate("UPDATE rental_instrument SET available='false' WHERE rental_instrument_ID=" + rental_instrument_ID);
                    conn.commit();
                    return "Created a new lease contract";
                } else {
                    return "This student already rents an instrument";
                }
            }else{
                return "Someone else is renting this instrument";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String deleteLeaseContract(String student_ID){
        try {
            String rental_instrument_ID = null;
            Statement statement = this.conn.createStatement();

            ResultSet result = statement.executeQuery("SELECT * FROM lease_contract WHERE student_id='" + student_ID + "'");
            if(result.next()){
                rental_instrument_ID = result.getString("rental_instrument_id");
                System.out.println(rental_instrument_ID);
            }

            statement.executeUpdate("UPDATE lease_contract SET terminated='true' where student_id='" + student_ID + "'");
            
            statement.executeUpdate("UPDATE rental_instrument SET available='true' where rental_instrument_id='" + rental_instrument_ID + "'");
            conn.commit();
            return "Lease contract terminated";
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
