package ${package}.utils;

import java.net.URI;
import java.net.URISyntaxException;

import ${package}.utils.Fetcher;


public class DefaultFetcher implements Fetcher {

    public String urlJoin(final String baseUrl, final String url) {
        if(url.startsWith("_:")) {
            return url;
        }

        final URI baseUri = _toUri(baseUrl);
        final URI uri = _toUri(url);
        if(baseUri.getScheme() != null && baseUri.getScheme() != "file" && uri.getScheme() == "file") {
            throw new ValidationException(
                String.format("Not resolving potential remote exploit %s from base %s".format(
                    url, baseUrl
                ))
            );
        }
        String result = baseUri.resolve(uri).toString();
        if(result.startsWith("file:")) {
            // Well this is gross - needed for http as well?
            result = "file://" + result.substring("file:".length());
        }
        return result;
    }

    public String fetchText(final String url) {
        return "fetched";
    }

    private URI _toUri(final String url) {
        try {
            return new URI(url);
        } catch(URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
