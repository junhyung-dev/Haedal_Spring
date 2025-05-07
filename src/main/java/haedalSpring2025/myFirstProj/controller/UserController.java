package haedalSpring2025.myFirstProj.controller;

import haedalSpring2025.myFirstProj.domain.User;
import haedalSpring2025.myFirstProj.dto.UserDetailResponseDto;
import haedalSpring2025.myFirstProj.dto.UserSimpleResponseDto;
import haedalSpring2025.myFirstProj.dto.UserUpdateRequestDto;
import haedalSpring2025.myFirstProj.service.AuthService;
import haedalSpring2025.myFirstProj.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    @Autowired
    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;

    }

    @GetMapping("/users") //유저 정보 간략 조회
    //parameter가 없어도 된다. username=null이면, getAllUsers, 존재하면 해당 유저 정보를 가져옴
    public ResponseEntity<List<UserSimpleResponseDto>> getUsers(@RequestParam(required = false)String username, HttpServletRequest request) {
        User currentUser = authService.getCurrentUser(request);

        List<UserSimpleResponseDto> users;
        if (username == null || username.isEmpty()) {
            users = userService.getAllUsers(currentUser);
        } else {
            users = userService.getUserByUsername(currentUser, username);
        }

        return ResponseEntity.ok(users);
    }

    @PutMapping("/users/profile") // 유저 정보 수정 (이미지 제외)
    public ResponseEntity<UserDetailResponseDto> updateUser(@RequestBody UserUpdateRequestDto userUpdateRequestDto, HttpServletRequest request) {
        User currentUser = authService.getCurrentUser(request);
        UserDetailResponseDto updated = userService.updateUser(currentUser, userUpdateRequestDto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/users/{userId}/profile") // 유저 정보 상세 조회
    //중괄호 필요정보 (requestparam이랑 반대)
    public ResponseEntity<UserDetailResponseDto>
    getUserProfile(@PathVariable Long userId, HttpServletRequest request) {
        User currentUser = authService.getCurrentUser(request);

        UserDetailResponseDto userDetailResponseDto = userService.getUserDetail(currentUser, userId);

        return ResponseEntity.ok(userDetailResponseDto);
    }
}