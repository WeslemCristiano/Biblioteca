package model.beans;

import control.Validator;

public class Usuario {

    private String user;
    private String password;

    public Usuario() {
    }

    public Usuario(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
        
    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public boolean validatePassword(String senha){
        return Validator.validatePassword(senha); 
    }
    
}
