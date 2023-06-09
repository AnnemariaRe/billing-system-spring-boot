package ru.nexign.cdr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nexign.cdr.generator.cdr.CdrGenerator;

import java.io.IOException;

@Service
public class CdrService {
    private final CdrGenerator generator;

    @Autowired
    public CdrService(CdrGenerator generator) {
        this.generator = generator;
    }

    public void generateCdrFile(int month, int year) {
        try {
            generator.generateCdrFile(month, year);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
