package com.iwell.eye.common.dao;


import com.iwell.eye.common.model.ApiUserVO;
import org.springframework.stereotype.Repository;


@Repository
public interface ApiAuthenticationDAO {
	ApiUserVO getUserInfoByUserId(String email);
}
