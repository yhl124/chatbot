package com.example.chatbot.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.chatbot.model.Policy;

@Repository
public class PolicyRepository implements IPolicyRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private class PolicyMapper implements RowMapper<Policy> {
	    @Override
	    public Policy mapRow(ResultSet rs, int rowNum) throws SQLException {
	        Policy policy = new Policy();
	        policy.setPolicyId(rs.getInt("policy_id"));
	        policy.setPolyBizSecd(rs.getString("polyBizSecd"));
	        policy.setPolyRlmCd(rs.getString("polyRlmCd"));
	        policy.setPolyBizSjnm(rs.getString("polyBizSjnm"));
	        policy.setPolyItcnCn(rs.getString("polyItcnCn"));
	        policy.setSporCn(rs.getString("sporCn"));
	        policy.setMngtMson(rs.getString("mngtMson"));
	        policy.setBizPrdCn(rs.getString("bizPrdCn"));
	        policy.setRqutPrdCn(rs.getString("rqutPrdCn"));
	        policy.setSporScvl(rs.getString("sporScvl"));
	        policy.setRfcSiteUrla1(rs.getString("rfcSiteUrla1"));
	        policy.setPrcpCn(rs.getString("prcpCn"));
	        policy.setAgeInfo(rs.getString("ageInfo"));
	        policy.setMinAge(rs.getString("min_age"));
	        policy.setMaxAge(rs.getString("max_age"));
	        policy.setAccrRqisCn(rs.getString("accrRqisCn"));
	        policy.setMajrRqisCn(rs.getString("majrRqisCn"));
	        policy.setEmpmSttsCn(rs.getString("empmSttsCn"));
	        policy.setSplzRlmRqisCn(rs.getString("splzRlmRqisCn"));
	        policy.setAditRscn(rs.getString("aditRscn"));
	        policy.setPrcpLmttTrgtCn(rs.getString("prcpLmttTrgtCn"));
	        policy.setRqutProcCn(rs.getString("rqutProcCn"));
	        policy.setPstnPaprCn(rs.getString("pstnPaprCn"));
	        policy.setJdgnPresCn(rs.getString("jdgnPresCn"));
	        policy.setRqutUrla(rs.getString("rqutUrla"));
	        policy.setEtct(rs.getString("etct"));
	        policy.setCnsgNmor(rs.getString("cnsgNmor"));
	        policy.setMngtMrofCherCn(rs.getString("mngtMrofCherCn"));
	        policy.setTintCherCtpcCn(rs.getString("tintCherCtpcCn"));
	        return policy;
	    }
	}

	@Override
	public List<Policy> getPoliciesByUserInfo(String userId, String age) {
        String sql = "SELECT policy_id, polyBizSecd, polyRlmCd, polyBizSjnm, polyItcnCn, sporCn, " +
                "mngtMson, bizPrdCn, rqutPrdCn, sporScvl, rfcSiteUrla1, prcpCn, ageInfo, " +
                "min_age, max_age, accrRqisCn, majrRqisCn, empmSttsCn, splzRlmRqisCn, " +
                "aditRscn, prcpLmttTrgtCn, rqutProcCn, pstnPaprCn, jdgnPresCn, rqutUrla, " +
                "etct, cnsgNmor, mngtMrofCherCn, tintCherCtpcCn " +
                "FROM policies " +
                "WHERE (min_age <= ? AND ? <= max_age) OR (min_age LIKE '제한없음')";
		try {
			return jdbcTemplate.query(sql, new PolicyMapper(), age, age);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public Policy getPolicyByPolicyId(int policyId) {
		String sql = "SELECT policy_id, polyBizSecd, polyRlmCd, polyBizSjnm, polyItcnCn, sporCn, " +
                "mngtMson, bizPrdCn, rqutPrdCn, sporScvl, rfcSiteUrla1, prcpCn, ageInfo, " +
                "min_age, max_age, accrRqisCn, majrRqisCn, empmSttsCn, splzRlmRqisCn, " +
                "aditRscn, prcpLmttTrgtCn, rqutProcCn, pstnPaprCn, jdgnPresCn, rqutUrla, " +
                "etct, cnsgNmor, mngtMrofCherCn, tintCherCtpcCn " +
                "FROM policies " +
                "WHERE policy_id = ?";
		try {
			return jdbcTemplate.queryForObject(sql, new PolicyMapper(), policyId);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}


}
