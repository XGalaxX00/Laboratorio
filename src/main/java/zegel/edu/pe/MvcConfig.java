package zegel.edu.pe;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer{
	@Override
	
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/src/main/resources/static/Img-com/**").addResourceLocations("file:src/main/resources/static/Img-com/");
    }
	
	/*public void addResourceHandlers(ResourceHandlerRegistry registry) {
		exposeDirectory("admin/productos", registry);
	}
	private void exposeDirectory(String dirName, ResourceHandlerRegistry registry) {
	    Path uploadDir = Paths.get(dirName);
	    String uploadPath = uploadDir.toFile().getAbsolutePath();
	    registry.addResourceHandler("/" + dirName + "/**").addResourceLocations("file:img/" + uploadPath + "/");
	}*/
}
