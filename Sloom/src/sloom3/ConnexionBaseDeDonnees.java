package sloom3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionBaseDeDonnees {

    public static Connection getConnection() {
        try {
            
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/sloom", "root", "");
        } catch (SQLException e) {
            System.out.println("Problème de connexion à la base de données fichier ConnexionBase");
            e.printStackTrace();
            return null; 
        }
    }
}
