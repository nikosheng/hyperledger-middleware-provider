package hyperledger.hku.hk.controller;

import hyperledger.hku.hk.utils.HttpUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Controller
public class HyperledgerController {
    private static final String PROTECTED_RESOURCE_URL = "http://hyperledger.hku.hk/api/";
    public static final String ACCESS_TOKEN = "access_token";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @RequestMapping("/")
    public String home(HttpServletRequest request, HttpServletResponse response) {
        return "index";
    }

    @RequestMapping("/callback")
    public String callback(HttpServletRequest request, HttpServletResponse response) {
        String code = request.getParameter("code");
        return "redirect:http://hyperledger.hku.hk/auth/github/callback?code=" + code;
    }

    @RequestMapping(value = "/api/{item}", method = RequestMethod.GET)
    @ResponseBody
    public String application(@PathVariable String item, HttpServletRequest request) throws Exception {
        String ret = null;
        String accessToken = redisTemplate.opsForValue().get(ACCESS_TOKEN);
        if (StringUtils.isEmpty(accessToken)) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null && cookies.length > 0) {
                accessToken = getAccessToken(cookies);
            }
        }

        if (StringUtils.isNotEmpty(accessToken)) {
            redisTemplate.opsForValue().set(ACCESS_TOKEN, accessToken, 7, TimeUnit.DAYS);
            String url = String.format("%s%s?access_token=%s", PROTECTED_RESOURCE_URL, item, accessToken);
            HttpResponse response =
                    HttpUtils.get(url);
            HttpEntity entity = response.getEntity();
            ret = EntityUtils.toString(entity);
        }

        return ret;
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
