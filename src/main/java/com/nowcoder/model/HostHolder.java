package com.nowcoder.model;

import org.springframework.stereotype.Component;

/**
 * Created by Wang Yiwen on 2017/5/20.
 */
@Component
public class HostHolder {
    private static ThreadLocal<User> users=new ThreadLocal<User>();
    //为每一个线程都分配了一个变量，但是可以通过公共的接口访问
    public User getUser(){
        return users.get();
        //根据当前线程，找到当前线程的user
    }
    public void setUsers(User user){
        users.set(user);
   }
    public void clear(){
        users.remove();
    }
}
