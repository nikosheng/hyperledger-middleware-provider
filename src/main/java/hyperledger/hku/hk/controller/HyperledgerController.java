package hyperledger.hku.hk.controller;

import hyperledger.hku.hk.utils.HttpUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class HyperledgerController {
    private static final String PROTECTED_RESOURCE_URL = "http://hyperledger.hku.hk/api/";

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
    public String application(@RequestParam String item, HttpServletRequest request) throws Exception{
        String ret = null;
        String accessToken = getAccessToken(request.getCookies());
        if (accessToken != null && accessToken.length() > 0) {
            String url = String.format("%s%saccess_token=%s", PROTECTED_RESOURCE_URL, item, accessToken);
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
            if ("access_token".equals(cookie.getName())) {
                String value = cookie.getValue();
                accessToken = value.substring(4, value.indexOf("."));
            }
        }
        return accessToken;
    }
}
