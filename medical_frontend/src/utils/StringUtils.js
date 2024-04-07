// null, undefined 체크
export function isNullOrEmptyChk(str) {
    if((typeof str) !== "string") {
        return true;
    }

    if(str === null || str === "null" || str === "Null" || str === "NULL") {
        return true;
    }

    if(str === undefined || str === "undefined") {
        return true;
    }

    if(str.trim() === "") {
        return true;
    }

    return false;
}

// 날짜 산출
export function getTodayDate(gbn) {
    var today = new Date();
    var year = today.getFullYear();
    var month = ("0" + (today.getMonth() + 1)).slice(-2);
    var day = ("0" + today.getDate()).slice(-2);

    if(gbn === 0) {
        return year + month + day;
    } else if(gbn === 1) {
        return year + "-" + month + "-" + day;
    } else {
        return year + month + day;
    }
}

// 일시 산출
export function getTodayDateIlsi() {
    var today = new Date();
    var year = today.getFullYear();
    var month = ("0" + (today.getMonth() + 1)).slice(-2);
    var day = ("0" + today.getDate()).slice(-2);
    var hours = ("0" + today.getHours()).slice(-2);
    var minutes = ("0" + today.getMinutes()).slice(-2);
    var seconds = ("0" + today.getSeconds()).slice(-2);
    
    return year + month + day + hours + minutes + seconds;
}

// 왼쪽에 0 추가
export function getLapdZero(num) {
    return String(num).padStart(5, "0");
}

export default { isNullOrEmptyChk, getTodayDate, getTodayDateIlsi, getLapdZero }