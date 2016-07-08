package xyz.personalenrichment.domain.dao.impl;

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
	public List<Matches> indexMatches() {
		Session s = sessionFactory.getCurrentSession();
		
		String hql = "from Matches";
		Query q = s.createQuery(hql);	

		return q.list();
	}

	@Override
	public Matches getMatchById(Short pk) {
		return sessionFactory.getCurrentSession().get(Matches.class, pk);
	}
	
}