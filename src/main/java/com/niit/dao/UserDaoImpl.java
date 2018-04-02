package com.niit.dao;


import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.model.User;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {
	@Autowired
private SessionFactory sessionFactory;
	public UserDaoImpl(){
		System.out.println("UserDaoImpl bean is created");
	}
	public void registerUser(User user) {
		System.out.println("registerUser in Dao" + user);
		Session session=sessionFactory.getCurrentSession();
		session.save(user);
		//insert into user_s180250 values (email,firstname,lastname,phonenumber,online_status,password)
		
	}
	public boolean isEmailUnique(String email) {// email - input from new user
		Session session=sessionFactory.getCurrentSession();
		// generate the query
		// select * from User_s180250 where email=?
		User user=(User)session.get(User.class, email);
		//1 object or null value
		//user is null if email entered by the new user is unique-(correct)
		//user is not null,email entered by the user is already exists-(incorrect)
		if(user==null)
			return true;
		else
			return false;
	}
	public User login(User user) {
		Session session= sessionFactory.getCurrentSession();
		// parameter position are                0(string)      1(string)
		Query query=session.createQuery("from User where email=? and password=?");
		query.setString(0, user.getEmail());
		query.setString(1, user.getPassword());
		return (User)query.uniqueResult();//1 object or null values
		// null for invalid credentials
	}
	public void update(User validUser) {
		Session session=sessionFactory.getCurrentSession();
		session.update(validUser);//update User_s180250 set online_status=1 where email=?
	}
	public User getUser(String email) {
		Session session=sessionFactory.getCurrentSession();
		User user=(User)session.get(User.class, email);
		return user;
	}
	public void updateUser(User user) {
		Session session=sessionFactory.getCurrentSession();
		session.update(user);//update User_s180250 set .... where email=?
		
	}

}
