package integration;
import model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SchoolDAO {
    private static final String url = "jdbc:postgresql://localhost/Soundgood_musical_school_V5?user=postgres&password=123123";
    private Connection conn;

    private static final String INSTRUMENT_TABLE_NAME = "rental_instrument";
    private static final String INSTRUMENT_ID_COLUMN_NAME = "rental_instrument_id";
    private static final String INSTRUMENT_TYPE_COLUMN_NAME = "instrument";
    private static final String INSTRUMENT_BRAND_COLUMN_NAME = "brand";
    private static final String INSTRUMENT_AVAILABLE_COLUMN_NAME = "available";
    private static final String INSTRUMENT_QUANTITY_COLUMN_NAME = "quantity";

    private PreparedStatement findAvailableInstrumentByKind;
    private PreparedStatement findAvailableInstrumentByID;

    public SchoolDAO() throws SchoolDBException{
        try {
            connectToDB();
            prepareStatements();
        } catch (ClassNotFoundException | SQLException e) {
            throw new SchoolDBException("Could not connect to database!", e);
        }
    }

    /**
     * connectToDB method establish a connection to the database
     */
    private void connectToDB() throws ClassNotFoundException, SQLException{
            conn = DriverManager.getConnection(url);
            conn.setAutoCommit(false);
    }

    /**
     * 
     * @param lock Locks so that no UPDATEs or DELETEs can be
     * performed on the the selected row or rows during the current transaction.
     * @param kind The instrument kind.
     * @throws SchoolDBException
     */
    public List<Instrument> findAvailableInstruments(String kind, boolean lock) throws SchoolDBException {
        PreparedStatement stmt;
        if(lock) stmt = null;
        else stmt = findAvailableInstrumentByKind;

        ResultSet result = null;
        List<Instrument> instruments = new ArrayList<>();
        try {
            stmt.setString(1, kind);;
            result = stmt.executeQuery();
            while (result.next()) {
                instruments.add(new Instrument(result.getString(INSTRUMENT_ID_COLUMN_NAME),
                        result.getString(INSTRUMENT_TYPE_COLUMN_NAME),
                        result.getString(INSTRUMENT_BRAND_COLUMN_NAME),
                        result.getString(INSTRUMENT_AVAILABLE_COLUMN_NAME),
                        result.getString(INSTRUMENT_QUANTITY_COLUMN_NAME)));
            }
            if(!lock) conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //Add closeResultSet
        }
        return instruments;
    }

    public String createLeaseContract(String student_ID, String rental_instrument_ID) {
        try {
            Statement statement = conn.createStatement();
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

    public void prepareStatements() throws SQLException{
        findAvailableInstrumentByKind = conn.prepareStatement("SELECT * FROM " + INSTRUMENT_TABLE_NAME 
            + " WHERE " + INSTRUMENT_TYPE_COLUMN_NAME + " = ? AND " + INSTRUMENT_AVAILABLE_COLUMN_NAME 
            + "='true'");
        findAvailableInstrumentByID = conn.prepareStatement("SELECT * FROM " + INSTRUMENT_TABLE_NAME 
            + " WHERE " + INSTRUMENT_ID_COLUMN_NAME + " = ? AND " + INSTRUMENT_AVAILABLE_COLUMN_NAME 
            + "='true'");
    }
}
