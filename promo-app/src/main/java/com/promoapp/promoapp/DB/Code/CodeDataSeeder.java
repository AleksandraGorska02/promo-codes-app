package com.promoapp.promoapp.DB.Code;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class CodeDataSeeder implements CommandLineRunner {
    private final CodeRepository codeRepository;

    public CodeDataSeeder(CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }
    private void seedData(CodeRepository codeRepository) {
        Code code1 = new Code();
        code1.setCode("Code1");
        code1.setDiscount(10.0);
        code1.setCurrency("USD");
        code1.setPercentage(true);
        code1.setMaxUses(100);
        code1.setExpirationDate("2024-12-31");

        Code code2 = new Code();
        code2.setCode("Code2");
        code2.setDiscount(20.0);
        code2.setCurrency("USD");
        code2.setExpirationDate("2024-05-31");
        code2.setMaxUses(3);

        Code code3 = new Code();
        code3.setCode("Code3");
        code3.setDiscount(30.0);
        code3.setCurrency("EUR,USD");
        code3.setExpirationDate("2024-08-31");
        code3.setMaxUses(1);

        Code code4 = new Code();
        code4.setCode("Code4");
        code4.setDiscount(40.0);
        code4.setCurrency("EUR");
        code4.setExpirationDate("2024-01-31");
        code4.setMaxUses(1);




        codeRepository.save(code1);
        codeRepository.save(code2);
        codeRepository.save(code3);
        codeRepository.save(code4);
    }
    @Override
    public void run(String... args) throws Exception {
        seedData(codeRepository);
    }
}
