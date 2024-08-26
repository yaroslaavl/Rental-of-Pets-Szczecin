package org.yaroslaavl.webappstarter.config;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.yaroslaavl.webappstarter.database.entity.Role;
import org.yaroslaavl.webappstarter.dto.UserCreateEditDto;
import org.yaroslaavl.webappstarter.service.UserService;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Set;

import static org.yaroslaavl.webappstarter.database.entity.Role.ADMIN;
import static org.yaroslaavl.webappstarter.database.entity.Role.USER;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;

    @Resource
    UserDetailsService userDetailsService;

    @SneakyThrows
    @Override
    protected void configure(HttpSecurity http) {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests(urlConfig -> urlConfig
                                .antMatchers("/login", "/users/registration","/v3/api-docs/**", "/swagger-ui/**","/firstPage","/pets","/company-info","/blog","api/pets","api/users/**").permitAll()
                                .antMatchers("/user/settings/**","/pet/booking/**","/pet/bookings/**","/user/notifications","api/user/**").authenticated()
                                .antMatchers("/admin/**").hasAuthority(ADMIN.name())
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")
                        .deleteCookies("JSESSIONID"))
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .defaultSuccessUrl("/firstPage"))
                .exceptionHandling()
                .accessDeniedHandler(((request, response, accessDeniedException) -> {
                    accessDeniedException.printStackTrace();
                    response.sendRedirect("http://localhost:8080/forbidden-error");
                }))
                .and()
                .rememberMe()
                .userDetailsService(userDetailsService)
                .key("remember-user-info")
                .tokenValiditySeconds(86400)
                .and()
                .oauth2Login(oauth2Login -> oauth2Login
                        .loginPage("/login")
                        .defaultSuccessUrl("/firstPage")
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                                .oidcUserService(oidcUserService())));
    }

    private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService(){
        return userRequest -> {
            String email = userRequest.getIdToken().getClaim("email");

            if (userService.findByUsername(email).isEmpty()) {
                userService.create(UserCreateEditDto.createNewUser(email, null, "User", "User", null, null, null, null, null,false,null));
            }
            UserDetails userDetails = userService.loadUserByUsername(email);
            DefaultOidcUser defaultOidcUser = new DefaultOidcUser(userDetails.getAuthorities(), userRequest.getIdToken());
            Set<Method> methods = Set.of(UserDetails.class.getMethods());
            return (OidcUser) Proxy.newProxyInstance(
                    SecurityConfig.class.getClassLoader(),
                    new Class[]{UserDetails.class, OidcUser.class},
                    (proxy, method, args) -> methods.contains(method)
                            ? method.invoke(userDetails,args)
                            : method.invoke(defaultOidcUser,args));
        };
    }


}
