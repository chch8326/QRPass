<template>
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