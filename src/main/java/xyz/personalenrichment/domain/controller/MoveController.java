package xyz.personalenrichment.domain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import xyz.personalenrichment.domain.dao.MoveDao;
import xyz.personalenrichment.domain.model.Match;
import xyz.personalenrichment.domain.model.Move;
import xyz.personalenrichment.domain.model.User;

@RestController
@RequestMapping("/move")
public class MoveController {
	@Autowired
	private MoveDao moveDao;

	// read a move by id
	@RequestMapping(value = "/{pk}", method = RequestMethod.GET)
	public @ResponseBody Move getMove(@PathVariable Integer pk) {
		return moveDao.readMove(pk);
	}

	// create a raw move (no associated match)
	@RequestMapping(value = "/match", method = RequestMethod.POST)
	public @ResponseBody Move postMove(@RequestBody Move move) {
		return moveDao.createMove(move);
	}
	
	// create a move with an associated match
	@RequestMapping(value = "/match/{mpk}", method = RequestMethod.POST)
	public @ResponseBody Move postMove(@PathVariable Integer mpk, @RequestBody Move move) {
		return moveDao.createMove(mpk, move);
	}
	
	// update a move
	@RequestMapping(value = "/{pk}", method = RequestMethod.PATCH)
	public @ResponseBody Move patchMove(@PathVariable Integer pk, @RequestBody Move move) {
		return moveDao.updateMove(pk, move);
	}	

	// delete a move
	@RequestMapping(value = "/{pk}", method = RequestMethod.DELETE)
	public @ResponseBody Move deleteMove(@PathVariable Integer pk) {
		return moveDao.deleteMove(pk);
	}
	
	// read the match associated with a move
	@RequestMapping(value = "/{pk}/match", method = RequestMethod.GET)
	public @ResponseBody Match getMatch(@PathVariable Integer pk) {
		return moveDao.readMatch(pk);
	}

	// read player playing the move
	@RequestMapping(value = "/{pk}/player", method = RequestMethod.GET)
	public @ResponseBody User getPlayer(@PathVariable Integer pk) {
		return moveDao.readPlayer(pk);
	}	
	
	// associate the move to a match
	@RequestMapping(value = "/{pk}/match", method = RequestMethod.PUT)
	public @ResponseBody Move putMatch(@PathVariable Integer pk, @RequestBody Match match) {
		return moveDao.createMatch(pk, match);
	}
	
	// associate the move to a player playing the move
	@RequestMapping(value = "/{pk}/player", method = RequestMethod.PUT)
	public @ResponseBody Move putPlayer(@PathVariable Integer pk, @RequestBody User user) {
		return moveDao.createPlayer(pk, user);
	}	
	
	// delete the association of the move to its match
	@RequestMapping(value = "/{pk}/match", method = RequestMethod.DELETE)
	public @ResponseBody Move deleteMatch(@PathVariable Integer pk) {
		return moveDao.deleteMatch(pk);
	}
	
	// delete the association of the move to its player
	@RequestMapping(value = "/{pk}/player", method = RequestMethod.DELETE)
	public @ResponseBody Move deletePlayer(@PathVariable Integer pk) {
		return moveDao.deletePlayer(pk);
	}	
}