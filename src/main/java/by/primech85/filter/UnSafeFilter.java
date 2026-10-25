package by.primech85.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.http.HttpRequest;

@WebFilter("/admin")
public class UnSafeFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        var user = ((HttpServletRequest) request).getSession().getAttribute("user");
        if(user != null){
            chain.doFilter(request,response);
        }else{
            ((HttpServletResponse) response).sendRedirect("/registration");
        }
    }
}
