package com.example.a183311027_makgz.AdvertManager;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a183311027_makgz.R;

import java.util.ArrayList;

public class AdvertListAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private ArrayList<Adverts> advertsArrayList;


    public AdvertListAdapter(Context context, int layout, ArrayList<Adverts> advertsArrayList) {
        this.context = context;
        this.layout = layout;
        this.advertsArrayList = advertsArrayList;
    }

    @Override
    public int getCount() {
        return advertsArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return advertsArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder {
        ImageView imageView;
        TextView tvBaslik, tvSehir,tvKat;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View myLayout = convertView;
        ViewHolder holder = new ViewHolder();

        if (myLayout == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            myLayout = inflater.inflate(layout, null);
            holder.tvBaslik = myLayout.findViewById(R.id.tvListBaslik);
            holder.tvSehir = myLayout.findViewById(R.id.tvListSehir);
            holder.imageView = myLayout.findViewById(R.id.imageMulk);
            holder.tvKat=myLayout.findViewById(R.id.tvKat);
            myLayout.setTag(holder);
        } else {
            holder = (ViewHolder) myLayout.getTag();
        }
        Adverts adverts = advertsArrayList.get(position);
        if (adverts.getAdvertsType().isEmpty()){
            holder.tvBaslik.setText(""+adverts.getTitle()+" (Belirtilmedi)");
        }else {
            holder.tvBaslik.setText(adverts.getTitle()+" "+"("+adverts.getAdvertsType()+")");
        }
        holder.tvSehir.setText(adverts.getCity()+"/"+adverts.getDistrict());
        holder.tvKat.setText(adverts.getFloor());
        byte[] recordImage = adverts.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(recordImage, 0, recordImage.length);
        holder.imageView.setImageBitmap(bitmap);


        return myLayout;
    }

    public void setFilter(ArrayList<Adverts> newList){
        advertsArrayList=new ArrayList<>();
        advertsArrayList.addAll(newList);
        notifyDataSetChanged();
    }



}
