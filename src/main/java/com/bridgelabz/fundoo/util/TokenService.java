package com.bridgelabz.fundoo.util;

import java.security.Key;
import java.util.Date;

import javax.xml.bind.DatatypeConverter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoo.exception.FundooException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class TokenService {

	//@Value("${secret.token}")
	public String tokenSecretKey = "bridgelabz";

	public String createToken(Long id) {

		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
		JwtBuilder builder = Jwts.builder().setId(String.valueOf(id))
				 //.setExpiration(new Date (System.currentTimeMillis()+( 180 * 1000)))
				.signWith(signatureAlgorithm,DatatypeConverter.parseString(tokenSecretKey) );
		return builder.compact();

	}
	
	public String createToken(Long id, Date expireTime) {

		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS512;
		JwtBuilder builder = Jwts.builder().setId(String.valueOf(id))
				 .setExpiration(expireTime)
				.signWith(signatureAlgorithm, DatatypeConverter.parseString(tokenSecretKey));
		return builder.compact();

	}

	public Long decodeToken(String token) {
		try {
			Claims claim = Jwts.parser().setSigningKey(DatatypeConverter.parseString(tokenSecretKey)).parseClaimsJws(token)
					.getBody();
			return Long.parseLong(claim.getId());

		} catch (Exception e) {
			throw new FundooException(HttpStatus.BAD_REQUEST.value(),e.getMessage());

		}
		
	}
	
}
