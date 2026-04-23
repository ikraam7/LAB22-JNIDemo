package com.example.jnidemo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public native String greetNative();
    public native int    computeFactorial(int n);
    public native String flipString(String s);
    public native int    arrayTotal(int[] data);
    public native long   benchmarkNative(int iterations);

    static {
        System.loadLibrary("jnidemo");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // References to UI
        View mainContent = findViewById(R.id.mainContent);
        TextView tvGreet = findViewById(R.id.tvGreet);
        TextView tvFacto = findViewById(R.id.tvFacto);
        TextView tvFlip  = findViewById(R.id.tvFlip);
        TextView tvTotal = findViewById(R.id.tvTotal);
        TextView tvBenchmark = findViewById(R.id.tvBenchmark);

        // 🚀 BONUS: Animation au lancement
        mainContent.setAlpha(0f);
        mainContent.animate().alpha(1f).setDuration(1000);

        // ① Salutation native
        tvGreet.setText(greetNative());

        // ② Factorielle (Amélioration UX)
        int n = 10;
        int facto = computeFactorial(n);
        tvFacto.setText(
                "Input value : n = " + n + "\n" +
                (facto >= 0 ? "Computed result : " + facto : "Error : " + facto)
        );

        // ③ Inversion (Amélioration UX)
        String original = "Android NDK rocks!";
        String flipped  = flipString(original);
        tvFlip.setText(
                "Input string : \"" + original + "\"\n" +
                "Processed result : \"" + flipped + "\""
        );

        // ④ Somme (Amélioration UX)
        int[] nums = {5, 15, 25, 35, 45};
        int total  = arrayTotal(nums);
        tvTotal.setText(
                "Input array : [5, 15, 25, 35, 45]\n" +
                "Summation total : " + total
        );

        // ⑤ Benchmark (Extension C)
        int iterations = 10000000;
        
        long startJava = System.nanoTime();
        long resultJava = benchmarkJava(iterations);
        long endJava = System.nanoTime();
        long timeJava = (endJava - startJava) / 1000000; // ms

        long startNative = System.nanoTime();
        long resultNative = benchmarkNative(iterations);
        long endNative = System.nanoTime();
        long timeNative = (endNative - startNative) / 1000000; // ms

        tvBenchmark.setText(
                "Iterations : " + iterations + "\n" +
                "Java : " + timeJava + "ms\n" +
                "Native : " + timeNative + "ms\n" +
                "Speedup : " + String.format("%.2f", (double)timeJava/timeNative) + "x"
        );
    }

    private long benchmarkJava(int iterations) {
        long sum = 0;
        for (int i = 0; i < iterations; i++) {
            sum += i;
        }
        return sum;
    }
}