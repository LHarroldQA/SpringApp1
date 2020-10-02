package com.qa.app.DTO;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor(access= AccessLevel.PUBLIC)
@AllArgsConstructor(access= AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@ToString
@EqualsAndHashCode
public class GuitaristDTO {
	
	private Long id;
	private String name;
	private Integer noOfStrings;
	private String type;

}
