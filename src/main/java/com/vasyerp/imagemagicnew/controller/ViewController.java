package com.vasyerp.imagemagicnew.controller;

import com.vasyerp.imagemagicnew.service.ImageService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Controller
public class ViewController {

    private final ImageService imageService;

    public ViewController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/")
    public String index() {
        return "home";
    }

    @PostMapping("/upload-image")
    public ResponseEntity<Resource> handleImageUpload(@RequestParam("imageFile") MultipartFile file,
                                                      @RequestParam("width") int width,
                                                      @RequestParam("height") int height,
                                                      @RequestParam("quality") int quality,
                                                      @RequestParam("format") String outputFormat
    ) throws IOException, InterruptedException {
//        String tempDir = System.getProperty("user.dir");
//        tempDir = tempDir + FileSystems.getDefault().getSeparator() + "images";
        String tempDir = System.getProperty("java.io.tmpdir");


        File tempInputFile = new File(tempDir, "upload_" + System.nanoTime() + "." + getFileExtension(file));
        File tempOutputFile = new File(tempDir, "processed_" + System.nanoTime() + "." + outputFormat);
        file.transferTo(tempInputFile);

        String outputFileName = tempOutputFile.getName();
        File processedFile = imageService.convertAndResizeImage(tempInputFile, tempOutputFile.getAbsolutePath(), height, width, quality,outputFormat);

        if (!processedFile.exists()) {
            throw new IOException("Processed file does not exist");
        }

        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(processedFile.toPath()));

        tempInputFile.delete();
        tempOutputFile.delete();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"%s\"".formatted(outputFileName))
                .header(HttpHeaders.CONTENT_TYPE, "image/" + outputFormat)
                .header(HttpHeaders.CONTENT_LENGTH, String.valueOf(resource.contentLength()))
                .body(resource);
    }

    private String getFileExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename != null && originalFilename.contains(".")) {
            return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        } else {
            throw new IllegalArgumentException("Invalid file: no extension found.");
        }
    }
}
