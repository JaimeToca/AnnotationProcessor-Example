package com.processor;

import com.google.auto.service.AutoService;
import com.api.InjectContent;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import static com.squareup.javapoet.JavaFile.builder;
import static java.util.Collections.singleton;
import static javax.lang.model.SourceVersion.latestSupported;

@AutoService(Processor.class)
public class ContentProcessor extends AbstractProcessor {

    private Messager messager;
    private Filer filer;
    private Elements elementUtils;
    private Types typeUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        filer = processingEnv.getFiler();
        elementUtils = processingEnv.getElementUtils();
        typeUtils = processingEnv.getTypeUtils();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return singleton(InjectContent.class.getCanonicalName());
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        ArrayList<AnnotatedField> annotatedFields = new ArrayList<>();
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(InjectContent.class)) {
            TypeElement annotatedField = (TypeElement) annotatedElement.getEnclosingElement();
            String oneCMSKey = annotatedElement.getAnnotation(InjectContent.class).key();
            annotatedFields.add(buildFieldAnnotatedField(annotatedField, oneCMSKey));
        }
        try {
            if (!annotatedFields.isEmpty()){
                generateCode(annotatedFields);
            }
        } catch (NoPackageNameException | IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    private AnnotatedField buildFieldAnnotatedField(TypeElement annotatedField, String oneCMSKey){
        return new AnnotatedField(annotatedField, oneCMSKey);
    }

    private void generateCode(ArrayList<AnnotatedField> annotatedFields) throws NoPackageNameException, IOException {
        String packageName = Utils.getPackageName(processingEnv.getElementUtils(), annotatedFields.get(0).getTypeElement());
        TypeSpec generatedClass = CodeGenerator.generateClass(annotatedFields);

        JavaFile javaFile = builder(packageName, generatedClass).build();
        javaFile.writeTo(processingEnv.getFiler());
    }
}
