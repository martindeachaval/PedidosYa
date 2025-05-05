package Sistema;

import Paquete_Usuario.Usuario;
import Sistema.Excepciones.PasswordException;

import java.lang.reflect.Type;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Sistema {
    private static final String USUARIOS_FILE_PATH = "usuarios.json";
    private Scanner scanner = new Scanner(System.in);
    private Gson gson = new Gson();

    private Usuario usuarioActivo;

    public void registrarUsuario() {
        try {
            System.out.print("Ingrese el Nombre: ");
            String nombre = scanner.nextLine();
            System.out.print("Ingrese el Email: ");
            String email = scanner.nextLine();
            System.out.print("Ingrese la Contraseña: ");
            String contrasena = scanner.nextLine();
            System.out.print("Confirme la Contraseña: ");
            String confirmarContrasena = scanner.nextLine();

            validarContrasena(contrasena);
            if (!contrasena.equals(confirmarContrasena)) {
                System.out.println("Las contraseñas no coinciden.");
                return;
            }

            if (buscarUsuarioPorEmail(email) != null) {
                System.out.println("El email ya está registrado.");
                return;
            }

            String id = generarIdUnico();
            Usuario nuevoUsuario = new Usuario(id, nombre,email, contrasena);
            usuarioActivo = nuevoUsuario;
            guardarUsuarioEnArchivo(nuevoUsuario);
            System.out.println("Usuario registrado exitosamente.");
        } catch (PasswordException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.err.println("Error al acceder al archivo de usuarios: " + e.getMessage());
        }
    }

    public void iniciarSesion() throws Exception {
        try {
            System.out.print("Ingrese su Email: ");
            String email = scanner.nextLine();
            System.out.print("Ingrese su Contraseña: ");
            String contrasena = scanner.nextLine();

            Usuario usuario = buscarUsuarioPorEmail(email);
            if (usuario == null || !usuario.getContrasena().equals(contrasena)) {
                throw new Exception("Usuario o contraseña incorrecta intente nuevamente");
            } else {
                usuarioActivo = usuario;  // Almacenar el usuario activo
                System.out.println("Inicio de sesión exitoso. Bienvenido, " + usuario.getNombre());
                return;
            }
        } catch (IOException e) {
            System.err.println("Error al acceder al archivo de usuarios: " + e.getMessage());
        }
    }

    public Usuario getUsuarioActivo() {
        return usuarioActivo;
    }

    private void validarContrasena(String contrasena) throws PasswordException {
        if (contrasena.length() < 8) {
            throw new PasswordException("La contraseña debe tener al menos 8 caracteres.");
        }

        boolean tieneMayuscula = false;
        boolean tieneMinuscula = false;
        boolean tieneNumero = false;
        boolean tieneEspecial = false;

        for (char c : contrasena.toCharArray()) {
            if (Character.isUpperCase(c)) {
                tieneMayuscula = true;
            } else if (Character.isLowerCase(c)) {
                tieneMinuscula = true;
            } else if (Character.isDigit(c)) {
                tieneNumero = true;
            } else if (!Character.isLetterOrDigit(c)) {
                tieneEspecial = true;
            }
        }

        if (!tieneMayuscula) {
            throw new PasswordException("La contraseña debe contener al menos una letra mayúscula.");
        }
        if (!tieneMinuscula) {
            throw new PasswordException("La contraseña debe contener al menos una letra minúscula.");
        }
        if (!tieneNumero) {
            throw new PasswordException("La contraseña debe contener al menos un número.");
        }
        if (!tieneEspecial) {
            throw new PasswordException("La contraseña debe contener al menos un carácter especial.");
        }
    }

    private Usuario buscarUsuarioPorEmail(String email) throws IOException {
        List<Usuario> usuarios = cargarUsuariosDesdeArchivo();
        for(Usuario usuario : usuarios){
            if(usuario.getEmail().equalsIgnoreCase(email.trim())){ //Trim elimina espacios
                return usuario;
            }
        }
        return null;
    }

    private void guardarUsuarioEnArchivo(Usuario usuario) throws IOException {
        List<Usuario> usuarios = cargarUsuariosDesdeArchivo();
        usuarios.add(usuario);
        guardarUsuariosEnArchivo(usuarios);
    }

    private List<Usuario> cargarUsuariosDesdeArchivo() throws IOException{
        File file = new File(USUARIOS_FILE_PATH);
        if(!file.exists()){
            return new ArrayList<>();
        }
        try(Reader reader = new FileReader(file)){
            Type usuarioListType = new TypeToken<ArrayList<Usuario>>() {}.getType();
            return gson.fromJson(reader, usuarioListType);
        }
    }

    private void guardarUsuariosEnArchivo(List<Usuario>usuarios)throws IOException{
        try(Writer writer = new FileWriter(USUARIOS_FILE_PATH)){
            gson.toJson(usuarios, writer);
        }
    }
    private String generarIdUnico() {
        return " "+System.currentTimeMillis(); // Genera ID mostrando la hora en milisegundcs
    }

    public void mostrarUsuarios(){
        try{
            List<Usuario>usuarios = cargarUsuariosDesdeArchivo();
            if(usuarios.isEmpty()){
                System.out.println("No hay usuarios Registrados");
            }else{
                for(Usuario usuario : usuarios){
                    System.out.println("ID: "+usuario.getId()+ ", nombre: "+ usuario.getNombre() + ", Email: " + usuario.getEmail());
                }
            }
        }catch (IOException e){
            System.out.println("Error al acceder al archivo: "+e.getMessage());
        }
    }
}
