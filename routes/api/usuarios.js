var express = require('express');
var router = express.Router();


const Usuario = require('../../database/models/usuario');


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
    
    if(/\w{3,6}(\s{1}\w{3,6})?/i.test(req.body.nombre) == false){
        
        res.status(500).json({
            msn:"no se cumple"
        });
        console.log('no paso el test');
        return;
    }
    
    

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
    let userData = {};

    Object.keys(req.body).forEach((key) => {
        userData[key] = req.body[key];
    })
    console.log(userData);
    
    Usuario.findByIdAndUpdate(idUser, userData ).exec((err, result) => {
          if(err){
              res.status(500).json({
                  error:err
              });
              return;
          }
          if(result){
              res.status(200).json({
                  message:"Usuario actulizado",
                  result: result
              })
          }
      })
});





module.exports = router;
