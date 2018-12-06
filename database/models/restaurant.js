
const mongoose =require('../connect');
const Schema = mongoose.Schema;

const restaurantSchema = Schema({
    nombre: String,
    nit: String,
    propietario: String,
    calle: String,
    telefono: Number,
    log: Number,
    lat: Number,
    logo: String,
    fechaRegistro: {
        type: Date,
        dafault: Date.now()

    },
    fotlLugar: String
})

const restaurant = mongoose.model('Restaurant', restaurantSchema);

module.exports = restaurant;