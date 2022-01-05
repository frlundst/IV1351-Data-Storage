import integration.SchoolDBException;
import view.BlockingInterpreter;
import controller.Controller;

public class App {

    /**
     * main starting method
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        try{
            new BlockingInterpreter(new Controller()).handleCmds();
        }catch(SchoolDBException exception){
            exception.printStackTrace();
        }

        /*try{
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
        }*/
        
    }
}
