package com.example.cryptocurrencyapp.adaptor;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cryptocurrencyapp.R;
import com.example.cryptocurrencyapp.model.CryptoModel;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdaptor extends RecyclerView.Adapter<CustomAdaptor.MyViewHolder> implements Filterable {
    private ArrayList<CryptoModel> cryptoList;
    private ArrayList<CryptoModel> filteredCryptoList;
    private Activity context;
    private CustomItemClickListener customItemClickListener;

    public CustomAdaptor(Activity context,ArrayList<CryptoModel> cryptoList,CustomItemClickListener customItemClickListener) {
        this.cryptoList = cryptoList;
        this.filteredCryptoList = cryptoList;
        this.context = context;
        this.customItemClickListener = customItemClickListener;
    }

    @Override
    public CustomAdaptor.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.currency_item, viewGroup, false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // for click item listener
                customItemClickListener.onItemClick(filteredCryptoList.get(myViewHolder.getAdapterPosition()),myViewHolder.getAdapterPosition());
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(CustomAdaptor.MyViewHolder viewHolder, int position) {

        String name = filteredCryptoList.get(position).getName();

        if (name.length() > 18){
            viewHolder.name.setText(name.substring(0,18) + "...");
        }
        else if (name.length() > 14){
            viewHolder.name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        }
        else {
            viewHolder.name.setText(name);
        }


        String currency = filteredCryptoList.get(position).getCurrency();
        if (currency.length() > 6){
            viewHolder.currency.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        }
        else if (currency.length() > 3){
            viewHolder.currency.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        }

        viewHolder.currency.setText(filteredCryptoList.get(position).getCurrency());



        String price = filteredCryptoList.get(position).getPrice();
        if (price.length() > 8){
            viewHolder.price.setText("$ "+ price.substring(0,8));
        }
        else {
            viewHolder.price.setText("$ "+ price);
        }

        viewHolder.rank.setText("#" + filteredCryptoList.get(position).getRank());


        String time = filteredCryptoList.get(position).getPriceDate();
        String stamp = "Time : " + time.substring(time.length()-9);
        viewHolder.timestamp.setText(stamp);

        String logo_path = filteredCryptoList.get(position).getLogo();
        if (logo_path.equals("")){
            viewHolder.logo.setImageResource(R.drawable.not_found);

        }
        else {
                String image_type = logo_path.substring(logo_path.length()-3);
                if (image_type.equals("svg")){
                    GlideToVectorYou.justLoadImage(context, Uri.parse(logo_path), viewHolder.logo);
                }
                else{
                    Picasso.get().load(logo_path).into(viewHolder.logo);

                }

        }

    }

    @Override
    public int getItemCount() {
        return filteredCryptoList.size();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String searchString = charSequence.toString();

                if (searchString.isEmpty()) {

                    filteredCryptoList = cryptoList;

                } else {

                    ArrayList<CryptoModel> tempFilteredList = new ArrayList<>();

                    for (CryptoModel currentCrypto : cryptoList) {

                        // search for user name
                        if (currentCrypto.getName().toLowerCase().contains(searchString)) {

                            tempFilteredList.add(currentCrypto);
                        }
                    }

                    filteredCryptoList = tempFilteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredCryptoList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredCryptoList = (ArrayList<CryptoModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView currency;
        private TextView price;
        private TextView name;
        private ImageView logo;
        private TextView timestamp;
        private TextView rank;
        public MyViewHolder(View view) {
            super(view);
            currency = view.findViewById(R.id.currency);
            price = view.findViewById(R.id.price);
            name = view.findViewById(R.id.name);
            logo = view.findViewById(R.id.logo);
            timestamp = view.findViewById(R.id.priceDate);
            rank = view.findViewById(R.id.rank);

        }
    }
}
