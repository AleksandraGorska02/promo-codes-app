package com.promoapp.promoapp.controller;

import com.promoapp.promoapp.db.entity.Code;
import com.promoapp.promoapp.db.service.CodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.promoapp.promoapp.db.service.CodeService.checkCode;

@RestController
@RequestMapping("/code")
public class CodeController {
    private final CodeService codeService;

    @Autowired
    public CodeController(CodeService codeService) {
        this.codeService = codeService;
    }

    @GetMapping("/all")
    public List<Code> getAllCodes() {
        return codeService.getAllCodes();
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCode(@RequestBody Code code) {
        if (code.getCode() == null || code.getDiscount() == 0 || code.getCurrency() == null || code.getExpirationDate() == null || code.getMaxUses() == 0) {
            return new ResponseEntity<>("All fields must be filled", HttpStatus.BAD_REQUEST);}
            if (!checkCode(code.getCode())) {

                return new ResponseEntity<>("code must be 3-24 characters long and contain only letters and numbers", HttpStatus.BAD_REQUEST);

            }
            if (code.isPercentage() && code.getDiscount() > 100) {
                return new ResponseEntity<>("The discount cannot be greater than 100%", HttpStatus.BAD_REQUEST);
            }
            if (code.getDiscount() < 0) {
                return new ResponseEntity<>("The discount cannot be negative", HttpStatus.BAD_REQUEST);
            }
            if (codeService.addCode(code)) {
                return new ResponseEntity<>("code added successfully", HttpStatus.OK);

            }
            return new ResponseEntity<>("Code already exists", HttpStatus.BAD_REQUEST);


        }

    @GetMapping("/details/{code}")
    public ResponseEntity<?> getCodeDetails(@PathVariable String code) {
        Code codeDetails = codeService.getCodeDetails(code);
        if (codeDetails == null) {
            return new ResponseEntity<>("Code not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(codeDetails, HttpStatus.OK);
    }

}

