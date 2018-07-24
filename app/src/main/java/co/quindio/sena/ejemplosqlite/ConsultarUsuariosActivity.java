package co.quindio.sena.ejemplosqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.Result;

import java.util.Calendar;

import co.quindio.sena.ejemplosqlite.entidades.Usuario;
import co.quindio.sena.ejemplosqlite.utilidades.Utilidades;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ConsultarUsuariosActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler  {
    //varabes para fecha
    private  int d,m , a;
    EditText campoId,campoNombre,campoAsistencia;
    private ZXingScannerView mysc;
    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_usuarios);

        conn=new ConexionSQLiteHelper(getApplicationContext(),"bd_usuarios",null,1);

        campoId=(EditText) findViewById(R.id.documentoId);
        campoNombre= (EditText) findViewById(R.id.campoNombreConsulta);
        campoAsistencia= (EditText) findViewById(R.id.campoAsistenciaConsulta);

    }

    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btnActualizar:
                actualizarUsuario();
                break;
            case R.id.btnScaner:
                btnqr();
                break;

        }

    }

    private void actualizarUsuario() {

        try {
            SQLiteDatabase db=conn.getWritableDatabase();
            String[] parametros={campoNombre.getText().toString()};
            ContentValues values=new ContentValues();
            Usuario per1=new Usuario();

            int w = Integer.parseInt(campoId.getText().toString());


            values.put(Utilidades.CAMPO_ID,w+1);
            values.put(Utilidades.CAMPO_ASISTENCIA,campoAsistencia.getText().toString());



            db.update(Utilidades.TABLA_USUARIO,values,Utilidades.CAMPO_NOMBRE+"=?",parametros);
            Toast.makeText(getApplicationContext(),"se actualizó el usuario",Toast.LENGTH_LONG).show();
            db.close();
            limpiar();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"El alumno no existe",Toast.LENGTH_LONG).show();
            limpiar();
        }


    }

    private void consultar() {
        campoId= (EditText) findViewById(R.id.documentoId);
        campoNombre= (EditText) findViewById(R.id.campoNombreConsulta);
        campoAsistencia= (EditText) findViewById(R.id.campoAsistenciaConsulta);
        SQLiteDatabase db=conn.getReadableDatabase();
        String[] parametros={campoNombre.getText().toString()};
        String[] campos={Utilidades.CAMPO_ID,Utilidades.CAMPO_ASISTENCIA};

        try {
            Cursor cursor =db.query(Utilidades.TABLA_USUARIO,campos,Utilidades.CAMPO_NOMBRE+"=?",parametros,null,null,null);
            cursor.moveToFirst();
            campoId.setText(cursor.getString(0));
            cursor.close();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"El documento no existe",Toast.LENGTH_LONG).show();
            limpiar();
        }

    }

    private void fechaActual(){
        final Calendar c = Calendar.getInstance();
        d = c.get(Calendar.DAY_OF_MONTH);
        m= c.get(Calendar.MONTH);
        a = c.get(Calendar.YEAR);
        campoAsistencia= (EditText) findViewById(R.id.campoAsistenciaConsulta);
        campoAsistencia.setText(d+"/"+(m+1)+"/"+a);
    }

    private void limpiar() {
        campoNombre.setText("");
        campoAsistencia.setText("");
        campoId.setText("");
    }

    public void btnqr() {
        mysc = new ZXingScannerView(this);
        mysc.setResultHandler(this);
        setContentView(mysc);
        mysc.startCamera();
    }

    public void cont(){

        int w = Integer.parseInt(campoId.getText().toString());

    }

    @Override
    public void handleResult(Result result) {
        String dato = result.getText();
        setContentView(R.layout.activity_consultar_usuarios);
        mysc.stopCamera();
        campoNombre= (EditText) findViewById(R.id.campoNombreConsulta);
        campoNombre.setText(dato);
        campoId.setText("1"+1);
        fechaActual();
        consultar();

    }

}
