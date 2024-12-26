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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class CréerUnClient extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField textFieldPrenom;
    private JTextField textFieldNom;
    private JTextField textFieldRaisonSociale;
    private JTextField textFieldMail;
    private JTextField textFieldTel;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CréerUnClient frame = new CréerUnClient();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public CréerUnClient() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 430);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblPrenom = new JLabel("Prénom");
        lblPrenom.setFont(new Font("Arial", Font.PLAIN, 12));
        lblPrenom.setBounds(30, 25, 150, 25);
        contentPane.add(lblPrenom);

        textFieldPrenom = new JTextField();
        textFieldPrenom.setFont(new Font("Arial", Font.PLAIN, 11));
        textFieldPrenom.setBounds(30, 55, 400, 25);
        contentPane.add(textFieldPrenom);

        JLabel lblNom = new JLabel("Nom");
        lblNom.setFont(new Font("Arial", Font.PLAIN, 12));
        lblNom.setBounds(30, 85, 150, 25);
        contentPane.add(lblNom);

        textFieldNom = new JTextField();
        textFieldNom.setFont(new Font("Arial", Font.PLAIN, 11));
        textFieldNom.setBounds(30, 115, 400, 25);
        contentPane.add(textFieldNom);

        JLabel lblRaisonSociale = new JLabel("Raison Sociale");
        lblRaisonSociale.setFont(new Font("Arial", Font.PLAIN, 12));
        lblRaisonSociale.setBounds(30, 145, 150, 25);
        contentPane.add(lblRaisonSociale);

        textFieldRaisonSociale = new JTextField();
        textFieldRaisonSociale.setFont(new Font("Arial", Font.PLAIN, 11));
        textFieldRaisonSociale.setBounds(30, 175, 400, 25);
        contentPane.add(textFieldRaisonSociale);

        JLabel lblMail = new JLabel("Email");
        lblMail.setFont(new Font("Arial", Font.PLAIN, 12));
        lblMail.setBounds(30, 205, 150, 25);
        contentPane.add(lblMail);

        textFieldMail = new JTextField();
        textFieldMail.setFont(new Font("Arial", Font.PLAIN, 11));
        textFieldMail.setBounds(30, 235, 400, 25);
        contentPane.add(textFieldMail);

        JLabel lblTel = new JLabel("Téléphone");
        lblTel.setFont(new Font("Arial", Font.PLAIN, 12));
        lblTel.setBounds(30, 265, 150, 25);
        contentPane.add(lblTel);

        textFieldTel = new JTextField();
        textFieldTel.setFont(new Font("Arial", Font.PLAIN, 11));
        textFieldTel.setBounds(30, 295, 400, 25);
        contentPane.add(textFieldTel);

        JButton btnAjouter = new JButton("Ajouter");
        btnAjouter.setFont(new Font("Arial", Font.BOLD, 12));
        btnAjouter.setBounds(180, 340, 120, 25);
        btnAjouter.setBackground(Color.BLACK);
        btnAjouter.setForeground(Color.WHITE);
        btnAjouter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ajouterClient();
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

    private void ajouterClient() {
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
            try {
                String insertQuery = "INSERT INTO Utilisateur (prenomUtil, nomUtil, raisonSociale, mailUtil, telUtil, dateInscUtil, idTypeUtil) VALUES (?, ?, ?, ?, ?, CURRENT_DATE, 2)";
                PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                insertStmt.setString(1, prenom);
                insertStmt.setString(2, nom);
                insertStmt.setString(3, raisonSociale);
                insertStmt.setString(4, mail);
                insertStmt.setString(5, tel);
                insertStmt.executeUpdate();
                insertStmt.close();
                conn.close();

                textFieldPrenom.setText("");
                textFieldNom.setText("");
                textFieldRaisonSociale.setText("");
                textFieldMail.setText("");
                textFieldTel.setText("");

                JOptionPane.showMessageDialog(this, "Client ajouté avec succès.");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
