package com.xavier.base.common;

import com.xavier.base.entity.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/status")
public class HttpStatusApi {

    @RequestMapping(path = "/{statusCode}")
    public ResponseEntity responseEntity(@PathVariable int statusCode) {
        HttpStatus httpStatus = HttpStatus.resolve(
                Optional.ofNullable(statusCode)
                        .orElse(500) /* 如果为空则为500 */
        );
        return new ResponseEntity(httpStatus.value(), httpStatus.getReasonPhrase(), null);
    }
}
