package ITVitae.PMT.services;

import ITVitae.PMT.DTOs.Tag.TagCreateDTO;
import ITVitae.PMT.DTOs.Tag.TagDTO;
import ITVitae.PMT.DTOs.Tag.TagEditDTO;
import ITVitae.PMT.miscellaneous.CheckCredentials;
import ITVitae.PMT.miscellaneous.Constants;
import ITVitae.PMT.miscellaneous.ErrorHandler;
import ITVitae.PMT.models.Project;
import ITVitae.PMT.models.Tag;
import ITVitae.PMT.models.Task;
import ITVitae.PMT.repositories.ProjectRepository;
import ITVitae.PMT.repositories.TagRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    final private TagRepository tagRepository;
    final private ProjectRepository projectRepository;

    @Autowired
    public TagService(TagRepository tagRepository, ProjectRepository projectRepository) {
        this.tagRepository = tagRepository;
        this.projectRepository = projectRepository;
    }

    public TagDTO createTag(TagCreateDTO createDTO, Long userId) {
        CheckCredentials.checkWithProject(userId, createDTO.projectId(), true);
        Tag tag = createDTO.toEntity();
        Project project = projectRepository.findById(createDTO.projectId())
                .orElseGet(() -> ErrorHandler.throwError("Project Id", Constants.Errors.NOT_FOUND));

        tag.setProject(project);

        Tag savedTag = tagRepository.save(tag);
        return TagDTO.fromEntity(savedTag);
    }

    public List<TagDTO> findAll() {
        return tagRepository.findAll()
                .stream()
                .map(TagDTO::fromEntity)
                .toList();
    }

    public TagDTO findById(Long id) {
        return tagRepository.findById(id)
                .map(TagDTO::fromEntity)
                .orElse(null);
    }

    @Transactional
    public TagDTO editTag(Long id, TagEditDTO editDTO, Long userId) {
        Tag tag = tagRepository.findById(id)
                .orElseGet(() -> ErrorHandler.throwError("Tag Id", Constants.Errors.NOT_FOUND));
        CheckCredentials.checkWithProject(userId, tag.getProject().getId(), true);

        if(!editDTO.name().equals(Constants.noEdit))
            tag.setName(editDTO.name());
        if(!editDTO.colour().isEmpty())
            tag.setColour(editDTO.colour().get());
        tagRepository.save(tag);
        return TagDTO.fromEntity(tag);
    }

    public ResponseEntity<String> deleteTag(Long id, Long userId)
    {
        Tag tag = tagRepository.findById(id)
                .orElseGet(() -> ErrorHandler.throwError("Tag Id", Constants.Errors.NOT_FOUND));
        CheckCredentials.checkWithProject(userId, tag.getProject().getId(), true);

        for (Task task : tag.getTasks()) {
            task.getTags().remove(tag);
        }
        tag.getTasks().clear();

        tagRepository.delete(tag);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
