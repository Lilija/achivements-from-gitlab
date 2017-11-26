package achievements.repos;

import achievements.enteties.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends CrudRepository<Game, String> {
    Optional<Game> findById(String id);
}
