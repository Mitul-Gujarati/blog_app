package com.blog.api.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RoleDto {
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //Using this role_id will not display in response.
	private Integer roleId;
	private String name;
}
