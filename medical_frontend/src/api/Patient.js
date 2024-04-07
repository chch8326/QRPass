import request from "../utils/request"

export function getPatientList(data) {
    return request({
        url: "/pat/list",
        method: "get",
        data
    })
}

export function getPatientDetail(data) {
    return request({
        url: "/pat/detail",
        method: "post",
        data
    })
}