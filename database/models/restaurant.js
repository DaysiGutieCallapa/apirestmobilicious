
const mongoose =require('../connect');
const Schema = mongoose.Schema;

const restaurantSchema = Schema({
    nombre: {
        type: String,
        required: [true, 'debe introducir nombre del restaurant']
    },
    nit: String,
    propietario: {
        type: String,//Schema.Types.ObjectId,
        ref: "Usuario"
        //required: [true, 'debe introducir el nombre del propietario completo']
    },
    calle: String,
    telefono: Number,
    log: Number,
    lat: Number,
    logo: String,
    fechaRegistro: {
        type: Date,
        dafault: Date.now()

    },
    fotoLugar: String
})

const restaurant = mongoose.model('Restaurant', restaurantSchema);

module.exports = restaurant;