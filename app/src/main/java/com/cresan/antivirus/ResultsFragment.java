package com.cresan.antivirus;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.cresan.androidprotector.R;
import com.tech.applications.coretools.ActivityTools;
import com.tech.applications.coretools.StringTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hexdump on 02/11/15.
 */

public class ResultsFragment extends Fragment
{
    final String _logTag=this.getClass().getSimpleName();

    AntivirusActivity getMainActivity() {return (AntivirusActivity) getActivity();}

    List<IProblem> _problems =null;

    private ListView _listview;

    ResultsAdapter _resultAdapter=null;

    TextView _threatsFoundSummary=null;

    public void setData(AntivirusActivity antivirusActivity, List<IProblem> problems)
    {
        _problems =problems;
        _resultAdapter=new ResultsAdapter(antivirusActivity, problems);

        _resultAdapter.setResultItemSelectedStateChangedListener(new IResultItemSelectedListener()
        {
            @Override
            public void onItemSelected(IProblem bpd)
            {
                showInfoAppFragment(bpd);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.results_fragment, container, false);

        _threatsFoundSummary = (TextView) rootView.findViewById(R.id.counterApps);

        _setupFragment(rootView);

        if(ActivityTools.checkIfUSBDebugIsEnabled(getContext()))
        {
           Log.i("usb","USB ENABLEEEE");

        }else

        {
            Log.i("usb", "USB NOT ENABLEEEE");
        }


        return rootView;
    }



    protected void _setupFragment(View view)
    {
        _listview = (ListView) view.findViewById(R.id.list);

        /*_resultAdapter=new ResultsAdapter(getMainActivity(), _problems, getMainActivity());
        _resultAdapter.setResultItemSelectedStateChangedListener(new IResultItemSelectedListener()
        {
            @Override
            public void onItemSelectedStateChanged(boolean isChecked, ResultsAdapterAppItem bpdw)
            {
                if (isChecked)
                {
                    // Si marcamos el checkbox cogemos su nombre de paquete y lo metemos en la lista
                    _selectedApps.add(bpdw);
                    Log.i("MSF", "METIDO A LA LISTA: " + bpdw.bpd.getPackageName());

                } else
                {

                    // Si desmarcamos el checkbox eliminamos el  nombre del paquete de la lista
                    _selectedApps.remove(bpdw);
                    Log.i("MSF", "SACADO DE A LA LISTA: " + bpdw.bpd.getPackageName());
                }
            }
        });*/



        _listview.setAdapter(_resultAdapter);
    }

    @Override
    public void onResume()
    {
        super.onResume();

        MenacesCacheSet menacesCache = getMainActivity().getMenacesCacheSet();

        //Remove not existant menaces and save this as current list if it was modified
        /*boolean dirty=ProblemsDataSetTools.removeNotExistingProblems(getActivity(),menacesCache);
        if(dirty)
            menacesCache.writeToJSON();
        ç*/
        getMainActivity().updateMenacesAndWhiteUserList();

        //Add new existant apps
        _resultAdapter.refreshByProblems(new ArrayList<IProblem>(menacesCache.getSet()));

        _updateFoundThreatsText(_threatsFoundSummary, _resultAdapter.getRealCount());

        if(menacesCache.getItemCount()<=0)
        {
            getMainActivity().goBack();
        }
    }

    void _updateFoundThreatsText(TextView textView, int appCount)
    {
        String finalStr=getString(R.string.threats_found);
        finalStr=StringTools.fillParams(finalStr, "#", Integer.toString(appCount));
        textView.setText(finalStr);
    }

    void showInfoAppFragment(final IProblem problem)
    {
        // Cuando pulses el boton de info coger su posicion y pasarselo por la variable pos
        InfoAppFragment newFragment =(InfoAppFragment) getMainActivity().slideInFragment(AntivirusActivity.kInfoFragmnetTag);
        /*newFragment.setAppEventListener(new IOnAppEvent()
        {
            @Override
            public void onAppUninstalled(AppProblem uninstalledApp)
            {
                _resultAdapter.removeByPackageData(uninstalledApp);
            }
        });*/
        newFragment.setData(problem);


    }
}

