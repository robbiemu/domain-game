package xyz.personalenrichment.domain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import xyz.personalenrichment.domain.dao.MatchesDao;
import xyz.personalenrichment.domain.model.Matches;

@RestController
@RequestMapping("/matches")
public class MatchesController {
	@Autowired
	private MatchesDao matchesDao;

	@RequestMapping(method= RequestMethod.GET) //root
	public @ResponseBody List<Matches> index() {
		return matchesDao.indexMatches();
	}
	
	@RequestMapping(value = "/byId/{pk}", method = RequestMethod.GET)
	public @ResponseBody Matches byId(@PathVariable Short pk) {
		return matchesDao.getMatchById(pk);
	}
}