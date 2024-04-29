export function init() {
    let colorInput = document.getElementById('post-color');
    let card = document.querySelector('.card');

    if (colorInput != null) {
        colorInput.addEventListener('input', function () {
            card.style.backgroundColor = this.value;
        });
    }
}