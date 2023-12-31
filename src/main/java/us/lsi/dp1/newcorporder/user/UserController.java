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
import us.lsi.dp1.newcorporder.user.payload.EditProfileRequest;
import us.lsi.dp1.newcorporder.util.RestPreconditions;

@RestController
@RequestMapping("/api/v1/users")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "User", description = "The User API")
class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
        summary = "Find an user by its id",
        description = "Find an user by its id",
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
    @GetMapping(value = "/{id}")
    public ResponseEntity<User> findById(@PathVariable("id") Integer id) {
        return new ResponseEntity<>(userService.findUser(id), HttpStatus.OK);
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
    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User update(@PathVariable Integer id, @RequestBody @Valid EditProfileRequest request) {
        User loggedIn = userService.findCurrentUser();
        RestPreconditions.checkAccess(id.equals(loggedIn.getId()) || loggedIn.hasAnyAuthority("admin"));

        return userService.editProfile(userService.findUser(id), request);
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
    @DeleteMapping(value = "/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<MessageResponse> delete(@PathVariable("userId") int id) {
        RestPreconditions.checkNotNull(userService.findUser(id), "User", "ID", id);
        if (userService.findCurrentUser().getId() != id) {
            userService.deleteUser(id);
            return new ResponseEntity<>(new MessageResponse("User deleted!"), HttpStatus.OK);
        } else
            throw new AccessDeniedException("You cannot delete yourself!");
    }
}
