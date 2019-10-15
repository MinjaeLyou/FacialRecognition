module.exports = (sequelize, DataTypes) => {
    const users = sequelize.define('users', {
      id: {
        type: DataTypes.INTEGER(11),
        autoIncrement: true,
        primaryKey: true,
        comment: '인덱스'
      },
      userId: {
        type: DataTypes.STRING(10),
        allowNull: false,
        comment: '사용자 아이디'
      },
      password: {
        type: DataTypes.STRING(10),
        allowNull: false,
        comment: '사용자 비밀번호'
      },
      name: {
        type: DataTypes.STRING(30),
        allowNull: false,
        comment: '사용자 이름'
      }
    }, {
      tableName: 'users',
      comment: '유저',
      charset: 'utf8',
      collate: 'utf8_general_ci'
    });
    users.associate = function (models) {
      users.hasMany(models.face, { foreignKey: { name: 'userId', allowNull: false}});
    };
    return users;
  };
  