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

module.exports = router;
