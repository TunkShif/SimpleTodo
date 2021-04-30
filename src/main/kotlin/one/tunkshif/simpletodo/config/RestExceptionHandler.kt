package one.tunkshif.simpletodo.config

import io.jsonwebtoken.security.SignatureException
import one.tunkshif.simpletodo.model.ResponseFormat
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.MissingPathVariableException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.lang.NullPointerException

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
class RestExceptionHandler : ResponseEntityExceptionHandler() {
    private fun buildResponseEntity(status: HttpStatus, msg: String): ResponseEntity<Any> =
        ResponseEntity(
            ResponseFormat(code = status.value(), msg = msg, success = false, data = null), status
        )

    override fun handleMissingPathVariable(
        ex: MissingPathVariableException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> =
        buildResponseEntity(status, "missing parameters")

    override fun handleMissingServletRequestParameter(
        ex: MissingServletRequestParameterException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> =
        buildResponseEntity(status, "missing parameters")

    @ExceptionHandler(BadCredentialsException::class)
    fun handleBadCredentialException(
        ex: BadCredentialsException,
    ): ResponseEntity<Any> =
        buildResponseEntity(HttpStatus.BAD_REQUEST, "invalid username or password")


    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthenticationException(
        ex: AuthenticationException,
    ): ResponseEntity<Any> =
        buildResponseEntity(HttpStatus.UNAUTHORIZED, "unauthorized request")

    @ExceptionHandler(SignatureException::class)
    fun handleJwtTokenSignatureException(
        ex: SignatureException
    ): ResponseEntity<Any> =
        buildResponseEntity(HttpStatus.UNAUTHORIZED, "unauthorized request")


    @ExceptionHandler(NullPointerException::class)
    fun handleNullPointerException(
        ex: NullPointerException
    ): ResponseEntity<Any> =
        buildResponseEntity(HttpStatus.BAD_REQUEST, "invalid resource id")


    @ExceptionHandler(org.springframework.security.access.AccessDeniedException::class)
    fun handleAccessDeniedException(
        ex: org.springframework.security.access.AccessDeniedException
    ): ResponseEntity<Any> =
        buildResponseEntity(HttpStatus.BAD_REQUEST, "access denied")
}
