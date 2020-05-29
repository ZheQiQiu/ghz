package com.ghz.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusCodeEnum {
    SUCCESS(0,"Success"),
    USERNAME_PASSWORD_ERROR(101,"用户名或密码错误"),
    PARAM_EXCEPTION(401,"参数异常"),
    NUMBER_FORMAT_ERROR(402,"数字格式转换错误"),
    NOT_FOUND(404,"404 NOT FOUND"),
    POLYGON_LEGAL(490,"多边形非法"),
    SERVER_ERROR(500,"服务器异常"),
    DATABASE_EXCEPTION(501,"数据库异常"),
    DATABASE_SAVE_FAIL(502,"数据保存失败"),
    DATABASE_UPDATE_FAIL(503,"数据库更新失败"),
    DATABASE_DELETE_FAIL(504,"数据库删除失败"),
    VIDEO_SERVER_ERROR(590,"视频服务异常"),
    UNKNOWN_ERROR(999,"未知错误"),
    LOGIN_INVALID(65535,"未登录");

    private final int code;
    private final String msg;
}
