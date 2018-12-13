const mongoose = require("mongoose");

mongoose.connect("mongodb://localhost:27017/PFF", { 
    //useNewUrlParser: 
     
}).then(()=>{
    console.log('conexion exitosa');
}).catch((err)=>{
    console.log('error',err);
});

module.exports = mongoose;