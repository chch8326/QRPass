import request from "../utils/request"

// QR code 생성
export function createQRCode(data) {
    return request({
        url: "/qr/create",
        method: "post",
        data
    })
}