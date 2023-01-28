package org.project.thehyundai.security.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.project.thehyundai.entity.ThehyundaiMember;
import org.project.thehyundai.entity.ThehyundaiMember2;
import org.project.thehyundai.entity.ThehyundaiMemberRole;
import org.project.thehyundai.entity.ThehyundaiMemberRoleSet;
import org.project.thehyundai.repository.ThehyundaiMemberRepository;
import org.project.thehyundai.security.dto.ThehyundaiAuthMemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ThehyundaiOAuth2UserDetailsService extends DefaultOAuth2UserService {
	// db 저장
	@Autowired
	private ThehyundaiMemberRepository MemberRepository;
	// 패스워드 암호화
	@Autowired
	private PasswordEncoder passwordEncoder;

	private ThehyundaiMember2 saveSocialMember(String email) throws SQLException {
		log.info("saveSocialMember  시작");
		// 기본에 동일한 이메일로 가입한 회원인지 확인
		ThehyundaiMember2 result = MemberRepository.findByEmail(email, 1);
		// 기본 회원이면 정보 반환
		if (!(result == null)) {
			log.info("기존 회원");
			return result;
		} // end if

		// 가입한적이 없다면 추가 패스워드 1111 이름은 이메일주소
		ThehyundaiMember Member = new ThehyundaiMember();
		Member.setEmail(email);
		Member.setName(email);
		Member.setPassword(passwordEncoder.encode("1111"));
		Member.setFrom_social(1);
		// 디비에 ClubMember 행저장
		MemberRepository.insertThehyundaiMember(Member);

		ThehyundaiMemberRoleSet MemberRoleSet = new ThehyundaiMemberRoleSet();
		MemberRoleSet.setClub_member_email(email);
		MemberRoleSet.setRole_set(ThehyundaiMemberRole.USER.toString());
		// 디비에 ClubRoleSet 행저장
		MemberRepository.insertThehyundaiRoleSet(MemberRoleSet);

		result = MemberRepository.findByEmail(email, 1);
		// 추가된 정보 반환
		return result;
	}// end saveSocialMember..

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		log.info("-------loaduser-------------");
		log.info("userRequest" + userRequest);

		String clienName = userRequest.getClientRegistration().getClientName();
		// 인증 제공자 출력
		log.info("clienName" + clienName);
		log.info(userRequest.getAdditionalParameters());

		// 사용자 정보 가져오기 구글에서 허용한 API 범위
		OAuth2User oAuth2User = super.loadUser(userRequest);
		log.info("======oAuth2User===============");
		oAuth2User.getAttributes().forEach((k, v) -> {
			log.info(k + " : " + v);
		});// end foreach

		// 신규회원 테이블에 저장 시작
		String email = null;
		if (clienName.equals("Google")) {// 구글 인증 확인
			email = oAuth2User.getAttribute("email");
		} // end if
		log.info("구글 인증 확인");
		log.info("email : " + email);

		try {
			ThehyundaiMember2 Member2 = saveSocialMember(email);
			log.info("---saveSocialMember--");
			log.info(Member2);
			// ClubAuthMemberDTO 생성시 필요한 authorities
			List<GrantedAuthority> authorities = new ArrayList<>();
			authorities.add(new SimpleGrantedAuthority("ROLE_" + Member2.getRole_set()));

			log.info(Member2.getPassword());
			// OAuth2User 를 clubAuthMemberDTO 로 변환
			ThehyundaiAuthMemberDTO AuthMemberDTO = new ThehyundaiAuthMemberDTO(Member2.getEmail(),
					Member2.getPassword(), 1, authorities, oAuth2User.getAttributes());
			AuthMemberDTO.setName(Member2.getName());
			AuthMemberDTO.setPassword(Member2.getPassword());
			// clubAuthMemberDTO --> UserDetails 반환
			log.info("OAuth2User 를 clubAuthMemberDTO");
			log.info(AuthMemberDTO);
			return AuthMemberDTO;

		} catch (SQLException e) {
			log.info("saveSocialMember error");
			log.info("에러 ");
			log.info(e.toString());
			return null;
		} // end try

	}// end load..

}// end class
