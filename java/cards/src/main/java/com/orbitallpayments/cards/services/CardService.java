package com.orbitallpayments.cards.services;

import com.orbitallpayments.cards.domains.Card;
import com.orbitallpayments.cards.repositories.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    public Card save(Card card){
        return cardRepository.save(card);
    }

    public List<Card> findAll(){
        return (List<Card>) cardRepository.findAll();
    }

    public Optional<Card> findById(Long id) throws Exception{
        if(this.isExist(id)) {
            return cardRepository.findById(id);
        }else{
            throw new Exception();
        }
    }

    public Page<Card> findAllPage(Pageable page) {
        return (Page<Card>) cardRepository.findAll(page);
    }

    public Card updateById(Long id, Card cardUpdate) throws Exception {
        if(this.isExist(cardUpdate.getId())){
            var card = this.cardRepository.findById(id).get();

            card.setCardNumber(cardUpdate.getCardNumber());
            card.setAddress(cardUpdate.getAddress());
            card.setCity(cardUpdate.getCity());
            card.setEmbossName(cardUpdate.getEmbossName());
            card.setDocumentNumber(cardUpdate.getDocumentNumber());
            card.setMotherName(cardUpdate.getMotherName());
            card.setCustomerName(cardUpdate.getCustomerName());

            return cardRepository.save(card);
        }else{
            throw new Exception();
        }


    }

    public void deleteById(long id) throws Exception{
        if(this.isExist(id)){
            this.cardRepository.deleteById(id);
        }else{
            throw new Exception();
        }
    }

    //Verifica se existe um objeto do tio Card no bd
    public boolean isExist(long id){
        if(!this.cardRepository.findById(id).isEmpty()){
            return true;
        }else{
            return false;
        }
    }
}
