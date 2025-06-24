package com.lurdharry.library.borrow;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lurdharry.library.book.BookClient;
import com.lurdharry.library.borrowline.BorrowLineRequest;
import com.lurdharry.library.borrowline.BorrowLineService;
import com.lurdharry.library.dto.ResponseDTO;
import com.lurdharry.library.exception.ResponseException;
import com.lurdharry.library.user.UserClient;
import com.lurdharry.library.user.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BorrowService {

    private final UserClient userClient;
    private final ObjectMapper objectMapper;
    private final BorrowRepository borrowRepository;
    private final BorrowMapper mapper;
    private final BookClient bookClient;
    private final BorrowLineService borrowLineService;



    public String orderBook(@Valid BorrowRequest request) {
//        var user = getVerifiedUser(request.userId());

        // borrow the book from book service
        var borrowedBook = bookClient.borrowBook(request.books());

        // persist borrow order
        var borrowOrder =  borrowRepository.save(mapper.toBorrowOrder(request));


        // persist borrow line for each book
        for (String bookRequest: request.books()){
            borrowLineService.saveBorrowLine(
                    new BorrowLineRequest(
                            null,
                            borrowOrder.getId(),
                            bookRequest
                    )
            );
        }

        // send order notification --> notification -ms


        return borrowOrder.getId();

    }






    private UserResponse getVerifiedUser(String userId) {
        ResponseDTO response = userClient.getUserById(userId);

        return Optional.ofNullable(response.data())
                .map(data -> objectMapper.convertValue(data, UserResponse.class))
                .orElseThrow(() -> new ResponseException("User not found with ID: " + userId, HttpStatus.NOT_FOUND));
    }
}
