
const themeToggle = document.getElementById('themeToggle');
const body = document.body;

if (themeToggle) {
    // Apply stored theme on load
    const savedTheme = localStorage.getItem('theme');
    if (savedTheme) {
        applyTheme(savedTheme);
    }

    // Toggle theme on button click
    themeToggle.addEventListener('click', () => {
        const newTheme = body.classList.contains('night') ? 'day' : 'night';
        applyTheme(newTheme);
        localStorage.setItem('theme', newTheme);
    });
}

function applyTheme(theme) {
    const container = document.querySelector('.container');
    const result = document.getElementById('result');
    const preElements = document.querySelectorAll('pre');
    const codeElements = document.querySelectorAll('code');
    const h1s = document.querySelectorAll('h1');
    const h2s = document.querySelectorAll('h2');
    const h3s = document.querySelectorAll('h3');

    if (theme === 'night') {
        body.classList.add('night');
        body.style.backgroundColor = '#2F4F4F';
        body.style.color = '#F5FFFA';

        h1s.forEach(el => el.style.color = '#90EE90');
        h2s.forEach(el => el.style.color = '#90EE90');
        h3s.forEach(el => el.style.color = '#98FB98');

        if (container) {
            container.style.backgroundColor = '#4B4B4B';
            container.style.color = '#F5FFFA';
        }

        if (result) {
            result.style.backgroundColor = '#4B4B4B';
            result.style.color = '#F5FFFA';
        }

        preElements.forEach(el => {
            el.style.backgroundColor = '#4B4B4B';
            el.style.borderLeftColor = '#90EE90';
        });

        codeElements.forEach(el => {
            el.style.backgroundColor = '#666';
            el.style.color = '#FFF';
        });

        if (themeToggle) {
            themeToggle.classList.add('night');
            themeToggle.style.backgroundColor = 'rgba(255, 255, 255, 0.14)';
            const icon = themeToggle.querySelector('.toggle-icon');
            if (icon) {
                icon.style.transform = 'translateX(34px)';
                icon.style.backgroundColor = 'white';
            }
            const cloud = themeToggle.querySelector('.cloud');
            const stars = themeToggle.querySelector('.stars');
            if (cloud) cloud.style.opacity = '0';
            if (stars) stars.style.display = 'block';
        }
    } else {
        body.classList.remove('night');
        body.style.backgroundColor = '#F5F5DC';
        body.style.color = '#000000';

        h1s.forEach(el => el.style.color = '#808000');
        h2s.forEach(el => el.style.color = '#808000');
        h3s.forEach(el => el.style.color = '#6B8E23');

        if (container) {
            container.style.backgroundColor = '#FFF';
            container.style.color = '#000';
        }

        if (result) {
            result.style.backgroundColor = '#F8F8F8';
            result.style.color = '#000000';
        }

        preElements.forEach(el => {
            el.style.backgroundColor = '#F8F8F8';
            el.style.borderLeftColor = '#2E8B57';
        });

        codeElements.forEach(el => {
            el.style.backgroundColor = '#EEE';
            el.style.color = '#000';
        });

        if (themeToggle) {
            themeToggle.classList.remove('night');
            themeToggle.style.backgroundColor = '#2196F3';
            const icon = themeToggle.querySelector('.toggle-icon');
            if (icon) {
                icon.style.transform = 'translateX(0px)';
                icon.style.backgroundColor = 'yellow';
            }
            const cloud = themeToggle.querySelector('.cloud');
            const stars = themeToggle.querySelector('.stars');
            if (cloud) cloud.style.opacity = '1';
            if (stars) stars.style.display = 'none';
        }
    }
}
