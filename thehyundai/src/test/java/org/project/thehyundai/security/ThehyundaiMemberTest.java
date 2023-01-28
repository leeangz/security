package org.project.thehyundai.security;

import java.sql.SQLException;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.project.thehyundai.entity.ThehyundaiMember;
import org.project.thehyundai.entity.ThehyundaiMemberRole;
import org.project.thehyundai.repository.ThehyundaiMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class ThehyundaiMemberTest {

	@Autowired
	private ThehyundaiMemberRepository thehyundaiMemberRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Test
	public void insertDummies() throws SQLException {
		for (int i = 1; i <= 101; i++) {
			ThehyundaiMember Mem = new ThehyundaiMember();
			Mem.setEmail("user" + i + "@project.thehyundai");
			Mem.setName("사용자" + i);
			Mem.setFrom_social(0);
			Mem.setPassword(passwordEncoder.encode("1111"));
			thehyundaiMemberRepository.insertThehyundaiMember(Mem);
		}
		;

		System.out.println("입력 완료");
	}

}
