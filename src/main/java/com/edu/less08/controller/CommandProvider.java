package com.edu.less08.controller;

import com.edu.less08.controller.impl.*;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {
    private Map<CommandName, Command> commandsMap;

    public CommandProvider() {
        this.commandsMap = new HashMap<>();
        commandsMap.put(CommandName.GO_TO_MAIN_PAGE, new GoToMainPage());
        commandsMap.put(CommandName.GO_TO_AUTH_PAGE, new GoToAuthPage());
        commandsMap.put(CommandName.GO_TO_REGISTRATION_PAGE, new GoToRegistrationPage());
        commandsMap.put(CommandName.DO_AUTH, new DoAuth());
        commandsMap.put(CommandName.DO_REGISTRATION, new DoRegistration());
        commandsMap.put(CommandName.DO_LOG_OUT, new DoLogOut());
    }

    public Command getCommand(String inputCommand) {
        try {
            CommandName commandName = CommandName.valueOf(inputCommand.toUpperCase());
            return commandsMap.get(commandName);
        } catch (IllegalArgumentException | NullPointerException e) {
            return null;
        }
    }
}
