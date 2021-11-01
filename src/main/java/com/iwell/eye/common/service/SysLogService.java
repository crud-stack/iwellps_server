package com.iwell.eye.common.service;

import com.iwell.eye.common.model.SysLogVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
//@Transactional(propagation = Propagation.NEVER)
public class SysLogService {
	final protected static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private final String INSERT_SYS_LOG_API = "INSERT INTO sys_log_api"
											+ "(DIRECTION,URL,METHOD,HEADER,Body,ID_REG) "
											+ "VALUES(?,?,?,?,?,?)";
	private final String INSERT_SYS_LOG_SQL = "INSERT INTO sys_log_sql"
											+ "(NOTE,sql_query,id_reg) "
											+ "VALUES(?,?,?)";

	@Value("${logging.db.api}")
	protected boolean loggingApi;
	@Value("${logging.db.sql}")
	protected boolean loggingSql;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public void postSysLogApi(SysLogVO param) {
		try {
			if (loggingApi) {
				jdbcTemplate.update(new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
						PreparedStatement ps = con.prepareStatement(INSERT_SYS_LOG_API);
						ps.setString(1, param.getArrow());
						ps.setString(2, param.getUrl());
						ps.setString(3, param.getMethod());
						ps.setString(4, param.getHder());
						ps.setString(5, param.getBody());
						ps.setString(6, param.getRegSid());
						return ps;
					}
				});
			}
		} catch (Exception e) {
			logger.warn("SysLogService :: postSysLogApi Error", e);
		}
	}

	public void postSysLogSql(SysLogVO param) {
		try {
			if (loggingSql) {
				jdbcTemplate.update(new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
						PreparedStatement ps = con.prepareStatement(INSERT_SYS_LOG_SQL);
						ps.setString(1, param.getNote());
						ps.setString(2, param.getSqlQry());
						ps.setString(3, param.getRegSid());
						return ps;
					}
				});
			}
		} catch (Exception e) {
			logger.warn("SysLogService :: postSysLogSql Error", e);
		}
	}

	public void postSysLogApi(HttpServletRequest request, String direction, String header, String body) {
		SysLogVO sysLogVO = new SysLogVO();
		sysLogVO.setArrow(direction);
		sysLogVO.setUrl(request.getRequestURL().toString());
		sysLogVO.setMethod(request.getMethod());
		sysLogVO.setHder(header);
		sysLogVO.setBody(body);
		sysLogVO.setRegSid("system");
		postSysLogApi(sysLogVO);
	}

	public void postSysLogApi(HttpRequest request, String direction, String body) {
		SysLogVO sysLogVO = new SysLogVO();
		sysLogVO.setArrow(direction);
		sysLogVO.setUrl(request.getURI().toString());
		sysLogVO.setMethod(request.getMethodValue());
		sysLogVO.setHder(request.getHeaders().toString());
		sysLogVO.setBody(body);
		sysLogVO.setRegSid("system");
		postSysLogApi(sysLogVO);
	}

	public void postSysLogApi(ClientHttpResponse response, String direction, String body) {
		SysLogVO sysLogVO = new SysLogVO();
		sysLogVO.setArrow(direction);
		sysLogVO.setUrl("");
		sysLogVO.setMethod("");
		sysLogVO.setHder(response.getHeaders().toString());
		sysLogVO.setBody(body);
		sysLogVO.setRegSid("system");
		postSysLogApi(sysLogVO);
	}

	public void postSysLogSql(String queryID, String sql) {
		SysLogVO sysLogVO = new SysLogVO();
		sysLogVO.setNote(queryID);
		sysLogVO.setSqlQry(sql);
		sysLogVO.setRegSid("system");
		postSysLogSql(sysLogVO);
	}

}
