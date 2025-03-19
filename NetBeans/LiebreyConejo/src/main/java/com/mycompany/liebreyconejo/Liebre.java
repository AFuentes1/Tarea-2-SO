package com.mycompany.liebreyconejo;

public class Liebre extends Thread {
    private PanelCarrera panel;
    private int x = 50; // Posición inicial
    private int velocidad = 15; // Variable de velocidad (antes era constante)
    private final int DISTANCIA_FINAL = 700; // Meta
    private boolean yaDurmio = false; // Variable para controlar si la liebre ya se ha dormido

    public Liebre(PanelCarrera panel) {
        this.panel = panel;
    }

    public void setVelocidad(int nuevaVelocidad) {
        this.velocidad = nuevaVelocidad;
    }

    @Override
    public void run() {
        while (x < DISTANCIA_FINAL) {
            x += velocidad; // La liebre avanza rápidamente

            // Simula que la liebre solo se duerme una vez en toda la carrera
            if (!yaDurmio && Math.random() < 0.1) { // 10% de probabilidad de dormir
                yaDurmio = true;
                System.out.println("😴 La liebre se ha dormido...");
                panel.dormirLiebre();
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                panel.despertarLiebre();
            }

            panel.actualizarPosicion(x, panel.getXTortuga());

            try {
                Thread.sleep(Math.max(50, 250 - (velocidad * 10))); // Velocidad ajustable en tiempo real
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("🐇 La liebre ha llegado a la meta.");
    }
}
