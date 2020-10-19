package com.sami.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sami.enums.Action;
import com.sami.enums.ModuleName;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class ActionLog implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Date created;

	@Enumerated(EnumType.STRING)
	@Column(length = 30)
	private ModuleName moduleName;

	@Enumerated(EnumType.STRING)
	@Column(length = 30)
	private Action action;

	private String userName;

	private String documentId;

	private String comments;

}
