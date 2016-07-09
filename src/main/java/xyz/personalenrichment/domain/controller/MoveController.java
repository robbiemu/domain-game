package xyz.personalenrichment.domain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import xyz.personalenrichment.domain.dao.MoveDao;
import xyz.personalenrichment.domain.model.Move;

@RestController
@RequestMapping("/move")
public class MoveController {
	@Autowired
	private MoveDao movesDao;

	@RequestMapping(method= RequestMethod.GET) //root
	public @ResponseBody List<Move> index() {
		return movesDao.indexMoves();
	}
	
	@RequestMapping(value = "/byId/{pk}", method = RequestMethod.GET)
	public @ResponseBody Move byId(@PathVariable Short pk) {
		return movesDao.getMoveById(pk);
	}
}