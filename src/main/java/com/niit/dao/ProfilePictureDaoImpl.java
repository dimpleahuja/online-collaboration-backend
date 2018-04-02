package com.niit.dao;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.model.ProfilePicture;

@Repository
@Transactional
public class ProfilePictureDaoImpl implements ProfilePictureDao {
	@Autowired
private SessionFactory sessionFactory;
	public void uploadProfilePicture(ProfilePicture profilePicture) {
		Session session=sessionFactory.getCurrentSession();
		//insert into profilepicture values (?,?)
		//or
		//update profilepicture set image=? where email=?
		session.saveOrUpdate(profilePicture);
	}
	public ProfilePicture getImage(String email) {
		Session session=sessionFactory.getCurrentSession();
		//select * from profilepicture where email=?
		ProfilePicture profilePicture=(ProfilePicture)session.get(ProfilePicture.class, email);
		return profilePicture;
	}

}
