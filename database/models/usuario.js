const mongoose =require('../connect');
const Schema = mongoose.Schema;

const usuarioSchema = Schema({
    nombre: String,
    ci: String,
    email: String,
    password: String,
    telefono: Number,
    log: Number,
    lat: Number,
    fechaRegistro: {
        type: Date,
        dafault: Date.now()

    },
    fotlLugar: String
    //tipo de usuario
})

const usuario = mongoose.model('Usuario', usuarioSchema);

module.exports = usuario;