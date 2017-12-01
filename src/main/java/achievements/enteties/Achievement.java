package achievements.enteties;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Objects;
import org.hibernate.validator.constraints.URL;

@Entity
public class Achievement {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @JsonIgnore//id is used just with serialization
    private String id;

    @NotNull(message = "ach.error.displayname.notnull")
    @Size(min = 1, max = 100, message = "ach.error.displayname.size")
    private String displayName;

    @NotNull(message = "ach.error.descritpion.notnull")
    @Size(min=1, max = 500, message = "ahc.error.descritpion.size")
    private String description;

    @URL(message = "ach.error.icon.invalidurl")
    private String icon;

    @Range(min =0, max = Integer.MAX_VALUE, message = "ach.error.displayOrder.range")
    private int displayOrder = 1 ;

    @JsonIgnore
    @Column(nullable = false)
    private LocalDateTime created;

    @JsonIgnore
    @Column(nullable = false)
    private LocalDateTime updated;

    @ManyToOne(optional = false)
    @JsonIgnore
    private Game game;

@JsonProperty("id")
    public String getId() {
        return this.id;
    }
@JsonIgnore
    public void setId(String id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return this.updated;
    }

    public void setUpdated(LocalDateTime updated) { this.updated = updated; }

    public Game getGame() {
        return this.game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    Achievement(){}

    @PrePersist
    public void onPrePersist() {
        setCreated(LocalDateTime.now());
        setUpdated(LocalDateTime.now());
    }

    @PreUpdate
    public void onPreUpdate() {
        setUpdated(LocalDateTime.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Achievement)) return false;
        Achievement that = (Achievement) o;
        return Objects.equals(this.getId(),that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId());
    }


}
