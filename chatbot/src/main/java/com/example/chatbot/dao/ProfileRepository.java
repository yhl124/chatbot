package com.example.chatbot.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.chatbot.model.Profile;




@Repository
public class ProfileRepository implements IProfileRepository {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private class ProfileMapper implements RowMapper<Profile> {
		@Override
		public Profile mapRow(ResultSet rs, int rowNum) throws SQLException {
			Profile profile = new Profile();
			profile.setProfileId(rs.getInt("profile_id"));
			profile.setUserId(rs.getString("user_id"));
			profile.setFileName(rs.getString("file_name"));
			profile.setFileSize(rs.getLong("file_size"));
			profile.setFileContentType(rs.getString("file_content_type"));
			profile.setFileData(rs.getBytes("file_data"));
			return profile;
		}
	}

	@Override
	public void uploadFile(Profile profile) {
		String sql = "INSERT INTO profiles "
				+ "(profile_id, user_id, file_name, file_size, "
				+ "file_content_type, file_data) "
				+ "VALUES (profile_seq.NEXTVAL, ?, ?, ?, ?, ?)";
		jdbcTemplate.update(sql, profile.getUserId(),
				profile.getFileName(), profile.getFileSize(), profile.getFileContentType(), profile.getFileData());
		
	}

	@Override
	public void deleteFile(int profileId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Profile getProfile(int profileId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Profile getProfileByUserId(String userId) {
		String sql = "SELECT profile_id, user_id, file_name, file_size, "
				+ "file_content_type, file_data FROM profiles WHERE user_id=?";
		try {
			return jdbcTemplate.queryForObject(sql, new ProfileMapper(), userId);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
}
