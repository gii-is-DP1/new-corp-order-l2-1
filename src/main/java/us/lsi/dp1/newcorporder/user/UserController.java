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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import us.lsi.dp1.newcorporder.auth.payload.response.MessageResponse;
import us.lsi.dp1.newcorporder.exceptions.AccessDeniedException;
import us.lsi.dp1.newcorporder.friendship.FriendshipService;
import us.lsi.dp1.newcorporder.user.payload.request.EditProfileRequest;
import us.lsi.dp1.newcorporder.user.payload.response.UserView;
import us.lsi.dp1.newcorporder.util.RestPreconditions;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "User", description = "The User API")
class UserController {

    private final UserService userService;
    private final FriendshipService friendshipService;

    @Autowired
    public UserController(UserService userService, FriendshipService friendshipService) {
        this.userService = userService;
        this.friendshipService = friendshipService;
    }

    @Operation(
        summary = "Get all users",
        description = "Get all users",
        tags = "get"
    )
    @ApiResponse(
        responseCode = "200",
        description = "All users"
    )
    @GetMapping()
    public List<UserView> findAll(@RequestParam(required = false) String username) {
         return userService.getAllUsers(username);
    }

    @Operation(
        summary = "Find an user by its username",
        description = "Find an user by its username",
        tags = "get"
    )
    @ApiResponse(
        responseCode = "200",
        description = "The found user"
    )
    @ApiResponse(
        responseCode = "404",
        description = "User not found"
    )
    @GetMapping("/{username}")
    public UserView findById(@PathVariable String username) {
        User user = userService.findUser(username);
        RestPreconditions.checkNotNull(user, "User", "username", username);

        return UserView.expanded(user, userService.findCurrentUser());
    }

    @Operation(
        summary = "Update a user's profile",
        description = "Update a user's profile",
        tags = "put"
    )
    @ApiResponse(
        responseCode = "200",
        description = "The updated user"
    )
    @ApiResponse(
        responseCode = "400",
        description = "Invalid arguments"
    )
    @PutMapping("/{username}")
    public UserView update(@PathVariable String username, @RequestBody @Valid EditProfileRequest request) {
        User user = userService.findUser(username);
        User loggedIn = userService.findCurrentUser();

        RestPreconditions.checkAccess(loggedIn.equals(user) || loggedIn.hasAnyAuthority("admin"));
        RestPreconditions.checkNotNull(user, "User", "username", username);

        return UserView.expanded(userService.editProfile(user, request), loggedIn);
    }

    @Operation(
        summary = "Delete an user by its id",
        description = "Delete an user by its id",
        tags = "delete"
    )
    @ApiResponse(
        responseCode = "200",
        description = "User deleted"
    )
    @ApiResponse(
        responseCode = "403",
        description = "You cannot delete yourself"
    )
    @ApiResponse(
        responseCode = "404",
        description = "User to delete not found"
    )
    @DeleteMapping("/{username}")
    public ResponseEntity<MessageResponse> delete(@PathVariable String username) {
        User user = userService.findUser(username);
        RestPreconditions.checkNotNull(user, "User", "username", username);

        if (!user.equals(userService.findCurrentUser())) {
            userService.deleteUser(user.getUsername());
            return new ResponseEntity<>(new MessageResponse("User deleted!"), HttpStatus.OK);
        } else {
            throw new AccessDeniedException("You cannot delete yourself!");
        }
    }

    @Operation(
        summary = "Find an user picture by its username",
        description = "Find an user picture by its username",
        tags = "get"
    )
    @ApiResponse(
        responseCode = "200",
        description = "The found user picture"
    )
    @ApiResponse(
        responseCode = "404",
        description = "User not found"
    )
    @GetMapping("/{username}/picture")
    public UserView findPicture(@PathVariable String username) {
        User user = userService.findUser(username);
        RestPreconditions.checkNotNull(user, "User", "username", username);

        return UserView.reduced(user);
    }

    @Operation(
        summary = "Get the friends of the given user",
        description = "Get the friends of the given user",
        tags = "get"
    )
    @ApiResponse(
        responseCode = "200",
        description = "The user's friends"
    )
    @ApiResponse(
        responseCode = "404",
        description = "User not found"
    )
    @GetMapping("/{username}/friends")
    public List<UserView> getFriends(@PathVariable String username) {
        User user = userService.findUser(username);
        RestPreconditions.checkNotNull(user, "User", "user", username);

        return friendshipService.getFriends(user);
    }
}
