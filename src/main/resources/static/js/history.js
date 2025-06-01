function loadResults() {
    const container = document.getElementById("results-container");
    const entries = JSON.parse(localStorage.getItem("cryptoPracticeResults")) || [];

    container.innerHTML = ""; // очищення перед новим завантаженням

    if (entries.length === 0) {
        container.innerHTML = "<p>Немає збережених результатів.</p>";
        return;
    }

    entries.reverse().forEach((entry, index) => {
        const actualIndex = entries.length - 1 - index; // реальний індекс у сховищі
        const div = document.createElement("div");
        div.className = "entry";
        div.innerHTML = `
            <div class="timestamp">🕒 ${entry.timestamp}</div>
            <pre><strong>Алгоритм:</strong> ${entry.algorithm}\n\n<strong>Вхідні дані:</strong> ${entry.input}\n<strong>Ключ:</strong> ${entry.key}\n<strong>Результат:</strong> ${entry.result}\n\n<strong>Пояснення:</strong>\n${entry.explanation}</pre>
            <button onclick="deleteResult(${actualIndex})">Видалити</button>
        `;
        container.appendChild(div);
    });
}

function deleteResult(index) {
    const entries = JSON.parse(localStorage.getItem("cryptoPracticeResults")) || [];
    if (index >= 0 && index < entries.length) {
        entries.splice(index, 1);
        localStorage.setItem("cryptoPracticeResults", JSON.stringify(entries));
        loadResults();
    }
}


function clearResults() {
    const confirmOverlay = document.getElementById("customConfirm");
    confirmOverlay.style.display = "flex";

    document.getElementById("confirmYes").onclick = () => {
        localStorage.removeItem("cryptoPracticeResults");
        loadResults();
        confirmOverlay.style.display = "none";
    };

    document.getElementById("confirmNo").onclick = () => {
        confirmOverlay.style.display = "none";
    };
}


loadResults();