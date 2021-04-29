package one.tunkshif.simpletodo.api

import one.tunkshif.simpletodo.service.UserAuthenticationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user", produces = ["application/json"])
@CrossOrigin("*")
class UserController(
    @Autowired val authService: UserAuthenticationService,
    @Autowired val passwordEncoder: PasswordEncoder
) {
    @GetMapping("/register")
    fun register(@RequestParam name: String, @RequestParam password: String) =
        authService.register(name, passwordEncoder.encode(password))
}