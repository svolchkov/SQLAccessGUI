/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package idsearch;

import static com.mysql.jdbc.MysqlErrorNumbers.ER_ABORTING_CONNECTION;
import static com.mysql.jdbc.MysqlErrorNumbers.ER_TABLE_EXISTS_ERROR;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author sergeyv
 */
public class SQLUtil {

    /**
     * @param args the command line arguments
     */
    
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
   static final String DB_ADDRESS = "jdbc:mysql://localhost/";
   static final String DB_NAME = "ID_SEARCH_DB";
   static final String FULL_NAME = DB_ADDRESS + DB_NAME;

   //  Database credentials
   static final String USER = "root";
   static final String PASS = "";
   public static final int MYSQL_DUPLICATE_PK = 1062;
   
    
    
    public static void createDB() {
        
        Connection conn = null;
        Statement s = null;
        
        try{
      //STEP 2: Register JDBC driver
          Class.forName("com.mysql.jdbc.Driver");

          //STEP 3: Open a connection
          System.out.println("Connecting to a selected database...");

          conn = DriverManager.getConnection(DB_ADDRESS, USER, PASS);
          s = conn.createStatement();
          int result = s.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
          // System.out.println(result);
          try{
             if(conn!=null)
                conn.close();
          
          
          }catch(SQLException se){
//             System.out.println(se.getErrorCode());
//             if (se.getErrorCode() == ER_ABORTING_CONNECTION ){
//                    
//             }
             se.printStackTrace();
          }
        }catch (com.mysql.jdbc.exceptions.jdbc4.CommunicationsException ce){
              System.out.println("Communications link failure, aborting connection...");
                    System.exit(-1);
          
        // TODO code application logic here
        }catch(Exception e){
      //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
      //finally block used to close resources
          try{
             if(s!=null)
                conn.close();
          }catch(SQLException se){
          }// do nothing
          try{
             if(conn!=null)
                conn.close();
          }catch(SQLException se){
             se.printStackTrace();
          }//end finally try
       }
    }
    
    public static void createTable() {
        System.out.println("Creating table in given database...");
        Connection conn = null;
        Statement s = null;
        try{
          conn = DriverManager.getConnection(FULL_NAME, USER, PASS);
          s = conn.createStatement();
      
      
      
          String sql = "CREATE TABLE IDTABLE( " +
                   "ID INTEGER(10) NOT NULL PRIMARY KEY," +
                    "NAME VARCHAR(20) NOT NULL)"; 
        
          s.executeUpdate(sql);
          // System.out.println("Created table in given database...");
        }catch(SQLException se){
      //Handle errors for JDBC
            if (se.getErrorCode() == ER_TABLE_EXISTS_ERROR ){
                    System.out.println("Table exists, accessing table...");
            }else{
                se.printStackTrace();
            }
          //System.out.println(se.getErrorCode());
        }catch(Exception e){
      //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
      //finally block used to close resources
            try{
                if(s!=null)
                    conn.close();
            }catch(SQLException se){
                }// do nothing
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
      }//end finally try
    }
  }
    
    
  public static int insertData(int ID, String name) {
    // System.out.println("Inserting records...");
    Connection conn = null;
    Statement s = null;
    int result = -1;
    try{
                conn = DriverManager.getConnection(FULL_NAME, USER, PASS);
                s = conn.createStatement();
                String stringID = Integer.toString(ID);
                String query = String.format("insert into IDTABLE (ID, NAME) values (%s, '%s')",stringID,name);
                result = s.executeUpdate(query);
                // System.out.println("Inserted data...");
                
    }catch(SQLException se){
            // System.out.println(se.getErrorCode());
            if(se.getErrorCode() == MYSQL_DUPLICATE_PK ){
                    result = 0;
            }
          // se.printStackTrace();
    }catch(Exception e){
      //Handle errors for Class.forName
            e.printStackTrace();
    }finally{
      //finally block used to close resources
            try{
                if(s!=null)
                    conn.close();
            }catch(SQLException se){
                }// do nothing
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
      }//end finally try
    }
        return result;
  }
  
  
  public static String selectData(int ID){
    // System.out.println("Checking existing records...");
    Connection conn = null;
    Statement s = null;
    String selectedName = "***Not found***";
    try{
                
                conn = DriverManager.getConnection(FULL_NAME, USER, PASS);
                s = conn.createStatement();
                String selectQuery = String.format("SELECT * "
                        + "FROM IDTABLE where ID = %d;",ID);
                 // String selectQuery = "SELECT Supplier,Name,Balance FROM SUPPLIERS;";
                 ResultSet result = null;
                 s = conn.createStatement();
                 result = s.executeQuery(selectQuery); // execute the SQL query
                 
                 // System.out.printf("%-12s%-20s\n","ID","Name");
                 
                 while (result.next()) { // loop until the end of the results
                    int selectedID = result.getInt("ID");
                    selectedName = result.getString("NAME");
                    break;
                   
                    
                    // System.out.printf("%-12d%-20s\n",selectedID,selectedName);
                    }
    }catch(SQLException se){
            // System.out.println(se.getErrorCode());
            se.printStackTrace();
          // se.printStackTrace();
    }catch(Exception e){
      //Handle errors for Class.forName
            e.printStackTrace();
    }finally{
      //finally block used to close resources
            try{
                if(s!=null)
                    conn.close();
            }catch(SQLException se){
                }// do nothing
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
      }//end finally try
    }
    return selectedName;
  }
  
  public static int updateData(int ID, String name) {
    // System.out.println("Updating records...");
    Connection conn = null;
    Statement s = null;
    int result = -1;
    try{
                conn = DriverManager.getConnection(FULL_NAME, USER, PASS);
                s = conn.createStatement();
                // String stringID = Integer.toString(ID);
                String query = String.format("update IDTABLE set NAME = '%s' where ID = %d",name, ID);
                result = s.executeUpdate(query);
                // System.out.println("Updated data...");
                
    }catch(SQLException se){
            System.out.println(se.getErrorCode());
//            if(se.getErrorCode() == MYSQL_DUPLICATE_PK ){
//                    result = 0;
//            }
          // se.printStackTrace();
    }catch(Exception e){
      //Handle errors for Class.forName
            e.printStackTrace();
    }finally{
      //finally block used to close resources
            try{
                if(s!=null)
                    conn.close();
            }catch(SQLException se){
                }// do nothing
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
      }//end finally try
    }
        return result;
  }
    private String selectedName;
  
    public static Map selectFirst(){
    // System.out.println("Checking existing records...");
    Connection conn = null;
    Statement s = null;
    String selectedName = "***Not found***";
    Map <Integer, String> recordMap = new HashMap<Integer, String>();
    int selectedID = 0;
    try{
                
                conn = DriverManager.getConnection(FULL_NAME, USER, PASS);
                s = conn.createStatement();
                String selectQuery = "SELECT * "
                        + "FROM IDTABLE ORDER BY ID LIMIT 1";
                 // String selectQuery = "SELECT Supplier,Name,Balance FROM SUPPLIERS;";
                 ResultSet result = null;
                 s = conn.createStatement();
                 result = s.executeQuery(selectQuery); // execute the SQL query
                 
                 // System.out.printf("%-12s%-20s\n","ID","Name");
                 
                 while (result.next()) { // loop until the end of the results
                    selectedID = result.getInt("ID");
                    selectedName = result.getString("NAME");
                    break;
                   
                    
                    // System.out.printf("%-12d%-20s\n",selectedID,selectedName);
                    }
    }catch(SQLException se){
            // System.out.println(se.getErrorCode());
            se.printStackTrace();
          // se.printStackTrace();
    }catch(Exception e){
      //Handle errors for Class.forName
            e.printStackTrace();
    }finally{
      //finally block used to close resources
            try{
                if(s!=null)
                    conn.close();
            }catch(SQLException se){
                }// do nothing
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
      }//end finally try
    }
    recordMap.put(selectedID, selectedName);
    return recordMap;
  }
  
  public static Map selectNextOrPrevious(int ID, boolean next){
    // System.out.println("Checking existing records...");
    Connection conn = null;
    Statement s = null;
    String selectedName = "***Not found***";
    Map <Integer, String> recordMap = new HashMap<Integer, String>();
    int selectedID = 0;
    try{
                
                conn = DriverManager.getConnection(FULL_NAME, USER, PASS);
                s = conn.createStatement();
                String selectQuery = null;
                if (next){
                    selectQuery = String.format("SELECT * "
                        + "FROM IDTABLE WHERE ID > %d ORDER BY ID LIMIT 1",ID);
                }
                else{
                    selectQuery = String.format("SELECT * "
                        + "FROM IDTABLE WHERE ID < %d ORDER BY ID DESC LIMIT 1",ID);
                }
                
                 // String selectQuery = "SELECT Supplier,Name,Balance FROM SUPPLIERS;";
                 ResultSet result = null;
                 s = conn.createStatement();
                 result = s.executeQuery(selectQuery); // execute the SQL query
                 
                 // System.out.printf("%-12s%-20s\n","ID","Name");
                 
                 while (result.next()) { // loop until the end of the results
                    selectedID = result.getInt("ID");
                    selectedName = result.getString("NAME");
                    break;
                   
                    
                    // System.out.printf("%-12d%-20s\n",selectedID,selectedName);
                    }
    }catch(SQLException se){
            // System.out.println(se.getErrorCode());
            se.printStackTrace();
          // se.printStackTrace();
    }catch(Exception e){
      //Handle errors for Class.forName
            e.printStackTrace();
    }finally{
      //finally block used to close resources
            try{
                if(s!=null)
                    conn.close();
            }catch(SQLException se){
                }// do nothing
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
      }//end finally try
    }
    recordMap.put(selectedID, selectedName);
    return recordMap;
  }  
  
  public static int deleteData(int ID) {
    // System.out.println("Updating records...");
    Connection conn = null;
    Statement s = null;
    int result = -1;
    try{
                conn = DriverManager.getConnection(FULL_NAME, USER, PASS);
                s = conn.createStatement();
                // String stringID = Integer.toString(ID);
                String query = String.format("DELETE FROM IDTABLE where ID = %d",ID);
                result = s.executeUpdate(query);
                // System.out.println(result);
                // System.out.println("Updated data...");
                
    }catch(SQLException se){
            System.out.println(se.getErrorCode());
//            if(se.getErrorCode() == MYSQL_DUPLICATE_PK ){
//                    result = 0;
//            }
          // se.printStackTrace();
    }catch(Exception e){
      //Handle errors for Class.forName
            e.printStackTrace();
    }finally{
      //finally block used to close resources
            try{
                if(s!=null)
                    conn.close();
            }catch(SQLException se){
                }// do nothing
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
      }//end finally try
    }
        return result;
  }
  
}
