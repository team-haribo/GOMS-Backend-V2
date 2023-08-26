package com.goms.v2.global.security

import com.goms.v2.domain.account.Authority
import com.goms.v2.global.filter.config.FilterConfig
import com.goms.v2.global.security.handler.CustomAccessDeniedHandler
import com.goms.v2.global.security.handler.CustomAuthenticationEntryPoint
import com.goms.v2.global.security.jwt.JwtParser
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtParser: JwtParser
) {

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors()
            .and()
            .csrf().disable()
            .formLogin().disable()
            .httpBasic().disable()
            .apply(FilterConfig(jwtParser))
        authorizeHttpRequests(http)
        exceptionHandling(http)
        return http.build()
    }

    private fun authorizeHttpRequests(http: HttpSecurity) {
        http.authorizeRequests()
            // /auth
            .mvcMatchers(HttpMethod.POST, "/api/v2/auth/signin").permitAll()
            .mvcMatchers(HttpMethod.PATCH, "/api/v2/auth").permitAll()
            .mvcMatchers(HttpMethod.POST, "/api/v2/auth/email/send").permitAll()
            .mvcMatchers(HttpMethod.GET, "/api/v2/auth/email/verify").permitAll()

            // /account
            .mvcMatchers(HttpMethod.GET, "/api/v2/account/profile").hasAnyAuthority(Authority.ROLE_STUDENT.name, Authority.ROLE_STUDENT_COUNCIL.name)

            // /outing
            .mvcMatchers(HttpMethod.POST, "/api/v2/outing/{outingUUID}").hasAnyAuthority(Authority.ROLE_STUDENT.name)
            .mvcMatchers(HttpMethod.GET, "/api/v2/outing").hasAnyAuthority(Authority.ROLE_STUDENT.name, Authority.ROLE_STUDENT_COUNCIL.name)
            .mvcMatchers(HttpMethod.GET, "/api/v2/outing/count").hasAnyAuthority(Authority.ROLE_STUDENT.name, Authority.ROLE_STUDENT_COUNCIL.name)
            .mvcMatchers(HttpMethod.GET, "/api/v2/outing/search").hasAnyAuthority(Authority.ROLE_STUDENT.name, Authority.ROLE_STUDENT_COUNCIL.name)

            // /late
            .mvcMatchers(HttpMethod.GET, "/api/v2/late/rank").hasAnyAuthority(Authority.ROLE_STUDENT.name, Authority.ROLE_STUDENT_COUNCIL.name)

            // /student-council
            .mvcMatchers(HttpMethod.POST, "/api/v2/student-council/outing").hasAnyAuthority(Authority.ROLE_STUDENT_COUNCIL.name)
            .mvcMatchers(HttpMethod.GET, "/api/v2/student-council/account").hasAnyAuthority(Authority.ROLE_STUDENT_COUNCIL.name)
            .mvcMatchers(HttpMethod.PATCH, "/api/v2/student-council/authority").hasAnyAuthority(Authority.ROLE_STUDENT_COUNCIL.name)
            .mvcMatchers(HttpMethod.POST, "/api/v2/student-council/black-list/{accountIdx}").hasAnyAuthority(Authority.ROLE_STUDENT_COUNCIL.name)
            .mvcMatchers(HttpMethod.DELETE, "/api/v2/student-council/black-list/{accountIdx}").hasAnyAuthority(Authority.ROLE_STUDENT_COUNCIL.name)
            .mvcMatchers(HttpMethod.DELETE, "/api/v2/student-council/outing/{accountIdx}").hasAnyAuthority(Authority.ROLE_STUDENT_COUNCIL.name)

            // /health
            .mvcMatchers(HttpMethod.GET, "/").permitAll()
    }

    private fun exceptionHandling(http: HttpSecurity) {
        http.exceptionHandling()
            .authenticationEntryPoint(CustomAuthenticationEntryPoint())
            .accessDeniedHandler(CustomAccessDeniedHandler())
    }

}