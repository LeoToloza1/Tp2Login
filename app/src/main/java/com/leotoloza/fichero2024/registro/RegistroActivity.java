package com.leotoloza.fichero2024.registro;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.leotoloza.fichero2024.databinding.ActivityRegistroBinding;
import com.leotoloza.fichero2024.login.MainActivity;
import com.leotoloza.fichero2024.modelo.Usuario;

public class RegistroActivity extends AppCompatActivity {
private ActivityRegistroBinding binding;
private RegistroViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      binding=ActivityRegistroBinding.inflate(getLayoutInflater());
      setContentView(binding.getRoot());
      viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(RegistroViewModel.class);
        viewModel.getUsuarioMutableLiveData().observe(this, new Observer<Usuario>() {
            @Override
            public void onChanged(Usuario usuario) {
                Log.d("salida", "USUARIO:"+usuario.getNombre());
                binding.EditNombre.setText(usuario.getNombre());
                binding.EditApellido.setText(usuario.getApellido());
                binding.EditTelefono.setText(usuario.getTelefono()+"");
                binding.EditEmail.setText(usuario.getEmail());
                binding.EditPass.setText(usuario.getPass());
            }
        });
      Button btnRegistro = binding.btnRegistro;
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = binding.EditNombre.getText().toString();
                String apellido = binding.EditApellido.getText().toString();
                String email = binding.EditEmail.getText().toString();
                String pass = binding.EditPass.getText().toString();
                long telefono = Long.parseLong(binding.EditTelefono.getText().toString());
                viewModel.guardarObjeto(nombre, apellido, email, pass, telefono);
                Toast.makeText(RegistroActivity.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                volverAlLogin();
            }
        });
        viewModel.recuperarUsuario();

    }
    private void volverAlLogin() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}