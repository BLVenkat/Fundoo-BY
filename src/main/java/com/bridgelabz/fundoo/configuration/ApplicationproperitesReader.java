package com.bridgelabz.fundoo.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import lombok.Getter;

@Configuration
@Component
@Getter
public class ApplicationproperitesReader {

	@Value("${zone.id}")
	private String zoneId;

	@Value("${aws.s3.access}")
	private String s3Access;

	@Value("${aws.s3.secret}")
	private String s3secretKey;

	@Value("${aws.s3.region}")
	private String region;

	@Value("${aws.s3.bucket}")
	private String bucketName;
}
