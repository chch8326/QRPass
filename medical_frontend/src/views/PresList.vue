<template>
  <div class="container-fluid">
    <div class="row">
      <div class="mt-7 mb-3 text-center">
        <h2>처방전</h2>
      </div>
      <div class="col-12">
        <div class="card">
          <div class="table-responsive p-0">
            <table class="table align-items-center mb-0">
              <thead>
                <tr>
                  <th class="text-center">번호</th>
                  <th class="text-center">교부일자 - 교부번호</th>
                  <th class="text-center">환자명</th>
                  <th class="text-center">주민등록번호</th>
                </tr>    
              </thead>
              <tbody>
                <tr :id="`row${idx}`" 
                    :key="idx" v-for="(pres, idx) in presList" 
                    @mouseover="mouseover(idx)" 
                    @mouseleave="mouseleave(idx)"
                    @click="movePresDetail(idx)">
                  <td class="text-center">{{presCnt - idx}}</td>
                  <td class="text-center">
                    {{pres.PRE_DT.substr(0, 4)}}년 {{pres.PRE_DT.substr(4, 2)}}월 {{pres.PRE_DT.substr(6, 2)}}일 - 제 {{pres.PRE_NO}}호
                  </td>
                  <td class="text-center">{{pres.PAT_NM}}</td>
                  <td class="text-center">{{pres.JUMIN_NO.substr(0, 6)}}-{{pres.JUMIN_NO.substr(6, 7)}}</td>
                </tr>    
              </tbody>        
            </table> 
          </div>     
        </div>    
      </div>          
    </div>    
  </div>    
</template>

<script>
import { getPrescriptionList } from "@/api/Prescription"
import { getLapdZero } from "@/utils/StringUtils"

export default {
  data() {
    return {
      // 처방전 갯수
      presCnt: 0,
      // 처방전 리스트
      presList: []
    }
  },

  created() {
    this.init();
  },

  mounted() {
    this.getPresList();
  },

  methods: {
    // 초기화
    init: function() {
      this.presCnt = 0;
      this.presList = [];
    },

    // 처방전 목록 마우스오버 이벤트
    mouseover: function(idx) {
      let presRow = document.getElementById("row" + idx);
      presRow.style.backgroundColor = "gainsboro";
    },

    // 처방전 목록 마우스리브 이벤트
    mouseleave: function(idx) {
      let presRow = document.getElementById("row" + idx);
      presRow.style.backgroundColor = "white";
    },

    // 처방전 상세 조회 화면 이동
    movePresDetail: function(idx) {
      this.$router.push({ name: "detail", params: this.presList[idx] });
    },

    // 처방전 조회
    getPresList: function() {
      let data = {}

      getPrescriptionList(data)
      .then((response) => {
        this.presCnt = response.presCnt;
        this.presList = response.presList;

        // 교부번호 왼쪽에 0으로 세팅
        this.presList.forEach((element) => {
          element.PRE_NO = getLapdZero(element.PRE_NO);
        })
      }).catch((error) => {
        console.log(error);
      })
    }
  }
}

</script>