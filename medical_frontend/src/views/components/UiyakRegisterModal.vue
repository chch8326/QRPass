<template>
  <div class="modal show">
    <div class="modal-dialog modal-xl">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">의약품 목록</h5>
        </div>    
        <div class="modal-body">
          <div class="d-flex">
            <div class="col-1 mt-1">
              <p>의약품명</p>
            </div>
            <div class="col-3 ms-n1">
              <input id="keyword" 
                     type="text" 
                     class="form-control form-control-sm"  
                     v-model=keyword
                     placeholder="검색어를 입력하세요"
                     @keyup.enter="getUiyakpList(1)" />
            </div>  
            <div class="col-3">
              <button type="button" class="btn btn-success btn-sm ms-3" @click="getUiyakpList(1)">검색</button>
            </div>
          </div>
          <div id="div-table">
            <div class="table-responsive p-0">
              <table class="table align-items-center">
                <thead>
                  <tr>
                    <th class="text-center">품목 일련번호</th>
                    <th class="text-center">품목명</th>
                    <th class="text-center">선택</th>
                  </tr>  
                </thead>
                <tbody>
                  <tr :key="idx" v-for="(uiyakp, idx) in uiyakpList">
                    <td class="text-center">{{uiyakp.ITEM_SEQ}}</td>
                    <td class="text-center">{{uiyakp.ITEM_NAME}}</td>
                    <td class="text-center">
                      <button type="button" 
                              class="btn btn-success btn-sm mt-3" 
                              @click="seletUiyakp(uiyakp.ITEM_SEQ, uiyakp.ITEM_NAME)">선택
                      </button>
                    </td>
                  </tr>
                </tbody>    
              </table>
              <div class="my-3">
                <ul class="pagination pagination-sm justify-content-center">
                  <li :class="`page-item ${pageInfo.prev ? '' : 'disabled'}`">
                    <span class="page-link" @click="getUiyakpList(pageNo - 1)">&laquo;</span>
                  </li>
                  <li class="page-item" :key="idx" v-for="(page, idx) in pageInfo.pageList">
                    <span class="page-link" @click="getUiyakpList(page)">{{page}}</span>
                  </li>
                  <li :class="`page-item ${pageInfo.next ? '' : 'disabled'}`">
                    <span class="page-link" @click="getUiyakpList(pageNo + 1)">&raquo;</span>
                  </li>   
                </ul>
              </div>
              <hr />
              <div v-if="seletedUiyakpList.length !== 0" class="table-responsive p-0">
                <table class="table align-items-center">
                  <thead>
                    <tr>
                      <th class="text-center">품목 일련번호</th>
                      <th class="text-center">품목명</th>
                      <th class="text-center">삭제</th>
                    </tr>  
                  </thead>
                  <tbody>
                    <tr :key="idx" v-for="(select, idx) in seletedUiyakpList">
                      <td class="text-center">{{select.itemSeq}}</td>
                      <td class="text-center">{{select.itemName}}</td>
                      <td class="text-center"><p @click="deleteUiyakp(select.itemSeq)">X</p></td>
                    </tr>  
                  </tbody>    
                </table>  
              </div>  
            </div>     
          </div>    
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-success" @click="confirm()">확인</button>
          <button type="button" class="btn btn-secondary" @click="closeModal()">취소</button>
        </div>        
      </div>  
    </div>    
  </div>  
</template>

<script>
import { getUiyakpoomList } from "@/api/Prescription"

export default {
  props: {
    // 의약품 리스트 모달을 다시 띄울 경우 이전에 선택된 리스트 출력
    beflist: {
      type: Array,
      default: () => {
        return []
      }
    }
  },

  data() {
    return {
      // 페이지 번호
      pageNo: 1,
      // 한 페이지 당 출력 개수
      numOfRows: 100,
      // 검색어
      keyword: null,
      // 총 건 수
      totalCnt: 0,
      // 중복 체크
      dubChk: false,
      // 의약품명
      uiyakpNm: null,
      // 의약품 리스트
      uiyakpList: [],
      // 선택된 의약품 리스트
      seletedUiyakpList: this.beflist,
      // 페이징 처리 객체
      pageInfo: {
        "startPage": 0,
        "endPage": 0,
        "prev": false,
        "next": false,
        "pageList": []
      }
    }
  },

  created() {
    this.init();
  },
  
  mounted() {
    this.getUiyakpList(1);
  },

  methods: {
    // 초기화
    init: function() {
      this.pageNo = 1;
      this.numOfRows = 100;
      this.totalCnt = 0;
      this.keyword = null;
      this.uiyakpNm = null;
    },

    // 모달 닫기
    closeModal: function() {
      this.$emit("close");
    },

    // 선택된 의약품 리스트 부모 컴포넌트(PresRegister)에 전송
    confirm: function() {
      this.$emit("senduiyakplist", this.seletedUiyakpList);
    },

    // 페이지 번호 세팅
    pageNoSet: function() {
      for(var pageNum = this.pageInfo.startPage; pageNum <= this.pageInfo.endPage; pageNum++) {
        this.pageInfo.pageList.push(pageNum);
      }
    },

    // 의약품 목록 페이징 처리
    pageProc: function(currentPage) {
      this.pageInfo.endPage = Math.ceil(currentPage / 10.0) * 10;             // 마지막 페이지
      this.pageInfo.startPage = this.pageInfo.endPage - 9;                    // 시작 페이지

      var realEndPage = Math.ceil((this.totalCnt * 1.0) / this.numOfRows);    // 실제 마지막 페이지 계산      
      this.pageInfo.prev = currentPage > 1;                                   // 이전 페이지
      this.pageInfo.next = this.pageInfo.endPage < realEndPage;               // 다음 페이지

      // 실제 마지막 페이지로 변경
      if(this.pageInfo.endPage > realEndPage) {
        this.pageInfo.endPage = realEndPage;
      }

      // 검색어 입력 시 페이지 번호 리스트 초기화
      if(this.keyword !== null) {
        this.pageInfo.pageList = [];
      }

      // 첫 번째 페이징 처리
      if(this.pageInfo.pageList.length === 0) {
        this.pageNoSet();                  // 페이지 번호 세팅
      } else {
        // 이전(prev) 페이징 처리 
        if(this.pageInfo.pageList[0] > currentPage) {
          this.pageInfo.pageList = [];    // 페이지 번호 리스트 초기화   
          this.pageNoSet();               // 페이지 번호 세팅
        }
        // 다음(next) 페이징 처리
        else if(this.pageInfo.pageList.slice(-1)[0] < currentPage) {
          this.pageInfo.pageList = [];    // 페이지 번호 리스트 초기화
          this.pageNoSet();               // 페이지 번호 세팅
        }
      }
    },

    // 선택된 의약품 선택 리스트에 추가
    seletUiyakp: function(itemSeq, itemName) {
      this.dubChk = false;
      this.uiyakpNm = "[" + itemSeq + "]" + itemName;

      let data = {
        "itemSeq": itemSeq,
        "itemName": this.uiyakpNm,
        "tuyak1Amt": null,
        "tuyak1Cnt": null,
        "totToyakIlsu": null,
        "gubun": null
      }

      if(this.seletedUiyakpList.length === 0) {
        this.seletedUiyakpList.push(data);
      } else {
        // 선택된 의약품 중복 체크
        this.seletedUiyakpList.forEach((element) => {
          if(element.itemSeq === data.itemSeq) this.dubChk = true;
          if(this.dubChk === true) return false;
        })

        if(this.dubChk == true) {
          alert("이미 선택된 의약품입니다.");
        } else {
          this.seletedUiyakpList.push(data);
        }
      }
    },

    // 선택된 의약품 삭제
    deleteUiyakp: function(itemSeq) {
      this.seletedUiyakpList.forEach((element, idx) => {
        if(element.itemSeq === itemSeq) {
          this.seletedUiyakpList.splice(idx, 1);
        }
      })
    },

    // 의약품 목록 조회
    getUiyakpList: function(currentPage) {
      this.pageNo = currentPage;

      let data = {
        "data": {
          "pageNo": this.pageNo,
          "numOfRows": this.numOfRows,
          "keyword": this.keyword
        }
      }

      getUiyakpoomList(data)
      .then((response) => {
        this.totalCnt = response.totcalCnt;
        this.uiyakpList = response.uiyackpoomList;
        this.pageProc(this.pageNo); 
      }).catch((error) => {
        console.log(error);
      })
    }
  }
}
</script>

<style>
#div-table {
  max-height: calc(100vh - 200px);
  overflow-x: hidden;
  overflow-y: auto;
}
</style>