package by.primech85.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

@WebFilter("/*")
public class AutorizationFilter implements Filter {
    private static final Set<String> PUBLIC_PATTERN = Set.of("/login","/registration");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var uri =((HttpServletRequest)request).getRequestURI();
        if(isPublicPath(uri) || isUserLoggedIn(request)){
            chain.doFilter(request,response);
        }else{
            ((HttpServletResponse)response).sendRedirect("/login");
        }
    }

    private boolean isUserLoggedIn(ServletRequest request) {
        var user = ((HttpServletRequest) request).getSession().getAttribute("user");
        return user!=null;
    }

    private boolean isPublicPath(String uri) {
        return PUBLIC_PATTERN.stream().anyMatch(e -> uri.startsWith(e));
    }
}
