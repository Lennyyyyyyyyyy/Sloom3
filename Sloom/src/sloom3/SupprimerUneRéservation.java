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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class SupprimerUneRéservation extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JComboBox<String> comboBoxIdReservation;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                SupprimerUneRéservation frame = new SupprimerUneRéservation();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public SupprimerUneRéservation() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 150);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblIdReservation = new JLabel("Identifiant de la réservation");
        lblIdReservation.setFont(new Font("Arial", Font.PLAIN, 12));
        lblIdReservation.setBounds(30, 25, 200, 30);
        contentPane.add(lblIdReservation);

        comboBoxIdReservation = new JComboBox<>();
        comboBoxIdReservation.setBackground(Color.WHITE);
        comboBoxIdReservation.setFont(new Font("Arial", Font.PLAIN, 12));
        comboBoxIdReservation.setBounds(30, 55, 150, 25);
        contentPane.add(comboBoxIdReservation);

        JButton btnSupprimer = new JButton("Supprimer");
        btnSupprimer.setForeground(Color.WHITE);
        btnSupprimer.setBackground(Color.BLACK);
        btnSupprimer.setFont(new Font("Arial", Font.BOLD, 12));
        btnSupprimer.setBounds(230, 55, 150, 25);
        btnSupprimer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                supprimerReservation();
            }
        });
        contentPane.add(btnSupprimer);
        
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
        
        chargerReservations();
    }

    private void chargerReservations() {
        Connection conn = ConnexionBaseDeDonnees.getConnection();
        if (conn != null) {
            try {
                String selectQuery = "SELECT id FROM Reservation";
                PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
                ResultSet rs = selectStmt.executeQuery();

                while (rs.next()) {
                    comboBoxIdReservation.addItem(rs.getString("id"));
                }

                rs.close();
                selectStmt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void supprimerReservation() {
        String idReservation = (String) comboBoxIdReservation.getSelectedItem();

        Connection conn = ConnexionBaseDeDonnees.getConnection();
        if (conn != null) {
            try {
            	
                String deleteQuery = "DELETE FROM Reservation WHERE id = ?";
                PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);
                deleteStmt.setString(1, idReservation);
                int rowsDeleted = deleteStmt.executeUpdate();
                deleteStmt.close();
                conn.close();

                if (rowsDeleted > 0) {
                    comboBoxIdReservation.removeItem(idReservation);
                    JOptionPane.showMessageDialog(this, "Réservation supprimée avec succès.");
                } else {
                    JOptionPane.showMessageDialog(this, "Aucune réservation trouvée avec cet identifiant.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
