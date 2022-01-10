package ru.semperante.learnback.utils.jwt;

public class JWTPayload
{
    public final Long id;
    public final long expires;


    public JWTPayload(Long id, long expires)
    {

        if(id == null)
            throw new IllegalArgumentException("Id cannot be null");
        this.id = id;
        this.expires = expires;
    }

    public JWTPayload(Long id)
    {
        this(id, System.currentTimeMillis() + 86400_000 * 7L);
    }


}
