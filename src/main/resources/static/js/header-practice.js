// так же само і в practice-aes.html, practice-caesar.html, practice-columnar.html, practice-ecdsa.html,
// practice-md5.html, practice-rsa.html, practice-sha256.html, practice-tripledes.html, practice-vigenere.html,
//

const themeToggle = document.getElementById('themeToggle');
const body = document.body;

// Перевірка збереженої теми при завантаженні сторінки
const savedTheme = localStorage.getItem('theme');
if (savedTheme) {
    applyTheme(savedTheme);
}

// Обробник кліку для перемикання теми
themeToggle.addEventListener('click', () => {
    const newTheme = body.classList.contains('night') ? 'day' : 'night'; // Змінюємо тему
    applyTheme(newTheme);
    localStorage.setItem('theme', newTheme); // Зберігаємо вибір у localStorage
});

function applyTheme(theme) {
    const preElements = document.querySelectorAll('pre');
    const codeElements = document.querySelectorAll('code');
    const container = document.querySelector('.container');
    const result = document.getElementById('result'); // Блок result

    if (theme === 'night') {
        body.classList.add('night');
        body.style.backgroundColor = '#2F4F4F'; // DarkSlateGray
        body.style.color = '#F5FFFA'; // MintCream
        document.querySelectorAll('h3').forEach(el => el.style.color = '#98FB98'); // PaleGreen
        document.querySelectorAll('h2').forEach(el => el.style.color = '#90EE90'); // PaleGreen
        document.querySelectorAll('h1').forEach(el => el.style.color = '#90EE90'); // PaleGreen

        preElements.forEach(el => {
            el.style.backgroundColor = '#4B4B4B'; // Darker background for night theme
            el.style.borderLeftColor = '#90EE90'; // Change border color to match night theme
        });

        codeElements.forEach(el => {
            el.style.backgroundColor = '#666'; // Darker background for code in night theme
            el.style.color = '#FFF'; // Light text color
        });

        if (container) {
            container.style.backgroundColor = '#4B4B4B'; // Darker background for container
            container.style.color = '#F5FFFA'; // Light text color for container
        }

        if (result) {
            result.style.backgroundColor = '#4B4B4B'; // Darker background for result block
            result.style.color = '#F5FFFA'; // Light text color for result block
        }

        themeToggle.classList.add('night'); // Нічна тема для кнопки
        themeToggle.style.backgroundColor = 'rgba(255, 255, 255, 0.14)';
        themeToggle.querySelector('.toggle-icon').style.transform = 'translateX(34px)';
        themeToggle.querySelector('.toggle-icon').style.backgroundColor = 'white';
        themeToggle.querySelector('.cloud').style.opacity = '0';
        themeToggle.querySelector('.stars').style.display = 'block';
    } else {
        body.classList.remove('night');
        body.style.backgroundColor = '#F5F5DC'; // Beige
        body.style.color = '#000000'; // Black
        document.querySelectorAll('h3').forEach(el => el.style.color = '#6B8E23'); // DarkOliveGreen
        document.querySelectorAll('h2').forEach(el => el.style.color = '#808000'); // Olive
        document.querySelectorAll('h1').forEach(el => el.style.color = '#808000'); // Olive

        preElements.forEach(el => {
            el.style.backgroundColor = '#F8F8F8'; // Light background for day theme
            el.style.borderLeftColor = '#2E8B57'; // Original green border
        });

        codeElements.forEach(el => {
            el.style.backgroundColor = '#EEE'; // Light background for code in day theme
            el.style.color = '#000'; // Dark text color
        });

        if (container) {
            container.style.backgroundColor = '#FFF'; // Light background for container
            container.style.color = '#000'; // Dark text color for container
        }

        if (result) {
            result.style.backgroundColor = '#F8F8F8'; // Light background for result block
            result.style.color = '#000000'; // Dark text color for result block
        }

        themeToggle.classList.remove('night'); // Денна тема для кнопки
        themeToggle.style.backgroundColor = '#2196F3';
        themeToggle.querySelector('.toggle-icon').style.transform = 'translateX(0px)';
        themeToggle.querySelector('.toggle-icon').style.backgroundColor = 'yellow';
        themeToggle.querySelector('.cloud').style.opacity = '1';
        themeToggle.querySelector('.stars').style.display = 'none';
    }
}