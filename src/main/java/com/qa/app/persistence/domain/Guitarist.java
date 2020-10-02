package com.qa.app.persistence.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.Max;

@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@ToString
@EqualsAndHashCode
public class Guitarist {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "guitarist_name", unique=true)
	@Size(min =1,max=120)
	private String name;
	
	@Column(name = "strings")
	@Min(4)
	@Max(12)
	private Integer noOfStrings;
	
	@Column(name = "type")
	@Size(min =1,max=120)
	private String type;
	
	@ManyToOne
	private Band band;

//	public Guitarist(@Size(min = 1, max = 120) String name,
//			@Min(4) @Max(12) @Size(min = 1, max = 120) Integer nofStrings, @Size(min = 1, max = 120) String type) {
//		super();
//		this.name = name;
//		this.noOfStrings = nofStrings;
//		this.type = type;
//	}
	
	public Guitarist(String name, Integer nofStrings,String type) {
		super();
		this.name = name;
		this.noOfStrings = nofStrings;
		this.type = type;
	}
}
