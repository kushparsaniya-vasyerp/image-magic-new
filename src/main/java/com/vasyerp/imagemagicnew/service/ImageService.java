package com.vasyerp.imagemagicnew.service;

import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.core.ImageMagickCmd;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class ImageService {

    @Value("${image-magic.imagemagick.path}")
    private String imageMagickPath;

    public File convertAndResizeImage(File inputFile, String outputPath, int height, int width, int percentage, String outputFormat) throws IOException, InterruptedException {
        IMOperation operation = new IMOperation();

        operation.addImage(inputFile.getAbsolutePath());
        if (height > 0 && width > 0) {
            operation.resize(height, width);
        } else {
            throw new IllegalArgumentException("Height and width must be greater than 0");
        }
        operation.quality((double) percentage);
        operation.format(outputFormat);
        operation.addImage(outputPath);

        ImageMagickCmd cmd = new ImageMagickCmd("magick");
        try {
            cmd.run(operation);
        } catch (IM4JavaException e) {
            e.printStackTrace();
            throw new IOException("Error during image conversion", e);
        }

        File outputFile = new File(outputPath);
        if (!outputFile.exists()) {
            throw new IOException("Output file was not created");
        }

        return outputFile;
    }

}
