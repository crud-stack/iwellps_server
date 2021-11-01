package com.iwell.eye.common.interceptor;

import com.iwell.eye.common.service.SysLogService;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

@Intercepts({ @Signature(type = StatementHandler.class, method = "update", args = { Statement.class }),
		@Signature(type = StatementHandler.class, method = "query", args = { Statement.class, ResultHandler.class }) })
public class SqlLoggingInterceptor implements Interceptor {
	final protected static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	@Value("${logging.db.sql}")
	protected boolean loggingSql;
	
	@Autowired
    private SysLogService sysLogService;

	@SuppressWarnings("rawtypes")
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler handler = (StatementHandler) invocation.getTarget();
		BoundSql boundSql = handler.getBoundSql();
		String queryID = "";

		// 쿼리문을 가져온다(이 상태에서의 쿼리는 값이 들어갈 부분에 ?가 있다)
		String sql = boundSql.getSql();
		// 쿼리실행시 맵핑되는 파라미터를 구한다
		Object param = handler.getParameterHandler().getParameterObject();
		
		if (param == null) { // 파라미터가 아무것도 없을 경우
			sql = sql.replaceFirst("\\?", "''");
		} else { // 해당 파라미터의 클래스가 Integer, Long, Float, Double 클래스일 경우
			if (param instanceof Integer || param instanceof Long || param instanceof Float
					|| param instanceof Double) {
				sql = sql.replaceFirst("\\?", param.toString());
			} else if (param instanceof String) { // 해당 파라미터의 클래스가 String 일 경우(이 경우는 앞뒤에 '(홑따옴표)를 붙여야해서 별도 처리
				sql = sql.replaceFirst("\\?", "'" + param + "'");
			} else if (param instanceof Map) { // 해당 파라미터가 Map 일 경우
				// 쿼리의 ?와 매핑되는 실제 값들의 정보가 들어있는 ParameterMapping 객체가 들어간 List 객체로 return이 된다.
				// 이때 List 객체의 0번째 순서에 있는 ParameterMapping 객체가 쿼리의 첫번째 ?와 매핑이 된다
				// 이런 식으로 쿼리의 ?과 ParameterMapping 객체들을 Mapping 한다
				List<ParameterMapping> paramMapping = boundSql.getParameterMappings();
				for (ParameterMapping mapping : paramMapping) {
					String propValue = mapping.getProperty(); // 파라미터로 넘긴 Map의 key 값이 들어오게 된다
					Object value = ((Map) param).get(propValue); // 넘겨받은 key 값을 이용해 실제 값을 꺼낸다
					if (value instanceof String) { // SQL의 ? 대신에 실제 값을 넣는다. 이때 String 일 경우는 '를 붙여야 하기땜에 별도 처리
						sql = sql.replaceFirst("\\?", "'" + value + "'");
					} else {
						if(value != null) {
							sql = sql.replaceFirst("\\?", value.toString());
						}
					}
				}
			} else { // 해당 파라미터가 사용자 정의 클래스일 경우
				// 쿼리의 ?와 매핑되는 실제 값들이 List 객체로 return이 된다.
				// 이때 List 객체의 0번째 순서에 있는 ParameterMapping 객체가 쿼리의 첫번째 ?와 매핑이 된다
				// 이런 식으로 쿼리의 ?과 ParameterMapping 객체들을 Mapping 한다
				List<ParameterMapping> paramMapping = boundSql.getParameterMappings();
				Class<? extends Object> paramClass = param.getClass();
				for (ParameterMapping mapping : paramMapping) {
					String propValue = mapping.getProperty(); // 해당 파라미터로 넘겨받은 사용자 정의 클래스 객체의 멤버변수명
					Field field = paramClass.getDeclaredField(propValue); // 관련 멤버변수 Field 객체 얻어옴
					field.setAccessible(true); // 멤버변수의 접근자가 private일 경우 reflection을 이용하여 값을 해당 멤버변수의 값을 가져오기 위해 별도로 셋팅
					Class<?> javaType = mapping.getJavaType(); // 해당 파라미터로 넘겨받은 사용자 정의 클래스 객체의 멤버변수의 타입
					if (String.class == javaType) { // SQL의 ? 대신에 실제 값을 넣는다. 이때 String 일 경우는 '를 붙여야 하기땜에 별도 처리
						sql = sql.replaceFirst("\\?", "'" + field.get(param) + "'");
					} else {
						// TODO :  String 클래스가 아닌 필드에 NULL값이 들어가면 java.lang.NullPointerException 발생 확인이 필요함, 임시 if(param != null) 추가
						if(param != null) {
							sql = sql.replaceFirst("\\?", field.get(param).toString());
						}
					}
				}
			}
		}

		// SQL 저장처리
		logger.debug("SqlLoggingInterceptor sysLogService:" + sysLogService);
		sysLogService.postSysLogSql(queryID, sql);
		
		return invocation.proceed(); // 쿼리 실행
	}

}