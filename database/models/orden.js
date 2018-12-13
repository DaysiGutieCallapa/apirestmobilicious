const mongoose = require('../connect');
const Schema = mongoose.Schema;

const ordenSchema = Schema({

  id_menu:[{
    type:Schema.Types.ObjectId,
    ref:"Menu"
  }],//aunque el  inge lo pone con un String
  id_restaurant:{
    type:Schema.Types.ObjectId,
    ref:"Restaurant"
  },//aunque el  inge lo pone con un String,aparte que ya no lo pone este atributo id_restaurant
  cantidad:[Number],//el inge tampoco lo pone como abributo
  id_usuario:{
    type:Schema.Types.ObjectId,
    ref:"Usuario"
  }, //aunque el  inge lo pone con un String
  calle: String, // xsi acaso lo puso el inge,xq dice q latitud y log es muy para robot en mi caso lugar de envio
  lugarEnvio: [Number],
  pagoTotal:Number,//aunque el inge lo pone un Number
  fechaRegistro:{
    type:Date,
    default: Date.now()
  }
});

const Orden = mongoose.model("Orden",ordenSchema);

module.exports = Orden;
