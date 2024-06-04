package ru.hogwarts.school.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.service.AvatarService;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/avatar")
public class AvatarController {
    private final AvatarService avatarService;

    public AvatarController(AvatarService avatarService) {
        this.avatarService = avatarService;
    }

    @PostMapping
    public ResponseEntity<byte[]> save(@RequestParam Long studentId, @RequestBody MultipartFile file) throws IOException {
        return ResponseEntity.ofNullable(avatarService.save(studentId, file).getData());
    }

    @GetMapping("/disk/{id}")
    public void loadFromDisk(@PathVariable Long id, HttpServletResponse response) throws IOException{
        var avatar = avatarService.getById(id);
        if(avatar!=null){
            response.setContentLength((int) avatar.getFileSize());
            response.setContentType(avatar.getMediaType());
            try(var fis = new FileInputStream(avatar.getFilePath())){
                fis.transferTo(response.getOutputStream());
            }
        }
    }

    @GetMapping("/db/{id}")
    public ResponseEntity<byte[]> loadFromDisk(@PathVariable Long id){
        var avatar = avatarService.getById(id);
        if(avatar==null){
            return ResponseEntity.ofNullable(null);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentLength(avatar.getFileSize());
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        return ResponseEntity.status(200).headers(headers).body(avatar.getData());
    }

    @GetMapping
    public List<Avatar> getByPage(@RequestParam("page") int page,
                                  @RequestParam("size") int size){
        return avatarService.getPage(page, size);
    }
}
