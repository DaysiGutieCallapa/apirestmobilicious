const mongoose =require('../connect');
const Schema = mongoose.Schema;

const usuarioSchema = Schema({
    nombre:{
        type: String,
        require: [true, 'debe poner un nombre'],
        //match: /^([A-Za-zÁÉÍÓÚñáéíóúÑ]{0}?[A-Za-zÁÉÍÓÚñáéíóúÑ\']+[\s])+([A-Za-zÁÉÍÓÚñáéíóúÑ]{0}?[A-Za-zÁÉÍÓÚñáéíóúÑ\'])+[\s]?([A-Za-zÁÉÍÓÚñáéíóúÑ]{0}?[A-Za-zÁÉÍÓÚñáéíóúÑ\'])?$/i,
        ///^([A-Z][a-z]+)$/i,
    },
    ci: {
        type: String,
        required: [true, 'debe poner el ci']
    },
    email: {
        type:String,
        require: ['debe poner email'],
        //match: /^(([^<>()\[\]\.,;:\s @\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i,
    },
    password: String,
    telefono: String, 
    //log: Number,
    //lat: Number,
    fechaRegistro: {
        type: Date,
        default: Date.now()

    },
    fotoLugar: String, //o como dice el aux, es el AVATAR
    tipo:String //tipo de usuario si es el cliente , dueño, etc
})

const usuario = mongoose.model('Usuario', usuarioSchema);

module.exports = usuario;