package br.gov.sp.fatec.usecases.shared;


public interface UseCase<Input, Output> {
    Output execute(Input input);
}
