<template>
  <div>
    <!-- 
        [요구사항]
        1. 처방전 상세 조회
        2. 각 처방전 상세에 해당되는 처방내역 조회
        3. 각 처방전 상세에 해당되는 환자 조회
        4. 의료기관 조회
        5. 화면은 PresRegister.vue 참고하면 될 듯 합니다.
    -->
    <h3>처방전 상세조회 화면</h3>
  </div>  
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
      console.log(this.$route.params);
    },

    methods: {
    
    }
  }
</script>