$(document).ready(function() {
    var lang = navigator.language || navigator.language; 
    lang = lang.split('-')[0]; // "en-US"와 같은 형태를 "en"으로 변환

    // 로케일 파일 로드
    $.getScript("https://cdn.jsdelivr.net/npm/timeago/locales/jquery.timeago." + lang + ".js")
    .done(function() {
        // 로케일 파일 로드 성공 시 timeago 적용
        document.body.addEventListener('htmx:afterOnLoad', function() {
            $("time.timeago").timeago();
        });
    })
    .fail(function() {
        // 로케일 파일 로드 실패 시 (지원하지 않는 언어 등) 기본 언어로 timeago 적용
        document.body.addEventListener('htmx:afterOnLoad', function() {
            $("time.timeago").timeago();
        });
    });
});