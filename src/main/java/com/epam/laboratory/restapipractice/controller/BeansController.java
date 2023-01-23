package com.epam.laboratory.restapipractice.controller;

import com.epam.laboratory.restapipractice.customannotations.ClientBean;
import com.epam.laboratory.restapipractice.customannotations.LogInvocation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/clientBeans")
@Tag(name = "Бины", description = "Возвращает список бинов")
@ClientBean
public class BeansController {
    @Autowired
    @ClientBean
    private List<Object> clientBeans;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Бины", description = "Возвращает список бинов")
    @LogInvocation
    public ResponseEntity<?> getAllBeans() {
        List<String> classNames = clientBeans.stream()
                .map(Object::getClass)
                .map(Class::getName)
                .map(s -> s.substring(s.lastIndexOf(".") + 1))
                .collect(Collectors.toList());
        return new ResponseEntity<>(classNames, HttpStatus.OK);
    }
}
