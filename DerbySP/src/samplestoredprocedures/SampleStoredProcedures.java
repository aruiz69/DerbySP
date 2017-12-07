/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package samplestoredprocedures;

import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author alvaro.ruiz
 */
public class SampleStoredProcedures {

    public static void main(String[] args) throws SQLException, MalformedURLException, ClassNotFoundException {
        String[] names = new String[1];

        getCustomerLastNameById(1, names);
        if (names.length > 0) {
            System.out.println("LastName = " + names[0]);
        }
    }

    public static void getCustomerLastNameById(long customerId /* IN paramater */,
            String[] customerLastName /* OUT paramater */) throws SQLException, MalformedURLException, ClassNotFoundException {

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet res = null;
        // default string 
        String urlConn = "jdbc:default:connection";
//        String urlConn = "jdbc:derby://localhost:1527/Employee1";
//        String user = "user1";
//        String pwd = "user1";        
        try {
            //Class.forName("org.apache.derby.jdbc.ClientDriver");
            conn = DriverManager.getConnection(urlConn); //,user,pwd);

            // prepare the query
            String sql = "SELECT LASTNAME FROM CUSTOMER WHERE ID = ?";
            stmt = conn.prepareStatement(sql);
            // bind parameters
            stmt.setLong(1, customerId);
            res = stmt.executeQuery();

            // set the result in OUT parameter
            // IMPORTANT: Notice that we never instantiate the customerLastName array.
            // The array is instead initialized and passed in by Derby, our SQL/JRT implementor
            customerLastName[0] = (res.next()) ? res.getString(1) : "Customer not Found.";
        } finally {
            if (res != null) {
                // close the result set
                res.close();
            }

            if (stmt != null) {
                // close the statement
                stmt.close();
            }

            if (conn != null) {
                // close the db connection
                conn.close();
            }
        }
    }
}
