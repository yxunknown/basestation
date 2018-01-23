package com.dev.mevur.baselocation;

import android.content.Context;
import android.telephony.CellInfo;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;

import java.util.Date;
import java.util.List;

/**
 * Created by Mevur on 01/22/18.
 */

public class BaseStation {
    private Context context;

    public BaseStation(Context context) {
        this.context = context;
    }

    public LogMessage getCurrentBaseStation() {
        TelephonyManager manager = (TelephonyManager) this.context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String operator = manager.getNetworkOperator();
        if (null == operator) {
            //获取运营商失败
            return null;
        } else {
            //mobile country code,移动国家代码(中国为460)
            int mcc = Integer.parseInt(operator.substring(0, 3));
            //mobile network code,移动网络代码(中国移动为0，中国联通为1，中国电信为2)
            int mnc = Integer.parseInt(operator.substring(3));
            try {
                GsmCellLocation location = (GsmCellLocation) manager.getCellLocation();
                int lac = location.getLac();
                int cellId = location.getCid();
                Log.i(BaseStation.class.getName(), "mcc:" + mcc + ",mnc:" +
                        mnc + ",lac:" + lac + "cellId:" + cellId);
                LogMessage msg = new LogMessage();
                msg.setMcc(mcc);
                msg.setMnc(mnc);
                msg.setLac(lac);
                msg.setCi(cellId);
                msg.setTime(new Date(System.currentTimeMillis()).toString());
                return msg;
            } catch (SecurityException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public List<NeighboringCellInfo> getBaseStations() {
        List<CellInfo> infos = null;
        TelephonyManager manager = (TelephonyManager) this.context
                                   .getSystemService(Context.TELEPHONY_SERVICE);
        String operator = manager.getNetworkOperator();
        if (null == operator) {
            //获取运营商失败
            return null;
        } else {
            try {
                GsmCellLocation location = (GsmCellLocation) manager.getCellLocation();
                int lac = location.getLac();
                int cellId = location.getCid();
                infos = manager.getAllCellInfo();
                for (CellInfo info : infos) {

                }
            } catch (SecurityException e) {
                e.printStackTrace();
                //获取权限失败
                return null;
            }
        }
        return null;
    }

}
