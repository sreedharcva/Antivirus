package com.cresan.antivirus;

import android.content.pm.PackageInfo;

/**
 * Created by hexdump on 15/01/16.
 */
public class PackageResultData
{
    public String getPackageName() { return _packageInfo.packageName; }

    private PackageInfo _packageInfo;
    public PackageInfo getPackageInfo() {return _packageInfo;}
    public void setPackageInfo(PackageInfo pi) { _packageInfo=pi;}

    public PackageResultData(PackageInfo packageInfo)
    {
        _packageInfo=packageInfo;
    }


    public int hashCode()
    {
        return (int) getPackageName().hashCode();
    }

    public boolean equals(Object o)
    {
        if(o == null)
            return false;

        PackageResultData other = (PackageResultData) o;
        return _packageInfo.packageName.equals(other._packageInfo);
    }
}
