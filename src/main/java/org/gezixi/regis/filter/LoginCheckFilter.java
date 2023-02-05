package org.gezixi.regis.filter;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.gezixi.regis.common.BaseContext;
import org.gezixi.regis.common.R;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

/**
 * 检查用户是否已经完成登录
 */
@Slf4j
@WebFilter(filterName = "loginCheckFilter", urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        // 获取本次访问路径的URL
        String requestUrl = request.getRequestURI();
        // 请求白名单
        String [] urls = new String[] {
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**"
        };
        boolean check = checkUrl(urls, requestUrl);
        if (check) {
            filterChain.doFilter(request, response);
            return;
        }
        // 判定是否登录
        if (request.getSession().getAttribute("employee") != null) {
            log.info("用户已登录：id为 {}", request.getSession().getAttribute("employee"));
            long id = (long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(id);
            filterChain.doFilter(request, response);
            return;
        }
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));  // 返回未登录的错误信息
        log.info("用户未登录");
    }

    /**
     * 路径匹配，检查url是否放行
     *
     * @param urls 路径
     * @param requestUrl 请求url
     * @return 布尔值
     */
    private boolean checkUrl(String[] urls, String requestUrl) {
        for (String url : urls) {
            if (PATH_MATCHER.match(url, requestUrl)) {
                return true;
            }
        }
        return false;
    }
}
