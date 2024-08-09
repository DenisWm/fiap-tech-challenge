package com.fiap.tech.common.application;

public abstract class UseCase<IN, OUT> {

    public abstract OUT execute(IN anIN);
}