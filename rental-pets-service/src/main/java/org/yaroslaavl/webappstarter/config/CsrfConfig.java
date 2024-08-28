package org.yaroslaavl.webappstarter.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.csrf.CsrfAuthenticationStrategy;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class CsrfConfig extends AbstractHttpConfigurer<CsrfConfig, HttpSecurity> {

    @Override
    public void init(HttpSecurity builder) throws Exception {
        super.init(builder);
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        HttpSessionCsrfTokenRepository csrfTokenRepository = new HttpSessionCsrfTokenRepository();
        builder.csrf()
                .csrfTokenRepository(csrfTokenRepository)
                .sessionAuthenticationStrategy(new CsrfAuthenticationStrategy(csrfTokenRepository))
                .ignoringRequestMatchers(new AntPathRequestMatcher("/api/**"));
    }
}
