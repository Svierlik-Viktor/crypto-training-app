function checkAnswers() {
    let correctAnswers = {
        q1: "c",
        q2: "c",
        q3: "c",
        q4: "c",
        q5: "c"
    };

    let score = 0;
    let total = Object.keys(correctAnswers).length;

    for (let q in correctAnswers) {
        let selected = document.querySelector(`input[name="${q}"]:checked`);
        if (selected && selected.value === correctAnswers[q]) {
            score++;
        }
    }

    let result = document.getElementById("result");
    result.innerText = `Ваш результат: ${score} / ${total}`;
    result.style.backgroundColor = score >= 3 ? "#32CD32" : "#DC143C";
    result.style.color = "white";
}