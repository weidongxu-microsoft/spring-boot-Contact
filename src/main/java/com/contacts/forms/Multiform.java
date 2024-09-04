package com.contacts.forms;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Multiform {
	
	@NotNull
	@Range(min=1,message="Select the contact")
	Long contactId;

}
