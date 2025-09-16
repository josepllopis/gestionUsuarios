// Call the dataTables jQuery plugin
$(document).ready(function() {
// en ready
});


function getHeaders(){
    return {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': localStorage.token
    };
}

async function registrarUsuarios(){

let datos = {}

datos.nombre = document.getElementById('txtNombre').value;
datos.apellidos = document.getElementById('txtApellido').value;
datos.email = document.getElementById('txtEmail').value;
datos.password = document.getElementById('txtPassword').value;
datos.telefono = document.getElementById('txtTelefono').value;
let repeatPassword = document.getElementById('txtRepeatPassword').value;

if(datos.password != repeatPassword){
    alert('Las contraseñas no son iguales')
    return;
}

  const request = await fetch('/api/insertar', {
    method: 'POST',
    headers: getHeaders(),
    body: JSON.stringify(datos)
  });
  window.location.href = "http://localhost:8080/usuarios.html";

}


