package com.sami.constants;

public class SecurityConstant {

	public static final String CLAIM_KEY_USERNAME = "sub";
	public static final String CLAIM_KEY_ROLE = "permission";
	public static final String CLAIM_KEY_AUDIENCE = "audience";
	public static final String CLAIM_KEY_CREATED = "created";
	public static final String CLAIM_KEY_EXPIRED = "exp";
	public static final String AUDIENCE_UNKNOWN = "unknown";
	public static final String AUDIENCE_WEB = "web";
	public static final String AUDIENCE_MOBILE = "mobile";
	public static final String AUDIENCE_TABLET = "tablet";
	
	public static final String[] PUBLIC_MATECHERS = {
    		"/setup",
    		"/login",
    		"/link/**",
    		"/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security/**",
            "/swagger-ui.html",
            "/webjars/**",
            "/permission-data-upload/**",
            "/ministry/**",
            "/eoi/**",
            "/advertisement-tender-count/**",
            "/reset/**",
            "/actuator/info"
			};
    
	public static final String[] SWAGGER_MATECHERS = {
    		"/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security/**",
            "/swagger-ui.html",
            "/webjars/**"
			};
    
	public static final String[] HTTP_ALLOWS_METHODS = {
    		"GET",
    		"POST",
    		"PUT",
    		"DELETE",
    		"OPTIONS"
    		};
}
