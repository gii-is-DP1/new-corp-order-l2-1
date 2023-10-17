package us.lsi.dp1.newcorporder.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import org.apache.commons.lang3.ArrayUtils;
import us.lsi.dp1.newcorporder.authority.Authority;
import us.lsi.dp1.newcorporder.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

	@Column(unique = true)
	String username;

	String password;

	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "authority")
    Authority authority;

	public Boolean hasAuthority(String auth) {
		return authority.getName().equals(auth);
	}

	public Boolean hasAnyAuthority(String... authorities) {
        return ArrayUtils.contains(authorities, this.authority.getName());
	}
}
