package com.darkjeff.jwt.playground.model.repository;

import com.darkjeff.jwt.playground.model.entity.Hardware;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HardwareRepository extends JpaRepository<Hardware, Long> {
}
