package org.yaroslaavl.webappstarter.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        SavedRequest savedRequest = requestCache.getRequest(request, response);
        log.info("SavedRequest is: {}",savedRequest);
        if(savedRequest == null){
            response.sendRedirect("/firstPage");
            return;
        }
        String redirectUrl = savedRequest.getRedirectUrl();
        log.info("RedirectUrl is: {}",redirectUrl);

        if(redirectUrl.contains("/admin/admin-panel")){
            if (authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .anyMatch(role -> role.equals("ADMIN"))){
                response.sendRedirect(redirectUrl);
            } else {
                response.sendRedirect("/firstPage");
            }
        } else {
            response.sendRedirect(redirectUrl);
        }
    }
}
