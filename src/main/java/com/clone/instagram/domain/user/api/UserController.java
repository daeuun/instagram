package com.clone.instagram.domain.user.api;

import com.clone.instagram.domain.user.dto.SignUpRequest;
import com.clone.instagram.domain.user.dto.UpdateProfileRequest;
import com.clone.instagram.domain.user.service.UserService;
import com.clone.instagram.domain.result.ResultCode;
import com.clone.instagram.domain.result.ResultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public Boolean signUp(@Validated @RequestBody SignUpRequest request) {
        userService.signup(request);
        return true;
    }

    @DeleteMapping("/users/me")
    public void withdraw() {
        userService.withdraw();
    }

    @PostMapping("/users/withdraw")
    public ResponseEntity<ResultResponse> softwithdraw() {
        userService.softWithdraw();
        ResultResponse result = ResultResponse.of(
                ResultCode.USER_WITHDRAW_SUCCESS,
                true
        );
        return new ResponseEntity(result, HttpStatus.valueOf(result.getStatus()));
    }

}
