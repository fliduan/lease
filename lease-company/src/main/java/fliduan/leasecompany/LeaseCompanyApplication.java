package fliduan.leasecompany;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
@SecurityScheme(name="X-Api-Key", in= SecuritySchemeIn.HEADER, type = SecuritySchemeType.APIKEY)
public class LeaseCompanyApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeaseCompanyApplication.class, args);
	}

}
