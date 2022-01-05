package integration;
import model.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SchoolDAO {
    private static final String url = "jdbc:postgresql://localhost/soundgood_musical_school_v4?user=postgres&password=hej12345";
    private Connection conn = null;

    private static final String STUDENT_ID_COLUMN_NAME = "student_id";
    private static final String STUDENT_SSN_COLUMN_NAME = "personal_number";
    private static final String STUDENT_NAME_COLUMN_NAME = "name";
    private static final String STUDENT_AGE_COLUMN_NAME = "age";
    private static final String STUDENT_ADDRESS_COLUMN_NAME = "address";
    private static final String STUDENT_SKILL_COLUMN_NAME = "skill_level";
    private static final String STUDENT_SIBLING_COLUMN_NAME = "sibling";
    private static final String STUDENT_ACCEPTED_COLUMN_NAME = "is_accepted";

    public SchoolDAO (){
        connectToDB();
    }

    /**
     * connectToDB method establish a connection to the database
     */
    private void connectToDB(){
        try {
            this.conn = DriverManager.getConnection(url);
            this.conn.setAutoCommit(false);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void createStudent(){
        try {
            Statement statement = this.conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStudent(){

    }

    public Student findStudentByID(String ID) throws SchoolDBException{
        ResultSet result = null;
        try{
            Statement statement = this.conn.createStatement();
            result = statement.executeQuery("select * from student where student_id="+ID);
            if(result.next()){
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
        }catch(SQLException exception){
            exception.printStackTrace();
        }
        return null;
    }

    public void createInstructor(){

    }

    public void deleteInstructor(){

    }

    public void findInstructorByID(){

    }
}
