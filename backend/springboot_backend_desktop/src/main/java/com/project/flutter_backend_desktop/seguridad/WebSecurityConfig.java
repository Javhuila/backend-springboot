package com.project.flutter_backend_desktop.seguridad;

import com.project.flutter_backend_desktop.modelo.Enum.ERol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.project.flutter_backend_desktop.seguridad.Jwt.AuthEntryPointJwt;
import com.project.flutter_backend_desktop.seguridad.Jwt.JwtAuthenticationFilter;
import com.project.flutter_backend_desktop.seguridad.User.UsuarioDetailsServiceImpl;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {
    @Autowired
    private UsuarioDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsServiceImpl);

        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception ->exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth->
                        auth.requestMatchers("/api/autorization/**").permitAll()
                                .requestMatchers("/api/init/**").permitAll()

                                .requestMatchers( "/api/product/productos/list",
                                        "/api/product/productos/{id}/catch",
                                        "/api/product/page-productos",
                                        "/api/product/productos/autocomplete",
                                        "/api/product/productos/filtro",
                                        "/api/product/productos/image/{fileName}",
                                        "/api/product/promociones/{id}/bonificaciones")
                                .hasAnyAuthority(ERol.ROLE_ADMIN.name(), ERol.ROLE_CAJERO.name(), ERol.ROLE_ASISTENTE.name())

                                .requestMatchers("/api/product/productos",
                                        "/api/product/products/",
                                        "/api/product/productos/delete/",
                                        "/api/product/editProductos/",
                                        "/api/product/productos/{id}/force",
                                        "/api/product/productos/{id}/estado",
                                        "/api/product/productos/photo",
                                        "/api/product/productos/menos-vendidos",
                                        "/api/product/productos/top-vendidos",
                                        "/api/product/productos/top-vendidos/mes-actual",
                                        "/api/product/promociones/{id}/bonificacions",
                                        "/api/product/promociones/{id}/bonificacion/{idBonificacion}",
                                        "/api/product/promociones/{id}/bonificaciones/{idBonificacion}")
                                .hasAuthority(ERol.ROLE_ADMIN.name())

                                .anyRequest().authenticated());

        http.authenticationProvider(authenticationProvider());

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}