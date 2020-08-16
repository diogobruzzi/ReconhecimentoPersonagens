/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opencv;

import java.io.File;
import org.bytedeco.javacpp.DoublePointer;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imread;
import org.bytedeco.opencv.opencv_core.Mat;

/**
 *
 * @author PC
 */
public class Testeee {
    private static Object reconhecedor;
    
    
    public static void main(String[] args) {
        // Diretório onde estão armazenadas as imagens
        File diretorio = new File("src\\imagens");
        
        File[] arquivos = diretorio.listFiles();
        
        int contador = 0;
        
        for (File imagem : arquivos) {

         //lê a imagem
         Mat img = imread(imagem.getAbsolutePath(), 1);
         
        System.out.println(img);
        System.out.println(imagem.getAbsolutePath());
/*
         // extrai a extensao que quer testar a partir do nome da imagem
         //(como ja ta separado por pastas nao vai precisar verificar a extensao)
         String extensao = imagem.getName().split("\\.")[1];

         // extrai o id a partir do nome da imagem
         int label = Integer.parseInt(imagem.getName().substring(7,9));

         // redimensiona imagem
         resize(img, img, new opencv_core.Size(160, 160));
*/
         //Predição
         //IntPointer id_predicao = new IntPointer(1);         //label = id
         //DoublePointer confianca = new DoublePointer(1);     //nivel de confiança
         //reconhecedor.predict(img, id_predicao, confianca);
/*
         int predicao = id_predicao.get(0);

         if (label == predicao){
             System.out.println(imagem.getName() + " foi reconhecido corretamente com confiança de " + confianca.get(0));
         }else{
             System.out.println(imagem.getName() + " foi reconhecido incorretamente com confiança de " + confianca.get(0));

         }*/
         contador++;
     }
    }
}
