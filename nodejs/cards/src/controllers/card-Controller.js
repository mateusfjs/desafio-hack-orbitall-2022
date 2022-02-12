const neDB = require('../configurations/database')
const api = {}


api.save = (request, response) => {
    const canonical = request.body

    neDB.insert(canonical, (exeception, card) => {
     if(exeception) {
         const setence = 'Falha a salvar'
         console.error(setence, exeception)

         response.status(exeception.status | 400)
         response.json( {'mensagem': setence} )
     }   

     console.log('Card salvo com sucesso', card)
     response.status(201)
     response.json(card)

    })


}


api.findAll = (request, response) => {
    neDB.find({}).sort({ name: 1}).exec((exeception, cards) => {
        if(exeception) {
            const setence = 'erro ao listar'
            console.error(setence, exeception)

            response.status(exeception.status | 400)
            response.json({ 'mensagem' : setence})
        }
        console.log('Lista de cards', cards)
        response.json(cards)

    })
}


api.findOne = (request, response) => {
    neDB.findOne({request}).exec((exeception, card) => {
        if(exeception) {
            const setence = 'Card não encontrado'
            response.status(exeception.status | 400)
            console.error(setence, exeception)

        }
    console.log('Card', card)
    response.json(card)
    })

}

api.remove = (request, response) => {
    neDB.remove({_id: request._id}, function ( exeception, cardRemoved){
        if(exeception){
            const setence = 'Card não removido'
            response.status(exeception.status | 500)
            console.error(setence, exeception)

        }
        response.Status(200);

    })


}


api.update = (request, response) => {

    let cardId = request._id;

    neDB.update( { _id:  cardId}, request.body, {}, function (exeception, cardReplaced, card) {
        if(exeception){

            const setence = 'Card não foi atualizado'
            response.status(exeception.status | 500)
            console.error(setence, exeception)
        }

        console.log('Card atualizado com sucesso', card)
        response.status(200)
        response.json(card)

    })


}

api.pageable = (request, response) => {

    neDB.find({}).sort({ _id: 1 }).skip(1).limit(3).exec(function (exeception, cards) {
        if(exeception){
            
        const setence = 'erro ao paginar'
        response.status(exeception.status | 500)
        console.error(setence, exeception)
        
    }

    response.status(200)
    response.json(cards)

    })
  

}






module.exports = api