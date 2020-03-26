package com.douzone.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.douzone.jblog.vo.UserVo;

public class AuthUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

	

	@Override
	public Object resolveArgument(
			MethodParameter parameter, 
			ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, 
			WebDataBinderFactory binderFactory) throws Exception {
			// 값세팅
		if(  !  supportsParameter(parameter)) {
			// 내가 처리할 애가 아니다.
			return WebArgumentResolver.UNRESOLVED;
		}
		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
		HttpSession session = request.getSession();
		if(session == null) {
			return null;
		}
		
		return session.getAttribute("authUser");
	}
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		// 검사
		AuthUser authUser =  parameter.getParameterAnnotation(AuthUser.class);
		
		// @AuthUser가 안붙어있으면
		if(authUser == null) {
			return false;
		}
		// 파라미터 타입이 UserVo인지 확인
		if(  !  parameter.getParameterType().equals(UserVo.class)) {
			return false;
		}
		return true;
	}
}
