package ru.gb.buv.spring1L11.api_controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.buv.spring1L11.service.UserService;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    //***********************************************
    @GetMapping()
    public List<String> getUsernames() {
        return userService.getAllUsernames();
    }
}
