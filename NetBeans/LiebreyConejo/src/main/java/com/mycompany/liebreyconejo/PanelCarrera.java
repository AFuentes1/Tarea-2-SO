package com.mycompany.liebreyconejo;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class PanelCarrera extends JPanel {
    private Image pista; // Imagen de la pista
    private BufferedImage spriteLiebre; // Imagen del sprite animado de la liebre
    private BufferedImage liebreDormidaImg; // Imagen del conejo dormido
    private BufferedImage tortuga; // Imagen de la tortuga

    
    private int frameActual = 0; // Frame actual de la animación
    private int frameTortuga = 0; // Frame actual para la animación de la tortuga

    private final int TOTAL_FRAMES = 4;
    private final int FRAME_WIDTH = 96;
    private final int FRAME_HEIGHT = 63;
    private boolean liebreDormida = false;

    private final int TOTAL_FRAMES_TORTUGA = 8;
    private final int FRAME_WIDTH_TORTUGA = 155;
    private final int FRAME_HEIGHT_TORTUGA = 136;

    

    private int xLiebre = 50, yLiebre = 105; // Posición inicial de la liebre
    private Timer animacionLiebre;

    private int xTortuga = 50, yTortuga = 320; // Posición inicial de la tortuga
    private Timer animacionTortuga;

    public PanelCarrera() {
        // Cargar la imagen de la pista
        pista = cargarImagen("Imagenes/Pista.jpg");

        // Cargar el sprite de la liebre
        spriteLiebre = cargarImagenBuffered("Imagenes/Rabbit.png");

        // Cargar la imagen del conejo dormido
        liebreDormidaImg = cargarImagenBuffered("Imagenes/SleepRabbit.png");

        //Cargar la imagen de la tortuga 
        tortuga = cargarImagenBuffered("Imagenes/Tortuga.png");

        // Hilo para cambiar los frames de la liebre periódicamente
        animacionLiebre = new Timer(100, e -> {
            if (!liebreDormida) {
                frameActual = (frameActual + 1) % TOTAL_FRAMES;
                repaint();
            }
        });
        animacionLiebre.start();

        //Animación de la tortuga 
        animacionTortuga = new Timer(100, e -> {
            frameTortuga = (frameTortuga + 1) % TOTAL_FRAMES_TORTUGA;
            repaint();
        });
        animacionTortuga.start();
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

        //Dibujamos la pista
        if (pista != null) {
            g.drawImage(pista, 0, 0, getWidth(), getHeight(), this);
        }

        //Dibujar la liebre dormida o despierta
        if (liebreDormida) {
            if (liebreDormidaImg != null) {
                g.drawImage(liebreDormidaImg, xLiebre, yLiebre, 80, 80, this);
            } else {
                System.out.println("❌ ERROR: Imagen del conejo dormido no disponible para dibujar.");
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
            
            // **Escalar la tortuga** (ejemplo: 50% de su tamaño original)
            int escala = 2;  
            int nuevoAncho = FRAME_WIDTH_TORTUGA / escala;
            int nuevoAlto = FRAME_HEIGHT_TORTUGA / escala;
            
            g.drawImage(subimage, xTortuga, yTortuga, nuevoAncho, nuevoAlto, this);
        } else {
            System.out.println("❌ ERROR: No se puede dibujar la tortuga.");
        } 
    }
}
