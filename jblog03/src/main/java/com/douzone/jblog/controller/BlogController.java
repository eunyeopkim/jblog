package com.douzone.jblog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.jblog.service.BlogService;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;
import com.douzone.security.AuthUser;

@Controller
@RequestMapping("/{id:(?!assets).*}")
public class BlogController {
	
	@Autowired
	private BlogService blogService;
	
	@RequestMapping( {"", "/{pathNo1}", "/{pathNo1}/{pathNo2}" } )
	public String main( 
		@PathVariable String id,
		@PathVariable Optional<Long> pathNo1,
		@PathVariable Optional<Long> pathNo2,
		ModelMap modelMap) {

		Long categoryNo = 0L;
		Long postNo = 0L;
		
		if( pathNo2.isPresent() ) {
			
			postNo = pathNo2.get();
			categoryNo = pathNo1.get();
		} else if( pathNo1.isPresent() ){
			categoryNo = pathNo1.get();
		}
		
		modelMap.putAll( blogService.getAll( id, categoryNo, postNo ) );
		return "blog/blog-main";
	}
	
	@RequestMapping(value="/admin/basic", method=RequestMethod.GET )
	public String adminBasic(@PathVariable String id) {
		return "blog/blog-admin-basic";
	}	
	
	@RequestMapping(value="/admin/basic", method=RequestMethod.POST )
	public String adminBasic(
			@PathVariable String id,
			@AuthUser BlogVo authUser,
			@ModelAttribute BlogVo blogVo,
			@RequestParam(value="logo-file") MultipartFile multipartFile,
			Model model) {
			
			String url = blogService.restore(blogVo,multipartFile);
			blogVo = blogService.findById(authUser.getId());
			model.addAttribute("url", url);
			model.addAttribute("blogVo",blogVo);
			
			return "redirect:/"+ id;
	}
	
	
	@RequestMapping(value="/admin/category", method=RequestMethod.GET )
	public String adminCategory(
			@AuthUser BlogVo authUser,
			@ModelAttribute CategoryVo categoryVo,
			Model model) {
			
			categoryVo.setId(authUser.getId());
			List<CategoryVo> categoryList = blogService.categoryList(categoryVo.getId());
			model.addAttribute("categoryList",categoryList);
			
		return "blog/blog-admin-category";
	}
	@RequestMapping(value="/admin/insert",method = RequestMethod.POST)
	public String adminCategoryInsert(
			@PathVariable String id,
			@AuthUser BlogVo authUser,
			@RequestParam(value="name") String name,
			@RequestParam(value="desc") String description,
			Model model) {
			
			CategoryVo categoryVo = new CategoryVo();
			categoryVo.setId(authUser.getId());
			categoryVo.setName(name);
			categoryVo.setDescription(description);
			blogService.categoryNewInsert(categoryVo);
		
			return "redirect:/"+id+"/admin/category";
			
	}
	
	@RequestMapping(value="/admin/delete/{no}",method = RequestMethod.GET)
	public String adminCategoryDelete(
			@PathVariable String id,
			@PathVariable("no") Long no,
			@ModelAttribute CategoryVo categoryVo) {
			
			
			int count = blogService.categoryCount(id);
			if(count > 1) {
				blogService.categoryDelete(no);
			}
			return "redirect:/"+id+"/admin/category";
	}
	@RequestMapping(value="/admin/postInsert", method = RequestMethod.POST)
	public String adminPostInsert(
			@PathVariable String id,
			@AuthUser BlogVo authUser,
			@ModelAttribute CategoryVo categoryVo, 
			@ModelAttribute PostVo postVo,
			Model model) {
		
			categoryVo.setId(authUser.getId());			
			blogService.postNewInsert(postVo);	
			
		return "redirect:/"+id+"/admin/category";
	}
	
	@RequestMapping(value= "/admin/write", method=RequestMethod.GET )
	public String adminWrite( 
			@PathVariable String id ,
			@ModelAttribute CategoryVo categoryVo,
			Model model) {
		
			List<CategoryVo> categoryList = blogService.categoryList(categoryVo.getId());
			model.addAttribute("categoryList",categoryList);
			
		return "blog/blog-admin-write";
	}
	
	
}
