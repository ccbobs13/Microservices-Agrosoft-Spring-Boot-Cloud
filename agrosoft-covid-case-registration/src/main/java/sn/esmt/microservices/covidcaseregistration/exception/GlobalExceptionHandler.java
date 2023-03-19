package sn.esmt.microservices.covidcaseregistration.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	private Map<String, Object> body = new LinkedHashMap<>();

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		body.clear();
		body.put("timestamp", LocalDateTime.now());
		body.put("code", status.value());
		body.put("status", "KO");
		List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());

		body.put("errors", errors);
		return ResponseEntity.status(status.value()).body(body);
	}

	/**
	 * Gestion de l'exception contraintViolation
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> constraintViolationException(ConstraintViolationException ex, WebRequest request) {
		body.clear();
		body.put("timestamp", LocalDateTime.now());
		body.put("code", HttpStatus.BAD_REQUEST.value());
		body.put("status", "KO");

		List<String> errors = new ArrayList<>();
		ex.getConstraintViolations().forEach(cv -> errors.add(cv.getMessage()));

		body.put("errors", errors);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(body);
	}
	

//	@Override
//	@ResponseStatus(HttpStatus.NOT_FOUND)
//	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
//			HttpStatusCode status, WebRequest request) {
//		body.clear();
//		body.put("timestamp", LocalDateTime.now());
//		body.put("code", status.value());
//		body.put("status", "KO");
//		body.put("error", ex.getMessage());
//		return ResponseEntity.status(status.value()).body(body);
////		return super.handleNoHandlerFoundException(ex, headers, status, request);
//	}
}
