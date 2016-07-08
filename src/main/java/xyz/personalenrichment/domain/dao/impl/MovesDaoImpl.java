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

import xyz.personalenrichment.domain.dao.MovesDao;
import xyz.personalenrichment.domain.model.Moves;

@Transactional
@Repository
public class MovesDaoImpl implements MovesDao {
	private static Logger log = LoggerFactory.getLogger(MovesDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<Moves> indexMoves() {
		Session s = sessionFactory.getCurrentSession();
		
		String hql = "from Moves";
		Query q = s.createQuery(hql);	

		return q.list();
	}

	@Override
	public Moves getMoveById(Short pk) {
		return sessionFactory.getCurrentSession().get(Moves.class, pk);
	}
	
}