package one.tunkshif.simpletodo.service

import one.tunkshif.simpletodo.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserRepositoryUserDetailsService(
    @Autowired
    val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails =
        userRepository.findByUsername(username) ?: throw UsernameNotFoundException("User $username not found")
}