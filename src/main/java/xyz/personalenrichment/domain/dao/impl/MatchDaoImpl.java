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

import xyz.personalenrichment.domain.dao.MatchesDao;
import xyz.personalenrichment.domain.model.Matches;

@Transactional
@Repository
public class MatchesDaoImpl implements MatchesDao {
	private static Logger log = LoggerFactory.getLogger(MatchesDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<Matches> readMatches() {
		Session s = sessionFactory.getCurrentSession();
		
		String hql = "from Matches";
		Query q = s.createQuery(hql);	

		return q.list();
	}

	@Override
	public Matches readMatch(Integer pk) {
		return sessionFactory.getCurrentSession().get(Matches.class, pk);
	}

	@Override
	public Matches createMatch(Matches match) {
		Serializable s = sessionFactory.getCurrentSession().save(match);

		return sessionFactory.getCurrentSession().get(Matches.class, s);
	}

	@Override
	public Matches updateMatch(Integer pk, Matches match) {
		match.setIdmatches(pk);
		
		sessionFactory.getCurrentSession().update(match);
		
		return sessionFactory.getCurrentSession().get(Matches.class, pk);
	}

	@Override
	public Matches deleteMatch(Integer pk) {
		Matches m = sessionFactory.getCurrentSession().get(Matches.class, pk);
		
		sessionFactory.getCurrentSession().delete(m);
		
		return m;
	}
	
}