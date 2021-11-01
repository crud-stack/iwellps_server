package com.iwell.eye.dao;

import com.iwell.eye.model.CustVO;
import com.iwell.eye.model.UserVO;
import org.springframework.stereotype.Repository;

@Repository
public interface MyDAO {
    UserVO postAdminInfo(UserVO userVO);
    CustVO postCustInfo(CustVO custVO);
    CustVO putSecede(CustVO custVO);
    CustVO putCustInfo(CustVO custVO);
}
