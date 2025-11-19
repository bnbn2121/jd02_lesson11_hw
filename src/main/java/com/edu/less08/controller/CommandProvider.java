package com.edu.less08.controller;

import com.edu.less08.controller.impl.*;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
    private static Map<CommandName, Command> commandsMap;

    static {
        commandsMap = new HashMap<>();
        commandsMap.put(CommandName.GO_TO_MAIN_PAGE, new GoToMainPage());
        commandsMap.put(CommandName.GO_TO_AUTH_PAGE, new GoToAuthPage());
        commandsMap.put(CommandName.GO_TO_REGISTRATION_PAGE, new GoToRegistrationPage());
        commandsMap.put(CommandName.GO_TO_NEWS_REDACTOR_PAGE, new GoToNewsRedactorPage());
        commandsMap.put(CommandName.DO_AUTH, new DoAuth());
        commandsMap.put(CommandName.DO_REGISTRATION, new DoRegistration());
        commandsMap.put(CommandName.DO_LOG_OUT, new DoLogOut());
        commandsMap.put(CommandName.ADD_NEWS, new AddNews());
        commandsMap.put(CommandName.VIEW_NEWS, new ViewNews());
        commandsMap.put(CommandName.EDIT_NEWS, new EditNews());
        commandsMap.put(CommandName.DELETE_NEWS, new DeleteNews());
        commandsMap.put(CommandName.ADD_COMMENT, new AddComment());
        commandsMap.put(CommandName.DELETE_COMMENT, new DeleteComment());
    }

    private CommandProvider() {}

    public static Command getCommand(String inputCommand) {
        try {
            CommandName commandName = CommandName.valueOf(inputCommand.toUpperCase());
            return commandsMap.get(commandName);
        } catch (IllegalArgumentException | NullPointerException e) {
            return null;
        }
    }

    public static CommandName getCommandName(String inputCommand) {
        try {
            return CommandName.valueOf(inputCommand.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            return null;
        }
    }
}
