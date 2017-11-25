package achievements.repos;

import achievements.enteties.Achievement;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AchievementRepository extends CrudRepository<Achievement, String> {

    List<Achievement> findByGame_IdOrderByDisplayOrderAscCreatedAsc (String gameId);
    List<Achievement> findByGame_Id (String gameId);
    boolean existsByGame_idAndDisplayName(String gameId, String displayName);




}
