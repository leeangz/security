package org.project.thehyundai.security;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.project.thehyundai.entity.ThehyundaiMember2;
import org.project.thehyundai.repository.ThehyundaiMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ThehyundaiMember2Test2 {

	@Autowired
	private ThehyundaiMemberRepository thehyundaiMemberRepository;

	@Test
	   public void testRead() throws SQLException{
	       //찾을 데이터 이메일
	       ThehyundaiMember2 reult
	               = thehyundaiMemberRepository.findByEmail("user95@project.thehyundai",0);
	       ThehyundaiMember2 clubMember2 = reult;
	       System.out.println(clubMember2);
	   }//end testRead..


}
