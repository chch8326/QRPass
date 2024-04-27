<template>
  <div class="container-fluid">
    <div class="row">
      <h2 class="mt-5 mb-4 text-center">처방전 상세</h2> 
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
              <input class="form-control form-control-sm" id="preDt" type="text" v-model="preDt" readonly />
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
              <input class="form-control form-control-sm" id="medOrgCode" type="text" :value=medOrg.MED_ORG_CODE readonly  />
            </div> 
            </div>
            <div class="col-md-6">
            <label class="form-control-label">환자명</label>
            <div class="input-group">
              <input class="form-control form-control-sm my-0" id="patNm" type="text" v-model="patNm" readonly />
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
              <input class="form-control form-control-sm" id="juminNo" type="text" v-model="juminNo" readonly />
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
              <input class="form-control form-control-sm" id="diseaseSign" type="text" v-model="diseaseSign" readonly />
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
              <input type="radio" name="prep" value="1" v-model="presPrep" disabled />
              <label class="ms-1 me-3" style="font-size:14px;">건강보험</label>
              <input type="radio" name="prep" value="2" v-model="presPrep" disabled />
              <label class="ms-1 me-3" style="font-size:14px;">의료급여</label>
              <input type="radio" name="prep" value="3" v-model="presPrep" disabled />
              <label class="ms-1 me-3" style="font-size:14px;">신체보험</label>
              <input type="radio" name="prep" value="4" v-model="presPrep" disabled />
              <label class="ms-1 me-3" style="font-size:14px;">자동차보험</label>
              <input type="radio" name="prep" value="5" v-model="presPrep" disabled />
              <label calss="ms-1" style="font-size:14px;">기타</label>
            </div>  
          </div>
          <div class="col-md-4">
            <p>원내 / 외 처방</p>
            <div class="form-group">
              <input type="radio" name="inout" value="1" v-model="inout" disabled />
              <label class="ms-1 me-3" style="font-size:14px;">원내처방</label>
              <input type="radio" name="inout" value="2" v-model="inout" disabled />
              <label calss="ms-1" style="font-size:14px;">원외처방</label>
            </div>  
          </div>
          <div class="d-flex">
            <p>의약품</p>
          </div>
          <!-- 조회된 의약품 반복문으로 출력 -->
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { getMedicalOrg } from "@/api/MedicalOrg"
import { getPrescriptionDetail } from "@/api/Prescription"
  
export default {
  data() {
    return {
      preDt: null,
      patNm: null,
      juminNo: null,
      diseaseSign: null,
      presPrep: 0,
      inout: 0,
      medOrg: {},
      detail: []
    }
  },

  created() {
    this.preDt = null;
    this.patNm = null;
    this.juminNo = null;
    this.diseaseSign = null;
    this.presPrep = 0;
    this.inout = 0;
    this.medOrg = {};
    this.detail = [];
  },

  mounted() {
    console.log(this.$route.params);
    this.preDt = this.$route.params.PRE_DT.substring(0, 4) + "-"
                  + this.$route.params.PRE_DT.substring(4, 6) + "-"
                  + this.$route.params.PRE_DT.substring(6, 8)
    this.patNm = this.$route.params.PAT_NM;
    this.juminNo = this.$route.params.JUMIN_NO;
    this.diseaseSign = this.$route.params.DISEASE_SIGN;
    this.presPrep = this.$route.params.PRE_PERP;
    this.inout = this.$route.params.WONNAE_OE_YN;
    this.getMedOrg();
    this.getPrescriptionDetail();

  },

  methods: {
    //  의료기관 조회
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

    // 의약품 조회
    getPrescriptionDetail: function(){
      let data = {
        "data" : {
          "presDt": this.$route.params.PRE_DT,
          "presNo": this.$route.params.PRE_NO,
          "juminNo": this.$route.params.JUMIN_NO
        }
      }

      getPrescriptionDetail(data)
      .then((response) => {
        this.uiyakpoomList = response.uiyakpoomList;
      }).catch((error) => {
        console.log(error);
      })
    }
  }
}
</script>