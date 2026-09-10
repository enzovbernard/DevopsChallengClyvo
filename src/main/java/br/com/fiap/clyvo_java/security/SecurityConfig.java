package br.com.fiap.clyvo_java.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filtrar(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests((req) -> req
				.requestMatchers("/veterinarios/**", "/doencas/**", "/vacinas/**").hasRole("ADMIN")
				.anyRequest().authenticated())
			.formLogin((login) -> login
				.loginPage("/login")
				.defaultSuccessUrl("/home", true)
				.failureUrl("/login?falha=true")
				.permitAll())
			.logout((logout) -> logout
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login?logout=true")
				.permitAll())
			.exceptionHandling((exception) -> exception
				.accessDeniedHandler((request, response, accessDeniedException)
					-> response.sendRedirect("/acesso_negado")));

		return http.build();
	}

}