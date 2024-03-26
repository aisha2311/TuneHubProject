package com.tunehub.services;
//import from entitiy class only there is a predefined class in java with the name user dont import from there.
import com.tunehub.entities.Users;

public interface UsersService {
	public String addUser(Users user);
    public boolean emailExists(String email);
    public boolean validateUser(String email,String password);
    public String getRole(String email);
    
    public Users getUser(String email);
    public void updateUser(Users user);
}
