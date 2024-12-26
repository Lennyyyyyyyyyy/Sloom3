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

public class CréerUnTarif extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JComboBox<String> comboBoxSalle;
    private JTextField textFieldNbJours;
    private JTextField textFieldPrix;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                CréerUnTarif frame = new CréerUnTarif();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public CréerUnTarif() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 320);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblSalle = new JLabel("Salle");
        lblSalle.setFont(new Font("Arial", Font.PLAIN, 12));
        lblSalle.setBounds(30, 25, 130, 25);
        contentPane.add(lblSalle);

        comboBoxSalle = new JComboBox<>();
        comboBoxSalle.setBounds(30, 55, 380, 25);
        contentPane.add(comboBoxSalle);

        JLabel lblNbJours = new JLabel("Nombre de jours");
        lblNbJours.setFont(new Font("Arial", Font.PLAIN, 12));
        lblNbJours.setBounds(30, 95, 130, 25);
        contentPane.add(lblNbJours);

        textFieldNbJours = new JTextField();
        textFieldNbJours.setBounds(30, 125, 380, 25);
        contentPane.add(textFieldNbJours);

        JLabel lblPrix = new JLabel("Prix (EUR)");
        lblPrix.setFont(new Font("Arial", Font.PLAIN, 12));
        lblPrix.setBounds(30, 155, 130, 25);
        contentPane.add(lblPrix);

        textFieldPrix = new JTextField();
        textFieldPrix.setBounds(30, 185, 380, 25);
        contentPane.add(textFieldPrix);

        JButton btnAjouter = new JButton("Ajouter");
        btnAjouter.setForeground(Color.WHITE);
        btnAjouter.setBackground(Color.BLACK);
        btnAjouter.setFont(new Font("Arial", Font.BOLD, 12));
        btnAjouter.setBounds(150, 225, 130, 25);
        btnAjouter.addActionListener(this::ajouterTarif);
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

        chargerSalles();
    }

    private void chargerSalles() {
        try (Connection conn = ConnexionBaseDeDonnees.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT id, nomEspace FROM Espace");) {
            var rs = stmt.executeQuery();
            while (rs.next()) {
                comboBoxSalle.addItem(rs.getString("id") + " - " + rs.getString("nomEspace"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void ajouterTarif(ActionEvent e) {
        String salleSelectionnee = (String) comboBoxSalle.getSelectedItem();
        if (salleSelectionnee == null) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une salle.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] salleDetails = salleSelectionnee.split(" - ");
        int idEspace = Integer.parseInt(salleDetails[0]);
        int nbJours;
        double prix;

        try {
            nbJours = Integer.parseInt(textFieldNbJours.getText().trim());
            prix = Double.parseDouble(textFieldPrix.getText().trim());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer des valeurs valides.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (Connection conn = ConnexionBaseDeDonnees.getConnection();
                PreparedStatement insertTarif = conn.prepareStatement(
                        "INSERT INTO Tarif (idEspace, numTarif, nbJour, prix) VALUES (?, ?, ?, ?)")) {

               insertTarif.setInt(1, idEspace);
               insertTarif.setInt(2, nbJours);  
               insertTarif.setInt(3, nbJours);
               insertTarif.setDouble(4, prix);
               insertTarif.executeUpdate();

            JOptionPane.showMessageDialog(this, "Tarif ajouté avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
            textFieldNbJours.setText("");
            textFieldPrix.setText("");
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout du tarif.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}