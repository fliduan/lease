package fliduan.leasecompany.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Log4j2
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint{

	private static final String UNAUTHORIZED_EXCEPTION = "ExceptiON: %s, %s, url: %s";
	
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {

		log.error(String.format(UNAUTHORIZED_EXCEPTION, HttpServletResponse.SC_UNAUTHORIZED, e.getMessage(), request.getRequestURL()));
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        
    }
}
