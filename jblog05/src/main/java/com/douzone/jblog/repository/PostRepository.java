package com.douzone.jblog.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.PostVo;

@Repository
public class PostRepository {
	@Autowired
	private SqlSession sqlSession;
	//Post
	public List<PostVo> getPost(Map<String, Object> map) {
		return sqlSession.selectList("post.getPost", map);
	}

	public int postNewInsert(PostVo postVo) {
		return sqlSession.insert("post.postInsert", postVo);
	}

	public PostVo getPostContents(Map<String, Object> map) {
		return sqlSession.selectOne("post.getPostContents", map);
	}
	
}
