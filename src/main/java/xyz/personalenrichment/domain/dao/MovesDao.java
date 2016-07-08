package xyz.personalenrichment.domain.dao;

import java.util.List;

import xyz.personalenrichment.domain.model.Moves;

public interface MovesDao {

	public List<Moves> indexMoves();
	public Moves getMoveById(Short pk);

}
