package com.app.auth;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.app.entity.Utilisateur;

@WebFilter("/secured/*")
public class SecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Rien à initialiser pour l’instant
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        HttpSession session = req.getSession(false);

        Utilisateur user = null;
        if (session != null) {
            user = (Utilisateur) session.getAttribute("currentUser");
        }

        String uri = req.getRequestURI();

        if (uri.contains("index.xhtml")
                || uri.contains("javax.faces")
                || uri.contains("resources")) {
            chain.doFilter(request, response);
            return;
        }

        if (user == null) {
            res.sendRedirect(req.getContextPath() + "/index.xhtml?expired=true");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Nettoyage si nécessaire
    }
}
