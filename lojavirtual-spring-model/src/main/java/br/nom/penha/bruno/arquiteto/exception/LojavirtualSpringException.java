package br.nom.penha.bruno.arquiteto.exception;

/**
 * <p>Title: Arquiteto Java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class LojavirtualSpringException extends Exception {
  public LojavirtualSpringException() {
  }

  public LojavirtualSpringException(String msg) {
    super(msg);
  }
  public LojavirtualSpringException(String msg, Throwable t) {
    super(msg, t);
  }
}