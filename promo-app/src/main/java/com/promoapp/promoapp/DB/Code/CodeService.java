package com.promoapp.promoapp.DB.Code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodeService {
    private final CodeRepository codeRepository;

    @Autowired
    public CodeService(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }
    public List < Code > getAllCodes() {
        return codeRepository.findAll();
    }
    public void addCode(Code code) {

        codeRepository.save(code);
    }
}
