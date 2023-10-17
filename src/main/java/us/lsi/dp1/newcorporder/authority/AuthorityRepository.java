package us.lsi.dp1.newcorporder.authority;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AuthorityRepository extends CrudRepository<Authority, String> {

	@Query("SELECT DISTINCT authority FROM Authority authority WHERE authority.name LIKE :name%")
	Optional<Authority> findByName(String name);

}
