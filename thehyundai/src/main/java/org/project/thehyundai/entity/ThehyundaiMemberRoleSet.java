package org.project.thehyundai.entity;

import java.io.Serializable;
import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
 
@Data
@Setter
@Getter
public class ThehyundaiMemberRoleSet implements Serializable{
    private String club_member_email ;
    private String role_set ;   
}//end class