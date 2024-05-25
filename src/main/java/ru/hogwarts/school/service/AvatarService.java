package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class AvatarService {
    private final static Logger LOGGER = LoggerFactory.getLogger(AvatarService.class);
    private final AvatarRepository avatarRepository;
    private final StudentRepository studentRepository;
    private final Path avatarsDir;

    public AvatarService(AvatarRepository avatarRepository, StudentRepository studentRepository, @Value("${avatars.dir}") Path avatarsDir) {
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentRepository;
        this.avatarsDir = avatarsDir;
    }

    public Avatar save(Long studentId, MultipartFile file) throws IOException {
        LOGGER.info("Avatar.save was invoked!");

        Files.createDirectories(avatarsDir);

        var index = file.getOriginalFilename().lastIndexOf('.');
        var extension = file.getOriginalFilename().substring(index);
        Path filePath = avatarsDir.resolve(studentId + extension);
        try (var in = file.getInputStream()) {
            Files.copy(in, filePath, StandardCopyOption.REPLACE_EXISTING);
        }

        Avatar avatar = avatarRepository.findAllByStudentId(studentId).orElse(new Avatar());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(file.getBytes());
        avatar.setStudent(studentRepository.getReferenceById(studentId));
        avatar.setFilePath(filePath.toString());
        return avatarRepository.save(avatar);
    }

    public Avatar getById(Long id) {
        LOGGER.info("Avatar.getById was invoked!");
        return avatarRepository.findById(id).orElse(null);
    }

    public List<Avatar> getPage(int pageNumber, int pageSize) {
        LOGGER.info("Avatar.getPage was invoked!");
        return avatarRepository.findAll(PageRequest.of(pageNumber, pageSize)).toList();
    }
}
















