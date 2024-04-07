import request from "../utils/request"

export function getMedicalOrg(data) {
    return request({
        url: "/medorg/detail",
        method: "post",
        data
    })
}