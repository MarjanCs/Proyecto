package co.quindio.sena.ejemplosqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import co.quindio.sena.ejemplosqlite.entidades.Usuario;
import co.quindio.sena.ejemplosqlite.utilidades.Utilidades;

public class ConsultaComboActivity extends AppCompatActivity {

    Spinner comboPersonas;
    TextView txtNombre,txtDocumento,txtAsistencia;
    ArrayList<String> listaPersonas;
    ArrayList<Usuario> personasList;

    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_combo);

        conn=new ConexionSQLiteHelper(getApplicationContext(),"bd_usuarios",null,1);

        comboPersonas= (Spinner) findViewById(R.id.comboPersonas);

        txtDocumento= (TextView) findViewById(R.id.txtDocumento);
        txtNombre= (TextView) findViewById(R.id.txtNombre);
        txtAsistencia= (TextView) findViewById(R.id.txtAsistencia);

        consultarListaPersonas();

        ArrayAdapter<CharSequence> adaptador=new ArrayAdapter
                (this,android.R.layout.simple_spinner_item,listaPersonas);

        comboPersonas.setAdapter(adaptador);

        comboPersonas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long idl) {

                if (position!=0){
                    txtDocumento.setText(personasList.get(position-1).getId().toString());
                    txtNombre.setText(personasList.get(position-1).getNombre());
                    txtAsistencia.setText(personasList.get(position-1).getAsistencia());
                }else{
                    txtDocumento.setText("");
                    txtNombre.setText("");
                    txtAsistencia.setText("");
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void consultarListaPersonas() {
        SQLiteDatabase db=conn.getReadableDatabase();

        Usuario persona=null;
        personasList =new ArrayList<Usuario>();

        Cursor cursor=db.rawQuery("SELECT * FROM "+Utilidades.TABLA_USUARIO,null);

        while (cursor.moveToNext()){
            persona=new Usuario();
            persona.setId(cursor.getInt(0));
            persona.setNombre(cursor.getString(1));
            persona.setAsistencia(cursor.getString(2));

            Log.i("id",persona.getId().toString());
            Log.i("Nombre",persona.getNombre());
            Log.i("Asistencia",persona.getAsistencia());

            personasList.add(persona);

        }
        obtenerLista();
    }

    private void obtenerLista() {
        listaPersonas=new ArrayList<String>();
        listaPersonas.add("Seleccione");

        for(int i=0;i<personasList.size();i++){
            listaPersonas.add(personasList.get(i).getId()+" - "+personasList.get(i).getNombre());
        }

    }

}
