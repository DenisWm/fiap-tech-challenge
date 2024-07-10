package com.fiap.tech.fiap_tech_challenge.common.application;

public abstract class UseCase<IN, OUT> {

    public abstract OUT execute(IN anIN);
}