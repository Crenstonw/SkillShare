package com.triana.salesianos.edu.skillshare.user.controller;

import com.triana.salesianos.edu.skillshare.order.dto.OrderResponse;
import com.triana.salesianos.edu.skillshare.security.jwt.JwtProvider;
import com.triana.salesianos.edu.skillshare.user.dto.*;
import com.triana.salesianos.edu.skillshare.user.model.User;
import com.triana.salesianos.edu.skillshare.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = {"*"})
public class UserController {
    private final UserService service;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;


    @Operation(summary = "Register an User")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200",
                    description = "User registered successfully",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CreateUserRequest.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "id": "4272a77c-8516-420f-a83c-33a1462426c2",
                                                "email": "email@email.com",
                                                "username": "dirtyBilly",
                                                "createdAt": "12/06/2024 19:27:05",
                                                "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0MjcyYTc3Yy04NTE2LTQyMGYtYTgzYy0zM2ExNDYyNDI2YzIiLCJpYXQiOjE3MTgyOTk2MjV9.GgK2QJic8m9KrQa6NSyF2y33NUJZ-100w802HUm-W0wrmpC2PCmtkLDoTeVkW7VagVbV21NcKAqlFzBddCLYmw",
                                                "admin": false
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "401",
                    description = "Query did not return a unique result: 2 results were returned",
                    content = @Content),
    })
    @PostMapping("/auth/register")
    public ResponseEntity<JwtUserResponse> createUSerWithUserRole(
            @RequestBody CreateUserRequest createUserRequest
    ) {
        User user = service.createUserWithUserRole(createUserRequest);
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                createUserRequest.email(),
                                createUserRequest.password()
                        )
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        User userResponse = (User) authentication.getPrincipal();

        return ResponseEntity.status(HttpStatus.CREATED).body(JwtUserResponse.of(userResponse, token));
    }

    @Operation(summary = "Log in an User")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200",
                    description = "User logged successfully",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = LoginRequest.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "id": "c104d919-8dbc-4abc-ae51-5857e0346764",
                                                "email": "hola@hola.com",
                                                "username": "Antonioo23",
                                                "createdAt": "12/06/2024 19:26:15",
                                                "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJjMTA0ZDkxOS04ZGJjLTRhYmMtYWU1MS01ODU3ZTAzNDY3NjQiLCJpYXQiOjE3MTgyOTk3NjZ9.HaXoK2BOCkbW2hBfKpsb6FvEHKBDC-G8XLDmzgk0gKz2e0VAHS_sgkrzjsjVerEbGGUXNOZOnWLtiY_ERDW_ag",
                                                "admin": true
                                            }
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "401",
                    description = "No user with email hol@hola.com",
                    content = @Content),
    })
    @PostMapping("/auth/login")
    public ResponseEntity<JwtUserResponse> login(@RequestBody LoginRequest loginRequest) {
        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginRequest.email(),
                                loginRequest.password()
                        )
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        User user = (User) authentication.getPrincipal();

        return ResponseEntity.status(HttpStatus.CREATED).body(JwtUserResponse.of(user, token));

    }

    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="201",
                    description = "User created successfully",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = CreateUserRequest.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "id": "3696865e-2082-4f47-83dc-51b431204107",
                                                "email": "asd@asd.com",
                                                "username": "aaaaaaaaaaaa",
                                                "name": "aaa",
                                                "surname": "bbbb",
                                                "password": "{bcrypt}$2a$10$9hdFFtazyw2xBX78PaRPWe3zLR0f6x.tcibr2zJcQb8K38Uz8Co.O",
                                                "profilePicture": "https://as2.ftcdn.net/v2/jpg/03/49/49/79/1000_F_349497933_Ly4im8BDmHLaLzgyKg2f2yZOvJjBtlw5.jpg",
                                                "userRole": "[USER]",
                                                "createdAt": "2024-06-12T19:31:06.5585948",
                                                "enabled": true
                                            }
                                            """
                            )}
                    )}),

            @ApiResponse(responseCode = "400",
                    description = "Email is being used",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Username is being used",
                    content = @Content)
    })
    @PostMapping("/user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AllUserResponse> createUser(@RequestBody CreateUserRequest createUserRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(AllUserResponse.of(service.createUserWithUserRole(createUserRequest)));
    }

    @Operation(summary = "Get All users")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="201",
                    description = "Get all users paginated",
                    content = { @Content(mediaType = "application/json",
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                 "content": [
                                                     {
                                                         "id": "f6ee878c-434e-4a96-8c3c-3ccf4eae2afc",
                                                         "email": "asd@asd.com",
                                                         "username": "aaaaaaaaaaaa",
                                                         "name": "aaa",
                                                         "surname": "bbbb",
                                                         "password": "{bcrypt}$2a$10$e9/IDJRllryVyK3ESH9KYuGou/5sLiR5n0EFHB1Lw4WAXJtBIvTWm",
                                                         "profilePicture": "https://as2.ftcdn.net/v2/jpg/03/49/49/79/1000_F_349497933_Ly4im8BDmHLaLzgyKg2f2yZOvJjBtlw5.jpg",
                                                         "userRole": "[USER]",
                                                         "createdAt": "2024-06-12T19:41:14.568629",
                                                         "enabled": true
                                                     },
                                                     {
                                                         "id": "b45474a3-517c-40bf-8bd1-b650b061a283",
                                                         "email": "adios@hola.com",
                                                         "username": "SuMorenito23",
                                                         "name": "user2",
                                                         "surname": "suruser2",
                                                         "password": "{bcrypt}$2a$10$GqPpSMa0MN56HjRApORvp.LijGfwMVFeXz34G0D4L0BWql2w/M3Vu",
                                                         "profilePicture": "https://b2472105.smushcdn.com/2472105/wp-content/uploads/2023/01/Perfil-Profesional-_63-819x1024.jpg?lossy=1&strip=1&webp=1",
                                                         "userRole": "[USER]",
                                                         "createdAt": "2022-06-12T19:41:05.410536",
                                                         "enabled": true
                                                     },
                                                     {
                                                         "id": "9ecd7179-7792-4f67-93fa-036d4139e722",
                                                         "email": "aitormartinezmorzillo@gmail.com",
                                                         "username": "Athirot",
                                                         "name": "Aitor",
                                                         "surname": "Martinez Morcillo",
                                                         "password": "{bcrypt}$2a$10$dXi58zQhkludRzZZAxutQupImZJg5zxnWhTFedhanWsRf/s/tTw66",
                                                         "profilePicture": "https://images.ctfassets.net/h6goo9gw1hh6/2sNZtFAWOdP1lmQ33VwRN3/e40b6ea6361a1abe28f32e7910f63b66/1-intro-photo-final.jpg?w=1200&h=992&fl=progressive&q=70&fm=jpg",
                                                         "userRole": "[USER]",
                                                         "createdAt": "2023-06-12T19:41:05.516535",
                                                         "enabled": true
                                                     },
                                                     {
                                                         "id": "f2cbb7dc-6b98-4ca7-8197-2c4ca88e8ae8",
                                                         "email": "hola@hola.com",
                                                         "username": "Antonioo23",
                                                         "name": "user1",
                                                         "surname": "suruser1",
                                                         "password": "{bcrypt}$2a$10$mtiSXBYcRWnVq5tJaiBpSOaObkAFrGohY.IlEmosOHui/sRqS/zNS",
                                                         "profilePicture": "https://ichef.bbci.co.uk/news/976/cpsprodpb/16620/production/_91408619_55df76d5-2245-41c1-8031-07a4da3f313f.jpg",
                                                         "userRole": "[ADMIN]",
                                                         "createdAt": "2024-06-12T19:41:05.282985",
                                                         "enabled": true
                                                     }
                                                 ],
                                                 "pageable": {
                                                     "pageNumber": 0,
                                                     "pageSize": 10,
                                                     "sort": {
                                                         "sorted": false,
                                                         "empty": true,
                                                         "unsorted": true
                                                     },
                                                     "offset": 0,
                                                     "paged": true,
                                                     "unpaged": false
                                                 },
                                                 "totalPages": 1,
                                                 "totalElements": 4,
                                                 "last": true,
                                                 "number": 0,
                                                 "size": 10,
                                                 "numberOfElements": 4,
                                                 "sort": {
                                                     "sorted": false,
                                                     "empty": true,
                                                     "unsorted": true
                                                 },
                                                 "first": true,
                                                 "empty": false
                                             }
                                            """
                            )}
                    )}),
    })
    @GetMapping("/user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<AllUserResponse>> getAllUsers(@PageableDefault Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAllUsers(pageable));
    }

    @Operation(summary = "Get an user")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200",
                    description = "get an user by its ID",
                    content = { @Content(mediaType = "application/json",
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                "id": "f2cbb7dc-6b98-4ca7-8197-2c4ca88e8ae8",
                                                "email": "hola@hola.com",
                                                "username": "Antonioo23",
                                                "name": "user1",
                                                "surname": "suruser1",
                                                "password": "{bcrypt}$2a$10$mtiSXBYcRWnVq5tJaiBpSOaObkAFrGohY.IlEmosOHui/sRqS/zNS",
                                                "profilePicture": "https://ichef.bbci.co.uk/news/976/cpsprodpb/16620/production/_91408619_55df76d5-2245-41c1-8031-07a4da3f313f.jpg",
                                                "role": [
                                                    "ADMIN"
                                                ],
                                                "orders": [
                                                    {
                                                        "id": "e1e8b836-47a0-4a61-93cc-d2b71b398f29",
                                                        "title": "Saco la basura por ti",
                                                        "description": "descripcion 2 de la ordenanza",
                                                        "price": 23.34,
                                                        "state": "CLOSED",
                                                        "createdAt": "2024-06-12T19:41:05.883529",
                                                        "lastTimeModified": "2024-06-12T19:41:05.883529",
                                                        "isAboutToExpire": false,
                                                        "user": {
                                                            "id": "f2cbb7dc-6b98-4ca7-8197-2c4ca88e8ae8",
                                                            "email": "hola@hola.com",
                                                            "username": "Antonioo23",
                                                            "name": "user1",
                                                            "surname": "suruser1",
                                                            "password": "{bcrypt}$2a$10$mtiSXBYcRWnVq5tJaiBpSOaObkAFrGohY.IlEmosOHui/sRqS/zNS",
                                                            "profilePicture": "https://ichef.bbci.co.uk/news/976/cpsprodpb/16620/production/_91408619_55df76d5-2245-41c1-8031-07a4da3f313f.jpg",
                                                            "userRole": "[ADMIN]",
                                                            "createdAt": "2024-06-12T19:41:05.282985",
                                                            "enabled": true
                                                        },
                                                        "tags": [
                                                            {
                                                                "id": "ed8cb3a8-f039-4626-8609-aa3ea3c5115b",
                                                                "name": "tag1"
                                                            }
                                                        ]
                                                    },
                                                    {
                                                        "id": "f72a7999-65d4-49a2-8598-c64c05a894db",
                                                        "title": "Limpieza del hogar",
                                                        "description": "Realizo limpieza completa del hogar.",
                                                        "price": 30.0,
                                                        "state": "OPEN",
                                                        "createdAt": "2024-06-12T19:41:05.911528",
                                                        "lastTimeModified": "2024-06-12T19:41:05.911528",
                                                        "isAboutToExpire": false,
                                                        "user": {
                                                            "id": "f2cbb7dc-6b98-4ca7-8197-2c4ca88e8ae8",
                                                            "email": "hola@hola.com",
                                                            "username": "Antonioo23",
                                                            "name": "user1",
                                                            "surname": "suruser1",
                                                            "password": "{bcrypt}$2a$10$mtiSXBYcRWnVq5tJaiBpSOaObkAFrGohY.IlEmosOHui/sRqS/zNS",
                                                            "profilePicture": "https://ichef.bbci.co.uk/news/976/cpsprodpb/16620/production/_91408619_55df76d5-2245-41c1-8031-07a4da3f313f.jpg",
                                                            "userRole": "[ADMIN]",
                                                            "createdAt": "2024-06-12T19:41:05.282985",
                                                            "enabled": true
                                                        },
                                                        "tags": [
                                                            {
                                                                "id": "ed8cb3a8-f039-4626-8609-aa3ea3c5115b",
                                                                "name": "tag1"
                                                            }
                                                        ]
                                                    },
                                                    {
                                                        "id": "20b68915-1542-4451-b5cf-f7ce2d4ac8a8",
                                                        "title": "titulo2",
                                                        "description": "descripcion 2 de la ordenanza",
                                                        "price": 25.34,
                                                        "state": "OPEN",
                                                        "createdAt": "2024-06-12T19:41:05.868527",
                                                        "lastTimeModified": "2024-06-12T19:41:05.868527",
                                                        "isAboutToExpire": false,
                                                        "user": {
                                                            "id": "f2cbb7dc-6b98-4ca7-8197-2c4ca88e8ae8",
                                                            "email": "hola@hola.com",
                                                            "username": "Antonioo23",
                                                            "name": "user1",
                                                            "surname": "suruser1",
                                                            "password": "{bcrypt}$2a$10$mtiSXBYcRWnVq5tJaiBpSOaObkAFrGohY.IlEmosOHui/sRqS/zNS",
                                                            "profilePicture": "https://ichef.bbci.co.uk/news/976/cpsprodpb/16620/production/_91408619_55df76d5-2245-41c1-8031-07a4da3f313f.jpg",
                                                            "userRole": "[ADMIN]",
                                                            "createdAt": "2024-06-12T19:41:05.282985",
                                                            "enabled": true
                                                        },
                                                        "tags": [
                                                            {
                                                                "id": "24ec790e-f230-4f5c-bb22-a99eb6b2750f",
                                                                "name": "tag2"
                                                            },
                                                            {
                                                                "id": "ed8cb3a8-f039-4626-8609-aa3ea3c5115b",
                                                                "name": "tag1"
                                                            }
                                                        ]
                                                    },
                                                    {
                                                        "id": "d3dc6b31-8243-4704-9f8e-698b4899d0d2",
                                                        "title": "Corte de cabello a domicilio",
                                                        "description": "Servicio de corte de cabello profesional en tu hogar.",
                                                        "price": 15.0,
                                                        "state": "CLOSED",
                                                        "createdAt": "2024-06-12T19:41:05.925528",
                                                        "lastTimeModified": "2024-06-12T19:41:05.925528",
                                                        "isAboutToExpire": false,
                                                        "user": {
                                                            "id": "f2cbb7dc-6b98-4ca7-8197-2c4ca88e8ae8",
                                                            "email": "hola@hola.com",
                                                            "username": "Antonioo23",
                                                            "name": "user1",
                                                            "surname": "suruser1",
                                                            "password": "{bcrypt}$2a$10$mtiSXBYcRWnVq5tJaiBpSOaObkAFrGohY.IlEmosOHui/sRqS/zNS",
                                                            "profilePicture": "https://ichef.bbci.co.uk/news/976/cpsprodpb/16620/production/_91408619_55df76d5-2245-41c1-8031-07a4da3f313f.jpg",
                                                            "userRole": "[ADMIN]",
                                                            "createdAt": "2024-06-12T19:41:05.282985",
                                                            "enabled": true
                                                        },
                                                        "tags": [
                                                            {
                                                                "id": "24ec790e-f230-4f5c-bb22-a99eb6b2750f",
                                                                "name": "tag2"
                                                            }
                                                        ]
                                                    },
                                                    {
                                                        "id": "0d180eb5-5e59-4cbf-add4-64f3f3a6a5e1",
                                                        "title": "Limpieza de jardines",
                                                        "description": "Limpieza y mantenimiento de jardines y áreas verdes.",
                                                        "price": 45.0,
                                                        "state": "OCCUPIED",
                                                        "createdAt": "2024-06-12T19:41:05.955524",
                                                        "lastTimeModified": "2024-06-12T19:41:05.955524",
                                                        "isAboutToExpire": false,
                                                        "user": {
                                                            "id": "f2cbb7dc-6b98-4ca7-8197-2c4ca88e8ae8",
                                                            "email": "hola@hola.com",
                                                            "username": "Antonioo23",
                                                            "name": "user1",
                                                            "surname": "suruser1",
                                                            "password": "{bcrypt}$2a$10$mtiSXBYcRWnVq5tJaiBpSOaObkAFrGohY.IlEmosOHui/sRqS/zNS",
                                                            "profilePicture": "https://ichef.bbci.co.uk/news/976/cpsprodpb/16620/production/_91408619_55df76d5-2245-41c1-8031-07a4da3f313f.jpg",
                                                            "userRole": "[ADMIN]",
                                                            "createdAt": "2024-06-12T19:41:05.282985",
                                                            "enabled": true
                                                        },
                                                        "tags": [
                                                            {
                                                                "id": "ed8cb3a8-f039-4626-8609-aa3ea3c5115b",
                                                                "name": "tag1"
                                                            }
                                                        ]
                                                    }
                                                ],
                                                "favoriteOrders": [],
                                                "enabled": true
                                            }
                                            """
                            )}
                    )})
    })
    @GetMapping("/user/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<UserDetailsDto> getUser(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getUser(id));
    }

    @Operation(summary = "Edit an user")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200",
                    description = "Edit an user by its ID",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = EditUserRequest.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                 "id": "9ecd7179-7792-4f67-93fa-036d4139e722",
                                                 "email": "hola@racano.com",
                                                 "username": "Antonio22",
                                                 "name": "useaaaaa",
                                                 "surname": "suruser1",
                                                 "password": "{bcrypt}$2a$10$dXi58zQhkludRzZZAxutQupImZJg5zxnWhTFedhanWsRf/s/tTw66",
                                                 "profilePicture": "https://ichef.bbci.co.uk/news/976/cpsprodpb/16620/production/_91408619_55df76d5-2245-41c1-8031-07a4da3f313f.jpg",
                                                 "role": [
                                                     "USER"
                                                 ],
                                                 "orders": [
                                                     {
                                                         "id": "b0676958-e09a-4471-ac8f-89f7bd2c8729",
                                                         "title": "Enseño guitarra",
                                                         "description": "Clases de guitarra para principiantes.",
                                                         "price": 40.0,
                                                         "state": "OPEN",
                                                         "createdAt": "2024-06-12T19:41:05.918525",
                                                         "lastTimeModified": "2024-06-12T19:41:05.918525",
                                                         "isAboutToExpire": false,
                                                         "user": {
                                                             "id": "9ecd7179-7792-4f67-93fa-036d4139e722",
                                                             "email": "hola@racano.com",
                                                             "username": "Antonio22",
                                                             "name": "useaaaaa",
                                                             "surname": "suruser1",
                                                             "password": "{bcrypt}$2a$10$dXi58zQhkludRzZZAxutQupImZJg5zxnWhTFedhanWsRf/s/tTw66",
                                                             "profilePicture": "https://ichef.bbci.co.uk/news/976/cpsprodpb/16620/production/_91408619_55df76d5-2245-41c1-8031-07a4da3f313f.jpg",
                                                             "userRole": "[USER]",
                                                             "createdAt": "2023-06-12T19:41:05.516535",
                                                             "enabled": true
                                                         },
                                                         "tags": [
                                                             {
                                                                 "id": "24ec790e-f230-4f5c-bb22-a99eb6b2750f",
                                                                 "name": "tag2"
                                                             }
                                                         ]
                                                     },
                                                     {
                                                         "id": "cab6421a-94b6-45cd-a901-18983ba3c79a",
                                                         "title": "Paseo con perros",
                                                         "description": "Paseo con tus perros por el parque durante 1 hora.",
                                                         "price": 20.5,
                                                         "state": "OPEN",
                                                         "createdAt": "2024-06-12T19:41:05.896523",
                                                         "lastTimeModified": "2024-06-12T19:41:05.896523",
                                                         "isAboutToExpire": false,
                                                         "user": {
                                                             "id": "9ecd7179-7792-4f67-93fa-036d4139e722",
                                                             "email": "hola@racano.com",
                                                             "username": "Antonio22",
                                                             "name": "useaaaaa",
                                                             "surname": "suruser1",
                                                             "password": "{bcrypt}$2a$10$dXi58zQhkludRzZZAxutQupImZJg5zxnWhTFedhanWsRf/s/tTw66",
                                                             "profilePicture": "https://ichef.bbci.co.uk/news/976/cpsprodpb/16620/production/_91408619_55df76d5-2245-41c1-8031-07a4da3f313f.jpg",
                                                             "userRole": "[USER]",
                                                             "createdAt": "2023-06-12T19:41:05.516535",
                                                             "enabled": true
                                                         },
                                                         "tags": [
                                                             {
                                                                 "id": "ed8cb3a8-f039-4626-8609-aa3ea3c5115b",
                                                                 "name": "tag1"
                                                             }
                                                         ]
                                                     },
                                                     {
                                                         "id": "27cfe6f6-1746-499c-ba14-488e470bc795",
                                                         "title": "Reparación de grifos",
                                                         "description": "Reparo cualquier tipo de grifos en tu hogar.",
                                                         "price": 25.0,
                                                         "state": "OPEN",
                                                         "createdAt": "2024-06-12T19:41:05.938526",
                                                         "lastTimeModified": "2024-06-12T19:41:05.938526",
                                                         "isAboutToExpire": false,
                                                         "user": {
                                                             "id": "9ecd7179-7792-4f67-93fa-036d4139e722",
                                                             "email": "hola@racano.com",
                                                             "username": "Antonio22",
                                                             "name": "useaaaaa",
                                                             "surname": "suruser1",
                                                             "password": "{bcrypt}$2a$10$dXi58zQhkludRzZZAxutQupImZJg5zxnWhTFedhanWsRf/s/tTw66",
                                                             "profilePicture": "https://ichef.bbci.co.uk/news/976/cpsprodpb/16620/production/_91408619_55df76d5-2245-41c1-8031-07a4da3f313f.jpg",
                                                             "userRole": "[USER]",
                                                             "createdAt": "2023-06-12T19:41:05.516535",
                                                             "enabled": true
                                                         },
                                                         "tags": [
                                                             {
                                                                 "id": "ed8cb3a8-f039-4626-8609-aa3ea3c5115b",
                                                                 "name": "tag1"
                                                             }
                                                         ]
                                                     },
                                                     {
                                                         "id": "34fa3a2c-23db-46e2-8cb6-c48bc67dab51",
                                                         "title": "Servicio de lavandería",
                                                         "description": "Lavado, secado y planchado de ropa.",
                                                         "price": 20.0,
                                                         "state": "CLOSED",
                                                         "createdAt": "2024-06-12T19:41:05.962523",
                                                         "lastTimeModified": "2024-06-12T19:41:05.962523",
                                                         "isAboutToExpire": false,
                                                         "user": {
                                                             "id": "9ecd7179-7792-4f67-93fa-036d4139e722",
                                                             "email": "hola@racano.com",
                                                             "username": "Antonio22",
                                                             "name": "useaaaaa",
                                                             "surname": "suruser1",
                                                             "password": "{bcrypt}$2a$10$dXi58zQhkludRzZZAxutQupImZJg5zxnWhTFedhanWsRf/s/tTw66",
                                                             "profilePicture": "https://ichef.bbci.co.uk/news/976/cpsprodpb/16620/production/_91408619_55df76d5-2245-41c1-8031-07a4da3f313f.jpg",
                                                             "userRole": "[USER]",
                                                             "createdAt": "2023-06-12T19:41:05.516535",
                                                             "enabled": true
                                                         },
                                                         "tags": [
                                                             {
                                                                 "id": "ed8cb3a8-f039-4626-8609-aa3ea3c5115b",
                                                                 "name": "tag1"
                                                             }
                                                         ]
                                                     },
                                                     {
                                                         "id": "e438c08c-4e3b-48dc-9b35-95e5ddbdff81",
                                                         "title": "Ensenio a comer caracoles",
                                                         "description": "descripcion de la ordenanza lorem ipsum amet",
                                                         "price": 24.34,
                                                         "state": "OPEN",
                                                         "createdAt": "2024-02-12T19:41:05.853525",
                                                         "lastTimeModified": "2024-04-13T19:41:05.854525",
                                                         "isAboutToExpire": true,
                                                         "user": {
                                                             "id": "9ecd7179-7792-4f67-93fa-036d4139e722",
                                                             "email": "hola@racano.com",
                                                             "username": "Antonio22",
                                                             "name": "useaaaaa",
                                                             "surname": "suruser1",
                                                             "password": "{bcrypt}$2a$10$dXi58zQhkludRzZZAxutQupImZJg5zxnWhTFedhanWsRf/s/tTw66",
                                                             "profilePicture": "https://ichef.bbci.co.uk/news/976/cpsprodpb/16620/production/_91408619_55df76d5-2245-41c1-8031-07a4da3f313f.jpg",
                                                             "userRole": "[USER]",
                                                             "createdAt": "2023-06-12T19:41:05.516535",
                                                             "enabled": true
                                                         },
                                                         "tags": [
                                                             {
                                                                 "id": "ed8cb3a8-f039-4626-8609-aa3ea3c5115b",
                                                                 "name": "tag1"
                                                             }
                                                         ]
                                                     }
                                                 ],
                                                 "favoriteOrders": [],
                                                 "enabled": true
                                             }
                                            """
                            )}
                    )})
    })
    @PutMapping("/user/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<UserDetailsDto> editUser(@PathVariable String id, @RequestBody EditUserRequest editUserRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(service.editUser(id, editUserRequest));
    }

    @Operation(summary = "Delete an user")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="204",
                    description = "Delete an user by its ID",
                    content = { @Content(mediaType = "application/json",
                            examples = {@ExampleObject(
                                    value = """
                                            []
                                            """
                            )}
                    )})
    })
    @DeleteMapping("user/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        service.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(summary = "Get my favorites")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200",
                    description = "Get all my favorite orders",
                    content = { @Content(mediaType = "application/json"
                    )})
    })
    @GetMapping("/user/favorite")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<OrderResponse>> getMyFavorites() {
        return ResponseEntity.ok().body(service.myFavorites());
    }

    @Operation(summary = "newFavoriteOrder")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200",
                    description = "updates user's favorite orders list with a new one",
                    content = { @Content(mediaType = "application/json"
                    )})
    })
    @PutMapping("/user/favorite/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<FavoriteDto> newFavoriteOrder(@PathVariable String id) {
        return ResponseEntity.ok().body(service.newFavoriteOrder(id));
    }

    @Operation(summary = "deleteFavoriteOrder")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200",
                    description = "updates user's favorite orders list without the mentioned one",
                    content = { @Content(mediaType = "application/json"
                    )})
    })
    @PutMapping("/user/unfavorite/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<FavoriteDto> deleteFavoriteOrder(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.deleteFavoriteOrder(id));
    }

    @Operation(summary = "actualUserInfo")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200",
                    description = "Gives your current user info",
                    content = { @Content(mediaType = "application/json"
                    )})
    })
    @GetMapping("/user/me")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<AllUserResponse> actualUserInfo() {
        return ResponseEntity.status(HttpStatus.OK).body(service.actualUserInfo());
    }

    @Operation(summary = "givePrivileges")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200",
                    description = "Give privileges or removes it from an user",
                    content = { @Content(mediaType = "application/json"
                    )})
    })
    @PutMapping("/user/privileges/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDetailsDto> givePrivileges(@PathVariable String id) {
        return ResponseEntity.ok().body(service.givePrivileges(id));
    }

    @Operation(summary = "Ban user")
    @ApiResponses(value = {
            @ApiResponse(responseCode ="200",
                    description = "updates an user so he cannot log into the app",
                    content = { @Content(mediaType = "application/json"
                    )})
    })
    @PutMapping("/user/ban/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDetailsDto> banUser(@PathVariable String id) {
        return ResponseEntity.ok().body(service.banUser(id));
    }
}
