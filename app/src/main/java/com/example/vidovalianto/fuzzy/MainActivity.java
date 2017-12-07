package com.example.vidovalianto.fuzzy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    float sedikit,sedang,banyak,full;
    float sdingin,dingin,normal,panas;

    float[] ptr;
    float[] nilteng;

    float jml_nil,jml_nilteng;
    float coa;

    EditText et_orang,et_suhu,et_luas;
    TextView tv_kecepatan,tv_hintdensitas,tv_hintsuhu,tv_hintkecepatan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_orang = (EditText) findViewById(R.id.jumlahorang);

        et_suhu = (EditText) findViewById(R.id.suhu);
        et_luas = (EditText) findViewById(R.id.luasruangan);

        tv_kecepatan = (TextView) findViewById(R.id.kecepatan);
        tv_hintdensitas = (TextView) findViewById(R.id.hintdensitas);
        tv_hintsuhu = (TextView) findViewById(R.id.hintsuhu);
        tv_hintkecepatan = (TextView) findViewById(R.id.hintkecepatan);

        Button btn_send = (Button) findViewById(R.id.send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jumlahorang = et_orang.getText().toString();
                String suhu = et_suhu.getText().toString();
                String luasruangan = et_luas.getText().toString();
                Kepadatan(Integer.valueOf(jumlahorang),Integer.valueOf(luasruangan));
                mengukurSuhu(Integer.valueOf(suhu));
                initializeArray();
                rule();
                kecepatanKipas();
                tv_kecepatan.setText(String.valueOf(coa));
            }
        });
    }


    public void Kepadatan(int sigm,int luas){

        //fuzzyfikasi jumlah orang

        int luasorang = sigm;       //ini jadi diganti jadi jumlah orang aja gak?
        float densitas = luasorang/luas;

        if(densitas<=0.25)       //sigm=jumlah orang
        {
            sedikit=100-(densitas*400);
            sedang=(densitas*400);
            banyak=0;
            full=0;
            if(sedikit>=sedang){
                tv_hintdensitas.setText("Sedikit");
            }else{
                tv_hintdensitas.setText("Sedang");
            }
        }
        else if(densitas>0.25 && densitas<=0.5)
        {
            sedikit=0;
            sedang=200-(densitas*400);
            banyak=(densitas*400)-100;
            full=0;
            if(banyak>=sedang){
                tv_hintdensitas.setText("Banyak");
            }else{
                tv_hintdensitas.setText("Sedang");
            }

        }
        else if(densitas>0.5 && densitas<=0.75)
        {
            sedikit=0;
            sedang=0;
            banyak=300-(densitas*400);
            full=(densitas*400)-200;
            if(banyak>=full){
                tv_hintdensitas.setText("Banyak");
            }else{
                tv_hintdensitas.setText("Full");
            }
        }
        else
        {
            sedikit=0;
            sedang=0;
            banyak=0;
            full=100;
            tv_hintdensitas.setText("Full");
        }

    };

    public void mengukurSuhu(int tmp ){

        // fuzzyfikasi suhu

        if(tmp<=16)                //tmp = temperatur terukur
        {
            sdingin=100;
            dingin=0;
            normal=0;
            panas=0;
            tv_hintsuhu.setText("Sangat Dingin");
        }
        else if(tmp>16 && tmp<=24)
        {
            sdingin=300-((tmp*25)/2);
            dingin=((tmp*25)/2)-200;
            normal=0;
            panas=0;
            if(sdingin>=dingin){
                tv_hintsuhu.setText("Sangat Dingin");
            }else{
                tv_hintsuhu.setText("Dingin");
            }
        }
        else if(tmp>24 && tmp<=32)
        {
            sdingin=0;
            dingin=400-((tmp*25)/2);
            normal=((tmp*25)/2)-300;
            panas=0;
            if(normal>=dingin){
                tv_hintsuhu.setText("Normal");
            }else{
                tv_hintsuhu.setText("Dingin");
            }
        }
        else if(tmp>32 && tmp<=40)
        {
            sdingin=0;
            dingin=0;
            normal=500-((tmp*25)/2);
            panas=((tmp*25)/2)-400;
            if(normal>=panas){
                tv_hintsuhu.setText("Normal");
            }else{
                tv_hintsuhu.setText("Panas");
            }
        }
        else
        {
            sdingin=0;
            dingin=0;
            normal=0;
            panas=100;
            tv_hintsuhu.setText("Panas");
        }
    }


    public void initializeArray(){
        ptr = new float[]{sedikit,sedikit,sedikit,sedikit,sedang,sedang,sedang,sedang,banyak,banyak,banyak,banyak,full,full,full,full};
        nilteng = new float[]{sdingin,dingin,normal,panas,sdingin,dingin,normal,panas,sdingin,dingin,normal,panas,sdingin,dingin,normal,panas};
    }
    //ptr[] = variabel untuk menampung nilai derajat keanggotaan dari input
    //nilteng[] = nilai titik tengah dari grafik fuzzyfikasi putaran kipas angin yang merupakan implementasi dari rule
    //kenapa inisiasi nilai awal seperti itu? sayapun tak tahu.


    //implikasi -> metode tsukamoto -> diambil yang membership degreenya kecil
    public void rule(){

        //rule based
        if(sedikit<sdingin)
        {ptr[0]=sedikit;nilteng[0]=64;}
        else
        {ptr[0]=sdingin;nilteng[0]=64;}

        if(sedikit<dingin)
        {ptr[1]=sedikit;nilteng[1]=64;}
        else
        {ptr[1]=dingin;nilteng[1]=64;}

        if(sedikit<normal)
        {ptr[2]=sedikit;nilteng[2]=128;}
        else
        {ptr[2]=normal;nilteng[2]=128;}

        if(sedikit<panas)
        {ptr[3]=sedikit;nilteng[3]=192;}
        else
        {ptr[3]=panas;nilteng[3]=192;}

        if(sedang<sdingin)
        {ptr[4]=sedang;nilteng[4]=64;}
        else
        {ptr[4]=sdingin;nilteng[4]=64;}

        if(sedang<dingin)
        {ptr[5]=sedang;nilteng[5]=64;}
        else
        {ptr[5]=dingin;nilteng[5]=64;}

        if(sedang<normal)
        {ptr[6]=sedang;nilteng[6]=128;}
        else
        {ptr[6]=normal;nilteng[6]=128;}

        if(sedang<panas)
        {ptr[7]=sedang;nilteng[7]=255;}
        else
        {ptr[7]=panas;nilteng[7]=255;}

        if(banyak<sdingin)
        {ptr[8]=banyak;nilteng[8]=64;}
        else
        {ptr[8]=sdingin;nilteng[8]=64;}

        if(banyak<dingin)
        {ptr[9]=banyak;nilteng[9]=64;}
        else
        {ptr[9]=dingin;nilteng[9]=64;}

        if(banyak<normal)
        {ptr[10]=banyak;nilteng[10]=192;}
        else
        {ptr[10]=normal;nilteng[10]=192;}

        if(banyak<panas)
        {ptr[11]=banyak;nilteng[11]=255;}
        else
        {ptr[11]=panas;nilteng[11]=255;}

        if(full<sdingin)
        {ptr[12]=full;nilteng[12]=64;}
        else
        {ptr[12]=sdingin;nilteng[12]=64;}

        if(full<dingin)
        {ptr[13]=full;nilteng[13]=128;}
        else
        {ptr[13]=dingin;nilteng[13]=128;}

        if(full<normal)
        {ptr[14]=full;nilteng[14]=192;}
        else
        {ptr[14]=normal;nilteng[14]=192;}

        if(full<panas)
        {ptr[15]=full;nilteng[15]=255;}
        else
        {ptr[15]=panas;nilteng[15]=255;}
    }

    public void kecepatanKipas(){
        jml_nil=ptr[0]+ptr[1]+ptr[2]+ptr[3]+ptr[4]+ptr[5]+ptr[6]+ptr[7]+ptr[8]+ptr[9]+ptr[10]+ptr[11]+ptr[12]+ptr[13]+ptr[14]+ptr[15];
        jml_nilteng=(ptr[0]*nilteng[0])+(ptr[1]*nilteng[1])+(ptr[2]*nilteng[2])+(ptr[3]*nilteng[3])+(ptr[4]*nilteng[4])+(ptr[5]*nilteng[5])+(ptr[6]*nilteng[6])+(ptr[7]*nilteng[7])+(ptr[8]*nilteng[8])+(ptr[9]*nilteng[9])+(ptr[10]*nilteng[10])+(ptr[11]*nilteng[11])+(ptr[12]*nilteng[12])+(ptr[13]*nilteng[13])+(ptr[14]*nilteng[14])+(ptr[15]*nilteng[15]);

        //defuzzyfikasi -> metode center of area. belom dikasih nilai linguistiknya nggak sih ini?? buat sebagai output
        if(jml_nil==0)
        {
            coa=0;
        }
        else
        {
            coa=jml_nilteng/jml_nil;
        }

        if(coa<=96){
            tv_hintkecepatan.setText("Sangat Pelan");
        }else if(coa<=160){
            tv_hintkecepatan.setText("Pelan");
        }else if(coa<=224){
            tv_hintkecepatan.setText("Sedang");
        }else{
            tv_hintkecepatan.setText("Cepat");
        }
    };


}
