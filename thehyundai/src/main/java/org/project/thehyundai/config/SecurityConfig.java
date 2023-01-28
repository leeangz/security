package org.project.thehyundai.config;

import org.project.thehyundai.security.handler.ThehyundaiLoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.log4j.Log4j2;

@Configuration
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	//LoginSuccessHandler 등록
    @Bean
    public ThehyundaiLoginSuccessHandler successHandler(){
        return new ThehyundaiLoginSuccessHandler(passwordEncoder());
    }//end CLu..   


	@Bean
	public RoleHierarchyImpl roleHierarchyImpl() {
		log.info("실행");
		RoleHierarchyImpl roleHierarchyImpl = new RoleHierarchyImpl();
		roleHierarchyImpl.setHierarchy("ROLE_ADMIN > ROLE_MANAGER > ROLE_USER");
		return roleHierarchyImpl;
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}// end pass..

	/*
	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception { // 사용자 계정 세팅 user1//패스워드 1111
	 * auth.inMemoryAuthentication().withUser("user1")
	 * .password("$2a$10$qbTVRGiC8RePIsMz4z/QP.LjBmLOMGXBCkmW2comzfNaoeidd5/aa").
	 * roles("USER"); }// configure
	 */

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// /samle/all 모든 사용자 가능
		// /sample/member USER 롤 사용자만
		http.authorizeRequests().antMatchers("/samle/all").permitAll().antMatchers("/sample/member").hasRole("USER");

		// 인가 인증 문제시 로그인 화면
		http.formLogin();
		// crsf 비활성화
		http.csrf().disable();
		// 로그 아웃 세팅
		http.logout();
		
		// 구글 oauth 인증 추가
		http.oauth2Login().successHandler(successHandler());

	}// end configure http

}// end class
