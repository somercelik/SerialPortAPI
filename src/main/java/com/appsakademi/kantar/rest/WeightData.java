package com.appsakademi.kantar.rest;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class WeightData {
    private String date_time,brut, dara, net, urun_kodu, islem_no;
    private @Id @GeneratedValue int id;

    private static WeightData singletonWeight;

    private synchronized static void createInstance(String date_time,String brut, String dara, String net, String urun_kodu, String islem_no){
        if (singletonWeight == null){
            singletonWeight = new WeightData(date_time, brut, dara, net, urun_kodu, islem_no);
        }else {
            singletonWeight.setDate_time(date_time);
            singletonWeight.setBrut(brut);
            singletonWeight.setDara(dara);
            singletonWeight.setNet(net);
            singletonWeight.setUrun_kodu(urun_kodu);
            singletonWeight.setIslem_no(islem_no);
        }
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public static WeightData getInstance(WeightData newWeight){
        if (singletonWeight == null){
            createInstance(newWeight.date_time,newWeight.brut, newWeight.dara, newWeight.net, newWeight.urun_kodu, newWeight.islem_no);
        }else {
            singletonWeight.setDate_time(newWeight.date_time);
            singletonWeight.setBrut(newWeight.brut);
            singletonWeight.setDara(newWeight.dara);
            singletonWeight.setNet(newWeight.net);
            singletonWeight.setUrun_kodu(newWeight.urun_kodu);
            singletonWeight.setIslem_no(newWeight.islem_no);
        }
        return singletonWeight;
    }

    private WeightData() {}

    private WeightData(String date_time,String brut, String dara, String net, String urun_kodu, String islem_no) {
        this.date_time = date_time;
        this.brut = brut;
        this.dara = dara;
        this.net = net;
        this.urun_kodu = urun_kodu;
        this.islem_no = islem_no;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrut() {
        return brut;
    }

    public void setBrut(String brut) {
        this.brut = brut;
    }

    public String getDara() {
        return dara;
    }

    public void setDara(String dara) {
        this.dara = dara;
    }

    public String getNet() {
        return net;
    }

    public void setNet(String net) {
        this.net = net;
    }

    public String getUrun_kodu() {
        return urun_kodu;
    }

    public void setUrun_kodu(String urun_kodu) {
        this.urun_kodu = urun_kodu;
    }

    public String getIslem_no() {
        return islem_no;
    }

    public void setIslem_no(String islem_no) {
        this.islem_no = islem_no;
    }


}
