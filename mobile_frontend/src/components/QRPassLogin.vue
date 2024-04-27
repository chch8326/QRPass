<template>
  <v-toolbar title="QRPass" color="primary" />
  <v-container>
    <v-row alignn="center" justify="center">
      <v-btn rounded="lg" size="large" color="primary" @click="login()">로그인</v-btn>
    </v-row>  
  </v-container> 
</template>
  
<script>
import MobileDetect from "mobile-detect"

export default {
  data() {
    return {
      
    }
  },

  created() {

  },

  mounted() {
    this.isMobileCheck();
  },

  methods: {
    // 접속 기기 체크
    isMobileCheck: function() {
      const md = new MobileDetect(window.navigator.userAgent);

      if(md.mobile() || md.tablet()) {  
        console.log("");
      } else {        
        console.log(md.mobile());                    
        this.$router.replace({ name: "pc" });
      }
    },

    // 로그인: 현재 로그인 로직 없으므로 화면 이동만 하도록 함
    login: function() {
      this.$router.replace({ name: "qrpass" });
    }
  }
} 
</script>  