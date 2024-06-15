package it.macgood.weldbootmvn.user;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.macgood.weldbootmvn.files.PhotoService;
import it.macgood.weldbootmvn.user.model.Role;
import it.macgood.weldbootmvn.user.model.User;
import it.macgood.weldbootmvn.user.presentation.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PhotoService photoService;

    @GetMapping("/auth")
    @ApiResponse(description = "Сюда кидается телефон + ник(может быть null если с сайта), " +
            "Если юзер есть в базе, то возвращается инфа по нему, " +
            "иначе отправляется код и возвращается телефон + id пользователя + сгенеренный код + эндпоинт, " +
            "где юзер может подтянуть свою роль"
    )
    public ResponseEntity<?> auth(AuthRequest request) {
        Optional<User> userByPhone =
                userRepository.findUserByPhone(request.phone());

        if (userByPhone.isPresent()) {
            User user = userByPhone.get();
            return ResponseEntity.status(200)
                    .body(
                            new AuthResponse(
                                    user.getId(),
                                    user.getFirstname(),
                                    user.getLastname(),
                                    user.getEmail(),
                                    user.getRole().name(),
                                    user.getPhone()
                            )
                    );
        }
        User saved = userRepository.save(User.createNewUser(request.name(), request.phone()));

        var auth = authService.auth(request.phone());
        if (auth != null && auth.getStatus().equals("OK")) {
            auth.setUserId(saved.getId());
            return ResponseEntity
                    .status(201)
                    .body(auth);
        }
        return ResponseEntity.internalServerError()
                .body(new CodeNotSentResponse(
                        request.phone(),
                        "Invalid phone number or service is crushed"
                ));
    }


    @PostMapping("/process")
    @ApiResponse(description = "Сюда кидается id пользователя полученное после auth  и его роль WELDER, DEFECTOSCOPIST. После чего пользователь полностью зареган")
    public ResponseEntity<?> process(AttachUserRoleRequest request) {
        Optional<User> byId = userRepository.findById(request.userId());

        if (byId.isPresent()) {
            User user = byId.get();
            user.setRole(Role.valueOf(request.role()));
            User saved = userRepository.save(user);
            return ResponseEntity.ok(saved);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(description = "Здесь можно отдельным запросом загрузить фотку пользователя")
    public String load(
            @RequestPart("photo") MultipartFile photo,
            @RequestPart("userId") String userId
    ) throws IOException {

        String s = photoService.getFileUrl(photo, Long.parseLong(userId));

        return s;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userRepository.findById(id));
    }

}
