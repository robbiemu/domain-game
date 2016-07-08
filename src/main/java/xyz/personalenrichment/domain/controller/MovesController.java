package xyz.personalenrichment.domain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import xyz.personalenrichment.domain.dao.MovesDao;
import xyz.personalenrichment.domain.model.Moves;

@RestController
@RequestMapping("/moves")
public class MovesController {
	@Autowired
	private MovesDao movesDao;

	@RequestMapping(method= RequestMethod.GET) //root
	public @ResponseBody List<Moves> index() {
		return movesDao.indexMoves();
	}
	
	@RequestMapping(value = "/byId/{pk}", method = RequestMethod.GET)
	public @ResponseBody Moves byId(@PathVariable Short pk) {
		return movesDao.getMoveById(pk);
	}
}