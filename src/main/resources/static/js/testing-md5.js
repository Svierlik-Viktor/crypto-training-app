function checkAnswers() {
    const correctAnswers = {
        q1: "b",
        q2: "b",
        q3: "b",
        q4: "b",
        q5: "a"
    };

    let score = 0;
    for (let q in correctAnswers) {
        const selected = document.querySelector(`input[name="${q}"]:checked`);
        if (selected && selected.value === correctAnswers[q]) {
            score++;
        }
    }

    const total = Object.keys(correctAnswers).length;
    const result = document.getElementById("result");
    result.textContent = `Ваш результат: ${score} / ${total}`;
    result.style.backgroundColor = score >= 3 ? "#32CD32" : "#DC143C";
    result.style.color = "white";
}