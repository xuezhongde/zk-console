package org.zdxue.zk.console.servlet.filter;

import org.zdxue.zk.console.servlet.http.MultiReadableHttpServletRequest;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author xuezhongde
 */
@WebFilter(urlPatterns = "/*")
public class Filter0_MultiReadableRequestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request = new MultiReadableHttpServletRequest((HttpServletRequest) request);
        chain.doFilter(request, response);
    }

}
