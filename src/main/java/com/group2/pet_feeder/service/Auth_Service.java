package com.group2.pet_feeder.service;

import com.group2.pet_feeder.entity.User;
import com.group2.pet_feeder.other.HttpClient;
import com.group2.pet_feeder.repository.Auth_Repository_Interface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Objects;

@Service
public class Auth_Service implements Auth_Service_Interface {

    @Autowired
    private Auth_Repository_Interface repository;

    @Override
    public HashMap<String, Object> login(User user) {
        HashMap<String, Object> rtn = repository.login(user);
        if ("success".equals(rtn.get("message"))) {
            HttpSession session = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession();
            session.setAttribute("operator", user.getUsername());
            System.out.println("log in success");
        }
        return rtn;
    }

    @Override
    public byte[] getPhoto() {
//        repository.getPhoto();
        HttpClient client = new HttpClient(new RestTemplateBuilder());
        return client.getPhoto();
    }

    @Override
    public HashMap<String, Object> register(User user) {
        HashMap<String, Object> rtn = repository.register(user);
        return rtn;
    }

    @Override
    public HashMap<String, Object> checkUsername(String username) {
        return repository.checkUsername(username);
    }
}
