<template>
  <div class="container-fluid">
    <div class="row">
      <div class="mt-6 mb-5 text-center">
        <h2>처방전</h2>
      </div>
      <div class="container-fluid mb-3">
        <div class="card">
          <div class="card-body p-2">
            <div class="d-flex mt-4">
              <div class="col-1 mt-1 ms-2">환자명</div>
              <div class="col-3 ms-n2">
                <input id="keyword"
                      type="text"
                      class="form-control form-control-sm"
                      v-model=patNm
                      placeholder="환자명을 입력하세요"
                      @keyup.enter="getPresList(patNm, 1)" />
              </div>
              <div class="col-3">
                <button type="button" class="btn btn-success btn-sm ms-3" @click="getPresList(this.patNm, 1)">검색</button>
              </div>
            </div>
          </div>
        </div>
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
                  <td class="text-center">{{pres.RNUM}}</td>
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
      <div class="my-3">
        <ul class="pagination pagination-sm justify-content-center">
          <li :class="`page-item ${pageInfo.prev ? '' : 'disabled'}`">
            <span class="page-link" @click="getPresList(patNm, pageNo - 1)">&laquo;</span>
          </li> 
          <li class="page-item" :key="idx" v-for="(page, idx) in pageList">
            <span class="page-link" @click="getPresList(patNm, page)">{{page}}</span>
          </li>
          <li :class="`page-item ${pageInfo.next ? '' : 'disabled'}`">
            <span class="page-link" @click="getPresList(patNm, pageNo + 1)">&raquo;</span>
          </li> 
        </ul>
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
      // 페이지
      pageNo: 1,
      // 출력 페이지 수
      numOfRows: 10,
      // 환자명
      patNm: null,
      // 처방전 갯수
      presCnt: 0,
      // 페이징 처리 데이터
      pageInfo: {},
      // 처방전 리스트
      presList: [],
      // 페이지 리스트
      pageList: []
    }
  },

  created() {
    this.init();
  },

  mounted() {
    this.getPresList(this.patNm, this.pageNo);
  },

  methods: {
    // 초기화
    init: function() {
      this.pageNo     = 1;
      this.numOfRows  = 10;
      this.patNm      = null;
      this.presCnt    = 0;
      this.presList   = [];
      this.pageList   = [];
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
    getPresList: function(patNm, pageNo) {
      let data = {
        "data": {
          "patNm": patNm,
          "pageNo": pageNo,
          "amount": this.numOfRows
        }
      }

      getPrescriptionList(data)
      .then((response) => {
        this.pageList   = [];
        this.presCnt    = response.presCnt;
        this.presList   = response.presList;
        this.pageInfo   = response.pagingInfo;

        // 교부번호 왼쪽에 0으로 세팅
        this.presList.forEach((element) => {
          element.PRE_NO = getLapdZero(element.PRE_NO);
        })

        // 페이지 세팅
        for(var i = this.pageInfo.startPage; i <= this.pageInfo.endPage; i++) {
          this.pageList.push(i);
        }
      }).catch((error) => {
        console.log(error);
      })
    }
  }
}

</script>