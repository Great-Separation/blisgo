document.addEventListener("htmx:afterOnLoad", function () {
    let toastElList = [].slice.call(document.querySelectorAll('.toast'))
    let toastList = toastElList.map(function (toastEl) {
        return new bootstrap.Toast(toastEl, { autohide: true })
    });

    let toastMessage = document.getElementById('toast-message');
    if (toastMessage && toastMessage.textContent.trim() !== '') {
        toastList.forEach(toast => toast.show());
    }
});