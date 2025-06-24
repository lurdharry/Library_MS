package com.lurdharry.library.book;


import com.lurdharry.library.dto.ResponseDTO;
import com.lurdharry.library.user.FeignClientConfig;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "book-service",
        url = "${application.config.book-url}",
        configuration = FeignClientConfig.class,
        contextId = "bookServiceClient"
)
public interface BookClient {

    @PostMapping("/borrow")
    ResponseDTO borrowBook(
            @RequestBody @Valid List<String> request
    );
}
