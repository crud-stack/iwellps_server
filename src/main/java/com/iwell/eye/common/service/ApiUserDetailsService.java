package com.iwell.eye.common.service;

import com.iwell.eye.common.dao.ApiAuthenticationDAO;
import com.iwell.eye.common.model.ApiUserVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;


@Service
@Transactional
public class ApiUserDetailsService implements UserDetailsService{
	final protected static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
    ApiAuthenticationDAO dao;
	private ApiUserVO apiUserVO;


	//throws UsernameNotFoundException
	@Override
	public UserDetails loadUserByUsername(String usrSid) throws UsernameNotFoundException {

		logger.info("● ApiUserDetailsService loadUserByUsername::{} ●", usrSid);
		setApiUserVO(null);
		System.out.println(usrSid);
		try {
			/* empty userId */
			if(StringUtils.isAllEmpty(usrSid)) {
				logger.warn("● loadUserByUsername::Userid is Empty ●");
				throw new UsernameNotFoundException("Userid is Empty");
			}

			//if 처리
			setApiUserVO(dao.getUserInfoByUserId(usrSid));

			/* wrong userId */
			if(apiUserVO == null) {
				logger.warn("● loadUserByUsername::User not found::{} ●", usrSid);
				throw new UsernameNotFoundException("User not found::" + usrSid);
			}			
			GrantedAuthority authority = new SimpleGrantedAuthority(apiUserVO.getRole());
			logger.info("● ApiUserDetailsService loadUserByUsername::return User::{} ●", usrSid);
			return new User(apiUserVO.getAgentId(), apiUserVO.getPassword(), Arrays.asList(authority));
		}catch (Exception e) {
			logger.error("● loadUserByUsername::Error! ●", e);
			throw new RuntimeException(e);
		}
	}

	public void setApiUserVO(ApiUserVO partner) {
		this.apiUserVO = partner;
	}

}
