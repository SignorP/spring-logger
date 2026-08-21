# Spring boot logger

### Files

```java
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

```

```java
package {{pkg}}.{{project-name}}.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public Map<String, Object> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "Application is running successfully!");
        response.put("timestamp", System.currentTimeMillis());
        System.out.println("Main Home");
        return response;
    }

    @GetMapping("health")
    public String healthCheck() {
        return "OK";
    }
}
```

## Install

```bash
└─> xnex install spring-logger@link
📦 Nex Installer
info Selected recipes: spring-logger@link

📦 Processing spring-logger...
  ✔ Using local cache: C:\Users\...\AppData\Local\xnex\Cache\recipes\spring-logger\link.tgz
√ Select project variant » test-controller
📦 Selected variant: test-controller
const pkg = com.example
const project-name = deneme

📦 Recipe file structure:

└─ 📁 src
   └─ 📁 main
      └─ 📁 java
         └─ 📁 com
            └─ 📁 example
               └─ 📁 deneme
                  ├─ 📁 middleware
                  │  └─ 📄 RequestLoggingMiddleware.java → src/main/java/com/example/deneme/middleware/RequestLoggingMiddleware.java
                  └─ 📁 controller
                     └─ 📄 HomeController.java → src/main/java/com/example/deneme/controller/HomeController.java

Do you want to apply these changes to your project? (y/n): y
✔ Applying changes...

✔ Successfully applyed changes

  ✨ Successfully installed spring-logger

✨ All operations completed successfully.
```
