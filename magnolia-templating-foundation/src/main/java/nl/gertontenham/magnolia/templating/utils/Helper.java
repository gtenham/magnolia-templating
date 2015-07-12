package nl.gertontenham.magnolia.templating.utils;

import com.github.jknack.handlebars.Options;

/**
 * Interface extending com.github.jknack.handlebars.Helper
 *
 */
public interface Helper<T> extends com.github.jknack.handlebars.Helper<T> {

    /**
     * Apply the helper to the context using provided template in options
     *
     * @param context The context object.
     * @param options The options object.
     * @return A string result.
     *
     */
    CharSequence applyWithCurrentTemplate(T context, Options options);
}
