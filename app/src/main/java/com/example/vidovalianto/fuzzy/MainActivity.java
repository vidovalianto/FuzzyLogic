package com.example.vidovalianto.fuzzy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    float sdk,sdg,byk,fll;
    float sdgn,dgn,nrml,pns;

    float[] ptr;
    float[] nilteng;

    float jml_nil,jml_nilteng;
    float coa;

    EditText et_orang,et_suhu,et_luas;
    TextView tv_kecepatan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_orang = (EditText) findViewById(R.id.jumlahorang);
        et_suhu = (EditText) findViewById(R.id.suhu);
        et_luas = (EditText) findViewById(R.id.luasruangan);

        tv_kecepatan = (TextView) findViewById(R.id.kecepatan);

        Button btn_send = (Button) findViewById(R.id.send);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initializeArray();
                String jumlahorang = et_orang.getText().toString();
                String suhu = et_suhu.getText().toString();
                String luasruangan = et_luas.getText().toString();
                Kepadatan(Integer.valueOf(jumlahorang),Integer.valueOf(luasruangan));
                mengukurSuhu(Integer.valueOf(suhu));
                rule();
                kecepatanKipas();
                tv_kecepatan.setText(String.valueOf(coa));
            }
        });
    }

    public void Kepadatan(int sigm,int luas){

        //fuzzyfikasi jumlah orang

        int luasorang = sigm * 2;
        float densitas = luasorang/luas;

        if(densitas<=0.25)       //sigm=jumlah orang
        {
            sdk=100-(densitas*400);         //sdk=sedikit
            sdg=(densitas*400);             //sdg=sedang
            byk=0;                     //byk=banyak
            fll=0;                     //fll=full
        }
        else if(densitas>0.25 && densitas<=0.5)
        {
            sdk=0;
            sdg=200-(densitas*400);
            byk=(densitas*400)-100;
            fll=0;
        }
        else if(densitas>0.5 && densitas<=0.75)
        {
            sdk=0;
            sdg=0;
            byk=300-(densitas*400);
            fll=(densitas*400)-200;
        }
        else
        {
            sdk=0;
            sdg=0;
            byk=0;
            fll=100;
        }

    };


//    public void Kepadatan(int sigm){
//
//        //fuzzyfikasi jumlah orang
//
//        if(sigm<=5)                //sigm=jumlah orang
//        {
//            sdk=100-(sigm*20);         //sdk=sedikit
//            sdg=(sigm*20);             //sdg=sedang
//            byk=0;                     //byk=banyak
//            fll=0;                     //fll=full
//        }
//        else if(sigm>5 && sigm<=10)
//        {
//            sdk=0;
//            sdg=200-(sigm*20);
//            byk=(sigm*20)-100;
//            fll=0;
//        }
//        else if(sigm>10 && sigm<=15)
//        {
//            sdk=0;
//            sdg=0;
//            byk=300-(sigm*20);
//            fll=(sigm*20)-200;
//        }
//        else
//        {
//            sdk=0;
//            sdg=0;
//            byk=0;
//            fll=100;
//        }
//
//    };

    public void mengukurSuhu(int tmp ){

        //fuzzyfikasi suhu
        if(tmp<=16)                //tmp=temperatur terukur
        {
            sdgn=100;               //sdgn=sangat dingin
            dgn=0;                  //dgn=gingin
            nrml=0;                 //nrml=normal
            pns=0;                  //pns=panas
        }
        else if(tmp>16 && tmp<=24)
        {
            sdgn=300-((tmp*25)/2);
            dgn=((tmp*25)/2)-200;
            nrml=0;
            pns=0;
        }
        else if(tmp>24 && tmp<=32)
        {
            sdgn=0;
            dgn=400-((tmp*25)/2);
            nrml=((tmp*25)/2)-300;
            pns=0;
        }
        else if(tmp>32 && tmp<=40)
        {
            sdgn=0;
            dgn=0;
            nrml=500-((tmp*25)/2);
            pns=((tmp*25)/2)-400;
        }
        else
        {
            sdgn=0;
            dgn=0;
            nrml=0;
            pns=100;
        }
    }


    public void initializeArray(){
        ptr = new float[]{sdk,sdk,sdk,sdk,sdg,sdg,sdg,sdg,byk,byk,byk,byk,fll,fll,fll,fll};
        nilteng = new float[]{sdgn,dgn,nrml,pns,sdgn,dgn,nrml,pns,sdgn,dgn,nrml,pns,sdgn,dgn,nrml,pns};
    }

    public void rule(){

        //rule based
        if(sdk<sdgn)
        {ptr[0]=sdk;nilteng[0]=64;}
        else
        {ptr[0]=sdgn;nilteng[0]=64;}

        if(sdk<dgn)
        {ptr[1]=sdk;nilteng[1]=64;}
        else
        {ptr[1]=dgn;nilteng[1]=64;}

        if(sdk<nrml)
        {ptr[2]=sdk;nilteng[2]=128;}
        else
        {ptr[2]=nrml;nilteng[2]=128;}

        if(sdk<pns)
        {ptr[3]=sdk;nilteng[3]=192;}
        else
        {ptr[3]=pns;nilteng[3]=192;}

        if(sdg<sdgn)
        {ptr[4]=sdg;nilteng[4]=64;}
        else
        {ptr[4]=sdgn;nilteng[4]=64;}

        if(sdg<dgn)
        {ptr[5]=sdg;nilteng[5]=64;}
        else
        {ptr[5]=dgn;nilteng[5]=64;}

        if(sdg<nrml)
        {ptr[6]=sdg;nilteng[6]=128;}
        else
        {ptr[6]=nrml;nilteng[6]=128;}

        if(sdg<pns)
        {ptr[7]=sdg;nilteng[7]=255;}
        else
        {ptr[7]=pns;nilteng[7]=255;}

        if(byk<sdgn)
        {ptr[8]=byk;nilteng[8]=64;}
        else
        {ptr[8]=sdgn;nilteng[8]=64;}

        if(byk<dgn)
        {ptr[9]=byk;nilteng[9]=64;}
        else
        {ptr[9]=dgn;nilteng[9]=64;}

        if(byk<nrml)
        {ptr[10]=byk;nilteng[10]=192;}
        else
        {ptr[10]=nrml;nilteng[10]=192;}

        if(byk<pns)
        {ptr[11]=byk;nilteng[11]=255;}
        else
        {ptr[11]=pns;nilteng[11]=255;}

        if(fll<sdgn)
        {ptr[12]=fll;nilteng[12]=64;}
        else
        {ptr[12]=sdgn;nilteng[12]=64;}

        if(fll<dgn)
        {ptr[13]=fll;nilteng[13]=128;}
        else
        {ptr[13]=dgn;nilteng[13]=128;}

        if(fll<nrml)
        {ptr[14]=fll;nilteng[14]=192;}
        else
        {ptr[14]=nrml;nilteng[14]=192;}

        if(fll<pns)
        {ptr[15]=fll;nilteng[15]=255;}
        else
        {ptr[15]=pns;nilteng[15]=255;}
    }

    public void kecepatanKipas(){
        jml_nil=ptr[0]+ptr[1]+ptr[2]+ptr[3]+ptr[4]+ptr[5]+ptr[6]+ptr[7]+ptr[8]+ptr[9]+ptr[10]+ptr[11]+ptr[12]+ptr[13]+ptr[14]+ptr[15];
        jml_nilteng=(ptr[0]*nilteng[0])+(ptr[1]*nilteng[1])+(ptr[2]*nilteng[2])+(ptr[3]*nilteng[3])+(ptr[4]*nilteng[4])+(ptr[5]*nilteng[5])+(ptr[6]*nilteng[6])+(ptr[7]*nilteng[7])+(ptr[8]*nilteng[8])+(ptr[9]*nilteng[9])+(ptr[10]*nilteng[10])+(ptr[11]*nilteng[11])+(ptr[12]*nilteng[12])+(ptr[13]*nilteng[13])+(ptr[14]*nilteng[14])+(ptr[15]*nilteng[15]);

        //defuzzyfikasi
        if(jml_nil==0)
        {
            coa=0;
        }
        else
        {
            coa=jml_nilteng/jml_nil;
        }
    };


}
