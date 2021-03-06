package com.orbitallpayments.cards.controllers;


import com.orbitallpayments.cards.domains.Card;
import com.orbitallpayments.cards.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cards")
public class CardController {

    @Autowired
    private CardService cardService;


    @PostMapping
    public ResponseEntity<Card> save(@RequestBody Card card){
        Card savedCard = cardService.save(card);

        return new ResponseEntity(savedCard, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Card>> findAll(){
        List<Card> cards = cardService.findAll();
        return ResponseEntity.ok(cards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Card> findById(@PathVariable Long id){
       try {
           Optional<Card> fetchedCard = cardService.findById(id);
           return new ResponseEntity<>(fetchedCard.get(), HttpStatus.OK);
       }catch (Exception e){
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
         }


    @GetMapping("/paginationAndSorting")
    public ResponseEntity<Page<Card>> findAllPagination(@PageableDefault(page = 0, size = 2, sort = "id", direction = Sort.Direction.ASC)Pageable page){
        Page<Card> cards = cardService.findAllPage(page);

        return new ResponseEntity(cards, HttpStatus.OK);
    }



    @PutMapping("/{id}")
    public ResponseEntity<Card> updateById(@PathVariable(value = "id")long id, @RequestBody Card card){
     try {
         Card cardUp = this.cardService.updateById(id, card);
         return new ResponseEntity<>(cardUp, HttpStatus.OK);
     }catch (Exception e){
         return new ResponseEntity<>(HttpStatus.NOT_FOUND);
     }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Card> deleteById(@PathVariable(value = "id")long id){
        try {
            this.cardService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
