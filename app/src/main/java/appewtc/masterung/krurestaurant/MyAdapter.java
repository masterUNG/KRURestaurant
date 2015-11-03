package appewtc.masterung.krurestaurant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by masterUNG on 11/3/15 AD.
 */
public class MyAdapter extends BaseAdapter{

    private Context objContext;
    private String[] foodStrings, priceStrings, sourceStrings;

    public MyAdapter(Context objContext, String[] foodStrings, String[] priceStrings, String[] sourceStrings) {
        this.objContext = objContext;
        this.foodStrings = foodStrings;
        this.priceStrings = priceStrings;
        this.sourceStrings = sourceStrings;
    }

    @Override
    public int getCount() {
        return foodStrings.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater objLayoutInflater = (LayoutInflater) objContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View objView1 = objLayoutInflater.inflate(R.layout.food_listview, viewGroup, false);

        TextView foodTextView = (TextView) objView1.findViewById(R.id.txtFood);
        foodTextView.setText(foodStrings[i]);

        TextView priceTextView = (TextView) objView1.findViewById(R.id.txtPrice);
        priceTextView.setText(priceStrings[i]);

        ImageView iconImageView = (ImageView) objView1.findViewById(R.id.imvIcon);
        Picasso.with(objContext).load(sourceStrings[i]).resize(100,100).into(iconImageView);


        return null;
    }
}   // Main Class
