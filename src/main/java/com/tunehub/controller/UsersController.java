package com.tunehub.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tunehub.entities.LoginData;
import com.tunehub.entities.Song;
import com.tunehub.entities.Users;
import com.tunehub.services.SongService;
import com.tunehub.services.UsersService;

import jakarta.servlet.http.HttpSession;
 //@CrossOrigin("*")
@Controller
public class UsersController {
	@Autowired
	UsersService service;
	
	@Autowired
	SongService songService;
	//post mapping annotation is a spring boot annotation that is used to map
	// HTTP post requests onto specific handler method.
	@PostMapping("/register")
	public String addUsers(@ModelAttribute Users user
			){
		  boolean userStatus= service.emailExists(user.getEmail());
		  if(userStatus == false) {
			  
			  service.addUser(user);
			  System.out.println("user added");
		  }
		  else {
			  System.out.println("user exists");
		  }
	
		return "home";
		
	}
	
	
	@PostMapping("/validate")
/*	public String  validate(@RequestBody LoginData data ,  --remove email and password as input-- */
	public String  validate(@RequestParam("email")  String email,
			@RequestParam("password") String password,
			HttpSession session ,Model model){
		
		// System.out.println("call recieved");
		
		
		       //  String email = data.getEmail();
		       //  String password = data.getEmail();
		      if(service.validateUser(email,password)==true) {
		    	  String role = service.getRole(email);
		    	  
		    	  session.setAttribute("email", email);
		    	  
		    	  if(role.equals("admin")) {
		    		  return "adminHome";
		    	  }
		    	  else {
		    		  Users user = service.getUser(email);
		    		  boolean userStatus = user.isPremium();
		    		  List<Song> songsList = songService.fetchAllSongs();
		    		  model.addAttribute("songs",songsList);
		    		  
		    		  model.addAttribute("isPremium",userStatus);
		    		  
		    		  return "customerHome";
		    	  }
		    	  
		      }
		      else {
		    	  return "login";
		      }
				
			}
	
	
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		
		session.invalidate();
		return "login";
	}
	
	
	
	
	
	

}
