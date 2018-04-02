package com.niit.dao;

import java.util.List;

import com.niit.model.BlogComment;
import com.niit.model.BlogPost;

public interface BlogPostDao {
void addBlogPost(BlogPost blogPost);
List<BlogPost> listOfBlogs(int approved);
//blogPostDao.listOfBlogs(1)  ->select * from blogpost where approved=1
//List of blogs which are approved
BlogPost getBlog(int id);
void approve(BlogPost blog);
void reject(BlogPost blog,String rejectionReason);

//blogPostDao.listOfBlogs(0) -> select * from blogpost where approved=0
//list of blogs which are waiting for approval

void addBlogComment(BlogComment blogComment);
List<BlogComment> getAllBlogComments(int blogPostId);//blogpost id
}



























/*package com.niit.dao;

import java.util.List;

import com.niit.model.BlogPost;

public interface BlogPostDao {
void addBlogPost(BlogPost blogPost);
	List<BlogPost> listOfBlogs(int approved);
// blogPostDao.listOfBlog(1) -> select * from blogpost where approved=1
//List of blogs which are approved
	BlogPost getBlog(int id);
	
//blogPostDao.listOfBlogs(0) -> select * from blogpost where approved=0
//list of blogs which are waiting for approval
}
*/