package com.mycompany.liebreyconejo;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Carrera {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame ventana = new JFrame("Carrera");
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.setSize(800, 600);
            ventana.setResizable(false);

            // Crear el panel de la pista
            PanelCarrera panel = new PanelCarrera();

            // Agregar el panel a la ventana
            ventana.add(panel);
            ventana.setVisible(true);

            // Crear los hilos para la liebre y la tortuga
            Liebre liebre = new Liebre(panel);
            Tortuga tortuga = new Tortuga(panel);

            // Iniciar los hilos (¡comienza la carrera!)
            tortuga.start();
            liebre.start();
        });
    }
}
