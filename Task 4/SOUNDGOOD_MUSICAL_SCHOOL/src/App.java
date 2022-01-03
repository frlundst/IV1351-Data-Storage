import java.sql.*;


public class App {
    private static final String TABLE_NAME = "student";
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
        String url = "jdbc:postgresql://localhost/soundgood_musical_school_v2?user=postgres&password=hej12345";
        try{
            Connection conn = DriverManager.getConnection(url);
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet results = metaData.getTables(null,null,null,null);
            boolean found = false;
            while(results.next()){
                String tableName = results.getString(3);
                System.out.println(tableName);
                if(tableName == TABLE_NAME){
                    found = true;
                }
            }
            System.out.println(found);
        }catch(SQLException e){
            e.printStackTrace();
        }
        
    }
}
