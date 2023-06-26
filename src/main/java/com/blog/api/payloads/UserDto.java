package com.blog.api.payloads;

import java.util.HashSet;
import java.util.Set;

import com.blog.api.entities.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	private Integer id;

	@NotEmpty
	@Size(min = 4, message = "Username must be min of 4 Characters!!")
	private String name;

	@Email(message = "Email Address Is Not Valid!!")
	private String email;

	@NotEmpty
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$", message = "Password must be between 8 and 20 characters long and contain at least one uppercase letter, one lowercase letter, one digit, and one special character")
	private String password;

	@NotEmpty
	private String about;
	
	private Set<RoleDto> roles = new HashSet<>();
}
