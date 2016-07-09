package xyz.personalenrichment.domain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import xyz.personalenrichment.domain.dao.MatchesDao;
import xyz.personalenrichment.domain.model.Matches;
import xyz.personalenrichment.domain.model.Moves;

@RestController
@CrossOrigin(methods = {RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.PUT})
@RequestMapping("/matches")
public class MatchesController {
	@Autowired
	private MatchesDao matchesDao;

	
	/* collections */
	@RequestMapping(method= RequestMethod.GET) //root
	public @ResponseBody List<Matches> getMatches() {
		return matchesDao.readMatches();
	}
	
	/* units */
	@RequestMapping(value = "/{pk}", method = RequestMethod.GET)
	public @ResponseBody Matches getMatch(@PathVariable Integer pk) {
		return matchesDao.readMatch(pk);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody Matches postMatch(@RequestBody Matches match) {
		return matchesDao.createMatch(match);
	}
	
	@RequestMapping(value = "/{pk}", method = RequestMethod.PATCH)
	public @ResponseBody Matches patchMatch(@PathVariable Integer pk, @RequestBody Matches match) {
		return matchesDao.updateMatch(pk, match);
	}	
	
	@RequestMapping(value = "/{pk}", method = RequestMethod.DELETE)
	public @ResponseBody Matches deleteMatch(@PathVariable Integer pk) {
		return matchesDao.deleteMatch(pk);
	}

	/* relations */
	@RequestMapping(value = "/{pk}/moves", method = RequestMethod.GET)
	public @ResponseBody List<Moves> getMoves(@PathVariable Integer pk) {
		return matchesDao.readMoves(pk);
	}
	
	@RequestMapping(value = "/{pk}/moves", method = RequestMethod.PUT)
	public @ResponseBody List<Moves> putMove(@PathVariable Integer pk, @RequestBody Moves move) {
		return movesDao.createMoveWithMatch(pk, move);
	}
	
}