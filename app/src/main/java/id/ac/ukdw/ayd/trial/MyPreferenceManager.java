package id.ac.ukdw.ayd.trial;


import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Juan on 3/16/17.
 */

public class MyPreferenceManager {

    SharedPreferences pref;

    SharedPreferences.Editor editor;
    Context _context;

    Panitia panitia;
    private static final String KODE_PANITIA = "kode_panitia";
    private static final String EMAIL_PANITIA = "email_panitia";
    private static final String NAMA_DEPAN_PANITIA = "nama_depan_panitia";
    private static final String NAMA_BELAKANG_PANITIA = "nama_belakang_panitia";
    private static final String NO_HP_PANITIA = "no_hp_panitia";

    public MyPreferenceManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void storePanitia(Panitia panitia){
        editor.putString(KODE_PANITIA, panitia.getKodePanitia());
        editor.putString(EMAIL_PANITIA, panitia.getEmail());
        editor.putString(NAMA_DEPAN_PANITIA, panitia.getNamaDepan());
        editor.putString(NAMA_BELAKANG_PANITIA, panitia.getNamaBelakang());
        editor.putString(NO_HP_PANITIA, panitia.getNoHpIndonesia());
        this.panitia = panitia;
        editor.commit();
//
//        Log.e(TAG, "User is stored in shared preferences. " + user.getName() + ", " + user.getEmail());
    }

    public Panitia getPanitia(){
        if (pref.getString(KODE_PANITIA, null) != null) {

            Panitia panitia = new Panitia();
            panitia.setKodePanitia(pref.getString(KODE_PANITIA, null));
            panitia.setEmail(pref.getString(EMAIL_PANITIA, null));
            panitia.setNamaBelakang(pref.getString(NAMA_BELAKANG_PANITIA, null));
            panitia.setNamaDepan(pref.getString(NAMA_DEPAN_PANITIA, null));
            panitia.setNoHpIndonesia(pref.getString(NO_HP_PANITIA, null));
            return panitia;
        }
        return null;
    }

    public void clear() {
        editor.clear();
        editor.commit();
    }
}
