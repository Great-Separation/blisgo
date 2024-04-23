const algoliasearch = window['algoliasearch'];
const {autocomplete, getAlgoliaResults} = window["@algolia/autocomplete-js"];

const appId = 'DZSY6U0S0J';
const apiKey = '6558cbc4f72828fe1cdad3d2a87264cb';
const searchClient = algoliasearch(appId, apiKey);

function debouncePromise(fn, time) {
    let timerId = undefined;

    return function debounced(...args) {
        if (timerId) {
            clearTimeout(timerId);
        }

        return new Promise((resolve) => {
            timerId = setTimeout(() => resolve(fn(...args)), time);
        });
    };
}

const debounced = debouncePromise((items) => Promise.resolve(items), 200);

window.onload = function () {
    document.body.setAttribute('data-theme', document.documentElement.getAttribute('data-bs-theme'));
    let lang = navigator.language || navigator.userLanguage;
    lang = lang.split('-')[0];
    
    if ($('#autocomplete').length > 0) {
        $(autocomplete({
            container: '#autocomplete',
            placeholder: '🛠️,👕, ...',
            openOnFocus: false,
            getSources({query}) {
                return debounced([
                    {
                        getItems() {
                            return getAlgoliaResults({
                                searchClient,
                                queries: [
                                    {
                                        indexName: ('wastes' + '_' + lang),
                                        query,
                                        params: {
                                            hitsPerPage: 6
                                        }
                                    }
                                ]
                            });
                        },
                        getItemUrl({item}) {
                            return "dictionary/" + item.wasteId;
                        },
                        templates: {
                            detachedCancelButtonText: "X",
                            item({item, components, html}) {
                                return html`
                                    <a href="dictionary/${item.wasteId}" class="text-decoration-none text-body">
                                        <div class="aa-ItemWrapper">
                                            <div class="aa-ItemContent"><img
                                                    src="${item.picture}"
                                                    alt="${item.name}"
                                                    width="50"
                                                    height="50"
                                            />
                                                <div class="aa-ItemContentBody">
                                                    <div class="aa-ItemContentTitle">
                                                        ${components.Highlight({
                                                            hit: item,
                                                            attribute: 'name'
                                                        })}
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </a>
                                `;
                            },
                            noResults() {
                                return '｡ﾟ(ﾟ´ω`ﾟ)ﾟ｡💦';
                            }
                        }
                    }
                ]);
            }
        }));
    }
};