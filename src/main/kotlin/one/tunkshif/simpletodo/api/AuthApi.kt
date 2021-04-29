package one.tunkshif.simpletodo.api

import one.tunkshif.simpletodo.model.User
import one.tunkshif.simpletodo.security.JwtTokenUtil
import one.tunkshif.simpletodo.service.UserAuthenticationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user", produces = ["application/json"])
@CrossOrigin("*")
class AuthApi(
    @Autowired val authService: UserAuthenticationService,
    @Autowired val authenticationManager: AuthenticationManager,
    @Autowired val jwtTokenUtil: JwtTokenUtil,
    @Autowired val passwordEncoder: PasswordEncoder
) {
    @GetMapping("/register")
    fun register(@RequestParam username: String, @RequestParam password: String) =
        authService.register(username, passwordEncoder.encode(password))

    @GetMapping("/login")
    fun login(@RequestParam username: String, @RequestParam password: String): ResponseEntity<User> {

        val authenticate = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(username, password)
        )
        val user = authenticate.principal as User
        return ResponseEntity.ok()
            .header(HttpHeaders.AUTHORIZATION, jwtTokenUtil.generateAccessToken(user))
            .body(user)
    }
}