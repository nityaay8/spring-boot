package com.n9.service;

import com.n9.model.TinyUrlData;
import com.n9.repository.BitlyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BitylService {

    @Autowired
    private BitlyRepository bitlyRepository;

    public List<TinyUrlData> getTinyUrlInfo() {
        return bitlyRepository.getTinyUrlInfo();
    }
}
