package com.cresan.antivirus;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.provider.MediaStore;

import com.tech.applications.coretools.FileTools;
import com.tech.applications.coretools.JSonTools;
import com.tech.applications.coretools.MediaTools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by hexdump on 22/01/16.
 */
public class UserWhiteList extends JSONDataSet<IProblem>
{
    public UserWhiteList(Context context)
    {
        super(context,"userwhitelist.json",new ProblemFactory());
    }

    boolean checkIfSystemPackageInList(Class<?> type)
    {
        Set<IProblem> problems=getSet();

        SystemProblem problem=null;

        for(IProblem p : problems)
        {
            if(p.getType()== IProblem.ProblemType.SystemProblem)
            {
                problem=(SystemProblem) p;
                if(problem.getClass()==type)
                    return true;
            }
        }

        return false;
    }
}
