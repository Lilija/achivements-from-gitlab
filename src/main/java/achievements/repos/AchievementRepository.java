package achievements.repos;

import achievements.enteties.Achievement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AchievementRepository extends JpaRepository<Achievement, String> {

    List<Achievement> findByGame_IdOrderByDisplayOrderAscCreatedAsc (String gameId);
    List<Achievement> findByGame_Id (String gameId);
    Optional<Achievement> findById (String Id);
    boolean existsByGame_idAndDisplayName(String gameId, String displayName);

    @Query("SELECT count(a) FROM Achievement a"+
            " where displayName = :displayName"+
            " and game.id=:gameId"+
            " and (:achievementId=null or id != :achievementId)")
    int achievementForGameAlreadyExists (@Param("achievementId") String achievementId,
                                      @Param("gameId")String gameId,
                                      @Param("displayName")String displayName);




}
