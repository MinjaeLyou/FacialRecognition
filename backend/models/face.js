module.exports = (sequelize, DataTypes) => {
    const face = sequelize.define('face', {
      index: {
        type: DataTypes.INTEGER(11),
        autoIncrement: true,
        primaryKey: true,
        comment: '인덱스'
      },
      posOrNeg: {
        type: DataTypes.BOOLEAN,
        allowNull: false,
        comment: '긍정/부정'
      },
      result: {
        type: DataTypes.FLOAT(10),
        allowNull: false,
        comment: '인식 척도'
      }
    }, {
      tableName: 'face',
      comment: '인식 결과',
      charset: 'utf8',
      collate: 'utf8_general_ci'
    });
    face.associate = function (models) {
      face.belongsTo(models.user, { foreignKey: { name: 'userId', allowNull: false}});
    };
    return face;
  };
  