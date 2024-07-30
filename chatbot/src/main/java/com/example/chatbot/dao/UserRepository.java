package com.example.chatbot.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.example.chatbot.model.User;

@Repository
public class UserRepository implements IUserRepository, UserDetailsService{

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private class UserMapper implements RowMapper<User>{
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException{
			User user = new User();
			user.setUserId(rs.getString("user_id"));
			user.setPassword(rs.getString("password"));
			user.setName(rs.getString("user_name"));
			user.setBirthday(rs.getDate("birthday"));
			user.setGender(rs.getString("gender"));
			user.setEmail(rs.getString("email"));
			
			return user;
		}
	}
	
	@Override
	public int getUserCount() {
		String sql = "select count(*) from users";
		//String sql = "select count(*) from employees";
		System.out.println("UserRepository - getUserCount");
		return jdbcTemplate.queryForObject(sql, Integer.class);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    try {
	        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE user_id = ?", new UserMapper(), username);
	    } catch (EmptyResultDataAccessException e) {
	        throw new UsernameNotFoundException("User not found");
	    } catch (Exception e) {
	        throw new RuntimeException("Database error", e);
	    }
	}
}
