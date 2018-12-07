var express = require('express');
var router = express.Router();


const Usuario = require('../../database/models/ususario');
const Restaurant =require('../../database/models/restaurant');



//USUARIO

/* GET users. */

router.get('/', function(req, res, next) {
  Usuario.find().exec()
  .then(docs => {
      if(docs.length == 0){
          res.json({
              message: "no hay usuarios en la BD"
          })
      }else{
          res.json({
              count: docs.length,
              result: docs,
              request: {
                  type: "GET"
              }

          })
      }
  }).catch(err=>{
      res.status(500).json({
          error:err
      });
  });
});

/* save users. */
router.post('/', function (req, res, next) {
    let usuarioData = {
        nombre: req.body.nombre,
        ci: req.body.ci,
        email: req.body.email,
        password: req.body.password,
        telefono: req.body.telefono,
        log: req.body.log,
        lat: req.body.lat,
        foto: req.body.foto,

    }
    let data = new Usuario(usuarioData);
    data.save()
        .then(docs => {
            console.log(docs);
            res.json({
                message: "usuario guardado",
                doc: docs
            })
        }).catch(err=>{
            console.log(err)
            res.status(500).json({
                error:err
            });
        });
  });
  router.delete('/:id', function (req, res, next) {
      let idUser = req.params.id;
      Usuario.remove({_id:idUser}).exec((err, result) => {
            if(err){
                res.status(500).json({
                    error:err
                });
                return;
            }
            if(result){
                res.status(200).json({
                    message:"Usuario eliminado",
                    result: result
                })
            }
        })
  });

  router.patch('/:id', function (req, res, next) {
    let idUser = req.params.id;
    let userData = {}
    Object.keys(req.body).forEach((key) => {
        userData[key] = req.body[key];
    })
    console.log(userData);
    return;
    Usuario.findByIdAndUpdate(idUser, ).exec((err, result) => {
          if(err){
              res.status(500).json({
                  error:err
              });
              return;
          }
          if(result){
              res.status(200).json({
                  message:"Usuario eliminado",
                  result: result
              })
          }
      })
});












//RESTAURANT
//GET


router.get('/', function(req, res, next) {
  Restaurant.find().exec()
  .then(docs => {
      if(docs.length == 0){
          res.json({
              message: "no hay restaurantes en la BD"
          })
      }else{
          res.json({
              count: docs.length,
              result: docs,
              request: {
                  type: "GET"
              }

          })
      }
  }).catch(err=>{
      res.status(500).json({
          error:err
      });
  });
});

//POST

/* save restaurant. */
router.post('/', function(req, res, next) {
    let restaurantData ={
        nombre: req.body.nombre,
        nit: req.body.nit,
        propietario: req.body.propietario,
        calle: req.body.calle,
        telefono: req.body.telefono,
        log: req.body.log,
        lat: req.body.lat,
        fechaRegistro: req.body.fechaRegistro,
        type: req.body.type,
        fotoLugar: req.body.fotoLugar,
    }
    let data = new Restaurant(restaurantData);
    data.save()
        .then(docs => {
            console.log(docs);
            res.json({
                message: "restaurant  guardado",
                doc: docs
            })
        }).catch(err=>{
            console.log(err)
            res.status(500).json({
                error:err
            });
        });
  });


//DELETE

router.delete('/:id', function(req, res, next){
      let idRestaurant = req.params.id;

     Restaurant.remove({_id:idRestaurant}).exec((err,result)=>{
        if(err){
          res.status(500).json({
               error: err
          });
          return;
        }
        if (result) {
            res.status(200).json({
               message:"Restaurant eliminado",
               result:result
            })
        }
   })
});

//PATCH

router.patch('/:id', function(req, res, next){
      let idRestaurant = req.params.id;
      let userData = {}
      Object.keys(req.body).forEach((key)=>{
          userData[key]  = req.body[key];
      })


     Restaurant.findByIdAndUpdate(idRestaurant, userData).exec((err,result)=>{
        if(err){
          res.status(500).json({
               error: err
          });
          return;
        }
        if (result) {
            res.status(200).json({
               message:"Se actualizaron los Datos",
            })
        }
   })
});

//PUT



//ORDENES________----------------------------


router.post('/', function(req, res, next) {
    let ordenData = {
        nombre: req.body.nombre,
        nit: req.body.nit,
        propietario: req.body.propietario,
        calle: req.body.calle,
        telefono: req.body.telefono,
        log: req.body.log,
        lat: req.body.lat,
        fechaRegistro: req.body.fechaRegistro,
        type: req.body.type,
        fotoLugar: req.body.fotoLugar,
    }
    let data = new Restaurant(ordenData);
    data.save()
        .then(docs => {
            console.log(docs);
            res.json({
                message: "restaurant  guardado",
                doc: docs
            })
        }).catch(err=>{
            console.log(err)
            res.status(500).json({
                error:err
            });
        });
  });























module.exports = router;
