var express = require('express');
var router = express.Router();


const Usuario = require('../../database/models/ususario');

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
router.post('/', function(req, res, next) {
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

module.exports = router;