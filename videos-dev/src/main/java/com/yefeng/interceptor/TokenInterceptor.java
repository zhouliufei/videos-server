package com.yefeng.interceptor;

import com.alibaba.fastjson.JSON;
import com.yefeng.UserLoginThreadLocal;
import com.yefeng.dto.UserTokenDTO;
import com.yefeng.util.JsonResult;
import com.yefeng.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        //如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        if(token==null||token.equals("")){
            printJson(response, "");
            return false;
        }
        String userId = redisUtil.get(token);
        if(userId == null || userId.trim().length() == 0) {
            printJson(response,"");
            return false;
        }
        redisUtil.expireTime(userId);
        redisUtil.expireTime(token);
        UserTokenDTO userTokenDTO = new UserTokenDTO(userId,token);
        UserLoginThreadLocal.set(userTokenDTO);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, X-Requested-With, token");
        response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, OPTIONS, POST, PUT, DELETE");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Max-Age", "3600");
    }

    private static void printJson(HttpServletResponse response, String code) {
        JsonResult jsonResult = new JsonResult("token过期,请重新登陆",null,401);
        String content = JSON.toJSONString(jsonResult);
        printContent(response, content);
    }


    private static void printContent(HttpServletResponse response, String content) {
        try {
            response.reset();
            response.setContentType("application/json");
            response.setHeader("Cache-Control", "no-store");
            response.setCharacterEncoding("UTF-8");
            PrintWriter pw = response.getWriter();
            pw.write(content);
            pw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
