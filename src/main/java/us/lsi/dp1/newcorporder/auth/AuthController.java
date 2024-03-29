package us.lsi.dp1.newcorporder.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import us.lsi.dp1.newcorporder.auth.jwt.JwtUtils;
import us.lsi.dp1.newcorporder.auth.payload.request.LoginRequest;
import us.lsi.dp1.newcorporder.auth.payload.request.SignupRequest;
import us.lsi.dp1.newcorporder.auth.payload.response.JwtResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "The Authentication API based on JWT")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final AuthService authService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, AuthService authService) {
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.authService = authService;
    }

    @Operation(
        summary = "Sign up",
        description = "Sign up",
        tags = "post"
    )
    @ApiResponse(
        responseCode = "201",
        description = "User registered successfully"
    )
    @ApiResponse(
        responseCode = "400",
        description = "username already taken"
    )
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        authService.registerUser(signUpRequest);
    }

    @Operation(
        summary = "Login",
        description = "Login",
        tags = "post"
    )
    @ApiResponse(
        responseCode = "200",
        description = "login successful"
    )
    @ApiResponse(
        responseCode = "400",
        description = "Bad Credentials"
    )

    @PostMapping("/login")
    public ResponseEntity<Object> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            ApplicationUserDetails userDetails = (ApplicationUserDetails) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

            return ResponseEntity.ok().body(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), roles));
        } catch (BadCredentialsException exception) {
            return ResponseEntity.badRequest().body("Bad Credentials!");
        }
    }

    @Operation(
        summary = "Validate token",
        description = "Validate token",
        tags = "get"
    )
    @ApiResponse(
        responseCode = "200",
        description = "The result"
    )
    @ApiResponse(
        responseCode = "400",
        description = "Bad Credentials"
    )
    @ApiResponse(
        responseCode = "401",
        description = "Authorization information is missing or invalid"
    )
    @GetMapping("/validate")
    @ResponseStatus(HttpStatus.OK)
    public boolean validateToken(@RequestParam String token) {
        return jwtUtils.validateJwtToken(token);
    }
}
