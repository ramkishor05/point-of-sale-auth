package com.brijframwork.authorization.adptor;

import static com.brijframwork.authorization.constant.Constants.ACCESS_TOKEN_VALIDITY_SECONDS;
import static com.brijframwork.authorization.constant.Constants.AUTHORITIES_KEY;
import static com.brijframwork.authorization.constant.Constants.SIGNING_KEY;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.brijframwork.authorization.beans.UserDetailResponse;
import com.brijframwork.authorization.mapper.UserDetailMapper;
import com.brijframwork.authorization.model.EOUserAccount;
import com.brijframwork.authorization.repository.UserAccountRepository;
import com.brijframwork.authorization.service.UserOnBoardingService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

@Component
public class TokenProvider implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private Logger logger=Logger.getLogger(TokenProvider.class.getName());
	

	@Autowired
	private UserDetailMapper userDetailMapper;

	@Autowired
	private UserAccountRepository userLoginRepository;
	
	@Autowired
	private UserOnBoardingService userOnBoardingService;

	public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
    	if(token==null || claimsResolver==null) {
    		return null;
    	}
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
    	try{
    		 return Jwts.parser()
    	                .setSigningKey(SIGNING_KEY)
    	                .parseClaimsJws(token)
    	                .getBody();
    	}catch(Exception e){
    		logger.fine("Exception in TokenProvider : getAllClaimsFromToken() :--"+e.getMessage());
    		e.printStackTrace();
    	}
        return null;
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    
    public long getTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        
        long diff = expiration.getTime() - new Date().getTime();
        return diff / (60 * 1000);
    }

    public String generateToken(Authentication authentication) {
    	logger.fine("Inside TokenProvider : generateToken");
        final String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, SIGNING_KEY)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_VALIDITY_SECONDS * 1000))
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
    	logger.fine("Inside TokenProvider : validateToken");
    	try {
	        final String username = getUsernameFromToken(token);
	        if(StringUtils.isEmpty(username)) {
	        	return false;
	        }
	        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    	}catch (Exception e) {
			return false;
		}
    }
    
    public Boolean validateToken(String token) {
    	return !isTokenExpired(token);
    }

    public UsernamePasswordAuthenticationToken getAuthentication(final String token, final Authentication existingAuth, final UserDetails userDetails) {
    	logger.fine("Inside TokenProvider : getAuthentication");
        final JwtParser jwtParser = Jwts.parser().setSigningKey(SIGNING_KEY);
        final Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
        final Claims claims = claimsJws.getBody();
        final Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

	public UserDetailResponse getUserDetailFromToken(String token) {
		String username = this.getUsernameFromToken(token);
		Optional<EOUserAccount> findUserLogin = userLoginRepository.findUserName(username);
		EOUserAccount eoUserAccount = findUserLogin.orElseThrow(()-> new RuntimeException("Not found!"));
		userOnBoardingService.initOnBoarding(eoUserAccount);
		return userDetailMapper.mapToDTO(eoUserAccount);
	}

}
