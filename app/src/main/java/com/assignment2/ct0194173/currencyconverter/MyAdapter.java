package com.assignment2.ct0194173.currencyconverter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements DownloadTask.Document {

    private List<Country> myList;
    private Context context;

    public MyAdapter(Context context){
        myList = new ArrayList<>();
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item,parent,false);
        return (new ViewHolder(view));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Country myCountry = myList.get(position);
        holder.bind(myCountry);
    }

    @Override
    public int getItemCount() {
        return myList.size();
    }

    @Override
    public void setDocument(List<Double> list) {
        String[] currency_symbols = context.getResources().getStringArray(R.array.currency_symbols);
        String[] currency_names = context.getResources().getStringArray(R.array.currency_names);
        for(int i=1; i<list.size() ;i++){
            myList.add(new Country(currency_names[i],currency_symbols[i],MyFlags.getFlags()[i],list.get(i)));
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv1,tv2,tv3;
        private ImageView myFlag;
        public ViewHolder(View itemView) {
            super(itemView);
            myFlag = (ImageView) itemView.findViewById(R.id.img);
            tv1 = (TextView) itemView.findViewById(R.id.txt1);
            tv2 = (TextView) itemView.findViewById(R.id.txt2);
            tv3 = (TextView) itemView.findViewById(R.id.txt3);
        }
        public void bind(Country c){
            myFlag.setImageResource(c.getFlag());
            tv1.setText(c.getCurrencyName());
            tv2.setText(c.getCountryName());
            tv3.setText(String.valueOf(c.getRate()));
        }
    }
}
