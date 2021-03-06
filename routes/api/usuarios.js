var express = require('express');
var router = express.Router();
const bcrypt = require("bcrypt");
//const jwt = require('jsonwebtoken');
//const auth = require('./middleware/checkAuth');

const Usuario = require('../../database/models/usuario');
/*

const multer = require('multer');
const path = require('path');
const fs = require('fs');

const storage = multer.diskStorage({
    destination: function (res, file, cb) {
        try {
            fs.statSync('./uploads/');
        } catch (e) {
            fs.mkdirSync('./uploads/');
        }
        cb(null, './uploads/');
    },
    filename: (res, file, cb) => {
        cb(null, 'IMG-' + Date.now() + path.extname(file.originalname))
    }
})

const fileFilter = (req, file, cb) => {
    if (file.mimetype === 'image/png' || file.mimetype === 'image/jpeg') {
        return cb(null, true);
    }
    return cb(new Error('Solo se admiten imagenes png y jpg jpeg'));
}

const upload = multer({
    storage: storage,
    fileFilter: fileFilter,
    limits: {
        fileSize: 1024 * 1024 * 5
    }
})*/

/* GET Usuarioe. */
router.get('/', function (req, res, next) {

    Usuario.find().exec().then(docs => {
        if (docs.length == 0) {
            res.json({
                message: "No existen usuarios en la base de datos"
            })
        } else {
            res.json(docs);
        }
    }).catch(err => {
        res.json({
            error: err
        });
    })

});

router.post('/', function (req, res, next) {


    const datos = {
        nombre: req.body.nombre,
        ci: req.body.ci,
        email: req.body.email,
        //password: req.body.password,
        telefono: req.body.telefono,
        //avatar: req.body.avatar,
        tipo: req.body.tipo,
    };
    bcrypt.hash(req.body.password, 10, (err, hash) => {
        if (err) {
            return res.status(500).json({
                error: err
            });
        } else {
            datos.password = hash;
            var modelUsuario = new Usuario(datos);
            modelUsuario.save().then(result => {
                    res.json({
                        message: "Usuario insertado en la bd"
                    });
                })
                .catch(err => {
                    res.status(500).json({
                        error: err
                    })
                });


        }
    });

});
/*router.post('/login', (req, res, next) => {
    Usuario.find({
            email: req.body.email
        })
        .exec()
        .then(user => {
            if (user.length < 1) {
                return res.status(401).json({
                    message: "Auth failed"
                });
            }
            bcrypt.compare(req.body.password, user[0].password, (err, result) => {
                if (err) {
                    return res.status(401).json({
                        message: "Auth failed"
                    });
                }
                if (result) {
                    const token = jwt.sign({
                            email: user[0].email,
                            userId: user[0]._id
                        },
                        process.env.JWT_KEY || 'secret321', {
                            expiresIn: "1h"
                        }
                    );
                    return res.status(200).json({
                        message: "Auth successfull",
                        token: token
                    });
                }
                res.status(401).json({
                    message: "Auth failed"
                });
            });
        })
        .catch(err => {
            console.log(err);
            res.status(500).json({
                error: err
            });
        });
});
*/
router.patch('/:id', function (req, res, next) {
    let idUsuario = req.params.id;
    const datos = {};

    Object.keys(req.body).forEach((key) => {
        datos[key] = req.body[key];
    });
    console.log(datos);
    Usuario.findByIdAndUpdate(idUsuario, datos).exec()
        .then(result => {
            res.json({
                message: "Datos actualizados"
            });
        }).catch(err => {
            res.status(500).json({
                error: err
            })
        });
});

router.delete('/:id', function (req, res, next) {
    let idUsuario = req.params.id;

    Usuario.findByIdAndRemove(idUsuario).exec()
        .then(() => {
            res.json({
                message: "Usuario eliminado"
            });
        }).catch(err => {
            res.status(500).json({
                error: err
            });
        });


});
/*router.delete('/:id', function (req, res, next) {
    let idUsuario = req.params.id;

    Usuario.remove({_id:idUsuario}).exec((err,result)=>{
        if(err){
            res.status(8000).json({
                error: err
            });
            return;
        }
        if(result) {
            res.status(200).json({
                message:"Usuario eliminado",
                result:result
            })
        }
    })


});*/


module.exports = router;
