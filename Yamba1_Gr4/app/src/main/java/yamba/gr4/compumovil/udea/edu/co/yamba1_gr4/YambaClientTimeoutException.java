
package yamba.gr4.compumovil.udea.edu.co.yamba1_gr4;

public class YambaClientTimeoutException extends YambaClientIOException {

    private static final long serialVersionUID = -3792023133642909075L;

    public YambaClientTimeoutException(String detailMessage) {
        super(detailMessage);
    }

    public YambaClientTimeoutException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
