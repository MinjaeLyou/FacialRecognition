<template>
  <div id="mainheader">
    <img src="../assets/facialr.png" width="50px"style="margin:5px 10px;"><span style="font-size: 25px; font-family: 'Abel'; margin-top: 15px;">Facial Recognition</span>
    <b-button variant="link" class="px-0" style="float:right; margin: 10px 30px;" v-on:click="gogit()">Logout</b-button>
    <b-button variant="link" class="px-0" style="float:right; margin: 10px 30px; color:black;">Download App</b-button>
                    
  </div>
</template>

<script>
import { HeaderDropdown as AppHeaderDropdown } from '@coreui/vue'
export default {
  name: 'DefaultHeaderDropdownAccnt',
  components: {
    AppHeaderDropdown
  },
  data: () => {
    return { itemsCount: 42 }
  },
  methods: {
    gogit() {
        
    }
  },
}
</script>
<style>
@import url('https://fonts.googleapis.com/css?family=Abel&display=swap');
#mainheader {
    width: 100%;
    background-color: white;
    height: 65px;
    border-bottom: 0.5px solid gray;
}
</style>