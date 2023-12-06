package us.lsi.dp1.newcorporder.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import us.lsi.dp1.newcorporder.user.User;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class ApplicationUserDetails implements UserDetails {

    public static ApplicationUserDetails build(User user) {
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getAuthority().getName()));
        return new ApplicationUserDetails(user, authorities);
    }

	@JsonIgnore
    @Getter private final User user;

    private final Collection<? extends GrantedAuthority> authorities;

    public ApplicationUserDetails(User user, Collection<? extends GrantedAuthority> authorities) {
        this.user = user;
		this.authorities = authorities;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

    public Integer getId() {
        return this.user.getId();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public int hashCode() {
        return Objects.hash(this.user.getId());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
        ApplicationUserDetails other = (ApplicationUserDetails) obj;
        return Objects.equals(this.user.getId(), other.getUser().getId());
	}

}
