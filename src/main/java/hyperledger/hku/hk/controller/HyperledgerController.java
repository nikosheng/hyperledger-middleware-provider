package hyperledger.hku.hk.controller;

import hyperledger.hku.hk.annotate.AccessToken;
import hyperledger.hku.hk.model.Apply;
import hyperledger.hku.hk.model.LoanProcess;
import hyperledger.hku.hk.model.Process;
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
    @AccessToken
    public String application(@PathVariable String item, HttpServletRequest request) throws Exception {
        String ret = null;
        String accessToken = redisTemplate.opsForValue().get(ACCESS_TOKEN);

        if(StringUtils.isNotEmpty(accessToken)) {
            String url = String.format("%s%s?access_token=%s", PROTECTED_RESOURCE_URL, item, accessToken);
            HttpResponse response =
                    HttpUtils.get(url);
            HttpEntity entity = response.getEntity();
            ret = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
        }

        return ret;
    }

    @RequestMapping(value = "/api/apply")
    @ResponseBody
    @AccessToken
    public String apply(@RequestBody Apply apply, HttpServletRequest request) throws Exception {
        String ret = null;
        String accessToken = redisTemplate.opsForValue().get(ACCESS_TOKEN);

        if(StringUtils.isNotEmpty(accessToken)) {
            String url = String.format("%s%s?access_token=%s", PROTECTED_RESOURCE_URL, "apply", accessToken);

            HttpResponse response = HttpUtils.post(url, apply.toMap(), "utf-8");
            HttpEntity entity = response.getEntity();
            ret = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
        }

        return ret;
    }

    @RequestMapping(value = "/api/process")
    @ResponseBody
    @AccessToken
    public String process(@RequestBody Process process, HttpServletRequest request) throws Exception {
        String ret = null;
        String accessToken = redisTemplate.opsForValue().get(ACCESS_TOKEN);

        if(StringUtils.isNotEmpty(accessToken)) {
            String url = String.format("%s%s?access_token=%s", PROTECTED_RESOURCE_URL, "process", accessToken);

            HttpResponse response = HttpUtils.post(url, process.toMap(), "utf-8");
            HttpEntity entity = response.getEntity();
            ret = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
        }

        return ret;
    }

    @RequestMapping(value = "/api/loanProcess")
    @ResponseBody
    @AccessToken
    public String loanProcess(@RequestBody LoanProcess loanProcess, HttpServletRequest request) throws Exception {
        String ret = null;
        String accessToken = redisTemplate.opsForValue().get(ACCESS_TOKEN);

        if(StringUtils.isNotEmpty(accessToken)) {
            String url = String.format("%s%s?access_token=%s", PROTECTED_RESOURCE_URL, "loanProcess", accessToken);

            HttpResponse response = HttpUtils.post(url, loanProcess.toMap(), "utf-8");
            HttpEntity entity = response.getEntity();
            ret = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
        }

        return ret;
    }
}
