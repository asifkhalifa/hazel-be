package com.hazelngrazel.app.entity;

import jakarta.persistence.*;

@Entity
public class ProfileEntity {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long usrId;

	 private String name;

	 @Column(unique = true, nullable = false)
	 private String email;

	 private String password;

	 @Enumerated(EnumType.STRING)
	 private Role role;

	 public Long getUsrId() {
		 return usrId;
	 }

	 public void setUsrId(Long usrId) {
		 this.usrId = usrId;
	 }

	 public String getName() {
		 return name;
	 }

	 public void setName(String name) {
		 this.name = name;
	 }

	 public String getEmail() {
		 return email;
	 }

	 public void setEmail(String email) {
		 this.email = email;
	 }

	 public String getPassword() {
		 return password;
	 }

	 public void setPassword(String password) {
		 this.password = password;
	 }

	 public Role getRole() {
		 return role;
	 }

	 public void setRole(Role role) {
		 this.role = role;
	 }
}