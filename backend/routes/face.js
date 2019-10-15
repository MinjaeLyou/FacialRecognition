var express = require('express');
var router = express.Router();

const wrap = require('express-async-wrap');
const models = require('../models');

router.post('/addResult',wrap(async (req, res) => {
  //console.log(req.body);
  const face = await models.face.create({
    posOrNeg: req.body.posOrNeg,
    result: req.body.result
  });
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
