<template>
  <div class="app flex-row align-items-center">
    <div class="container">
      <b-row class="justify-content-center">
        <b-col md="8">
          <b-card-group>
            <b-card no-body class="p-4">
              <b-card-body>
                <b-form>
                  <h2>로그인</h2>
                  <p class="text-muted">Sign In to your account</p>
                  <b-input-group class="mb-3">
                    <b-input-group-prepend><b-input-group-text><i class="icon-user"></i></b-input-group-text></b-input-group-prepend>
                    <b-form-input type="text" class="form-control" placeholder="아이디" autocomplete="username email" v-model="uid"/>
                  </b-input-group>
                  <b-input-group class="mb-4">
                    <b-input-group-prepend><b-input-group-text><i class="icon-lock"></i></b-input-group-text></b-input-group-prepend>
                    <b-form-input type="password" class="form-control" placeholder="비밀번호" autocomplete="current-password" v-model="password"/>
                  </b-input-group>
                  <b-row>
                    <b-col cols="6">
                      <b-button variant="primary" class="px-4" v-on:click="login()">Login</b-button>
                    </b-col>
                    <b-col cols="6">
                      <b-button variant="primary" class="px-4" v-on:click="login()">Register</b-button>
                    </b-col>
                  </b-row>
                </b-form>
              </b-card-body>
            </b-card>
            
          </b-card-group>
        </b-col>
      </b-row>
    </div>
  </div>
</template>

<script>
export default {
  name: 'HelloWorld',
  data () {
    return {
      msg: 'Welcome to Your Vue.js App',
      uid: '',
      pw: ''
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
