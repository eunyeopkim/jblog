package com.douzone.jblog.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;
import com.douzone.jblog.vo.UserVo;

@Repository
public class BlogRepository {

	@Autowired
	private SqlSession sqlSession;

	//join시 자동 insert
	public int blogInsert(UserVo vo) {
		return sqlSession.insert("blog.blogInsert", vo);
	}

	//Basic title update
	public int update(BlogVo vo) {
		return sqlSession.update("blog.update", vo);
	}

	//Blog title, logo
	public BlogVo getBlog(String id) {
		return sqlSession.selectOne("blog.getBlog", id);
	}

	//Category
	public List<CategoryVo> getCategory(Long categoryNo) {
		return sqlSession.selectList("blog.getCategory", categoryNo);
	}
	public List<CategoryVo> categoryList(String id) {
		return sqlSession.selectList("blog.categoryList", id);
	}
	public int categoryDelete(Long no) {
		return sqlSession.delete("blog.deleteCategory", no);
	}

	//Post
	public List<PostVo> getPost(Long postNo) {
		return sqlSession.selectList("blog.getPost", postNo);
	}

	

	

}
