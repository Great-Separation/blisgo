export function init(){let t=navigator.language||navigator.language;t=t.split("-")[0],$.getScript("https://cdn.jsdelivr.net/npm/timeago/locales/jquery.timeago."+t+".js").done((function(){document.body.addEventListener("htmx:afterOnLoad",(function(){$("time.timeago").timeago()}))})).fail((function(){document.body.addEventListener("htmx:afterOnLoad",(function(){$("time.timeago").timeago()}))}))}