package opencv;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.bytedeco.javacpp.indexer.UByteIndexer;
import static org.bytedeco.opencv.global.opencv_highgui.cvWaitKey;
import static org.bytedeco.opencv.global.opencv_highgui.imshow;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;
import org.bytedeco.opencv.opencv_core.Mat;


public class ExtratorCaracteristicas {

    public static void main(String[] args) throws IOException {
        int proximaImagem, classePersonagem;
        String classePersonagemString;
        double red, green, blue;

        // Cabeçalho do arquivo Weka
	String exportacao = "@relation caracteristicas\n\n";
	exportacao += "@attribute laranja_camisa_bart real\n";
	exportacao += "@attribute azul_calcao_bart real\n";
	exportacao += "@attribute azul_sapato_bart real\n";
	exportacao += "@attribute marrom_boca_homer real\n";
	exportacao += "@attribute azul_calca_homer real\n";
	exportacao += "@attribute cinza_sapato_homer real\n";
	exportacao += "@attribute classe {Bart, Homer}\n\n";
	exportacao += "@data\n";
        
        // Diretório onde estão armazenadas as imagens
        File diretorio = new File("src\\imagens");
        File[] arquivos = diretorio.listFiles();

        // Características do Homer e Bart
        float laranjaCamisaBart, azulCalcaoBart, azulSapatoBart;
        float azulCalcaHomer, marromBocaHomer, cinzaSapatoHomer;
        
        // Definição do vetor de características
        float[][] caracteristicas = new float[293][7];

        // Percorre todas as imagens do diretório
        for (int i = 0; i < arquivos.length; i++) {
            laranjaCamisaBart = 0;
            azulCalcaoBart = 0;
            azulSapatoBart = 0;
            azulCalcaHomer = 0;
            marromBocaHomer = 0;
            cinzaSapatoHomer = 0;

            // Carrega cada imagem do diretório
            
            //  Mat imagemOriginal = imread(diretorio.getAbsolutePath() + "\\" + arquivos[i].getName());
            //  ** caso a linha acima não funcione, troque pela linha de baixo **
            Mat imagemOriginal = imread("src\\imagens\\" + arquivos[i].getName());
            //  obs: caso as fotos estejam num local diferente só altere "src\\imagens\\" pelo caminho do diretório das imagens relativo ao diretório principal do projeto 
            // (e não esqueça de alterar de \\ para / caso utilize outro sistema operacional)
            
            // (deixamos as duas opçoes pois a primeira pode não dar certo quando há caracteres especiais no caminho absouto (do diretório do projeto), 
            // ou pode dar isso dependendo do Sistema Operacional, então o JavaCV vai exibir um erro. Caso ocorra utilize a segunda forma)
            
            // Imagem processada - cria uma imagem no formato Mat (recomendado para as versões mais recentes)
            Mat imagemProcessada = new Mat(imagemOriginal.clone());

            // Definição da classe - Homer ou Bart
            // Aprendizagem supervisionada
            if (arquivos[i].getName().charAt(0) == 'b') {
                classePersonagem = 0;
                classePersonagemString = "Bart";
            } else {
                classePersonagem = 1;
                classePersonagemString = "Homer";
            }
            
            // Cria um indexer para poder acessar os valores dos pixels da imagem de forma mais rápida
            UByteIndexer imgIndexer = imagemOriginal.createIndexer();
            UByteIndexer imgProcessada = imagemProcessada.createIndexer();

            // Varre a imagem pixel a pixel
            for (int altura = 0; altura < imgIndexer.rows(); altura++) {
                for (int largura = 0; largura < imgIndexer.cols(); largura++) {
                    //inicializa uma array para os valores do pixel (3 valores pois são 3 canais de cor: R G B) 
                    int[] pixel = new int[3]; 
                     // Extração do RGB de cada pixel da imagem
                    imgIndexer.get(altura, largura, pixel);
                    blue = pixel[0]; //azul
                    green = pixel[1]; //verde
                    red = pixel[2]; //vermelho
                    
                    // Camisa laranja do Bart                    
                    if (blue >= 11 && blue <= 22 && 
                        green >= 85 && green <= 105 && 
                        red >= 240 && red <= 255) {                       
                        // Pinta a imagem processada com o verde limão
                        imgProcessada.put(altura, largura, new int[]{0, 255, 128});

                        // Incrementa a quantidade de pixels laranja
                        laranjaCamisaBart++;
                    }

                    // Calção azul do Bart (metade de baixo da imagem)
                    if (altura > (imgIndexer.rows() / 2)) {
                        if (blue >= 125 && blue <= 170 && 
                                green >= 0 && green <= 12 && 
                                red >= 0 && red <= 20) {
                            imgProcessada.put(altura, largura, new int[]{0, 255, 128});

                            azulCalcaoBart++;
                        }
                    }

                    // Sapato do Bart (parte inferior da imagem)
                    if (altura > (imgIndexer.rows() / 2) + (imgIndexer.rows() / 3)) {
                        if (blue >= 125 && blue <= 140 && 
                                green >= 3 && green <= 12 && 
                                red >= 0 && red <= 20) {
                            imgProcessada.put(altura, largura, new int[]{0, 255, 128});

                            azulSapatoBart++;
                        }
                    }

                    // Calça azul do Homer
                    if (blue >= 150 && blue <= 180 && 
                            green >= 98 && green <= 120 && 
                            red >= 0 && red <= 90) {
                        imgProcessada.put(altura, largura, new int[]{0, 255, 255});

                        azulCalcaHomer++;
                    }

                    // Boca do Homer (pouco mais da metade da imagem)
                    if (altura < (imgIndexer.rows() / 2) + (imgIndexer.rows() / 3)) {
                        if (blue >= 95 && blue <= 140 && 
                                green >= 160 && green <= 185 && 
                                red >= 175 && red <= 200) {
                            imgProcessada.put(altura, largura, new int[]{0, 255, 255});

                            marromBocaHomer++;
                        }
                    }

                    // Sapato do Homer (parte inferior da imagem)
                    if (altura > (imgIndexer.rows() / 2) + (imgIndexer.rows() / 3)) {
                        if (blue >= 25 && blue <= 45 && green >= 25 && 
                                green <= 45 && red >= 25 && red <= 45) {
                            imgProcessada.put(altura, largura, new int[]{0, 255, 255});

                            cinzaSapatoHomer++;
                        }
                    }
                        
                }
            }
            

            // Normaliza as características pelo número de pixels totais da imagem
            laranjaCamisaBart = (laranjaCamisaBart / (imagemOriginal.rows() * imagemOriginal.cols())) * 100;
            azulCalcaoBart = (azulCalcaoBart / (imagemOriginal.rows() * imagemOriginal.cols())) * 100;
            azulSapatoBart = (azulSapatoBart / (imagemOriginal.rows() * imagemOriginal.cols())) * 100;
            azulCalcaHomer = (azulCalcaHomer / (imagemOriginal.rows() * imagemOriginal.cols())) * 100;
            marromBocaHomer = (marromBocaHomer / (imagemOriginal.rows() * imagemOriginal.cols())) * 100;
            cinzaSapatoHomer = (cinzaSapatoHomer / (imagemOriginal.rows() * imagemOriginal.cols())) * 100;
            //obs: rows() corresponde ao height() e cols() corresponde ao width()
            
            // Grava as características no vetor de características
            caracteristicas[i][0] = laranjaCamisaBart;
            caracteristicas[i][1] = azulCalcaoBart;
            caracteristicas[i][2] = azulSapatoBart;
            caracteristicas[i][3] = azulCalcaHomer;
            caracteristicas[i][4] = marromBocaHomer;
            caracteristicas[i][5] = cinzaSapatoHomer;
            caracteristicas[i][6] = classePersonagem;

            System.out.println("Laranja camisa Bart: " + caracteristicas[i][0] + " - Azul calção Bart: " + caracteristicas[i][1] + " - Azul sapato Bart: " + caracteristicas[i][2] + " - Azul calça Homer: " + caracteristicas[i][3] + " - Marrom boca Homer: " + caracteristicas[i][4] + " - Preto sapato Homer: " + caracteristicas[i][5] + " - Classe: " + caracteristicas[i][6]);
            exportacao += caracteristicas[i][0] + "," + caracteristicas[i][1] + "," + caracteristicas[i][2] + "," + caracteristicas[i][3] + "," + caracteristicas[i][4] + "," + caracteristicas[i][5] + "," + classePersonagemString + "\n";

            imshow("Imagem original", imagemOriginal);
            // Imagem processada de acordo com as características (alteração das cores)
            imshow("Imagem processada", imagemProcessada);
            proximaImagem = cvWaitKey();
        }

        // Grava o arquivo ARFF no disco
        File arquivo = new File("caracteristicas.arff");
        FileOutputStream f = new FileOutputStream(arquivo);
        f.write(exportacao.getBytes());
        f.close();
    }
}