function saveResult(algorithm, input, key, result, explanation) {
    const previous = JSON.parse(localStorage.getItem("cryptoPracticeResults")) || [];
    const entry = {
        algorithm,
        input,
        key,
        result,
        explanation,
        timestamp: new Date().toLocaleString()
    };
    previous.push(entry);
    localStorage.setItem("cryptoPracticeResults", JSON.stringify(previous));
}
