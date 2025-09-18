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
    let botones = `<a href="#" onclick="eliminarUsuario('+usuario.id+')" style="margin-right:10px" class="btn btn-danger btn-circle"><i class="fas fa-trash"></i></a><a href="javascript:void(0)" onclick="actualizarUsuario(${usuario.id},'${usuario.email}')" class="btn btn-warning btn-circle"><i class="fas fa-pen"></i></a>`

let telefono = usuario.telefono == null ? "-" : usuario.telefono;
    let usuarioHtml = ' <tr><td>'+usuario.id+'</td><td>'+usuario.nombre+' '+usuario.apellidos+'</td><td>'+usuario.email+'</td><td>'+telefono+'</td><td>'+botones+'</td></tr>'
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

function actualizarUsuario(id,email) {

 console.log("Holaaaaaa");
    Swal.fire({
      title: 'Actualizar Usuario',
      html: `
        <input id="id" class="swal2-input" placeholder="ID: ${id}" readonly>
        <input id="email" class="swal2-input" placeholder="Email: ${email}" readonly>
        <input id="nombre" class="swal2-input" placeholder="Nombre">
        <input id="apellidos" class="swal2-input" placeholder="Apellidos">
      `,
      confirmButtonText: 'Actualizar',
      showCancelButton: true,
      preConfirm: () => {
        const data = {
          nombre : document.getElementById('nombre').value,
          apellidos : document.getElementById('apellidos').value,
        };
        return fetch(`/api/usuario/${id}`, {
          method: 'PUT',
          headers: getHeaders(),
          body: JSON.stringify(data)
        }).then(r => {
          if (!r.ok) throw new Error();
          Swal.fire('Éxito', 'Usuario actualizado', 'success');
          cargarUsuarios();
        }).catch(() => Swal.fire('Error', 'No se pudo actualizar el usuario', 'error'));
      }
    });
  }


