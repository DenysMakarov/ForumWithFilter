package telran.forumservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import telran.accountservise.dao.UserMongoRepository;
import telran.accountservise.security.context.SecurityContext;
import telran.accountservise.security.context.UserProfile;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@Service
@Order(20)
public class AddPostFilter implements Filter {
    UserMongoRepository repository;
    SecurityContext securityContext;

    @Autowired
    public AddPostFilter(UserMongoRepository repository, SecurityContext securityContext) {
        this.repository = repository;
        this.securityContext = securityContext;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (checkEndPoints(request.getServletPath(), request.getMethod())){
            Principal principal = request.getUserPrincipal();
            UserProfile user = securityContext.getUser(principal.getName());
            String[] arrStr = request.getServletPath().split("/");
            String str = arrStr[arrStr.length-1];

            if (!user.getLogin().equals(str)){
                response.sendError(403);
                return;
            }
        }
        filterChain.doFilter(request, response);

    }

    private boolean checkEndPoints(String path, String method) {
        return ("POST".equalsIgnoreCase(method) && path.matches("[/]forum[/]post[/]\\w+[/]?"));
    }
}