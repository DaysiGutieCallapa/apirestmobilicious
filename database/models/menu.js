const mongoose = require('../connect');
const Schema = mongoose.Schema;

const menuSchema = Schema({
  nombre:String,
  precio:Number,
  descripcion:String,
  fechaRegistro:{
    type: Date,
    default: Date.now()
  },
  fotografia_del_producto:String,
  id_restaurant:{
    type:Schema.Types.ObjectId,
    ref:"Restaurant"
  } //aunque el  inge lo pone con un String
})

const menu = mongoose.model('Menu', menuSchema);

module.exports = menu;
