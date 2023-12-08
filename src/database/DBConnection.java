package database;

import json.JSONFile;

import java.util.Map;

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
    public static boolean connEstablished = false;
    private static boolean _retryConn = false;

    public static void init() {
        Map<String, Object> map = JSONFile.toMap();

        DB_SERVER_URL = (String) map.get("db_srv_url");
        DB_USERNAME = (String) map.get("db_usr");
        DB_PASSWORD = (String) map.get("db_pw");
        DB_NAME = ((String) map.get("house_name"))
            .replaceAll(" ", "_") + "_db";

        do {
            try (Connection conn = DriverManager.getConnection(_driver + DB_SERVER_URL + "/" + DB_NAME, 
            DB_USERNAME, DB_PASSWORD)) {
                _retryConn = false;
                connEstablished = true;
            }
            // Handler for unknown host
            catch (CommunicationsException a) {
                connEstablished = false;
            }
            catch (SQLException s) {
                switch (s.getErrorCode()) {
                    case (MysqlErrorNumbers.ER_BAD_DB_ERROR):
                        try (Connection tempConn = DriverManager.getConnection(_driver + DB_SERVER_URL, 
                            DB_USERNAME, DB_PASSWORD); Statement tempStm = tempConn.createStatement();) {
                            tempStm.executeUpdate(String.format("CREATE DATABASE `%s`", DB_NAME));

                            _retryConn = true;
                        }
                        // Handler for conenction error
                        catch (SQLException b) {
                            b.printStackTrace();
                            System.exit(0);
                        }

                        break;
                    
                    default:
                        s.printStackTrace();
                        System.exit(0);
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                System.exit(0);
            }
        } while (_retryConn);
    }
}