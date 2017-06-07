package com.bloomberg.comdb2.jdbc;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.*;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;


/**
 * Created by pdeshpande4 on 6/6/17.
 */

public class UserSpecifiedHostPortTest {
    private final String jdbcClass = "com.bloomberg.system.comdb2.jdbc.Driver";
    @Before
    public void loadDriver()  {
        try {
            Class.forName(jdbcClass);
        } catch ( ClassNotFoundException e ) {
            fail(String.format("Unable to load class -> %s", jdbcClass))    ;
        }
    }

    @Test
    public void runSelectWithLocalHost() {
        Connection conn = null;
        System.out.println("Running with local host");
        try {
            conn = DriverManager.getConnection("jdbc:comdb2://localhost:33200/dststdb");
        } catch (SQLException e ) {
            fail (String.format("Unable to get connection to localhost due %s", e ));
        }

        try {
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery("Select * from clusters");
            assertNotNull(rs);
            ResultSetMetaData rmd = rs.getMetaData();
            while (rs.next()) {
                System.out.println(rs.getString("name") + " - " + rs.getString("cluster_name"));
            }
        } catch (SQLException e ) {
            fail (String.format("Unable to execute select due %s", e ));
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                fail (String.format("Unable to close connection due %s", e ));
            }

        }
    }

    @Test
    public void runSelectWithSpecificHost() {
        Connection conn = null;
        System.out.println("Running with specific host");
        try {
            conn = DriverManager.getConnection("jdbc:comdb2://dststdb.bloomberg.ds.dev:33200/dststdb");
        } catch (SQLException e ) {
            fail (String.format("Unable to get connection to localhost due %s", e ));
        }

        try {
            Statement stmnt = conn.createStatement();
            ResultSet rs = stmnt.executeQuery("Select * from clusters");
            assertNotNull(rs);
            ResultSetMetaData rmd = rs.getMetaData();
            while (rs.next()) {
                System.out.println(rs.getString("name") + " - " + rs.getString("cluster_name"));
            }
        } catch (SQLException e ) {
            fail (String.format("Unable to execute select due %s", e ));
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                fail (String.format("Unable to close connection due %s", e ));
            }

        }
    }


}
