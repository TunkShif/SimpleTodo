package one.tunkshif.simpletodo.security

import one.tunkshif.simpletodo.extension.Logging
import one.tunkshif.simpletodo.extension.logger
import one.tunkshif.simpletodo.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtTokenFilter(
    @Autowired private val jwtTokenUtil: JwtTokenUtil,
    @Autowired private val userRepo: UserRepository
) : OncePerRequestFilter(), Logging {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token: String? = request.getHeader(HttpHeaders.AUTHORIZATION)
        if (token.isNullOrEmpty() || !jwtTokenUtil.validate(token)) {
            filterChain.doFilter(request, response)
            return
        }
        val user: UserDetails? = userRepo.findByUsername(jwtTokenUtil.getUsername(token))
        val authentication = UsernamePasswordAuthenticationToken(
            user, null, user?.authorities
        )
        authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
        SecurityContextHolder.getContext().authentication = authentication
        filterChain.doFilter(request, response)
    }
}
