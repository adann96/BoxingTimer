package com.example.stoperbokserski.ui.gallery;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.stoperbokserski.R;
import com.example.stoperbokserski.databinding.FragmentGalleryBinding;

import org.w3c.dom.Text;

import java.util.Timer;
import java.util.TimerTask;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    Button button1;
    TextView text1;
    static int interval;
    static Timer countdown;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel = new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        button1 = root.findViewById(R.id.button1);
        text1 = root.findViewById(R.id.timer1);
        clickButton1();

        final TextView textView = binding.timer1;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void clickButton1() {
        CharSequence text = "Hello toast!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getActivity(), "Attack!", duration);

        MediaPlayer beginBell = MediaPlayer.create(getActivity(), R.raw.app_src_main_res_raw_singlebell);
        MediaPlayer endBell = MediaPlayer.create(getActivity(), R.raw.app_src_main_res_raw_bell);
        MediaPlayer oneScream = MediaPlayer.create(getActivity(), R.raw.one);
        MediaPlayer twoScream = MediaPlayer.create(getActivity(), R.raw.two);
        MediaPlayer threeScream = MediaPlayer.create(getActivity(), R.raw.three);
        MediaPlayer fourScream = MediaPlayer.create(getActivity(), R.raw.four);
        MediaPlayer fiveScream = MediaPlayer.create(getActivity(), R.raw.five);
        MediaPlayer sixScream = MediaPlayer.create(getActivity(), R.raw.six);

        MediaPlayer ringTable[]={oneScream,twoScream,threeScream,fourScream,fiveScream,sixScream};
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //text1.setText("This is gallery fragment");
                int delay = 1000;
                int period = 1000;
                countdown = new Timer();
                interval = Integer.parseInt(String.valueOf(184));
                countdown.scheduleAtFixedRate(new TimerTask() {

                    public void run() {
                        int ret = setInterval();
                        //text1.setText(String.valueOf(ret));
                        if (ret == 180) {
                            beginBell.start();
                        }
                        else if ((ret % 5==0) && (ret != 180) && (ret > 1)) {
                            System.out.println(ret + "scream");
                            toast.show();
                            int range = ringTable.length - 1;
                            int voice = (int)Math.round(Math.random()*range);
                            ringTable[voice].start();
                        }
                        else if (ret == 1) {
                            endBell.start();
                        }
                        else {
                            System.out.println(ret);
                        }
                    }
                }, delay, period);

            }

        });
    }

    private static final int setInterval() {
        if (interval == 1)
            countdown.cancel();
        int ret = --interval;
        return ret;
    }
}