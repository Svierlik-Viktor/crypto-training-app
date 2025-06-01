
function isEnglishOnly(text) {
    return /^[a-zA-Z\s.,!?'"-]+$/.test(text);
}

async function handleColumnarRequest(endpoint) {
    const text = document.getElementById("text").value.trim();
    const key = document.getElementById("key").value.trim();
    const resultDiv = document.getElementById("result");
    const explanationDiv = document.getElementById("explanation");

    resultDiv.innerHTML = "";
    explanationDiv.innerHTML = "";

    if (!text) {
        resultDiv.textContent = `Будь ласка, введіть текст для ${endpoint === "encrypt" ? "шифрування" : "розшифрування"}.`;
        return;
    }

    if (!key) {
        resultDiv.textContent = "Будь ласка, введіть ключ.";
        return;
    }

    if (!isEnglishOnly(text)) {
        resultDiv.textContent = `Текст має містити лише англійські літери, без цифр чи спеціальних символів.`;
        return;
    }

    if (!isEnglishOnly(key)) {
        resultDiv.textContent = "Ключ має містити лише англійські літери без цифр чи спеціальних символів.";
        return;
    }

    try {
        const response = await fetch(`/api/columnar/${endpoint}`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ text, key })
        });

        if (!response.ok) {
            throw new Error("Помилка від сервера");
        }

        const data = await response.json();

        resultDiv.innerHTML = `<strong>${endpoint === 'encrypt' ? 'Зашифровано' : 'Розшифровано'}:</strong> ${data.result}`;
        renderExplanation(data);

        // ✅ Збереження результату
        const explanationHTML = explanationDiv.innerHTML;
        saveResult("Columnar Transposition Cipher", text, key, data.result, explanationHTML);

    } catch (error) {
        resultDiv.textContent = "Помилка при обробці запиту.";
        explanationDiv.innerHTML = "";
        console.error("Error:", error);
    }
}

function renderExplanation(data) {
    const { key, order, matrix } = data;
    let html = `<p><strong>Ключ:</strong> ${key.join(" ")}</p>`;
    html += `<p><strong>Порядок стовпців:</strong> ${order.join(" ")}</p>`;
    html += `<table style="border-collapse: collapse; margin-top: 10px;">`;

    html += "<tr>";
    key.forEach(k => {
        html += `<th style="border: 1px solid black; padding: 5px;">${k}</th>`;
    });
    html += "</tr>";

    html += "<tr>";
    order.forEach(o => {
        html += `<td style="border: 1px solid black; padding: 5px;">${o}</td>`;
    });
    html += "</tr>";

    matrix.forEach(row => {
        html += "<tr>";
        row.forEach(cell => {
            html += `<td style="border: 1px solid black; padding: 5px;">${cell}</td>`;
        });
        html += "</tr>";
    });

    html += "</table>";
    document.getElementById("explanation").innerHTML = html;
}

document.getElementById("encryptBtn").addEventListener("click", () => handleColumnarRequest("encrypt"));
document.getElementById("decryptBtn").addEventListener("click", () => handleColumnarRequest("decrypt"));