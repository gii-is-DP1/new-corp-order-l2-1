package us.lsi.dp1.newcorporder.authority;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import us.lsi.dp1.newcorporder.model.BaseEntity;

@Getter
@Setter
@Entity
@Table(name = "authorities")
public class Authority extends BaseEntity {

    @Column(length = 20)
    String name;

    public Authority() {
        // default constructor for Jackson
    }

    public Authority(String name) {
        this.name = name;
    }

    @Override
    @JsonIgnore
    public Integer getId() {
        return super.getId();
    }
}
