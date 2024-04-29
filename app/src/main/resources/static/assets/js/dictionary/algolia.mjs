import algoliasearch from 'https://cdn.jsdelivr.net/npm/algoliasearch/+esm';
import {autocomplete, getAlgoliaResults} from 'https://cdn.jsdelivr.net/npm/@algolia/autocomplete-js/+esm';

const appId = 'DZSY6U0S0J';
const apiKey = '6558cbc4f72828fe1cdad3d2a87264cb';
const searchClient = algoliasearch(appId, apiKey);
const debounced = debouncePromise((items) => Promise.resolve(items), 200);

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

export function init() {
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
                            item({item, components, html}) {
                                return html`
                                    <a href="dictionary/${item.wasteId}" class="btn p-1">
                                        <div class="aa-ItemWrapper">
                                            <div class="aa-ItemContent"><img
                                                    src="${item.picture}"
                                                    alt="${item.name}"
                                                    width="50px"
                                                    height="50px"
                                            />
                                                <div class="aa-ItemContentBody">
                                                    <div class="aa-ItemContentTitle">
                                                        <span>
                                                            ${components.Highlight({
                                                                hit: item,
                                                                attribute: 'name'
                                                            })}
                                                        </span>
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
}