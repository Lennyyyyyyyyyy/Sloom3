package sloom3;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class CréerUneSalle extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFieldNomEspace;
    private JTextField textFieldSuperfEspace;
    private JTextField textFieldCapaciteAcc;
    private JComboBox<String> comboBoxDispo;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CréerUneSalle frame = new CréerUneSalle();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public CréerUneSalle() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 365);
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        textFieldNomEspace = new JTextField();
        textFieldNomEspace.setFont(new Font("Arial", Font.PLAIN, 11));
        textFieldNomEspace.setBackground(new Color(255, 255, 255));
        textFieldNomEspace.setBounds(30, 55, 380, 25);
        contentPane.add(textFieldNomEspace);
        textFieldNomEspace.setColumns(10);

        JLabel lblNomEspace = new JLabel("Nom de l'espace");
        lblNomEspace.setFont(new Font("Arial", Font.PLAIN, 12));
        lblNomEspace.setBounds(30, 25, 130, 30);
        contentPane.add(lblNomEspace);

        textFieldSuperfEspace = new JTextField();
        textFieldSuperfEspace.setFont(new Font("Arial", Font.PLAIN, 11));
        textFieldSuperfEspace.setColumns(10);
        textFieldSuperfEspace.setBackground(Color.WHITE);
        textFieldSuperfEspace.setBounds(30, 115, 380, 25);
        contentPane.add(textFieldSuperfEspace);

        JLabel lblSuperfEspace = new JLabel("Superficie de l'espace");
        lblSuperfEspace.setFont(new Font("Arial", Font.PLAIN, 12));
        lblSuperfEspace.setBounds(30, 85, 130, 30);
        contentPane.add(lblSuperfEspace);

        JLabel lblDispo = new JLabel("Disponibilité");
        lblDispo.setFont(new Font("Arial", Font.PLAIN, 12));
        lblDispo.setBounds(30, 145, 130, 30);
        contentPane.add(lblDispo);

        comboBoxDispo = new JComboBox<>(new String[] {"Oui", "Non"});
        comboBoxDispo.setBackground(new Color(255, 255, 255));
        comboBoxDispo.setFont(new Font("Arial", Font.PLAIN, 11));
        comboBoxDispo.setBounds(30, 175, 380, 25);
        contentPane.add(comboBoxDispo);

        JLabel lblCapaciteAcc = new JLabel("Capacité d'accueil");
        lblCapaciteAcc.setFont(new Font("Arial", Font.PLAIN, 12));
        lblCapaciteAcc.setBounds(30, 205, 130, 30);
        contentPane.add(lblCapaciteAcc);

        textFieldCapaciteAcc = new JTextField();
        textFieldCapaciteAcc.setFont(new Font("Arial", Font.PLAIN, 11));
        textFieldCapaciteAcc.setColumns(10);
        textFieldCapaciteAcc.setBackground(Color.WHITE);
        textFieldCapaciteAcc.setBounds(30, 235, 380, 25);
        contentPane.add(textFieldCapaciteAcc);

        JButton btnAjouter = new JButton("Ajouter");
        btnAjouter.setForeground(new Color(255, 255, 255));
        btnAjouter.setBackground(new Color(0, 0, 0));
        btnAjouter.setFont(new Font("Arial", Font.BOLD, 12));
        btnAjouter.setBounds(150, 280, 130, 25);
        btnAjouter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ajouterSalle();
            }
        });
        contentPane.add(btnAjouter);
        
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

    private void ajouterSalle() {
        String nomEspace = textFieldNomEspace.getText().trim();
        String superfEspace = textFieldSuperfEspace.getText().trim();
        String dispoEspace = (String) comboBoxDispo.getSelectedItem();
        String capaciteAcc = textFieldCapaciteAcc.getText().trim();

        if (nomEspace.isEmpty() || superfEspace.isEmpty() || dispoEspace.isEmpty() || capaciteAcc.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.");
            return;
        }

        Connection conn = ConnexionBaseDeDonnees.getConnection();
        if (conn != null) {
            try {
                String insertQuery = "INSERT INTO Espace (nomEspace, superfEspace, dispo, capaciteAcc) VALUES (?, ?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                insertStmt.setString(1, nomEspace);
                insertStmt.setString(2, superfEspace);
                insertStmt.setString(3, dispoEspace);
                insertStmt.setString(4, capaciteAcc);
                insertStmt.executeUpdate();
                insertStmt.close();
                conn.close();

                textFieldNomEspace.setText("");
                textFieldSuperfEspace.setText("");
                comboBoxDispo.setSelectedIndex(0);
                textFieldCapaciteAcc.setText("");

                JOptionPane.showMessageDialog(this, "Salle ajoutée avec succès.");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
