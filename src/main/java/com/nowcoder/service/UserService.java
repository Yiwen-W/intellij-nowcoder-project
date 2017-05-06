package com.nowcoder.service;

import antlr.StringUtils;
import com.nowcoder.controller.HomeController;
import com.nowcoder.dao.UserDAO;
import com.nowcoder.model.User;
import com.nowcoder.util.WendaUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;


/**
 * Created by Wang Yiwen on 2017/4/25.
 */
@Service
public class UserService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserDAO userDAO;

    public Map<String,String> register(String username,String password){
        Map<String,String> map=new HashMap<String,String>();
        if(org.apache.commons.lang.StringUtils.isBlank(username)){
            map.put("msg","用户名为空");
            return map;
        }
        if(org.apache.commons.lang.StringUtils.isBlank(password)){
            map.put("msg","密码不能为空");
            return map;
        }

        User user=userDAO.selectByName(username);
        if(user!=null){
            map.put("msg","用户名已经被注册");
            return map;
        }
        user=new User();
        user.setName(username);
        user.setSalt(UUID.randomUUID().toString().substring(0,5));
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(1000)));
        user.setPassword(WendaUtil.MD5(password+user.getSalt()));
        userDAO.addUser(user);
        return map;

    }

    public Map<String,String> login(String username,String password){
        Map<String,String> map=new HashMap<String,String>();
        if(org.apache.commons.lang.StringUtils.isBlank(username)){
            map.put("msg","用户名为空");
            return map;
        }
        if(org.apache.commons.lang.StringUtils.isBlank(password)){
            map.put("msg","密码不能为空");
            return map;
        }

        User user=userDAO.selectByName(username);
        if(user==null){
            map.put("msg","用户名不存在");
            return map;
        }
       if(!WendaUtil.MD5(password+user.getSalt()).equals(user.getPassword())){
            map.put("msg","密码错误");
            return map;
       }

        return map;

    }

    public User getUser(int id) {
        return userDAO.selectById(id);
    }
}
