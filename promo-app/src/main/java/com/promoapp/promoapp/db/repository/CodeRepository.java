package com.promoapp.promoapp.db.repository;

import com.promoapp.promoapp.db.entity.Code;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeRepository extends JpaRepository<Code, Long> {
    Code findByCode(String code);
}
