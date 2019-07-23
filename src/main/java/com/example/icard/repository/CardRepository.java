package com.example.icard.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.icard.model.Card;

public interface CardRepository extends MongoRepository<Card, String>  {

	Optional<Card> findByCardNumber(String cardNumber);
}
