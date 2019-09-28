package com.example.coderacer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CoderacerBD  extends SQLiteOpenHelper {

    private static String TAG = "bancox";
    private static final String NOME_BD = "coderacer.sqlite";
    private static final int VERSAO = 10;
    private static CoderacerBD coderacerBD = null; //Singleton

    public CoderacerBD(Context context) {
        // context, nome do banco, factory, versão
        super(context, NOME_BD, null, VERSAO);
    }

    public static CoderacerBD getInstance(Context context){
        if(coderacerBD == null){
            coderacerBD = new CoderacerBD(context);
            return coderacerBD;
        }else{
            return coderacerBD;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table if not exists Ponto" +
                "( _id integer primary key autoincrement, " +
                " descricao text, " +
                " longitude text, " +
                " latitude text," +
                "tipo text);";
        Log.d(TAG, "Criando a tabela DAO. Aguarde ...");
        db.execSQL(sql);
        Log.d(TAG, "Tabela cachorro criada");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Caso mude a versão do banco de dados, podemos executar um SQL aqui
        // exemplo:
        Log.d("teste", "Upgrade da versão " + oldVersion + " para "
                + newVersion + ", destruindo tudo.");
        db.execSQL("DROP TABLE IF EXISTS Ponto");
        onCreate(db); // chama onCreate e recria o banco de dados
        Log.i("teste", "Executou o script de upgrade da tabela Dao.");

    }

    public Long save(Ponto ponto){
        SQLiteDatabase db = getWritableDatabase(); //abre a conexão com o banco
            //tupla com: chave, valor
            ContentValues values = new ContentValues();
            values.put("longitude", ponto.getLongitude());
            values.put("latitude", ponto.getLatitude());
            values.put("descricao", ponto.getDescricao());
            values.put("tipo", ponto.getTipo());
            Log.i("teste", "Executou o script de upgrade da tabela Dao.");
            if(ponto.get_id() == null){
                //insere no banco de dados
                return ((long) db.insert("Ponto", null, values));


            }else{//altera no banco de dados
                values.put("_id", ponto.get_id());
                return (((long) db.update("Ponto", values, "_id=" + ponto.get_id(), null)));
            }
    }

    public List<Ponto> getAll(){
        SQLiteDatabase db = getReadableDatabase();

        try{
            return toList(db.rawQuery("SELECT * FROM Ponto",null));
        }finally {
            db.close();
        }

    }

    public List<Ponto> toList(Cursor c){

        List<Ponto> pontos = new ArrayList<>();
        if(c.moveToFirst()){
            do{
                Ponto ponto = new Ponto();

                ponto.set_id(c.getLong(c.getColumnIndex("_id")));
                ponto.setLatitude(c.getString(c.getColumnIndex("latitude")));
                ponto.setLongitude(c.getString(c.getColumnIndex("longitude")));
                ponto.setDescricao(c.getString(c.getColumnIndex("descricao")));
                ponto.setTipo(c.getString(c.getColumnIndex("tipo")));
                pontos.add(ponto);
            }while (c.moveToNext());
        }
        return pontos;
    }

}
