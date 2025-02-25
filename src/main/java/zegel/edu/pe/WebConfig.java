package zegel.edu.pe;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get("uploads/foto_club");
        String uploadPath = uploadDir.toFile().getAbsolutePath();

        registry.addResourceHandler("/foto_club/**")
                .addResourceLocations("file:" + uploadPath + "/");
    }
}
