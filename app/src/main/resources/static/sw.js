if ('workbox' in self) {
    // Cache CSS files
    workbox.routing.registerRoute(
        /.*\.css$/,
        new workbox.strategies.StaleWhileRevalidate({
            cacheName: 'css-cache',
        })
    );

    // Cache JavaScript files
    workbox.routing.registerRoute(
        /.*\.js$/,
        new workbox.strategies.StaleWhileRevalidate({
            cacheName: 'js-cache',
        })
    );
} else {
    console.log("Workbox not loaded");
}