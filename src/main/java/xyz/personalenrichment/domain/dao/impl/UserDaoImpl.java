package xyz.personalenrichment.domain.dao.impl;

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
import xyz.personalenrichment.domain.dao.UsersDao;
import xyz.personalenrichment.domain.model.Users;
import xyz.personalenrichment.domain.tx.DBTXResponse;

@Transactional
@Repository
public class UsersDaoImpl implements UsersDao {
	private static Logger log = LoggerFactory.getLogger(UsersDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public List<Users> indexUsers() {
		Session s = sessionFactory.getCurrentSession();
		
		String hql = "from Users";
		Query q = s.createQuery(hql);	

		return q.list();
	}

	@Override
	public Users getUserById(Short pk) {
		return sessionFactory.getCurrentSession().get(Users.class, pk);
	}
	
	@Override
	public DBTXResponse deleteUser(Short pk) {
		sessionFactory.getCurrentSession().delete(pk);
		return new DBTXResponse(isUserIdPresent(pk), Users.class.getSimpleName(), 
				pk.toString(), DBTXResponse.DELETE);
	}

	private Boolean isUserIdPresent(Short pk) {
		return (null != sessionFactory.getCurrentSession().get(Users.class, pk));
	}

	@Override
	public Users updateUser(Short pk, String criteria) {
		Users updateUser = getUserById(pk);
		
		Map<String, Tuple<Class<?>, Object>> m = Util.modelParamsFromCriteria(Users.class, criteria);
		String key;
		try {
			for(Entry<String, Tuple<Class<?>, Object>> e: m.entrySet()) {
				key = e.getKey();
				Tuple<Class<?>, Object> t = e.getValue();
				
				Method um = Users.class.getMethod(
							"set" + 
								key.substring(0, 1).toUpperCase() + 
								key.substring(1), 
							t.getLeft());
				um.invoke(updateUser, t.getLeft().cast(t.getRight()));
			}
		} catch (NoSuchMethodException | IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException | IllegalAccessException | 
				InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		sessionFactory.getCurrentSession().update(updateUser);

		return updateUser;
	}
	
}
