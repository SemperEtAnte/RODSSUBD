package ru.semperante.learnback.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.semperante.learnback.utils.jwt.JwtFilter;

@Configuration
@EnableWebSecurity
@SecurityScheme(name = "jwtToken", paramName = "Authorization", scheme = "bearer", type= SecuritySchemeType.APIKEY, in= SecuritySchemeIn.HEADER)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter)
    {
        this.jwtFilter = jwtFilter;
    }

    /**
     * Тут происходит много чего... мы разрешаем незалогиненным всё, кроме того, что не разрешаем...
     * @param security объект httpSecurity
     * @throws Exception любые ошибки
     */
    @Override
    protected void configure(HttpSecurity security) throws Exception
    {
        security.csrf().disable().httpBasic().disable().authorizeRequests().antMatchers(
                        "/",
                        "/v1/user/register",
                        "/v1/user/login",
                        "/swagger-ui/**",
                        "/api-docs/**",
                        "/api-docs-op/**"
                ).permitAll()
                .antMatchers(HttpMethod.GET,
                        "/v1/news/",
                        "/v1/news/{id}").permitAll()
                .anyRequest().authenticated().and().addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)).accessDeniedHandler(
                        (req, res, e) ->
                        {
                            res.setContentType("application/json");
                            res.setStatus(401);
                            res.getWriter().append("{\"message\":\"").append(e.getMessage()).append("\"}").flush();
                        }
                );

    }


}