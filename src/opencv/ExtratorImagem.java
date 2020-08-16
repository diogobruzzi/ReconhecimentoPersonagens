/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opencv;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.bytedeco.javacpp.indexer.UByteIndexer;
import org.bytedeco.javacpp.indexer.UByteRawIndexer;
import static org.bytedeco.opencv.global.opencv_core.CV_8UC;
import org.bytedeco.opencv.opencv_core.Mat;

/**
 *
 * @author Jones
 */
public class ExtratorImagem {

    public float[] extrairCaracteristicasImagem(String caminho) throws IOException {
        float laranjaCamisaBart = 0, azulCalcaoBart = 0, azulSapatoBart = 0;
        float azulCalcaHomer = 0, marromBocaHomer = 0, cinzaSapatoHomer = 0;
        double red, green, blue;
        float[] caracteristicas = new float[6];

        //IplImage imagem = cvLoadImage(caminho);
        BufferedImage BuffImagemOriginal = ImageIO.read(new File(caminho));
        Mat imagem = bufferedImageToMat(BuffImagemOriginal);
        
        UByteIndexer imgIndexer = imagem.createIndexer();

        for (int altura = 0; altura < imgIndexer.rows(); altura++) {
            for (int largura = 0; largura < imgIndexer.cols(); largura++) {

                // Extração do RGB de cada pixel da imagem
                int[] pixel = new int[3];
                imgIndexer.get(altura, largura, pixel);
                blue = pixel[0]; //azul
                green = pixel[1]; //verde
                red = pixel[2]; //vermelho

                // Camisa laranja do Bart                    
                if (blue >= 11 && blue <= 22
                        && green >= 85 && green <= 105
                        && red >= 240 && red <= 255) {

                    // Incrementa a quantidade de pixels laranja
                    laranjaCamisaBart++;
                }

                // Calção azul do Bart (metade de baixo da imagem)
                if (altura > (imagem.rows() / 2)) {
                    if (blue >= 125 && blue <= 170
                            && green >= 0 && green <= 12
                            && red >= 0 && red <= 20) {

                        azulCalcaoBart++;
                    }
                }

                // Sapato do Bart (parte inferior da imagem)
                if (altura > (imagem.rows() / 2) + (imagem.rows() / 3)) {
                    if (blue >= 125 && blue <= 140
                            && green >= 3 && green <= 12
                            && red >= 0 && red <= 20) {

                        azulSapatoBart++;
                    }
                }

                // Calça azul do Homer
                if (blue >= 150 && blue <= 180
                        && green >= 98 && green <= 120
                        && red >= 0 && red <= 90) {

                    azulCalcaHomer++;
                }

                // Boca do Homer (pouco mais da metade da imagem)
                if (altura < (imagem.rows() / 2) + (imagem.rows() / 3)) {
                    if (blue >= 95 && blue <= 140
                            && green >= 160 && green <= 185
                            && red >= 175 && red <= 200) {
                        marromBocaHomer++;
                    }
                }

                // Sapato do Homer (parte inferior da imagem)
                if (altura > (imagem.rows() / 2) + (imagem.rows() / 3)) {
                    if (blue >= 25 && blue <= 45 && green >= 25
                            && green <= 45 && red >= 25 && red <= 45) {

                        cinzaSapatoHomer++;
                    }
                }
            }
        }

        // Normaliza as características pelo número de pixels totais da imagem
        laranjaCamisaBart = (laranjaCamisaBart / (imagem.rows() * imagem.cols())) * 100;
        azulCalcaoBart = (azulCalcaoBart / (imagem.rows() * imagem.cols())) * 100;
        azulSapatoBart = (azulSapatoBart / (imagem.rows() * imagem.cols())) * 100;
        azulCalcaHomer = (azulCalcaHomer / (imagem.rows() * imagem.cols())) * 100;
        marromBocaHomer = (marromBocaHomer / (imagem.rows() * imagem.cols())) * 100;
        cinzaSapatoHomer = (cinzaSapatoHomer / (imagem.rows() * imagem.cols())) * 100;

        // Grava as características no vetor de características
        caracteristicas[0] = laranjaCamisaBart;
        caracteristicas[1] = azulCalcaoBart;
        caracteristicas[2] = azulSapatoBart;
        caracteristicas[3] = azulCalcaHomer;
        caracteristicas[4] = marromBocaHomer;
        caracteristicas[5] = cinzaSapatoHomer;
        
        return caracteristicas;
    }
    
    public Mat bufferedImageToMat(BufferedImage bi) {
        Mat mat = new Mat(bi.getHeight(), bi.getWidth(), CV_8UC(3));

        int r, g, b;
        UByteRawIndexer indexer = mat.createIndexer();
        for (int y = 0; y < bi.getHeight(); y++) {
            for (int x = 0; x < bi.getWidth(); x++) {
                int rgb = bi.getRGB(x, y);

                r = (byte) ((rgb >> 0) & 0xFF);
                g = (byte) ((rgb >> 8) & 0xFF);
                b = (byte) ((rgb >> 16) & 0xFF);

                indexer.put(y, x, 0, r);
                indexer.put(y, x, 1, g);
                indexer.put(y, x, 2, b);
            }
        }
        indexer.release();
        return mat;
    }
}
