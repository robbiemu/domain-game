CREATE DATABASE `domain_game` /*!40100 DEFAULT CHARACTER SET utf8 */;
CREATE TABLE `match` (
  `idmatch` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `winner` int(10) unsigned DEFAULT NULL,
  `date_finished` date DEFAULT NULL,
  `pre_board_state` blob NOT NULL,
  PRIMARY KEY (`idmatch`),
  UNIQUE KEY `idmatch_UNIQUE` (`idmatch`),
  KEY `match_user_fk_idx` (`winner`),
  CONSTRAINT `match_user_fk` FOREIGN KEY (`winner`) REFERENCES `user` (`iduser`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `move` (
  `idmove` int(10) unsigned NOT NULL,
  `x` tinyint(8) NOT NULL,
  `y` tinyint(8) NOT NULL,
  `match_id` int(10) unsigned NOT NULL,
  `player_id` int(10) unsigned NOT NULL,
  `post_board_state` blob,
  PRIMARY KEY (`idmove`),
  UNIQUE KEY `idmove_UNIQUE` (`idmove`),
  KEY `moves_matches_fk_idx` (`match_id`),
  KEY `moves_players_fk_idx` (`player_id`),
  CONSTRAINT `move_match_fk` FOREIGN KEY (`match_id`) REFERENCES `match` (`idmatch`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `move_player_fk` FOREIGN KEY (`player_id`) REFERENCES `user` (`iduser`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `user` (
  `iduser` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `display_name` varchar(127) NOT NULL,
  `login_name` varchar(127) NOT NULL,
  `password` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  PRIMARY KEY (`iduser`),
  UNIQUE KEY `idusers_UNIQUE` (`iduser`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
CREATE TABLE `match_player_jt` (
  `idmatch_players` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `match_id` int(10) unsigned NOT NULL,
  `player_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`idmatch_players`),
  UNIQUE KEY `idmatch_players_UNIQUE` (`idmatch_players`),
  KEY `match_players_match_fk_idx` (`match_id`),
  KEY `match_players_players_fk_idx` (`player_id`),
  CONSTRAINT `match_player_jt_match_fk` FOREIGN KEY (`match_id`) REFERENCES `match` (`idmatch`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `match_player_jt_player_fk` FOREIGN KEY (`player_id`) REFERENCES `user` (`iduser`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

