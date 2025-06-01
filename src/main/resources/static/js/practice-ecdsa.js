const generateKeysBtn = document.getElementById("generateKeysBtn");
const signBtn = document.getElementById("signBtn");
const verifyBtn = document.getElementById("verifyBtn");

generateKeysBtn.addEventListener("click", async () => {
    try {
        const response = await fetch("/api/ecdsa/generate-keys", { method: "GET" });
        const keys = await response.json();
        document.getElementById("ecdsaPrivateKey").value = keys.privateKey;
        document.getElementById("ecdsaPublicKey").value = keys.publicKey;
        document.getElementById("result").textContent = "Ключі успішно згенеровано.";
        document.getElementById("explanation").innerHTML = "";
    } catch (error) {
        document.getElementById("result").textContent = "Помилка при генерації ключів.";
        document.getElementById("explanation").innerHTML = "";
    }
});

signBtn.addEventListener("click", async () => {
    const message = document.getElementById("ecdsaMessage").value.trim();
    const privateKey = document.getElementById("ecdsaPrivateKey").value.trim();
    const resultDiv = document.getElementById("result");
    const explanationDiv = document.getElementById("explanation");

    resultDiv.textContent = "";
    explanationDiv.innerHTML = "";

    if (!privateKey) {
        resultDiv.textContent = "Згенеруйте ключі для підпису.";
        return;
    }

    if (!message) {
        resultDiv.textContent = "Введіть повідомлення.";
        return;
    }

    try {
        const response = await fetch("/api/ecdsa/sign", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ message, privateKey })
        });

        const data = await response.json();
        document.getElementById("ecdsaSignature").value = data.base64Signature;
        resultDiv.textContent = "Підпис успішно створено ✅";
        explanationDiv.innerHTML = data.htmlFormatted;

        saveResult({
            algorithm: "ECDSA",
            input: message,
            key: `Приватний ключ:<br><pre>${privateKey}</pre>`,
            result: `Підпис (Base64):<br><pre>${data.base64Signature}</pre>`,
            explanation: data.htmlFormatted
        });

    } catch (error) {
        resultDiv.textContent = "Помилка при підписуванні.";
        explanationDiv.innerHTML = "";
    }
});

verifyBtn.addEventListener("click", async () => {
    const message = document.getElementById("ecdsaMessage").value.trim();
    const publicKey = document.getElementById("ecdsaPublicKey").value.trim();
    const signature = document.getElementById("ecdsaSignature").value.trim();
    const resultDiv = document.getElementById("result");
    const explanationDiv = document.getElementById("explanation");

    resultDiv.textContent = "";
    explanationDiv.innerHTML = "";

    if (!publicKey || !signature) {
        resultDiv.textContent = "Згенеруйте ключі та створіть підпис.";
        return;
    }

    if (!message) {
        resultDiv.textContent = "Введіть повідомлення.";
        return;
    }

    try {
        const response = await fetch("/api/ecdsa/verify", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ message, publicKey, signature })
        });

        const data = await response.json();
        resultDiv.textContent = data.valid ? "Підпис вірний ✅" : "Підпис недійсний ❌";
        explanationDiv.innerHTML = data.htmlFormatted;

        saveResult({
            algorithm: "ECDSA",
            input: `${message}<br>Підпис:<br><pre>${signature}</pre>`,
            key: `Публічний ключ:<br><pre>${publicKey}</pre>`,
            result: data.valid ? "Підпис підтверджено ✅" : "Підпис недійсний ❌",
            explanation: data.htmlFormatted
        });

    } catch (error) {
        resultDiv.textContent = "Помилка при перевірці підпису.";
        explanationDiv.innerHTML = "";
    }
});