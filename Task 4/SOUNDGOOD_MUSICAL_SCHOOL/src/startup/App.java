package startup;
import java.sql.*;


public class App {
    private static final String TABLE_NAME = "student";
    private static final String url = "jdbc:postgresql://localhost/soundgood_musical_school_v3?user=postgres&password=hej12345";
    public static void main(String[] args) throws Exception {
        try{
            Connection conn = DriverManager.getConnection(url);
            
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet results = metaData.getTables(null , null, TABLE_NAME ,null);
            while(results.next()){
                String tableName = results.getString(3);
                System.out.println(tableName);
            }

            Statement statement = conn.createStatement();
            ResultSet students = statement.executeQuery("select * from student");
            while(students.next()){
                System.out.println(students.getString(3));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        
    }
}
