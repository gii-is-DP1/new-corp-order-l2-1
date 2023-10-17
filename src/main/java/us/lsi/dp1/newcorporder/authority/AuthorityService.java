/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package us.lsi.dp1.newcorporder.authority;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import us.lsi.dp1.newcorporder.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthorityService {

	private final AuthorityRepository authorityRepository;

	@Autowired
	public AuthorityService(AuthorityRepository authorityRepository) {
		this.authorityRepository = authorityRepository;
	}

	@Transactional(readOnly = true)
	public Iterable<Authority> findAll() {
		return this.authorityRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Authority findByName(String authority) {
		return this.authorityRepository.findByName(authority)
				.orElseThrow(() -> new ResourceNotFoundException("Authority", "Name", authority));
	}

	@Transactional
	public void save(Authority authority) throws DataAccessException {
		authorityRepository.save(authority);
	}
}
