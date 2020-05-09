package cn.xiaoliublog.demo.filter;

import cn.xiaoliublog.demo.utils.JWTUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@Component
@Configurable
public class AuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private final JWTUtils jwtUtils = new JWTUtils();

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        val cookie = WebUtils.getCookie(httpServletRequest, "authorization");
        if ( cookie == null || cookie.getValue().isEmpty()){
            httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
        }else {
            String token = cookie.getValue();
            try {
                DecodedJWT data = jwtUtils.verify(token);
                httpServletRequest.setAttribute("username", data.getClaim("username").asString());
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            }catch (JWTVerificationException e){
                httpServletResponse.sendError(498, "Invalid Token");
            }
        }
    }
}
