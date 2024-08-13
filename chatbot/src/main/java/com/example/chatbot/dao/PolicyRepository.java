package com.example.chatbot.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.chatbot.model.Policy;

@Repository
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
	public List<Policy> getPoliciesByUserInfo(String userId, String age) {
        String sql = "SELECT * FROM policies " +
                "WHERE (min_age <= ? AND ? <= max_age) OR (min_age LIKE '제한없음')";
		try {
			return jdbcTemplate.query(sql, new PolicyMapper(), age, age);
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
	
	


}
