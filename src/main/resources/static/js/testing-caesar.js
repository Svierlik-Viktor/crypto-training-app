function checkAnswers() {
    let correctAnswers = {
        q1: "c",
        q2: "a",
        q3: "c",
        q4: "b",
        q5: "c"
    };

    let score = 0;
    let totalQuestions = Object.keys(correctAnswers).length;

    for (let question in correctAnswers) {
        let selected = document.querySelector(`input[name="${question}"]:checked`);
        if (selected && selected.value === correctAnswers[question]) {
            score++;
        }
    }

    let resultText = `Ваш результат: ${score} / ${totalQuestions}`;
    let resultDiv = document.getElementById("result");
    resultDiv.innerText = resultText;
    resultDiv.style.backgroundColor = score >= 3 ? "#32CD32" : "#DC143C";
    resultDiv.style.color = "white";
}