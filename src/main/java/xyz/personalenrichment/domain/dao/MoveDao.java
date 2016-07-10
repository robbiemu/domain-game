package xyz.personalenrichment.domain.dao;

import java.util.List;

import xyz.personalenrichment.domain.model.Match;
import xyz.personalenrichment.domain.model.Move;
import xyz.personalenrichment.domain.model.User;

public interface MoveDao {

	public Move readMove(Integer pk);
	public Move createMove(Move move);
	public Move createMove(Integer mpk, Move move);
	public Move updateMove(Integer pk, Move move);
	public Move deleteMove(Integer pk);
	public Match readMatch(Integer pk);
	public User readPlayer(Integer pk);
	public Move createMatch(Integer pk, Match match);
	public Move createPlayer(Integer pk, User user);
	public Move deleteMatch(Integer pk);
	public Move deletePlayer(Integer pk);

}
