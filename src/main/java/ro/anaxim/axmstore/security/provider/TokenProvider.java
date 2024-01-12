package ro.anaxim.axmstore.security.provider;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import ro.anaxim.axmstore.user.domain.UserPrincipal;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static java.lang.System.currentTimeMillis;
import static java.util.Arrays.stream;


@Component
public class TokenProvider {

    public static final String AUTHORITIES = "authorities";
    private static final long ACCESS_TOKEN_EXPIRATION_TIME = 18000000;
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 432000000;
    @Value(value = "${jwt.secret}")
    private String secret;


    public String createAccessToken(UserPrincipal userPrincipal){
        String[] claims = getClaimsFromUser(userPrincipal);
        return JWT.create().withIssuer("ANAXIM").withAudience(" ")
                .withIssuedAt(new Date()).withSubject(userPrincipal.getUsername())
                .withArrayClaim(AUTHORITIES,claims).withExpiresAt(new Date(currentTimeMillis() + REFRESH_TOKEN_EXPIRATION_TIME))
                .sign(HMAC512(secret.getBytes()));
        
    }


    public String createRefreshToken(UserPrincipal userPrincipal){
        String[] claims = getClaimsFromUser(userPrincipal);
        return JWT.create().withIssuer("ANAXIM").withAudience(" ")
                .withIssuedAt(new Date()).withSubject(userPrincipal.getUsername())
                .withExpiresAt(new Date(currentTimeMillis() + ACCESS_TOKEN_EXPIRATION_TIME))
                .sign(HMAC512(secret.getBytes()));

    }

    private String[] getClaimsFromUser(UserPrincipal userPrincipal) {
        return userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toArray(String[]::new);
    }


    public Authentication getAuthentication(String email, List<GrantedAuthority> authorities, HttpServletRequest request){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(email,null,authorities);
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return  usernamePasswordAuthenticationToken;
    }

    public boolean isTokenValid(String email,String token){
        JWTVerifier verifier = getVerifier();
        return StringUtils.isNotEmpty(email) && !isTokenExpired(verifier,token);
    }

    private boolean isTokenExpired(JWTVerifier verifier, String token) {
        Date expiration  = verifier.verify(token).getExpiresAt();
                return expiration.before(new Date());
    }


    public String getSubject(String token,HttpServletRequest request){
        try{
            return getVerifier().verify(token).getSubject();
        }
        catch(TokenExpiredException ex){
            request.setAttribute("expiredMessage",ex.getMessage());
            throw ex;

        }catch (InvalidClaimException ex){
            request.setAttribute("InvalidClaim",ex.getMessage());

            throw ex;
        }catch(Exception exception){
            throw exception;

        }


    }

    public List<GrantedAuthority> getAuthorities(String token){
            String[] claims = getClaimsFromToken(token);
            return stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    private String[] getClaimsFromToken(String token) {
        JWTVerifier jwtVerifier = getVerifier();
        return jwtVerifier.verify(token).getClaim(AUTHORITIES).asArray(String.class);
    }

    private JWTVerifier getVerifier() {
        JWTVerifier jwtVerifier;
        try{
            Algorithm algorithm = HMAC512(secret);
            jwtVerifier = JWT.require(algorithm).withIssuer("ANAXIM").build();

        }
        catch(JWTVerificationException ex){ throw new JWTVerificationException("JWT VERIFICATION EXCEPTION");}

   return jwtVerifier;

    }


}
