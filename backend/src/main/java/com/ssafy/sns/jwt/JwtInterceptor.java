package com.ssafy.sns.jwt;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtService jwtService;

    public static final Logger logger = LoggerFactory.getLogger(JwtInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info(request.getMethod() + " : " + request.getServletPath());

        // option 요청은 바로 통과시켜주자.
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        } else {
            String token = request.getHeader("Authorization");

            if (token != null && token.length() > 0) {
                try {
                    // 유효한 토큰인지 확인
                    jwtService.checkValid(token);

                    // 토큰 꺼내기
                    Map<String, Object> result = jwtService.getToken(token);
                    // 토큰 타입 확인하기 (Subject)
                    String type = (String) result.get("sub");

                    if (type.equals("AccessToken")) {   // Access Token 토큰일 때
                        // Access Token 사용 가능
                        String refToken = (String) result.get("RefreshToken");
                        // Refresh Token 유효한지 확인
                        jwtService.checkValid(refToken);
                        logger.info("Access Token 사용 가능 : {}", token);
                        return true;
                    } else if (type.equals("RefreshToken")) {   // Refresh Token 토큰일 때
                        // Refresh Token 으로 재발급 요청
                        return true;
                    }
                } catch(ExpiredJwtException e) {
                    // Token 만료
                    logger.warn("Token 만료 : {}", e.getMessage());
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write(writeResult("expire"));
                    return false;
                } catch (Exception e) {
                    // 그외의 에러
                    logger.warn("JWT 그외 에러 : {}", e.getMessage());
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write(writeResult("fail"));
                    return false;
                }
            }
        }

        logger.warn("Token 없음");
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        response.getWriter().write(writeResult("fail"));
        return false;
    }

    public String writeResult (String type) {
        return "{\"result\": \"" + type + "\"}";
    }
}
