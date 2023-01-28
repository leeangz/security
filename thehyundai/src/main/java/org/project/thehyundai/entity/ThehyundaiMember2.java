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
@AllArgsConstructor
@NoArgsConstructor
public class ThehyundaiMember2 implements Serializable{
	
	private String email;
    private String password;
    private String name;
    private int from_social;
    private Date regdate, moddate;
    private String role_set;

}
