import request from "../utils/request"

// 처방전 목록 조회
export function getPrescriptionList(data) {
    return request({
        url: "/pres/list",
        method: "get",
        data
    })
}

// 의약품 목록 조회
export function getUiyakpoomList(data) {
    return request({
        url: "/pres/uiyakpoom",
        method: "post",
        data
    })
}

// 처방전 등록
export function getPrescriptionRegister(data) {
    return request({
        url: "/pres/register",
        method: "post",
        data
    })
}

// 처방전 PDF 파일 생성
export function createPrescription(data) {
    return request({
        url: "/pres/pdf",
        method: "post",
        data
    })
}