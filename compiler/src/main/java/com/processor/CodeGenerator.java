package com.processor;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import java.util.ArrayList;
import static com.squareup.javapoet.ClassName.get;
import static com.squareup.javapoet.MethodSpec.methodBuilder;
import static com.squareup.javapoet.TypeSpec.classBuilder;
import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

final class CodeGenerator {

    private static final String CLASS_NAME = "InitContent";

    public static TypeSpec generateClass(ArrayList<AnnotatedField> annotatedFields) {
        TypeSpec.Builder builder = classBuilder(CLASS_NAME)
                .addModifiers(PUBLIC, FINAL);
        builder.addMethod(createInitContentMethod(annotatedFields.get(0)));
        return builder.build();
    }

    private static MethodSpec createInitContentMethod(AnnotatedField annotatedField){
        return methodBuilder("injectMainActivityContent")
                .addModifiers(PUBLIC, STATIC)
                .addParameter(get(annotatedField.getTypeElement().asType()), "mainActivity")
                .addStatement("mainActivity." + "mTvHello.setText(\"prueba\")")
        //        .addStatement("mainActivity."+ annotatedField.getVariableName() + ".setText(\"prueba\")")
                //.addStatement("mainActivity."+ annotatedField.getVariableName() + ".setText(LocalizablesFacade.getString(getActivity(), OneCMSKeys." + annotatedField.getOneCMSKey() + "))")
                .returns(TypeName.VOID)
                .build();
    }
}
