package com.mycompany.liebreyconejo;

public class Tortuga extends Thread {
    private PanelCarrera panel;
    private int x = 50; // Posición inicial
    private final int VELOCIDAD = 5; // La tortuga es más lenta
    private final int DISTANCIA_FINAL = 700; // Meta

    public Tortuga(PanelCarrera panel) {
        this.panel = panel;
    }

    @Override
    public void run() {
        while (x < DISTANCIA_FINAL) {
            x += VELOCIDAD; // La tortuga avanza lentamente pero sin parar
            panel.actualizarPosicion(panel.getXLiebre(), x);

            try {
                Thread.sleep(75);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("🐢 ¡La tortuga ha ganado la carrera!");
    }
}
