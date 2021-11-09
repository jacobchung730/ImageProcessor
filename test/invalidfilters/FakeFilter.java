package invalidfilters;

/**
 * Represents an fake Filter that only checks for invalid kernels.
 */
public class FakeFilter {


  /**
   * Creates a new filter with the given kernel.
   * @param kernel the filter kernel
   */
  public FakeFilter(double[][] kernel) {
    if (kernel.length != kernel[0].length) {
      throw new IllegalArgumentException("Kernel must be square.");
    }
    if (kernel.length % 2 == 0) {
      throw new IllegalArgumentException("kernel must have odd dimension,");
    }
  }



}
