package com.lurdharry.library.borrow;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lurdharry.library.book.BookBorrowRequest;
import com.lurdharry.library.book.BookClient;
import com.lurdharry.library.book.BookRequest;
import com.lurdharry.library.borrowline.BorrowLine;
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

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BorrowService {

    private final UserClient userClient;
    private final ObjectMapper objectMapper;
    private final BorrowRepository borrowRepository;
    private final BorrowMapper mapper;
    private final BorrowLineService borrowLineService;
    private final BookClient bookClient;



    public String borrowBook(@Valid BorrowRequest request) {
        var user = getVerifiedUser(request.userId());

        // borrow the book from book service

        // persist borrow order
        var borrowOrder =  borrowRepository.save(mapper.toBorrowOrder(request));


        // persist orderline
        for (BookRequest bookRequest: request.books()){
            borrowLineService.saveBorrowLine(
                    new BorrowLineRequest(
                            null,
                            bookRequest.bookId(),
                            borrowOrder.getId()
                    )
            );
        }

        return borrowOrder.getId();
    }


    public void updateBorrowStatus(@Valid StatusRequest request) {
      BorrowOrder borrowOrder=  borrowRepository.findById(request.borrowId()).orElseThrow(
              () -> new ResponseException("Can not find Order in DB",HttpStatus.NOT_FOUND)
      );

      System.out.println("borrowOrder"+borrowOrder);

//      update book details using the book ids from the orderlines
        List<BookBorrowRequest> borrowRequestList = borrowOrder.getBorrowLines().stream().map(mapper::toBookBorrowrequest).toList();

        System.out.println("borrowRequestList"+borrowRequestList);

        bookClient.processBook(borrowRequestList);

        borrowOrder.approve();

    }





    private UserResponse getVerifiedUser(String userId) {
        ResponseDTO response = userClient.getUserById(userId);

        return Optional.ofNullable(response.data())
                .map(data -> objectMapper.convertValue(data, UserResponse.class))
                .orElseThrow(() -> new ResponseException("User not found with ID: " + userId, HttpStatus.NOT_FOUND));
    }


}
