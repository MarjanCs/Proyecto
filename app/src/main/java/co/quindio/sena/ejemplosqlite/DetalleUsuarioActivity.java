package co.quindio.sena.ejemplosqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import co.quindio.sena.ejemplosqlite.entidades.Usuario;

public class DetalleUsuarioActivity extends AppCompatActivity {

    TextView campoId, campoNombre, campoAsistencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_usuario);

        campoId = (TextView) findViewById(R.id.campoId);
        campoNombre = (TextView) findViewById(R.id.campoNombre);
        campoAsistencia = (TextView) findViewById(R.id.campoAsistencia);

        Bundle objetoEnviado=getIntent().getExtras();
        Usuario user=null;

        if(objetoEnviado!=null){
            user= (Usuario) objetoEnviado.getSerializable("usuario");
            campoId.setText(user.getId().toString());
            campoNombre.setText(user.getNombre().toString());
            campoAsistencia.setText(user.getAsistencia().toString());

        }

    }
}
