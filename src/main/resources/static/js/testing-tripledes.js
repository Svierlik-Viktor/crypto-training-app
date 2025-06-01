function checkAnswers() {
    let correctAnswers = {
        q1: "b",
        q2: "b",
        q3: "a",
        q4: "b",
        q5: "c"
    };

    let score = 0;
    let totalQuestions = Object.keys(correctAnswers).length;

    for (let question in correctAnswers) {
        let selectedOption = document.querySelector(`input[name="${question}"]:checked`);
        if (selectedOption && selectedOption.value === correctAnswers[question]) {
            score++;
        }
    }

    let resultText = `Ваш результат: ${score} / ${totalQuestions}`;
    let resultDiv = document.getElementById("result");

    resultDiv.innerText = resultText;
    resultDiv.style.backgroundColor = score >= 3 ? "#32CD32" : "#DC143C"; // Зелений або червоний фон
    resultDiv.style.color = "white";
}