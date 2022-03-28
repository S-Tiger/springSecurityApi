// 세자리 단위 마다 Comma
function addComma(num) {
    var regexp = /\B(?=(\d{3})+(?!\d))/g;
  	return num.toString().replace(regexp, ',');
}

//백분율 소숫점 1번째 자리 반환
function pct(num) {
	var result =  (num * 100).toFixed(1);
	if(isNaN(result)){
		result = 0;
	}
	result +=  "%";
	return result;
}

//백분율 소숫점 2번쨰 자리 반환
function pct2(num) {
	var result =  (num * 100).toFixed(2);
	if(isNaN(result)){
		result=0;
	}
	result += "%";
	return result;
}

// 현재 일시 (12h)
function crtTime() {
	var today = new Date();

	var year = today.getFullYear();
	var month = today.getMonth() + 1;
	var date = today.getDate();
	var hour12h = "";
	if (today.getHours() < 12) {
		hour12h = '오전 ' + today.getHours();
	} else if (today.getHours() == 12) {
		hour12h = '오후 ' + today.getHours();
	} else if (today.getHours() > 12) {
		hour12h = '오후 ' + (today.getHours()-12);
	}
	var minute = ("0" + today.getMinutes()).slice(-2);

	return year + ". " + month + ". " + date + ". " + hour12h + ":" + minute;
}

//현재 일시 (요일 포함)
function realTime() {
	var today = new Date();

	var year = today.getFullYear();
	var month = today.getMonth() + 1;
	var date = today.getDate();
	var hour = today.getHours();
	var minute = ("0" + today.getMinutes()).slice(-2);
	var second = ("0" + today.getSeconds()).slice(-2);
	var day = today.getDay();
	var week = ['일', '월', '화', '수', '목', '금', '토']

	return year + "/" + month + "/" + date + "(" + week[day] + ") "  + hour + ":" + minute + ":" + second;
}

//nan 이면 0 반환
function nanToNum(num) {
	if (isNaN(num)) {
		num = 0;
	}
	return num;
}

//1000원 단위로 변환
function thousand(num) {
	return Math.floor(num / 1000);
}

