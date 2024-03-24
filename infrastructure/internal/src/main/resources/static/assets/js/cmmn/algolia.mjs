const algoliasearch = window['algoliasearch'];
const { autocomplete, getAlgoliaResults } = window["@algolia/autocomplete-js"];

const appId = 'DZSY6U0S0J';
const apiKey = '6558cbc4f72828fe1cdad3d2a87264cb';
const searchClient = algoliasearch(appId, apiKey);

document.body.setAttribute('data-theme', document.documentElement.getAttribute('data-bs-theme'));

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

const debounced = debouncePromise((items) => Promise.resolve(items), 250);


$(autocomplete({
  container: '#autocomplete',
  placeholder: '그릇, 가방...',
  openOnFocus: false,
  getSources({ query }) {
    return debounced([
      {
        getItems() {
          return getAlgoliaResults({
            searchClient,
            queries: [
              {
                indexName: 'waste_collection',
                query,
                params: {
                  hitsPerPage: 5
                }
              }
            ]
          });
        },
        getItemUrl({ item }) {
          return "dictionary/" + item.dicNo;
        },
        templates: {
          detachedCancelButtonText: "asd",
          item({ item, components, html }) {
            return html`
            <a href="dictionary/${item.dicNo}" class="text-decoration-none text-body">
            <div class="aa-ItemWrapper">
              <div class="aa-ItemContent"><img
                    src="${item.thumbnail}"
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
            return '일치하는 검색결과가 없습니다.';
          }
        }
      }
    ]);
  }
}));