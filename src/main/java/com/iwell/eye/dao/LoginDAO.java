package com.iwell.eye.dao;

import com.iwell.eye.model.LoginVO;
import com.iwell.eye.model.UserVO;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginDAO {
    LoginVO postUserLogin(LoginVO vo);
    LoginVO postCustLogin(LoginVO vo);
}
