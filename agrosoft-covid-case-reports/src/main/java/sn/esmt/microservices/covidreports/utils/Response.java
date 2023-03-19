package sn.esmt.microservices.covidreports.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Response {
	private Map<String, Object> body = new LinkedHashMap<>();

	public void prepareSuccessResponse(String message) {
		prepareResponse(HttpStatus.OK.value(), ResponseStatus.OK.name(), message);
	}

	public void prepareSuccessResponse(int code, String message) {
		prepareResponse(code, ResponseStatus.OK.name(), message);
	}

	public void prepareCreatedSuccessResponse(int code, String message, Object data) {
		prepareResponse(code, HttpStatus.CREATED.name(), message, data);
	}

	public void prepareCreatedSuccessResponse(int code, String message, Object data, String entity) {
		prepareResponse(code, HttpStatus.CREATED.name(), message, data, entity);
	}

	public void prepareCreatedSuccessResponse(int code, String message) {
		prepareResponse(code, HttpStatus.CREATED.name(), message);
	}

	public void prepareSuccessResponse(int code, String message, Object data) {
		prepareResponse(code, ResponseStatus.OK.name(), message, data);
	}

	public void prepareSuccessResponse(int code, String message, Object data, String entity) {
		prepareResponse(code, ResponseStatus.OK.name(), message, data, entity);
	}

	public void prepareFailureResponse(Exception e) {
		prepareResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ResponseStatus.KO.name(), e.getMessage());
	}

	public void prepareFailureResponse(int code, String message) {
		prepareResponse(code, ResponseStatus.KO.name(), message);
	}

	public void prepareResponse(Integer code, String status, String message) {
		body.clear();
		body.put("code", code);
		body.put("status", status);
		body.put("message", message);
	}

	public void prepareResponse(Integer code, String status, String message, Object data) {
		prepareResponse(code, status, message);
		body.put("data", data);
	}

	public void prepareResponse(Integer code, String status, String message, Object data, String entityName) {
		prepareResponse(code, status, message);
		Map<String, Object> datas = new HashMap<>();
		datas.put(entityName, data);
		body.put("data", datas);
	}
}