package org.project.thehyundai.security;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;
import org.project.thehyundai.entity.ThehyundaiMemberRole;
import org.project.thehyundai.entity.ThehyundaiMemberRoleSet;
import org.project.thehyundai.repository.ThehyundaiMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ThehyundaiMemberRoleSetTest {
	@Autowired
	private ThehyundaiMemberRepository clubMemberRepository;

	@Test
	public void insertDummies2() throws SQLException {
		// 1~80 USER 81~90 USER,MANAGER
		// 91~100 USER,MANAGER,ADMIN
		for (int i = 1; i <= 101; i++) {
			ThehyundaiMemberRoleSet MemberRoleSet = new ThehyundaiMemberRoleSet();
			MemberRoleSet.setClub_member_email("user" + i + "@project.thehyundai");
			String role = null;
			// 기본 권한 user
			role = ThehyundaiMemberRole.USER.toString();
			// 81번 부터 manager 권한 추가
			if (i > 80) {
				role = ThehyundaiMemberRole.MANAGER.toString();
			} // end if
				// 91번 부터 admin권한 추가
			if (i > 90) {
				role = ThehyundaiMemberRole.ADMIN.toString();
			} // end if
			MemberRoleSet.setRole_set(role);
			// db에 저장
			clubMemberRepository.insertThehyundaiRoleSet(MemberRoleSet);
		}
		;// end for

	}// end insert..
}// end class
