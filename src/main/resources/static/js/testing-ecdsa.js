function updatemenu() {
    if (document.getElementById('responsive-menu').checked == true) {
        document.getElementById('menu').style.borderBottomRightRadius = '0';
        document.getElementById('menu').style.borderBottomLeftRadius = '0';
    }else{
        document.getElementById('menu').style.borderRadius = '50px';
    }
}

function checkAnswers() {
    const correctAnswers = {
        q1: "c",
        q2: "b",
        q3: "b",
        q4: "c",
        q5: "b"
    };

    let score = 0;
    const total = Object.keys(correctAnswers).length;

    for (let q in correctAnswers) {
        const selected = document.querySelector(`input[name="${q}"]:checked`);
        if (selected && selected.value === correctAnswers[q]) {
            score++;
        }
    }

    const resultDiv = document.getElementById("result");
    resultDiv.textContent = `Ваш результат: ${score} / ${total}`;
    resultDiv.style.backgroundColor = score >= 4 ? "#32CD32" : "#DC143C";
    resultDiv.style.color = "#FFF";
}