package xyz.personalenrichment.domain.dao;

import java.util.List;

import xyz.personalenrichment.domain.model.Move;

public interface MoveDao {

	public List<Move> indexMoves();
	public Move getMoveById(Short pk);

}
