CREATE TABLE `users` (
  `idusers` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `display_name` varchar(127) NOT NULL,
  `login_name` varchar(127) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  PRIMARY KEY (`idusers`),
  UNIQUE KEY `idusers_UNIQUE` (`idusermatchesmatchess`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `matches` (
  `idmatches` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `player_a_id` int(10) unsigned NOT NULL,
  `player_b_id` int(10) unsigned NOT NULL,
  `winner` tinyint(1) DEFAULT NULL,
  `date_finished` date DEFAULT NULL,
  PRIMARY KEY (`idmatches`),
  UNIQUE KEY `idgames_UNIQUE` (`idmatches`),
  KEY `matches_player_a_fk_idx` (`player_a_id`),
  KEY `matches_player_b_fk_idx` (`player_b_id`),
  CONSTRAINT `matches_player_a_fk` FOREIGN KEY (`player_a_id`) REFERENCES `users` (`idusers`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `matches_player_b_fk` FOREIGN KEY (`player_b_id`) REFERENCES `users` (`idusers`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `moves` (
  `idmoves` int(10) unsigned NOT NULL,
  `x` tinyint(8) NOT NULL,
  `y` tinyint(8) NOT NULL,
  `match_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`idmoves`),
  KEY `moves_matches_fk_idx` (`match_id`),
  CONSTRAINT `moves_matches_fk` FOREIGN KEY (`match_id`) REFERENCES `matches` (`idmatches`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
