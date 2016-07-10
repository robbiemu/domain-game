package xyz.personalenrichment.domain.dao.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import xyz.personalenrichment.domain.Tuple;
import xyz.personalenrichment.domain.Util;
import xyz.personalenrichment.domain.dao.UserDao;
import xyz.personalenrichment.domain.model.Match;
import xyz.personalenrichment.domain.model.Move;
import xyz.personalenrichment.domain.model.User;
import xyz.personalenrichment.domain.tx.DBTXResponse;

@Transactional
@Repository
public class UserDaoImpl implements UserDao {
	private static Logger log = LoggerFactory.getLogger(UserDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<User> readUsers() {
		Session s = sessionFactory.getCurrentSession();
		
		String hql = "from Users";
		Query q = s.createQuery(hql);	

		return q.list();
	}

	@Override
	public User readUser(Integer pk) {
		return sessionFactory.getCurrentSession().get(User.class, pk);
	}

	@Override
	public User createUser(User user) {
		Serializable s = sessionFactory.getCurrentSession().save(user);

		return sessionFactory.getCurrentSession().get(User.class, s);
	}

	@Override
	public User updateUser(Integer pk, User user) {
		user.setIduser(pk);
		
		sessionFactory.getCurrentSession().update(user);
		
		return sessionFactory.getCurrentSession().get(User.class, pk);
	}

	@Override
	public User deleteUser(Integer pk) {
		User u = sessionFactory.getCurrentSession().get(User.class, pk);
		
		sessionFactory.getCurrentSession().delete(u);
		
		return u;
	}

	@Override
	public List<Move> readMatches(Integer pk) {
		Session s = sessionFactory.getCurrentSession();
		
//		String hql = "from Match where matchid = :matchid";
		Query q = s.createQuery(hql);
		q.setInteger("matchId", pk);

		return q.list();

	}
	
}
