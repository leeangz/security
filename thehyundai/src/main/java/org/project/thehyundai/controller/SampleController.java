package org.project.thehyundai.controller;

import org.project.thehyundai.security.dto.ThehyundaiAuthMemberDTO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/sample/")
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SampleController {

	@PreAuthorize("permitAll()")
	@GetMapping("/all")
	public void exALL() {
		log.info("exAll.....");
	}// end ex..

	/*
	 * @GetMapping("/member") public void exMember(){ log.info("exMember.....");
	 * }//end ex..
	 */

	@PreAuthorize("hasRole('USER')")
	@GetMapping("/member")
	public void exMember(@AuthenticationPrincipal ThehyundaiAuthMemberDTO MemberDTO) {
		log.info("exMember.....");
		log.info("--------------");
		log.info(MemberDTO);
	}// end ex..

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/admin")
	public void exAdmin() {
		log.info("exAdmin.....");
	}// end ex..

	@PreAuthorize("hasRole('USER')")
	@GetMapping("/modify")
	public void exmodify(@AuthenticationPrincipal ThehyundaiAuthMemberDTO AuthMemberDTO) {
		log.info("exModify.....");
		log.info("--------------");
		log.info(AuthMemberDTO);
	}// end ex..

	// user95@project.thehyundai 만 접근 가능
	@PreAuthorize(" #ThehyundaiAuthMemberDTO != null " + 
	" && #ThehyundaiAuthMemberDTO.username eq \"user95@project.thehyundai\" ")
	@GetMapping("/exOnly")
	public void exMemebrOnly(@AuthenticationPrincipal ThehyundaiAuthMemberDTO AuthMemberDTO) {
		log.info("exMemberOnly-------");
		log.info(AuthMemberDTO);
	}// end exM..

}// end class
