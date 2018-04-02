package com.niit.dao;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.niit.model.BlogPost;
import com.niit.model.BlogPostLikes;
import com.niit.model.User;
@Repository
@Transactional
public class BlogPostLikesDaoImpl implements BlogPostLikesDao {
	@Autowired
private SessionFactory sessionFactory;
	public BlogPostLikes hasUserLikedBlog(int blogId, String email) {
		Session session=sessionFactory.getCurrentSession();
		//select * from blogpostlikes_s180250 where blogpost_id=? and user_email=?
		Query query=session.createQuery("from BlogPostLikes where blogPost.id=? and user.email=?");
		query.setInteger(0, blogId);
		query.setString(1, email);
		//if blog is not liked by the user , query returns null
		//if blog is liked by the user,query return 1 blogPostlikes object
		BlogPostLikes blogPostLikes=(BlogPostLikes)query.uniqueResult();
		return blogPostLikes;
	}
	public BlogPost updateLikes(int id,String email) {//id is blogpost id
     		//insert or delete from blogpostlikes 
		Session session=sessionFactory.getCurrentSession();
		BlogPostLikes blogPostLikes=hasUserLikedBlog(id,email);
		 BlogPost blogPost=(BlogPost)session.get(BlogPost.class, id);
		if(blogPostLikes==null){//insert - like
			 blogPostLikes=new BlogPostLikes();	
			 User user=(User)session.get(User.class,email);
			 blogPostLikes.setBlogPost(blogPost);
			 blogPostLikes.setUser(user);
			 session.save(blogPostLikes);//insert into blogpostlikes
			 blogPost.setLikes(blogPost.getLikes() +  1);
			 session.update(blogPost);//update blogpost set likes=likes+1 where id=?
		}else{//delete - unliked
			session.delete(blogPostLikes);//delete from blogpostlikes where id=? id is likeid
			 blogPost.setLikes(blogPost.getLikes() - 1);
			 session.update(blogPost);//update blogpost set likes=likes-1 where id=?
		}
		return blogPost;
	}

}


