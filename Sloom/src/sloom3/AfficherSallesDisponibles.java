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

public class AfficherSallesDisponibles extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JComboBox<String> comboBoxCapacite;
    private JTable tableSalles;
    private DefaultTableModel tableModel;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                AfficherSallesDisponibles frame = new AfficherSallesDisponibles();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public AfficherSallesDisponibles() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 600, 400);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblCapacite = new JLabel("Filtrer par capacité :");
        lblCapacite.setFont(new Font("Arial", Font.PLAIN, 12));
        lblCapacite.setBounds(30, 30, 120, 25);
        contentPane.add(lblCapacite);

        comboBoxCapacite = new JComboBox<>();
        comboBoxCapacite.setBackground(Color.WHITE);
        comboBoxCapacite.setFont(new Font("Arial", Font.PLAIN, 11));
        comboBoxCapacite.setBounds(145, 30, 130, 25);
        contentPane.add(comboBoxCapacite);

        tableModel = new DefaultTableModel(new Object[]{"id", "espace", "superficie"}, 0);
        tableSalles = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableSalles);
        scrollPane.setBounds(30, 75, 520, 250);
        contentPane.add(scrollPane);

        chargerCapacites();

        comboBoxCapacite.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                afficherSalles();  
            }
        });
        
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

    private void chargerCapacites() {
        Connection conn = ConnexionBaseDeDonnees.getConnection();
        if (conn != null) {
            try {
                String selectQuery = "SELECT DISTINCT capaciteAcc FROM Espace ORDER BY capaciteAcc ASC";
                PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
                ResultSet rs = selectStmt.executeQuery();
                while (rs.next()) {
                    comboBoxCapacite.addItem(rs.getString("capaciteAcc"));
                }
                rs.close();
                selectStmt.close();
                conn.close();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void afficherSalles() {
        String capaciteSelectionnee = (String) comboBoxCapacite.getSelectedItem();
        tableModel.setRowCount(0); 

        Connection conn = ConnexionBaseDeDonnees.getConnection();
        if (conn != null && capaciteSelectionnee != null) {
            try {
               
                String selectQuery = "SELECT id, nomEspace, superfEspace FROM Espace WHERE capaciteAcc = ? AND dispo = 'Oui'";
                PreparedStatement selectStmt = conn.prepareStatement(selectQuery);
                selectStmt.setString(1, capaciteSelectionnee);
                ResultSet rs = selectStmt.executeQuery();
                while (rs.next()) {
                    
                    tableModel.addRow(new Object[]{
                        rs.getString("id"),
                        rs.getString("nomEspace"),
                        rs.getString("superfEspace")
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
