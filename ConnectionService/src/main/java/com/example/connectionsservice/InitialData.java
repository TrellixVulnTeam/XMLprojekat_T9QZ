package com.example.connectionsservice;

import com.example.connectionsservice.Model.Message;
import com.example.connectionsservice.Model.Notification;
import com.example.connectionsservice.Model.User;
import com.example.connectionsservice.Repository.UserRepository;
import com.example.connectionsservice.Service.MessageService;
import com.example.connectionsservice.Service.NotificationService;
import com.example.connectionsservice.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class InitialData {


    private final UserService userService;

    private final MessageService messageService;

    private final NotificationService notificationService;
    
    private final UserRepository userRepository;

    public InitialData(UserService userService,MessageService messageService, NotificationService notificationService,UserRepository userRepository ){
        this.userService = userService;
        this.messageService = messageService;
        this.notificationService = notificationService;
        this.userRepository=userRepository;
    }


    @PostConstruct
    public void init(){
		List<String> following = new ArrayList<>();
		following.add("stef");
		
		List<String> following1 = new ArrayList<>();
		following1.add("marko99");
		
		List<String> following_marko=new ArrayList<>();
		following_marko.add("lana");
		
		List<String> following_stef=new ArrayList<>();
		following_stef.add("lana99");
		
		List<String> following_maja=new ArrayList<>();
		
		List<String> following_admin=new ArrayList<>();
///////////////////follow requests/////////////////////////
		List<String> followRequests1 = new ArrayList<>();
		followRequests1.add("maja");
		
		List<String> followRequests2 = new ArrayList<>();
		followRequests2.add("maja");
		
		List<String> followRequests3 = new ArrayList<>();
		followRequests2.add("lana99");
		
		List<String> followRequests3_stef=new ArrayList<>();
		followRequests3_stef.add("lana");
		
		List<String> followRequests_public=new ArrayList<>();
		
		List<String> followRequests_admin=new ArrayList<>();
		///////////////////////////////////////////
        List<Message> mess = new ArrayList<>();
        List<Message> mess2 = new ArrayList<>();
        List<Notification> notifs = new ArrayList<>();
        
        
 
        Date time = new Date();
       
        
        /////////////messages//////////////////////////////
        Message m1 = new Message("50","maja","marko99","hi",time);
        messageService.save(m1);

        Message m2 = new Message("51","maja","marko99","caoo",time);
        messageService.save(m2);

        Message m3 = new Message("52","lana99","marko99","hej",time);
        messageService.save(m3);
        
        Message m5 = new Message("53","marko99","lana99","sta ima",time);
        messageService.save(m5);
///////////////////////////////////blocking lists////////////////////////////////
        List<String> blocked1 = new ArrayList<>();
        blocked1.add("lana99");
        
        List<String> blocked2 = new ArrayList<>();

        List<String> blocked3 = new ArrayList<>();
        
        List<String> blocked4 = new ArrayList<>();
        blocked4.add("marko99");
        
        List<String> blocked5 = new ArrayList<>();
        blocked5.add("admin");
        
        List<String> blocked6 = new ArrayList<>();
        blocked6.add("marko99");
        
        List<String> blocked_maja=new ArrayList<>();
        
        List<String> blocked_admin=new ArrayList<>();

        User u1=new User((long)1,"lana","123","Lana","Lanic","lana@gmail.com","3242476777","female","/assets/profilePicture/profile1.jpeg",true,following1,followRequests1,mess,mess2,blocked1,notifs,notifs);
        User u2=new User((long)2,"lana99","123","Lana","Lanic","lanal@gmail.com","3242476777","female","/assets/profilePicture/profile2.jpeg",false,following,followRequests_public,mess,mess2,blocked2,notifs,notifs);
        User u3=new User((long)3,"marko99","123","Marko","Markovic","markom@gmail.com","3242476777","male","/assets/profilePicture/profile3.jpeg",true,following_marko,followRequests3,mess,mess2,blocked3,notifs,notifs);
        User u4=new User((long)4,"stef","123","Stefan","Stefanovic","stefans@gmail.com","3242476777","male","/assets/profilePicture/profile4-man.jpeg",true,following_stef,followRequests3_stef,mess,mess2,blocked4,notifs,notifs);
        User u5=new User((long)5,"maja","123","Maja","Majkovic","maja@gmail.com","3242476777","female","/assets/profilePicture/profile5-female.jpeg",false,following_maja,followRequests_public,mess,mess2,blocked_maja,notifs,notifs);
		User u6=new User((long)6,"admin", "123","Nikola","Nikolic", "admin@example.com","3242476777","female","/assets/profilePicture/profile7-admin.jpeg",true,following_admin,followRequests_admin,mess,mess2,blocked_admin,notifs,notifs);

		
		
		
        userRepository.save(u1);
        userRepository.save(u2);
        userRepository.save(u3);
        userRepository.save(u4);
        userRepository.save(u5);
        userRepository.save(u6);
    }


}
