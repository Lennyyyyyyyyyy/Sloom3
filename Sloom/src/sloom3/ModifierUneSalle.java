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
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ModifierUneSalle extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFieldNomEspace;
    private JTextField textFieldSuperfEspace;
    private JTextField textFieldCapaciteAcc;
    private JComboBox<String> comboBoxDispo;
    private JComboBox<String> comboBoxIdEspace;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ModifierUneSalle frame = new ModifierUneSalle();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ModifierUneSalle() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 305);
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
        comboBoxIdEspace.setFont(new Font("Arial", Font.PLAIN, 11));
        comboBoxIdEspace.setBackground(new Color(255, 255, 255));
        comboBoxIdEspace.setBounds(30, 55, 150, 25);
        contentPane.add(comboBoxIdEspace);
        comboBoxIdEspace.addActionListener(e -> remplirChamps());

        JLabel lblNomEspace = new JLabel("Nom de l'espace");
        lblNomEspace.setFont(new Font("Arial", Font.PLAIN, 12));
        lblNomEspace.setBounds(230, 25, 150, 30);
        contentPane.add(lblNomEspace);

        textFieldNomEspace = new JTextField();
        textFieldNomEspace.setFont(new Font("Arial", Font.PLAIN, 11));
        textFieldNomEspace.setBounds(230, 55, 150, 25);
        contentPane.add(textFieldNomEspace);

        JLabel lblSuperfEspace = new JLabel("Superficie de l'espace");
        lblSuperfEspace.setFont(new Font("Arial", Font.PLAIN, 12));
        lblSuperfEspace.setBounds(30, 85, 150, 30);
        contentPane.add(lblSuperfEspace);

        textFieldSuperfEspace = new JTextField();
        textFieldSuperfEspace.setFont(new Font("Arial", Font.PLAIN, 11));
        textFieldSuperfEspace.setBounds(30, 115, 150, 25);
        contentPane.add(textFieldSuperfEspace);

        JLabel lblDispo = new JLabel("Disponibilité");
        lblDispo.setFont(new Font("Arial", Font.PLAIN, 12));
        lblDispo.setBounds(230, 85, 150, 30);
        contentPane.add(lblDispo);

        comboBoxDispo = new JComboBox<>(new String[]{"Oui", "Non"});
        comboBoxDispo.setFont(new Font("Arial", Font.PLAIN, 11));
        comboBoxDispo.setBackground(new Color(255, 255, 255));
        comboBoxDispo.setBounds(230, 115, 150, 25);
        contentPane.add(comboBoxDispo);

        JLabel lblCapaciteAcc = new JLabel("Capacité d'accueil");
        lblCapaciteAcc.setFont(new Font("Arial", Font.PLAIN, 12));
        lblCapaciteAcc.setBounds(30, 145, 350, 30);
        contentPane.add(lblCapaciteAcc);

        textFieldCapaciteAcc = new JTextField();
        textFieldCapaciteAcc.setFont(new Font("Arial", Font.PLAIN, 11));
        textFieldCapaciteAcc.setBounds(30, 175, 350, 25);
        contentPane.add(textFieldCapaciteAcc);

        JButton btnModifier = new JButton("Modifier");
        btnModifier.setForeground(new Color(255, 255, 255));
        btnModifier.setBackground(new Color(0, 0, 0));
        btnModifier.setFont(new Font("Arial", Font.BOLD, 12));
        btnModifier.setBounds(150, 220, 130, 25);
        btnModifier.addActionListener(e -> modifierSalle());
        contentPane.add(btnModifier);
        
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

        chargerIdentifiants();
    }

    private void chargerIdentifiants() {
        Connection conn = ConnexionBaseDeDonnees.getConnection();
        if (conn != null) {
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT id FROM Espace")) {
                while (rs.next()) {
                    comboBoxIdEspace.addItem(rs.getString("id"));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void remplirChamps() {
        String idEspace = (String) comboBoxIdEspace.getSelectedItem();
        Connection conn = ConnexionBaseDeDonnees.getConnection();
        if (conn != null && idEspace != null) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT nomEspace, superfEspace, dispo, capaciteAcc FROM Espace WHERE id = ?")) {
                stmt.setString(1, idEspace);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    textFieldNomEspace.setText(rs.getString("nomEspace"));
                    textFieldSuperfEspace.setText(rs.getString("superfEspace"));
                    comboBoxDispo.setSelectedItem(rs.getString("dispo"));
                    textFieldCapaciteAcc.setText(rs.getString("capaciteAcc"));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void modifierSalle() {
        String idEspace = (String) comboBoxIdEspace.getSelectedItem();
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
            try (PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE Espace SET nomEspace = ?, superfEspace = ?, dispo = ?, capaciteAcc = ? WHERE id = ?")) {
                stmt.setString(1, nomEspace);
                stmt.setString(2, superfEspace);
                stmt.setString(3, dispoEspace);
                stmt.setString(4, capaciteAcc);
                stmt.setString(5, idEspace);

                if (stmt.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(this, "Salle modifiée avec succès.");
                    remplirChamps();
                } 
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
} 