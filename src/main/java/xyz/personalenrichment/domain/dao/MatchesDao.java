package xyz.personalenrichment.domain.dao;

import java.util.List;

import xyz.personalenrichment.domain.model.Matches;

public interface MatchesDao {

	public List<Matches> indexMatches();

	public Matches getMatchById(Short pk);
}
