package achievements.repos;

import achievements.enteties.Achievement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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
    Optional<Achievement> findByIdAndGame_Id (String Id, String gameId);
    boolean existsByIdAndGame_id(String id, String gameId);

    @Modifying
    @Query(value = "delete from Achievement where game.id = :gameId" )
    void deleteAllByGameId (@Param("gameId")String gameId);

    @Query("SELECT 1 FROM Achievement a"+
            " where displayName = :displayName"+
            " and game.id=:gameId"+
            " and (:achievementId=null or id != :achievementId)")
    Optional<Integer> achievementForGameAlreadyExists (@Param("achievementId") String achievementId,
                                      @Param("gameId")String gameId,
                                      @Param("displayName")String displayName);




}
