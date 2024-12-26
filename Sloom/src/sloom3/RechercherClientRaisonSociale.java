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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class RechercherClientRaisonSociale extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JComboBox<String> comboBoxRaisonSociale;
    private JTable tableClients;
    private DefaultTableModel tableModel;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                RechercherClientRaisonSociale frame = new RechercherClientRaisonSociale();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public RechercherClientRaisonSociale() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 400);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblRaisonSociale = new JLabel("Raison Sociale");
        lblRaisonSociale.setFont(new Font("Arial", Font.PLAIN, 12));
        lblRaisonSociale.setBounds(30, 30, 100, 25);
        contentPane.add(lblRaisonSociale);

        comboBoxRaisonSociale = new JComboBox<>();
        comboBoxRaisonSociale.setBackground(new Color(255, 255, 255));
        comboBoxRaisonSociale.setFont(new Font("Arial", Font.PLAIN, 12));
        comboBoxRaisonSociale.setBounds(130, 30, 130, 25);
        contentPane.add(comboBoxRaisonSociale);

        JButton btnRechercher = new JButton("Rechercher");
        btnRechercher.setForeground(Color.WHITE);
        btnRechercher.setBackground(Color.BLACK);
        btnRechercher.setFont(new Font("Arial", Font.BOLD, 12));
        btnRechercher.setBounds(520, 30, 130, 25);
        btnRechercher.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rechercherClients();
            }
        });
        contentPane.add(btnRechercher);

        tableModel = new DefaultTableModel(
                new Object[]{"id", "nom", "prénom", "raison sociale", "e-mail", "téléphone"}, 0);
        tableClients = new JTable(tableModel);
        tableClients.setFont(new Font("Arial", Font.PLAIN, 11));
        JScrollPane scrollPane = new JScrollPane(tableClients);
        scrollPane.setBounds(30, 75, 620, 250);
        contentPane.add(scrollPane);
        
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

        chargerRaisonsSociales();
    }

    private void chargerRaisonsSociales() {
        Connection conn = ConnexionBaseDeDonnees.getConnection();
        if (conn != null) {
            try {
                String selectQuery = "SELECT DISTINCT raisonSociale FROM Utilisateur ORDER BY raisonSociale ASC";
                PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
                ResultSet rs = selectStmt.executeQuery();
                while (rs.next()) {
                    comboBoxRaisonSociale.addItem(rs.getString("raisonSociale"));
                }
                rs.close();
                selectStmt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void rechercherClients() {
        String raisonSociale = (String) comboBoxRaisonSociale.getSelectedItem();
        tableModel.setRowCount(0);

        if (raisonSociale == null || raisonSociale.isEmpty()) {
            return;
        }

        Connection conn = ConnexionBaseDeDonnees.getConnection();
        if (conn != null) {
            try {
                String selectQuery = "SELECT id, nomUtil, prenomUtil, raisonSociale, mailUtil, telUtil "
                        + "FROM Utilisateur WHERE raisonSociale = ? ORDER BY nomUtil ASC";
                PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
                selectStmt.setString(1, raisonSociale);
                ResultSet rs = selectStmt.executeQuery();

                while (rs.next()) {
                    tableModel.addRow(new Object[]{
                        rs.getString("id"),
                        rs.getString("nomUtil"),
                        rs.getString("prenomUtil"),
                        rs.getString("raisonSociale"),
                        rs.getString("mailUtil"),
                        rs.getString("telUtil")
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
