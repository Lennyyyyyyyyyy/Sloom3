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
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;


public class CréerUneRéservation extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JComboBox<String> comboBoxSalle;
    private JComboBox<String> comboBoxUtilisateur;
    private JDateChooser dateHeureDebut;
    private JDateChooser dateHeureFin;
    private JTextField textFieldMontant;
    private JSpinner spinnerHeureDebut;
    private JSpinner spinnerHeureFin;
    private JTextField textFieldPrenom;
    private JTextField textFieldNom;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                CréerUneRéservation frame = new CréerUneRéservation();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public CréerUneRéservation() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 480, 500);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblUtilisateur = new JLabel("Identifiant du client");
        lblUtilisateur.setFont(new Font("Arial", Font.PLAIN, 12));
        lblUtilisateur.setBounds(30, 25, 150, 25);
        contentPane.add(lblUtilisateur);

        comboBoxUtilisateur = new JComboBox<>();
        comboBoxUtilisateur.setBackground(new Color(255, 255, 255));
        comboBoxUtilisateur.setFont(new Font("Arial", Font.PLAIN, 11));
        comboBoxUtilisateur.setBounds(30, 55, 150, 25);
        comboBoxUtilisateur.addActionListener(e -> remplirInfosUtilisateur());
        contentPane.add(comboBoxUtilisateur);

        JLabel lblPrenom = new JLabel("Prénom");
        lblPrenom.setFont(new Font("Arial", Font.PLAIN, 12));
        lblPrenom.setBounds(30, 85, 150, 25);
        contentPane.add(lblPrenom);

        textFieldPrenom = new JTextField();
        textFieldPrenom.setBounds(30, 115, 150, 25);
        textFieldPrenom.setEditable(false);
        contentPane.add(textFieldPrenom);

        JLabel lblNom = new JLabel("Nom");
        lblNom.setFont(new Font("Arial", Font.PLAIN, 12));
        lblNom.setBounds(280, 85, 150, 25);
        contentPane.add(lblNom);

        textFieldNom = new JTextField();
        textFieldNom.setBounds(280, 115, 150, 25);
        textFieldNom.setEditable(false);
        contentPane.add(textFieldNom);

        JLabel lblSalle = new JLabel("Salle");
        lblSalle.setFont(new Font("Arial", Font.PLAIN, 12));
        lblSalle.setBounds(30, 145, 150, 25);
        contentPane.add(lblSalle);

        comboBoxSalle = new JComboBox<>();
        comboBoxSalle.setFont(new Font("Arial", Font.PLAIN, 11));
        comboBoxSalle.setBackground(new Color(255, 255, 255));
        comboBoxSalle.setBounds(30, 175, 400, 25);
        contentPane.add(comboBoxSalle);

        JLabel lblDebut = new JLabel("Date Début");
        lblDebut.setFont(new Font("Arial", Font.PLAIN, 12));
        lblDebut.setBounds(30, 205, 150, 25);
        contentPane.add(lblDebut);

        dateHeureDebut = new JDateChooser();
        dateHeureDebut.setDateFormatString("dd/MM/yyyy");
        dateHeureDebut.setBounds(30, 235, 150, 25);
        contentPane.add(dateHeureDebut);

        spinnerHeureDebut = new JSpinner(new SpinnerDateModel());
        spinnerHeureDebut.setBounds(280, 235, 150, 25);
        JSpinner.DateEditor editorDebut = new JSpinner.DateEditor(spinnerHeureDebut, "HH:mm");
        spinnerHeureDebut.setEditor(editorDebut);
        contentPane.add(spinnerHeureDebut);

        JLabel lblFin = new JLabel("Date Fin");
        lblFin.setFont(new Font("Arial", Font.PLAIN, 12));
        lblFin.setBounds(30, 265, 150, 25);
        contentPane.add(lblFin);

        dateHeureFin = new JDateChooser();
        dateHeureFin.setDateFormatString("dd/MM/yyyy");
        dateHeureFin.setBounds(30, 295, 150, 25);
        contentPane.add(dateHeureFin);

        spinnerHeureFin = new JSpinner(new SpinnerDateModel());
        spinnerHeureFin.setBounds(280, 295, 150, 25);
        JSpinner.DateEditor editorFin = new JSpinner.DateEditor(spinnerHeureFin, "HH:mm");
        spinnerHeureFin.setEditor(editorFin);
        contentPane.add(spinnerHeureFin);

        JLabel lblMontant = new JLabel("Montant Total (EUR)");
        lblMontant.setFont(new Font("Arial", Font.PLAIN, 12));
        lblMontant.setBounds(30, 325, 150, 25);
        contentPane.add(lblMontant);

        textFieldMontant = new JTextField();
        textFieldMontant.setForeground(new Color(0, 0, 0));
        textFieldMontant.setBackground(new Color(255, 255, 255));
        textFieldMontant.setFont(new Font("Arial", Font.BOLD, 12));
        textFieldMontant.setEditable(false);
        textFieldMontant.setBounds(30, 355, 400, 25);
        contentPane.add(textFieldMontant);

        JButton btnCalculer = new JButton("Calculer Montant");
        btnCalculer.setBackground(new Color(0, 0, 0));
        btnCalculer.setFont(new Font("Arial", Font.PLAIN, 12));
        btnCalculer.setForeground(new Color(255, 255, 255));
        btnCalculer.setBounds(30, 400, 130, 25);
        btnCalculer.addActionListener(e -> calculerMontant());
        contentPane.add(btnCalculer);

        JButton btnReserver = new JButton("Réserver");
        btnReserver.setForeground(new Color(255, 255, 255));
        btnReserver.setBackground(new Color(0, 0, 0));
        btnReserver.setFont(new Font("Arial", Font.PLAIN, 12));
        btnReserver.setBounds(300, 400, 130, 25);
        btnReserver.addActionListener(e -> ajouterReservation());
        contentPane.add(btnReserver);
        
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
   
        remplirComboBoxUtilisateur();
        remplirComboBoxSalle();
    }

    private void remplirComboBoxUtilisateur() {
        Connection conn = ConnexionBaseDeDonnees.getConnection();
        try {
            String query = "SELECT id, identifiantUtil FROM Utilisateur";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                comboBoxUtilisateur.addItem(rs.getString("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void remplirComboBoxSalle() {
        Connection conn = ConnexionBaseDeDonnees.getConnection();
        try {
            String query = "SELECT id, nomEspace FROM Espace WHERE dispo = 'Oui'";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                comboBoxSalle.addItem(rs.getString("id") + " - " + rs.getString("nomEspace"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void remplirInfosUtilisateur() {
        String idUtilisateur = (String) comboBoxUtilisateur.getSelectedItem();
        Connection conn = ConnexionBaseDeDonnees.getConnection();
        try {
            String query = "SELECT prenomUtil, nomUtil FROM Utilisateur WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, idUtilisateur);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                textFieldPrenom.setText(rs.getString("prenomUtil"));
                textFieldNom.setText(rs.getString("nomUtil"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void calculerMontant() {

        if (comboBoxUtilisateur.getSelectedItem() == null || comboBoxSalle.getSelectedItem() == null ||
            dateHeureDebut.getDate() == null || dateHeureFin.getDate() == null ||
            spinnerHeureDebut.getValue() == null || spinnerHeureFin.getValue() == null) {

            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs avant de calculer le montant.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Date debut = dateHeureDebut.getDate();
        Date fin = dateHeureFin.getDate();
        LocalTime heureDebut = ((java.util.Date) spinnerHeureDebut.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
        LocalTime heureFin = ((java.util.Date) spinnerHeureFin.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalTime();

        LocalDateTime dateHeureDebut = debut.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().with(heureDebut);
        LocalDateTime dateHeureFin = fin.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().with(heureFin);

        if (dateHeureDebut.isBefore(dateHeureFin)) {
            Duration duration = Duration.between(dateHeureDebut, dateHeureFin);
            long differenceHeures = duration.toHours();
            long differenceJours = differenceHeures / 24;
            long resteHeures = differenceHeures % 24;

            if (resteHeures >= 12) {
                differenceJours++;
            }

            boolean demiJournee = false;
            if (differenceJours == 0 && resteHeures > 0 && resteHeures < 12) {
                demiJournee = true;
            }

            String idSalle = comboBoxSalle.getSelectedItem().toString().split(" - ")[0];
            double tarif = obtenirTarif(idSalle, differenceJours, demiJournee);

            if (tarif != -1) {

                textFieldMontant.setText(String.format("%.2f", tarif) + " EUR");
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la récupération du tarif.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "La date de début doit être antérieure à la date de fin.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }



    private double obtenirTarif(String idSalle, long nbJours, boolean demiJournee) {
        double tarif = -1;

        Connection conn = ConnexionBaseDeDonnees.getConnection();
        try {
            
            String query = "SELECT prix FROM Tarif WHERE idEspace = ? AND nbJour = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, idSalle);
            stmt.setDouble(2, nbJours); 

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                tarif = rs.getDouble("prix");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tarif;
    }


    private void ajouterReservation() {

        if (comboBoxUtilisateur.getSelectedItem() == null || comboBoxSalle.getSelectedItem() == null ||
            dateHeureDebut.getDate() == null || dateHeureFin.getDate() == null ||
            spinnerHeureDebut.getValue() == null || spinnerHeureFin.getValue() == null) {

            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs avant de calculer le montant.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String idUtilisateur = (String) comboBoxUtilisateur.getSelectedItem();
        String idSalle = comboBoxSalle.getSelectedItem().toString().split(" - ")[0];
        Date dateDebut = dateHeureDebut.getDate();
        Date dateFin = dateHeureFin.getDate();

      
        LocalDateTime localDateTimeDebut = dateDebut.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime localDateTimeFin = dateFin.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalTime heureDebut = ((java.util.Date) spinnerHeureDebut.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
        LocalTime heureFin = ((java.util.Date) spinnerHeureFin.getValue()).toInstant().atZone(ZoneId.systemDefault()).toLocalTime();
        LocalDateTime dateHeureDebutFinal = localDateTimeDebut.with(heureDebut);
        LocalDateTime dateHeureFinFinal = localDateTimeFin.with(heureFin);

        if (dateHeureFinFinal.isBefore(dateHeureDebutFinal) || dateHeureFinFinal.isEqual(dateHeureDebutFinal)) {
            JOptionPane.showMessageDialog(this, "La date et l'heure de fin doivent être strictement supérieures à la date et l'heure de début.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }


        String montantText = textFieldMontant.getText().replace(" EUR", "").trim();
        montantText = montantText.replace(",", ".");
        double montant = Double.parseDouble(montantText);

        if (dateDebut.before(new Date())) {
            JOptionPane.showMessageDialog(this, "La date de début doit être postérieure à la date actuelle.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (isSalleDisponible(idSalle, dateDebut, dateFin)) {
            try {
                Timestamp timestampDebut = Timestamp.valueOf(dateHeureDebutFinal);
                Timestamp timestampFin = Timestamp.valueOf(dateHeureFinFinal);

                String query = "INSERT INTO Reservation (datePaiementReser, dateHeureDebReser, dateHeureFinReser, montantReser, idEtat, idUtil, idEspace) VALUES (NULL, ?, ?, ?, 1, ?, ?)";
                Connection conn = ConnexionBaseDeDonnees.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setTimestamp(1, timestampDebut);
                stmt.setTimestamp(2, timestampFin);
                stmt.setDouble(3, montant);
                stmt.setString(4, idUtilisateur);
                stmt.setString(5, idSalle);
                stmt.executeUpdate();
                
                JOptionPane.showMessageDialog(this, "Réservation ajoutée avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
                
                comboBoxUtilisateur.setSelectedIndex(-1);
                comboBoxSalle.setSelectedIndex(-1);
                textFieldPrenom.setText("");
                textFieldNom.setText("");
                textFieldMontant.setText("");
                dateHeureDebut.setDate(null);
                dateHeureFin.setDate(null);
                spinnerHeureDebut.setValue(new Date());
                spinnerHeureFin.setValue(new Date());
                
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de la réservation.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "La salle est déjà réservée pendant cette période.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }


    private boolean isSalleDisponible(String idSalle, Date dateDebut, Date dateFin) {
        String query = "SELECT COUNT(*) FROM Reservation WHERE idEspace = ? AND ("
                + "(dateHeureDebReser < ? AND dateHeureFinReser > ?) OR " 
                + "(dateHeureDebReser < ? AND dateHeureFinReser > ?) OR " 
                + "(dateHeureDebReser >= ? AND dateHeureFinReser <= ?))"; 

        try (Connection conn = ConnexionBaseDeDonnees.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, idSalle);
            stmt.setTimestamp(2, new Timestamp(dateDebut.getTime()));  
            stmt.setTimestamp(3, new Timestamp(dateFin.getTime()));    
            stmt.setTimestamp(4, new Timestamp(dateDebut.getTime()));  
            stmt.setTimestamp(5, new Timestamp(dateFin.getTime()));    
            stmt.setTimestamp(6, new Timestamp(dateDebut.getTime()));  
            stmt.setTimestamp(7, new Timestamp(dateFin.getTime()));    

            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return false;  
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;  
    }

}
