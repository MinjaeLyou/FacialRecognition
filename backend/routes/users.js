var express = require('express');
var router = express.Router();

const wrap = require('express-async-wrap');
const models = require('../models');

/* GET users listing. */
router.get('/', function(req, res, next) {
  res.send('respond with a resource');
});

router.get('/getUser/:id', wrap(async (req, res) => {
  console.log(req.params.id)
  const user = await models.users.findOne({
    where: {
      userId: req.params.id
    }
  });
  res.send(user);
}));

router.post('/login', wrap(async (req, res) => {
  console.log(req.body);
  console.log(req.body.uid);
  const user = await models.users.findOne({ // 유저 검색
      where: {
          userId: req.body.userId,
          password: req.body.password
      }
  });
  console.log(user);
  if(user) {
          delete req.body.password;
          res.send({ // 로그인 결과 response
              result: true,
              data: user
          });
      } else {
          res.send({
              result: false
          });
      }
  
}));

module.exports = router;
