package sloom3;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class SeConnecter extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFieldIdentifiant;
    private JPasswordField passwordField;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                SeConnecter frame = new SeConnecter();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public SeConnecter() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 280);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblIdentifiant = new JLabel("Identifiant");
        lblIdentifiant.setFont(new Font("Arial", Font.PLAIN, 12));
        lblIdentifiant.setBounds(130, 25, 130, 25);
        contentPane.add(lblIdentifiant);

        textFieldIdentifiant = new JTextField();
        textFieldIdentifiant.setBounds(130, 55, 180, 25);
        contentPane.add(textFieldIdentifiant);
        textFieldIdentifiant.setColumns(10);

        JLabel lblMotDePasse = new JLabel("Mot de passe");
        lblMotDePasse.setFont(new Font("Arial", Font.PLAIN, 12));
        lblMotDePasse.setBounds(130, 90, 130, 25);
        contentPane.add(lblMotDePasse);

        passwordField = new JPasswordField();
        passwordField.setBounds(130, 120, 180, 25);
        contentPane.add(passwordField);

        JButton btnConnexion = new JButton("Se connecter");
        btnConnexion.setForeground(Color.WHITE);
        btnConnexion.setBackground(Color.BLACK);
        btnConnexion.setFont(new Font("Arial", Font.BOLD, 12));
        btnConnexion.setBounds(130, 165, 130, 25);
        btnConnexion.addActionListener(this::connexion);
        contentPane.add(btnConnexion);
    }

    private void connexion(ActionEvent e) {
        String identifiant = textFieldIdentifiant.getText().trim();
        char[] motDePasse = passwordField.getPassword();

        if (identifiant.isEmpty() || motDePasse.length == 0) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer votre identifiant et votre mot de passe.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = ConnexionBaseDeDonnees.getConnection()) {
            String sql = "SELECT identifiantUtil, mdpUtil FROM Utilisateur WHERE identifiantUtil = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, identifiant);
            ResultSet rs = stmt.executeQuery();

            if (rs.next() && rs.getString("mdpUtil").equals(String.valueOf(motDePasse))) {
                JOptionPane.showMessageDialog(this, "Connexion réussie!", "Succès", JOptionPane.INFORMATION_MESSAGE);
                new MenuPrincipal().setVisible(true); 
                dispose(); 
            } else {
                JOptionPane.showMessageDialog(this, "Identifiant ou mot de passe incorrect.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur de connexion à la base de données.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}


