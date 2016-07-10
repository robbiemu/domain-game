package xyz.personalenrichment.domain.controller;

import java.util.List;

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

	@RequestMapping(value = "/{pk}", method = RequestMethod.GET)
	public @ResponseBody Move getMove(@PathVariable Integer pk) {
		return moveDao.readMove(pk);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public @ResponseBody Move postMove(@RequestBody Move move) {
		return moveDao.createMove(move);
	}
	
	@RequestMapping(value = "/{pk}", method = RequestMethod.PATCH)
	public @ResponseBody Move patchMove(@PathVariable Integer pk, @RequestBody Move move) {
		return moveDao.updateMove(pk, move);
	}	
	
	@RequestMapping(value = "/{pk}", method = RequestMethod.DELETE)
	public @ResponseBody Move deleteMove(@PathVariable Integer pk) {
		return moveDao.deleteMove(pk);
	}
	
	@RequestMapping(value = "/{pk}/match", method = RequestMethod.GET)
	public @ResponseBody Match getMatch(@PathVariable Integer pk) {
		return moveDao.readMatch(pk);
	}
	
	@RequestMapping(value = "/{pk}/player", method = RequestMethod.GET)
	public @ResponseBody User getPlayer(@PathVariable Integer pk) {
		return moveDao.readPlayer(pk);
	}	
	
	@RequestMapping(value = "/{pk}/match", method = RequestMethod.PUT)
	public @ResponseBody Match putMatch(@PathVariable Integer pk) {
		return moveDao.createMatch(pk);
	}
	
	@RequestMapping(value = "/{pk}/player", method = RequestMethod.PUT)
	public @ResponseBody User putPlayer(@PathVariable Integer pk) {
		return moveDao.createPlayer(pk);
	}	
	
	@RequestMapping(value = "/{pk}/match", method = RequestMethod.DELETE)
	public @ResponseBody Match deleteMatch(@PathVariable Integer pk) {
		return moveDao.deleteMatch(pk);
	}
	
	@RequestMapping(value = "/{pk}/player", method = RequestMethod.DELETE)
	public @ResponseBody User deletePlayer(@PathVariable Integer pk) {
		return moveDao.deletePlayer(pk);
	}	
}