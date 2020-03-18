package com.douzone.jblog.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import com.douzone.jblog.repository.BlogRepository;
import com.douzone.jblog.vo.BlogVo;
import com.douzone.jblog.vo.CategoryVo;
import com.douzone.jblog.vo.PostVo;


@Service
public class BlogService {
	private static final String SAVE_PATH = "/jblog-uploads";
	private static final String URL = "/assets/blog/images";
	

	@Autowired
	private BlogRepository blogRepository;
	
	
	
	//Main
	public ModelMap getAll(String id, Long categoryNo, Long postNo) {
		BlogVo blogVo = blogRepository.getBlog(id);
		System.out.println("blogVo:" + blogVo);
		
		List<CategoryVo> categoryVo = blogRepository.getCategory(categoryNo);
			System.out.println("categoryNo: " + categoryNo);
		
		List<PostVo> postVo = blogRepository.getPost(postNo);
		System.out.println("postVo: " + postVo);
		
		ModelMap modelMap = new ModelMap();
		modelMap.put("blogVo",blogVo);
		modelMap.put("categoryVo",categoryVo);
		modelMap.put("postVo",postVo);
		return modelMap;
	}
	//Category
	public List<CategoryVo> categoryList(String id) {
		return blogRepository.categoryList(id);
		
	}
	public int categoryDelete(Long no) {
		return blogRepository.categoryDelete(no);
		
	}

	//Basic
	public BlogVo findById(String id) {
		return blogRepository.getBlog(id);
	}
	
	
	public String restore(BlogVo blogVo, MultipartFile multipartFile) {
		String url = "";
		try {
			if (multipartFile.isEmpty()) {
				return url;
			}

			String originFilename = multipartFile.getOriginalFilename();
			String extName = originFilename.substring(originFilename.lastIndexOf('.') + 1);

			String saveFilename = generateSaveFilename(extName);
			long fileSize = multipartFile.getSize();

			System.out.println("######### " + originFilename);
			System.out.println("######### " + saveFilename);
			System.out.println("######### " + fileSize);

			byte[] fileData = multipartFile.getBytes();
			OutputStream os = new FileOutputStream(SAVE_PATH + "/" + saveFilename);
			os.write(fileData);
			os.close();
			url = URL + "/" + saveFilename;
			if(!multipartFile.isEmpty()) {
				blogVo.setLogo(url);
			}
			blogRepository.update(blogVo);
		} catch (IOException ex) {
			throw new RuntimeException("file upload error:" + ex);
		}
		
		return url;
	}
	private String generateSaveFilename(String extName) {
		String filename = "";

		Calendar calendar = Calendar.getInstance();
		filename += calendar.get(Calendar.YEAR);
		filename += calendar.get(Calendar.MONTH);
		filename += calendar.get(Calendar.DATE);
		filename += calendar.get(Calendar.HOUR);
		filename += calendar.get(Calendar.MINUTE);
		filename += calendar.get(Calendar.SECOND);
		filename += calendar.get(Calendar.MILLISECOND);
		filename += ("." + extName);

		return filename;
	}
	

	


}
