package fishtudo.sos.mainfragment;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import fishtudo.sos.Establishment;
import fishtudo.sos.R;

/**
 * Created by Carlos on 12/10/2016.
 */
public class MainFragment extends Fragment {

    private final static String ARGUMENT_ESTABLISHMENTS = "argument establishment";
    private final static String ARGUMENT_LOCATION = "argument location";

    /**
     * Use createInstance instead
     */
    public MainFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);
        ((ListView)rootView.findViewById(R.id.listview)).setAdapter(new EstablishmentsAdapter(getActivity(),
                getEstablishments(),getLocation()));
        return rootView;
    }

    public List<Establishment> getEstablishments(){
        if (getArguments() == null || !getArguments().containsKey(ARGUMENT_ESTABLISHMENTS)){
            return new ArrayList<>();
        }

        return (List<Establishment>) getArguments().get(ARGUMENT_ESTABLISHMENTS);
    }

    public Location getLocation(){
        if (getArguments() == null || !getArguments().containsKey(ARGUMENT_LOCATION)){
            return null;
        }

        return (Location) getArguments().get(ARGUMENT_LOCATION);
    }

    public static MainFragment createInstance(Location location, ArrayList<Establishment> establishments){
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGUMENT_LOCATION, location);
        bundle.putParcelableArrayList(ARGUMENT_ESTABLISHMENTS, establishments);

        MainFragment mainFragment =  new MainFragment();
        mainFragment.setArguments(bundle);

        return mainFragment;
    }
}