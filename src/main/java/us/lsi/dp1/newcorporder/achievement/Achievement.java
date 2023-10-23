package us.lsi.dp1.newcorporder.achievement;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import us.lsi.dp1.newcorporder.model.BaseEntity;
import us.lsi.dp1.newcorporder.model.NamedEntity;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "achievements")
@Entity
public class Achievement extends NamedEntity {
    @Size(max=256)
    String description;
    String imageUrl;
    @Enumerated(EnumType.STRING)
    Stat stat;
    Integer threshold;
}
