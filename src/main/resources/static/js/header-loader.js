
fetch('/header.html')
    .then(response => response.text())
    .then(html => {
        document.getElementById('site-header').innerHTML = html;
        // Завантажуємо скрипт після вставлення header
        const script = document.createElement('script');
        script.src = '/js/header-unified.js';
        document.body.appendChild(script);
    });