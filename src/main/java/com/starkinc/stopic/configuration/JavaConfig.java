package com.starkinc.stopic.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.starkinc.stopic.constants.Constants;
import com.starkinc.stopic.entity.Error;
import com.starkinc.stopic.util.EncryptorUtil;
import com.starkinc.stopic.util.ServiceUtil;

@Configuration
public class JavaConfig {
	
	private TextEncryptor textEncryptor;
	
	@Bean(name="noRecordFound")
	public ResponseEntity<Object> responseEntityNRF(){
		return ServiceUtil.buildEntity(HttpStatus.NOT_FOUND, new Error(HttpStatus.NOT_FOUND, Constants.NO_RECORD_FOUND));
	}
	
	@Bean(name="invalidRequest")
	public ResponseEntity<Object> responseEntityIR(){
		return ServiceUtil.buildEntity(HttpStatus.BAD_REQUEST, new Error(HttpStatus.BAD_REQUEST, Constants.INVALID_REQUEST));
	}
	
	@Bean(name="noContent")
	public ResponseEntity<Object> responseEntityNC(){
		return ServiceUtil.buildEntity(HttpStatus.NO_CONTENT, null);
	}
	
	@Bean(name="topicSearchValidation")
	public ResponseEntity<Object> responseEntityTS(){
		return ServiceUtil.buildEntity(HttpStatus.BAD_REQUEST, new Error(HttpStatus.BAD_REQUEST, Constants.TOPIC_SEARCH));
	}
	
	@Bean(name="topicAlreadyExist")
	public ResponseEntity<Object> responseEntityTAE(){
		return ServiceUtil.buildEntity(HttpStatus.CONFLICT, new Error(HttpStatus.BAD_REQUEST, Constants.TOPIC_ALREADY_EXIST));
	}
	
	@Bean(name="userAlreadyExist")
	public ResponseEntity<Object> responseEntityUAE(){
		return ServiceUtil.buildEntity(HttpStatus.CONFLICT, new Error(HttpStatus.BAD_REQUEST, Constants.USER_ALREADY_EXIST));
	}
	
	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder(5);
	}
	
	@Bean
	public TextEncryptor textEncryptor(@Value(Constants.SALT) String salt, @Value(Constants.SECRECT_KEY) String key){
		textEncryptor = Encryptors.text(key, salt);
		return textEncryptor;
	}
	
	@Bean
	public EncryptorUtil encryptorUtil(){
		return new EncryptorUtil(textEncryptor);
	}

}
