const input4 = document.getElementById("apellido");
const errorApe = document.getElementById("errorApellido");

input4.addEventListener("input", () => {
    const value = input4.value;

    
    input4.value = value.replace(/[^A-Za-zÁÉÍÓÚáéíóúÑñ\s]/g, "");

    
    if (input3.value === "") {
        errorApe.textContent = "Solo se permiten letras.";
    } else {
        errorApe.textContent = "";
    }
});