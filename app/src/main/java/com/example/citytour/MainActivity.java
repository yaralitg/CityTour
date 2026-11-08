package com.example.citytour;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends AppCompatActivity {

    ViewPager2 carrusel;
    Button btnAnterior, btnSiguiente, btnPlay, btnPausa, btnStop;
    SeekBar seekVolumen;
    TextView txtEstado;

    MediaPlayer mediaPlayer;
    float volumen = 0.5f;

    // Imagenes del carrusel
    int[] imagenes = {
            R.drawable.monumento1,
            R.drawable.monumento2,
            R.drawable.monumento3
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enlazar las vistas con el XML
        carrusel = findViewById(R.id.carrusel);
        btnAnterior = findViewById(R.id.btnAnterior);
        btnSiguiente = findViewById(R.id.btnSiguiente);
        btnPlay = findViewById(R.id.btnPlay);
        btnPausa = findViewById(R.id.btnPausa);
        btnStop = findViewById(R.id.btnStop);
        seekVolumen = findViewById(R.id.seekVolumen);
        txtEstado = findViewById(R.id.txtEstado);

        // Carrusel
        carrusel.setAdapter(new ImagenAdapter(imagenes));

        btnAnterior.setOnClickListener(v -> {
            int actual = carrusel.getCurrentItem();
            if (actual > 0) {
                carrusel.setCurrentItem(actual - 1);
            }
        });

        btnSiguiente.setOnClickListener(v -> {
            int actual = carrusel.getCurrentItem();
            if (actual < imagenes.length - 1) {
                carrusel.setCurrentItem(actual + 1);
            }
        });

        // Boton PLAY (tambien sirve para reanudar)
        btnPlay.setOnClickListener(v -> {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(this, R.raw.explicacion);
                mediaPlayer.setVolume(volumen, volumen);
                // Cuando el audio termina se libera
                mediaPlayer.setOnCompletionListener(mp -> {
                    liberarAudio();
                    txtEstado.setText("Estado: Terminado");
                });
            }
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                txtEstado.setText("Estado: Reproduciendo");
            }
        });

        // Boton PAUSA
        btnPausa.setOnClickListener(v -> {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                txtEstado.setText("Estado: En pausa");
            }
        });

        // Boton STOP
        btnStop.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                liberarAudio();
                txtEstado.setText("Estado: Detenido");
            }
        });

        // Control de volumen
        seekVolumen.setProgress(50);
        seekVolumen.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                volumen = progress / 100f;
                if (mediaPlayer != null) {
                    mediaPlayer.setVolume(volumen, volumen);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    // Detiene y libera el MediaPlayer
    private void liberarAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    // Si la app se pausa (el usuario sale), se pausa el audio
    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            txtEstado.setText("Estado: En pausa");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        liberarAudio();
    }
}
