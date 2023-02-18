package kg.dev_abe.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@SpringBootApplication
public class    ECommerceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ECommerceApplication.class, args);
        System.out.println("Welcome colleagues, project name is E-COMMERСE!");
    }

    @GetMapping("/")
    public String greetingPage() {
        return "welcome";
    }
}
