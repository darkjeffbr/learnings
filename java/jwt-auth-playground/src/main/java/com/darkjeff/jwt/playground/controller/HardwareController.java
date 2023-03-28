package com.darkjeff.jwt.playground.controller;

import com.darkjeff.jwt.playground.model.entity.Hardware;
import com.darkjeff.jwt.playground.model.entity.User;
import com.darkjeff.jwt.playground.model.repository.HardwareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hardwares")
@RequiredArgsConstructor
public class HardwareController {

  private final HardwareRepository hardwareRepository;

  @GetMapping
  public ResponseEntity<List<Hardware>> list() {
    return ResponseEntity.ok(hardwareRepository.findAll());
  }

}
