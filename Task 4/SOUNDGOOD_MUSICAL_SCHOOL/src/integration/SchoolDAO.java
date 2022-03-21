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

    private static final String LEASE_CONTRACT_TABLE_NAME = "lease_contract";
    private static final String LEASE_TERMINATED_COLUMN_NAME = "terminated";

    private static final String STUDENT_ID_COLUMN_NAME = "student_id";

    private PreparedStatement findAvailableInstrumentByKind;
    private PreparedStatement findAvailableInstrumentByID;
    private PreparedStatement findActiveLeaseContractByID;
    private PreparedStatement createLeaseContract;
    private PreparedStatement makeInstrumentUnavailableByID;
    private PreparedStatement makeInstrumentAvailableByID;
    private PreparedStatement terminateLeaseContrectByInstrumentID;
    private PreparedStatement findActiveLeaseContractByInstrumentID;

    public SchoolDAO() throws SchoolDBException {
        try {
            connectToDB();
            prepareStatements();
        } catch (ClassNotFoundException | SQLException e) {
            throw new SchoolDBException("Could not connect to database!", e);
        }
    }

    /**
     * connectToDB method establish a connection to the database
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private void connectToDB() throws ClassNotFoundException, SQLException {
        conn = DriverManager.getConnection(url);
        conn.setAutoCommit(false);
    }

    /**
     * 
     * @param kind The instrument kind.
     * @throws SchoolDBException
     * @throws SQLException
     */
    public List<Instrument> findAvailableInstruments(String kind) throws SchoolDBException {
        ResultSet result = null;
        List<Instrument> instruments = new ArrayList<>();
        try {
            findAvailableInstrumentByKind.setString(1, kind);
            ;
            result = findAvailableInstrumentByKind.executeQuery();
            while (result.next()) {
                instruments.add(new Instrument(result.getString(INSTRUMENT_ID_COLUMN_NAME),
                        result.getString(INSTRUMENT_TYPE_COLUMN_NAME),
                        result.getString(INSTRUMENT_BRAND_COLUMN_NAME),
                        result.getString(INSTRUMENT_AVAILABLE_COLUMN_NAME),
                        result.getString(INSTRUMENT_QUANTITY_COLUMN_NAME)));
            }
            conn.commit();
        } catch (SQLException e) {
            handleException("Could not get instruments.", e);
        } finally {
            closeResultSet("Could not get instruments.", result);
        }
        return instruments;
    }

    /**
     * 
     * @param student_ID
     * @param rental_instrument_ID
     * @throws SchoolDBException
     */
    public void createLeaseContract(int student_ID, int rental_instrument_ID) throws SchoolDBException {
        ResultSet result = null;
        int rowCount = 0;
        try {
            findAvailableInstrumentByID.setInt(1, rental_instrument_ID);
            if (!findAvailableInstrumentByID.executeQuery().next()) {
                handleException("Instrument is not available", null);
            }
            findActiveLeaseContractByID.setInt(1, student_ID);
            result = findActiveLeaseContractByID.executeQuery();
            while(result.next()){
                rowCount++;
            }
            if(rowCount > 1){
                handleException("Student already has two lease contracts active!", null);
            }
            createLeaseContract.setInt(1, student_ID);
            createLeaseContract.setInt(2, rental_instrument_ID);
            int createdRows = createLeaseContract.executeUpdate();
            if (createdRows != 1) {
                handleException("Could not create lease contract", null);
            }
            makeInstrumentUnavailableByID.setInt(1, rental_instrument_ID);
            int updatedRows = makeInstrumentUnavailableByID.executeUpdate();
            if (updatedRows != 1) {
                handleException("Could not make instrument unavailable", null);
            }
            conn.commit();
        } catch (SQLException e) {
            handleException("Could not create lease contract", e);
        }finally{
            closeResultSet("Could not terminate lease contract.", result);
        }
    }

    /**
     * 
     * @param student_ID
     * @throws SchoolDBException
     */
    public void terminateLeaseContract(int instrument_id) throws SchoolDBException {
        ResultSet result = null;
        try {
            findActiveLeaseContractByInstrumentID.setInt(1, instrument_id);
            result = findActiveLeaseContractByInstrumentID.executeQuery();
            if (!result.next()) {
                handleException("Could not find an active lease contract with the given student ID", null);
            }
            terminateLeaseContrectByInstrumentID.setInt(1, instrument_id);
            int updatedRows = terminateLeaseContrectByInstrumentID.executeUpdate();
            if(updatedRows != 1){
                handleException("Could not terminate lease contract", null);
            }
            makeInstrumentAvailableByID.setInt(1, instrument_id);
            updatedRows = makeInstrumentAvailableByID.executeUpdate();
            if(updatedRows != 1){
                handleException("Could not make instrument available", null);
            }
            conn.commit();
        } catch (SQLException e) {
            handleException("Could not terminate lease contract", e);
        } finally{
            closeResultSet("Could not terminate lease contract.", result);
        }
    }

    /**
     * prepared statements to be executed.
     * @throws SQLException
     */
    public void prepareStatements() throws SQLException {
        findAvailableInstrumentByKind = conn.prepareStatement("SELECT * FROM " + INSTRUMENT_TABLE_NAME
                + " WHERE " + INSTRUMENT_TYPE_COLUMN_NAME + " = ? AND " + INSTRUMENT_AVAILABLE_COLUMN_NAME
                + "='true'");

        findAvailableInstrumentByID = conn.prepareStatement("SELECT * FROM " + INSTRUMENT_TABLE_NAME
                + " WHERE " + INSTRUMENT_ID_COLUMN_NAME + " = ? AND " + INSTRUMENT_AVAILABLE_COLUMN_NAME
                + "='true'");

        findActiveLeaseContractByID = conn.prepareStatement("SELECT * FROM " + LEASE_CONTRACT_TABLE_NAME
                + " WHERE " + STUDENT_ID_COLUMN_NAME + " = ? AND " + LEASE_TERMINATED_COLUMN_NAME + "='false'");
        
        findActiveLeaseContractByInstrumentID = conn.prepareStatement("SELECT * FROM " + LEASE_CONTRACT_TABLE_NAME
                + " WHERE " + INSTRUMENT_ID_COLUMN_NAME + " = ? AND " + LEASE_TERMINATED_COLUMN_NAME + "='false'");

        createLeaseContract = conn.prepareStatement("INSERT INTO " + LEASE_CONTRACT_TABLE_NAME
                + " (student_id, rental_instrument_id, price, time_start, time_end, terminated)" +
                "VALUES ( ? , ? , 39, '2021-02-11 17:21:05', '2021-10-23 21:35:51', 'false');");

        makeInstrumentUnavailableByID = conn.prepareStatement("UPDATE " + INSTRUMENT_TABLE_NAME
                + " SET " + INSTRUMENT_AVAILABLE_COLUMN_NAME + " ='false' WHERE " + INSTRUMENT_ID_COLUMN_NAME + " = ? ");
        
        terminateLeaseContrectByInstrumentID = conn.prepareStatement("UPDATE " + LEASE_CONTRACT_TABLE_NAME 
        + " SET " + LEASE_TERMINATED_COLUMN_NAME + " ='true' WHERE " + INSTRUMENT_ID_COLUMN_NAME + " = ? AND " + LEASE_TERMINATED_COLUMN_NAME + " ='false'");

        makeInstrumentAvailableByID = conn.prepareStatement("UPDATE " + INSTRUMENT_TABLE_NAME 
        + " SET " + INSTRUMENT_AVAILABLE_COLUMN_NAME + " ='true' WHERE " + INSTRUMENT_ID_COLUMN_NAME + " = ? ");
    }

    /**
     * handleException handles exceptions and can be called by other methods with 
     * if-statements to ensure that nothing goes wrong during the query process and
     * makes the code follow ACID.
     * @param failureMsg
     * @param cause
     * @throws SchoolDBException
     */
    private void handleException(String failureMsg, Exception cause) throws SchoolDBException {
        String completeFailureMsg = failureMsg;
        try {
            conn.rollback();
        } catch (SQLException rollbackExc) {
            completeFailureMsg = completeFailureMsg +
                    ". Also failed to rollback transaction because of: " + rollbackExc.getMessage();
        }

        if (cause != null) {
            throw new SchoolDBException(failureMsg, cause);
        } else {
            throw new SchoolDBException(failureMsg);
        }
    }

    /**
     * 
     * @param failureMsg
     * @param result
     * @throws BankDBException
     */
    private void closeResultSet(String failureMsg, ResultSet result) throws SchoolDBException {
        try {
            result.close();
        } catch (Exception e) {
            throw new SchoolDBException(failureMsg + " Could not close result set.", e);
        }
    }
}
