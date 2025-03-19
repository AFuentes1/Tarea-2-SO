package com.mycompany.liebreyconejo;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class PanelCarrera extends JPanel {
    private Image pista; // Imagen de la pista
    private BufferedImage spriteLiebre; // Imagen del sprite animado de la liebre
    private BufferedImage liebreDormidaImg; // Imagen del conejo dormido
    private BufferedImage tortuga; // Imagen del sprite de la tortuga

    private int frameActual = 0; // Frame actual de la animación de la liebre
    private int frameTortuga = 0; // Frame actual para la animación de la tortuga

    private final int TOTAL_FRAMES = 4;
    private final int FRAME_WIDTH = 96;
    private final int FRAME_HEIGHT = 63;
    private boolean liebreDormida = false;

    private final int TOTAL_FRAMES_TORTUGA = 8;
    private final int FRAME_WIDTH_TORTUGA = 155;
    private final int FRAME_HEIGHT_TORTUGA = 136;

    private int xLiebre = 50, yLiebre = 100; // Posición inicial de la liebre
    private Timer animacionLiebre;

    private int xTortuga = 50, yTortuga = 310; // Posición inicial de la tortuga
    private Timer animacionTortuga;

    private JTextField velocidadLiebreInput;
    private JTextField velocidadTortugaInput;

    public PanelCarrera() {
        // Cargar la imagen de la pista
        pista = cargarImagen("Imagenes/Pista.jpg");

        // Cargar el sprite de la liebre
        spriteLiebre = cargarImagenBuffered("Imagenes/Rabbit.png");

        // Cargar la imagen del conejo dormido
        liebreDormidaImg = cargarImagenBuffered("Imagenes/SleepRabbit.png");

        // Cargar la imagen de la tortuga
        tortuga = cargarImagenBuffered("Imagenes/Tortuga.png");

        // Hilo para cambiar los frames de la liebre periódicamente
        animacionLiebre = new Timer(100, e -> {
            if (!liebreDormida) {
                frameActual = (frameActual + 1) % TOTAL_FRAMES;
                repaint();
            }
        });
        animacionLiebre.start();

        // Animación de la tortuga
        animacionTortuga = new Timer(100, e -> {
            frameTortuga = (frameTortuga + 1) % TOTAL_FRAMES_TORTUGA;
            repaint();
        });
        animacionTortuga.start();

        // Configurar los cuadros de texto para la velocidad
        SwingUtilities.invokeLater(() -> configurarPanelVelocidades());
    }

    private void configurarPanelVelocidades() {
        JPanel panelControl = new JPanel();
        panelControl.setLayout(new GridLayout(1, 4, 5, 5));

        JLabel labelLiebre = new JLabel("Velocidad Liebre:");
        velocidadLiebreInput = new JTextField("15", 5); // Valor inicial
        JLabel labelTortuga = new JLabel("Velocidad Tortuga:");
        velocidadTortugaInput = new JTextField("5", 5); // Valor inicial

        panelControl.add(labelLiebre);
        panelControl.add(velocidadLiebreInput);
        panelControl.add(labelTortuga);
        panelControl.add(velocidadTortugaInput);

        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (frame != null) {
            frame.add(panelControl, BorderLayout.SOUTH);
            frame.revalidate();
            frame.repaint();
        }
    }

    private Image cargarImagen(String ruta) {
        URL url = getClass().getClassLoader().getResource(ruta);
        if (url != null) {
            return new ImageIcon(url).getImage();
        } else {
            System.out.println("❌ ERROR: No se encontró la imagen " + ruta);
            return null;
        }
    }

    private BufferedImage cargarImagenBuffered(String ruta) {
        try {
            URL url = getClass().getClassLoader().getResource(ruta);
            if (url != null) {
                return ImageIO.read(url);
            } else {
                System.out.println("❌ ERROR: No se encontró la imagen " + ruta);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void actualizarPosicion(int xLiebre, int xTortuga) {
        this.xLiebre = xLiebre;
        this.xTortuga = xTortuga;
        repaint();
    }

    public int getXLiebre() {
        return xLiebre;
    }

    public int getXTortuga() {
        return xTortuga;
    }

    public void dormirLiebre() {
        if (!liebreDormida) {
            liebreDormida = true;
            animacionLiebre.stop();
            repaint();
        }
    }

    public void despertarLiebre() {
        liebreDormida = false;
        animacionLiebre.start();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibujar la pista
        if (pista != null) {
            g.drawImage(pista, 0, 0, getWidth(), getHeight(), this);
        }

        // Dibujar la liebre dormida o animada
        if (liebreDormida) {
            if (liebreDormidaImg != null) {
                g.drawImage(liebreDormidaImg, xLiebre, yLiebre, 80, 80, this);
            } else {
                System.out.println("❌ ERROR: Imagen del conejo dormido no disponible.");
            }
        } else {
            if (spriteLiebre != null) {
                int xFrame = frameActual * FRAME_WIDTH;
                BufferedImage subimage = spriteLiebre.getSubimage(xFrame, 0, FRAME_WIDTH, FRAME_HEIGHT);
                g.drawImage(subimage, xLiebre, yLiebre, FRAME_WIDTH, FRAME_HEIGHT, this);
            }
        }

        // Dibujar la tortuga
        if (tortuga != null) {
            int xFrame = frameTortuga * FRAME_WIDTH_TORTUGA;
            BufferedImage subimage = tortuga.getSubimage(xFrame, 0, FRAME_WIDTH_TORTUGA, FRAME_HEIGHT_TORTUGA);
            int nuevoAncho = FRAME_WIDTH_TORTUGA / 2;
            int nuevoAlto = FRAME_HEIGHT_TORTUGA / 2;
            g.drawImage(subimage, xTortuga, yTortuga, nuevoAncho, nuevoAlto, this);
        } else {
            System.out.println("❌ ERROR: No se puede dibujar la tortuga.");
        }
    }
}
