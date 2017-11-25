package achievements.enteties;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Game {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @NotNull
    private String displayName;
    @OneToMany(mappedBy = "game")
    List<Achievement> achievements;

    public String getId() {
        return this.id;
    }

    public String setId(String id) {
        return this.id = id;
    }


    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    Game(){};
    public Game (String name){
        this.displayName = name;
    }

}
