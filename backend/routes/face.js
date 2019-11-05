var express = require('express');
var router = express.Router();

const wrap = require('express-async-wrap');
const models = require('../models');

router.post('/addResult',wrap(async (req, res) => {
  console.log(req.body);
  const id = await models.users.findOne({
    where:{
      userId: req.body.userId
    }
  })
  console.log(id.dataValues.id)
  let posOrNegg
  if(req.body.result > 0.2){
    posOrNegg = 1
  } else
    posOrNegg = 0
  console.log(req.body.result)
  console.log(String(req.body.result))
  const face = await models.face.create({
    posOrNeg: posOrNegg,
    result: String(req.body.result),
    userId: id.dataValues.id
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
  const id = await models.users.findOne({
    where:{
      userId: req.params.user
    }
  })
  console.log(id.dataValues.id)
  const result = await models.face.findAll({
    where: {
      userId: id.dataValues.id
    },
    offset: 0,
    limit: 20,
    order: [
      ['createdAt', 'DESC']
    ]
  });
  console.log(result);
  res.send(result);
}));

module.exports = router;
