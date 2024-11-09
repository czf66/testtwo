package com.flight.base.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Enumeration;

@Component
public class JwtUtils {
    // 设置token过期时间
    public static final long EXPIRE = 1000 * 60 * 60 * 24;// 一天
    // 密钥，随便写，做加密操作
    public static final String APP_SECRET ="LwwwwaaaXUKwYIRoQJndTPFNzAmhLoveLMHandsomeXUKwYIRoQJndTPFNzAmhLwwkLME";

    // 生成jwt字符串的方法
    public static String getJwtToken(Long userId){

        String JwtToken = Jwts.builder()
                //设置头信息，固定
                .setHeaderParam("typ", "JWT") // 声明加密的算法
                .setHeaderParam("alg", "HS256") // 声明类型
                //设置过期时间
                .setSubject("flight-user") // 名字随便取
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                //设置jwt主体部分
                .claim("userId", userId)
                //根据密钥生成字符串
                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();

        return JwtToken;
    }

    /**
     * 判断token是否存在与有效
     * @Param jwtToken
     */
    public static boolean checkToken(String jwtToken){
        if (StringUtils.isEmpty(jwtToken)){
            return false;
        }
        try{
            //验证token
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 判断jwt是否存在与有效
     * @param request
     * @return
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("token");
//            System.out.println("jwtToken"+jwtToken);
            if(StringUtils.isEmpty(jwtToken)) {
                return false;
            }
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            // e.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     * 根据jwt获取用户信息
     * @param request
     * @return
     */
    public static Claims getMemberByJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("token");
        if(StringUtils.isEmpty(jwtToken)) {
            return null;
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        Claims claims = claimsJws.getBody();
        return claims;
    }
}

