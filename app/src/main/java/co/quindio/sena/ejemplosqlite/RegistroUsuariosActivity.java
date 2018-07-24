package co.quindio.sena.ejemplosqlite;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.zxing.Result;
import co.quindio.sena.ejemplosqlite.utilidades.Utilidades;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class RegistroUsuariosActivity extends AppCompatActivity {

    EditText campoId,campoNombre,campoAsistencia;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuarios);

        campoId= (EditText) findViewById(R.id.campoId);
        campoNombre= (EditText) findViewById(R.id.campoNombre);
        campoAsistencia= (EditText) findViewById(R.id.campoAsistencia);

    }

    public void onClick(View view) {
        registrarUsuarios();

    }



    private void registrarUsuarios() {

            ConexionSQLiteHelper conn=new ConexionSQLiteHelper(this,"bd_usuarios",null,1);
            SQLiteDatabase db=conn.getWritableDatabase();
            ContentValues values=new ContentValues();
            values.put(Utilidades.CAMPO_ID,campoId.getText().toString());
            values.put(Utilidades.CAMPO_NOMBRE,campoNombre.getText().toString());
            values.put(Utilidades.CAMPO_ASISTENCIA,campoAsistencia.getText().toString());
            Long idResultante=db.insert(Utilidades.TABLA_USUARIO,Utilidades.CAMPO_ID,values);
            Toast.makeText(getApplicationContext()," Registro: "+idResultante,Toast.LENGTH_SHORT).show();
            db.close();
            campoNombre.setText("");
            campoId.setText("");
    }


}
