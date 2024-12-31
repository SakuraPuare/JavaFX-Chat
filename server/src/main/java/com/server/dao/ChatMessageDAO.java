package com.server.dao;

import com.messages.Message;
import com.server.util.DatabaseUtil;

import java.sql.*;

public class ChatMessageDAO {
    
    public void saveMessage(Message message) {
        String sql = "INSERT INTO chat_messages (sender, message, message_type) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, message.getName());
            pstmt.setString(2, message.getMsg());
            pstmt.setString(3, message.getType().toString());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
} 