package {{pkg}}.{{project-name}}.middleware;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RequestLoggingMiddleware implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // --- Request öncesi (Middleware Before) ---
        long startTime = System.currentTimeMillis();
        System.out.println("[LOG] " + httpRequest.getRequestURI());

        // Örnek: Header Kontrolü / Auth Guard
        String authHeader = httpRequest.getHeader("Authorization");
        /*
        if (authHeader == null) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.getWriter().write("Unauthorized");
            return; // Zinciri devam ettirme, geri dön
        }
         */

        // İsteyi sonraki filter'a veya Controller'a ilet
        chain.doFilter(request, response);

        // --- Response sonrası (Middleware After) ---
        long duration = System.currentTimeMillis() - startTime;
        System.out.println("[LOG] " + httpRequest.getRequestURI()  + " Completed in " + duration + "ms with Status: " + httpResponse.getStatus());
    }
}
