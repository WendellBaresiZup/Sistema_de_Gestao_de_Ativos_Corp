//package com.zup.gestaodeativos.exceptions;
//
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.servlet.NoHandlerFoundException;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(UserExceptions.class)
//    public ResponseEntity<ErrorResponse> handleUserException(UserExceptions ex) {
//        HttpStatus status = HttpStatus.BAD_REQUEST;
//        try {
//            int code = Integer.parseInt(ex.getCode());
//            HttpStatus resolved = HttpStatus.resolve(code);
//            if (resolved != null) status = resolved;
//        } catch (Exception e) {
//        }
//        return ResponseEntity.status(status)
//                .body(new ErrorResponse(
//                        ex.getCode(),
//                        ex.getMessage(),
//                        ex.getDescription(),
//                        status.name()
//                ));
//    }
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
//        String msg = ex.getBindingResult().getFieldErrors().stream()
//                .map(e -> e.getField() + ": " + e.getDefaultMessage())
//                .findFirst().orElse("Dados inválidos");
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .body(new ErrorResponse(
//                        "400",
//                        "Dados inválidos",
//                        msg,
//                        HttpStatus.BAD_REQUEST.name()
//                ));
//    }
//
//    @ExceptionHandler(NoHandlerFoundException.class)
//    public ResponseEntity<ErrorResponse> handleNotFound(NoHandlerFoundException ex, HttpServletRequest request) {
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body(new ErrorResponse(
//                        "404",
//                        "Recurso não encontrado",
//                        "O endpoint " + request.getRequestURI() + " não foi encontrado.",
//                        HttpStatus.NOT_FOUND.name()
//                ));
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleOther(Exception ex) throws Exception {
//        String pkg = ex.getClass().getPackageName();
//        if (pkg.startsWith("org.springdoc")) {
//            throw ex;
//        }
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(new ErrorResponse(
//                        "500",
//                        "Erro interno",
//                        ex.getMessage(),
//                        HttpStatus.INTERNAL_SERVER_ERROR.name()
//                ));
//    }
//}