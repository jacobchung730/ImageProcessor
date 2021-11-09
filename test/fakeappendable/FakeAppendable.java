package fakeappendable;

import java.io.IOException;


/**
 * This {@code fakeappendable.FakeAppendable} class is used for testing the  renderMessage methods.
 * This class is a fake appendable that will where these methods will try to append to, but it will
 * not work and end up throwing an exception.
 */
public class FakeAppendable implements Appendable {

  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException();
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new IOException();
  }

  @Override
  public Appendable append(char c) throws IOException {
    throw new IOException();
  }
}
