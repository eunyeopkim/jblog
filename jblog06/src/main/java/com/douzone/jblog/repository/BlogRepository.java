package com.douzone.jblog.repository;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.BlogVo;
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

}
