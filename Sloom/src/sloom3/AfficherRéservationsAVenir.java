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
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class AfficherRéservationsAVenir extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JComboBox<String> comboBoxSalles;
    private JTable tableReservations;
    private DefaultTableModel tableModel;
    private JTextField textFieldSalle;  

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AfficherRéservationsAVenir frame = new AfficherRéservationsAVenir();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public AfficherRéservationsAVenir() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 700, 500);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblSalle = new JLabel("Identifiant");
        lblSalle.setFont(new Font("Arial", Font.PLAIN, 12));
        lblSalle.setBounds(30, 30, 75, 25);
        contentPane.add(lblSalle);

        comboBoxSalles = new JComboBox<>();
        comboBoxSalles.setBackground(Color.WHITE);
        comboBoxSalles.setFont(new Font("Arial", Font.PLAIN, 11));
        comboBoxSalles.setBounds(100, 30, 200, 25);
        comboBoxSalles.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	nomSalle();
                afficherReservations();
            }
        });
        contentPane.add(comboBoxSalles);

        JLabel lblSalle1 = new JLabel("Nom");
        lblSalle1.setFont(new Font("Arial", Font.PLAIN, 12));
        lblSalle1.setBounds(30, 80, 50, 25);
        contentPane.add(lblSalle1);

        textFieldSalle = new JTextField();
        textFieldSalle.setFont(new Font("Arial", Font.PLAIN, 11));
        textFieldSalle.setBounds(100, 80, 200, 20);
        textFieldSalle.setEditable(false);  
        contentPane.add(textFieldSalle);

        tableModel = new DefaultTableModel(
            new Object[]{"id", "dateHeureDeb", "dateHeureFin", "montant", "idUtil", "état"}, 0);
        tableReservations = new JTable(tableModel);
        tableReservations.setFont(new Font("Arial", Font.PLAIN, 11));
        JScrollPane scrollPane = new JScrollPane(tableReservations);
        scrollPane.setBounds(30, 130, 620, 250);
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

        chargerSalles();
    }

    private void chargerSalles() {
        Connection conn = ConnexionBaseDeDonnees.getConnection();
        if (conn != null) {
            try {
                String selectQuery = "SELECT id, nomEspace FROM Espace ORDER BY nomEspace ASC";
                PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
                ResultSet rs = selectStmt.executeQuery();
                while (rs.next()) {
                    comboBoxSalles.addItem(rs.getString("id"));
                }
                rs.close();
                selectStmt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void nomSalle() {
        String idSalle = (String) comboBoxSalles.getSelectedItem();
        if (idSalle != null && !idSalle.isEmpty()) {
            Connection conn = ConnexionBaseDeDonnees.getConnection();
            if (conn != null) {
                try {
                    String selectQuery = "SELECT nomEspace FROM Espace WHERE id = ?";
                    PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
                    selectStmt.setString(1, idSalle);
                    ResultSet rs = selectStmt.executeQuery();
                    if (rs.next()) {
                        textFieldSalle.setText(rs.getString("nomEspace")); 
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

    private void afficherReservations() {
        String salleSelectionnee = (String) comboBoxSalles.getSelectedItem();
        if (salleSelectionnee == null || salleSelectionnee.isEmpty()) {
            return;
        }
        String idSalle = salleSelectionnee.trim();
        tableModel.setRowCount(0);  

        Connection conn = ConnexionBaseDeDonnees.getConnection();
        if (conn != null) {
            try {
                String selectQuery = """
                	SELECT Reservation.id, 
                	       Reservation.dateHeureDebReser, 
                	       Reservation.dateHeureFinReser, 
                	       Reservation.montantReser, 
                	       Reservation.idUtil, 
                	       EtatReservation.libelleEtat
                	FROM Reservation
                	JOIN EtatReservation ON Reservation.idEtat = EtatReservation.id
                	WHERE Reservation.idEspace = ? 
                	AND Reservation.dateHeureDebReser >= CURRENT_DATE
                	ORDER BY Reservation.dateHeureDebReser ASC""";

                PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
                selectStmt.setString(1, idSalle);
                ResultSet rs = selectStmt.executeQuery();
                while (rs.next()) {
                    tableModel.addRow(new Object[]{
                        rs.getString("id"),
                        rs.getString("dateHeureDebReser"),
                        rs.getString("dateHeureFinReser"),
                        rs.getString("montantReser"),
                        rs.getString("idUtil"),
                        rs.getString("libelleEtat")
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
