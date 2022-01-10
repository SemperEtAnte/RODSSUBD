package ru.semperante.learnback.utils.jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.server.ResponseStatusException;
import ru.semperante.learnback.entities.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends GenericFilterBean
{
    private final JWTUtils jwtUtils;

    private final AuthorizedUserService authorizedUserService;


    public JwtFilter(JWTUtils jwtUtils, AuthorizedUserService authorizedUserService)
    {
        this.jwtUtils = jwtUtils;
        this.authorizedUserService = authorizedUserService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
    {
        try
        {
            HttpServletRequest req = (HttpServletRequest) servletRequest;
            String auth = req.getHeader("Authorization");

            JWTPayload user_id = jwtUtils.readToken(auth);
            if (user_id != null)
            {
                User user = (User) authorizedUserService.loadUserByUsername(String.valueOf(user_id.id));
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(token);
            }

            filterChain.doFilter(servletRequest, servletResponse);
        }
        catch (ResponseStatusException e)
        {
            String message = e.getReason();
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setStatus(e.getRawStatusCode());
            response.setContentType("application/json");
            servletResponse.getWriter().append("{\"error\": \"").append(message).append("\"}").flush();
        }
        catch (Throwable e)
        {

            e.printStackTrace();
        }
    }
}