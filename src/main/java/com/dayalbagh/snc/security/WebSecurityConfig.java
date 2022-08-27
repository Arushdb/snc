package com.dayalbagh.snc.security;


import java.util.Arrays;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.CorsFilter;

import com.dayalbagh.snc.security.jwt.AuthEntryPointJwt;
import com.dayalbagh.snc.security.jwt.AuthTokenFilter;
import com.dayalbagh.snc.service.UserDetailsServiceImpl;




@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		// securedEnabled = true,
		// jsr250Enabled = true,
		prePostEnabled = true)
public class WebSecurityConfig  {

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(
	        AuthenticationConfiguration authConfig) throws Exception {
	    return authConfig.getAuthenticationManager();
	}
	
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	
	@Bean
	public CorsFilter corsFilter() {
		
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
		
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin","Access-Control-Allow-Origin","Content-Type",
				"Accept","Authorization","Origin","Accept","X-Requested-With"));
		
		corsConfiguration.setExposedHeaders(Arrays.asList("Origin","Content-Type","Accept",
				"Authorization","Access-Control-Allow-Origin","Access-Control-Allow-Credentials"));
		
		corsConfiguration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","OPTIONS"));
		
		org.springframework.web.cors.UrlBasedCorsConfigurationSource thesource = new org.springframework.web.cors.UrlBasedCorsConfigurationSource();
				thesource.registerCorsConfiguration("/**", corsConfiguration);
		//theurlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(thesource);
	}
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
 
		
		http.cors().and().csrf().disable()
		//http.cors().and()
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
			//.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS).and()
			.authorizeRequests()
			.antMatchers("/api/auth/**").permitAll()
			.antMatchers("/api/test/**").permitAll() 
			
			
			.anyRequest().authenticated();
		/////////////////////////////////////////////////////////////////////////////////////////
//			.and()
//			.formLogin()
//			.loginPage("/login")
//			.permitAll()
//			.and()
//			.logout()
//				
//			.logoutSuccessHandler(new LogoutSuccessHandler() {
//				
//				@Override
//				public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
//						throws IOException, ServletException {
//					// TODO Auto-generated method stub
//					System.out.println("The User "+authentication.getName()+ "has loggedout ");
//					UrlPathHelper helper = new UrlPathHelper();
//					String context = helper.getContextPath(request);
//					response.sendRedirect(context+"/");
//					
//				}
//			}).permitAll();
		
		

		
//		http
//		.authorizeRequests()
//			.antMatchers("/", "/home").permitAll()
//			.anyRequest().authenticated()
//			.and()
//		.formLogin()
//						
//			.and()
//		.logout()
//		     .logoutSuccessHandler(new LogoutSuccessHandler() {
//				
//				@Override
//				public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
//						throws IOException, ServletException {
//					System.out.println("The User "+authentication.getName()+ "has logged out ");
//					UrlPathHelper helper = new UrlPathHelper();
//					String context = helper.getContextPath(request);
//					System.out.println("Context :"+context);
//					response.sendRedirect(context+"/"+"login");
//				}
//			})
//			.permitAll();
		
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	
	return http.build();
	
	}
	
}
