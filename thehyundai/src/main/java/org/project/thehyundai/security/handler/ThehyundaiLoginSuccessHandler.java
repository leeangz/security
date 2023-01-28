package org.project.thehyundai.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.project.thehyundai.security.dto.ThehyundaiAuthMemberDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ThehyundaiLoginSuccessHandler implements AuthenticationSuccessHandler {
	// 구성자 추가 SecurityConfig 에서 사용
    public ThehyundaiLoginSuccessHandler(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
 
    // RedirectStrategy 인터페이스 생성 sendRedirect() 메서드 이용
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
 
    // 사용자 암호 확인 용
    private PasswordEncoder passwordEncoder;
 
    // 인증 성공시 진행되는 코드
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        log.info("------------------------");
        log.info("onAuthenticationSuccess....");
 
        // 인증 객체에서 사용자 정보 저장
        ThehyundaiAuthMemberDTO AuthMemberDTO = (ThehyundaiAuthMemberDTO) authentication.getPrincipal();
        log.info(AuthMemberDTO);
        // 소셜 사용자인지 확인
        int fromSocial = AuthMemberDTO.getFromSocial();
        // 사용자 암호 1111 인지 확인
        boolean passresult = passwordEncoder.matches("1111", AuthMemberDTO.getPassword());
 
        // 소셜 사용자이고 암호 1111이면 modify.html 페이지로 이동
        if ((fromSocial == 1) && passresult) {
            redirectStrategy.sendRedirect(request, response, "/sample/modify?from=social");
        } // end if
    }// end onAu…
 
}// end class
