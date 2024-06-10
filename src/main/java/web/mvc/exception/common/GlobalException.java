package web.mvc.exception.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import web.mvc.exception.user.ErrorCode;

@Getter
@RequiredArgsConstructor
public class GlobalException  extends RuntimeException{
    private final ErrorCode errorCode;
}
