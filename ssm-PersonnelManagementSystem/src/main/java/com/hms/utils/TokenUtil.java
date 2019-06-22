package com.hms.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

public class TokenUtil {
	private static final long EXPIRE_TIME = 15 * 60 * 1000;
	private static final String TOKEN_SECRET = "thefirsttoken123";
	/**
	 * ����ǩ����15���ӹ���
	 * @param **username**
	* @param **password**
	* @return
	 */
	public static String sign(String loginname, String password,String username) {
	    try {
	        // ���ù���ʱ��
	        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
	        // ˽Կ�ͼ����㷨
	        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
	        // ����ͷ����Ϣ
	        Map<String, Object> header = new HashMap<String, Object>(2);
	        header.put("Type", "Jwt");
	        header.put("alg", "HS256");
	        // ����token�ַ���
	        return JWT.create()
	                .withHeader(header)
	                .withClaim("loginName", loginname)
	                .withClaim("password", password)
	                .withClaim("username", username)
	                .withExpiresAt(date)
	                .sign(algorithm);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	
	/**
	 * ����token�Ƿ���ȷ
	 * @param **token**
	* @return
	 */
	public static boolean verify(String token){
	    try {
	        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
	        JWTVerifier verifier = JWT.require(algorithm).build();
	        DecodedJWT jwt = verifier.verify(token);
	        return true;
	    } catch (Exception e){
	        return false;
	    }
	}
	
	/**
	 * ��token�л�ȡusername��Ϣ
	 * @param **token**
	* @return
	 */
	public static String getUserName(String token){
	    try {
	        DecodedJWT jwt = JWT.decode(token);
	        return jwt.getClaim("loginName").asString();
	    } catch (JWTDecodeException e){
	        e.printStackTrace();
	        return null;
	    }
	}

}
