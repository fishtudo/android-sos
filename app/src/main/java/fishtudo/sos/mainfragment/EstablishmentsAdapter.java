package fishtudo.sos.mainfragment;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import api.location.LocationUtils;
import fishtudo.sos.Establishment;
import fishtudo.sos.R;

/**
 * Created by Carlos on 13/10/2016.
 */
public class EstablishmentsAdapter extends BaseAdapter {

    private List<Establishment> establishments;

    private Context context;

    private Location userLocation;

    public EstablishmentsAdapter(Context context, List<Establishment> establishments, Location userLocation){
        this.establishments = establishments;
        this.context = context;
        this.userLocation = userLocation;
    }

    @Override
    public int getCount() {
        return establishments == null ? 0 : establishments.size() ;
    }

    @Override
    public Establishment getItem(int position) {
        return getCount() > position ? establishments.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.establishment_cell, parent, false);
        ((TextView)view.findViewById(R.id.name)).setText(establishments.get(position).getName());
        ((TextView)view.findViewById(R.id.phone)).setText(establishments.get(position).getPhoneNumber());
        ((TextView)view.findViewById(R.id.distance)).setText(calculateDistance(establishments.get(position)));
        return view;
    }

    private String calculateDistance(Establishment establishment){
        return LocationUtils.formmatDistance(
                userLocation.distanceTo(LocationUtils.createLocation(
                        establishment.getLatitude(), establishment.getLongitude())));
    }
}