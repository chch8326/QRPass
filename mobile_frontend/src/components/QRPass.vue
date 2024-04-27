<template>
  <v-toolbar title="QRPass" color="primary" />
  <v-container>
    <v-row align="center" justify="center">
      <img src="http://192.168.0.3:8080/qr/view?codeName=20240417_00001_최환자.png" />
    </v-row>
  </v-container>
  <v-footer>
    <v-bottom-navigation bg-color="primary" grow>
      <v-btn>QR</v-btn>
      <v-btn>처방전</v-btn>
    </v-bottom-navigation>  
  </v-footer>  
</template>
  
<script>
export default {
  data() {
    return {
      
    }
  },

  created() {

  },

  mounted() {

  },

  methods: {
    
  }
} 
</script>  