package fishtudo.sos.detailsfragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import fishtudo.sos.Establishment;
import fishtudo.sos.R;

/**
 * Created by Carlos on 15/10/2016.
 */
public class DetailsFragment extends Fragment {

    private static String ARGUMENT_ESTABLISHMENT = "argument stablishment";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.detailsfragment, container, false);
        ((TextView)rootView.findViewById(R.id.name)).setText(getEstablishment().getName());
        ((TextView)rootView.findViewById(R.id.description)).setText(getEstablishment().getDescription());
        ((TextView)rootView.findViewById(R.id.administrative_esphere)).setText(getActivity().getString(
                R.string.administrative_esphere_description) +" "+ getEstablishment().getAdministrativeSphere());

        ((CheckBox)rootView.findViewById(R.id.sus)).setChecked(getEstablishment().hasSus());
        ((CheckBox)rootView.findViewById(R.id.emergency)).setChecked(getEstablishment().hasEmergency());
        ((CheckBox)rootView.findViewById(R.id.ambulatory)).setChecked(getEstablishment().hasAmbulatory());
        ((CheckBox)rootView.findViewById(R.id.surgery)).setChecked(getEstablishment().hasSurgery());
        ((CheckBox)rootView.findViewById(R.id.obstetrics)).setChecked(getEstablishment().hasObstetrics());
        ((CheckBox)rootView.findViewById(R.id.neonatal)).setChecked(getEstablishment().hasNeonatal());
        ((CheckBox)rootView.findViewById(R.id.dialysis)).setChecked(getEstablishment().hasDialysis());

        return rootView;
    }

    private Establishment getEstablishment(){
        if (getArguments() == null || !getArguments().containsKey(ARGUMENT_ESTABLISHMENT)){
            return null;
        }

        return getArguments().getParcelable(ARGUMENT_ESTABLISHMENT);
    }

    public static DetailsFragment createInstance(Establishment establishment){
        Bundle bundle = new Bundle();
        bundle.putParcelable(ARGUMENT_ESTABLISHMENT, establishment);
        DetailsFragment detailsFragment =  new DetailsFragment();
        detailsFragment.setArguments(bundle);
        return detailsFragment;
    }
}
