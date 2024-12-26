package sloom3;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class MenuPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuPrincipal frame = new MenuPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MenuPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 555, 560);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnAfficherReservationsAvenir = new JButton("Afficher les réservations à venir pour une salle");
		btnAfficherReservationsAvenir.setBackground(new Color(255, 255, 255));
		btnAfficherReservationsAvenir.setFont(new Font("Arial", Font.BOLD, 12));
		btnAfficherReservationsAvenir.setBounds(0, 65, 325, 65);
		contentPane.add(btnAfficherReservationsAvenir);
		btnAfficherReservationsAvenir.addActionListener(new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                dispose ();
	                AfficherRéservationsAVenir frame = new AfficherRéservationsAVenir();
	                frame.setVisible(true);  
	            }
	        });
		
		JButton btnUneCreerSalle = new JButton("Créer une salle");
		btnUneCreerSalle.setFont(new Font("Arial", Font.BOLD, 12));
		btnUneCreerSalle.setBackground(Color.WHITE);
		btnUneCreerSalle.setBounds(0, 0, 145, 65);
		contentPane.add(btnUneCreerSalle);
		btnUneCreerSalle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose ();
                CréerUneSalle frame = new CréerUneSalle();
                frame.setVisible(true);  
            }
        });
		
		JButton btnAfficherSallesDisponibles = new JButton("Afficher les salles disponibles pour une période et en fonction d’une capacité d’accueil");
		btnAfficherSallesDisponibles.setToolTipText("");
		btnAfficherSallesDisponibles.setFont(new Font("Arial", Font.BOLD, 12));
		btnAfficherSallesDisponibles.setBackground(Color.WHITE);
		btnAfficherSallesDisponibles.setBounds(0, 195, 540, 65);
		contentPane.add(btnAfficherSallesDisponibles);
		btnAfficherSallesDisponibles.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose ();
                AfficherSallesDisponibles frame = new AfficherSallesDisponibles();
                frame.setVisible(true);  
            }
        });
		
		JButton btnModifierUneSalle = new JButton("Modifier une salle");
		btnModifierUneSalle.setFont(new Font("Arial", Font.BOLD, 12));
		btnModifierUneSalle.setBackground(Color.WHITE);
		btnModifierUneSalle.setBounds(0, 130, 145, 65);
		contentPane.add(btnModifierUneSalle);
		btnModifierUneSalle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose ();
                ModifierUneSalle frame = new ModifierUneSalle();
                frame.setVisible(true);  
            }
        });
		
		JButton btnSupprimerUneSalle = new JButton("Supprimer une salle");
		btnSupprimerUneSalle.setFont(new Font("Arial", Font.BOLD, 12));
		btnSupprimerUneSalle.setBackground(Color.WHITE);
		btnSupprimerUneSalle.setBounds(145, 0, 170, 65);
		contentPane.add(btnSupprimerUneSalle);
		btnSupprimerUneSalle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose ();
                SupprimerUneSalle frame = new SupprimerUneSalle();
                frame.setVisible(true);  
            }
        });
		
		JButton btnSeDeconnecter = new JButton("Se déconnecter");
		btnSeDeconnecter.setForeground(new Color(255, 255, 255));
		btnSeDeconnecter.setFont(new Font("Arial", Font.BOLD, 12));
		btnSeDeconnecter.setBackground(new Color(0, 0, 0));
		btnSeDeconnecter.setBounds(315, 0, 225, 65);
		contentPane.add(btnSeDeconnecter);
		btnSeDeconnecter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose ();
                SeDéconnecter frame = new SeDéconnecter();
                frame.setVisible(true);  
            }
        });
		
		JButton btnRechercherClientRaisonSociale = new JButton("Rechercher un client sur sa raison sociale");
		btnRechercherClientRaisonSociale.setFont(new Font("Arial", Font.BOLD, 12));
		btnRechercherClientRaisonSociale.setBackground(Color.WHITE);
		btnRechercherClientRaisonSociale.setBounds(145, 130, 395, 65);
		contentPane.add(btnRechercherClientRaisonSociale);
		btnRechercherClientRaisonSociale.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose ();
                RechercherClientRaisonSociale frame = new RechercherClientRaisonSociale();
                frame.setVisible(true);  
            }
        });
		
		JButton btnCreerUnClient = new JButton("Créer un nouveau client");
		btnCreerUnClient.setFont(new Font("Arial", Font.BOLD, 12));
		btnCreerUnClient.setBackground(Color.WHITE);
		btnCreerUnClient.setBounds(325, 65, 215, 65);
		contentPane.add(btnCreerUnClient);
		btnCreerUnClient.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose ();
                CréerUnClient frame = new CréerUnClient();
                frame.setVisible(true);  
            }
        });
		
		JButton btnModifierUnClient = new JButton(" Modifier un client");
		btnModifierUnClient.setFont(new Font("Arial", Font.BOLD, 12));
		btnModifierUnClient.setBackground(Color.WHITE);
		btnModifierUnClient.setBounds(0, 325, 180, 65);
		contentPane.add(btnModifierUnClient);
		btnModifierUnClient.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose ();
                ModifierUnClient frame = new ModifierUnClient();
                frame.setVisible(true);  
            }
        });
		
		JButton btnSupprimerUnClient = new JButton("Supprimer un client");
		btnSupprimerUnClient.setFont(new Font("Arial", Font.BOLD, 12));
		btnSupprimerUnClient.setBackground(Color.WHITE);
		btnSupprimerUnClient.setBounds(370, 260, 170, 65);
		contentPane.add(btnSupprimerUnClient);
		btnSupprimerUnClient.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose ();
                SupprimerUnClient frame = new SupprimerUnClient();
                frame.setVisible(true);  
            }
        });
		
		JButton btnAfficherDemandesEnRetard = new JButton("Afficher les demandes de locations en retard de paiement");
		btnAfficherDemandesEnRetard.setToolTipText("");
		btnAfficherDemandesEnRetard.setFont(new Font("Arial", Font.BOLD, 12));
		btnAfficherDemandesEnRetard.setBackground(Color.WHITE);
		btnAfficherDemandesEnRetard.setBounds(0, 260, 370, 65);
		contentPane.add(btnAfficherDemandesEnRetard);
		btnAfficherDemandesEnRetard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose ();
                AfficherDemandesEnRetard frame = new AfficherDemandesEnRetard();
                frame.setVisible(true);  
                frame.afficherDemandesEnRetard();
            }
        });
		
		JButton btnCreerUnTarif = new JButton("Créer un nouveau tarif pour une salle");
		btnCreerUnTarif.setToolTipText("");
		btnCreerUnTarif.setFont(new Font("Arial", Font.BOLD, 12));
		btnCreerUnTarif.setBackground(Color.WHITE);
		btnCreerUnTarif.setBounds(180, 390, 360, 65);
		contentPane.add(btnCreerUnTarif);
		btnCreerUnTarif.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose ();
                CréerUnTarif frame = new CréerUnTarif();
                frame.setVisible(true);  
            }
        });
		
		JButton btnCreerUneReservation = new JButton("Créer une réservation");
		btnCreerUneReservation.setFont(new Font("Arial", Font.BOLD, 12));
		btnCreerUneReservation.setBackground(Color.WHITE);
		btnCreerUneReservation.setBounds(180, 325, 190, 65);
		contentPane.add(btnCreerUneReservation);
		btnCreerUneReservation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose ();
                CréerUneRéservation frame = new CréerUneRéservation();
                frame.setVisible(true);  
            }
        });
		
		JButton btnModifierUneReservation = new JButton("Modifier une réservation");
		btnModifierUneReservation.setFont(new Font("Arial", Font.BOLD, 12));
		btnModifierUneReservation.setBackground(Color.WHITE);
		btnModifierUneReservation.setBounds(370, 325, 170, 65);
		contentPane.add(btnModifierUneReservation);
		btnModifierUneReservation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose ();
                ModifierUneRéservation frame = new ModifierUneRéservation();
                frame.setVisible(true);  
            }
        });
		
		JButton btnSupprimerUneReservation = new JButton("Supprimer une réservation");
		btnSupprimerUneReservation.setFont(new Font("Arial", Font.BOLD, 12));
		btnSupprimerUneReservation.setBackground(Color.WHITE);
		btnSupprimerUneReservation.setBounds(0, 455, 270, 65);
		contentPane.add(btnSupprimerUneReservation);
		btnSupprimerUneReservation.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose ();
                SupprimerUneRéservation frame = new SupprimerUneRéservation();
                frame.setVisible(true);  
            }
        });

		JButton btnModifierUnTarif = new JButton("Modifier un tarif");
		btnModifierUnTarif.setFont(new Font("Arial", Font.BOLD, 12));
		btnModifierUnTarif.setBackground(Color.WHITE);
		btnModifierUnTarif.setBounds(0, 390, 180, 65);
		contentPane.add(btnModifierUnTarif);
		btnModifierUnTarif.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose ();
                ModifierUnTarif frame = new ModifierUnTarif();
                frame.setVisible(true);  
            }
        });
		
		JButton btnSupprimerUnTarif = new JButton("Supprimer un tarif");
		btnSupprimerUnTarif.setFont(new Font("Arial", Font.BOLD, 12));
		btnSupprimerUnTarif.setBackground(Color.WHITE);
		btnSupprimerUnTarif.setBounds(270, 455, 270, 65);
		contentPane.add(btnSupprimerUnTarif);
		btnSupprimerUnTarif.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose ();
                SupprimerUnTarif frame = new SupprimerUnTarif();
                frame.setVisible(true);  
            }
        });
		
	}	
}
