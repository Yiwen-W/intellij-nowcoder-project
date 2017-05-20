package com.nowcoder.interceptor;

import com.nowcoder.dao.LoginTicketDAO;
import com.nowcoder.dao.UserDAO;
import com.nowcoder.model.HostHolder;
import com.nowcoder.model.LoginTicket;
import com.nowcoder.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 用户身份的验证
 * Created by Wang Yiwen on 2017/5/20.
 */

@Component
public class PassportInterceptor implements HandlerInterceptor{

    @Autowired
    LoginTicketDAO loginTicketDAO;

    @Autowired
    UserDAO userDAO;

    @Autowired
    HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //所有处理http请求的最前面
        String ticket=null;
        if(httpServletRequest.getCookies()!=null){
            for(Cookie cookie:httpServletRequest.getCookies()){
                if(cookie.getName().equals("ticket")){
                    ticket=cookie.getValue();
                    break;
                }
            }
        }
        if(ticket!=null){
            LoginTicket loginTicket=loginTicketDAO.selectByTicket(ticket);
            if(loginTicket!=null||loginTicket.getExpired().before(new Date())||
                    loginTicket.getStatus()!=0){
                return true;
            }

            User user=userDAO.selectById(loginTicket.getUserId());
            hostHolder.setUsers(user);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        //handler处理完之后回到这个地方，渲染之前
        if(modelAndView!=null){//modelandview
            modelAndView.addObject("user",hostHolder.getUser());
        }//把user放到了valocity的上下文
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
         //所有的都渲染完成
        hostHolder.clear();
    }
}
