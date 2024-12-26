package sloom3;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class AfficherDemandesEnRetard extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable tableReservations;
    private DefaultTableModel tableModel;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AfficherDemandesEnRetard frame = new AfficherDemandesEnRetard();
                frame.setVisible(true);
                frame.afficherDemandesEnRetard(); 
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public AfficherDemandesEnRetard() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 400);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        tableModel = new DefaultTableModel(
            new Object[]{"id", "prénom", "nom", "espace", "dateReser", "montant"}, 0);
        tableReservations = new JTable(tableModel);
        tableReservations.setBackground(Color.WHITE);
        tableReservations.setFont(new Font("Arial", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(tableReservations);
        scrollPane.setBounds(30, 30, 620, 300);
        contentPane.add(scrollPane);
        
        tableReservations.setEnabled(false);
        
        JButton btnRetour = new JButton("←");
        btnRetour.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		dispose ();
                MenuPrincipal frameReservations = new MenuPrincipal();
                frameReservations.setVisible(true);  
        	}
        });
        btnRetour.setForeground(Color.WHITE);
        btnRetour.setFont(new Font("Arial", Font.BOLD, 11));
        btnRetour.setBackground(Color.BLACK);
        btnRetour.setBounds(0, 0, 45, 15);
        contentPane.add(btnRetour);
    }

    public void afficherDemandesEnRetard() {
        tableModel.setRowCount(0);

        Connection conn = ConnexionBaseDeDonnees.getConnection();
        if (conn != null) {
            try {
                String selectQuery = """
                    SELECT Reservation.id, 
                           Utilisateur.prenomUtil, Utilisateur.nomUtil, 
                           Espace.nomEspace, 
                           Reservation.dateReser, 
                           Reservation.montantReser
                    FROM Reservation
                    JOIN Utilisateur ON Reservation.idUtil = Utilisateur.id
                    JOIN Espace ON Reservation.idEspace = Espace.id
                    WHERE Reservation.idEtat = 1 
                    AND DATEDIFF(CURRENT_DATE, Reservation.dateReser) > 8""";

                PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
                ResultSet rs = selectStmt.executeQuery();

                while (rs.next()) {
                    tableModel.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("prenomUtil"),
                        rs.getString("nomUtil"),
                        rs.getString("nomEspace"),
                        rs.getTimestamp("dateReser"),
                        rs.getDouble("montantReser")
                    });
                }

                rs.close();
                selectStmt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
