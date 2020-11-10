package com.lucascabral.applistadetarefas.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.lucascabral.applistadetarefas.R;
import com.lucascabral.applistadetarefas.helper.TarefaDAO;
import com.lucascabral.applistadetarefas.model.Tarefa;

public class AdicionarTarefaActivity extends AppCompatActivity {

    private TextInputEditText editTarefa;
    private Tarefa tarefaAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);

        editTarefa = findViewById(R.id.editTextTarefa);

        //Recuperar tarefa caso seja edição
        tarefaAtual = (Tarefa) getIntent().getSerializableExtra("tarefaSelecionada");

        //Configurar tarefa na caixa de texto
        if ( tarefaAtual != null ){
            editTarefa.setText(tarefaAtual.getNomeTarefa());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_adicionar_tarefa, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_salvar) {

            TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());

            if (tarefaAtual != null) {  // Edição
                String nomeTarefa = editTarefa.getText().toString();
                if (!nomeTarefa.isEmpty()){
                    Tarefa tarefa = new Tarefa();
                    tarefa.setNomeTarefa(nomeTarefa);
                    tarefa.setId(tarefaAtual.getId());

                    //Atualizar no banco de dados
                    if ( tarefaDAO.atualizar(tarefa) ){
                        finish();
                        Toast.makeText(getApplicationContext(), "Sucesso ao atualizar tarefa!",
                                Toast.LENGTH_SHORT).show();
                    }else{
                        finish();
                        Toast.makeText(getApplicationContext(), "Erro ao atualizar tarefa!",
                                Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Digite uma tarefa!",
                            Toast.LENGTH_LONG).show();
                }

            } else {                    // Salvar

                String nomeTarefa = editTarefa.getText().toString();
                if (!nomeTarefa.isEmpty()) {
                    Tarefa tarefa = new Tarefa();
                    tarefa.setNomeTarefa(nomeTarefa);
                    if (tarefaDAO.salvar(tarefa)){
                        finish();
                        Toast.makeText(getApplicationContext(), "Sucesso ao salvar tarefa!",
                                Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "Erro ao salvar tarefa!",
                                Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Digite uma tarefa!",
                            Toast.LENGTH_LONG).show();
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }
}