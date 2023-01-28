package org.project.thehyundai.controller;

import org.project.thehyundai.security.dto.ThehyundaiAuthMemberDTO;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
@RequestMapping("/sample/")
public class SampleController {

	@GetMapping("/all")
	public void exALL() {
		log.info("exAll.....");
	}// end ex..

	/*
	 * @GetMapping("/member") public void exMember(){ log.info("exMember.....");
	 * }//end ex..
	 */

	@GetMapping("/member")
	public void exMember(@AuthenticationPrincipal ThehyundaiAuthMemberDTO MemberDTO) {
		log.info("exMember.....");
		log.info("--------------");
		log.info(MemberDTO);
	}// end ex..

	@GetMapping("/admin")
	public void exAdmin() {
		log.info("exAdmin.....");
	}// end ex..

	@GetMapping("/modify")
	public void exmodify(@AuthenticationPrincipal ThehyundaiAuthMemberDTO AuthMemberDTO) {
		log.info("exModify.....");
		log.info("--------------");
		log.info(AuthMemberDTO);
	}// end ex..

}// end class
