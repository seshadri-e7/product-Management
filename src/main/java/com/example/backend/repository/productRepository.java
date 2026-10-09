package com.example.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.models.product;


public interface productRepository extends JpaRepository <product,Integer>{

}
