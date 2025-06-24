package com.lurdharry.library.borrowline;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BorrowLineService {
    private final BorrowLineRepository borrowLineRepository;
    private final BorrowLineMapper borrowLineMapper;

    public void saveBorrowLine(BorrowLineRequest borrowLineRequest){
        var orderLine = borrowLineMapper.toOrderLine(borrowLineRequest);
        borrowLineRepository.save(orderLine);
    }
}
