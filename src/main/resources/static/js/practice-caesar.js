function isEnglishText(text) {
    return /^[a-zA-Z\s.,!?'"-]*$/.test(text); // допускаємо англійські літери, пробіли і базову пунктуацію
}

document.getElementById("encryptBtn").addEventListener("click", async () => {
    const text = document.getElementById("caesarText").value.trim();
    const shift = document.getElementById("shift").value.trim();

    const resultDiv = document.getElementById("result");

    if (!text) {
        resultDiv.textContent = "Будь ласка, введіть текст для шифрування.";
        return;
    }

    if (!shift) {
        resultDiv.textContent = "Будь ласка, вкажіть зсув.";
        return;
    }

    if (!isEnglishText(text)) {
        resultDiv.textContent = "Текст має містити лише англійські літери, без цифр чи спеціальних символів.";
        return;
    }

    try {
        const response = await fetch("/api/caesar/encrypt", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ text, shift })
        });

        const data = await response.json();
        resultDiv.innerHTML =
            `<strong>Результат шифрування:</strong> ${data.resultText}<br/><br/>
       <strong>Пояснення:</strong><br/><pre>${data.explanation}</pre>`;

        saveResult("Caesar Cipher", text, shift, data.resultText, data.explanation);
    } catch (error) {
        resultDiv.textContent = "Помилка при шифруванні.";
    }
});

document.getElementById("decryptBtn").addEventListener("click", async () => {
    const text = document.getElementById("caesarText").value.trim();
    const shift = document.getElementById("shift").value.trim();

    const resultDiv = document.getElementById("result");

    if (!text) {
        resultDiv.textContent = "Будь ласка, введіть текст для розшифрування.";
        return;
    }

    if (!shift) {
        resultDiv.textContent = "Будь ласка, вкажіть зсув.";
        return;
    }

    if (!isEnglishText(text)) {
        resultDiv.textContent = "Текст має містити лише англійські літери, без цифр чи спеціальних символів.";
        return;
    }

    try {
        const response = await fetch("/api/caesar/decrypt", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ text, shift })
        });

        const data = await response.json();
        resultDiv.innerHTML =
            `<strong>Результат розшифрування:</strong> ${data.resultText}<br/><br/>
       <strong>Пояснення:</strong><br/><pre>${data.explanation}</pre>`;

        saveResult("Caesar Cipher", text, shift, data.resultText, data.explanation);
    } catch (error) {
        resultDiv.textContent = "Помилка при розшифруванні.";
    }
});