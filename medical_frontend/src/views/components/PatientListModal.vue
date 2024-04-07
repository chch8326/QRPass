<template>
  <div class="modal show">
    <div class="modal-dialog modal-xl">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">환자 목록</h5>
        </div>  
        <div class="modal-body">
          <div class="table-responsive p-0">
            <table class="table align-items-center mb-0">
              <thead>
                <tr>
                  <th class="text-center">선택</th>
                  <th class="text-center">환자번호</th>
                  <th class="text-center">환자명</th>
                  <th class="text-center">주민번호</th>
                  <th class="text-center">성별</th>    
                </tr>    
              </thead>
              <tbody>
                <tr :key="idx" v-for="(pat, idx) in patList">
                  <td class="text-center">
                    <input type="checkbox" name="select" :value=pat.PAT_NO @click="patNoSet(pat.PAT_NO)" />
                  </td>
                  <td class="text-center">{{pat.PAT_NO}}</td>
                  <td class="text-center">{{pat.PAT_NM}}</td>
                  <td class="text-center">{{pat.JUMIN_NO.substr(0, 6)}}-{{pat.JUMIN_NO.substr(6, 7)}}</td>
                  <td class="text-center">
                    <p v-if="pat.GENDER === 'M'">남</p>
                    <p v-else-if="pat.GENDER === 'G'">여</p>
                  </td>
                </tr>    
              </tbody>    
            </table>     
          </div> 
        </div> 
        <div class="modal-footer">
          <button type="button" class="btn btn-success" @click="getPatient()">선택</button>
          <button type="button" class="btn btn-secondary" @click="closeModal()">취소</button>
        </div>   
      </div>  
    </div>  
  </div>  
</template>

<script>
import { getPatientList, getPatientDetail } from "@/api/Patient"

export default {
  data() {
    return {
      // 환자 번호
      patNo: 0,
      // 환자 목록 갯수
      patCnt: 0,
      // 환자 정보
      patInfo: {},
      // 환자 목록
      patList: []    
    }
  },

  created() {
    this.init();
  },

  mounted() {
    this.getPatList();
  },

  methods: {
    // 초기화
    init: function() {
      this.patNo = 0;
      this.patCnt = 0;
      this.patInfo = {};
      this.patList = [];
    },

    // 모달 닫기
    closeModal: function() {
      this.$emit("close");
    },

    // 환자 번호 세팅
    patNoSet: function(data) {
      // 체크박스 중복체크 방지
      var patNoObj = document.getElementsByName("select");

      for(var idx = 0; idx < patNoObj.length; idx++) {
        if(patNoObj[idx].value != data) {
          patNoObj[idx].checked = false;
        }
      }

      this.patNo = data;
    },

    // 환자 목록 조회
    getPatList: function() {
      let data = {}

      getPatientList(data)
      .then((response) => {
        this.patCnt = response.patCnt;
        this.patList = response.patList;
      }).catch((error) => {
        console.log(error);
      })
    },

    // 환자 상세 조회
    getPatient: function() {
      let data = {
        "data": {
          "patNo": this.patNo
        }
      }

      getPatientDetail(data)
      .then((response) => {
        this.patInfo = response.patInfo;
        this.$emit("sendpatinfo", this.patInfo);  // 환자 상세 정보 데이터 부모 컴포넌트(PresRegister)로 전송
      }).catch((error) => {
        console.log(error);
      })
    }
  }
}
</script>