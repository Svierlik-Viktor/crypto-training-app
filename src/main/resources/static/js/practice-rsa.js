function isUkrainianOrEnglish(text) {
    return /^[a-zA-Zа-яА-ЯёЁіІїЇєЄґҐ0-9\s.,!?'"():;[\]{}<>@#%^&*+=_\-\\|/~`$]*$/.test(text);
}

document.getElementById("generateKeysBtn").addEventListener("click", async () => {
    try {
        const response = await fetch("/api/rsa/generate");
        if (!response.ok) throw new Error("Помилка відповіді сервера");

        const data = await response.json();
        document.getElementById("publicKey").value = data.publicKey;
        document.getElementById("privateKey").value = data.privateKey;
        document.getElementById("result").innerHTML = "<b>Ключі згенеровано успішно.</b>";
    } catch (error) {
        console.error("Помилка генерації ключів:", error);
        document.getElementById("result").textContent = "Сталася помилка при генерації ключів.";
    }
});

document.getElementById("encryptBtn").addEventListener("click", async () => {
    const text = document.getElementById("textInput").value.trim();
    const publicKey = document.getElementById("publicKey").value.trim();
    const resultDiv = document.getElementById("result");

    resultDiv.innerHTML = "";

    if (!publicKey) {
        resultDiv.textContent = "Згенеруйте ключі для шифрування.";
        return;
    }

    if (!text) {
        resultDiv.textContent = "Введіть текст для шифрування.";
        return;
    }

    if (!isUkrainianOrEnglish(text)) {
        resultDiv.textContent = "Текст може містити лише літери української та англійської мов, цифри та спецсимволи.";
        return;
    }

    try {
        const response = await fetch("/api/rsa/encrypt", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ text })
        });

        if (!response.ok) throw new Error("Помилка відповіді сервера");

        const htmlResult = await response.text();
        resultDiv.innerHTML = htmlResult;

        const algorithm = "RSA";
        const inputText = text;
        const outputText = extractOutputFromHTML(htmlResult);
        const explanation = htmlResult;

        saveResult(algorithm, inputText, publicKey, outputText, explanation);
    } catch (error) {
        console.error("Помилка шифрування:", error);
        resultDiv.textContent = "Сталася помилка при шифруванні.";
    }
});

document.getElementById("decryptBtn").addEventListener("click", async () => {
    const encryptedText = document.getElementById("textInput").value.trim();
    const privateKey = document.getElementById("privateKey").value.trim();
    const resultDiv = document.getElementById("result");

    resultDiv.innerHTML = "";

    if (!privateKey) {
        resultDiv.textContent = "Згенеруйте ключі для розшифрування.";
        return;
    }

    if (!encryptedText) {
        resultDiv.textContent = "Введіть зашифрований текст для розшифрування.";
        return;
    }

    try {
        const response = await fetch("/api/rsa/decrypt", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ text: encryptedText })
        });

        if (!response.ok) throw new Error("Помилка відповіді сервера");

        const htmlResult = await response.text();
        resultDiv.innerHTML = htmlResult;

        const algorithm = "RSA";
        const inputText = encryptedText;
        const outputText = extractOutputFromHTML(htmlResult);
        const explanation = htmlResult;

        saveResult(algorithm, inputText, privateKey, outputText, explanation);
    } catch (error) {
        console.error("Помилка розшифрування:", error);
        resultDiv.textContent = "Сталася помилка при розшифруванні.";
    }
});

function extractOutputFromHTML(html) {
    const tempDiv = document.createElement("div");
    tempDiv.innerHTML = html;

    const strongTags = tempDiv.querySelectorAll("strong");

    for (const tag of strongTags) {
        const labelText = tag.innerText.trim();

        if (
            labelText === "Зашифрований текст (Base64):" ||
            labelText === "Розшифрований текст:"
        ) {
            let next = tag.nextSibling;
            while (next && next.nodeType !== Node.TEXT_NODE) {
                next = next.nextSibling;
            }
            return next ? next.textContent.trim() : "[Результат не знайдено]";
        }
    }

    return "[Результат не знайдено]";
}