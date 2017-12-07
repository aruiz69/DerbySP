/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Types;

/**
 *
 * @author alvaro.ruiz
 */
public class JavaApplication1 {

    /**
     * @param args the command line arguments
     */
    public static void main (String[] args) throws Exception {
        Connection conn = null;
        CallableStatement stmt = null;
        try {
            // Connect to the database
            conn = DriverManager
                    .getConnection("jdbc:derby://localhost:1527/Employee1","user1","user1");
             
            // Create the CALL statement
            stmt = conn.prepareCall("CALL GETCUSTOMERLASTNAME( ?, ? )");
            // Bind the customer id to the first parameter
            stmt.setLong(1, 5);
            // Register the second parameter as an OUT parameter
            stmt.registerOutParameter(2, Types.VARCHAR);
 
            stmt.execute();
 
            // Print result in the OUT parameter - e.g. the customer's last name
            System.out.println(stmt.getString(2));
        } finally {
            // Release db resources
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
    }
}

