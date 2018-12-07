var express = require('express');
var router = express.Router();


const Orden = require('../../database/models/orden');



//ORDENES________----------------------------


router.get('/', function(req, res, next) {

    Orden.find().exec().then(docs => {
      if (docs.length == 0){
        res.json({
          message: "No se encontro en la base de datos"
        })
      } else{
        res.json(docs);
      }
    }).catch(err => {
      res.json({
        error: err
      });
    })
    
  });  
  
router.post('/', function(req, res, next) {
    const datos = {
      id_menu: req.body.id_Menu,//array
      id_restaurant: req.body.id_restaurant,
      cantidad: req.body.cantidad,//array
      id_usuario: id_restaurant,
      calle: req.body.calle,
      lugarEnvio: req.body.lugarEnvio,//array
      pagoTotal: req.body.pagoTotal     
    };

    var modelMenu = new Orden(datos);
    modelMenu.save().then(
      res.json({
        message: "Menu insertado en la bd"
      })
    ).catch(err =>{
      res.status(500).json({
        error: err
      })
    });
  });

  module.exports = router;