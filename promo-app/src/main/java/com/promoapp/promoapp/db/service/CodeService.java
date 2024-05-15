package com.promoapp.promoapp.db.service;

import com.promoapp.promoapp.db.entity.Code;
import com.promoapp.promoapp.db.repository.CodeRepository;
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

    public List<Code> getAllCodes() {
        return codeRepository.findAll();
    }

    public boolean addCode(Code code) {
        try {
            codeRepository.save(code);
            return true;
        } catch (Exception ignored) {
            return false;

        }
    }

    public Code getCodeDetails(String code) {
        return codeRepository.findByCode(code);
    }

    public static boolean checkCode(String code) {
        String regex = "^[a-zA-Z0-9]{3,23}$";
        return code.matches(regex);
    }
}
