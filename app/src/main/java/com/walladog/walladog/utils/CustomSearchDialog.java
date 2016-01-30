package com.walladog.walladog.utils;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import com.walladog.walladog.R;
import com.walladog.walladog.adapters.DistanceSpinArrayAdapter;
import com.walladog.walladog.adapters.BasicsSpinArrayAdapter;
import com.walladog.walladog.models.Category;
import com.walladog.walladog.models.Race;

import java.util.List;

/**
 * Created by hadock on 30/01/16.
 *
 */
public class CustomSearchDialog extends Dialog implements android.view.View.OnClickListener {

    private final static String TAG = CustomSearchDialog.class.getName();
    private SearchDialogListener mListener;
    public Activity c;
    public Dialog d;
    public Spinner mSpinRazas,mSpinDistancia,mSpinCategoria;
    public Button yes, no;

    SearchObject mSo = new SearchObject();


    private BasicsSpinArrayAdapter<Race> mAdapterRazas = null;
    private BasicsSpinArrayAdapter<Category> mAdapterCategorias = null;
    private DistanceSpinArrayAdapter mAdapterDistance = null;

    public CustomSearchDialog(Activity a,SearchDialogListener listener) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.filter_dialog);


        mSpinRazas = (Spinner) findViewById(R.id.spin_razas);
        mSpinDistancia = (Spinner) findViewById(R.id.spin_distancia);
        mSpinCategoria = (Spinner) findViewById(R.id.spin_categorias);

        yes = (Button) findViewById(R.id.btn_yes);
        no = (Button) findViewById(R.id.btn_no);
        yes.setOnClickListener(this);
        no.setOnClickListener(this);

        mSpinRazas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Race searchRace = (Race) parent.getItemAtPosition(position);
                mSo.setRace((int) searchRace.getId_race());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSpinDistancia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DistanceItem di = (DistanceItem) parent.getItemAtPosition(position);
                mSo.setDistance(di.getDistance());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSpinCategoria.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //TODO seguimos aqui
                mSo.setCategory((int)((Category) parent.getItemAtPosition(position)).getId_category());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        loadSpinners();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                //c.finish();
                mListener.OnDialogData(mSo);
                dismiss();
                break;
            case R.id.btn_no:
                mListener.OnDialogCanceled();
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }

    public static interface SearchDialogListener
    {
        public void OnDialogData(SearchObject so);
        public void OnDialogCanceled();
    }

    private void loadSpinners(){
        DBAsyncTasksGet<Race> task2 = new DBAsyncTasksGet<Race>(DBAsyncTasksGet.TASK_GET_LIST,
                new Race(), c.getApplicationContext(),
                new DBAsyncTasksGet.OnItemsRecoveredFromDBListener<Race>() {
                    @Override
                    public void onItemsRecovered(List<Race> items) {
                        mAdapterRazas = new BasicsSpinArrayAdapter(c,android.R.layout.simple_spinner_dropdown_item,items);
                        mSpinRazas.setAdapter(mAdapterRazas);
                    }
                });
        task2.execute();

        DBAsyncTasksGet<Category> task3 = new DBAsyncTasksGet<Category>(DBAsyncTasksGet.TASK_GET_LIST,
                new Category(), c.getApplicationContext(),
                new DBAsyncTasksGet.OnItemsRecoveredFromDBListener<Category>() {
                    @Override
                    public void onItemsRecovered(List<Category> items) {
                        mAdapterCategorias = new BasicsSpinArrayAdapter(c,android.R.layout.simple_spinner_dropdown_item,items);
                        mSpinCategoria.setAdapter(mAdapterCategorias);
                    }
                });
        task3.execute();

        mAdapterDistance = new DistanceSpinArrayAdapter(c,android.R.layout.simple_spinner_dropdown_item);
        mSpinDistancia.setAdapter(mAdapterDistance);

    }
}