package org.project.thehyundai.repository;

import java.sql.SQLException;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.project.thehyundai.entity.ThehyundaiMember;
import org.project.thehyundai.entity.ThehyundaiMember2;
import org.project.thehyundai.entity.ThehyundaiMemberRoleSet;

@Mapper
public interface ThehyundaiMemberRepository {
	public void insertThehyundaiMember(ThehyundaiMember thehyundaiMember);
	public void insertThehyundaiRoleSet(ThehyundaiMemberRoleSet thehyundaiMemberRoleSet);
	public ThehyundaiMember2 findByEmail(@Param("email") String email
            , @Param("social") int social)
    throws SQLException;


}

