const mongoose =require('../connect');
const Schema = mongoose.Schema;

const detalleSchema = Schema({
    //idmenu: String,
    id_menu:{
        type:Schema.Types.ObjectId,
        ref:"Menu"
      },//aunque el  inge lo pone con un String
    //idorden: String,
    id_orden:{
        type:Schema.Types.ObjectId,
        ref:"Orden"
      },
    cantidad: Number
});

const detalle = mongoose.model('Detalle', detalleSchema);

module.exports = detalle;