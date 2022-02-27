package dev.triumphteam.cmd.core.argument.named;

import org.jetbrains.annotations.NotNull;

public interface Argument {

    /**
     * Creates a builder for a {@link String} argument.
     *
     * @return A {@link ArgumentBuilder} for a {@link String} argument.
     */
    static ArgumentBuilder forString() {
        return new ArgumentBuilder(String.class);
    }

    /**
     * Creates a builder for a {@link Integer} argument.
     *
     * @return A {@link ArgumentBuilder} for a {@link Integer} argument.
     */
    static ArgumentBuilder forInt() {
        return new ArgumentBuilder(int.class);
    }

    /**
     * Creates a builder for a {@link Float} argument.
     *
     * @return A {@link ArgumentBuilder} for a {@link Float} argument.
     */
    static ArgumentBuilder forFloat() {
        return new ArgumentBuilder(float.class);
    }

    /**
     * Creates a builder for a {@link Double} argument.
     *
     * @return A {@link ArgumentBuilder} for a {@link Double} argument.
     */
    static ArgumentBuilder forDouble() {
        return new ArgumentBuilder(double.class);
    }

    /**
     * Creates a builder for a {@link Boolean} argument.
     *
     * @return A {@link ArgumentBuilder} for a {@link Boolean} argument.
     */
    static ArgumentBuilder forBoolean() {
        return new ArgumentBuilder(boolean.class);
    }

    /**
     * Creates a builder for a custom type argument.
     *
     * @return A {@link ArgumentBuilder} for a custom type argument.
     */
    static ArgumentBuilder forType(@NotNull final Class<?> type) {
        return new ArgumentBuilder(type);
    }

    @NotNull
    Class<?> getType();

    @NotNull
    String getName();

    @NotNull
    String getDescription();
}
