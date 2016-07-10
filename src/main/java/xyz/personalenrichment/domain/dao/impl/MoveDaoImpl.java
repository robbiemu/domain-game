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

import xyz.personalenrichment.domain.dao.MoveDao;
import xyz.personalenrichment.domain.model.Match;
import xyz.personalenrichment.domain.model.Move;
import xyz.personalenrichment.domain.model.User;

@Transactional
@Repository
public class MoveDaoImpl implements MoveDao {
	private static Logger log = LoggerFactory.getLogger(MoveDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Move readMove(Integer pk) {
		return sessionFactory.getCurrentSession().get(Move.class, pk);
	}

	@Override
	public Move createMove(Move move) {
		Serializable s = sessionFactory.getCurrentSession().save(move);

		return sessionFactory.getCurrentSession().get(Move.class, s);
	}

	@Override
	public Move updateMove(Integer pk, Move move) {
		move.setIdmove(pk);
		
		sessionFactory.getCurrentSession().update(move);
		
		return sessionFactory.getCurrentSession().get(Move.class, pk);

	}

	@Override
	public Move deleteMove(Integer pk) {
		Move m = sessionFactory.getCurrentSession().get(Move.class, pk);
		
		sessionFactory.getCurrentSession().delete(m);
		
		return m;
	}

	@Override
	public Match readMatch(Integer pk) {
		Session s = sessionFactory.getCurrentSession();
		
		String hql = "select mo.match from Move mo where mo.matchid = :matchid";
		Query q = s.createQuery(hql);
		q.setInteger("matchId", pk);

		return (Match) q.list().get(0);
	}

	@Override
	public User readPlayer(Integer pk) {
		Session s = sessionFactory.getCurrentSession();
		
		String hql = "select mo.user from Move mo where mo.matchid = :matchid";
		Query q = s.createQuery(hql);
		q.setInteger("matchId", pk);

		return (User) q.list().get(0);	}

	@Override
	public Match createMatch(Integer pk) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User createPlayer(Integer pk) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Match deleteMatch(Integer pk) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User deletePlayer(Integer pk) {
		// TODO Auto-generated method stub
		return null;
	}
	
}