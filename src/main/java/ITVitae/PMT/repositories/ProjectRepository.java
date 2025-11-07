package ITVitae.PMT.repositories;

import ITVitae.PMT.models.Project;
import ITVitae.PMT.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
