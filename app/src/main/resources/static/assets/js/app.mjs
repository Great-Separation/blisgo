const t=window.location.pathname;document.addEventListener("DOMContentLoaded",(function(){var t=document.title;t=t.split("-",1),document.getElementById("page-title").innerText=t,"분리ㅅㄱ"==t&&(document.getElementById("navbar-mobile-arrow").style.display="none",document.getElementById("page-title").innerText="🪴")})),document.body.addEventListener("htmx:configRequest",(function(t){if(t.detail.headers.accept="text/html-partial","get"!==t.detail.verb){const e=document.querySelector("meta[name='_csrf_header']").getAttribute("content");t.detail.headers[e]=document.querySelector("meta[name='_csrf']").getAttribute("content")}})),("/"===t||t.startsWith("/dictionary"))&&import("./dictionary/algolia.mjs").then((t=>{t.init()})),t.startsWith("/community/")&&(import("./community/editorjs.umd.min.js").then((()=>{import("./community/editor.mjs").then((t=>{t.init()}))})),import("./community/jquery.timeago.min.js").then((()=>{import("./community/time-ago.mjs").then((t=>{t.init()}))}))),t.startsWith("/community/write")&&import("./community/post-color-changer.mjs").then((t=>{t.init()}));