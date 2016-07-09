package xyz.personalenrichment.domain.dao;

import java.util.List;

import xyz.personalenrichment.domain.model.Matches;

public interface MatchesDao {

	public List<Matches> readMatches();
	public Matches readMatch(Integer pk);
	public Matches createMatch(Matches match);
	public Matches updateMatch(Integer pk, Matches match);
	public Matches deleteMatch(Integer pk);
}
