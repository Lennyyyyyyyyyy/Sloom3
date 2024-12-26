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
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ModifierUnTarif extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JComboBox<String> comboBoxNbJour;  
    private JTextField textFieldPrix;
    private JComboBox<String> comboBoxTarif;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ModifierUnTarif frame = new ModifierUnTarif();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ModifierUnTarif() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 250);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblTarif = new JLabel("Sélectionner une salle");
        lblTarif.setFont(new Font("Arial", Font.PLAIN, 12));
        lblTarif.setBounds(30, 25, 150, 30);
        contentPane.add(lblTarif);

        comboBoxTarif = new JComboBox<>();
        comboBoxTarif.setFont(new Font("Arial", Font.PLAIN, 11));
        comboBoxTarif.setBounds(30, 55, 350, 25);
        comboBoxTarif.addActionListener(e -> remplirChamps());
        contentPane.add(comboBoxTarif);

        JLabel lblNbJour = new JLabel("Nombre de jours");
        lblNbJour.setFont(new Font("Arial", Font.PLAIN, 12));
        lblNbJour.setBounds(30, 95, 150, 30);
        contentPane.add(lblNbJour);

        comboBoxNbJour = new JComboBox<>();  
        comboBoxNbJour.setFont(new Font("Arial", Font.PLAIN, 11));
        comboBoxNbJour.setBounds(30, 125, 150, 25);
        comboBoxNbJour.addActionListener(e -> afficherPrix());
        contentPane.add(comboBoxNbJour);

        JLabel lblPrix = new JLabel("Prix en EUR");
        lblPrix.setFont(new Font("Arial", Font.PLAIN, 12));
        lblPrix.setBounds(230, 95, 150, 30);
        contentPane.add(lblPrix);

        textFieldPrix = new JTextField();
        textFieldPrix.setFont(new Font("Arial", Font.PLAIN, 11));
        textFieldPrix.setBounds(230, 125, 150, 25);
        contentPane.add(textFieldPrix);

        JButton btnModifier = new JButton("Modifier");
        btnModifier.setForeground(Color.WHITE);
        btnModifier.setBackground(Color.BLACK);
        btnModifier.setFont(new Font("Arial", Font.BOLD, 12));
        btnModifier.setBounds(150, 170, 130, 25);
        btnModifier.addActionListener(e -> modifierTarif());
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

        chargerTarifs();
    }

    private void chargerTarifs() {
        Connection conn = ConnexionBaseDeDonnees.getConnection();
        if (conn != null) {
            try {
                String selectQuery = "SELECT id, nomEspace FROM Espace";
                PreparedStatement stmt = conn.prepareStatement(selectQuery);
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    String tarifDetails = rs.getInt("id") + " - " + rs.getString("nomEspace");
                    comboBoxTarif.addItem(tarifDetails);
                }

                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void remplirChamps() {
        String tarifSelectionne = (String) comboBoxTarif.getSelectedItem();
        if (tarifSelectionne == null) return;

        String[] details = tarifSelectionne.split(" - ");
        int idEspace = Integer.parseInt(details[0]);

        Connection conn = ConnexionBaseDeDonnees.getConnection();
        if (conn != null) {
            try {
                String selectQuery = "SELECT DISTINCT nbJour FROM Tarif WHERE idEspace = ?";
                PreparedStatement stmt = conn.prepareStatement(selectQuery);
                stmt.setInt(1, idEspace);
                ResultSet rs = stmt.executeQuery();

                comboBoxNbJour.removeAllItems();  
                while (rs.next()) {
                    comboBoxNbJour.addItem(String.valueOf(rs.getInt("nbJour")));
                }

                if (comboBoxNbJour.getItemCount() > 0) {
                    comboBoxNbJour.setSelectedIndex(0);
                    afficherPrix();  
                }

                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void afficherPrix() {
        String tarifSelectionne = (String) comboBoxTarif.getSelectedItem();
        if (tarifSelectionne == null || comboBoxNbJour.getSelectedItem() == null) return;

        String[] details = tarifSelectionne.split(" - ");
        int idEspace = Integer.parseInt(details[0]);
        int nbJour = Integer.parseInt((String) comboBoxNbJour.getSelectedItem());

        Connection conn = ConnexionBaseDeDonnees.getConnection();
        if (conn != null) {
            try {
                String selectQuery = "SELECT prix FROM Tarif WHERE idEspace = ? AND nbJour = ?";
                PreparedStatement stmt = conn.prepareStatement(selectQuery);
                stmt.setInt(1, idEspace);
                stmt.setInt(2, nbJour);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    textFieldPrix.setText(String.valueOf(rs.getDouble("prix")));
                }

                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void modifierTarif() {
        String tarifSelectionne = (String) comboBoxTarif.getSelectedItem();
        if (tarifSelectionne == null) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner une salle.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] details = tarifSelectionne.split(" - ");
        int idEspace = Integer.parseInt(details[0]);

        String nbJour = (String) comboBoxNbJour.getSelectedItem();
        String prix = textFieldPrix.getText().trim();

        if (nbJour == null || prix.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.");
            return;
        }

        Connection conn = ConnexionBaseDeDonnees.getConnection();
        if (conn != null) {
            try {
                String updateQuery = "UPDATE Tarif SET prix = ? WHERE idEspace = ? AND nbJour = ?";
                PreparedStatement stmt = conn.prepareStatement(updateQuery);
                stmt.setDouble(1, Double.parseDouble(prix));
                stmt.setInt(2, idEspace);
                stmt.setInt(3, Integer.parseInt(nbJour));

                if (stmt.executeUpdate() > 0) {
                    JOptionPane.showMessageDialog(this, "Tarif modifié avec succès.");
                    textFieldPrix.setText("");
                    comboBoxNbJour.setSelectedIndex(0);
                    comboBoxTarif.setSelectedIndex(0);
                }
                stmt.close();
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
