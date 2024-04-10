document.addEventListener("htmx:afterOnLoad", function () {
    var toastElList = [].slice.call(document.querySelectorAll('.toast'))
    var toastList = toastElList.map(function (toastEl) {
        return new bootstrap.Toast(toastEl, { autohide: true })
    });

    var toastMessage = document.getElementById('toast-message');
    if (toastMessage && toastMessage.textContent.trim() !== '') {
        toastList.forEach(toast => toast.show());
    }
});