package com.clone.instagram.domain.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class ResultResponse {
//    @ApiModelProperty(value = "Http status code")
    private final int status;

//    @ApiModelProperty(value = "Business status code")
    private final String code;

//    @ApiModelProperty(value = "Response message")
    private final String message;

//    @ApiModelProperty(value = "Response data")
    private final Object data;

    public ResultResponse(ResultCode resultCode, Object data) {
        this.status = resultCode.getStatus();
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }

    public static ResultResponse of(ResultCode resultCode, Object data) {
        return new ResultResponse(resultCode, data);
    }

    public int getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }
}
