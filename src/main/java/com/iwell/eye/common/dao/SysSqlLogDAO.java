package com.iwell.eye.common.dao;

import com.iwell.eye.common.model.SysLogVO;
import org.springframework.stereotype.Repository;

@Repository
public interface SysSqlLogDAO {

    void SqlError(SysLogVO log);

}
