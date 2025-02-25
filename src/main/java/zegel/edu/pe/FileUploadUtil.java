package zegel.edu.pe;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.Path;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {
	public static void saveFile(String uploadDir, String fileName,
            MultipartFile multipartFile) throws IOException {
		Path uploadPath = Paths.get(uploadDir);

		if (!Files.exists(uploadPath)) {
		Files.createDirectories(uploadPath);
		}
		
		try (InputStream inputStream = multipartFile.getInputStream()){
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ice) {
			throw new IOException("Could not save image file: " + fileName, ice);	
		}
	}
}
