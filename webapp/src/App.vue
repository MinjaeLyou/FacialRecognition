<template>
  <div>
    
    <mainHeader/>

    <router-view/>
  </div>
</template>

<script>

import mainHeader from '@/components/Header'
export default {
  name: 'App',
  components: {
    'mainHeader': mainHeader
  }
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
  width: 800px;
  height: 600px;
  margin: 100px auto;
}

img{
  margin-top: 1px;
}

body {
  background-color: #E6E6E6;
}
</style>
