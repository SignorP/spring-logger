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
