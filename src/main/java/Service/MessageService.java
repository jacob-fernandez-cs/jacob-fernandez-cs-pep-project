package Service;



import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
    
    private MessageDAO messageDAO;


     public MessageService(){
        messageDAO = new MessageDAO();
    }
    
    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }


    public Message createMessage(Message message)
    {

        if(message.getMessage_text().length() <= 255 && !(message.getMessage_text().isEmpty()))

            return messageDAO.createMessage(message);
        
        return null;
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int id)
    {
        return messageDAO.getMessageById(id);
    }

    public List<Message> getAllMessagesByUser(int accountID)
    {
        return messageDAO.getAllMessagesByUser(accountID);
    }

    public Message updateMessage(int id, String message)
    {
        
        if(message.length() <= 255 && message.length() > 0)
            return messageDAO.updateMessage(id, message);
        else
            return null;
    }

    public Message deleteMessageById(int id)
    {
        return messageDAO.deleteMessageById(id);
    }
}
