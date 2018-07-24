package co.quindio.sena.ejemplosqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import co.quindio.sena.ejemplosqlite.utilidades.Utilidades;

public class Parametros extends AppCompatActivity {
    EditText valor,limFaltas,diasAsis;
    TextView campoResultado,campoNumAsist,campoPromedio;
    EditText campoNombre;
    ConexionSQLiteHelper conn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametros);

        conn=new ConexionSQLiteHelper(getApplicationContext(),"bd_usuarios",null,1);
        valor= (EditText) findViewById(R.id.valor);
        limFaltas= (EditText) findViewById(R.id.faltas);
        diasAsis= (EditText) findViewById(R.id.dias);
        campoNumAsist= (EditText) findViewById(R.id.txtnum);
        campoResultado=  (EditText) findViewById(R.id.textresul);
        campoPromedio= (EditText) findViewById(R.id.txtpromedio);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSacar:
                    calcular();
                break;
            case R.id.btncancelar:
                    limpiar();
                break;
            case R.id.btnconsul:
                consultar();
                break;


        }
    }

    public void calcular() {

            try {
                int v = Integer.parseInt(valor.getText().toString());
                int d = Integer.parseInt(diasAsis.getText().toString());
                int f = Integer.parseInt(limFaltas.getText().toString());
                int asistencia = Integer.parseInt(campoNumAsist.getText().toString());

                campoPromedio.setText("" + ((asistencia * v) / d));

                int faltas = d - asistencia;

                if (faltas > f) {
                    campoResultado.setText("Reprobado en asistencia");

                } else {
                    campoResultado.setText("Aprobado en asistencia");

                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "El documento no existe", Toast.LENGTH_LONG).show();
            }


    }

    public void limpiar() {
        valor.setText("");
        limFaltas.setText("");
        diasAsis.setText("");
        campoPromedio.setText("");
        campoResultado.setText("");
        campoNumAsist.setText("");
        campoNombre.setText("");
    }

    public void consultar(){
        campoNombre = (EditText) findViewById(R.id.idNombre);
        campoNumAsist= (EditText) findViewById(R.id.txtnum);
        campoResultado=  (EditText) findViewById(R.id.textresul);
        campoPromedio= (EditText) findViewById(R.id.txtpromedio);
        SQLiteDatabase db=conn.getReadableDatabase();
        String[] parametros={campoNombre.getText().toString()};
        String[] campos={Utilidades.CAMPO_ID,Utilidades.CAMPO_ASISTENCIA};

        try {
            Cursor cursor =db.query(Utilidades.TABLA_USUARIO,campos,Utilidades.CAMPO_NOMBRE+"=?",parametros,null,null,null);
            cursor.moveToFirst();
            campoNumAsist.setText(cursor.getString(0));
            cursor.close();
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"El documento no existe",Toast.LENGTH_LONG).show();
        }

    }
}
