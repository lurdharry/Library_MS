package com.lurdharry.library.borrow;

import com.lurdharry.library.book.BookClient;
import com.lurdharry.library.book.BookResponse;
import com.lurdharry.library.borrowline.BorrowLine;
import com.lurdharry.library.borrowline.BorrowLineRequest;
import com.lurdharry.library.borrowline.BorrowLineService;
import com.lurdharry.library.dto.ResponseDTO;
import com.lurdharry.library.exception.ResponseException;
import com.lurdharry.library.kafka.BorrowOrderConfirmation;
import com.lurdharry.library.kafka.BorrowStatusConfirmation;
import com.lurdharry.library.kafka.KafkaBorrowProducer;
import com.lurdharry.library.user.UserClient;
import com.lurdharry.library.user.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BorrowService {

    private final UserClient userClient;
    private final BorrowRepository borrowRepository;
    private final BorrowMapper mapper;
    private final BookClient bookClient;
    private final BorrowLineService borrowLineService;
    private final KafkaBorrowProducer kafkaBorrowProducer;


    public BorrowOrderResponse getBorrowOrderById(String borrowId){
        return borrowRepository.findById(borrowId).map(mapper::toBorrowOrderResponse)
                .orElseThrow(() -> new ResponseException("Order not found with ID: " + borrowId, HttpStatus.NOT_FOUND));

    }

    public List<BorrowOrderResponse> getAllBorrowOrders(){
        return borrowRepository.findAll().stream().map(mapper::toBorrowOrderResponse).collect(Collectors.toList());
    }


    @Transactional
    public String orderBook(@Valid BorrowRequest request) {

        // persist borrow order
        var borrowOrder =  borrowRepository.save(mapper.toBorrowOrder(request));

        // persist borrow line for each book
        for (String bookRequest: request.bookIds()){
            borrowLineService.saveBorrowLine(
                    new BorrowLineRequest(
                            null,
                            borrowOrder.getId(),
                            bookRequest
                    )
            );
        }

        // send order notification --> notification -ms
        var user = getVerifiedUser(request.userId());
        var books = getBooksByIds(request.bookIds());
        String orderRef = String.format("REF"+borrowOrder.getId()).toUpperCase();

        kafkaBorrowProducer.sendBorrowOrderConfirmation(
                new BorrowOrderConfirmation(
                        orderRef,
                        user,
                        books
                )
        );

        return borrowOrder.getId();

    }


    @Transactional
    public String approveBorrowStatus(StatusUpdateRequest request) {

        // first get borrow order and then get the list of borrow order lines to get the book ids
        var orderDetails = borrowRepository.findById(request.orderId())
                .orElseThrow(() -> new ResponseException("Order not found with ID: " + request.orderId(), HttpStatus.NOT_FOUND));

        // get bookIds from orderLines
        var bookIds = orderDetails.getBorrowLines().stream().map(BorrowLine::getBookId).toList();


        // borrow the book from book service  to be handles by admin to update the books count
         bookClient.borrowBook(bookIds);

        // approve order
        orderDetails.approve(request.adminId(), LocalDateTime.now());

        String orderRef = String.format("REF"+orderDetails.getId()).toUpperCase();
        // send order notification --> notification -ms
        var user = getVerifiedUser(orderDetails.getUserId());
        var books = getBooksByIds(bookIds);
        kafkaBorrowProducer.sendStatusConfirmation(
                new BorrowStatusConfirmation(
                        orderRef,
                        user,
                        books,
                        ApprovalStatus.APPROVED
                )
        );


       return borrowRepository.save(orderDetails).getId();

    }


    private UserResponse getVerifiedUser(String userId) {
        ResponseDTO<UserResponse> response = userClient.getUserById(userId);
        return Optional.ofNullable(response.data())
                .orElseThrow(() -> new ResponseException("User not found with ID: " + userId, HttpStatus.NOT_FOUND));
    }

    private List<BookResponse> getBooksByIds(List<String> bookIds){
        ResponseDTO<List<BookResponse>> response = bookClient.getBooksByIds(bookIds);
        return Optional.ofNullable(response.data()).orElseThrow(
                ()->new ResponseException("Unable to get book details",HttpStatus.NOT_FOUND)
        );
    }
}
