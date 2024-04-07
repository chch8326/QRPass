<template> 
  <div class="container-fluid">
    <div class="row">
      <h2 class="mt-5 mb-4 text-center">처방전 등록</h2> 
    </div>
    <div class="row">
      <div class="card">
        <p class="text-center mt-3 mb-n2">PRESCRIPTION</p>
        <hr />
        <div class="row">
          <div class="col-md-6">
            <p>환자 정보</p>
          </div>
          <div class="col-md-6">
            <p>의료 기관</p>
          </div>
          <div class="col-md-6">
            <label class="form-control-label">교부 연월일</label>
            <div class="form-group">
              <input class="form-control form-control-sm" id="preDt" type="text" v-model="today" />
            </div>  
          </div>   
          <div class="col-md-4">
            <label class="form-control-label">의료기관명</label>
            <div class="form-group">
              <input class="form-control form-control-sm" id="medOrgNm" type="text" :value=medOrg.MED_ORG_NM readonly />
            </div> 
          </div>
          <div class="col-md-2">
            <label class="form-control-label">의료기관기호</label>
            <div class="form-group">
              <input class="form-control form-control-sm" id="medOrgCode" type="text" :value=medOrg.MED_ORG_CODE readonly />
            </div> 
          </div>
          <div class="col-md-6">
            <label class="form-control-label">환자명</label>
            <div class="input-group">
              <input class="form-control form-control-sm my-0" id="patNm" type="text" :value="patInfo.PAT_NM" />
              <button type="button" class="btn btn-success btn-sm my-0" @click="isModalOpen1 = true">
                <i class="fas fa-search" />
              </button>
            </div> 
          </div>
          <div class="col-md-6">
            <label class="form-control-label">전화번호</label>
            <div class="form-group">
              <input class="form-control form-control-sm" id="telNo" type="text" :value=medOrg.MED_ORG_TEL readonly />
            </div> 
          </div> 
          <div class="col-md-6">
            <label class="form-control-label">주민등록번호</label>
            <div class="form-group">
              <input class="form-control form-control-sm" id="juminNo" type="text" :value="patInfo.JUMIN_NO" />
            </div>  
          </div>
          <div class="col-md-6">
            <label class="form-control-label">팩스번호</label>
            <div class="form-group">
              <input class="form-control form-control-sm" id="faxNo" type="text" :value=medOrg.MED_ORG_FAX_NO readonly />
            </div>  
          </div>  
          <div class="col-md-6">
            <label class="form-control-label">질병분류기호</label>
            <div class="form-group">
              <input class="form-control form-control-sm" id="diseaseSign" type="text" v-model=diseaseSign />
            </div>  
          </div>
          <div class="col-md-6">
            <label class="form-control-label">E-MAIL</label>
            <div class="form-group">
              <input class="form-control form-control-sm" id="email" type="text" :value=medOrg.MED_ORG_EMAIL readonly />
            </div>  
          </div>   
          <div class="row">
            <p>처방 의료인</p>
          </div>  
          <div class="col-md-4">
            <label class="form-control-label">성명</label>
            <div class="form-group">
              <input class="form-control form-control-sm" id="docNm" type="text" :value=medOrg.DOC_NM readonly />
            </div>  
          </div> 
          <div class="col-md-4">
            <label class="form-control-label">면허종별</label>
            <div class="form-group">
              <input class="form-control form-control-sm" id="licenseType" type="text" :value=medOrg.LICENSE_TYPE readonly />
            </div>  
          </div> 
          <div class="col-md-4">
            <label class="form-control-label">면허번호</label>
            <div class="form-group">
              <input class="form-control form-control-sm" id="licenseNo" type="text" :value=medOrg.LICENSE_NO readonly />
            </div>  
          </div>
          <div class="col-md-8">
            <p>처방 목적</p>
            <div class="form-group">
              <input type="radio" checked name="prep" value="1" v-model="presPrep" />
              <label class="ms-1 me-3" style="font-size:14px;">건강보험</label>
              <input type="radio" name="prep" value="2" v-model="presPrep" />
              <label class="ms-1 me-3" style="font-size:14px;">의료급여</label>
              <input type="radio" name="prep" value="3" v-model="presPrep" />
              <label class="ms-1 me-3" style="font-size:14px;">신체보험</label>
              <input type="radio" name="prep" value="4" v-model="presPrep" />
              <label class="ms-1 me-3" style="font-size:14px;">자동차보험</label>
              <input type="radio" name="prep" value="5" v-model="presPrep" />
              <label calss="ms-1" style="font-size:14px;">기타</label>
            </div>  
          </div>
          <div class="col-md-4">
            <p>원내 / 외 처방</p>
            <div class="form-group">
              <input type="radio" name="inout" value="1" v-model="inout" />
              <label class="ms-1 me-3" style="font-size:14px;">원내처방</label>
              <input type="radio" name="inout" value="2" v-model="inout" />
              <label calss="ms-1" style="font-size:14px;">원외처방</label>
            </div>  
          </div>
          <div class="d-flex">
            <p>의약품</p>
            <div class="ms-auto">
              <button class="btn btn-outline-success btn-sm mx-3" @click="isModalOpen2 = true">의약품</button>
            </div>  
          </div>
          <div v-if="uiyakpList.length !== 0">
            <div :key="idx" v-for="(uiyakp, idx) in uiyakpList">
              <div class="d-flex">
                <div class="col-md-6 me-3">
                  <label class="form-control-label">의약품명</label>
                  <div class="input-group">
                    <input class="form-control form-control-sm my-0" type="text" v-model=uiyakp.itemName />
                  </div>  
                </div>
                <div class="col-md-1 me-3">
                  <label class="form-control-label">1회 투약량</label>
                  <div class="form-group">
                    <input class="form-control form-control-sm" name="tuyak1Amt" type="text" v-model="uiyakp.tuyak1Amt" />
                  </div> 
                </div>
                <div class="col-md-1 me-3">
                  <label class="form-control-label">1일 투여횟수</label>
                  <div class="form-group">
                    <input class="form-control form-control-sm" name="tuyak1Cnt" type="text" v-model="uiyakp.tuyak1Cnt" />
                  </div> 
                </div>  
                <div class="col-md-1 me-3">
                  <label class="form-control-label">총 투약일수</label>
                  <div class="form-group">
                    <input class="form-control form-control-sm" name="totToyakIlsu" type="text" v-model="uiyakp.totToyakIlsu" />
                  </div> 
                </div>  
                <div class="col-md-1 me-3">
                  <label class="form-control-label">급여구분</label>
                  <div class="form-group">
                    <select class="form-select form-select-sm" v-model="uiyakp.gubun">
                      <option value="0">비급여</option>
                      <option value="1">급여</option>
                    </select>  
                  </div>         
                </div>
                <div class="col-md-2 mt-4">
                  <button class="btn btn-outline-secondary btn-sm" type="button" @click="uiyakpDelete(uiyakp.itemSeq)">삭제</button>
                </div>   
              </div>
            </div> 
            <div>
              <label class="form-control-label">용법</label>
              <div class="form-group">
                <select class="form-select form-select-sm" id="method">
                  <option value="매식 30분 전 복용">매식 30분 전 복용</option>
                  <option value="매식 30분 간 복용">매식 30분 간 복용</option>
                  <option value="매식 30분 후 복용">매식 30분 후 복용</option>
                </select>  
              </div>  
            </div>   
          </div>  
          <div v-else>
            <p class="text-center my-4">의약품을 추가하세요</p>
          </div>           
        </div>
        <hr />
        <button type="submit" class="btn btn-success" @click="onSubmit">처방전 등록</button>
      </div>     
    </div>
    <!-- 환자 목록 모달 -->
    <patient-list-modal v-if="isModalOpen1" @sendpatinfo="patInfoSet" @close="closePatLstModal" />
    <!-- 의약품 목록 및 등록 모달 -->
    <uiyak-register-modal v-if="isModalOpen2" :beflist="uiyakpList" @senduiyakplist="uiyakpInfoSet" @close="closeUiyakpRegModal" />
  </div>
</template>

<script>
import { getMedicalOrg } from "@/api/MedicalOrg"
import { getTodayDate, getTodayDateIlsi } from "@/utils/StringUtils"
import { getPrescriptionRegister, createPrescription } from "@/api/Prescription"
import { createQRCode } from "@/api/QR"
import PatientListModal from "./components/PatientListModal.vue"
import UiyakRegisterModal from "./components/UiyakRegisterModal.vue"

export default {
  components: {
    PatientListModal,
    UiyakRegisterModal
  },

  data() {
    return {
      // 교부날짜
      today: getTodayDate(1),
      // 질병분류기호
      diseaseSign: null,
      // 처방 목적
      presPrep: 1,
      // 원내외 처방
      inout: 0,
      // 환자 목록 모달 상태
      isModalOpen1: false,
      // 의약품 등록 모달 상태
      isModalOpen2: false,
      // 의료기관
      medOrg: {},
      // 환자 상세 정보
      patInfo: {},
      // 의약품 리스트
      uiyakpList: []
    }
  },

  created() {
    this.init();
  },

  mounted() {
    this.getMedOrg();
  },

  methods: {
    // 초기화
    init: function() {
      this.isModalOpen1 = false;
      this.isModalOpen2 = false;
      this.patInfo = {};
      this.uiyakpList = [];
    },

    // 환자 목록 모달 닫기
    closePatLstModal: function() {
      this.patInfo = {};
      this.isModalOpen1 = false;
    },

    // 의약품 등록 모달 닫기
    closeUiyakpRegModal: function() {
      this.uiyakpList = [];
      this.isModalOpen2 = false;
    },

    // 자식 컴포넌트(PatientListModal)에서 전달 받은 데이터 세팅
    patInfoSet: function(data) {
      this.patInfo = data;
      this.isModalOpen1 = false;
    },

    // 자식 컴포넌트(UiyakRegisterModal)에서 전달 받은 데이터 세팅
    uiyakpInfoSet: function(data) {
      this.uiyakpList = data;
      this.isModalOpen2 = false;
    },

    // 의약품 목록 삭제
    uiyakpDelete: function(itemSeq) {
      if(confirm("의약품을 삭제하시겠습니까?")) {
        this.uiyakpList.forEach((element, idx) => {
          if(element.itemSeq === itemSeq) {
            this.uiyakpList.splice(idx, 1);
          }
        })
      } 
    },

    // 처방전 및 의약품 내역 입력검증
    regValidation: function(presDt, juminNo, presPrep, presIlsi, methodVal) {
      // 처방전 내용 입력검증
      if(presDt === null   || juminNo === null || presPrep === null || 
         presIlsi === null || methodVal === null) {
          alert("처방 내용을 모두 입력해주세요.");
          return false;
      }

      // 의약품 내역 입력검증
      if(this.uiyakpList.length <= 0) {
        alert("의약품을 입력하세요.");
        return false;
      } else {
        this.uiyakpList.forEach((element) => {
          if(element.itemSeq === null   || element.itemName === null     || element.tuyak1Amt === null ||
             element.tuyak1Cnt === null || element.totToyakIlsu === null || element.gubun === null) {
            alert("의약품 내역을 모두 입력해주세요.");
            return false;
          }

          if(element.tuyak1Amt <= 0 || element.tuyak1Cnt <= 0 || element.totToyakIlsu <= 0) {
            alert("의약품 내역을 정확히 입력해주세요.");
            return false;
          }
        })
      }
    },

    // 환자 목록 조회
    getMedOrg: function() {
      let data = {
        "data": {
          "medOrgSer": 1,
          "medOrgNm": "테스트 병원"
        }
      }

      getMedicalOrg(data)
      .then((response) => {
        this.medOrg = response.medOrg;
      }).catch((error) => {
        console.log(error);
      })
    },

    // 처방전 등록
    onSubmit: function() {
      let presDt      = getTodayDate(0);
      let juminNo     = this.patInfo.JUMIN_NO;
      let diseaseSign = this.diseaseSign;
      let presPrep    = this.presPrep;
      let wonaeOeYn   = this.inout;
      let presIlsi    = getTodayDateIlsi();
      let methodVal   = document.getElementById("method");

      // 처방전 등록 전 입력 검증
      this.regValidation(presDt, juminNo, presPrep, presIlsi, methodVal);

      let data = {
        "data": {
          "presDt": presDt,
          "juminNo": juminNo,
          "diseaseSign": diseaseSign,
          "presPrep": presPrep,
          "wonaeOeYn": wonaeOeYn,
          "useGigan": 3,
          "useMth": methodVal.options[methodVal.selectedIndex].value,
          "presIlsi": presIlsi,
          "patNm": this.patInfo.PAT_NM,
          "medOrg": this.medOrg,
          "uiyakpList": this.uiyakpList
        }
      }

      // 처방전 등록 -> PDF 파일 생성 / QR 코드 생성 -> 처방전 목록 페이지 이동
      getPrescriptionRegister(data)
      .then((response) => {
        if(response === true) {
          console.log("처방전 등록");

          createPrescription(data)
          .then((response) => {
            if(response === true) {
              console.log("처방전 PDF 파일 생성");
            }
          }).catch((error) => {
            console.log(error);
          }) 

          createQRCode(data)
          .then(() => {
            console.log("QR code 생성");
          }).catch((error) => {
            console.log(error);
          })
        }

        alert("처방전이 등록되었습니다.");
        this.$router.push({ name: "prescription" });
      }).catch((error) => {
        console.log(error);
      })
    }
  }
}
</script>