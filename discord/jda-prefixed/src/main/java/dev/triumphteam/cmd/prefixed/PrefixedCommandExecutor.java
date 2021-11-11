/**
 * MIT License
 *
 * Copyright (c) 2019-2021 Matt
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package dev.triumphteam.cmd.prefixed;

import dev.triumphteam.cmd.core.message.MessageKey;
import dev.triumphteam.cmd.core.message.MessageRegistry;
import dev.triumphteam.cmd.core.message.context.DefaultMessageContext;
import dev.triumphteam.cmd.prefixed.command.PrefixedCommand;
import dev.triumphteam.cmd.prefixed.factory.PrefixedCommandProcessor;
import dev.triumphteam.cmd.prefixed.sender.PrefixedSender;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

final class PrefixedCommandExecutor {

    private final Map<String, PrefixedCommand> commands = new HashMap<>();

    private final MessageRegistry<PrefixedSender> messageRegistry;

    public PrefixedCommandExecutor(@NotNull final MessageRegistry<PrefixedSender> messageRegistry) {
        this.messageRegistry = messageRegistry;
    }

    public void register(@NotNull final PrefixedCommandProcessor processor) {
        final String name = processor.getName();

        final PrefixedCommand command = commands.computeIfAbsent(name, p -> new PrefixedCommand(processor));

        for (final String alias : processor.getAlias()) {
            commands.putIfAbsent(alias, command);
        }

        command.addSubCommands(processor.getBaseCommand());
    }

    public void execute(
            @NotNull final String commandName,
            @NotNull final PrefixedSender sender,
            @NotNull final List<String> args
    ) {

        final PrefixedCommand command = commands.get(commandName);
        if (command == null) {
            // TODO: 11/6/2021 INVALID COMMAND!
            messageRegistry.sendMessage(MessageKey.UNKNOWN_COMMAND, sender, new DefaultMessageContext(commandName, ""));
            return;
        }

        command.execute(sender, args);
    }

}
