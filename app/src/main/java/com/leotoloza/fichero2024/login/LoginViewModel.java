package com.leotoloza.fichero2024.login;

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

public class LoginViewModel extends AndroidViewModel {
    private MutableLiveData<Usuario> usuarioMutableLiveData;
    private Context context;
    public LoginViewModel(@NonNull Application application) {
        super(application);
        context = getApplication();
    }

    public MutableLiveData<Usuario> getUsuarioMutableLiveData() {
        if(usuarioMutableLiveData ==null){
            usuarioMutableLiveData = new MutableLiveData<>();
        }
        return usuarioMutableLiveData;
    }
    public void login(String email, String pass) {
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
            ois.close();
            fis.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error al recuperar el usuario: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }
        if (email.equalsIgnoreCase(usuario.getEmail()) && pass.equalsIgnoreCase(usuario.getPass())) {
            Log.d("salida", usuario.toString());
            usuarioMutableLiveData.setValue(usuario);
        } else {
            // Si las credenciales son incorrectas, mostrar mensaje de error
            Toast.makeText(getApplication(), "Credenciales inválidas", Toast.LENGTH_LONG).show();
        }
    }
}

