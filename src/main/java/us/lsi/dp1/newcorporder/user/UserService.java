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
package us.lsi.dp1.newcorporder.user;

import com.google.common.base.Preconditions;
import jakarta.validation.Valid;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.lsi.dp1.newcorporder.auth.ApplicationUserDetails;
import us.lsi.dp1.newcorporder.authority.AuthorityService;
import us.lsi.dp1.newcorporder.exceptions.ResourceNotFoundException;
import us.lsi.dp1.newcorporder.user.payload.request.EditProfileRequest;
import us.lsi.dp1.newcorporder.user.payload.response.UserView;

import java.time.Instant;
import java.util.List;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final AuthorityService authorityService;
    private final UserRepository userRepository;

    public UserService(PasswordEncoder passwordEncoder, AuthorityService authorityService, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.authorityService = authorityService;
        this.userRepository = userRepository;
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public List<UserView> getAllUsers(String filter, Pageable pageable) {
        return userRepository.findByUsernameContainsIgnoreCase(filter, pageable).stream()
            .map(UserView::reduced)
            .toList();
    }

    public Iterable<User> findAllByAuthority(String authority) {
        return userRepository.findAllByAuthority(authority);
    }

    @Transactional(readOnly = true)
    public User findUser(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    @Transactional(readOnly = true)
    public User findUser(String username) {
        return userRepository.findByUsernameIgnoreCase(username)
            .orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
    }

    @Transactional
    public User saveUser(User user) throws DataAccessException {
        user.setLastSeen(Instant.now());
        userRepository.save(user);
        return user;
    }

    @Transactional(readOnly = true)
    public User findCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) {
            throw new ResourceNotFoundException("Nobody authenticated!");
        }

        if (auth.getPrincipal() instanceof ApplicationUserDetails userDetails) {
            return userDetails.getUser();
        }

        UserDetails userDetails = (UserDetails) auth.getPrincipal();
        return userRepository.findByUsernameIgnoreCase(userDetails.getUsername())
            .orElseThrow(() -> new ResourceNotFoundException("User", "Username", auth.getName()));
    }

    @Transactional
    public void deleteUser(String username) {
        User toDelete = findUser(username);
        userRepository.delete(toDelete);
    }

    @Transactional
    public User editProfile(User user, @Valid EditProfileRequest request) {
        if (!request.getUsername().isBlank()) {
            this.changeUsername(user, request.getUsername());
        }

        if (!request.getPassword().isBlank()) {
            this.changePassword(user, request.getPassword());
        }

        if (!request.getEmail().isBlank()) {
            this.changeEmail(user, request.getEmail());
        }

        return this.saveUser(user);
    }

    public boolean existsUser(String username) {
        return userRepository.existsByUsernameIgnoreCase(username);
    }

    public void changeUsername(User user, String username) {
        Preconditions.checkState(!this.existsUser(username), "username already taken!");
        user.setUsername(username);
    }

    public void changeEmail(User user, String email) {
        Preconditions.checkState(!userRepository.existsByEmail(email), "email already taken!");
        user.setEmail(email);
    }

    public void changeAuthority(User user, String authority) {
        user.setAuthority(authorityService.findByName(authority));
    }

    public void changePassword(User user, String password) {
        Preconditions.checkState(password.length() > 5 && password.length() < 30, "password length must be between 5 and 30 characters");
        user.setPassword(passwordEncoder.encode(password));
    }
}
