/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bdd;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class bddConnection {

    Properties properties = new Properties();
    InputStream inputStream;

    public Connection con;

    String url;
    String user;
    String pass;
    String unicode = "?useUnicode=yes&characterEncoding=UTF-8";

    public void loadPropertiesFile() {
        try {
            inputStream = new FileInputStream("bdd.config");
            properties.load(inputStream);
            url = "jdbc:mysql://" + properties.getProperty("host") + ":" + properties.getProperty("port") + "/";
            user = properties.getProperty("user");
            pass = properties.getProperty("password");
        } catch (IOException e) {
            System.out.println("erreur sur le fichier");
        }
    }

    public Connection mkDataBase() throws SQLException {
               
        loadPropertiesFile();
              
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, pass);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(bddConnection.class.getName()).log(Level.SEVERE, null, ex);

        }
        return con;
    }

    public Connection geConnection() {
        loadPropertiesFile();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url + unicode, user, pass);

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(bddConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }
}
