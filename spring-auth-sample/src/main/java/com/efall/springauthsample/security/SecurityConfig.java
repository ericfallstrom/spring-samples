package com.efall.springauthsample.security;

import com.efall.springauthsample.security.jwt.CustomJwtAuthenticationConverter;
import com.efall.springauthsample.security.oauth.Oauth2ResponseHandler;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.List;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final SecurityProperties securityProperties;

    @Bean
    public SecurityFilterChain filterChain(
            final HttpSecurity http,
            final Oauth2ResponseHandler oauth2ResponseHandler,
            final OAuth2AuthorizedClientService oAuth2AuthorizedClientService) throws Exception
    {
        // Stateless session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        // Disable csrf
        http.csrf().disable();

        // Disable basic authentication
        http.httpBasic().disable();

        // Disable form login
        http.formLogin().disable();

        // Enable cors
        http.cors().configurationSource(corsConfigurationSource());

        // Enable jwt authentication
        http.oauth2ResourceServer().jwt().jwtAuthenticationConverter(new CustomJwtAuthenticationConverter());;

        // Enable oauth2 authentication
        http.oauth2Login(o -> {
            o.authorizedClientService(oAuth2AuthorizedClientService);
            o.successHandler(oauth2ResponseHandler::oauthSuccessResponse);
            o.failureHandler(oauth2ResponseHandler::oauthFailureResponse);
        });

        // Return 401 (unauthorized) instead of 403 (redirect to login) when authorization is missing or invalid
        http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
            response.addHeader(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"Restricted Content\"");
            response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
        });

        // Set access routes
        http.authorizeHttpRequests()
                .requestMatchers("/").permitAll()
                .requestMatchers("/oauth2/**").permitAll()
                .requestMatchers("/login**").permitAll()
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/public/**").permitAll()
                .anyRequest().authenticated();

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(final UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return new ProviderManager(authProvider);
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder
                .withSecretKey(new SecretKeySpec(securityProperties.getJwt().getSecret().getBytes(), "HmacSHA256"))
                .build();
    }

    @Bean
    public JwtEncoder jwtEncoder() {
        SecretKey key = new SecretKeySpec(securityProperties.getJwt().getSecret().getBytes(), "HmacSHA256");
        return new NimbusJwtEncoder(new ImmutableSecret<>(key));
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(securityProperties.getCors().getAllowedOrigins().split(",", -1)));
        configuration.setAllowedHeaders(List.of(securityProperties.getCors().getAllowedHeaders().split(",", -1)));
        configuration.setAllowedMethods(List.of(securityProperties.getCors().getAllowedMethods().split(",", -1)));
        configuration.setExposedHeaders(List.of(securityProperties.getCors().getExposedHeaders().split(",", -1)));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);

        return source;
    }
}
