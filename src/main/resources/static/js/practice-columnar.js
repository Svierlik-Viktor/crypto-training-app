
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

        //  Збереження результату
        const explanationHTML = explanationDiv.innerHTML;
        saveResult("Columnar Transposition Cipher", text, key, data.result, explanationHTML);

    } catch (error) {
        resultDiv.textContent = "Помилка при обробці запиту.";
        explanationDiv.innerHTML = "";
        console.error("Error:", error);
    }
}

function renderExplanation(data) {
    const { key, order, matrix, rearrangedMatrix, decryptedMatrix } = data;
    let html = `
        <div class="explanation-section">
            <p><strong>Ключ:</strong> ${key.join(' ')}</p>
            <p><strong>Порядок стовпців:</strong> ${order.map(i => i + 1).join(' → ')}</p>
            ${renderMatrix("Початкова матриця", matrix)}
    `;

    if (rearrangedMatrix) {
        html += renderMatrix("Матриця після перестановки (шифрування)", rearrangedMatrix);
    }
    if (decryptedMatrix) {
        html += renderMatrix("Матриця для дешифрування", decryptedMatrix);
    }

    document.getElementById("explanation").innerHTML = html + '</div>';
}

function renderMatrix(title, matrix) {
    return `
        <div class="matrix-container">
            <h4>${title}</h4>
            <table class="matrix-table">
                ${matrix.map((row, rowIndex) => `
                    <tr>
                        ${row.map((cell, colIndex) => `
                            <td class="${rowIndex === 0 ? 'header-cell' : 'data-cell'}">
                                ${cell}
                            </td>
                        `).join('')}
                    </tr>
                `).join('')}
            </table>
        </div>
    `;
}

document.getElementById("encryptBtn").addEventListener("click", () => handleColumnarRequest("encrypt"));
document.getElementById("decryptBtn").addEventListener("click", () => handleColumnarRequest("decrypt"));