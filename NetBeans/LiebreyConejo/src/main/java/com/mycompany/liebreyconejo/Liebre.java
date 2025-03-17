package com.mycompany.liebreyconejo;

public class Liebre extends Thread {
    private PanelCarrera panel;
    private int x = 50; // Posición inicial
    private final int VELOCIDAD = 15; // La liebre avanza más rápido que la tortuga
    private final int DISTANCIA_FINAL = 700; // Meta
    private boolean yaDurmio = false; // Variable para controlar si la liebre ya se ha dormido

    public Liebre(PanelCarrera panel) {
        this.panel = panel;
    }

    @Override
    public void run() {
        while (x < DISTANCIA_FINAL) {
            x += VELOCIDAD; // La liebre avanza rápidamente

            // Simula que la liebre solo se duerme una vez en toda la carrera
            if (!yaDurmio && Math.random() < 0.1) { // 10% de probabilidad de dormir
                yaDurmio = true; // Marca que ya se ha dormido
                System.out.println("😴 La liebre se ha dormido...");
                panel.dormirLiebre();
                try {
                    Thread.sleep(6000); // Se duerme por 3 segundos
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                panel.despertarLiebre();
            }

            panel.actualizarPosicion(x, panel.getXTortuga());

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("🐇 La liebre ha llegado a la meta.");
    }
}
