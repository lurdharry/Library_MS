package com.lurdharry.library.book;

import com.lurdharry.library.dto.ResponseDTO;
import com.lurdharry.library.user.FeignClientConfig;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "book-service",
        url = "${application.config.book-url}",
        configuration = FeignClientConfig.class
)
public interface BookClient {

    @PostMapping("/borrow")
     ResponseEntity<ResponseDTO> processBook(
            @RequestBody @Valid List<BookBorrowRequest> request
    );

}
