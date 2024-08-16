package com.example.chatbot.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.chatbot.model.Policy;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class PolicyRepository implements IPolicyRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public class PolicyMapper implements RowMapper<Policy> {
	    @Override
	    public Policy mapRow(ResultSet rs, int rowNum) throws SQLException {
	        Policy policy = new Policy();
	        policy.setPolicyId(rs.getInt("policy_id")); // 정책 아이디
	        policy.setRegion(rs.getString("지역")); // 지역
	        policy.setCategory(rs.getString("분야")); // 분야
	        policy.setPName(rs.getString("정책명")); // 정책명
	        policy.setExplanation(rs.getString("정책설명")); // 정책설명
	        policy.setSupport(rs.getString("지원내용")); // 지원내용
	        policy.setSupervision(rs.getString("주관기관")); // 주관기관
	        policy.setManagePeriod(rs.getString("운영기간")); // 운영기간
	        policy.setSupportPeriod(rs.getString("지원기간")); // 지원기간
	        policy.setPeople(rs.getString("지원인원")); // 지원인원
	        policy.setCondition(rs.getString("지원조건")); // 지원조건
	        policy.setAge(rs.getString("연령")); // 연령
	        policy.setMinAge(rs.getString("min_age")); // 최소 연령
	        policy.setMaxAge(rs.getString("max_age")); // 최대 연령
	        policy.setAcademicAbility(rs.getString("학력요구사항")); // 학력요구사항
	        policy.setMajor(rs.getString("전공요구사항")); // 전공요구사항
	        policy.setEmployement(rs.getString("고용상태")); // 고용상태
	        policy.setExpertise(rs.getString("전문분야요구사항")); // 전문분야요구사항
	        policy.setAddition(rs.getString("추가단서사항")); // 추가단서사항
	        policy.setLimit(rs.getString("참여제한대상")); // 참여제한대상
	        policy.setProcess(rs.getString("신청절차")); // 신청절차
	        policy.setPapers(rs.getString("제출서류")); // 제출서류
	        policy.setScreening(rs.getString("심사및발표")); // 심사 및 발표
	        policy.setUrl(rs.getString("신청url")); // 신청url
	        policy.setEtct(rs.getString("기타")); // 기타
	        policy.setManageOrg(rs.getString("운영기관")); // 운영기관
	        policy.setCharge(rs.getString("관리담당자")); // 관리담당자
	        policy.setCallNumber(rs.getString("운영기관연락처")); // 운영기관연락처
	        policy.setUrl1(rs.getString("참고사이트url1")); // 참고사이트url1
	        policy.setSDate(rs.getString("시작일")); // 시작일
	        policy.setEDate(rs.getString("종료일")); // 종료일
	        policy.setSMonth(rs.getInt("정책시작월")); // 정책시작월

	        return policy;
	    }
	}


	@Override
	public List<Policy> getPoliciesByUserInfo(String userId, String age, String interest, String area, String employment, String academic) {
        String sql = "SELECT * FROM policies " 
                + "WHERE ((min_age <= ? AND ? <= max_age) OR (min_age = '제한없음')) and "
                + "(분야 like ?) and "
        		+ "((지역 like ?) or (지역 = '서울시')) and "
        		+ "((고용상태 like ?) or (고용상태 = '제한없음') or (고용상태 = '-')) and "
        		+ "((학력요구사항 like ?) or (학력요구사항 = '제한없음') or (학력요구사항 = '-')) ";
		try {
			return jdbcTemplate.query(sql, new PolicyMapper(), age, age, interest, area, "%"+employment+"%", "%"+academic+"%");
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	

	@Override
	public Policy getPolicyByPolicyId(int policyId) {
		String sql = "SELECT * FROM policies " +
                "WHERE policy_id = ?";
		try {
			return jdbcTemplate.queryForObject(sql, new PolicyMapper(), policyId);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	//코드 분석 필요
	@Override
	public HashMap<String, Integer> getPolicyFieldsStatistics() {
        String sql = "SELECT 분야, count(분야) as count FROM policies GROUP BY 분야";
        try {
            return jdbcTemplate.query(sql, new ResultSetExtractor<HashMap<String, Integer>>() {
                @Override
                public HashMap<String, Integer> extractData(ResultSet rs) throws SQLException, DataAccessException {
                    HashMap<String, Integer> map = new HashMap<>();
                    while (rs.next()) {
                        map.put(rs.getString("분야"), rs.getInt("count"));
                    }
                    return map;
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }


	@Override//나이, 검색어 null
	public List<Policy> searchPolicy1(String employment, String academicAbility, List<String> selectedPolicies,
	                                  List<String> selectedRegions) {
	    // 기본 SQL 쿼리
	    StringBuilder sql = new StringBuilder("SELECT * FROM policies WHERE 1=1");

	    // employment 조건 추가
	    if (employment != null && !employment.isEmpty()) {
	        sql.append(" AND ((고용상태 LIKE ?) or (고용상태 in ('제한없음', '-')))");
	    }
	    // academicAbility 조건 추가
	    if (academicAbility != null && !academicAbility.isEmpty()) {
	        sql.append(" AND ((학력요구사항 LIKE ?) or (학력요구사항 in ('제한없음', '-')))");
	    }

	    // selectedPolicies 리스트에 대한 조건 추가
	    if (selectedPolicies != null && !selectedPolicies.isEmpty() && !selectedPolicies.get(0).equals("분야 전체")) {
	        sql.append(" AND ( 분야 IN (");
	        
	        for (int i = 0; i < selectedPolicies.size(); i++) {
	        	if (i > 0) {
	                sql.append(", ");
	            }
	        	sql.append("'"+selectedPolicies.get(i)+"'");
	        }
	        sql.append(") )");
	    }

	    // selectedRegions 리스트에 대한 조건 추가
	    if (selectedRegions != null && !selectedRegions.isEmpty() && !selectedRegions.get(0).equals("서울시")) {
	        sql.append(" AND ( 지역 IN (");
	        for (int i = 0; i < selectedRegions.size(); i++) {
	            if (i > 0) {
	                sql.append(", ");
	            }
	            sql.append("'"+selectedRegions.get(i)+"'");
	        }
	        sql.append(") )");
	    }

	    try {
	        // PreparedStatement를 위한 매개변수 설정
	        List<Object> params = new ArrayList<>();

	        if (employment != null && !employment.isEmpty()) {
	            params.add("%" + employment + "%");
	        }
	        if (academicAbility != null && !academicAbility.isEmpty()) {
	            params.add("%" + academicAbility + "%");
	        }


	        log.info(sql.toString());
	        return jdbcTemplate.query(sql.toString(), new PolicyMapper(), params.toArray());
	    } catch (EmptyResultDataAccessException e) {
	        return null;
	    }
	}


	@Override//검색어만 null
	public List<Policy> searchPolicy2(String employment, String academicAbility, List<String> selectedPolicies,
	                                  List<String> selectedRegions, String age) {
	    // 기본 SQL 쿼리
	    StringBuilder sql = new StringBuilder("SELECT * FROM policies WHERE 1=1");

	    // employment 조건 추가
	    if (employment != null && !employment.isEmpty()) {
	        sql.append(" AND ((고용상태 LIKE ?) or (고용상태 in ('제한없음', '-')))");
	    }
	    // academicAbility 조건 추가
	    if (academicAbility != null && !academicAbility.isEmpty()) {
	        sql.append(" AND ((학력요구사항 LIKE ?) or (학력요구사항 in ('제한없음', '-')))");
	    }

	    // age 조건 추가
	    if (age != null && !age.isEmpty()) {
	        sql.append(" AND (((min_age <= ?) AND (? <= max_age)) or (min_age = '제한없음'))");
	    }

	    // selectedPolicies 리스트에 대한 조건 추가
	    if (selectedPolicies != null && !selectedPolicies.isEmpty() && !selectedPolicies.get(0).equals("분야 전체")) {
	        sql.append(" AND ( 분야 IN (");
	        
	        for (int i = 0; i < selectedPolicies.size(); i++) {
	        	if (i > 0) {
	                sql.append(", ");
	            }
	        	sql.append("'"+selectedPolicies.get(i)+"'");
	        }
	        sql.append(") )");
	    }

	    // selectedRegions 리스트에 대한 조건 추가
	    if (selectedRegions != null && !selectedRegions.isEmpty() && !selectedRegions.get(0).equals("서울시")) {
	        sql.append(" AND ( 지역 IN (");
	        for (int i = 0; i < selectedRegions.size(); i++) {
	            if (i > 0) {
	                sql.append(", ");
	            }
	            sql.append("'"+selectedRegions.get(i)+"'");
	        }
	        sql.append(") )");
	    }

	    try {
	        // PreparedStatement를 위한 매개변수 설정
	        List<Object> params = new ArrayList<>();

	        if (employment != null && !employment.isEmpty()) {
	            params.add("%" + employment + "%");
	        }
	        if (academicAbility != null && !academicAbility.isEmpty()) {
	            params.add("%" + academicAbility + "%");
	        }
	        if (age != null && !age.isEmpty()) {
	            params.add(age);
	            params.add(age);
	        }


	        return jdbcTemplate.query(sql.toString(), new PolicyMapper(), params.toArray());
	    } catch (EmptyResultDataAccessException e) {
	        return null;
	    }
	}


	@Override//나이만 null
	public List<Policy> searchPolicy3(String employment, String academicAbility, List<String> selectedPolicies,
	                                  List<String> selectedRegions, String searchInput) {
	    // 기본 SQL 쿼리
	    StringBuilder sql = new StringBuilder("SELECT * FROM policies WHERE 1=1");

	    // employment 조건 추가
	    if (employment != null && !employment.isEmpty()) {
	        sql.append(" AND ((고용상태 LIKE ?) or (고용상태 in ('제한없음', '-')))");
	    }
	    // academicAbility 조건 추가
	    if (academicAbility != null && !academicAbility.isEmpty()) {
	        sql.append(" AND ((학력요구사항 LIKE ?) or (학력요구사항 in ('제한없음', '-')))");
	    }

	    // searchInput 조건 추가
	    if (searchInput != null && !searchInput.isEmpty()) {
	        sql.append(" AND (정책명 like ?)");
	    }

	    // selectedPolicies 리스트에 대한 조건 추가
	    if (selectedPolicies != null && !selectedPolicies.isEmpty() && !selectedPolicies.get(0).equals("분야 전체")) {
	        sql.append(" AND ( 분야 IN (");
	        
	        for (int i = 0; i < selectedPolicies.size(); i++) {
	        	if (i > 0) {
	                sql.append(", ");
	            }
	        	sql.append("'"+selectedPolicies.get(i)+"'");
	        }
	        sql.append(") )");
	    }

	    // selectedRegions 리스트에 대한 조건 추가
	    if (selectedRegions != null && !selectedRegions.isEmpty() && !selectedRegions.get(0).equals("서울시")) {
	        sql.append(" AND ( 지역 IN (");
	        for (int i = 0; i < selectedRegions.size(); i++) {
	            if (i > 0) {
	                sql.append(", ");
	            }
	            sql.append("'"+selectedRegions.get(i)+"'");
	        }
	        sql.append(") )");
	    }

	    try {
	        // PreparedStatement를 위한 매개변수 설정
	        List<Object> params = new ArrayList<>();

	        if (employment != null && !employment.isEmpty()) {
	            params.add("%" + employment + "%");
	        }
	        if (academicAbility != null && !academicAbility.isEmpty()) {
	            params.add("%" + academicAbility + "%");
	        }
	        if (searchInput != null && !searchInput.isEmpty()) {
	            String searchPattern = "%" + searchInput + "%";
	            params.add(searchPattern);
	        }

	        return jdbcTemplate.query(sql.toString(), new PolicyMapper(), params.toArray());
	    } catch (EmptyResultDataAccessException e) {
	        return null;
	    }
	}



	@Override//나이, 검색어 있음
	public List<Policy> searchPolicy4(String employment, String academicAbility, List<String> selectedPolicies,
	                                  List<String> selectedRegions, String age, String searchInput) {
	    // 기본 SQL 쿼리
	    StringBuilder sql = new StringBuilder("SELECT * FROM policies WHERE 1=1");

	    // employment 조건 추가
	    if (employment != null && !employment.isEmpty()) {
	        sql.append(" AND ((고용상태 LIKE ?) or (고용상태 in ('제한없음', '-')))");
	    }
	    // academicAbility 조건 추가
	    if (academicAbility != null && !academicAbility.isEmpty()) {
	        sql.append(" AND ((학력요구사항 LIKE ?) or (학력요구사항 in ('제한없음', '-')))");
	    }
	    // age 조건 추가
	    if (age != null && !age.isEmpty()) {
	        sql.append(" AND (((min_age <= ?) AND (? <= max_age)) or (min_age = '제한없음'))");
	    }
	    // searchInput 조건 추가
	    if (searchInput != null && !searchInput.isEmpty()) {
	        sql.append(" AND (정책명 like ?)");
	    }

	    // selectedPolicies 리스트에 대한 조건 추가
	    if (selectedPolicies != null && !selectedPolicies.isEmpty() && !selectedPolicies.get(0).equals("분야 전체")) {
	        sql.append(" AND ( 분야 IN (");
	        
	        for (int i = 0; i < selectedPolicies.size(); i++) {
	        	if (i > 0) {
	                sql.append(", ");
	            }
	        	sql.append("'"+selectedPolicies.get(i)+"'");
	        }
	        sql.append(") )");
	    }

	    // selectedRegions 리스트에 대한 조건 추가
	    if (selectedRegions != null && !selectedRegions.isEmpty() && !selectedRegions.get(0).equals("서울시")) {
	        sql.append(" AND ( 지역 IN (");
	        for (int i = 0; i < selectedRegions.size(); i++) {
	            if (i > 0) {
	                sql.append(", ");
	            }
	            sql.append("'"+selectedRegions.get(i)+"'");
	        }
	        sql.append(") )");
	    }

	    try {
	        // PreparedStatement를 위한 매개변수 설정
	        List<Object> params = new ArrayList<>();

	        if (employment != null && !employment.isEmpty()) {
	            params.add("%" + employment + "%");
	        }
	        if (academicAbility != null && !academicAbility.isEmpty()) {
	            params.add("%" + academicAbility + "%");
	        }
	        if (age != null && !age.isEmpty()) {
	            params.add(age);
	            params.add(age);
	        }
	        if (searchInput != null && !searchInput.isEmpty()) {
	            String searchPattern = "%" + searchInput + "%";
	            params.add(searchPattern);
	        }

	        return jdbcTemplate.query(sql.toString(), new PolicyMapper(), params.toArray());
	    } catch (EmptyResultDataAccessException e) {
	        return null;
	    }
	}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	public Page<Policy> searchPolicy1(String employment, String academicAbility, List<String> selectedPolicies,
	                                  List<String> selectedRegions, Pageable pageable) {

	    // 기본 SQL 쿼리
	    StringBuilder sql = new StringBuilder("SELECT * FROM policies WHERE 1=1");

	    // employment 조건 추가
	    if (employment != null && !employment.isEmpty()) {
	        sql.append(" AND ((고용상태 LIKE ?) or (고용상태 in ('제한없음', '-')))");
	    }

	    // academicAbility 조건 추가
	    if (academicAbility != null && !academicAbility.isEmpty()) {
	        sql.append(" AND ((학력요구사항 LIKE ?) or (학력요구사항 in ('제한없음', '-')))");
	    }

	    // selectedPolicies 리스트에 대한 조건 추가
	    if (selectedPolicies != null && !selectedPolicies.isEmpty() && !selectedPolicies.get(0).equals("분야 전체")) {
	        sql.append(" AND ( 분야 IN (");

	        for (int i = 0; i < selectedPolicies.size(); i++) {
	            if (i > 0) {
	                sql.append(", ");
	            }
	            sql.append("'" + selectedPolicies.get(i) + "'");
	        }
	        sql.append(") )");
	    }

	    // selectedRegions 리스트에 대한 조건 추가
	    if (selectedRegions != null && !selectedRegions.isEmpty() && !selectedRegions.get(0).equals("서울시")) {
	        sql.append(" AND ( 지역 IN (");
	        for (int i = 0; i < selectedRegions.size(); i++) {
	            if (i > 0) {
	                sql.append(", ");
	            }
	            sql.append("'" + selectedRegions.get(i) + "'");
	        }
	        sql.append(") ) order by policy_id");
	    }

	    // 페이지네이션을 위한 OFFSET 및 LIMIT 추가
	    sql.append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

	    try {
	        // PreparedStatement를 위한 매개변수 설정
	        List<Object> params = new ArrayList<>();

	        if (employment != null && !employment.isEmpty()) {
	            params.add("%" + employment + "%");
	        }
	        if (academicAbility != null && !academicAbility.isEmpty()) {
	            params.add("%" + academicAbility + "%");
	        }

	        // LIMIT과 OFFSET 값을 Pageable에서 추출하여 추가
	        params.add(pageable.getOffset());
	        params.add(pageable.getPageSize());
	        

	        log.info(sql.toString());

	        // 실제 쿼리 실행
	        List<Policy> policies = jdbcTemplate.query(sql.toString(), new PolicyMapper(), params.toArray());
	        //System.out.println(policies);

	        // 전체 결과 수 계산을 위한 쿼리 (페이지네이션 없이)
	        StringBuilder countSql = new StringBuilder("SELECT COUNT(*) FROM policies WHERE 1=1");

	        if (employment != null && !employment.isEmpty()) {
	            countSql.append(" AND ((고용상태 LIKE ?) or (고용상태 in ('제한없음', '-')))");
	        }
	        if (academicAbility != null && !academicAbility.isEmpty()) {
	            countSql.append(" AND ((학력요구사항 LIKE ?) or (학력요구사항 in ('제한없음', '-')))");
	        }
	        if (selectedPolicies != null && !selectedPolicies.isEmpty() && !selectedPolicies.get(0).equals("분야 전체")) {
	            countSql.append(" AND ( 분야 IN (");

	            for (int i = 0; i < selectedPolicies.size(); i++) {
	                if (i > 0) {
	                    countSql.append(", ");
	                }
	                countSql.append("'" + selectedPolicies.get(i) + "'");
	            }
	            countSql.append(") )");
	        }
	        if (selectedRegions != null && !selectedRegions.isEmpty() && !selectedRegions.get(0).equals("서울시")) {
	            countSql.append(" AND ( 지역 IN (");
	            for (int i = 0; i < selectedRegions.size(); i++) {
	                if (i > 0) {
	                    countSql.append(", ");
	                }
	                countSql.append("'" + selectedRegions.get(i) + "'");
	            }
	            countSql.append(") ) order by policy_id");
	        }

	        // 총 카운트를 가져오기 위해 쿼리 실행
	        log.info(countSql.toString());
	        System.out.println(params);
	        int total = jdbcTemplate.queryForObject(countSql.toString(), Integer.class, params.get(0), params.get(1));

	        // 결과를 Page 객체로 반환
	        return new PageImpl<>(policies, pageable, total);

	    } catch (EmptyResultDataAccessException e) {
	        return new PageImpl<>(new ArrayList<>(), pageable, 0);
	    }
	}

	@Override
	public Page<Policy> searchPolicy2(String employment, String academicAbility, List<String> selectedPolicies,
	                                  List<String> selectedRegions, String age, Pageable pageable) {
	    // 기본 SQL 쿼리
	    StringBuilder sql = new StringBuilder("SELECT * FROM policies WHERE 1=1");

	    // employment 조건 추가
	    if (employment != null && !employment.isEmpty()) {
	        sql.append(" AND ((고용상태 LIKE ?) or (고용상태 in ('제한없음', '-')))");
	    }

	    // academicAbility 조건 추가
	    if (academicAbility != null && !academicAbility.isEmpty()) {
	        sql.append(" AND ((학력요구사항 LIKE ?) or (학력요구사항 in ('제한없음', '-')))");
	    }

	    // age 조건 추가
	    if (age != null && !age.isEmpty()) {
	        sql.append(" AND (((min_age <= ?) AND (? <= max_age)) or (min_age = '제한없음'))");
	    }

	    // selectedPolicies 리스트에 대한 조건 추가
	    if (selectedPolicies != null && !selectedPolicies.isEmpty() && !selectedPolicies.get(0).equals("분야 전체")) {
	        sql.append(" AND ( 분야 IN (");
	        for (int i = 0; i < selectedPolicies.size(); i++) {
	            if (i > 0) {
	                sql.append(", ");
	            }
	            sql.append("'" + selectedPolicies.get(i) + "'");
	        }
	        sql.append(") )");
	    }

	    // selectedRegions 리스트에 대한 조건 추가
	    if (selectedRegions != null && !selectedRegions.isEmpty() && !selectedRegions.get(0).equals("서울시")) {
	        sql.append(" AND ( 지역 IN (");
	        for (int i = 0; i < selectedRegions.size(); i++) {
	            if (i > 0) {
	                sql.append(", ");
	            }
	            sql.append("'" + selectedRegions.get(i) + "'");
	        }
	        sql.append(") ) order by policy_id");
	    }

	    // 페이지네이션을 위한 LIMIT 및 OFFSET 추가
	    sql.append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

	    try {
	        // PreparedStatement를 위한 매개변수 설정
	        List<Object> params = new ArrayList<>();

	        if (employment != null && !employment.isEmpty()) {
	            params.add("%" + employment + "%");
	        }
	        if (academicAbility != null && !academicAbility.isEmpty()) {
	            params.add("%" + academicAbility + "%");
	        }
	        if (age != null && !age.isEmpty()) {
	            params.add(age);
	            params.add(age);
	        }

	        // LIMIT과 OFFSET 값을 Pageable에서 추출하여 추가
	        params.add(pageable.getOffset());
	        params.add(pageable.getPageSize());


	        // 실제 쿼리 실행
	        List<Policy> policies = jdbcTemplate.query(sql.toString(), new PolicyMapper(), params.toArray());

	        // 전체 결과 수 계산을 위한 쿼리 (페이지네이션 없이)
	        StringBuilder countSql = new StringBuilder("SELECT COUNT(*) FROM policies WHERE 1=1");

	        if (employment != null && !employment.isEmpty()) {
	            countSql.append(" AND ((고용상태 LIKE ?) or (고용상태 in ('제한없음', '-')))");
	        }
	        if (academicAbility != null && !academicAbility.isEmpty()) {
	            countSql.append(" AND ((학력요구사항 LIKE ?) or (학력요구사항 in ('제한없음', '-')))");
	        }
	        if (age != null && !age.isEmpty()) {
	            countSql.append(" AND (((min_age <= ?) AND (? <= max_age)) or (min_age = '제한없음'))");
	        }
	        if (selectedPolicies != null && !selectedPolicies.isEmpty() && !selectedPolicies.get(0).equals("분야 전체")) {
	            countSql.append(" AND ( 분야 IN (");

	            for (int i = 0; i < selectedPolicies.size(); i++) {
	                if (i > 0) {
	                    countSql.append(", ");
	                }
	                countSql.append("'" + selectedPolicies.get(i) + "'");
	            }
	            countSql.append(") )");
	        }
	        if (selectedRegions != null && !selectedRegions.isEmpty() && !selectedRegions.get(0).equals("서울시")) {
	            countSql.append(" AND ( 지역 IN (");
	            for (int i = 0; i < selectedRegions.size(); i++) {
	                if (i > 0) {
	                    countSql.append(", ");
	                }
	                countSql.append("'" + selectedRegions.get(i) + "'");
	            }
	            countSql.append(") ) order by policy_id");
	        }

	        // 총 카운트를 가져오기 위해 쿼리 실행
	        int total = jdbcTemplate.queryForObject(countSql.toString(), Integer.class, params.get(0), params.get(1), params.get(2), params.get(3));

	        // 결과를 Page 객체로 반환
	        return new PageImpl<>(policies, pageable, total);

	    } catch (EmptyResultDataAccessException e) {
	        return new PageImpl<>(new ArrayList<>(), pageable, 0);
	    }
	}

	@Override
	public Page<Policy> searchPolicy3(String employment, String academicAbility, List<String> selectedPolicies,
	                                  List<String> selectedRegions, String searchInput, Pageable pageable) {
	    // 기본 SQL 쿼리
	    StringBuilder sql = new StringBuilder("SELECT * FROM policies WHERE 1=1");

	    // employment 조건 추가
	    if (employment != null && !employment.isEmpty()) {
	        sql.append(" AND ((고용상태 LIKE ?) or (고용상태 in ('제한없음', '-')))");
	    }

	    // academicAbility 조건 추가
	    if (academicAbility != null && !academicAbility.isEmpty()) {
	        sql.append(" AND ((학력요구사항 LIKE ?) or (학력요구사항 in ('제한없음', '-')))");
	    }

	    // searchInput 조건 추가
	    if (searchInput != null && !searchInput.isEmpty()) {
	        sql.append(" AND (정책명 LIKE ?)");
	    }

	    // selectedPolicies 리스트에 대한 조건 추가
	    if (selectedPolicies != null && !selectedPolicies.isEmpty() && !selectedPolicies.get(0).equals("분야 전체")) {
	        sql.append(" AND ( 분야 IN (");
	        for (int i = 0; i < selectedPolicies.size(); i++) {
	            if (i > 0) {
	                sql.append(", ");
	            }
	            sql.append("'" + selectedPolicies.get(i) + "'");
	        }
	        sql.append(") )");
	    }

	    // selectedRegions 리스트에 대한 조건 추가
	    if (selectedRegions != null && !selectedRegions.isEmpty() && !selectedRegions.get(0).equals("서울시")) {
	        sql.append(" AND ( 지역 IN (");
	        for (int i = 0; i < selectedRegions.size(); i++) {
	            if (i > 0) {
	                sql.append(", ");
	            }
	            sql.append("'" + selectedRegions.get(i) + "'");
	        }
	        sql.append(") ) order by policy_id");
	    }

	    // 페이지네이션을 위한 LIMIT 및 OFFSET 추가
	    sql.append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

	    try {
	        // PreparedStatement를 위한 매개변수 설정
	        List<Object> params = new ArrayList<>();

	        if (employment != null && !employment.isEmpty()) {
	            params.add("%" + employment + "%");
	        }
	        if (academicAbility != null && !academicAbility.isEmpty()) {
	            params.add("%" + academicAbility + "%");
	        }
	        if (searchInput != null && !searchInput.isEmpty()) {
	            String searchPattern = "%" + searchInput + "%";
	            params.add(searchPattern);
	        }

	        // LIMIT과 OFFSET 값을 Pageable에서 추출하여 추가
	        params.add(pageable.getOffset());
	        params.add(pageable.getPageSize());


	        // 실제 쿼리 실행
	        List<Policy> policies = jdbcTemplate.query(sql.toString(), new PolicyMapper(), params.toArray());

	        // 전체 결과 수 계산을 위한 쿼리 (페이지네이션 없이)
	        StringBuilder countSql = new StringBuilder("SELECT COUNT(*) FROM policies WHERE 1=1");

	        if (employment != null && !employment.isEmpty()) {
	            countSql.append(" AND ((고용상태 LIKE ?) or (고용상태 in ('제한없음', '-')))");
	        }
	        if (academicAbility != null && !academicAbility.isEmpty()) {
	            countSql.append(" AND ((학력요구사항 LIKE ?) or (학력요구사항 in ('제한없음', '-')))");
	        }
	        if (searchInput != null && !searchInput.isEmpty()) {
	            countSql.append(" AND (정책명 LIKE ?)");
	        }
	        if (selectedPolicies != null && !selectedPolicies.isEmpty() && !selectedPolicies.get(0).equals("분야 전체")) {
	            countSql.append(" AND ( 분야 IN (");
	            for (int i = 0; i < selectedPolicies.size(); i++) {
	                if (i > 0) {
	                    countSql.append(", ");
	                }
	                countSql.append("'" + selectedPolicies.get(i) + "'");
	            }
	            countSql.append(") )");
	        }
	        if (selectedRegions != null && !selectedRegions.isEmpty() && !selectedRegions.get(0).equals("서울시")) {
	            countSql.append(" AND ( 지역 IN (");
	            for (int i = 0; i < selectedRegions.size(); i++) {
	                if (i > 0) {
	                    countSql.append(", ");
	                }
	                countSql.append("'" + selectedRegions.get(i) + "'");
	            }
	            countSql.append(") ) order by policy_id");
	        }

	        // 총 카운트를 가져오기 위해 쿼리 실행
	        int total = jdbcTemplate.queryForObject(countSql.toString(), Integer.class, params.get(0), params.get(1), params.get(2));

	        // 결과를 Page 객체로 반환
	        return new PageImpl<>(policies, pageable, total);

	    } catch (EmptyResultDataAccessException e) {
	        return new PageImpl<>(new ArrayList<>(), pageable, 0);
	    }
	}


	@Override
	public Page<Policy> searchPolicy4(String employment, String academicAbility, List<String> selectedPolicies,
	                                  List<String> selectedRegions, String age, String searchInput, Pageable pageable) {
	    // 기본 SQL 쿼리
	    StringBuilder sql = new StringBuilder("SELECT * FROM policies WHERE 1=1");

	    // employment 조건 추가
	    if (employment != null && !employment.isEmpty()) {
	        sql.append(" AND ((고용상태 LIKE ?) or (고용상태 in ('제한없음', '-')))");
	    }

	    // academicAbility 조건 추가
	    if (academicAbility != null && !academicAbility.isEmpty()) {
	        sql.append(" AND ((학력요구사항 LIKE ?) or (학력요구사항 in ('제한없음', '-')))");
	    }

	    // age 조건 추가
	    if (age != null && !age.isEmpty()) {
	        sql.append(" AND (((min_age <= ?) AND (? <= max_age)) or (min_age = '제한없음'))");
	    }

	    // searchInput 조건 추가
	    if (searchInput != null && !searchInput.isEmpty()) {
	        sql.append(" AND (정책명 LIKE ?)");
	    }

	    // selectedPolicies 리스트에 대한 조건 추가
	    if (selectedPolicies != null && !selectedPolicies.isEmpty() && !selectedPolicies.get(0).equals("분야 전체")) {
	        sql.append(" AND ( 분야 IN (");
	        for (int i = 0; i < selectedPolicies.size(); i++) {
	            if (i > 0) {
	                sql.append(", ");
	            }
	            sql.append("'" + selectedPolicies.get(i) + "'");
	        }
	        sql.append(") )");
	    }

	    // selectedRegions 리스트에 대한 조건 추가
	    if (selectedRegions != null && !selectedRegions.isEmpty() && !selectedRegions.get(0).equals("서울시")) {
	        sql.append(" AND ( 지역 IN (");
	        for (int i = 0; i < selectedRegions.size(); i++) {
	            if (i > 0) {
	                sql.append(", ");
	            }
	            sql.append("'" + selectedRegions.get(i) + "'");
	        }
	        sql.append(") ) order by policy_id");
	    }

	    // 페이지네이션을 위한 LIMIT 및 OFFSET 추가
	    sql.append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

	    try {
	        // PreparedStatement를 위한 매개변수 설정
	        List<Object> params = new ArrayList<>();

	        if (employment != null && !employment.isEmpty()) {
	            params.add("%" + employment + "%");
	        }
	        if (academicAbility != null && !academicAbility.isEmpty()) {
	            params.add("%" + academicAbility + "%");
	        }
	        if (age != null && !age.isEmpty()) {
	            params.add(age);
	            params.add(age);
	        }
	        if (searchInput != null && !searchInput.isEmpty()) {
	            String searchPattern = "%" + searchInput + "%";
	            params.add(searchPattern);
	        }

	        // LIMIT과 OFFSET 값을 Pageable에서 추출하여 추가
	        params.add(pageable.getOffset());
	        params.add(pageable.getPageSize());


	        // 실제 쿼리 실행
	        List<Policy> policies = jdbcTemplate.query(sql.toString(), new PolicyMapper(), params.toArray());

	        // 전체 결과 수 계산을 위한 쿼리 (페이지네이션 없이)
	        StringBuilder countSql = new StringBuilder("SELECT COUNT(*) FROM policies WHERE 1=1");

	        if (employment != null && !employment.isEmpty()) {
	            countSql.append(" AND ((고용상태 LIKE ?) or (고용상태 in ('제한없음', '-')))");
	        }
	        if (academicAbility != null && !academicAbility.isEmpty()) {
	            countSql.append(" AND ((학력요구사항 LIKE ?) or (학력요구사항 in ('제한없음', '-')))");
	        }
	        if (age != null && !age.isEmpty()) {
	            countSql.append(" AND (((min_age <= ?) AND (? <= max_age)) or (min_age = '제한없음'))");
	        }
	        if (searchInput != null && !searchInput.isEmpty()) {
	            countSql.append(" AND (정책명 LIKE ?)");
	        }
	        if (selectedPolicies != null && !selectedPolicies.isEmpty() && !selectedPolicies.get(0).equals("분야 전체")) {
	            countSql.append(" AND ( 분야 IN (");
	            for (int i = 0; i < selectedPolicies.size(); i++) {
	                if (i > 0) {
	                    countSql.append(", ");
	                }
	                countSql.append("'" + selectedPolicies.get(i) + "'");
	            }
	            countSql.append(") )");
	        }
	        if (selectedRegions != null && !selectedRegions.isEmpty() && !selectedRegions.get(0).equals("서울시")) {
	            countSql.append(" AND ( 지역 IN (");
	            for (int i = 0; i < selectedRegions.size(); i++) {
	                if (i > 0) {
	                    countSql.append(", ");
	                }
	                countSql.append("'" + selectedRegions.get(i) + "'");
	            }
	            countSql.append(") ) order by policy_id");
	        }

	        // 총 카운트를 가져오기 위해 쿼리 실행
	        int total = jdbcTemplate.queryForObject(countSql.toString(), Integer.class, params.get(0),params.get(1),params.get(2),params.get(3),params.get(4));

	        // 결과를 Page 객체로 반환
	        return new PageImpl<>(policies, pageable, total);

	    } catch (EmptyResultDataAccessException e) {
	        return new PageImpl<>(new ArrayList<>(), pageable, 0);
	    }
	}

}
