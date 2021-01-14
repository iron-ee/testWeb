package com.cos.testWeb.user.dto;

import lombok.Data;

@Data

public class CommonRespDto<T> {
	private int statusCode;
	private T data;
}
