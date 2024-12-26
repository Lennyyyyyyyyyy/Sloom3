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

public class SupprimerUnTarif extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JComboBox<String> comboBoxIdEspace;
    private JComboBox<String> comboBoxNumTarif;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                SupprimerUnTarif frame = new SupprimerUnTarif();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public SupprimerUnTarif() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 200);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblIdEspace = new JLabel("Identifiant de l'espace");
        lblIdEspace.setFont(new Font("Arial", Font.PLAIN, 12));
        lblIdEspace.setBounds(30, 25, 150, 30);
        contentPane.add(lblIdEspace);

        comboBoxIdEspace = new JComboBox<>();
        comboBoxIdEspace.setBackground(Color.WHITE);
        comboBoxIdEspace.setFont(new Font("Arial", Font.PLAIN, 12));
        comboBoxIdEspace.setBounds(180, 25, 150, 25);
        contentPane.add(comboBoxIdEspace);

        JLabel lblNumTarif = new JLabel("Numéro du tarif");
        lblNumTarif.setFont(new Font("Arial", Font.PLAIN, 12));
        lblNumTarif.setBounds(30, 65, 150, 30);
        contentPane.add(lblNumTarif);

        comboBoxNumTarif = new JComboBox<>();
        comboBoxNumTarif.setBackground(Color.WHITE);
        comboBoxNumTarif.setFont(new Font("Arial", Font.PLAIN, 12));
        comboBoxNumTarif.setBounds(180, 65, 150, 25);
        contentPane.add(comboBoxNumTarif);

        JButton btnSupprimer = new JButton("Supprimer");
        btnSupprimer.setForeground(Color.WHITE);
        btnSupprimer.setBackground(Color.BLACK);
        btnSupprimer.setFont(new Font("Arial", Font.BOLD, 12));
        btnSupprimer.setBounds(180, 110, 150, 25);
        btnSupprimer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                supprimerTarif();
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

        chargerEspaces();

        comboBoxIdEspace.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                chargerTarifs();
            }
        });
    }

    private void chargerEspaces() {
        Connection conn = ConnexionBaseDeDonnees.getConnection();
        if (conn != null) {
            try {
            	
                String selectQuery = "SELECT id FROM Espace";
                PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
                ResultSet rs = selectStmt.executeQuery();

                while (rs.next()) {
                    comboBoxIdEspace.addItem(rs.getString("id"));
                }

                rs.close();
                selectStmt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void chargerTarifs() {
        String idEspace = (String) comboBoxIdEspace.getSelectedItem();

        if (idEspace != null) {
            Connection conn = ConnexionBaseDeDonnees.getConnection();
            if (conn != null) {
                try {
                	
                    String selectQuery = "SELECT numTarif FROM Tarif WHERE idEspace = ?";
                    PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
                    selectStmt.setString(1, idEspace);
                    ResultSet rs = selectStmt.executeQuery();

                    comboBoxNumTarif.removeAllItems(); 

                    while (rs.next()) {
                        comboBoxNumTarif.addItem(rs.getString("numTarif"));
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

    private void supprimerTarif() {
        String idEspace = (String) comboBoxIdEspace.getSelectedItem();
        String numTarif = (String) comboBoxNumTarif.getSelectedItem();

        Connection conn = ConnexionBaseDeDonnees.getConnection();
        if (conn != null) {
            try {
                String deleteQuery = "DELETE FROM Tarif WHERE idEspace = ? AND numTarif = ?";
                PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);
                deleteStmt.setString(1, idEspace);
                deleteStmt.setString(2, numTarif);
                int rowsDeleted = deleteStmt.executeUpdate();
                deleteStmt.close();
                conn.close();

                if (rowsDeleted > 0) {
                    comboBoxNumTarif.removeItem(numTarif);
                    JOptionPane.showMessageDialog(this, "Tarif supprimé avec succès.");
                } 
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
