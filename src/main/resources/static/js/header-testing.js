// так же само і в testing-caesar.html, testing-columnar.html, testing-ecdsa.html,

const themeToggle = document.getElementById('themeToggle');
const body = document.body;
const container = document.querySelector('.container');
const resultDiv = document.getElementById('result');

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
        body.style.backgroundColor = '#2F4F4F'; // Темний фон
        body.style.color = '#F5FFFA'; // Світлий текст
        document.querySelectorAll('h3').forEach(el => el.style.color = '#98FB98'); // Блідо-зелений заголовок
        document.querySelectorAll('h2').forEach(el => el.style.color = '#90EE90'); // Блідо-зелений заголовок

        // Темний режим для контейнера
        if (container) {
            container.style.backgroundColor = '#4B4B4B'; // Темно-сірий фон контейнера
            container.style.color = '#F5FFFA'; // Світлий текст контейнера
        }

        // Темний режим для результату
        if (resultDiv) {
            resultDiv.style.backgroundColor = '#666'; // Темний фон
            resultDiv.style.color = '#FFF'; // Білий текст
        }

        themeToggle.classList.add('night'); // Нічна тема для кнопки
        themeToggle.style.backgroundColor = 'rgba(255, 255, 255, 0.14)';
        themeToggle.querySelector('.toggle-icon').style.transform = 'translateX(34px)';
        themeToggle.querySelector('.toggle-icon').style.backgroundColor = 'white';
        themeToggle.querySelector('.cloud').style.opacity = '0';
        themeToggle.querySelector('.stars').style.display = 'block';
    } else {
        body.classList.remove('night');
        body.style.backgroundColor = '#F5F5DC'; // Бежевий фон
        body.style.color = '#000000'; // Чорний текст
        document.querySelectorAll('h3').forEach(el => el.style.color = '#6B8E23'); // Темно-оливковий
        document.querySelectorAll('h2').forEach(el => el.style.color = '#808000'); // Оливковий

        // Світлий режим для контейнера
        if (container) {
            container.style.backgroundColor = '#FFF'; // Білий фон контейнера
            container.style.color = '#000'; // Чорний текст контейнера
        }

        // Світлий режим для результату
        if (resultDiv) {
            resultDiv.style.backgroundColor = '#F8F8F8'; // Світло-сірий фон
            resultDiv.style.color = '#000'; // Чорний текст
        }

        themeToggle.classList.remove('night'); // Денна тема для кнопки
        themeToggle.style.backgroundColor = '#2196F3';
        themeToggle.querySelector('.toggle-icon').style.transform = 'translateX(0px)';
        themeToggle.querySelector('.toggle-icon').style.backgroundColor = 'yellow';
        themeToggle.querySelector('.cloud').style.opacity = '1';
        themeToggle.querySelector('.stars').style.display = 'none';
    }
}