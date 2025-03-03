document.addEventListener("DOMContentLoaded", function () {
    // ✅ Mostrar alerta solo la primera vez que el usuario inicie sesión
    if (window.location.href.includes("home.jsp") && !sessionStorage.getItem("alertShown")) {
        alert("✅ Inicio de sesión exitoso. ¡Bienvenido!");
        sessionStorage.setItem("alertShown", "true");  // Evitar mostrar el alert nuevamente
    }

    // ✅ Confirmar eliminación de productos
    let deleteButtons = document.querySelectorAll(".btn-eliminar");
    deleteButtons.forEach(button => {
        button.addEventListener("click", function (event) {
            let confirmacion = confirm("⚠️ ¿Estás seguro de que quieres eliminar este producto?");
            if (!confirmacion) {
                event.preventDefault(); // Si cancela, se detiene el evento de eliminar
            }
        });
    });
});