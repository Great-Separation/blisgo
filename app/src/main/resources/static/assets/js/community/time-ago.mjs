export function init() {
    let lang = navigator.language || navigator.language;
    lang = lang.split('-')[0];
    $.getScript("https://cdn.jsdelivr.net/npm/timeago/locales/jquery.timeago." + lang + ".js")
        .done(function () {
            document.body.addEventListener('htmx:afterOnLoad', function () {
                $("time.timeago").timeago();
            });
        })
        .fail(function () {
            document.body.addEventListener('htmx:afterOnLoad', function () {
                $("time.timeago").timeago();
            });
        });
}