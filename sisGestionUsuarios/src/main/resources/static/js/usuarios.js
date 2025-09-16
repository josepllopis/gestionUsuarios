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
    let botones = '<a href="#" onclick="eliminarUsuario('+usuario.id+')" style="margin-right:10px" class="btn btn-danger btn-circle"><i class="fas fa-trash"></i></a><a href="#" class="btn btn-warning btn-circle"><i class="fas fa-pen"></i></a>'

let telefono = usuario.telefono == null ? "-" : usuario.telefono;
    let usuarioHtml = ' <tr><td>'+usuario.id+'</td><td>'+usuario.nombre+'</td><td>'+usuario.email+'</td><td>'+telefono+'</td><td>'+botones+'</td></tr>'
    listaUsuarios+=usuarioHtml;
}

listaUsuarios+='<tr><td colspan="5" style="text-align: center;"><b>AÑADIR NUEVO USUARIO<b><br><a href="./registrar.html" class="btn btn-success btn-circle"><i class="fas fa-plus"></i></a></td></tr>';

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


