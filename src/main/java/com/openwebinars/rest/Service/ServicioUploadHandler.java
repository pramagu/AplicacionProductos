package com.openwebinars.rest.Service;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.openwebinars.rest.Controller.FicherosController;
import com.openwebinars.rest.upload.StorageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicioUploadHandler {

    private static final Logger logger = LoggerFactory.getLogger(FicherosController.class);

    private final StorageService storageService;

    public ResponseEntity<Resource> serveFileHandler(String filename, HttpServletRequest request) {
        Resource file = storageService.loadAsResource(filename);

        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(file.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(file);
    }

}
