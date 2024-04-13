<template>
  <!-- 
    [요구사항]
    1. 화면은 PresRegister.vue와 같습니다. 다만 각 처방전의 상세내역으로 데이터를 변경할 수 없기 때문에 각 input에 readonly는 필수입니다.
    2. this.$route.params를 통해 처방전 데이터는 기본적으로 넘어오므로 넘어오는 값은 그대로 코딩하면 됩니다. (개발자 도구 콘솔창에서 확인 가능)
    3. this.$route.params를 통해 넘어오지 않지만 필요한 데이터는 병원 데이터와 처방전 상세내역(의약품 리스트) 데이터입니다.
        -> 병원과 처방전 상세내역(의약품 리스트) 데이터는 서버단(spring)에서 조회를 한 뒤 화면으로 전송을 해줘야 합니다.
    4. PresList.vue와 presRegister.vue 참고 부탁드립니다.
    5. 화이팅  \ >_< / ~~  
  -->
  <div class="container-fluid">
    <div class="row">
      <h2 class="mt-5 mb-4 text-center">처방전 상세내역</h2>
    </div>
    <div class="row">
      <div class="card">
        <p class="text-center mt-3 mb-n2">PRESCRIPTION</p>
        <hr />
      </div>
    </div>
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