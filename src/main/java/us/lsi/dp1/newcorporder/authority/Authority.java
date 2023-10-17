package us.lsi.dp1.newcorporder.authority;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import us.lsi.dp1.newcorporder.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

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
}
