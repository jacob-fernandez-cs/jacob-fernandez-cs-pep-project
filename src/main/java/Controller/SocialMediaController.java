package Controller;

import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;

import io.javalin.Javalin;
import io.javalin.http.Context;

import Service.AccountService;
import Service.MessageService; 

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    

    AccountService accountService;
    MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

     public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postRegisterHandler);
        app.post("/login", this::postRegisterHandler2);
        app.post("/messages", this::postRegisterHandler3);
        app.get("/messages", this::getAllMessageHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::updateMessageByIdHandler);
        app.get("/accounts/{account_id}/messages", this::getMessageByUserHandler);

        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void postRegisterHandler(Context context) throws JsonProcessingException  {
        //context.json("sample text");

        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.addAccount(account);
        
        if(addedAccount==null){
            context.status(400);
        }else{
            context.json(mapper.writeValueAsString(addedAccount));
        }

        ///System.out.println("I can be seen! ");

    }


    private void postRegisterHandler2(Context context) throws JsonProcessingException  {
        //context.json("sample text");

        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.loginAccount(account);
        
        if(addedAccount==null){
            context.status(401);
        }else{
            context.json(mapper.writeValueAsString(addedAccount));
        }



    }


    private void postRegisterHandler3(Context context) throws JsonProcessingException  {
        //context.json("sample text");

        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message addedMessage = messageService.createMessage(message);
        
        if(addedMessage==null){
            context.status(400);
        }else{
            context.json(mapper.writeValueAsString(addedMessage));
        }



    }

    private void updateMessageByIdHandler(Context context) throws JsonProcessingException {
        
        String messageU = context.body();
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, String> map = objectMapper.readValue(messageU, HashMap.class);
        System.out.println(map);
        String lastSegment = context.pathParam("message_id");
        messageU = map.get("message_text");

        System.out.println("**********************" + messageU + "Hi! ");

       // Message message = messageService.getMessageById(Integer.parseInt(lastSegment));
       
       Message message = messageService.updateMessage(Integer.parseInt(lastSegment), messageU);
        
         
        if (message != null)
        {
        context.json(message);
        }
        else
        {
            context.json("");
            context.status(400);
        }

    }


    public void getMessageByUserHandler(Context ctx){
        
        String accountId = ctx.pathParam("account_id");
        
        List<Message> messages = messageService.getAllMessagesByUser(Integer.parseInt(accountId));
        ctx.json(messages);

    }


    public void getAllMessageHandler(Context ctx){
        List<Message> messages = messageService.getAllMessages();
        ctx.json(messages);
    }

    public void getMessageByIdHandler(Context context) throws JsonProcessingException{
        
        String lastSegment = context.pathParam("message_id");
       
       // Message message = messageService.getMessageById(Integer.parseInt(lastSegment));
        Message message = messageService.getMessageById(Integer.parseInt(lastSegment));
        
        
        if (message != null)
        {
        context.json(message);
        }
        else
            context.json("");
    }
 
    public void deleteMessageByIdHandler(Context context) throws JsonProcessingException{
        
        String id = context.fullUrl();
        String lastSegment = null;
        int lastSlashIndex = id.lastIndexOf('/');
        if (lastSlashIndex != -1) {
            lastSegment = id.substring(lastSlashIndex + 1);
        } else {
            System.out.println("No '/' found in the URL.");
        }

        if (lastSegment != null) {
            System.out.println(lastSegment); // Output: resource
        }
       
       // Message message = messageService.getMessageById(Integer.parseInt(lastSegment));
        Message message = messageService.deleteMessageById(Integer.parseInt(lastSegment));
        
        
        if (message != null)
        {
        context.json(message);
        }
        else
            context.json("");
    }


}