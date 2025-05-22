package com.kodvix.healthsafetyawareness.entity;


import jakarta.persistence.*;
import org.hibernate.type.descriptor.jdbc.VarcharJdbcType;

import java.time.LocalDateTime;

@Entity
@Table
public class Solution {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String text;
	private LocalDateTime createdAt;
	private VarcharJdbcType problem;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public VarcharJdbcType getProblem() {
		return problem;
	}
	public void setProblem(VarcharJdbcType problem) {
		this.problem = problem;
	}

	@PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
	
}