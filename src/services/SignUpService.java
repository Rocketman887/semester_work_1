package services;

import models.User;
import repositories.UserRepositoryJDBCImpl;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;


public class SignUpService {
    private PasswordHashCodeService passwordHashCodeService = new PasswordHashCodeService();
    private UserRepositoryJDBCImpl userRepositoryJDBC = new UserRepositoryJDBCImpl();
    private CreateProfileService createProfileService = new CreateProfileService();
    public void signUp(String login, String password) throws NoSuchAlgorithmException {
        User user = new User(login,passwordHashCodeService.passwordHashCode(password));
        userRepositoryJDBC.save(user);
        createProfileService.createProfile(user);
    }
    public boolean checkLoginInput(String login) {
        return login.length() >=3;
    }
    public boolean checkPasswordInput(String password){
        return password.length()>=7;
    }
    public boolean isNotExist(String login){
        return userRepositoryJDBC.findByLogin(login).size()==0;
    }
    public boolean checkForbiddenSeq(String login){
        String regexTitleInput = "[a-zA-ZА-Яа-я0-9]";
        return Pattern.matches(login,regexTitleInput);
    }

}
