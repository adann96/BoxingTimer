package com.example.stoperbokserski.ui.gallery;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.stoperbokserski.R;
import com.example.stoperbokserski.databinding.FragmentSecondexerciseBinding;

import java.util.Timer;
import java.util.TimerTask;

public class SecondExerciseFragment extends Fragment {

    private FragmentSecondexerciseBinding binding;
    int secs = 180;
    Button button1, buttonAdd, buttonSubtract;
    TextView text1, textView2;
    static int interval;
    static Timer countdown;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SecondExerciseViewModel secondExerciseViewModel = new ViewModelProvider(this).get(SecondExerciseViewModel.class);

        binding = FragmentSecondexerciseBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        button1 = root.findViewById(R.id.second_exercise_start_button);
        buttonAdd = root.findViewById(R.id.buttonAdd);
        buttonSubtract = root.findViewById(R.id.buttonSubtract);

        text1 = root.findViewById(R.id.timer1);
        textView2 = root.findViewById(R.id.textView2);
        int minute = secs / 60;
        int second = 0;
        textView2.setText("0" + minute + ":" + second + second);

        clickButton1();
        addSeconds();
        subtractSeconds();

        final TextView textView = binding.timer1;
        secondExerciseViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public int addSeconds() {
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int addSec = 5;
                secs += addSec;

                int minute = secs / 60;
                int calc = secs - (minute * 60);

                if (secs == 300)  {
                    buttonAdd.setEnabled(false);
                    textView2.setText("0" + minute + ":" + "0" + calc);
                }
                else if (calc < 10) {
                    textView2.setText("0" + minute + ":" + "0" + calc);
                    buttonSubtract.setEnabled(true);
                }
                else {
                    textView2.setText("0" + minute + ":" + calc);
                    buttonSubtract.setEnabled(true);
                }

            }
        });
        return secs;
    }

    public int subtractSeconds() {
        buttonSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int addSec = 5;
                secs -= addSec;

                int minute = secs / 60;
                int calc = secs - (minute * 60);

                if (secs == 60)  {
                    buttonSubtract.setEnabled(false);
                    textView2.setText("0" + minute + ":" + "0" + calc);
                }
                else if (calc < 10) {
                    textView2.setText("0" + minute + ":" + "0" + calc);
                    buttonAdd.setEnabled(true);
                }
                else {
                    textView2.setText("0" + minute + ":" + calc);
                    buttonAdd.setEnabled(true);
                }
            }
        });
        return secs;
    }

    public void clickButton1() {
        CharSequence text = "Hello toast!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(getActivity(), "Attack!", duration);

        MediaPlayer beginBell = MediaPlayer.create(getActivity(), R.raw.app_src_main_res_raw_singlebell);
        MediaPlayer endBell = MediaPlayer.create(getActivity(), R.raw.app_src_main_res_raw_bell);
        //MediaPlayer oneScream = MediaPlayer.create(getActivity(), R.raw.one);
        MediaPlayer twoScream = MediaPlayer.create(getActivity(), R.raw.two);
        MediaPlayer threeScream = MediaPlayer.create(getActivity(), R.raw.three);
        MediaPlayer fourScream = MediaPlayer.create(getActivity(), R.raw.four);
        MediaPlayer fiveScream = MediaPlayer.create(getActivity(), R.raw.five);
        MediaPlayer sixScream = MediaPlayer.create(getActivity(), R.raw.six);

        MediaPlayer ringTable[]={
                //oneScream,
                twoScream,threeScream,fourScream,fiveScream,sixScream};
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonSubtract.setEnabled(false);
                buttonAdd.setEnabled(false);

                int delay = 1000;
                int period = 1000;
                countdown = new Timer();
                interval = Integer.parseInt(String.valueOf(secs + 4));
                countdown.scheduleAtFixedRate(new TimerTask() {

                    public void run() {
                        int ret = setInterval();
                        if (ret == secs) {
                            beginBell.start();
                            new Handler(Looper.getMainLooper()).post(new Runnable(){
                                @Override
                                public void run() {
                                    text1.setText(String.valueOf(ret));
                                }
                            });
                        }
                        else if ((ret % 4==0) && (ret < secs && ret > 1)) {
                            System.out.println(ret + "scream");
                            new Handler(Looper.getMainLooper()).post(new Runnable(){
                                @Override
                                public void run() {
                                    text1.setText(String.valueOf(ret));
                                }
                            });

                            toast.show();

                            int range = ringTable.length - 1;
                            int voice = (int)Math.round(Math.random()*range);
                            ringTable[voice].start();
                        }
                        else if (ret == 0) {
                            endBell.start();
                            new Handler(Looper.getMainLooper()).post(new Runnable(){
                                @Override
                                public void run() {
                                    buttonSubtract.setEnabled(true);
                                    buttonAdd.setEnabled(true);
                                    text1.setText(String.valueOf(ret));
                                }
                            });

                        }
                        else {
                            System.out.println(ret);
                            new Handler(Looper.getMainLooper()).post(new Runnable(){
                                @Override
                                public void run() {
                                    text1.setText(String.valueOf(ret));
                                }
                            });
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