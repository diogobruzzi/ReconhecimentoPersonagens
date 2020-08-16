/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opencv;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;

import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.functions.MultilayerPerceptron;
import weka.classifiers.lazy.IBk;
import weka.classifiers.rules.JRip;
import weka.classifiers.rules.OneR;
import weka.classifiers.trees.J48;
import weka.core.Debug.Random;
import weka.core.Instances;
import weka.core.SelectedTag;
import weka.core.converters.ConverterUtils.DataSource;

/**
 *
 * @author Jones
 */
public class Testes {
    public static void main(String args[]) throws Exception {
        DataSource fonte = new DataSource("src\\opencv\\caracteristicas.arff");
        
        int folds = 10;
        int vezes = 30;
        
        //Classifier classificador = new NaiveBayes();
        //Classifier classificador = new J48();
        //Classifier classificador = new OneR();
        
        //JRip jrip = new JRip();
        //jrip.setUsePruning(false);
        //Classifier classificador = jrip;
        
        //Classifier classificador = new IBk(3);
        
        //LibSVM svm = new LibSVM();
        //svm.setKernelType(new SelectedTag(LibSVM.KERNELTYPE_LINEAR, 
        //    LibSVM.TAGS_KERNELTYPE));
        //Classifier classificador = svm;
        
        Classifier classificador = new MultilayerPerceptron();
        
        
        Instances dados = fonte.getDataSet();
        dados.setClassIndex(dados.numAttributes() - 1);
        
        for (int i = 1; i <= vezes; i++) {
            Evaluation avaliador = new Evaluation(dados);
            avaliador.crossValidateModel(classificador, dados, folds, new Random(i));
            System.out.println(String.valueOf(avaliador.pctCorrect()).replace('.', ','));
        }
    }
}
