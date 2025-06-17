package com.lurdharry.library.borrowline;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BorrowLineService {
    private final BorrowLineRepository repository;
    private final BorrowLineMapper mapper;


    public String saveBorrowLine(BorrowLineRequest request){
        var order = mapper.toBorrowLine(request);

        return  repository.save(order).getId();
    }
}
