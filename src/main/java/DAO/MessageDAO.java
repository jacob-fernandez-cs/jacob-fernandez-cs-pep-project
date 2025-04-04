package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Util.ConnectionUtil;

import Model.Message;

public class MessageDAO {

    public Message createMessage(Message message)
    {
        Connection connection = ConnectionUtil.getConnection();

        try {
           

            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) values (?, ?, ?);" ;
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            //write preparedStatement's setString and setInt methods here.
            preparedStatement.setInt(1, message.getPosted_by());
            preparedStatement.setString(2, message.getMessage_text());
            preparedStatement.setLong(3, message.getTime_posted_epoch());
            
            preparedStatement.executeUpdate();
           
            ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
            if(pkeyResultSet.next()){
                int generated_message_id = (int) pkeyResultSet.getLong(1);
                return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return null;
    }


    public List<Message> getAllMessages(){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "select * from message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                        messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public Message getMessageById(int id){
        Connection connection = ConnectionUtil.getConnection();
        try {
            //Write SQL logic here
            String sql = "select * from message where message_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            //write preparedStatement's setInt method here.
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                        
                return message;
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;

    }

    public List<Message> getAllMessagesByUser(int id){
        Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            //Write SQL logic here
            String sql = "select * from message where posted_by = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                        messages.add(message);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return messages;

    }

    public Message deleteMessageById(int id) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            // Step 1: Retrieve the message you want to delete
            String fetchSql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement fetchStatement = connection.prepareStatement(fetchSql);
            fetchStatement.setInt(1, id);
            ResultSet rs = fetchStatement.executeQuery();
    
            // Check if the message exists
            if (rs.next()) {
                // Step 2: If the message exists, proceed with the delete operation
                String deleteSql = "DELETE FROM message WHERE message_id = ?";
                PreparedStatement deleteStatement = connection.prepareStatement(deleteSql);
                deleteStatement.setInt(1, id);
                deleteStatement.executeUpdate(); // Delete the message
    
                // Return the deleted message (as a Message object)
                Message message = new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch")
                );
                return message;
            } else {
                // If no message is found with the given ID
                System.out.println("Message with ID " + id + " does not exist.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Message updateMessage(int id, String Umessage){
        
        Connection connection = ConnectionUtil.getConnection();
        
        try {
              
            String updateSql = "update message set message_text = ? WHERE message_id = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateSql);
            updateStatement.setString(1, Umessage);
            updateStatement.setInt(2, id);
            updateStatement.executeUpdate();// Step 1: Retrieve the message you want to update
            
            String fetchSql = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement fetchStatement = connection.prepareStatement(fetchSql);
            fetchStatement.setInt(1, id);
            ResultSet rs = fetchStatement.executeQuery();
    
            // Check if the message exists
            if (rs.next()) {
               
                // Return the deleted message (as a Message object)
                Message message = new Message(
                        rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch")
                );
                return message;
            } else {
                // If no message is found with the given ID
                System.out.println("Message with ID " + id + " does not exist.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        
        
        return null;
    }
    
    
}
