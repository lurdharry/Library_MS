package com.lurdharry.library.book;


import com.lurdharry.library.dto.ResponseDTO;
import com.lurdharry.library.user.FeignClientConfig;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
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
            @RequestBody @NotEmpty List<String> bookIds
    );

    @PostMapping("/by-ids")
    ResponseDTO getBooksByIds(
            @RequestBody @NotEmpty List<String> bookIds
    );
}
