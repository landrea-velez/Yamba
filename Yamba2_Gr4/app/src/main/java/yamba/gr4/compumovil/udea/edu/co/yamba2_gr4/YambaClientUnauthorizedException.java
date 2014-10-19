
package yamba.gr4.compumovil.udea.edu.co.yamba2_gr4;

public class YambaClientUnauthorizedException extends YambaClientException {

    private static final long serialVersionUID = -3792023133642909075L;

    public YambaClientUnauthorizedException(String detailMessage) {
        super(detailMessage);
    }

    public YambaClientUnauthorizedException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
}
