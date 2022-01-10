package ru.semperante.learnback.utils.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import ru.semperante.learnback.exceptions.ReservedExceptions;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class JWTUtils
{
    @Value("${spring.jwt_salt}")
    private String salt;

    private final DataSource dataSource;

    private JWTVerifier jwtVerifier;

    public JWTUtils(DataSource dataSource)
    {
        this.dataSource = dataSource;
    }

    @PostConstruct
    protected void onInit()
    {
        jwtVerifier = JWT.require(Algorithm.HMAC512(salt)).build();
    }

    public Long getUserId(String token)
    {
        return readToken(token).id;
    }

    public String writeJWT(JWTPayload payload)
    {
        return JWT.create().withKeyId(payload.id.toString())
                .withClaim("exp", payload.expires)
                .sign(Algorithm.HMAC512(salt));
    }

    public JWTPayload readToken(String token)
    {

        if (token == null || token.isBlank())
        {
            return null;
        }
        Connection con = null;
        try
        {
            con = dataSource.getConnection();

            try
            {
                PreparedStatement ps = con.prepareStatement("SELECT 1 FROM logout_tokens WHERE token = ?");
                ps.setString(1, token);
                ResultSet rs = ps.executeQuery();
                if (rs.next())
                {
                    rs.close();
                    ps.close();
                    throw ReservedExceptions.INVALID_TOKEN;
                }
                rs.close();
                ps.close();
                con.close();
                DecodedJWT decoded = jwtVerifier.verify(token);
                long expires = decoded.getClaim("exp").asLong();
                if (expires < System.currentTimeMillis())
                {
                    throw ReservedExceptions.JWT_EXPIRED;
                }
                return new JWTPayload(Long.parseLong(decoded.getKeyId()), expires);
            }
            catch (Throwable e)
            {
                con.close();
                if (e instanceof ResponseStatusException)
                {
                    throw (ResponseStatusException) e;
                }
                e.printStackTrace();
                throw ReservedExceptions.INVALID_TOKEN;
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            throw ReservedExceptions.DB_ERROR;
        }
    }

}
