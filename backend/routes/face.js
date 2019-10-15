var express = require('express');
var router = express.Router();

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

module.exports = router;
