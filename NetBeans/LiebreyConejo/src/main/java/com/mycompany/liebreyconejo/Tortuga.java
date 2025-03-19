package com.mycompany.liebreyconejo;

public class Tortuga extends Thread {
    private PanelCarrera panel;
    private int x = 50; // Posición inicial
    private int velocidad = 5; // Variable de velocidad (antes era constante)
    private final int DISTANCIA_FINAL = 700; // Meta

    public Tortuga(PanelCarrera panel) {
        this.panel = panel;
    }

    public void setVelocidad(int nuevaVelocidad) {
        this.velocidad = nuevaVelocidad;
    }

    @Override
    public void run() {
        while (x < DISTANCIA_FINAL) {
            x += velocidad; // La tortuga avanza lentamente pero sin parar
            panel.actualizarPosicion(panel.getXLiebre(), x);

            try {
                Thread.sleep(Math.max(50, 125 - (velocidad * 10))); // Velocidad ajustable en tiempo real
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("🐢 ¡La tortuga ha ganado la carrera!");
    }
}
