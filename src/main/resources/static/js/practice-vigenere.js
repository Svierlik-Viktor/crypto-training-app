function isEnglishOnly(text) {
    return /^[a-zA-Z\s.,!?'"-]+$/.test(text);
}

async function vigenereRequest(endpoint, text, key) {
    try {
        const response = await fetch(`/api/vigenere/${endpoint}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ text, key })
        });

        if (!response.ok) {
            throw new Error("Сервер повернув помилку");
        }

        const resultJson = await response.json();
        return resultJson;
    } catch (error) {
        console.error(`Помилка при запиті до /vigenere/${endpoint}:`, error);
        return { result: "", explanation: "Помилка при запиті до сервера." };
    }
}

document.getElementById('encryptBtn').addEventListener('click', async () => {
    const text = document.getElementById('textInput').value.trim();
    const key = document.getElementById('keyInput').value.trim();
    const resultDiv = document.getElementById('result');
    const stepsDiv = document.getElementById('steps');

    resultDiv.innerHTML = "";
    stepsDiv.innerHTML = "";

    if (!text) {
        resultDiv.textContent = "Будь ласка, введіть текст для шифрування.";
        return;
    }

    if (!key) {
        resultDiv.textContent = "Будь ласка, введіть ключ для шифрування.";
        return;
    }

    if (!isEnglishOnly(text)) {
        resultDiv.textContent = "Текст має містити лише англійські літери, без цифр чи спеціальних символів.";
        return;
    }

    if (!isEnglishOnly(key)) {
        resultDiv.textContent = "Ключ має містити лише англійські літери без цифр чи спеціальних символів.";
        return;
    }

    const response = await vigenereRequest("encrypt", text, key);
    if (response.result) {
        resultDiv.innerHTML = `<strong>Результат:</strong> ${response.result}`;
        stepsDiv.innerHTML = response.explanation;
        saveResult("Vigenère Cipher", text, key, response.result, response.explanation);
    } else {
        resultDiv.textContent = "Помилка при шифруванні.";
    }
});

document.getElementById('decryptBtn').addEventListener('click', async () => {
    const text = document.getElementById('textInput').value.trim();
    const key = document.getElementById('keyInput').value.trim();
    const resultDiv = document.getElementById('result');
    const stepsDiv = document.getElementById('steps');

    resultDiv.innerHTML = "";
    stepsDiv.innerHTML = "";

    if (!text) {
        resultDiv.textContent = "Будь ласка, введіть текст для розшифрування.";
        return;
    }

    if (!key) {
        resultDiv.textContent = "Будь ласка, введіть ключ для розшифрування.";
        return;
    }

    if (!isEnglishOnly(text)) {
        resultDiv.textContent = "Текст має містити лише англійські літери, без цифр чи спеціальних символів.";
        return;
    }

    if (!isEnglishOnly(key)) {
        resultDiv.textContent = "Ключ має містити лише англійські літери без цифр чи спеціальних символів.";
        return;
    }

    const response = await vigenereRequest("decrypt", text, key);
    if (response.result) {
        resultDiv.innerHTML = `<strong>Результат:</strong> ${response.result}`;
        stepsDiv.innerHTML = response.explanation;
        saveResult("Vigenère Cipher", text, key, response.result, response.explanation);
    } else {
        resultDiv.textContent = "Помилка при розшифруванні.";
    }
});