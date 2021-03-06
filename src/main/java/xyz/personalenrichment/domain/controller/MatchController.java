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

import xyz.personalenrichment.domain.dao.MatchDao;
import xyz.personalenrichment.domain.model.Match;
import xyz.personalenrichment.domain.model.Move;
import xyz.personalenrichment.domain.model.User;

@RestController
@CrossOrigin(methods = {RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PATCH, RequestMethod.PUT})
@RequestMapping("/match")
public class MatchController {
	@Autowired
	private MatchDao matchDao;

	
	/* collections */
	// read matches
	@RequestMapping(method= RequestMethod.GET) //root
	public @ResponseBody List<Match> getMatches() {
		return matchDao.readMatches();
	}
	
	/* units */
	// read a match
	@RequestMapping(value = "/{pk}", method = RequestMethod.GET)
	public @ResponseBody Match getMatch(@PathVariable Integer pk) {
		return matchDao.readMatch(pk);
	}
	
	// create a new match
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody Match postMatch(@RequestBody Match match) {
		return matchDao.createMatch(match);
	}
	
	// update an existing match
	@RequestMapping(value = "/{pk}", method = RequestMethod.PATCH)
	public @ResponseBody Match patchMatch(@PathVariable Integer pk, @RequestBody Match match) {
		return matchDao.updateMatch(pk, match);
	}	
	
	// delete a match
	@RequestMapping(value = "/{pk}", method = RequestMethod.DELETE)
	public @ResponseBody Match deleteMatch(@PathVariable Integer pk) {
		return matchDao.deleteMatch(pk);
	}

	/* relations */
	// get moves list for a match
	@RequestMapping(value = "/{pk}/moves", method = RequestMethod.GET)
	public @ResponseBody List<Move> getMoves(@PathVariable Integer pk) {
		return matchDao.readMoves(pk);
	}

	// get winner of a match
	@RequestMapping(value = "/{pk}/winner", method = RequestMethod.GET)
	public @ResponseBody User getWinner(@PathVariable Integer pk) {
		return matchDao.readWinner(pk);
	}

	// get players of a match
	@RequestMapping(value = "/{pk}/players", method = RequestMethod.GET)
	public @ResponseBody List<User> getPlayers(@PathVariable Integer pk) {
		return matchDao.readPlayers(pk);
	}

	// associate a user as a winner of a match
	@RequestMapping(value = "/{pk}/winner/{upk}", method = RequestMethod.PUT)
	public @ResponseBody Match putMove(@PathVariable Integer pk, @PathVariable Integer upk){
		return matchDao.createWinner(pk, upk);
	}
	
	// associate a user as a player of a match
	@RequestMapping(value = "/{pk}/players/{upk}", method = RequestMethod.PUT)
	public @ResponseBody Match putPlayer(@PathVariable Integer pk, @PathVariable Integer upk){
		return matchDao.createPlayer(pk, upk);
	}

	// remove association of a user as a winner of a match
	@RequestMapping(value = "/{pk}/winner/{upk}", method = RequestMethod.DELETE)
	public @ResponseBody Match deleteMove(@PathVariable Integer pk, @PathVariable Integer upk){
		return matchDao.deleteWinner(pk, upk);
	}

	// remove association of a user as a winner of a match
	@RequestMapping(value = "/{pk}/players/{upk}", method = RequestMethod.DELETE)
	public @ResponseBody Match deletePlayer(@PathVariable Integer pk, @PathVariable Integer upk){
		return matchDao.deletePlayer(pk, upk);
	}
}