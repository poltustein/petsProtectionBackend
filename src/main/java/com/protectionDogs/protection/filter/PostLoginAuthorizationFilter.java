package com.protectionDogs.protection.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.protectionDogs.protection.Util.ResponseEnums;
import com.protectionDogs.protection.Util.ResponseReasonEnums;
import com.protectionDogs.protection.Util.SecurityConstants;
import com.protectionDogs.protection.Util.Utility;
import com.protectionDogs.protection.pojo.UnAuthorizedAccess;

public class PostLoginAuthorizationFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterchain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String preLoginHeader = httpRequest.getHeader(SecurityConstants.HEADER_STRING);
		if (preLoginHeader != null && !preLoginHeader.isEmpty()
				&& preLoginHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			String token = preLoginHeader.replaceFirst(SecurityConstants.TOKEN_PREFIX, "").trim();
			System.out.println("Got token" + token);
			try {
				String subject = JWT.require(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes())).build()
						.verify(token.replace(SecurityConstants.TOKEN_PREFIX, "").trim()).getSubject();
				
				if(subject==null || subject.isEmpty() || !subject.equals(httpRequest.getHeader("emailid"))) {
					throw new InvalidClaimException(subject);
				}
				filterchain.doFilter(request, response);
				return;
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("URL was "+httpRequest.getRequestURL());
			}
		}
		UnAuthorizedAccess errorResponse = new UnAuthorizedAccess();
		errorResponse.setStatus(ResponseEnums.FAILURE.toString());
		errorResponse.setReason(ResponseReasonEnums.UNAUTHORIZEDACCESS.toString());
		((HttpServletResponse) response).setHeader("Content-Type", "application/json");
		((HttpServletResponse) response).setStatus(401);
		response.getOutputStream().write(Utility.gson.toJson(errorResponse).getBytes());
	}

	@Override
	public void init(FilterConfig filterconfig) throws ServletException {
	}

}
