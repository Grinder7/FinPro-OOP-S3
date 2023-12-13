package database;

import java.util.Map;

import json.JSONFile;

// MySQL connector lib
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import com.mysql.cj.exceptions.MysqlErrorNumbers;
import com.mysql.cj.jdbc.exceptions.CommunicationsException;

public class DBConnection {
    private static String DB_SERVER_URL;
    private static String DB_USERNAME;
    private static String DB_PASSWORD;
    private static String DB_NAME;
    private static String _driver = "jdbc:mysql://";
    private static boolean _connEstablished = false;
    private static boolean _retryConn = false;
    private static Connection _conn = null;

    public static void init() {
        Map<String, Object> map = JSONFile.toMap();

        DB_SERVER_URL = (String) map.get("db_srv_url");
        DB_USERNAME = (String) map.get("db_usr");
        DB_PASSWORD = (String) map.get("db_pw");
        DB_NAME = ((String) map.get("house_name"))
                .replaceAll(" ", "_") + "_db";

        do {
            try {
                // Create connection
                _conn = DriverManager.getConnection(_driver + DB_SERVER_URL + "/" + DB_NAME,
                        DB_USERNAME, DB_PASSWORD);

                _retryConn = false;
                _connEstablished = true;
            }
            // Handler for unknown host
            catch (CommunicationsException a) {
                _connEstablished = false;
            } catch (SQLException s) {
                switch (s.getErrorCode()) {
                    case (MysqlErrorNumbers.ER_BAD_DB_ERROR):
                        try (Connection tempConn = DriverManager.getConnection(_driver + DB_SERVER_URL,
                                DB_USERNAME, DB_PASSWORD); Statement stmt = tempConn.createStatement();) {
                            // Create database
                            stmt.executeUpdate(String.format("CREATE DATABASE `%s`;", DB_NAME));
                            stmt.executeUpdate(String.format("USE `%s`;", DB_NAME));

                            DBFactories.setStatement(stmt);

                            // Create table(s)
                            DBFactories.createPatientTable();
                            DBFactories.createCaretakerTable();
                            DBFactories.createSupplyTable();
                            DBFactories.createDonationTable();

                            _retryConn = true;
                        }
                        // Handler for connection error
                        catch (SQLException b) {
                            b.printStackTrace();
                            System.exit(0);
                        }

                        break;

                    default:
                        s.printStackTrace();
                        System.exit(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }
        } while (_retryConn);
    }

    public static boolean isEstablished() {
        return _connEstablished;
    }

    public static Connection getConnection() {
        return _conn;
    }
}