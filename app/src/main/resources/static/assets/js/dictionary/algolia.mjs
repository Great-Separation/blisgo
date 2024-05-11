import e from"https://cdn.jsdelivr.net/npm/algoliasearch/+esm";import{autocomplete as t,getAlgoliaResults as a}from"https://cdn.jsdelivr.net/npm/@algolia/autocomplete-js/+esm";const n=e("DZSY6U0S0J","6558cbc4f72828fe1cdad3d2a87264cb"),i=function(e,t){let a;return function(...n){return a&&clearTimeout(a),new Promise((i=>{a=setTimeout((()=>i(e(...n))),t)}))}}((e=>Promise.resolve(e)),200);export function init(){document.body.setAttribute("data-theme",document.documentElement.getAttribute("data-bs-theme"));let e=navigator.language||navigator.userLanguage;e=e.split("-")[0],document.getElementById("autocomplete")&&new t({container:"#autocomplete",placeholder:"🛠️,👕, ...",openOnFocus:!1,getSources:({query:t})=>i([{getItems:()=>a({searchClient:n,queries:[{indexName:"wastes_"+e,query:t,params:{hitsPerPage:6}}]}),getItemUrl:({item:e})=>"dictionary/"+e.wasteId,templates:{item:({item:e,components:t,html:a})=>a`
                                    <a href="dictionary/${e.wasteId}" class="btn p-1">
                                        <div class="aa-ItemWrapper">
                                            <div class="aa-ItemContent"><img
                                                    src="${e.picture}"
                                                    alt="${e.name}"
                                                    width="50px"
                                                    height="50px"
                                            />
                                                <div class="aa-ItemContentBody">
                                                    <div class="aa-ItemContentTitle">
                                                        <span>
                                                            ${t.Highlight({hit:e,attribute:"name"})}
                                                        </span>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </a>
                                `,noResults:()=>"｡ﾟ(ﾟ´ω`ﾟ)ﾟ｡💦"}}])})}