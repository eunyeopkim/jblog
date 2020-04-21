package com.douzone.jblog.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.UserVo;

@Repository
public class CategoryRepository {

	@Autowired
	private SqlSession sqlSession;
	
	//Category
	public List<CategoryVo> getCategory(String id) {
		return sqlSession.selectList("category.getCategory", id);
	}
	public List<CategoryVo> categoryList(String id) {
		return sqlSession.selectList("category.categoryList", id);
	}
	
	public int categoryInsert(UserVo vo) {
		return sqlSession.insert("category.categoryInsert", vo);
	}

	public int categoryNewInsert(CategoryVo categoryVo) {
		return sqlSession.insert("category.categoryNewInsert", categoryVo);
	}
	public int categoryDelete(Long no) {
		return sqlSession.delete("category.categoryDelete", no);
	}
	public int categoryCount(String id) {
		return sqlSession.selectOne("category.categoryCount", id);
	}


}
