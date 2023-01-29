package org.project.thehyundai.security.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.project.thehyundai.entity.ThehyundaiMember2;
import org.project.thehyundai.repository.ThehyundaiMemberRepository;
import org.project.thehyundai.security.dto.ThehyundaiAuthMemberDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ThehyundaiDetailsService implements UserDetailsService {

	@Autowired
	private ThehyundaiMemberRepository MemberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// 입력한 이메일로 ClubMember 찾음
		ThehyundaiMember2 result = null;
		try {
			log.info(username);
			result = MemberRepository.findByEmail(username, 0);
		} catch (SQLException e) {
			log.info(e.getMessage());
		} // end try

		if (result == null) {
			try {
				result = MemberRepository.findByEmail(username, 1);
			} catch (SQLException e1) {
				throw new UsernameNotFoundException("Check Email or Social!!");
			} // end try

		} // end if

		// clubMember 생성
		ThehyundaiMember2 Member2 = result;
		log.info("-------------------");
		log.info(Member2);
		log.info(Member2.getRole_set().toString());

		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + Member2.getRole_set()));

		// clubMember --> ClubAuthMemberDTO 변환
		ThehyundaiAuthMemberDTO DTO = new ThehyundaiAuthMemberDTO(Member2.getEmail(), Member2.getPassword(),
				Member2.getFrom_social(), authorities);
		// ClubAuthMemberDTO 값 세팅
		DTO.setName(Member2.getName());
		DTO.setFromSocial(Member2.getFrom_social());
		DTO.setPassword(Member2.getPassword());

		log.info(DTO);
		log.info(DTO.getAuthorities().toString());

		// ClubAuthMemberDTO는 UserDetails 타입으로 처리됨
		return DTO;
	}// end load..

}// end Cla...
