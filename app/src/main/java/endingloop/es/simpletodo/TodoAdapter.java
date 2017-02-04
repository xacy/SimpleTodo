package endingloop.es.simpletodo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import endingloop.es.simpletodo.model.Listas;

import static endingloop.es.simpletodo.R.color.background_material_light;
import static endingloop.es.simpletodo.R.id.check;

/**
 * Created by xacy on 04/02/2017.
 */

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder>  {
    private ArrayList<Listas> mDataset;
    private Context mContext;
    private TodoAdapterCallback mAdapterCallback;
    private boolean editMenuVisible=false;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{
        // each data item is just a string in this case
        public TextView description;
        public ImageView tick;

        public ViewHolder(View v) {
            super(v);
            description = (TextView) v.findViewById(R.id.eachItem);
            tick = (ImageView) v.findViewById(check);
            v.setOnClickListener(this);
            v.setOnLongClickListener(this);
        }
        @Override
        public void onClick(View v){
            //Snackbar.make(v, "Element click: ", Snackbar.LENGTH_SHORT).setAction("Action",null).show();
            Log.i("Context: ","Valor context: "+mContext);
            Log.i("Context: ","Visible: "+editMenuVisible);
            if(editMenuVisible){
                Log.i("Menu","Esta visible");
                if(v.isSelected()){
                    unSelectItem(v);
                }
                else{
                    selectItem(v);
                }
            }
            else{
                try {
                    mAdapterCallback.onItemClickCallback();
                } catch (ClassCastException exception) {
                    // do something
                }
                //View check = v.findViewById(R.id.check);
                //TextView eachItemText = (TextView) v.findViewById(R.id.eachItem);
                if(tick.getVisibility()==View.INVISIBLE){
                    tick.setVisibility(View.VISIBLE);
                    description.setPaintFlags(description.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }
                else{
                    description.setPaintFlags(description.getPaintFlags() & (~ Paint.STRIKE_THRU_TEXT_FLAG));
                    tick.setVisibility(View.INVISIBLE);
                }
            }
        }

        @Override
        public boolean onLongClick(View v) {
            if(!editMenuVisible){
                mAdapterCallback.onLongItemClickCallback();
                editMenuVisible=true;
                selectItem(v);

            }
            else{
                Snackbar.make(v, "Long click again", Snackbar.LENGTH_SHORT).setAction("Action",null).show();
            }
            return true;
        }
        public void selectItem(View v){
            mAdapterCallback.onItemSelect();
            v.setBackgroundResource(R.color.colorPrimaryLowAlpha);
            v.setSelected(true);
        }
        public void unSelectItem(View v){
            mAdapterCallback.onItemUnSelect();
            v.setBackgroundResource(R.color.backgroundWhite);
            v.setSelected(false);
        }
    }

    public interface TodoAdapterCallback {
        void onItemClickCallback();
        void onLongItemClickCallback();
        void onItemSelect();
        void onItemUnSelect();
    }
    public void add(Listas item) {
        //mDataset.add(position, item);
        mDataset.add(item);
        //notifyItemInserted(position);
    }

    public void remove(Listas item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        //notifyItemRemoved(position);
    }

    public TodoAdapter(ArrayList<Listas> myDataset, Context context) {
        mDataset = myDataset;
        try {
            this.mAdapterCallback = ((TodoAdapterCallback) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AdapterCallback.");
        }
    }


    // Create new views (invoked by the layout manager)
    @Override
    public TodoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_todo, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Listas lista = mDataset.get(position);
        holder.description.setText(mDataset.get(position).getDescripcion());
        //holder.tick.setVisibility(mDataset.get(position).get);
        /*holder.tick.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(name);
            }
        });

        holder.txtFooter.setText("Footer: " + mDataset.get(position));*/

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public boolean isEditMenuVisible() {
        return editMenuVisible;
    }

    public void setEditMenuVisible(boolean editMenuVisible) {
        this.editMenuVisible = editMenuVisible;
    }
}
