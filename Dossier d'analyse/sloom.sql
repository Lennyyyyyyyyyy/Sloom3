-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : lun. 16 déc. 2024 à 17:52
-- Version du serveur : 8.3.0
-- Version de PHP : 8.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `sloom`
--

-- --------------------------------------------------------

--
-- Structure de la table `espace`
--

DROP TABLE IF EXISTS `espace`;
CREATE TABLE IF NOT EXISTS `espace` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nomEspace` varchar(255) DEFAULT NULL,
  `superfEspace` float DEFAULT NULL,
  `dispo` varchar(3) DEFAULT NULL,
  `capaciteAcc` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ;

--
-- Déchargement des données de la table `espace`
--

INSERT INTO `espace` (`id`, `nomEspace`, `superfEspace`, `dispo`, `capaciteAcc`) VALUES
(4, 'Salle de méditation', 50, 'Oui', 30),
(1, 'Salle de créativité', 50, 'Oui', 30),
(2, 'Salle de réunion', 80, 'Oui', 50),
(3, 'Salle de conférence', 250, 'Oui', 200),
(5, 'Salle Focus', 80, 'Oui', 50),
(6, 'Salle de jeux', 50, 'Oui', 30),
(7, 'Salle de formation', 250, 'Oui', 200),
(8, 'Salle de pause', 80, 'Oui', 50),
(9, 'Salle de télétravail', 250, 'Oui', 200);

-- --------------------------------------------------------

--
-- Structure de la table `etatreservation`
--

DROP TABLE IF EXISTS `etatreservation`;
CREATE TABLE IF NOT EXISTS `etatreservation` (
  `id` int NOT NULL,
  `libelleEtat` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `etatreservation`
--

INSERT INTO `etatreservation` (`id`, `libelleEtat`) VALUES
(1, 'En attente'),
(2, 'Confirmée'),
(3, 'Annulée');

-- --------------------------------------------------------

--
-- Structure de la table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
CREATE TABLE IF NOT EXISTS `reservation` (
  `id` int NOT NULL AUTO_INCREMENT,
  `datePaiementReser` date DEFAULT NULL,
  `dateReser` datetime DEFAULT CURRENT_TIMESTAMP,
  `dateHeureDebReser` datetime DEFAULT NULL,
  `dateHeureFinReser` datetime DEFAULT NULL,
  `montantReser` float DEFAULT NULL,
  `idEtat` int DEFAULT NULL,
  `idUtil` int DEFAULT NULL,
  `idEspace` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idEtat` (`idEtat`),
  KEY `idUtil` (`idUtil`),
  KEY `idEspace` (`idEspace`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `reservation`
--

INSERT INTO `reservation` (`id`, `datePaiementReser`, `dateReser`, `dateHeureDebReser`, `dateHeureFinReser`, `montantReser`, `idEtat`, `idUtil`, `idEspace`) VALUES
(1, NULL, '2024-12-05 18:20:36', '2025-12-25 08:00:00', '2025-12-26 12:00:00', 180, 1, 2, 1),
(2, NULL, '2024-12-16 18:23:38', '2024-12-31 12:30:00', '2024-12-31 15:30:00', 150, 1, 3, 8),
(3, NULL, '2024-12-01 18:25:57', '2025-04-18 08:00:00', '2025-04-22 17:00:00', 1700, 1, 4, 9),
(4, NULL, '2024-12-16 18:28:51', '2025-09-22 08:30:00', '2025-09-26 22:30:00', 2100, 1, 5, 7),
(5, NULL, '2024-12-16 18:29:33', '2025-11-23 18:30:00', '2025-11-23 20:30:00', 150, 1, 6, 2),
(6, NULL, '2024-12-02 18:30:29', '2025-10-16 10:30:00', '2025-10-16 12:30:00', 100, 1, 7, 4),
(7, NULL, '2024-12-05 18:31:51', '2024-12-31 21:00:00', '2025-01-01 05:30:00', 350, 1, 8, 3),
(8, NULL, '2024-12-16 18:32:55', '2025-08-20 08:30:00', '2025-08-22 17:30:00', 450, 1, 9, 5),
(9, NULL, '2024-12-16 18:33:39', '2025-12-31 15:30:00', '2025-12-31 17:30:00', 100, 1, 10, 6);

-- --------------------------------------------------------

--
-- Structure de la table `tarif`
--

DROP TABLE IF EXISTS `tarif`;
CREATE TABLE IF NOT EXISTS `tarif` (
  `idEspace` int NOT NULL,
  `numTarif` int NOT NULL,
  `nbJour` int DEFAULT NULL,
  `prix` float DEFAULT NULL,
  PRIMARY KEY (`idEspace`,`numTarif`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `tarif`
--

INSERT INTO `tarif` (`idEspace`, `numTarif`, `nbJour`, `prix`) VALUES
(6, 7, 7, 1050),
(4, 7, 7, 1050),
(4, 6, 6, 900),
(4, 5, 5, 750),
(4, 4, 4, 600),
(4, 3, 3, 450),
(4, 2, 2, 320),
(4, 1, 1, 180),
(1, 7, 7, 1050),
(1, 6, 6, 900),
(1, 5, 5, 750),
(1, 4, 4, 600),
(1, 3, 3, 450),
(1, 2, 2, 320),
(1, 1, 1, 180),
(1, 0, 0, 100),
(6, 6, 6, 900),
(6, 5, 5, 750),
(6, 4, 4, 600),
(6, 3, 3, 450),
(6, 2, 2, 320),
(6, 1, 1, 180),
(4, 0, 0, 100),
(6, 0, 0, 100),
(2, 0, 0, 150),
(2, 1, 1, 250),
(2, 2, 2, 450),
(2, 3, 3, 650),
(2, 4, 4, 800),
(2, 5, 5, 1000),
(2, 6, 6, 1200),
(2, 7, 7, 1400),
(5, 0, 0, 150),
(5, 1, 1, 250),
(5, 2, 2, 450),
(5, 3, 3, 650),
(5, 4, 4, 800),
(5, 5, 5, 1000),
(5, 6, 6, 1200),
(5, 7, 7, 1400),
(8, 0, 0, 150),
(8, 1, 1, 250),
(8, 2, 2, 450),
(8, 3, 3, 650),
(8, 4, 4, 800),
(8, 5, 5, 1000),
(8, 6, 6, 1200),
(8, 7, 7, 1400),
(3, 0, 0, 350),
(3, 1, 1, 500),
(3, 2, 2, 900),
(3, 3, 3, 1300),
(3, 4, 4, 1700),
(3, 5, 5, 2100),
(3, 6, 6, 2500),
(3, 7, 7, 2900),
(7, 0, 0, 350),
(7, 1, 1, 500),
(7, 2, 2, 900),
(7, 3, 3, 1300),
(7, 4, 4, 1700),
(7, 5, 5, 2100),
(7, 6, 6, 2500),
(7, 7, 7, 2900),
(9, 0, 0, 350),
(9, 1, 1, 500),
(9, 2, 2, 900),
(9, 3, 3, 1300),
(9, 4, 4, 1700),
(9, 5, 5, 2100),
(9, 6, 6, 2500),
(9, 7, 7, 2900);

-- --------------------------------------------------------

--
-- Structure de la table `typeutilisateur`
--

DROP TABLE IF EXISTS `typeutilisateur`;
CREATE TABLE IF NOT EXISTS `typeutilisateur` (
  `id` int NOT NULL,
  `libelleTypeUtil` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `typeutilisateur`
--

INSERT INTO `typeutilisateur` (`id`, `libelleTypeUtil`) VALUES
(1, 'Assistante'),
(2, 'Client');

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

DROP TABLE IF EXISTS `utilisateur`;
CREATE TABLE IF NOT EXISTS `utilisateur` (
  `id` int NOT NULL AUTO_INCREMENT,
  `identifiantUtil` varchar(255) DEFAULT NULL,
  `mdpUtil` varchar(255) DEFAULT NULL,
  `prenomUtil` varchar(255) DEFAULT NULL,
  `nomUtil` varchar(255) DEFAULT NULL,
  `raisonSociale` varchar(255) DEFAULT NULL,
  `mailUtil` varchar(255) DEFAULT NULL,
  `telUtil` varchar(15) DEFAULT NULL,
  `dateInscUtil` date DEFAULT NULL,
  `idTypeUtil` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idTypeUtil` (`idTypeUtil`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`id`, `identifiantUtil`, `mdpUtil`, `prenomUtil`, `nomUtil`, `raisonSociale`, `mailUtil`, `telUtil`, `dateInscUtil`, `idTypeUtil`) VALUES
(1, 'Edupont', 'KWg42ljqb9', 'Eugénie', 'Dupont', 'sloom', 'eugenie@company.com', '0601020304', '2025-01-16', 1),
(2, NULL, NULL, 'John', 'Doe', 'Green Solutions', 'john.doe@email.com', '0654321098', '2024-12-17', 2),
(3, NULL, NULL, 'Sarah', 'Martin', 'Eco Business Group', 'sarah.martin@email.com', '0665876543', '2024-12-17', 2),
(4, NULL, NULL, 'Michael', 'Johnson', 'Data Tech Co.', 'michael.johnson@email.com', '0689988776', '2024-12-17', 2),
(5, NULL, NULL, 'Alex', 'Rodriguez', 'Vision Marketing', 'alex.rodriguez@email.com', '0689988776', '2024-12-17', 2),
(6, NULL, NULL, 'Julia', 'Bennett', 'NextGen Systems', 'julia.bennett@nextgen.com', '0691122334', '2024-12-17', 2),
(7, NULL, NULL, 'Martin', 'Dupuis', 'Creative Minds Studio', 'martin.dupuis@creativeminds.com', '0677888999', '2024-12-17', 2),
(8, NULL, NULL, 'Chloé', 'Leclerc', 'Global Enterprises', 'chloe.leclerc@global.com', '0666777888', '2024-12-17', 2),
(9, NULL, NULL, 'James', 'Parker', 'Smart Builders', 'chloe.leclerc@global.com', '0655443322', '2024-12-17', 2),
(10, NULL, NULL, 'Elena', 'Garcia', 'Services & Solutions', 'lena.garcia@services&solutions.com', '0644225566', '2024-12-17', 2);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
