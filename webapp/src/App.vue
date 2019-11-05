<template>
  <div id="app">
    <img src="./assets/facialr.png" width="200px">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
  background-color: white;
  width: 800px;
  height: 600px;
  margin: 100px auto;
  border: 1px solid #bcbcbc;
  border-radius: 3%;
}

img{
  margin-top: 15px;
}

body {
  background-color: #E6E6E6;
}
</style>
