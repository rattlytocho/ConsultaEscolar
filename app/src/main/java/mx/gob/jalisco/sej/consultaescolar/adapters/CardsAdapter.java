package mx.gob.jalisco.sej.consultaescolar.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.BundleCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import mx.gob.jalisco.sej.consultaescolar.R;
import mx.gob.jalisco.sej.consultaescolar.ViewPage;
import mx.gob.jalisco.sej.consultaescolar.objects.Card;

/**
 * Created by root on 28/06/16.
 */
public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder>{

    List<Card> items = new ArrayList<>();

    private final Context context;

    public CardsAdapter(List<Card> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView title;
        public ImageView img;
        public RelativeLayout to;

        public ViewHolder(View v, CardsAdapter padre) {
            super(v);

            img = (ImageView) v.findViewById(R.id.image);
            title = (TextView) v.findViewById(R.id.title);
            to = (RelativeLayout) v.findViewById(R.id.to);
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = null;

        v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_item, viewGroup, false);
        return new ViewHolder(v,this);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {

        //viewHolder.text.setText(items.get(i).getText());

        viewHolder.img.setImageResource(items.get(i).getImage());
        viewHolder.title.setText(items.get(i).getText());

        final int item = i;

        viewHolder.to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewPage.class);

                Bundle bundle = new Bundle();
                bundle.putString("URL",items.get(item).getUrl());
                bundle.putString("TITLE",items.get(item).getText());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        setFadeAnimation(viewHolder.itemView);
    }
    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(1);
        view.startAnimation(anim);
    }
}