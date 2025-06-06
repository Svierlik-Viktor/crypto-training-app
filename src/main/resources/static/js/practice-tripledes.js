
async function processTripleDes(endpoint) {
    const text = document.getElementById("text").value.trim();
    const key = document.getElementById("key").value.trim();
    const resultDiv = document.getElementById("result");

    // Очистити попередній результат
    resultDiv.innerHTML = "";
    document.getElementById("explanation")?.remove?.();

    // Перевірки
    if (!text) {
        resultDiv.textContent = "Будь ласка, введіть текст.";
        return;
    }

    if (!isValidTripleDesText(text)) {
        resultDiv.textContent = "Текст містить недопустимі символи.";
        return;
    }

    if (!key) {
        resultDiv.textContent = "Будь ласка, введіть ключ.";
        return;
    }

    if (key.length !== 24) {
        resultDiv.textContent = "Ключ має бути довжиною 24 символи.";
        return;
    }

    if (!isValidTripleDesKey(key)) {
        resultDiv.textContent = "Ключ повинен містити лише англійські літери, цифри та спецсимволи.";
        return;
    }

    resultDiv.innerHTML = "Обробка...";

    try {
        const response = await fetch(`/api/tripledes/${endpoint}`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ text, key })
        });

        const content = await response.text();
        resultDiv.innerHTML = content;

        const algorithm = "Triple DES";
        const inputText = text;
        const outputText = extractOutputFromHTML(content);
        const explanation = content;

        saveResult(algorithm, inputText, key, outputText, explanation);
    } catch (error) {
        resultDiv.innerHTML = "<span style='color:red;'>Помилка при запиті до сервера.</span>";
        console.error("TripleDES error:", error);
    }
}

function extractOutputFromHTML(html) {
    const tempDiv = document.createElement("div");
    tempDiv.innerHTML = html;
    const codeTags = tempDiv.querySelectorAll("code");
    if (codeTags.length > 0) {
        return codeTags[codeTags.length - 1].innerText;
    }
    return "[Результат не знайдено]";
}

document.getElementById("encryptBtn").addEventListener("click", () => {
    processTripleDes("encrypt");
});

document.getElementById("decryptBtn").addEventListener("click", () => {
    processTripleDes("decrypt");
});