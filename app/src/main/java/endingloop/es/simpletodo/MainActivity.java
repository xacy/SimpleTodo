package endingloop.es.simpletodo;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;

import java.util.ArrayList;
import java.util.List;

import endingloop.es.simpletodo.model.Listas;
import endingloop.es.simpletodo.model.TodoDataSource;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static endingloop.es.simpletodo.R.id.addItem;
import static endingloop.es.simpletodo.R.id.etNewItem;
import static endingloop.es.simpletodo.R.id.visible;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, TodoAdapter.TodoAdapterCallback {
    private FABToolbarLayout morph;
    private ListView todoItems;
    private ArrayList<Listas> items;
    private ArrayAdapter<Listas> itemsAdapter;
    private EditText newItem;
    private InputMethodManager imm;
    private TodoDataSource datasource;

    private Toolbar toolbar;
    private RecyclerView mRecyclerView;
    private TodoAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private int selectedItems=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        RelativeLayout relative= (RelativeLayout) findViewById(R.id.content_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.todoItems);
        //imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        relative.setOnClickListener(this);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabtoolbar_fab);
        morph = (FABToolbarLayout) findViewById(R.id.fabtoolbar);
        View addItem = findViewById(R.id.addItem);

        fab.setOnClickListener(this);
        addItem.setOnClickListener(this);
        Log.i("Prueba","prueba");

        //todoItems = (ListView) findViewById(R.id.todoItems);

        datasource = new TodoDataSource(this);
        datasource.open();

        List<Listas> allLists = datasource.getAllLists();
        Log.i("Listas","Numero de items: "+allLists.size());

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new TodoAdapter((ArrayList)allLists,this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setOnClickListener(this);


        /*items = (ArrayList<Listas>) allLists;
        itemsAdapter = new ArrayAdapter<Listas>(this,
                R.layout.single_todo,R.id.eachItem, items);

        todoItems.setAdapter(itemsAdapter);
        /*items.add("First Item");
        items.add("Second Item");*/
        /*
        todoItems.setOnItemClickListener(this);
        setupListViewListener();*/

        newItem = (EditText) findViewById(R.id.etNewItem);
        newItem.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    newItem.getText().clear();
                    imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                }
            }
        });


    }

    @Override
    public void onItemClickCallback() {
        desactivarMorphBar();
    }

    @Override
    public void onLongItemClickCallback() {
        activarToolbarEdicion();
    }

    @Override
    public void onItemSelect() {
        selectedItems++;
        TextView number=(TextView)toolbar.findViewById(R.id.action_main_number);
        number.setText(selectedItems+"");

    }

    @Override
    public void onItemUnSelect() {
        selectedItems--;
        TextView number=(TextView)toolbar.findViewById(R.id.action_main_number);
        number.setText(selectedItems+"");
        if(selectedItems==0){
            desactivarToolbarEdicion();
            mAdapter.setEditMenuVisible(false);
        }
    }

    @Override
    public void onClick(View v) {

        Log.i("Onclick","LLamado onclick"+v.getId());
        if (v.getId() == R.id.fabtoolbar_fab) {
            morph.show();
        }
        else{
            if(v.getId( )== R.id.addItem){
                if(newItem.getText().length()!=0){
                    //itemsAdapter.add(newItem.getText().toString());
                    //itemsAdapter.add(datasource.createList(newItem.getText().toString()));
                    mAdapter.add(datasource.createList(newItem.getText().toString()));
                }

            }
        }

        morph.hide();
    }
    public void desactivarMorphBar(){
        morph.hide();
    }
    public void activarToolbarEdicion(){
        toolbar.getMenu().clear();
        //selectedItems++;
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.findViewById(R.id.action_main_number).setVisibility(View.VISIBLE);
        ((TextView)toolbar.findViewById(R.id.action_main_number)).setText(selectedItems+"");
        toolbar.findViewById(R.id.action_main_delete).setVisibility(View.VISIBLE);
        toolbar.setTitle("Tareas seleccionadas: ");
    }
    public void desactivarToolbarEdicion(){
        toolbar.getMenu().clear();
        selectedItems=0;
        toolbar.setNavigationIcon(null);
        toolbar.findViewById(R.id.action_main_number).setVisibility(View.GONE);
        ((TextView)toolbar.findViewById(R.id.action_main_number)).setText(selectedItems+"");
        toolbar.findViewById(R.id.action_main_delete).setVisibility(View.GONE);
        toolbar.setTitle(R.string.app_name);
    }
    /*
    @Override
    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
        //Snackbar.make(view, "Element: "+position, Snackbar.LENGTH_SHORT).setAction("Action",null).show();
        View check = v.findViewById(R.id.check);
        TextView eachItemText = (TextView) v.findViewById(R.id.eachItem);
        if(check.getVisibility()==View.INVISIBLE){
            check.setVisibility(View.VISIBLE);
            eachItemText.setPaintFlags(eachItemText.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        else{
            eachItemText.setPaintFlags(eachItemText.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
            check.setVisibility(View.INVISIBLE);
        }

    }*/
    private void setupListViewListener() {

        todoItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id) {
                        Log.i("Delete","PRueba: "+pos+" - "+id+" - "+items.get(pos));
                        // Remove the item within array at position
                        datasource.deleteList(items.get(pos));

                        items.remove(pos);
                        // Refresh the adapter
                        itemsAdapter.notifyDataSetChanged();
                        // Return true consumes the long click event (marks it handled)
                        return true;
                    }

                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id==android.R.id.home){
            mAdapter.setEditMenuVisible(false);
        }

        return super.onOptionsItemSelected(item);
    }

}
