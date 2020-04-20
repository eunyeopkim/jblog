package com.douzone.jblog.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.jblog.dto.JsonResult;
import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.security.AuthUser;

@RestController("JblogApiController")
@RequestMapping("/{id:(?!assets).*}/api")
public class BlogController {
	
	@Autowired
	private BlogService blogService;
	
	@GetMapping("/admin/category")
	public JsonResult adminCategory(
			@AuthUser BlogVo authUser,
			@ModelAttribute CategoryVo categoryVo) {
			
			categoryVo.setId(authUser.getId());
			List<CategoryVo> categoryList = blogService.categoryList(categoryVo.getId());

			
		return JsonResult.success(categoryList);
	}
	
	@PostMapping("/add")
	public JsonResult adminCategoryInsert(
			@RequestBody CategoryVo categoryVo,
			@PathVariable String id) {
			categoryVo.setId(id);
			blogService.categoryNewInsert(categoryVo);
			return JsonResult.success(categoryVo);
			
	}
	@DeleteMapping("/delete/{no}")
	public JsonResult adminCategoryDelete(
			@PathVariable String id,
			@PathVariable("no") Long no) {
			
			boolean result = false;
			int count = blogService.categoryCount(id);
			if(count > 1) {
				result = blogService.categoryDelete(no);
			}
			return JsonResult.success(result? no : -1);
	}
}
