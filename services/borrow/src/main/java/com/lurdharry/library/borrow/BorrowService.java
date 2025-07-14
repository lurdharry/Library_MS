package com.lurdharry.library.borrow;

import com.lurdharry.library.book.BookClient;
import com.lurdharry.library.borrowline.BorrowLine;
import com.lurdharry.library.borrowline.BorrowLineRepository;
import com.lurdharry.library.exception.ResponseException;
import com.lurdharry.library.kafka.BorrowApprovedEvent;
import com.lurdharry.library.kafka.BorrowKafkaEventListener;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BorrowService {

    private final BorrowRepository borrowRepository;
    private final BorrowMapper mapper;
    private final BookClient bookClient;
    private final BorrowLineRepository borrowLineRepository;
    private final BorrowKafkaEventListener borrowKafkaEventListener;


    public BorrowOrderResponse getBorrowOrderById(String borrowId){
        return borrowRepository.findById(borrowId).map(mapper::toBorrowOrderResponse)
                .orElseThrow(() -> new ResponseException("Order not found with ID: " + borrowId, HttpStatus.NOT_FOUND));

    }

    public List<BorrowOrderResponse> getAllBorrowOrders(){
        return borrowRepository.findAll().stream()
                .map(mapper::toBorrowOrderResponse)
                .toList();
    }


    @Transactional
    public String orderBook(@Valid BorrowRequest request) {

        // persist borrow order
        var borrowOrder =  borrowRepository.save(mapper.toBorrowOrder(request));

        // Or if you need a reference-only entity
        var borrowOrderRef = new BorrowOrder();
        borrowOrderRef.setId(borrowOrder.getId());
        // persist borrow line for each book
        var lines = request.bookIds().stream()
                .map(bookId -> new BorrowLine(
                        null,
                        borrowOrderRef,
                        bookId
                        )
                )
                .toList();

        borrowLineRepository.saveAll(lines);

        borrowKafkaEventListener.onBorrowCreated(
                new BorrowRequest(borrowOrder.getId(),request.userId(),request.bookIds())
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

        // send notification
        borrowKafkaEventListener.onBorrowApproved(
                new BorrowApprovedEvent(request.orderId(),orderDetails)
        );


       return borrowRepository.save(orderDetails).getId();

    }

}
