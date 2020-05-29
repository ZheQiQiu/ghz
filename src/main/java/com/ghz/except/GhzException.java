package com.ghz.except;

import com.ghz.enumeration.StatusCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GhzException extends Exception {

    private int code;
    private String msg;

    public GhzException(StatusCodeEnum sce){
        code = sce.getCode();
        msg = sce.getMsg();
    }
}
