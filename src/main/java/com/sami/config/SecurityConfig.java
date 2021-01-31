package com.sami.config;

import static com.sami.constants.SecurityConstant.PUBLIC_MATECHERS;
import static com.sami.constants.SecurityConstant.SWAGGER_MATECHERS;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sami.security.CustomAuthenticationEntryPoint;
import com.sami.security.CustomAuthenticationTokenFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private UserDetailsService userDetailsService;
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

   
    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(this.userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CustomAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new CustomAuthenticationTokenFilter();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
    	
        httpSecurity
			        .csrf().disable()
			        .cors().and()
	                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
	                .and()
	                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	                .and()
	                .authorizeRequests()
	                .antMatchers(PUBLIC_MATECHERS)
	                .permitAll()
	                .anyRequest()
	                .authenticated()
	                .and()
	                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
        
       httpSecurity
                    .headers().cacheControl();
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(SWAGGER_MATECHERS);
    }
    
    @Bean
    public WebMvcConfigurer corsConfigurer() {
    	
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("*").allowedOriginPatterns("*");
            }
        };
    }
    
    
}

