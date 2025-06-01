document.getElementById("hashBtn").addEventListener("click", async () => {
    const text = document.getElementById("md5Input").value.trim();
    const resultDiv = document.getElementById("md5Result");

    if (!text) {
        resultDiv.textContent = "Будь ласка, введіть текст для хешування.";
        return;
    }

    try {
        const response = await fetch("/api/md5/hash", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ text })
        });

        if (!response.ok) {
            throw new Error("Помилка відповіді сервера");
        }

        const resultHtml = await response.text();

        // Витягаємо лише сам хеш з HTML
        const hashMatch = resultHtml.match(/([a-f0-9]{32})/i);
        const hashValue = hashMatch ? hashMatch[1] : "Невідомо";

        resultDiv.innerHTML = resultHtml;

        // Зберігаємо результат
        saveResult({
            algorithm: "MD5",
            input: text,
            key: "Ключ не використовується",
            result: hashValue,
            explanation: resultHtml.replace(`Результат хешування (MD5, hex):<br><pre>${hashValue}</pre>`, "")
        });

    } catch (error) {
        resultDiv.textContent = "Помилка під час хешування.";
    }
});