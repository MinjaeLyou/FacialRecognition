var express = require('express');
var router = express.Router();

const wrap = require('express-async-wrap');
const models = require('../models');

router.post('/addResult',wrap(async (req, res) => {
  console.log(req.body);
  //const uid = req.body.userId
  const id = await models.users.findOne({
    where:{
      userId: req.body.userId
    }
  })
  console.log(id.dataValues.id)
  let posOrNegg
  if(req.body.result > 0.3){
    posOrNegg = 1
  } else
    posOrNegg = 0
  console.log(req.body.result)
  console.log(String(req.body.result))
  const face = await models.face.create({
    posOrNeg: true,
    result: String(req.body.result),
    userId: id.dataValues.id
  });
  console.log("222")
  if(face){
    res.send({
      result: true,
      data: face
    });
  } else
    res.send({
      result: false,
      data: face
    });
}));

router.get('/getAllResult/:user', wrap(async (req, res) => {
  console.log(req.params.user)
  const result = await models.face.findAll({
    where: {
      userId: req.params.user
    }
  });
  res.send(result);
}));

module.exports = router;
