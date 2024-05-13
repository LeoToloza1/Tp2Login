package com.leotoloza.fichero2024.registro;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.leotoloza.fichero2024.modelo.Usuario;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;


public class RegistroViewModel extends AndroidViewModel {
    private MutableLiveData<Usuario> usuarioMutableLiveData;
    private Context context;

    public RegistroViewModel(@NonNull Application application) {
        super(application);
        context = getApplication();
    }

    public MutableLiveData<Usuario> getUsuarioMutableLiveData() {
        if (usuarioMutableLiveData == null) {
            usuarioMutableLiveData = new MutableLiveData<>();
        }
        return usuarioMutableLiveData;
    }

    public void guardarObjeto(String nombre, String apellido, String email, String pass, long telefono) {
        Usuario usuario = new Usuario(nombre, apellido, telefono, email, pass);
        Log.d("salida", context.getFilesDir().toString());
        File archivo = new File(context.getFilesDir(), "usuario.dat");
        try {
            FileOutputStream fos = new FileOutputStream(archivo);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream ous = new ObjectOutputStream(bos);
            ous.writeObject(usuario);
            bos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error al guardar: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void recuperarUsuario() {
        File archivo = new File(context.getFilesDir(), "usuario.dat");
        if (!archivo.exists()) {
            Toast.makeText(context, "No se encontró el archivo de usuario", Toast.LENGTH_LONG).show();
            return;
        }
        Usuario usuario = null;
        try {
            FileInputStream fis = new FileInputStream(archivo);
            BufferedInputStream bis = new BufferedInputStream(fis);
            ObjectInputStream ois = new ObjectInputStream(bis);
            usuario = (Usuario) ois.readObject();
            Log.d("salida", usuario.toString());
            usuarioMutableLiveData.setValue(usuario);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error al recuperar el usuario: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}