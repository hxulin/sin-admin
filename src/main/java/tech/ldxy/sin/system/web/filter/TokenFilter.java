package tech.ldxy.sin.system.web.filter;

import tech.ldxy.sin.system.common.Constant;
import tech.ldxy.sin.system.context.UserContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

/**
 * 功能描述:
 *
 * @author hxulin
 */
public class TokenFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) {
        HttpServletRequest request = (HttpServletRequest) req;
        String token = request.getHeader(Constant.TOKEN_KEY);
        ContextManager.addAttribute(Constant.TOKEN_KEY, token);
        // Token 续约
        UserContext.refreshToken();
        try {
            chain.doFilter(request, resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            ContextManager.removeContext();
        }
    }

}
