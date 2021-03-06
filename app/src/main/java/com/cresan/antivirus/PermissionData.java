package com.cresan.antivirus;

import android.content.pm.ActivityInfo;

import java.io.Serializable;

/**
 * Created by hexdump on 15/01/16.
 */
public class PermissionData
{
    private int _dangerous;
    public int getDangerous() {return _dangerous;}
    private String _permissionName;
    public String getPermissionName() { return _permissionName;}

    public PermissionData(String permissionName, int dangerous)
    {
        _permissionName=permissionName;
        _dangerous=dangerous;
    }

    public int hashCode()
    {
        return (int) _permissionName.hashCode()+_dangerous;
    }

    public boolean equals(Object o)
    {
        if(o == null)
            return false;

        PermissionData other = (PermissionData) o;
        return  _permissionName.equals(other._permissionName);
    }
}
