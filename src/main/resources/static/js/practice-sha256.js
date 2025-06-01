document.getElementById('hashBtn').addEventListener('click', async () => {
    const inputText = document.getElementById('textInput').value;
    const resultDiv = document.getElementById('result');

    if (!inputText.trim()) {
        resultDiv.textContent = 'Будь ласка, введіть текст для хешування.';
        return;
    }

    try {
        const response = await fetch('http://localhost:8080/api/crypto/sha256', {
            method: 'POST',
            headers: {
                'Content-Type': 'text/plain',
            },
            body: inputText
        });

        if (!response.ok) {
            throw new Error('Помилка при запиті до сервера');
        }

        const resultHtml = await response.text();
        resultDiv.innerHTML = resultHtml;

        // ✅ Витягуємо хеш-значення з HTML (наприклад, із <pre>)
        const hashMatch = resultHtml.match(/([a-f0-9]{64})/i);
        const hashValue = hashMatch ? hashMatch[1] : "Невідомо";

        // ✅ Зберігаємо результат у localStorage
        saveResult({
            algorithm: "SHA-256",
            input: `${inputText}`,
            key: "Ключ не використовується",
            result: `${hashValue}`,
            explanation: resultHtml.replace(`Результат хешування (SHA-256, hex):<br><pre>${hashValue}</pre>`, "")
        });

    } catch (error) {
        resultDiv.textContent = 'Сталася помилка: ' + error.message;
    }
});