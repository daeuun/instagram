package com.clone.instagram.domain.user.api;

import com.clone.instagram.domain.user.dto.SignUpRequest;
import com.clone.instagram.domain.user.dto.UpdateProfileRequest;
import com.clone.instagram.domain.user.service.UserService;
import com.clone.instagram.domain.result.ResultCode;
import com.clone.instagram.domain.result.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public Boolean signUp(@Validated @RequestBody SignUpRequest request) {
        userService.signup(request);
        return true;
    }

    @DeleteMapping("/users/me")
    public void withdraw() {
        userService.withdraw();
    }

    @DeleteMapping("/users/me")
    public ResponseEntity<ResultResponse> softwithdraw() {
        userService.softWithdraw();
        ResultResponse result = ResultResponse.of(
                ResultCode.USER_WITHDRAW_SUCCESS,
                true
        );
        return new ResponseEntity(result, HttpStatus.valueOf(result.getStatus()));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResultResponse> userProfile(@PathVariable Long userId) {
        ResultResponse result = ResultResponse.of(
                ResultCode.GET_USER_PROFILE_SUCCESSFULLY,
                userService.userProfile(userId)
        );
        return new ResponseEntity(result, HttpStatus.valueOf(result.getStatus()));
    }

    @PutMapping("/users/me")
    public ResponseEntity<ResultResponse> updateProfile(@RequestBody UpdateProfileRequest request) {
        ResultResponse result = ResultResponse.of(
                ResultCode.UPDATE_MY_PROFILE_SUCCESSFULLY,
                userService.updateProfile(request)
        );
        return new ResponseEntity(result, HttpStatus.valueOf(result.getStatus()));
    }

    @PostMapping("/follow/{userId}")
    public ResponseEntity<ResultResponse> follow(@PathVariable Long userId) {
        ResultResponse result = ResultResponse.of(
                ResultCode.USER_FOLLOW_SUCCESS,
                userService.follow(userId)
        );
        return new ResponseEntity(result, HttpStatusCode.valueOf(result.getStatus()));
    }
}
