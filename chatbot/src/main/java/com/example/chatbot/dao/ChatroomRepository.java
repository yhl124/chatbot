package com.example.chatbot.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.chatbot.model.Chatroom;
import com.example.chatbot.model.User;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class ChatroomRepository implements IChatroomRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private class ChatroomMapper implements RowMapper<Chatroom> {
        @Override
        public Chatroom mapRow(ResultSet rs, int rowNum) throws SQLException {
            Chatroom chatroom = new Chatroom();
            chatroom.setRoomId(rs.getInt("room_id"));
            chatroom.setUserId(rs.getString("user_id"));
            chatroom.setRoomName(rs.getString("room_name"));
            chatroom.setLastUse(rs.getDate("last_use"));
            chatroom.setGenerationTime(rs.getDate("generation_time"));
            return chatroom;
        }
    }

	@Override
	public void insertChatroom(User user) {
		String sql = "INSERT INTO chatroom (room_id, user_id, room_name, last_use, generation_time) "
				+ "VALUES (chatroom_seq.NEXTVAL, "
				+ "?, '새 대화', "
				+ "sysdate, sysdate)";
		
		log.info(user.getUserId());
        jdbcTemplate.update(sql, user.getUserId());
	}
	
	@Override
	public Chatroom getLastChatroomForUser(User user) {
		String sql = "SELECT room_id, user_id, room_name, last_use, generation_time FROM chatroom "
				+ " where user_id = ? order BY room_id DESC "
				+ "FETCH FIRST 1 ROWS ONLY";
		try {
			return jdbcTemplate.queryForObject(sql, new ChatroomMapper(), user.getUserId());
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	@Override
	public List<Chatroom> getChatroomByUserId(String userId) {
		String sql = "select room_id, user_id, room_name, last_use, generation_time from chatroom where user_id = ?"
				+ "order by last_use desc";
		try {
			return jdbcTemplate.query(sql, new ChatroomMapper(), userId);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	@Override
	public void deleteChatroomByRoomId(String userId, int roomId) {
		String sql = "delete from chatroom where user_id = ? and room_id = ?";
		jdbcTemplate.update(sql, userId, roomId);
	}
	
	

	@Override
	public void renameChatroomByRoomId(String roomName, String userId, int roomId) {
		String sql = "update chatroom set room_name = ? where user_id = ? and room_id = ?";
		jdbcTemplate.update(sql, roomName, userId, roomId);
	}

	@Override
	public Chatroom getChatroomByRoomId(int roomId) {
		String sql = "select room_id, user_id, room_name, last_use, generation_time from chatroom where room_id = ?";
		try {
			return jdbcTemplate.queryForObject(sql, new ChatroomMapper(), roomId);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

}
