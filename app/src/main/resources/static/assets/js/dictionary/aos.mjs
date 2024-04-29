import AOS from 'https://cdn.jsdelivr.net/npm/aos/+esm';

export function init() {
    AOS.init({duration: 250, offset: 20, delay: 0, once: true});
}