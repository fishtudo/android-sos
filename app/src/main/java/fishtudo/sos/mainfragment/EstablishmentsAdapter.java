package fishtudo.sos.mainfragment;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import api.location.LocationUtils;
import api.util.StringUtil;
import fishtudo.sos.Establishment;
import fishtudo.sos.R;
import fishtudo.sos.permissions.PermissionResultListener;

/**
 * Created by Carlos on 13/10/2016.
 */
public class EstablishmentsAdapter extends BaseAdapter implements PermissionResultListener {

    private List<Establishment> establishments;

    private Activity activity;

    private Location userLocation;

    private String lastPhoneToCall = "";

    private EstablishmentSelectListener establishmentSelectListener;

    private final int MY_PERMISSIONS_REQUEST_MAKE_CALL = 1234;

    public EstablishmentsAdapter(Activity activity, List<Establishment> establishments, Location userLocation){
        this.establishments = establishments;
        this.activity = activity;
        this.userLocation = userLocation;
    }

    public void setEstablishmentSelectListener(EstablishmentSelectListener establishmentSelectListener) {
        this.establishmentSelectListener = establishmentSelectListener;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(activity).inflate(R.layout.establishment_cell, parent, false);
        ((TextView)view.findViewById(R.id.name)).setText(establishments.get(position).getName());
        ((TextView)view.findViewById(R.id.phone)).setText(establishments.get(position).getPhoneNumber());
        ((TextView)view.findViewById(R.id.distance)).setText(calculateDistance(establishments.get(position)));
        view.findViewById(R.id.call_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastPhoneToCall = establishments.get(position).getPhoneNumber();
                if(!StringUtil.isEmpty(lastPhoneToCall)){
                    makeCall(lastPhoneToCall);
                } else{
                    Toast.makeText(activity, "Telefone inválido!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        view.findViewById(R.id.drive_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap(establishments.get(position).getLatitude(), establishments.get(position).getLongitude(),
                        establishments.get(position).getName());
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(establishmentSelectListener !=null){
                    establishmentSelectListener.onEstablishmentSelected(establishments.get(position));
                }
            }
        });

        return view;
    }

    private void makeCall(String call){
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+call));
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_REQUEST_MAKE_CALL);
            return;
        }

        activity.startActivity(intent);
    }
    private void openMap(double latitude, double longitude, String establishmentName){
        String uri = "http://maps.google.com/maps?q=loc:"+latitude+","+longitude+" ("+establishmentName+")";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        intent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
        intent.setFlags(Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP);
        intent.setData(Uri.parse(uri));
        activity.startActivity(intent);
    }

    private String calculateDistance(Establishment establishment){
        return LocationUtils.formmatDistance(
                userLocation.distanceTo(LocationUtils.createLocation(
                        establishment.getLatitude(), establishment.getLongitude())));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            switch (requestCode) {
                case MY_PERMISSIONS_REQUEST_MAKE_CALL:
                    if (grantResults.length > 0
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        makeCall(lastPhoneToCall);
                    }
                    break;
                default:
            }
    }
}