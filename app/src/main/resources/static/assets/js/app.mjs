const path = window.location.pathname;

if (path === '/' || path.startsWith('/dictionary')) {
    import('./dictionary/algolia.mjs').then(module => {
        module.init();
    });
}

if (path.startsWith('/dictionary') || path.startsWith('/profile')) {
    import('./dictionary/aos.mjs').then(module => {
        module.init();
    });
}

if (path.startsWith('/community/')) {
    import('./community/editorjs.umd.min.js').then(() => {
        import('./community/editor.mjs').then(module => {
            module.init();
        });
    });


    import('./community/jquery.timeago.min.js').then(() => {
        import('./community/time-ago.mjs').then(module => {
            module.init();
        });
    });
}

if (path.startsWith('/community/write')) {
    import('./community/post-color-changer.mjs').then(module => {
        module.init();
    });
}