package com.lurdharry.library.borrow;

import com.lurdharry.library.book.BookClient;
import com.lurdharry.library.book.BookResponse;
import com.lurdharry.library.borrowline.BorrowLine;
import com.lurdharry.library.dto.ResponseDTO;
import com.lurdharry.library.exception.ResponseException;
import com.lurdharry.library.kafka.BorrowOrderConfirmation;
import com.lurdharry.library.kafka.BorrowStatusConfirmation;
import com.lurdharry.library.kafka.KafkaBorrowProducer;
import com.lurdharry.library.user.UserClient;
import com.lurdharry.library.user.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BorrowEventListener {
    private final UserClient userClient;
    private final BookClient bookClient;
    private final KafkaBorrowProducer producer;

    @EventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void  onBorrowCreated(BorrowRequest request){

        var user = getVerifiedUser(request.userId());
        var books = getBooksByIds(request.bookIds());

        String orderRef = ("REF" + request.id()).toUpperCase();

        producer.sendBorrowOrderConfirmation(
                new BorrowOrderConfirmation(
                        orderRef,
                        user,
                        books
                )
        );
    }


    @EventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void  onBorrowApproved(StatusUpdateRequest request,BorrowOrder order){
        // get bookIds from orderLines
        var bookIds = order.getBorrowLines().stream().map(BorrowLine::getBookId).toList();

        // borrow the book from book service  to be handles by admin to update the books count
        bookClient.borrowBook(bookIds);

        var user = getVerifiedUser(order.getUserId());
        var books = getBooksByIds(bookIds);

        String orderRef = ("REF" + request.orderId()).toUpperCase();

        producer.sendStatusConfirmation(
                new BorrowStatusConfirmation(
                        orderRef,
                        user,
                        books,
                        ApprovalStatus.APPROVED
                )
        );
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
