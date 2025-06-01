// Генерація випадкового IV (16 байт)
function generateIV() {
    const iv = crypto.getRandomValues(new Uint8Array(16));
    return Array.from(iv).map(byte => byte.toString(16).padStart(2, '0')).join('');
}

// Функція шифрування
async function encryptText(text, key) {
    const encoder = new TextEncoder();
    const data = encoder.encode(text);
    const keyMaterial = await crypto.subtle.importKey(
        "raw",
        encoder.encode(key),
        "AES-CBC",
        false,
        ["encrypt"]
    );

    const iv = generateIV(); // Генерація IV
    const ivArray = new Uint8Array(16);
    for (let i = 0; i < iv.length; i += 2) {
        ivArray[i / 2] = parseInt(iv.substr(i, 2), 16);
    }

    const encryptedData = await crypto.subtle.encrypt(
        {
            name: "AES-CBC",
            iv: ivArray,
        },
        keyMaterial,
        data
    );

    return {
        encrypted: btoa(String.fromCharCode(...new Uint8Array(encryptedData))),
        iv: iv, // Повертаємо згенерований IV
    };
}

// Функція розшифрування
async function decryptText(encrypted, key, iv) {
    const encoder = new TextEncoder();
    const data = new Uint8Array(
        atob(encrypted)
            .split("")
            .map((char) => char.charCodeAt(0))
    );
    const keyMaterial = await crypto.subtle.importKey(
        "raw",
        encoder.encode(key),
        "AES-CBC",
        false,
        ["decrypt"]
    );

    const ivArray = new Uint8Array(16);
    for (let i = 0; i < iv.length; i += 2) {
        ivArray[i / 2] = parseInt(iv.substr(i, 2), 16);
    }

    const decryptedData = await crypto.subtle.decrypt(
        {
            name: "AES-CBC",
            iv: ivArray,
        },
        keyMaterial,
        data
    );

    return new TextDecoder().decode(decryptedData);
}

function isValidAesText(text) {
    return /^[a-zA-Zа-яА-ЯёЁіІїЇєЄґҐ0-9\s.,!?'"():;[\]{}<>@#%^&*+=_\-\\|/~`$]*$/.test(text);
}

function isUkrainian(text) {
    return /[а-яіїєґА-ЯІЇЄҐ]/.test(text);
}

function isValidUkrainianKey(key) {
    return /^[а-яіїєґА-ЯІЇЄҐ]{16}$/.test(key);
}

function isValidEnglishKey(key) {
    return /^[a-zA-Z0-9!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]{16}$/.test(key);
}

document.getElementById("encryptBtn").addEventListener("click", async () => {
    const text = document.getElementById("textInput").value.trim();
    const key = document.getElementById("keyInput").value.trim();

    const resultDiv = document.getElementById("result");
    const explanationDiv = document.getElementById("explanation");

    if (!text) {
        resultDiv.textContent = "Будь ласка, введіть текст для шифрування.";
        return;
    }

    if (!isValidAesText(text)) {
        resultDiv.textContent = "Текст містить недопустимі символи.";
        return;
    }

    if (key.length !== 16) {
        resultDiv.textContent = "Ключ має бути довжиною 16 символів.";
        return;
    }

    if (isUkrainian(key)) {
        if (!isValidUkrainianKey(key)) {
            resultDiv.textContent = "Ключ українською має містити лише літери.";
            return;
        }
    } else {
        if (!isValidEnglishKey(key)) {
            resultDiv.textContent = "Ключ англійською може містити лише англійські символи, цифри та спецсимволи.";
            return;
        }
    }

    try {
        const { encrypted, iv } = await encryptText(text, key);

        const explanationHTML =
            `<b>Кроки шифрування:</b><br>
        1 Вихідний текст: <code>${text}</code><br>
        2 Ключ (16 символів): <code>${key}</code><br>
        3 Згенерований IV: <code>${iv}</code><br>
        4 Режим: AES-CBC з PKCS#5 Padding<br>
        5 Результат (Base64): <code>${encrypted}</code>`;

        resultDiv.innerText = `Зашифрований текст (Base64): ${encrypted}`;
        explanationDiv.innerHTML = explanationHTML;
        document.getElementById("textInput").value = `${encrypted}\n${iv}`;

        saveResult("AES", text, key, encrypted, explanationHTML);
    } catch (error) {
        console.error("Помилка шифрування:", error);
        resultDiv.textContent = "Сталася помилка під час шифрування.";
        explanationDiv.innerHTML = "";
    }
});

document.getElementById("decryptBtn").addEventListener("click", async () => {
    const encryptedWithIv = document.getElementById("textInput").value.trim();
    const key = document.getElementById("keyInput").value.trim();

    const resultDiv = document.getElementById("result");
    const explanationDiv = document.getElementById("explanation");

    if (!encryptedWithIv) {
        resultDiv.textContent = "Будь ласка, введіть текст для розшифрування.";
        return;
    }

    if (!key) {
        resultDiv.textContent = "Будь ласка, введіть ключ.";
        return;
    }

    if (key.length !== 16) {
        resultDiv.textContent = "Ключ має бути довжиною 16 символів.";
        return;
    }

    if (isUkrainian(key)) {
        if (!isValidUkrainianKey(key)) {
            resultDiv.textContent = "Ключ українською має містити лише літери.";
            return;
        }
    } else {
        if (!isValidEnglishKey(key)) {
            resultDiv.textContent = "Ключ англійською може містити лише англійські символи, цифри та спецсимволи.";
            return;
        }
    }

    const parts = encryptedWithIv.split('\n');
    const encrypted = parts[0];
    const iv = parts[1];

    if (!iv) {
        resultDiv.textContent = "Не вказано IV для розшифрування.";
        return;
    }

    try {
        const decrypted = await decryptText(encrypted, key, iv);

        const explanationHTML =
            `<b>Кроки розшифрування:</b><br>
        1 Зашифрований текст: <code>${encrypted}</code><br>
        2 Ключ: <code>${key}</code><br>
        3 IV: <code>${iv}</code><br>
        4 Режим: AES-CBC з PKCS#5 Padding<br>
        5 Вивідний текст: <code>${decrypted}</code>`;

        resultDiv.innerText = `Розшифрований текст: ${decrypted}`;
        explanationDiv.innerHTML = explanationHTML;

        saveResult("AES", encrypted, key, decrypted, explanationHTML);
    } catch (error) {
        console.error("Помилка розшифрування:", error);
        resultDiv.textContent = "Сталася помилка під час розшифрування.";
        explanationDiv.innerHTML = "";
    }
});