package jaz.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import jaz.request.LoginRequest;
import jaz.session.SessionUtils;
import jaz.webapp.UserToDatabase;

import javax.annotation.ManagedBean;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@Named
@ManagedBean
@ApplicationScoped
public class LoginController implements Serializable
{
    @Inject
    UserToDatabase userToDatabase = new UserToDatabase();

    @Inject
    private LoginRequest loginRequest;

    public String register()
    {
        HttpSession session = SessionUtils.getSession();
        session.setAttribute("username", "creatingUser");
        return "true";
    }

    public String validateUsernamePassword()
    {
        boolean valid = false;
        String pass;


        String username = userToDatabase.getUsername(loginRequest.getUsername());

        if(username != "nothing")
        {
            if (username.equals(loginRequest.getUsername()))
            {
                String password = userToDatabase.getPassword(loginRequest.getUsername());
                if (passMatch(loginRequest.getPassword(), password))
                {
                    valid = true;
                }
            }
        }
        else
        {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Can't find user",
                            "Please enter username again"));
        }

        if (valid)
        {
            HttpSession session = SessionUtils.getSession();
            session.setAttribute("username", loginRequest.getUsername());
            pass= "true";
        } else {
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Incorrect Username or Passowrd",
                            "Please enter correct username and Password"));
            pass = "false";
        }
        return pass;
    }

    public String logout()
    {
        HttpSession session = SessionUtils.getSession();
        session.invalidate();
        return "true";
    }

    public boolean passMatch(String password, String hashedPassword)
    {
        var passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(password, hashedPassword);
    }
}