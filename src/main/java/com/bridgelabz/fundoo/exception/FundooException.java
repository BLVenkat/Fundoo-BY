package com.bridgelabz.fundoo.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FundooException extends RuntimeException {
	
	private static final long serialVersionUID = -2205404817330167264L;

	private int statusCode;
	
	private String statusMessage;
}
