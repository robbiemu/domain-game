package xyz.personalenrichment.domain.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import xyz.personalenrichment.domain.dao.MatchDao;
import xyz.personalenrichment.domain.model.Match;
import xyz.personalenrichment.domain.model.MatchPlayerJt;
import xyz.personalenrichment.domain.model.Move;
import xyz.personalenrichment.domain.model.User;

@Transactional
@Repository
public class MatchDaoImpl implements MatchDao {
	private static Logger log = LoggerFactory.getLogger(MatchDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<Match> readMatches() {
		Session s = sessionFactory.getCurrentSession();
		
		String hql = "from Matches";
		Query q = s.createQuery(hql);	

		return q.list();
	}

	@Override
	public Match readMatch(Integer pk) {
		return sessionFactory.getCurrentSession().get(Match.class, pk);
	}

	@Override
	public Match createMatch(Match match) {
		Serializable s = sessionFactory.getCurrentSession().save(match);

		return sessionFactory.getCurrentSession().get(Match.class, s);
	}

	@Override
	public Match updateMatch(Integer pk, Match match) {
		match.setIdmatch(pk);
		
		sessionFactory.getCurrentSession().update(match);
		
		return sessionFactory.getCurrentSession().get(Match.class, pk);
	}

	@Override
	public Match deleteMatch(Integer pk) {
		Match m = sessionFactory.getCurrentSession().get(Match.class, pk);
		
		sessionFactory.getCurrentSession().delete(m);
		
		return m;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Move> readMoves(Integer pk) {
		Session s = sessionFactory.getCurrentSession();
		
		String hql = "from Move where matchid = :matchid";
		Query q = s.createQuery(hql);
		q.setInteger("matchId", pk);

		return q.list();
	}

	@Override
	public Match createWinner(Integer pk, Integer upk) {
		Match m = sessionFactory.getCurrentSession().get(Match.class, pk);
		User w = sessionFactory.getCurrentSession().get(User.class, upk);
		m.setUser(w);
		return m;
	}

	@Override
	public Match deleteWinner(Integer pk, Integer upk) {
		Match m = sessionFactory.getCurrentSession().get(Match.class, pk);
		m.setUser(null);
		return m;
	}

	@Override
	public User readWinner(Integer pk) {
		Session s = sessionFactory.getCurrentSession();
		
		String hql = "select ma.winner from Match ma where ma.matchid = :matchid";
		Query q = s.createQuery(hql);
		q.setInteger("matchId", pk);

		return (User) q.list().get(0);
	}

	// lists players of a match
	@SuppressWarnings("unchecked")
	@Override
	public List<User> readPlayers(Integer pk) {
		Session s = sessionFactory.getCurrentSession();
		
		String hql = "select mp.user from MatchPlayerJt mp where mp.match.matchid = :matchId";
		Query q = s.createQuery(hql);
		q.setInteger("matchId", pk);

		return q.list();
	}

	// adds a player to a match
	@Override
	public Match createPlayer(Integer pk, Integer upk) {
		Session s = sessionFactory.getCurrentSession();
		
		MatchPlayerJt mp = new MatchPlayerJt();
		Match m = s.get(Match.class, pk);

		mp.setMatch(m);
		mp.setUser(s.get(User.class, upk));

		s.save(mp);

		return m;
	}

	// removes the player from the match
	@Override
	public Match deletePlayer(Integer pk, Integer upk) {
		Session s = sessionFactory.getCurrentSession();

		Match m = s.get(Match.class, pk);
		User p = s.get(User.class, upk);
		
		String hql = "from MatchPlayerJt mp where mp.match.matchid = :matchId and mp.user.userid = :userid";
		Query q = s.createQuery(hql);
		q.setInteger("matchId", pk);
		q.setInteger("userId", upk);

		MatchPlayerJt mp = (MatchPlayerJt) q.list().get(0);

		s.delete(mp);
		
		return m;
	}
	
}