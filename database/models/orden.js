const mongoose = require('../connect');
const Schema = mongoose.Schema;

cont ordenSchema = Schema({
  idCliente:Schema.Types.ObjectId,
  lugarEnvio:[Number],
  idRestaurant:Schema.Types.ObjectId,
  telefono:Number,
  idMenu:[Schema.Types.ObjectId],
  cantidad:[Number],
  fechaRegistro:{
    type:Date,
    default: Date.now()
  },
  pagoTotal:String
})

const Orden = mongoose.model("Orden",OrdenSchema);}

module.exports = Orden;
