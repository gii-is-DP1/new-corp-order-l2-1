package us.lsi.dp1.newcorporder.achievement;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import us.lsi.dp1.newcorporder.model.NamedEntity;

@Getter
@Setter
@Entity
@Table(name = "achievements")
@SuperBuilder
@EqualsAndHashCode(callSuper = true, of = {})
public class Achievement extends NamedEntity {

    @Size(max=256)
    private String description;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    private Stat stat;

    private Integer threshold;

    public Achievement() {
    }
}
