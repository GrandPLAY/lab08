package g313.martin.lab08;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity {

    EditText txtid;
    EditText txtctl;

    int nid;
    String ntxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        txtid = findViewById(R.id.txt_id);
        txtctl = findViewById(R.id.txt_content);

        Intent i = getIntent();
        nid = i.getIntExtra("note-id",0);
        ntxt = i.getStringExtra("note-txt");

        txtid.setText(String.valueOf(nid));
        txtctl.setText(ntxt);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){

        int itemId = item.getItemId();

        int newID;

        if (itemId == R.id.itm_save) {
            try{
                newID = Integer.valueOf(txtid.getText().toString());
            }
            catch (Exception e){
                Toast.makeText(this, "ERROR: Wrong ID", Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
            }

            String txt = txtctl.getText().toString();
            g.notes.alterNote(nid, newID, txt);
            Toast.makeText(this, "note saved", Toast.LENGTH_SHORT).show();
            finish();
        }
        else if (itemId == R.id.itm_delete) {
            AlertDialog.Builder bld = new AlertDialog.Builder(this);
            AlertDialog dlg = bld.create();
            dlg.setTitle("Delete");
            dlg.setMessage("Are you sure you want to delete note?");
            dlg.setButton(Dialog.BUTTON_POSITIVE,"Yes", (dialog, id) -> {
                        g.notes.delete_note(nid);
                        Toast.makeText(this, "note deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    });
            dlg.setButton(Dialog.BUTTON_NEGATIVE,"No", (dialog, id) -> dialog.cancel());
            dlg.show();
        }
        else if (itemId == R.id.itm_exit) {
            AlertDialog.Builder bld = new AlertDialog.Builder(this);
            AlertDialog dlg = bld.create();
            dlg.setTitle("Exit");
            dlg.setMessage("All unsaved changes will be lost. Exit?");
            dlg.setButton(Dialog.BUTTON_POSITIVE,"Yes", (dialog, id) -> finish());
            dlg.setButton(Dialog.BUTTON_NEGATIVE,"No", (dialog, id) -> dialog.cancel());
            dlg.show();
        }

        return super.onOptionsItemSelected(item);
    }
}