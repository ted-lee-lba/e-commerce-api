package com.sample.ecommerce.api.exception;

import java.time.LocalDateTime;

import com.sample.ecommerce.api.model.ExceptionResponse;
import com.sample.ecommerce.domain.exception.ElementExistedException;
import com.sample.ecommerce.domain.exception.ElementNotFoundException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {
    
    @Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(ExceptionResponse.builder().timestamp(LocalDateTime.now())
            .error(
                ex.getBindingResult().getFieldErrors().stream().map(err -> err.getDefaultMessage())
                .collect(java.util.stream.Collectors.joining(", ")))
            .status(HttpStatus.BAD_REQUEST.value()).build(), HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler({ ElementNotFoundException.class })
	public ResponseEntity<ExceptionResponse> customHandleException(ElementNotFoundException ex) {
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(ExceptionResponse.builder().timestamp(LocalDateTime.now()).error(ex.getMessage())
				.status(HttpStatus.NOT_FOUND.value()).build(), HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler({ ElementExistedException.class })
	public ResponseEntity<ExceptionResponse> customHandleException(ElementExistedException ex) {
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(ExceptionResponse.builder().timestamp(LocalDateTime.now()).error(ex.getMessage())
				.status(HttpStatus.BAD_REQUEST.value()).build(), HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler({ AccessDeniedException.class })
	public ResponseEntity<ExceptionResponse> customHandleException(AccessDeniedException ex) {
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(ExceptionResponse.builder().timestamp(LocalDateTime.now()).error(ex.getMessage())
				.status(HttpStatus.FORBIDDEN.value()).build(), HttpStatus.FORBIDDEN);

	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<ExceptionResponse> customHandleException(Exception ex) {
		logger.error(ex.getMessage(), ex);
		return new ResponseEntity<>(ExceptionResponse.builder().timestamp(LocalDateTime.now()).error(ex.getMessage())
				.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).build(), HttpStatus.INTERNAL_SERVER_ERROR);

	}
}
