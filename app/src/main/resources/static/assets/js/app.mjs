const t=window.location.pathname;"serviceWorker"in navigator&&window.addEventListener("load",(function(){navigator.serviceWorker.register("/sw.js").then((function(t){console.log("ServiceWorker registration successful with scope: ",t.scope)}),(function(t){console.log("ServiceWorker registration failed: ",t)}))})),("/"===t||t.startsWith("/dictionary"))&&import("./dictionary/algolia.mjs").then((t=>{t.init()})),(t.startsWith("/dictionary")||t.startsWith("/profile"))&&import("./dictionary/aos.mjs").then((t=>{t.init()})),t.startsWith("/community/")&&(import("./community/editorjs.umd.min.js").then((()=>{import("./community/editor.mjs").then((t=>{t.init()}))})),import("./community/jquery.timeago.min.js").then((()=>{import("./community/time-ago.mjs").then((t=>{t.init()}))}))),t.startsWith("/community/write")&&import("./community/post-color-changer.mjs").then((t=>{t.init()}));