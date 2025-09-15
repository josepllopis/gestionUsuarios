// Call the dataTables jQuery plugin
$(document).ready(function() {

cargarUsuarios();
  $('usuarios').DataTable();

  emailUsuario();
});

function getHeaders(){
    return {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
        'Authorization': localStorage.token
    };
}


async function cargarUsuarios(){

  const request = await fetch('/api/listausuarios', {
    method: 'GET',
    headers : getHeaders()

  });
  const usuarios = await request.json();
    let listaUsuarios = '';
for(let usuario of usuarios){
    let botonEliminar = '<a href="#" onclick="eliminarUsuario('+usuario.id+')" class="btn btn-danger btn-circle"><i class="fas fa-trash"></i></a>'

let telefono = usuario.telefono == null ? "-" : usuario.telefono;
    let usuarioHtml = ' <tr><td>'+usuario.id+'</td><td>'+usuario.nombre+'</td><td>'+usuario.email+'</td><td>'+telefono+'</td><td>'+botonEliminar+'</td></tr>'
    listaUsuarios+=usuarioHtml;
}

document.querySelector('#usuarios tbody').outerHTML = listaUsuarios;
  console.log("usuarios");



}

async function eliminarUsuario(id){

if(!confirm('¿Desea eliminar el usuario con id: '+id+' ?')){
    return;
}
const request = await fetch('/api/borrar/'+id, {
    method: 'DELETE',
    headers: getHeaders()
  });

  location.reload();

}

function emailUsuario(){
    document.getElementById('txt-email-usuario').outerHTML = localStorage.email;
}


