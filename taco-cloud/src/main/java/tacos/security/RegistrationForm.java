package tacos.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.Data;
import tacos.User;

@Data//정확히 뭘하는지 알아야 할듯
public class RegistrationForm {

    private String username;
    private String password;
    private String fullname;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String phone;

    public String getInfo(){
        return this.username+"  "+ this.password+"  "+this.street;
    }

//    public User toUser(PasswordEncoder passwordEncoder){
//        return new User(username, passwordEncoder.encode(password),fullname,street,city,state,zip,phone);
//    }

}
