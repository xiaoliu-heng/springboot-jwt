package cn.xiaoliublog.demo.filter;

import cn.xiaoliublog.demo.utils.JWTUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Configurable
public class AuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private final JWTUtils jwtUtils = new JWTUtils();

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String header = httpServletRequest.getHeader("Authorization");
        if ( header == null || header.isEmpty() || !header.startsWith("Bearer")){
            httpServletResponse.sendError(HttpServletResponse.SC_FORBIDDEN);
        }else {
            String token = header.split(" ")[1];
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
