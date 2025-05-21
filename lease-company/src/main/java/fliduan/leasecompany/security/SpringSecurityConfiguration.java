package fliduan.leasecompany.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fliduan.leasecompany.dto.ApiKeyDto;
import fliduan.leasecompany.dto.ApiKeysDto;
import fliduan.leasecompany.logging.LogUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.header.writers.CrossOriginResourcePolicyHeaderWriter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Log4j2
public class SpringSecurityConfiguration {
	private final LogUtil logUtil;

	@Value("${lease-company.apikeys:defaultApiKeys}")
	private String apiKeysJson;

	private static final String UNAUTHORIZED_EXCEPTION = "Exceptie: %s, %s, url: %s";
	private static final String APIKEY_MISSING_EXCEPTION = "API key is missing in the request.";
	private static final String APIKEY_INCORRECT_EXCEPTION = "The given API key is incorrect.";
	private static final String APIKEYS_IS_NULL_EXCEPTION = "De API key list is empty.";

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.cors(Customizer.withDefaults());
		http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers(getAuthWhiteList()).permitAll()
                        .anyRequest().authenticated())
				.sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.exceptionHandling(exceptionHandling -> exceptionHandling
				.authenticationEntryPoint((request, response, ex) -> {
					log.error(String.format(UNAUTHORIZED_EXCEPTION, HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage(), request.getRequestURL()));
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				}
				)
		);

		http.headers(headers ->
				headers
						.httpStrictTransportSecurity(hstsConfig ->
								hstsConfig.preload(true)
										.includeSubDomains(true)
										.maxAgeInSeconds(63072000))
						.contentSecurityPolicy(cspConfig ->
								cspConfig.policyDirectives("default-src 'self'; frame-src 'self'; frame-ancestors 'self'"))
						.referrerPolicy(referrerPolicyConfig ->
								referrerPolicyConfig.policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.SAME_ORIGIN))
						.crossOriginResourcePolicy(crossOriginResourcePolicyConfig ->
								crossOriginResourcePolicyConfig.policy(CrossOriginResourcePolicyHeaderWriter.CrossOriginResourcePolicy.SAME_SITE)));

		// API key validation
		ApiKeyAuthenticationFilter filter = new ApiKeyAuthenticationFilter();

		ApiKeysDto apiKeys = convertToObject(apiKeysJson, ApiKeysDto.class);
		filter.setContinueFilterChainOnUnsuccessfulAuthentication(false);
		filter.setAuthenticationManager(
				authentication -> {
					String apiKeyUitRequest = (String) authentication.getPrincipal();
					if (apiKeyUitRequest == null) {
						   throw new BadCredentialsException (APIKEY_MISSING_EXCEPTION);
						}

					ApiKeyDto apiKeyDto = findApiKeyDto(apiKeys, apiKeyUitRequest);
					if (apiKeyDto == null) {
						throw new BadCredentialsException(APIKEY_INCORRECT_EXCEPTION);
					}

					logUtil.logApiKeyClient(apiKeyDto.getClient());
					authentication.setAuthenticated(true);
					return authentication;
				});
		http.addFilter(filter);
		http.addFilterBefore(new ExceptionTranslationFilter(new CustomAuthenticationEntryPoint()), filter.getClass());

        return http.build();
    }

    /**
	 * Get all generic whitelist
	 * @return String[] generic whitelist
	 */
	protected String[] getAuthWhiteList() {
		String[] whiteList = {
				"/swagger-ui/**",
				"/v3/api-docs/**",
				"/actuator/**",								
				"/*/*.css",
				"/*/*.js",
				"/favicon.ico"
		};

		return whiteList;
	}

	/**
	 * Check whether key appears in apiKeys. There is no check whether the offered key actually
	 * belongs to the caller api; this could only be done if the incoming request shows who the caller is.

	 * @param apiKeys list of all apikeys that can be used.
	 * @param key key to validate
	 * @return {@link ApiKeyDto} the matching Api key from
	 */
	protected ApiKeyDto findApiKeyDto(ApiKeysDto apiKeys, String key) {
		if (apiKeys == null || apiKeys.getApiKeys() == null) {
			log.error(APIKEYS_IS_NULL_EXCEPTION);
			return null;
		}	
		
		return apiKeys.getApiKeys().stream()
						.filter (a -> (key.equals(a.getKey1()) || key.equals(a.getKey2())))
						.findAny()
						.orElse(null);
		
	}

	public static <T> T convertToObject(String json, Class<T> clazz)  {
		try {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.readValue(json, clazz);
		}
		catch (JsonProcessingException e) {
			log.error("Exception occurred during deserialization of json string: " + e.getOriginalMessage());
			return null;
		}
	}
}
