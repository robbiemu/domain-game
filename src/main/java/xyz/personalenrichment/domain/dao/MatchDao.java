package xyz.personalenrichment.domain.dao;

import java.util.List;

import xyz.personalenrichment.domain.model.Match;
import xyz.personalenrichment.domain.model.Move;
import xyz.personalenrichment.domain.model.User;

public interface MatchDao {

	public List<Match> readMatches();
	public Match readMatch(Integer pk);
	public Match createMatch(Match match);
	public Match updateMatch(Integer pk, Match match);
	public Match deleteMatch(Integer pk);
	public List<Move> readMoves(Integer pk);
	public Match createWinner(Integer pk, Integer upk);
	public Match deleteWinner(Integer pk, Integer upk);
	public User readWinner(Integer pk);
	public List<User> readPlayers(Integer pk);
	public Match createPlayer(Integer pk, Integer upk);
	public Match deletePlayer(Integer pk, Integer upk);
}
