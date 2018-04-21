package hyperledger.hku.hk.annotate;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

@Component
@Aspect
public class AccessTokenAspect {
    public static final String ACCESS_TOKEN = "access_token";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Pointcut("@annotation(hyperledger.hku.hk.annotate.AccessToken)")
    public void remoteRequest() {
    }

    @Before("remoteRequest()")
    public void before(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        HttpServletRequest request = (HttpServletRequest) args[1];

        String accessToken = redisTemplate.opsForValue().get(ACCESS_TOKEN);
        if (StringUtils.isEmpty(accessToken)) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null && cookies.length > 0) {
                accessToken = getAccessToken(cookies);
            }
        }

        if (StringUtils.isNotEmpty(accessToken)) {
            redisTemplate.opsForValue().set(ACCESS_TOKEN, accessToken, 7, TimeUnit.DAYS);
        }
    }

    private String getAccessToken(Cookie[] cookies) {
        String accessToken = null;
        for (Cookie cookie : cookies) {
            if (ACCESS_TOKEN.equals(cookie.getName())) {
                String value = cookie.getValue();
                accessToken = value.substring(4, value.indexOf("."));
            }
        }
        return accessToken;
    }
}
