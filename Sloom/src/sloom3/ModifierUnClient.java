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

public class ModifierUnClient extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFieldPrenom;
    private JTextField textFieldNom;
    private JTextField textFieldRaisonSociale;
    private JTextField textFieldMail;
    private JTextField textFieldTel;
    private JComboBox<String> comboBoxIdClient;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ModifierUnClient frame = new ModifierUnClient();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ModifierUnClient() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 430);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblIdClient = new JLabel("Identifiant du client");
        lblIdClient.setFont(new Font("Arial", Font.PLAIN, 12));
        lblIdClient.setBounds(30, 25, 150, 30);
        contentPane.add(lblIdClient);

        comboBoxIdClient = new JComboBox<>();
        comboBoxIdClient.setFont(new Font("Arial", Font.PLAIN, 11));
        comboBoxIdClient.setBackground(new Color(255, 255, 255));
        comboBoxIdClient.setBounds(30, 55, 150, 25);
        comboBoxIdClient.addActionListener(e -> remplirChamps());
        contentPane.add(comboBoxIdClient);

        JLabel lblPrenom = new JLabel("Prénom");
        lblPrenom.setFont(new Font("Arial", Font.PLAIN, 12));
        lblPrenom.setBounds(30, 85, 150, 30);
        contentPane.add(lblPrenom);

        textFieldPrenom = new JTextField();
        textFieldPrenom.setFont(new Font("Arial", Font.PLAIN, 11));
        textFieldPrenom.setBounds(30, 115, 150, 25);
        contentPane.add(textFieldPrenom);

        JLabel lblNom = new JLabel("Nom");
        lblNom.setFont(new Font("Arial", Font.PLAIN, 12));
        lblNom.setBounds(230, 85, 150, 30);
        contentPane.add(lblNom);

        textFieldNom = new JTextField();
        textFieldNom.setFont(new Font("Arial", Font.PLAIN, 11));
        textFieldNom.setBounds(230, 115, 150, 25);
        contentPane.add(textFieldNom);

        JLabel lblRaisonSociale = new JLabel("Raison Sociale");
        lblRaisonSociale.setFont(new Font("Arial", Font.PLAIN, 12));
        lblRaisonSociale.setBounds(30, 145, 150, 30);
        contentPane.add(lblRaisonSociale);

        textFieldRaisonSociale = new JTextField();
        textFieldRaisonSociale.setFont(new Font("Arial", Font.PLAIN, 11));
        textFieldRaisonSociale.setBounds(30, 175, 350, 25);
        contentPane.add(textFieldRaisonSociale);

        JLabel lblMail = new JLabel("Email");
        lblMail.setFont(new Font("Arial", Font.PLAIN, 12));
        lblMail.setBounds(30, 205, 150, 30);
        contentPane.add(lblMail);

        textFieldMail = new JTextField();
        textFieldMail.setFont(new Font("Arial", Font.PLAIN, 11));
        textFieldMail.setBounds(30, 235, 350, 25);
        contentPane.add(textFieldMail);

        JLabel lblTel = new JLabel("Téléphone");
        lblTel.setFont(new Font("Arial", Font.PLAIN, 12));
        lblTel.setBounds(30, 265, 150, 30);
        contentPane.add(lblTel);

        textFieldTel = new JTextField();
        textFieldTel.setFont(new Font("Arial", Font.PLAIN, 11));
        textFieldTel.setBounds(30, 295, 350, 25);
        contentPane.add(textFieldTel);

        JButton btnModifier = new JButton("Modifier");
        btnModifier.setForeground(Color.WHITE);
        btnModifier.setBackground(Color.BLACK);
        btnModifier.setFont(new Font("Arial", Font.BOLD, 12));
        btnModifier.setBounds(150, 340, 130, 25);
        btnModifier.addActionListener(e -> modifierClient());
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
                 ResultSet rs = stmt.executeQuery("SELECT id FROM Utilisateur WHERE idTypeUtil = 2")) {
                while (rs.next()) {
                    comboBoxIdClient.addItem(rs.getString("id"));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void remplirChamps() {
        String idClient = (String) comboBoxIdClient.getSelectedItem();
        Connection conn = ConnexionBaseDeDonnees.getConnection();
        if (conn != null && idClient != null) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "SELECT prenomUtil, nomUtil, raisonSociale, mailUtil, telUtil FROM Utilisateur WHERE id = ?")) {
                stmt.setString(1, idClient);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    textFieldPrenom.setText(rs.getString("prenomUtil"));
                    textFieldNom.setText(rs.getString("nomUtil"));
                    textFieldRaisonSociale.setText(rs.getString("raisonSociale"));
                    textFieldMail.setText(rs.getString("mailUtil"));
                    textFieldTel.setText(rs.getString("telUtil"));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void modifierClient() {
        String idClient = (String) comboBoxIdClient.getSelectedItem();
        String prenom = textFieldPrenom.getText().trim();
        String nom = textFieldNom.getText().trim();
        String raisonSociale = textFieldRaisonSociale.getText().trim();
        String mail = textFieldMail.getText().trim();
        String tel = textFieldTel.getText().trim();

        if (prenom.isEmpty() || nom.isEmpty() || mail.isEmpty() || tel.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs obligatoires.");
            return;
        }

        Connection conn = ConnexionBaseDeDonnees.getConnection();
        if (conn != null) {
            try (PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE Utilisateur SET prenomUtil = ?, nomUtil = ?, raisonSociale = ?, mailUtil = ?, telUtil = ? WHERE id = ?")) {
                stmt.setString(1, prenom);
                stmt.setString(2, nom);
                stmt.setString(3, raisonSociale);
                stmt.setString(4, mail);
                stmt.setString(5, tel);
                stmt.setString(6, idClient);

                if (stmt.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(this, "Client modifié avec succès.");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
