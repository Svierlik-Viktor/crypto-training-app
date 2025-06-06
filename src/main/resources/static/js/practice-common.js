// Загальні функції перевірки

function isEnglishOnly(text) {
    return /^[a-zA-Z\s.,!?'"-]+$/.test(text);
}

function isUkrainianOrEnglish(text) {
    return /^[a-zA-Zа-яА-ЯёЁіІїЇєЄґҐ0-9\s.,!?'"():;[\]{}<>@#%^&*+=_\-\\|/~`$]*$/.test(text);
}

function isUkrainian(text) {
    return /[а-яіїєґА-ЯІЇЄҐ]/.test(text);
}

function isValidAesText(text) {
return isUkrainianOrEnglish(text);
}

function isValidUkrainianKey(key) {
return /^[а-яіїєґА-ЯІЇЄҐ]{16}$/.test(key);
}

function isValidEnglishKey(key) {
    return /^[a-zA-Z0-9!@#$%^&*()_+-={};':"\|,.<>/?]{16}$/.test(key);
}

function isValidTripleDesText(text) {
return isUkrainianOrEnglish(text);
}

function isValidTripleDesKey(key) {
return /^[a-zA-Z0-9!@#$%^&*()_+-={};':"\|,.<>/?]{24}$/.test(key) && !isUkrainian(key);
}

function containsUkrainian(text) {
    return /[а-яіїєґА-ЯІЇЄҐ]/.test(text);
}

// Збереження результату у localStorage
function saveResult(algorithm, input, key, result, explanation) {
    const previous = JSON.parse(localStorage.getItem("cryptoPracticeResults")) || [];
    const entry = {
        algorithm,
        input,
        key,
        result,
        explanation,
        timestamp: new Date().toLocaleString()
    };
    previous.push(entry);
    localStorage.setItem("cryptoPracticeResults", JSON.stringify(previous));
}


// Функція для адаптації меню (hamburger)
function updatemenu() {
    if (document.getElementById('responsive-menu').checked == true) {
        document.getElementById('menu').style.borderBottomRightRadius = '0';
        document.getElementById('menu').style.borderBottomLeftRadius = '0';
    }else{
        document.getElementById('menu').style.borderRadius = '50px';
    }
}