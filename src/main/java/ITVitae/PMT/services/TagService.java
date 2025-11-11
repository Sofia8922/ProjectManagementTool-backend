package ITVitae.PMT.services;

import ITVitae.PMT.DTOs.Tag.TagCreateDTO;
import ITVitae.PMT.DTOs.Tag.TagDTO;
import ITVitae.PMT.DTOs.Tag.TagEditDTO;
import ITVitae.PMT.DTOs.Task.TaskDTO;
import ITVitae.PMT.DTOs.Task.TaskEditDTO;
import ITVitae.PMT.miscellaneous.Constants;
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

    public TagDTO createTag(TagCreateDTO createDTO) {
        Tag tag = createDTO.toEntity();
        Project project = projectRepository.findById(createDTO.projectId())
                .orElseThrow(() -> new RuntimeException("Project id not found"));

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
    public TagDTO editTag(Long id, TagEditDTO editDTO) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tag id not found"));

        if(!editDTO.name().equals(Constants.noEdit))
            tag.setName(editDTO.name());
        if(!editDTO.colour().isEmpty())
            tag.setColour(editDTO.colour().get());
        tagRepository.save(tag);
        return TagDTO.fromEntity(tag);
    }

    public ResponseEntity<String> deleteTag(Long id)
    {
        if(tagRepository.findById(id).isEmpty())
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
        tagRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body("");
    }
}
