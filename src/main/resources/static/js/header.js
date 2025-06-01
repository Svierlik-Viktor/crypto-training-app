// так ж як у history.html, home.html, practice.html, testing.html, theory.html,

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

// Функція застосування теми
function applyTheme(theme) {
    if (theme === 'night') {
        body.classList.add('night');
        body.style.backgroundColor = '#2F4F4F'; // DarkSlateGray
        body.style.color = '#F5FFFA'; // MintCream
        document.querySelectorAll('h3').forEach(el => el.style.color = '#98FB98'); // PaleGreen
        document.querySelectorAll('h2').forEach(el => el.style.color = '#90EE90'); // PaleGreen
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
        themeToggle.classList.remove('night'); // Денна тема для кнопки
        themeToggle.style.backgroundColor = '#2196F3';
        themeToggle.querySelector('.toggle-icon').style.transform = 'translateX(0px)';
        themeToggle.querySelector('.toggle-icon').style.backgroundColor = 'yellow';
        themeToggle.querySelector('.cloud').style.opacity = '1';
        themeToggle.querySelector('.stars').style.display = 'none';
    }
}