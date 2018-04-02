package com.niit.dao;

import java.util.List;


import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.model.Friend;
import com.niit.model.User;

@Repository
@Transactional
public class FriendDaoImpl implements FriendDao {
	@Autowired
private SessionFactory sessionFactory;
	public List<User> suggestedUsers(String email) {
		Session session=sessionFactory.getCurrentSession();
		//query - S in (A MINUS (B UNION C))
		//S - select * from user_s180250 where email
		//A - select toId_email from user_s180250 where email=?
		//B - select toId_email from friend_s180250 where fromId_email=?
		//C - select fromId_email from friend_s180250 where toId_email=?
		String queryString="select * from user_s180250 where email in "+ "(select email from user_s180250 where email!=? "+ "minus"+ "(select toId_email from friend_s180250 where fromId_email=? "+ "union "+ "select fromId_email from friend_s180250 where toId_email=? )) ";//friend requests from other users to loogedIn user
		SQLQuery query=session.createSQLQuery(queryString);
		query.setString(0, email);
		query.setString(1, email);
		query.setString(2, email);
		query.addEntity(User.class);
		List<User> suggestedUsers=query.list();
		return suggestedUsers;
	}
	public void addFriend(Friend friend) {
		Session session=sessionFactory.getCurrentSession();
		session.save(friend);//insert into friend values (?,fromId.toId,'P')
		
	}
	public List<Friend> pendingRequests(String toIdEmail) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from Friend where status=? and toId.email=?");
		query.setCharacter(0, 'P');
		query.setString(1, toIdEmail);
		List<Friend> pendingRequests=query.list();
		return pendingRequests;
	}
	public void acceptRequest(Friend request) {
		//request.getStatus() -> 'P'
		Session session=sessionFactory.getCurrentSession();
		request.setStatus('A');
		//update friend_s180250 set status='A' where id=?
		session.update(request);
	}
	public void deleteRequest(Friend request) {
		Session session=sessionFactory.getCurrentSession();
		//delete from friend_s180250 where id=?
		//only 1 row will get deleted
		session.delete(request);
	}
	public List<Friend> listOfFriends(String email) {
		Session session=sessionFactory.getCurrentSession();
		Query query1=session.createQuery("select f.toId from Friend f where f.fromId.email=? and status=?");
		query1.setString(0, email);
		query1.setCharacter(1, 'A');
		List<Friend> friendsList1=query1.list();
		Query query2=session.createQuery("select f.fromId from Friend f where f.toId.email=? and f.status=?");
		query2.setString(0, email);
		query2.setCharacter(1, 'A');
		List<Friend> friendsList2=query2.list();
		friendsList1.addAll(friendsList2);
		return friendsList1;
	}

}
