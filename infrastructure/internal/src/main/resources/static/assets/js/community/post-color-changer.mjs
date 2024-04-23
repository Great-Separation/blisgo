// 색상 입력 필드를 선택합니다.
var colorInput = document.getElementById('post-color');

// 카드 요소를 선택합니다.
var card = document.querySelector('.card');

if (colorInput != null) {
    // 색상 입력 필드에 이벤트 리스너를 추가합니다.
    colorInput.addEventListener('input', function () {
        // 입력된 색상 값에 따라 카드의 배경색을 변경합니다.
        card.style.backgroundColor = this.value;
    });
}
