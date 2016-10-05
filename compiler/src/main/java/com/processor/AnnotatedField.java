package com.processor;

import javax.lang.model.element.TypeElement;

public class AnnotatedField {

    CharSequence variableName;
    TypeElement typeElement;
    String oneCMSKey;

    public AnnotatedField (TypeElement typeElement, String oneCMSKey){
        this.variableName = typeElement.getSimpleName();
        this.typeElement = typeElement;
        this.oneCMSKey = oneCMSKey;
    }

    public CharSequence getVariableName() {
        return variableName;
    }

    public TypeElement getTypeElement() {
        return typeElement;
    }

    public String getOneCMSKey() {
        return oneCMSKey;
    }
}
