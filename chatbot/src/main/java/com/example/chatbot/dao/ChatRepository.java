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
import com.example.chatbot.model.Chat;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class ChatRepository implements IChatRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	private class ChatMapper implements RowMapper<Chat> {
        @Override
        public Chat mapRow(ResultSet rs, int rowNum) throws SQLException {
            Chat chat = new Chat();
            chat.setChatId(rs.getInt("chat_id"));
            chat.setRoomId(rs.getInt("room_id"));
            chat.setSendText(rs.getString("send_text"));
            chat.setSendTime(rs.getDate("send_time"));
            chat.setRecvText(rs.getString("recv_text"));
            chat.setRecvTime(rs.getDate("recv_time"));
            return chat;
        }
    }

	@Override
	public List<Chat> getChatsByRoomId(int roomId) {
		String sql = "select chat_id, room_id, send_text, send_time, recv_text, recv_time from chats where room_id = ?"
				+ "order by chat_id";
		try {
			return jdbcTemplate.query(sql, new ChatMapper(), roomId);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public void insertChatLog(int roomId, String sendText, Date sendTime, String recvText, Date recvTime) {
		String sql = "insert into chats (chat_id, room_id, send_text, send_time, recv_text, recv_time) "
				+ "VALUES (chats_seq.NEXTVAL, ?, ?, ?, ?, ?)";
		
		log.info("채팅로그 : "+roomId + " 보낸 메시지 : " + sendText+ " 보낸 시간 : " + sendTime+ " 받은 메시지 : " + recvText+ " 받은 시간 : " + recvTime);
        jdbcTemplate.update(sql, roomId, sendText, sendTime, recvText, recvTime);
	}

}
