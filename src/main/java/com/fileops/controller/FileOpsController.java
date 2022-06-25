package com.fileops.controller;

import com.fileops.service.FileOpsService;
import com.fileops.utils.FileResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


@RestController
@RequestMapping("/api")
public class FileOpsController {

    @Autowired
    FileOpsService fileOpsService;

    @PostMapping("/uploadfile")
    public  ResponseEntity uploadSingleFile (@RequestParam("file") MultipartFile file) {
        String upfile = fileOpsService.uploadFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/download/")
                .path(upfile)
                .toUriString();

        return ResponseEntity.status(HttpStatus.OK).body(new FileResponse(upfile,fileDownloadUri,"File uploaded with success!"));
    }

    @GetMapping("/download/{filename:.+}")
    public ResponseEntity downloadFile(@PathVariable String filename) {

        Resource resource = fileOpsService.downloadFile(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @GetMapping("/test")
    public String test() {

       // Resource resource = fileOpsService.loadFile(filename);

        return "<html>hi</html>";


    }


}